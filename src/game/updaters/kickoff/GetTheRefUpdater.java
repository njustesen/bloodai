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
import models.dice.D6;
import models.dice.D8;
import models.dice.IDice;

public class GetTheRefUpdater extends GameUpdater {
	
	private static GetTheRefUpdater instance;
	
	public static GetTheRefUpdater getInstance(){
		if (instance == null)
			instance = new GetTheRefUpdater();
		return instance;
	}

	@Override
	/**
	 * Get the Ref: The fans exact gruesome revenge on the 
	 * referee for some of the dubious decisions he has 
	 * made, either during this match or in the past. His 
	 * replacement is so intimidated that for the rest of the 
	 * half he will not send players from either team off for
	 */
	public void update(GameState state, Action action, RuleBook rulebook) {
	
		state.setRefAgainstHomeTeam(false);
		state.setRefAgainstAwayTeam(false);

	}
}
