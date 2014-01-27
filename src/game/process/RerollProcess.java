package game.process;

import sound.Sound;
import game.rulebooks.RuleBook;
import ai.actions.Action;
import ai.actions.IllegalActionException;
import models.GameStage;
import models.GameState;
import models.Team;
import models.dice.IDice;

public class RerollProcess extends GameProcess {
	
	private static RerollProcess instance;
	
	public static RerollProcess getInstance(){
		if (instance == null)
			instance = new RerollProcess();
		return instance;
	}

	@Override
	public void run(GameState state, Action action, RuleBook rulebook) throws IllegalActionException {
		
		Team movingTeam = state.getMovingTeam();
		
		// Can reroll
		if (!state.isAwaitingReroll() || 
				state.getCurrentDiceRoll() == null || 
				!state.isAbleToReroll(movingTeam))
			throw new IllegalActionException("Reroll not allowed!");
		
		movingTeam.useReroll();
		state.setAwaitReroll(false);

		// Dodge/going/block/pass
	 	if (state.getCurrentDodge() != null){
	 		
	 		DodgeProcess.getInstance().run(state, action, rulebook);
			
		} else if (state.getCurrentGoingForIt() != null){
			
			GoingForItProcess.getInstance().run(state, action, rulebook);
			
		} else if (state.getCurrentBlock() != null){

			BlockProcess.getInstance().run(state, action, rulebook);
			
		} else if (state.getCurrentPickUp() != null){

			PickupProcess.getInstance().run(state, action, rulebook);
			
		} else if (state.getCurrentCatch() != null){

			CatchProcess.getInstance().run(state, action, rulebook);
			
		} else if (state.getCurrentPass() != null){

			PassProcess.getInstance().run(state, action, rulebook);
			
		}
	}
	
}
