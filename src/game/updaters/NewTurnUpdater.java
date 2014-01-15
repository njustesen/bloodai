package game.updaters;

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

public class NewTurnUpdater extends GameUpdater {
	
	private static NewTurnUpdater instance;
	
	public static NewTurnUpdater getInstance(){
		if (instance == null)
			instance = new NewTurnUpdater();
		return instance;
	}

	@Override
	public void update(GameState state, Action action, RuleBook rulebook) throws IllegalActionException {
	
		if (state.getGameStage() == GameStage.KICK_OFF || 
				state.getGameStage() == GameStage.BLITZ || 
				state.getGameStage() == GameStage.QUICK_SNAP || 
				state.getGameStage() == GameStage.PLACE_BALL_ON_PLAYER || 
				state.getGameStage() == GameStage.HIGH_KICK){
			
			if (state.getHomeTeam() == state.getReceivingTeam()){
				
				state.setGameStage(GameStage.HOME_TURN);
				state.incHomeTurn();
				
				resetStatii(state.getAwayTeam(), false);
				resetStatii(state.getHomeTeam(), false);
				fixStunnedPlayers(state.getHomeTeam());
				
			} else {
				
				state.setGameStage(GameStage.AWAY_TURN);
				state.incAwayTurn();
				
				resetStatii(state.getAwayTeam(), false);
				resetStatii(state.getHomeTeam(), false);
				fixStunnedPlayers(state.getAwayTeam());
				
			}
			
			state.setRerollAllowed(true);
			
		} else if (state.getGameStage() == GameStage.HOME_TURN){
			
			state.setGameStage(GameStage.AWAY_TURN);
			state.incAwayTurn();
			
			resetStatii(state.getHomeTeam(), false);
			resetStatii(state.getAwayTeam(), false);
			fixStunnedPlayers(state.getAwayTeam());
			
			state.setRerollAllowed(true);
			
		} else if (state.getGameStage() == GameStage.AWAY_TURN){
				
			state.setGameStage(GameStage.HOME_TURN);
			state.incHomeTurn();
			
			resetStatii(state.getAwayTeam(), false);
			resetStatii(state.getHomeTeam(), false);
			fixStunnedPlayers(state.getHomeTeam());
			
			state.setRerollAllowed(true);
				
		} else {
			throw new IllegalActionException("Illegal game stage!");
		}
	}
}
