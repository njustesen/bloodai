package game.process;

import sound.Sound;
import game.GameLog;
import game.rulebooks.RuleBook;
import ai.actions.Action;
import ai.actions.IllegalActionException;
import ai.actions.MovePlayerAction;
import models.GameStage;
import models.GameState;
import models.Player;
import models.Skill;
import models.Square;
import models.Team;
import models.actions.Dodge;
import models.dice.D6;
import models.dice.DiceRoll;
import models.dice.IDice;

public class DodgeProcess extends GameProcess {
	
	private static DodgeProcess instance;
	
	public static DodgeProcess getInstance(){
		if (instance == null)
			instance = new DodgeProcess();
		return instance;
	}

	@Override
	public void run(GameState state, Action action, RuleBook rulebook) throws IllegalActionException {
		
		Player player = null;
		Square square = null;
		int success = Integer.MAX_VALUE;
		if (state.getCurrentDodge() == null){
			player = extractPlayer(state, action, 0);
			square = ((MovePlayerAction)action).getSquare();
			success = state.getCurrentDodge().getSuccess(); 
			if (success == Integer.MAX_VALUE)
				throw new IllegalActionException("No success roll assigned in current dodge!");
		} else {
			player = state.getCurrentDodge().getPlayer();
			square = state.getCurrentDodge().getSquare();
			success = rulebook.dodgeSuccess(state, player, square);
		}
		
		if (player == null)
			throw new IllegalActionException("No player assigned!");
		
		if (square == null)
			throw new IllegalActionException("No square assigned!");
		
		DiceRoll r = new DiceRoll();
		D6 d = new D6();
		d.roll();
		int result = d.getResultAsInt();
		state.setCurrentDiceRoll(r);
		
		if (result == 6 || (result != 1 && result >= success)){

			// Move
			movePlayer(state, player, square, false);
			
		} else {
			
			// Dodge skill
			if (player.getSkills().contains(Skill.DODGE)){
				
				DiceRoll dr = new DiceRoll();
				D6 dd = new D6();
				dd.roll();
				result = dd.getResultAsInt();
				state.setCurrentDiceRoll(dr);
				
				if (result == 6 || (result != 1 && result >= success)){
					
					// Move
					movePlayer(state, player, square, false);
					return;
				
				}
				
			} else if (state.isAbleToReroll((state.owner(player)))){
				
				// Prepare for reroll usage
				state.setCurrentDodge(new Dodge(player, square, success));
				state.setAwaitReroll(true);
				return;
				
			}
			
			// Player fall
			movePlayer(state, player, square, true);
			
		}
		
	}
	
}
