package game.process;

import sound.Sound;
import game.GameLog;
import game.rulebooks.RuleBook;
import ai.actions.Action;
import ai.actions.IllegalActionException;
import models.GameStage;
import models.GameState;
import models.Square;
import models.Team;
import models.dice.IDice;

public class EndPhaseProcess extends GameProcess {
	
	private static EndPhaseProcess instance;
	
	public static EndPhaseProcess getInstance(){
		if (instance == null)
			instance = new EndPhaseProcess();
		return instance;
	}

	@Override
	public void run(GameState state, Action action, RuleBook rulebook) throws IllegalActionException {
	
		// Check if reroll?
		if (state.isAwaitingReroll())
			throw new IllegalActionException("Game is awaiting a reroll decision!");
		
		switch(state.getGameStage()){
			case KICKING_SETUP : EndSetupProcess.getInstance().run(state, action, rulebook); break;
			case RECEIVING_SETUP : EndSetupProcess.getInstance().run(state, action, rulebook); break;
			case KICK_PLACEMENT : PlaceBallProcess.getInstance().run(state, action, rulebook); break;
			case HOME_TURN : EndTurnProcess.getInstance().run(state, action, rulebook); break;
			case AWAY_TURN : EndTurnProcess.getInstance().run(state, action, rulebook); break;
			case BLITZ : EndTurnProcess.getInstance().run(state, action, rulebook); break;
			case QUICK_SNAP : EndTurnProcess.getInstance().run(state, action, rulebook); break;
			case HIGH_KICK : EndTurnProcess.getInstance().run(state, action, rulebook); break;
			case PERFECT_DEFENSE :EndTurnProcess.getInstance().run(state, action, rulebook); break;
			case PLACE_BALL_ON_PLAYER : EndTurnProcess.getInstance().run(state, action, rulebook); break;
			default: throw new IllegalActionException("Illegal game stage!");
		}
		
	}
	
}
