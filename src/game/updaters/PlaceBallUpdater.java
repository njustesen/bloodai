package game.updaters;

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

public class PlaceBallUpdater extends GameUpdater {
	
	private static PlaceBallUpdater instance;
	
	public static PlaceBallUpdater getInstance(){
		if (instance == null)
			instance = new PlaceBallUpdater();
		return instance;
	}

	@Override
	public void update(GameState state, Action action, RuleBook rulebook) throws IllegalActionException {
		
		Square square = ((PlaceBallAction)action).getSquare();
		state.getPitch().getBall().setSquare(square);
		
		EndPhaseUpdater.getInstance().update(state, action, rulebook);
		
	}
	
}
