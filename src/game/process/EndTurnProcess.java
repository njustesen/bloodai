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

public class EndTurnProcess extends GameProcess {
	
	private static EndTurnProcess instance;
	
	public static EndTurnProcess getInstance(){
		if (instance == null)
			instance = new EndTurnProcess();
		return instance;
	}

	@Override
	public void run(GameState state, Action action, RuleBook rulebook) throws IllegalActionException {
	
		if (state.isAwaitingFollowUp() || state.isAwaitingPush() || state.isAwaitingReroll())
			if (state.getGameStage() != GameStage.PLACE_BALL_ON_PLAYER)
				throw new IllegalActionException("Game is awaitin a follow up, push or reroll decision!");
		
		// Clear state for activity
		state.setCurrentBlock(null);
		state.setCurrentDodge(null);
		state.setCurrentPickUp(null);
		state.setCurrentPass(null);
		state.setCurrentCatch(null);
		state.setCurrentHandOff(null);
		state.setCurrentGoingForIt(null);
		state.setAwaitFollowUp(false);
		state.setAwaitPush(false);
		state.setAwaitReroll(false);
		
		// Any turns left?
		if (state.getGameStage() == GameStage.HOME_TURN){
			
			if (state.getAwayTurn() < 8)
				NewTurnProcess.getInstance().run(state, action, rulebook);
			else
				EndHalfProcess.getInstance().run(state, action, rulebook);
			
		} else if (state.getGameStage() == GameStage.AWAY_TURN){
			
			if (state.getHomeTurn() < 8)
				NewTurnProcess.getInstance().run(state, action, rulebook);
			else 
				EndHalfProcess.getInstance().run(state, action, rulebook);
				
		} else if (state.getGameStage() == GameStage.BLITZ || 
				state.getGameStage() == GameStage.QUICK_SNAP || 
				state.getGameStage() == GameStage.PERFECT_DEFENSE){
			
			EndKickOffProcess.getInstance().run(state, action, rulebook);
		
		} else if (state.getGameStage() == GameStage.PLACE_BALL_ON_PLAYER){
			
			if (state.getPitch().getBall().isUnderControl()) {
				NewTurnProcess.getInstance().run(state, action, rulebook);
			}
		
		} else if (state.getGameStage() == GameStage.KICK_OFF){
	
			EndKickOffProcess.getInstance().run(state, action, rulebook);
			
		}
		
	}
	
}
