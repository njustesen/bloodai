package game.updaters;

import sound.Sound;
import game.GameLog;
import game.rulebooks.RuleBook;
import ai.actions.Action;
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

public class DodgeUpdater extends GameUpdater {
	
	private static DodgeUpdater instance;
	
	public static DodgeUpdater getInstance(){
		if (instance == null)
			instance = new DodgeUpdater();
		return instance;
	}

	@Override
	public void update(GameState state, Action action, RuleBook rulebook) {
		
		Player player = null;
		Square square = null;
		int success = 0;
		if (state.getCurrentDodge() == null){
			player = extractPlayer(state, action, 0);
			square = ((MovePlayerAction)action).getSquare();
			success = state.getCurrentDodge().getSuccess(); 
		} else {
			player = state.getCurrentDodge().getPlayer();
			square = state.getCurrentDodge().getSquare();
			success = rulebook.dodgeSuccess(state, player, square);
		}
		
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
