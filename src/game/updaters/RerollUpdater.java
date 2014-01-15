package game.updaters;

import sound.Sound;
import game.rulebooks.RuleBook;
import ai.actions.Action;
import ai.actions.IllegalActionException;
import models.GameStage;
import models.GameState;
import models.Team;
import models.dice.IDice;

public class RerollUpdater extends GameUpdater {
	
	private static RerollUpdater instance;
	
	public static RerollUpdater getInstance(){
		if (instance == null)
			instance = new RerollUpdater();
		return instance;
	}

	@Override
	public void update(GameState state, Action action, RuleBook rulebook) throws IllegalActionException {
		
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
	 		
	 		DodgeUpdater.getInstance().update(state, action, rulebook);
			
		} else if (state.getCurrentGoingForIt() != null){
			
			GoingForItUpdater.getInstance().update(state, action, rulebook);
			
		} else if (state.getCurrentBlock() != null){

			BlockUpdater.getInstance().update(state, action, rulebook);
			
		} else if (state.getCurrentPickUp() != null){

			PickupUpdater.getInstance().update(state, action, rulebook);
			
		} else if (state.getCurrentCatch() != null){

			CatchUpdater.getInstance().update(state, action, rulebook);
			
		} else if (state.getCurrentPass() != null){

			PassUpdater.getInstance().update(state, action, rulebook);
			
		}
	}
	
}
