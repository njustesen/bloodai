package game.process;

import game.rulebooks.RuleBook;
import ai.actions.Action;
import ai.actions.IllegalActionException;
import ai.actions.PlaceBallAction;
import models.GameState;
import models.Square;

public class PlaceBallProcess extends GameProcess {
	
	private static PlaceBallProcess instance;
	
	public static PlaceBallProcess getInstance(){
		if (instance == null)
			instance = new PlaceBallProcess();
		return instance;
	}

	@Override
	public void run(GameState state, Action action, RuleBook rulebook) throws IllegalActionException {
		
		if (state.getPitch().ballCorreclyPlaced(state.getKickingTeam())){
			
			Square square = ((PlaceBallAction)action).getSquare();
			state.getPitch().getBall().setSquare(square);
		
			EndPhaseProcess.getInstance().run(state, action, rulebook);
		
		} else {
			throw new IllegalActionException("Illegal ball position!");
		}
	}
	
}
