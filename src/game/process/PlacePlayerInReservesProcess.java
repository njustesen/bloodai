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

public class PlacePlayerInReservesProcess extends GameProcess {
	
	private static PlacePlayerInReservesProcess instance;
	
	public static PlacePlayerInReservesProcess getInstance(){
		if (instance == null)
			instance = new PlacePlayerInReservesProcess();
		return instance;
	}

	@Override
	public void run(GameState state, Action action, RuleBook rulebook) {
		
		Player player = extractPlayer(state, action, 0);
		
		Team team = state.owner(player);
		boolean moveAllowed = false;
		
		// Setting up?
		if (state.getGameStage() == GameStage.KICKING_SETUP &&
				state.getKickingTeam() == team){
			
			moveAllowed = true;
			
		} else if (state.getGameStage() == GameStage.RECEIVING_SETUP &&
				state.getReceivingTeam() == team){
			
			moveAllowed = true;
			
		}
		
		if (state.getPitch().getDogout(team).getReserves().contains(player))
			moveAllowed = false;
		
		if (moveAllowed){
			state.removePlayerFromCurrentSquare(player);
			state.getPitch().getDogout(team).getReserves().add(player);
		}
		
	}
	
}
