package game.process;

import sound.Sound;
import game.GameLog;
import game.rulebooks.RuleBook;
import ai.actions.Action;
import ai.actions.IllegalActionException;
import ai.actions.MovePlayerAction;
import ai.actions.SelectCoinSideAction;
import ai.actions.SelectCoinTossEffectAction;
import models.GameStage;
import models.GameState;
import models.Player;
import models.Skill;
import models.Square;
import models.Team;
import models.actions.Dodge;
import models.dice.D6;
import models.dice.DiceRoll;
import models.dice.IDice;

public class KickBallProcess extends GameProcess {
	
	private static KickBallProcess instance;
	
	public static KickBallProcess getInstance(){
		if (instance == null)
			instance = new KickBallProcess();
		return instance;
	}

	@Override
	public void run(GameState state, Action action, RuleBook rulebook) throws IllegalActionException {
		
		if (state.getGameStage() != GameStage.KICK_OFF)
			return;
		
		// Ball not placed?
		if (state.getPitch().getBall().getSquare() == null)
			throw new IllegalActionException("No square assigned to ball!");
		
		// Ball corectly placed?
		if (!state.getPitch().ballCorreclyPlaced(state.getKickingTeam()))
			throw new IllegalActionException("Ball incorrectly placed!");
		
		KickScatterProcess.getInstance().run(state, action, rulebook);
		
		if (state.getGameStage() == GameStage.KICK_OFF)
			EndPhaseProcess.getInstance().run(state, action, rulebook);
		
	}
	
}
