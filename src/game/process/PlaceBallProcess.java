package game.process;

import sound.Sound;
import game.GameLog;
import game.rulebooks.RuleBook;
import ai.actions.Action;
import ai.actions.IllegalActionException;
import ai.actions.MovePlayerAction;
import ai.actions.PlaceBallAction;
import ai.actions.SelectCoinSideAction;
import ai.actions.SelectCoinTossEffectAction;
import ai.actions.SelectPushSquareAction;
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

public class PlaceBallProcess extends GameProcess {
	
	private static PlaceBallProcess instance;
	
	public static PlaceBallProcess getInstance(){
		if (instance == null)
			instance = new PlaceBallProcess();
		return instance;
	}

	@Override
	public void run(GameState state, Action action, RuleBook rulebook) throws IllegalActionException {
		
		Square square = ((PlaceBallAction)action).getSquare();
		state.getPitch().getBall().setSquare(square);
		
		EndPhaseProcess.getInstance().run(state, action, rulebook);
		
	}
	
}
