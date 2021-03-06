package game.process.kickoff;

import sound.Sound;
import game.GameLog;
import game.process.CatchProcess;
import game.process.GameProcess;
import game.process.KickScatterProcess;
import game.process.ScatterBallProcess;
import game.rulebooks.RuleBook;
import ai.actions.Action;
import ai.actions.IllegalActionException;
import models.GameStage;
import models.GameState;
import models.Player;
import models.Square;
import models.Standing;
import models.Team;
import models.dice.D6;
import models.dice.D8;
import models.dice.IDice;

public class HighKickProcess extends GameProcess {
	
	private static HighKickProcess instance;
	
	public static HighKickProcess getInstance(){
		if (instance == null)
			instance = new HighKickProcess();
		return instance;
	}

	@Override
	public void run(GameState state, Action action, RuleBook rulebook) throws IllegalActionException {
	
		state.setGameStage(GameStage.HIGH_KICK);
		
		KickScatterProcess.getInstance().run(state, action, rulebook);
		
	}
}
