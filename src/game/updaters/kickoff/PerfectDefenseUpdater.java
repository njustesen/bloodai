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

public class PerfectDefenseUpdater extends GameUpdater {
	
	private static PerfectDefenseUpdater instance;
	
	public static PerfectDefenseUpdater getInstance(){
		if (instance == null)
			instance = new PerfectDefenseUpdater();
		return instance;
	}

	@Override
	public void update(GameState state, Action action, RuleBook rulebook) {
	
		state.setGameStage(GameStage.PERFECT_DEFENSE);
		
	}
}
