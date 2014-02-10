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

public class GetTheRefProcess extends GameProcess {
	
	private static GetTheRefProcess instance;
	
	public static GetTheRefProcess getInstance(){
		if (instance == null)
			instance = new GetTheRefProcess();
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
	public void run(GameState state, Action action, RuleBook rulebook) {
	
		state.setRefAgainstHomeTeam(false);
		state.setRefAgainstAwayTeam(false);

	}
}
