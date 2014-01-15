package game.updaters;

import java.util.ArrayList;

import sound.Sound;
import game.GameLog;
import game.rulebooks.RuleBook;
import ai.actions.Action;
import ai.actions.IllegalActionException;
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
	public void update(GameState state, Action action, RuleBook rulebook) throws IllegalActionException {
		
		if (state.isAwaitingReroll())
			throw new IllegalActionException("Game is awaiting a reroll decision!");
		
		Player player = extractPlayer(state, action, 0);
		Square square = ((MovePlayerAction)action).getSquare();
		boolean moveAllowed = moveAllowed(state, action, rulebook);
		
		if (!moveAllowed)
			throw new IllegalActionException("Move not allowed!");
		
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
	
	private boolean moveAllowed(GameState state, Action action, RuleBook rulebook) throws IllegalActionException {
		
		Player player = extractPlayer(state, action, 0);
		Square square = ((MovePlayerAction)action).getSquare();
		
		// Block?
		if (state.isAwaitingPush() || state.isAwaitingFollowUp())
			throw new IllegalActionException("Game is awaiting a follow up decision!");
		
		// Legal square
		if (!state.nextToEachOther(player, square))
			throw new IllegalActionException("Player is not next to the selected square!");
		
		// Square on pitch
		if (!state.getPitch().isOnPitch(square))
			throw new IllegalActionException("Player is not on the pitch!");
		
		// Square occupied?
		if (state.getPitch().getPlayerAt(square) != null)
			throw new IllegalActionException("The selected square is occupied!");
		
		// Turn
		if (!isPlayerTurn(state, player))
			throw new IllegalActionException("Not players turn!");
		
		// Player turn
		if (player.getPlayerStatus().getTurn() == PlayerTurn.USED)
			throw new IllegalActionException("The selected player is already used!");
		
		// Enough movement left?
		if (playerMovementLeft(state, player))
			throw new IllegalActionException("Player has no movement left!");
		
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
