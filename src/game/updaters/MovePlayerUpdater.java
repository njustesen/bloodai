package game.updaters;

import java.util.ArrayList;

import sound.Sound;
import game.GameLog;
import game.rulebooks.RuleBook;
import ai.actions.Action;
import ai.actions.MovePlayerAction;
import ai.actions.PlacePlayerAction;
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

public class MovePlayerUpdater extends GameUpdater {
	
	private static MovePlayerUpdater instance;
	
	public static MovePlayerUpdater getInstance(){
		if (instance == null)
			instance = new MovePlayerUpdater();
		return instance;
	}

	@Override
	public void update(GameState state, Action action, RuleBook rulebook) {
		
		if (state.isAwaitingReroll())
			return;
		
		Player player = extractPlayer(state, action, 0);
		Square square = ((MovePlayerAction)action).getSquare();
		boolean moveAllowed = moveAllowed(state, action, rulebook);
		
		if (!moveAllowed){
			return;
		}
		
		// Player turn
		if (player.getPlayerStatus().getTurn() == PlayerTurn.UNUSED){
			endTurnForOtherPlayers(state, state.owner(player), player);
			player.getPlayerStatus().setTurn(PlayerTurn.MOVE_ACTION);
		}
		
		// Dodge
		if (state.isInTackleZone(player) && state.getGameStage() != GameStage.QUICK_SNAP){
			
			DodgeUpdater.getInstance().update(state, action, rulebook);
			
		} else {
			
			// Move
			movePlayer(state, player, square, false);
			
		}
	}
	
	private boolean moveAllowed(GameState state, Action action, RuleBook rulebook) {
		
		Player player = extractPlayer(state, action, 0);
		Square square = ((MovePlayerAction)action).getSquare();
		
		// Block?
		if (state.isAwaitingPush() || state.isAwaitingFollowUp()){
			return false;
		}
		
		// Legal square
		if (!state.nextToEachOther(player, square)){
			return false;
		}
		
		// Square on pitch
		if (!state.getPitch().isOnPitch(square)){
			return false;
		}
		
		// Square occupied?
		if (state.getPitch().getPlayerAt(square) != null){
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
		
		// Able to sprint
		if (state.getGameStage() != GameStage.QUICK_SNAP){
			
			if (player.getPlayerStatus().getMovementUsed() < player.getMA() + 2 && 
					player.getPlayerStatus().getStanding() == Standing.UP){
				
				// Going for it
				GoingForItUpdater.getInstance().update(state, action, rulebook);
				
			}
			
		}
		
		return false;
		
	}
	
}
