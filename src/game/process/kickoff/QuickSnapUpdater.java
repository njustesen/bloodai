package game.process.kickoff;

import sound.Sound;
import game.GameLog;
import game.process.CatchProcess;
import game.process.GameProcess;
import game.process.ScatterBallProcess;
import game.rulebooks.RuleBook;
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

public class QuickSnapUpdater extends GameProcess {
	
	private static QuickSnapUpdater instance;
	
	public static QuickSnapUpdater getInstance(){
		if (instance == null)
			instance = new QuickSnapUpdater();
		return instance;
	}

	@Override
	public void run(GameState state, Action action, RuleBook rulebook) {
	
		state.setGameStage(GameStage.QUICK_SNAP);
		
	}
}
