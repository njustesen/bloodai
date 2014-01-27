package game.process;

import sound.Sound;
import game.GameLog;
import game.rulebooks.RuleBook;
import ai.actions.Action;
import ai.actions.IllegalActionException;
import models.GameStage;
import models.GameState;
import models.Player;
import models.Skill;
import models.Square;
import models.Team;
import models.Weather;
import models.actions.Dodge;
import models.actions.GoingForIt;
import models.dice.D6;
import models.dice.DiceRoll;
import models.dice.IDice;

public class GoingForItProcess extends GameProcess {
	
	private static GoingForItProcess instance;
	
	public static GoingForItProcess getInstance(){
		if (instance == null)
			instance = new GoingForItProcess();
		return instance;
	}

	@Override
	public void run(GameState state, Action action, RuleBook rulebook) throws IllegalActionException {
		
		if (state.getCurrentGoingForIt() == null)
			return;
		
		DiceRoll roll = new DiceRoll();
		D6 d = new D6();
		roll.addDice(d);
		d.roll();
		state.setCurrentDiceRoll(roll);
		int success = 2;
		if (state.getWeather() == Weather.BLIZZARD)
			success++;
		
		Player player = state.getCurrentGoingForIt().getPlayer();
		Square square = state.getCurrentGoingForIt().getSquare();
		
		if (d.getResultAsInt() >= success){
			
			// Going for it to Blitz?
			if (player.getPosition().equals(square) && 
					state.getCurrentBlock() != null && 
					state.getCurrentBlock().getAttacker() == player){
				player.getPlayerStatus().setMovedToBlock(true);
				
				BlockProcess.getInstance().run(state, action, rulebook);
				return;
			}
			
			if (state.isInTackleZone(player))
				dodgeToMovePlayer(state, player, square);
			else 
				movePlayer(state, player, square, false);
			
		} else {
			
			if (state.isAbleToReroll(state.owner(player))){
				state.setCurrentGoingForIt(new GoingForIt(player, square, success));
				state.setAwaitReroll(true);
			} else {
				movePlayer(state, player, square, true);
			}
			
		}
		
	}
	
}
