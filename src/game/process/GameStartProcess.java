package game.process;

import game.rulebooks.RuleBook;

import ai.actions.Action;
import ai.actions.IllegalActionException;

import models.GameStage;
import models.GameState;

public class GameStartProcess extends GameProcess {
	
	private static GameStartProcess instance;
	
	public static GameStartProcess getInstance(){
		if (instance == null)
			instance = new GameStartProcess();
		return instance;
	}

	@Override
	public void run(GameState state, Action action, RuleBook rulebook) throws IllegalActionException {
		
		// Legal action?
		if (state.getGameStage() == GameStage.START_UP){
			
			// Roll for fans and FAME
			rulebook.rollForFans(state);
			
			// Roll for weather
			rulebook.rollForWeather(state);
			
			// Go to coin toss
			state.setGameStage(GameStage.COIN_TOSS);
			
			// Move all players to reserve
			state.getPitch().getHomeDogout().putPlayersInReserves();
			state.getPitch().getAwayDogout().putPlayersInReserves();
			
		} else {
			throw new IllegalActionException("Game has already started!");
		}
		
	}

}
