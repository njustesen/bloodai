package game.updaters;

import sound.Sound;
import game.GameLog;
import game.rulebooks.RuleBook;
import ai.actions.Action;
import ai.actions.SelectPlayerTurnAction;
import models.GameStage;
import models.GameState;
import models.Player;
import models.PlayerTurn;
import models.Skill;
import models.Square;
import models.Standing;
import models.Team;
import models.actions.Dodge;
import models.dice.D6;
import models.dice.DiceRoll;
import models.dice.IDice;

public class SelectActionUpdater extends GameUpdater {
	
	private static SelectActionUpdater instance;
	
	public static SelectActionUpdater getInstance(){
		if (instance == null)
			instance = new SelectActionUpdater();
		return instance;
	}

	@Override
	public void update(GameState state, Action action, RuleBook rulebook) {
		
		Player player = ((SelectPlayerTurnAction) action).getPlayer();
		PlayerTurn playerTurn = ((SelectPlayerTurnAction) action).getTurn();
		
		// Only if allowed
		if ((state.owner(player) == state.getMovingTeam() || 
				(state.getGameStage() == GameStage.QUICK_SNAP && 
				state.owner(player) == state.getReceivingTeam())) 
				&&
				player.getPlayerStatus().getTurn() == PlayerTurn.UNUSED){
			
			if (playerTurn == PlayerTurn.USED || 
					playerTurn == PlayerTurn.UNUSED || 
					player.getPlayerStatus().getStanding() == Standing.STUNNED){
				return;
			}
			
			if (playerTurn == PlayerTurn.BLITZ_ACTION && 
					state.owner(player).getTeamStatus().hasBlitzed()){
				return;
			}
			
			if (playerTurn == PlayerTurn.FOUL_ACTION && 
					state.owner(player).getTeamStatus().hasFouled()){
				return;
			}
			
			if (playerTurn == PlayerTurn.PASS_ACTION && 
					state.owner(player).getTeamStatus().hasPassed()){
				return;
			}
			
			if (playerTurn == PlayerTurn.HAND_OFF_ACTION && 
					state.owner(player).getTeamStatus().hasHandedOf()){
				return;
			}
			
			endTurnForOtherPlayers(state, state.owner(player), player);
			
			player.getPlayerStatus().setTurn(playerTurn);
				
		}
		
	}
	
}
