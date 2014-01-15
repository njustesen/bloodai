package game.updaters;

import sound.Sound;
import game.GameLog;
import game.rulebooks.RuleBook;
import ai.actions.Action;
import ai.actions.IllegalActionException;
import ai.actions.SelectDieAction;
import models.GameStage;
import models.GameState;
import models.Player;
import models.Skill;
import models.Square;
import models.Team;
import models.Weather;
import models.actions.Dodge;
import models.actions.PickUp;
import models.dice.D6;
import models.dice.DiceRoll;
import models.dice.IDice;

public class PickupUpdater extends GameUpdater {
	
	private static PickupUpdater instance;
	
	public static PickupUpdater getInstance(){
		if (instance == null)
			instance = new PickupUpdater();
		return instance;
	}

	@Override
	public void update(GameState state, Action action, RuleBook rulebook) throws IllegalActionException {
		
		Square square = state.getPitch().getBall().getSquare();
		
		Player player = state.getPitch().getPlayerAt(square);
		
		if (player == null)
			throw new IllegalActionException("Player not assigned!");
		
		if ( square == null)
			throw new IllegalActionException("Square not assigned!");
		
		int zones = state.numberOfTackleZones(player);
		int success = 6 - player.getAG() + zones;
		success = Math.max( 2, Math.min(6, success) );
		if (state.getWeather() == Weather.POURING_RAIN)
			success++;
		
		// Dice pick?
		DiceRoll roll = new DiceRoll();
		if (action != null && action instanceof SelectDieAction){
			IDice d = state.getCurrentDiceRoll().getDices().get(((SelectDieAction) action).getDie());
			roll.addDice(d);
		} else {
			roll = new DiceRoll();
			D6 d = new D6();
			d.roll();
			roll.addDice(d);
		}
		state.setCurrentDiceRoll(roll);
		int result = roll.getDices().get(0).getResultAsInt();
		
		if (result == 6 || (result != 1 && result >= success)){
			
			state.getPitch().getBall().setUnderControl(true);
			
			// Touchdown
			if (state.getPitch().isBallInEndzone(state.oppositeTeam(state.owner(player))))
				touchdown(state, state.owner(player));
			
		} else { 
			
			if (player.getSkills().contains(Skill.SURE_HANDS)){
			
				// Roll
				DiceRoll sroll = new DiceRoll();
				D6 sd = new D6();
				sroll.addDice(sd);
				sd.roll();
				state.setCurrentDiceRoll(sroll);
				result = sd.getResultAsInt();
				
				if (result == 6 || (result != 1 && result >= success)){
					
					state.getPitch().getBall().setUnderControl(true);
					
					// Touchdown
					if (state.getPitch().isBallInEndzone(state.oppositeTeam(state.owner(player))))
						touchdown(state, state.owner(player));
					
				}
				
			} else if (state.isAbleToReroll(state.owner(player))) {
				
				state.setCurrentPickUp(new PickUp(player, square, success));
				state.setAwaitReroll(true);
				return;
				
			}
				
			ScatterBallUpdater.getInstance().update(state, action, rulebook);
			
			EndTurnUpdater.getInstance().update(state, action, rulebook);
			
		}
		
	}
	
}
