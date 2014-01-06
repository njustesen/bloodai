package game.updaters;

import java.util.ArrayList;

import sound.Sound;
import game.GameLog;
import game.rulebooks.RuleBook;
import ai.actions.Action;
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

public class CatchUpdater extends GameUpdater {
	
	private static CatchUpdater instance;
	
	public static CatchUpdater getInstance(){
		if (instance == null)
			instance = new CatchUpdater();
		return instance;
	}

	@Override
	public void update(GameState state, Action action, RuleBook rulebook) {
		
		Square square = state.getPitch().getBall().getSquare();
		
		if (square == null)
			return;
		
		Player player = state.getPitch().getPlayerAt(square);
		
		if (player == null || square == null){	
			return;
		}
		
		int zones = state.numberOfTackleZones(player);
		int success = 6 - player.getAG() + zones + 1;
		if (state.getCurrentPass() != null && state.getCurrentPass().isAccurate()){
			success -= 1;
		} else if (state.getCurrentHandOff() != null){
			success -= 1;
		}
		if (state.getWeather() == Weather.POURING_RAIN){
			success++;
		}
		success = Math.max( 2, Math.min(6, success) );
		
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
		
		if (result == 6 || 
				(result != 1 && result >= success)){
			
			state.getPitch().getBall().setUnderControl(true);
			
			// Touchdown
			if (state.getPitch().isBallInEndzone(state.oppositeTeam(state.owner(player))))
				touchdown(state, state.owner(player));
			
		} else { 
			
			if (player.getSkills().contains(Skill.CATCH)){
		
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
					
					return;
					
				}
				
			} else if (state.isAbleToReroll(state.owner(player))) {
				
				state.setCurrentCatch(new Catch(player, square, success));
				state.setAwaitReroll(true);
				return;
				
			}
			
			// FAIL
			boolean endTurn = false;
			if (state.getCurrentPass() != null || state.getCurrentHandOff() != null){
				endTurn = true;
				state.setCurrentPass(null);
				state.setCurrentHandOff(null);
			}
			
			ScatterBallUpdater.getInstance().update(state, action, rulebook);
			
			if (endTurn)
				EndTurnUpdater.getInstance().update(state, action, rulebook);
			
		}
	}
	
}
