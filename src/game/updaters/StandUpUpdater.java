package game.updaters;

import java.util.ArrayList;

import sound.Sound;
import game.GameLog;
import game.rulebooks.RuleBook;
import ai.actions.Action;
import ai.actions.MovePlayerAction;
import ai.actions.SelectDieAction;
import models.BlockSum;
import models.GameStage;
import models.GameState;
import models.Player;
import models.PlayerTurn;
import models.Skill;
import models.Square;
import models.Standing;
import models.Team;
import models.Weather;
import models.actions.Block;
import models.actions.Catch;
import models.actions.Dodge;
import models.actions.GoingForIt;
import models.actions.Push;
import models.dice.BB;
import models.dice.D6;
import models.dice.DiceFace;
import models.dice.DiceRoll;
import models.dice.IDice;

public class StandUpUpdater extends GameUpdater {
	
	private static StandUpUpdater instance;
	
	public static StandUpUpdater getInstance(){
		if (instance == null)
			instance = new StandUpUpdater();
		return instance;
	}

	@Override
	public void update(GameState state, Action action, RuleBook rulebook) {
		
		if (state.isAwaitingReroll())
			return;
		
		Player player = extractPlayer(state, action, 0);
		
		boolean standUpAllowed = standUpAllowed(state, player);
		
		if (!standUpAllowed){
			return;
		}
		
		// Player turn
		if (player.getPlayerStatus().getTurn() == PlayerTurn.UNUSED){
			endTurnForOtherPlayers(state, state.owner(player), player);
			player.getPlayerStatus().setTurn(PlayerTurn.MOVE_ACTION);
		}
		
		player.getPlayerStatus().setStanding(Standing.UP);
	}
	
	private boolean standUpAllowed(GameState state, Player player) {
		
		// Block?
		if (state.isAwaitingPush() || state.isAwaitingFollowUp()){
			return false;
		}
		
		// Turn
		if (!isPlayerTurn(state, player)){
			return false;
		}
		
		// Player turn
		if (player.getPlayerStatus().getTurn() == PlayerTurn.USED){
			return false;
		}
		
		// Enough movement left?
		if (playerMovementLeft(state, player))
			return true;
		
		return false;
		
	}
	
}
