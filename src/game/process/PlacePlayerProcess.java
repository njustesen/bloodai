package game.process;

import java.util.ArrayList;

import sound.Sound;
import game.GameLog;
import game.rulebooks.RuleBook;
import ai.actions.Action;
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
import models.actions.Block;
import models.actions.Dodge;
import models.actions.GoingForIt;
import models.actions.Push;
import models.dice.BB;
import models.dice.D6;
import models.dice.DiceFace;
import models.dice.DiceRoll;
import models.dice.IDice;

public class PlacePlayerProcess extends GameProcess {
	
	private static PlacePlayerProcess instance;
	
	public static PlacePlayerProcess getInstance(){
		if (instance == null)
			instance = new PlacePlayerProcess();
		return instance;
	}

	@Override
	public void run(GameState state, Action action, RuleBook rulebook) {
		
		Player player = extractPlayer(state, action, 0);
		
		Team team = state.owner(player);
		Square square = ((PlacePlayerAction)action).getSquare();
		boolean moveAllowed = false;
		
		// Setting up?
		if (state.getGameStage() == GameStage.KICKING_SETUP &&
				state.getKickingTeam() == team){
			
			moveAllowed = true;
			
		} else if (state.getGameStage() == GameStage.RECEIVING_SETUP &&
				state.getReceivingTeam() == team){
			
			moveAllowed = true;
			
		} else if (state.getGameStage() == GameStage.PERFECT_DEFENSE &&
				state.getKickingTeam() == team){
			
			moveAllowed = true;
			
		} else if (state.getGameStage() == GameStage.HIGH_KICK &&
				!state.isPlayerPlaced() && 
				state.getReceivingTeam() == team){
			
			moveAllowed = true;
			
		}
		
		// Square occupied?
		if (state.getPitch().getPlayerAt(square) != null)
			moveAllowed = false;
		
		if (moveAllowed){
			state.removePlayerFromReserves(player);
			state.removePlayerFromCurrentSquare(player);
			state.placePlayerOnSquare(player, square);
			
			if (state.getGameStage() == GameStage.HIGH_KICK)
				state.setPlayerPlaced(true);
		}
		
	}
	
}
