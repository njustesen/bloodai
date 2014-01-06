package game.updaters;

import game.GameLog;
import game.rulebooks.RuleBook;

import java.util.Date;

import ai.actions.Action;

import sound.Sound;

import models.GameStage;
import models.GameState;
import models.Weather;
import models.dice.D6;

public class GameStartUpdater extends GameUpdater {
	
	private static GameStartUpdater instance;
	
	public static GameStartUpdater getInstance(){
		if (instance == null)
			instance = new GameStartUpdater();
		return instance;
	}

	@Override
	public void update(GameState state, Action action, RuleBook rulebook) {
		
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
			
		}
		
	}

}
