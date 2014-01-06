package game.updaters;

import sound.Sound;
import game.GameLog;
import game.rulebooks.RuleBook;
import ai.actions.Action;
import models.GameStage;
import models.GameState;
import models.Square;
import models.Team;
import models.dice.IDice;

public class EndTurnUpdater extends GameUpdater {
	
	private static EndTurnUpdater instance;
	
	public static EndTurnUpdater getInstance(){
		if (instance == null)
			instance = new EndTurnUpdater();
		return instance;
	}

	@Override
	public void update(GameState state, Action action, RuleBook rulebook) {
	
		if (state.isAwaitingFollowUp() || state.isAwaitingPush() || state.isAwaitingReroll()){
			if (state.getGameStage() != GameStage.PLACE_BALL_ON_PLAYER)
				return;
		}
		
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
				NewTurnUpdater.getInstance().update(state, action, rulebook);
			else
				EndHalfUpdater.getInstance().update(state, action, rulebook);
			
		} else if (state.getGameStage() == GameStage.AWAY_TURN){
			
			if (state.getHomeTurn() < 8)
				NewTurnUpdater.getInstance().update(state, action, rulebook);
			else 
				EndHalfUpdater.getInstance().update(state, action, rulebook);
				
		} else if (state.getGameStage() == GameStage.BLITZ || 
				state.getGameStage() == GameStage.QUICK_SNAP || 
				state.getGameStage() == GameStage.PERFECT_DEFENSE){
			
			EndKickOffUpdater.getInstance().update(state, action, rulebook);
		
		} else if (state.getGameStage() == GameStage.PLACE_BALL_ON_PLAYER){
			
			if (state.getPitch().getBall().isUnderControl()) {
				NewTurnUpdater.getInstance().update(state, action, rulebook);
			}
		
		} else if (state.getGameStage() == GameStage.HIGH_KICK){
			
			Square ballOn = state.getPitch().getBall().getSquare();
			
			if (state.getPitch().getPlayerAt(ballOn) != null || selectedPlayer == null){
				EndKickOffUpdater.getInstance().update(state, action, rulebook);
				return;
			}
			
			if (state.owner(selectedPlayer) == state.getReceivingTeam() && 
					state.getPitch().isOnPitch(selectedPlayer) && 
					!inTacklesZoneExcept(selectedPlayer, selectedPlayer)){
				
				// Place player under ball
				placePlayerUnderBall(selectedPlayer);
				
				state.getPitch().getBall().setOnGround(true);
				catchBall();
				
				//endKickOffPhas0e();
				state.setGameStage(GameStage.KICK_OFF);
				//startNewTurn();
				
			}
		} else if (state.getGameStage() == GameStage.KICK_OFF){
	
			EndKickOffUpdater.getInstance().update(state, action, rulebook);
			
		}
		
	}
	
}
