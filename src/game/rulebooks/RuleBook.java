package game.rulebooks;

import models.GameState;

public interface RuleBook {
	
	String getVersion();

	void rollForFans(GameState state);

	void rollForWeather(GameState state);

	
}
