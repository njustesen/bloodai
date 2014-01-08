package game.rulebooks;

import models.GameState;
import models.Player;
import models.Square;

public interface RuleBook {
	
	String getVersion();

	void rollForFans(GameState state);

	void rollForWeather(GameState state);

	int dodgeSuccess(GameState state, Player player, Square square);

	int calculateFoulSum(GameState state, Player fouler, Player target);

	
}
