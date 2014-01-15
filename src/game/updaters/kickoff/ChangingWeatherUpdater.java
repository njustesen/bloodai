package game.updaters.kickoff;

import sound.Sound;
import game.GameLog;
import game.rulebooks.RuleBook;
import game.updaters.CatchUpdater;
import game.updaters.GameUpdater;
import game.updaters.ScatterBallUpdater;
import ai.actions.Action;
import models.GameStage;
import models.GameState;
import models.Player;
import models.Square;
import models.Standing;
import models.Team;
import models.Weather;
import models.dice.D6;
import models.dice.D8;
import models.dice.IDice;

public class ChangingWeatherUpdater extends GameUpdater {
	
	private static ChangingWeatherUpdater instance;
	
	public static ChangingWeatherUpdater getInstance(){
		if (instance == null)
			instance = new ChangingWeatherUpdater();
		return instance;
	}

	@Override
	public void update(GameState state, Action action, RuleBook rulebook) {
	
		rulebook.rollForWeather(state);
		
		// Gentle gust
		if (state.getWeather() == Weather.NICE)
			state.setGust(true);
		
	}
}
