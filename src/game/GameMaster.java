package game;

import game.rulebooks.RuleBook;
import game.updaters.EndPhaseUpdater;
import game.updaters.GameStartUpdater;
import game.updaters.GameUpdater;
import game.updaters.RerollUpdater;
import game.updaters.SelectActionUpdater;
import game.updaters.SelectDieUpdater;

import java.util.ArrayList;
import java.util.Date;

import Statistics.StatisticManager;
import ai.AIAgent;
import ai.actions.Action;
import ai.actions.BlockPlayerAction;
import ai.actions.DoublePlayerAction;
import ai.actions.EndPhaseAction;
import ai.actions.EndPlayerTurnAction;
import ai.actions.EndSetupAction;
import ai.actions.FollowUpAction;
import ai.actions.FoulPlayerAction;
import ai.actions.HandOffPlayerAction;
import ai.actions.PlayerAction;
import ai.actions.SelectInterceptionAction;
import ai.actions.MovePlayerAction;
import ai.actions.PassPlayerAction;
import ai.actions.PlaceBallAction;
import ai.actions.PlaceBallOnPlayerAction;
import ai.actions.PlacePlayerAction;
import ai.actions.RerollAction;
import ai.actions.SelectCoinSideAction;
import ai.actions.SelectCoinTossEffectAction;
import ai.actions.SelectDieAction;
import ai.actions.SelectPlayerAction;
import ai.actions.SelectPlayerTurnAction;
import ai.actions.SelectPushSquareAction;
import ai.actions.StandPlayerUpAction;
import ai.util.GameStateCloner;

import sound.Sound;
import sound.SoundManager;
import models.BlockSum;
import models.GameStage;
import models.GameState;
import models.PassRange;
import models.Pitch;
import models.Player;
import models.PlayerTurn;
import models.RangeRuler;
import models.Skill;
import models.Square;
import models.Standing;
import models.Team;
import models.TeamFactory;
import models.Weather;
import models.actions.Block;
import models.actions.Catch;
import models.actions.Dodge;
import models.actions.GoingForIt;
import models.actions.HandOff;
import models.actions.Pass;
import models.actions.PickUp;
import models.actions.Push;
import models.dice.BB;
import models.dice.D3;
import models.dice.D6;
import models.dice.D8;
import models.dice.DiceFace;
import models.dice.DiceRoll;
import models.dice.IDice;

public class GameMaster {
	
	private GameState state;
	private RuleBook rulebook;
	private AIAgent homeAgent;
	private AIAgent awayAgent;
	private boolean awaitUI;
	
	public GameMaster(GameState gameState, RuleBook rulebook, AIAgent homeAgent, AIAgent awayAgent) {
		super();
		this.state = gameState;
		this.rulebook = rulebook;
		this.homeAgent = homeAgent;
		this.awayAgent = awayAgent;
		this.awaitUI = false;
	}
	
	/**
	 * Starts the blood bowl game.
	 */
	public void start(){
		while(true)
			update();
	}
	
	/**
	 * Updates the Blood Bowl game.
	 * Decide whether to:
	 * 1) await UI input or
	 * 2) call AI agent
	 */
	public void update(){
		
		// Begin game if not started
		if (state.getGameStage() == GameStage.START_UP){
			GameStartUpdater.getInstance().update(state, null, rulebook);
			return;
		}
		
		// If game is ended
		if (state.getGameStage() == GameStage.GAME_ENDED){
			return;
		}

		boolean home = isHomeTurn(state);
		
		Action action = null;
		GameState clone = new GameStateCloner().clone(state);
		
		// Call AI agent or await UI
		if (home && homeAgent != null)
			action = homeAgent.takeAction(this, clone);
		else if (!home && awayAgent != null)
			action = awayAgent.takeAction(this, clone);
		else
			awaitUI = true;
		
		if (action != null)
			performAIAction(action);
		
	}
	
	/**
	 * Returns true if it is home turn to take action and false if not.
	 * @param state
	 * @return
	 */
	private boolean isHomeTurn(GameState state) {
		
		boolean home = false;
		
		if (state.getGameStage() == GameStage.HOME_TURN){
			home = true;
			if (state.getCurrentPass() != null &&
					state.getCurrentPass().getInterceptionPlayers() != null &&
					state.getCurrentPass().getInterceptionPlayers().size() > 0){
				home = false;
			}
			if (state.isAwaitingReroll() && state.getCurrentDiceRoll() != null && state.getCurrentBlock() != null && state.getCurrentGoingForIt() == null){
				if (!state.isAwaitingFollowUp() && !state.isAwaitingPush())
					home = (state.getHomeTeam() == state.getCurrentBlock().getSelectTeam());
			}
		} else if (state.getGameStage() == GameStage.AWAY_TURN){
			home = false;
			if (state.getCurrentPass() != null &&
					state.getCurrentPass().getInterceptionPlayers() != null &&
					state.getCurrentPass().getInterceptionPlayers().size() > 0){
				home = true;
			}
			if (state.isAwaitingReroll() && state.getCurrentDiceRoll() != null && state.getCurrentBlock() != null && state.getCurrentGoingForIt() == null){
				if (!state.isAwaitingFollowUp() && !state.isAwaitingPush())
					home = (state.getHomeTeam() == state.getCurrentBlock().getSelectTeam());
			}
		} else if (state.getGameStage() == GameStage.COIN_TOSS){
			home = false;
		} else if (state.getGameStage() == GameStage.PICK_COIN_TOSS_EFFECT){
			if (state.getCoinToss().hasAwayPickedHeads() == state.getCoinToss().isResultHeads())
				home = false;
			else 
				home = true;
		} else if (state.getGameStage() == GameStage.KICK_OFF){
			startNewTurn();
		} else if (state.getGameStage() == GameStage.KICKING_SETUP){
			if (state.getKickingTeam() == state.getHomeTeam())
				home = true;
			else if (state.getKickingTeam() == state.getAwayTeam())
				home = false;
			
		} else if (state.getGameStage() == GameStage.RECEIVING_SETUP){
			if (state.getReceivingTeam() == state.getHomeTeam())
				home = true;
			else if (state.getReceivingTeam() == state.getAwayTeam())
				home = false;
		} else if (state.getGameStage() == GameStage.KICK_PLACEMENT){
			if (state.getKickingTeam() == state.getHomeTeam())
				home = true;
			else if (state.getKickingTeam() == state.getAwayTeam())
				home = false;
		} else if (state.getGameStage() == GameStage.PLACE_BALL_ON_PLAYER){
			if (state.getReceivingTeam() == state.getHomeTeam())
				home = true;
			else if (state.getReceivingTeam() == state.getAwayTeam())
				home = false;
		} else if (state.getGameStage() == GameStage.BLITZ){
			if (state.getKickingTeam() == state.getHomeTeam())
				home = true;
			else if (state.getKickingTeam() == state.getAwayTeam())
				home = false;
		} else if (state.getGameStage() == GameStage.QUICK_SNAP){
			if (state.getReceivingTeam() == state.getHomeTeam())
				home = true;
			else if (state.getReceivingTeam() == state.getAwayTeam())
				home = false;
		} else if (state.getGameStage() == GameStage.HIGH_KICK){
			if (state.getReceivingTeam() == state.getHomeTeam())
				home = true;
			else if (state.getReceivingTeam() == state.getAwayTeam())
				home = false;
		} else if (state.getGameStage() == GameStage.PERFECT_DEFENSE){
			if (state.getKickingTeam() == state.getHomeTeam())
				home = true;
			else if (state.getKickingTeam() == state.getAwayTeam())
				home = false;
		}
		return home;
	}

	public void performAIAction(Action action) {

		if (action == null)
			return;
		
		// Extract players
		Player playerA = null;
		Player playerB = null;
		
		if (action instanceof PlayerAction){
			playerA = identifyPlayer(((PlayerAction) action).getPlayer());
		} else if (action instanceof DoublePlayerAction){
			playerA = identifyPlayer(((DoublePlayerAction) action).getPlayerA());
			playerB = identifyPlayer(((DoublePlayerAction) action).getPlayerB());
		}
		
		if(action instanceof RerollAction){
			
			RerollUpdater.getInstance().update(state, action, rulebook);
			
		} else if(action instanceof SelectDieAction){
			
			SelectDieUpdater.getInstance().update(state, action, rulebook);
			
		} else if(action instanceof PlacePlayerAction){
			
			//TODO: HER!
			placePlayerIfAllowed(playerA, ((PlacePlayerAction)action).getSquare());
			
		} else if(action instanceof SelectPlayerAction){
			
			if (state.getGameStage() == GameStage.HIGH_KICK)
				EndPhaseUpdater.getInstance().update(state, action, rulebook);
			
		} else if(action instanceof SelectPlayerTurnAction){
			
			SelectActionUpdater.getInstance().update(state, action, rulebook);
			selectAction(((SelectPlayerTurnAction) action).getTurn());
			
		} else if(action instanceof PlaceBallOnPlayerAction){
			
			Square square = playerA.getPosition();
			state.getPitch().getBall().setSquare(square);
			state.getPitch().getBall().setOnGround(true);
			state.getPitch().getBall().setUnderControl(true);
			endPhase();
			
		} else if(action instanceof MovePlayerAction){
			
			movePlayerIfAllowed(playerA, ((MovePlayerAction) action).getSquare());
			
		} else if(action instanceof StandPlayerUpAction){
			
			standPlayerUpIfAllowed(playerA);
			
		} else if(action instanceof BlockPlayerAction){
			
			blockTarget = playerB;
			performBlock(playerA, playerB);
			
		} else if(action instanceof PassPlayerAction){
			
			passTarget = playerB;
			performPass(playerA, playerB);
			
		} else if(action instanceof HandOffPlayerAction){
			
			handOffTarget = playerB;
			performHandOff(playerA, playerB);
			
		} else if(action instanceof FoulPlayerAction){

			foulTarget = playerB;
			performFoul(playerA, playerB);
			
		} else if(action instanceof FollowUpAction){

			followUp(((FollowUpAction) action).isFollowUp());
			
		} else if(action instanceof SelectPushSquareAction){
			
			Square from = state.getCurrentBlock().getCurrentPush().getFrom();
			
			pushToSquare(from, ((SelectPushSquareAction) action).getSquare());
			
		} else if(action instanceof EndPlayerTurnAction){
			
			playerA.getPlayerStatus().setTurn(PlayerTurn.USED);
			
		} else if(action instanceof EndPhaseAction){
			
			endPhase();
			
		} else if(action instanceof SelectInterceptionAction){
			
			continueInterception(playerA);
			
		} else if(action instanceof SelectCoinSideAction){
			
			pickCoinSide(((SelectCoinSideAction)action).isHeads());
			
		} else if(action instanceof SelectCoinTossEffectAction){
			
			pickCoinTossEffect(((SelectCoinTossEffectAction)action).isReceive());
			
		} else if(action instanceof EndSetupAction){
			
			endSetup();
			
		} else if(action instanceof PlaceBallAction){
			
			placeBall(((PlaceBallAction)action).getSquare());
			
			endPhase();
			
		}
		
	}

	private Player identifyPlayer(Player player) {
		
		String teamId = player.getTeamId();
		Team team = null;
		
		if (teamId.equals(state.getHomeTeam().getId()))
			team = state.getHomeTeam();
		else if (teamId.equals(state.getAwayTeam().getId()))
			team = state.getAwayTeam();
		
		if (team == null)
			return null;
		
		return team.getPlayer(player.getNumber());
	}

}