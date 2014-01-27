package game.process;

import java.util.ArrayList;

import sound.Sound;
import game.GameLog;
import game.rulebooks.RuleBook;
import ai.actions.Action;
import ai.actions.IllegalActionException;
import ai.actions.SelectDieAction;
import models.BlockSum;
import models.GameStage;
import models.GameState;
import models.Player;
import models.PlayerTurn;
import models.Skill;
import models.Square;
import models.Standing;
import models.Team;
import models.Weather;
import models.actions.Block;
import models.actions.Catch;
import models.actions.Dodge;
import models.actions.GoingForIt;
import models.actions.Push;
import models.dice.BB;
import models.dice.D6;
import models.dice.DiceFace;
import models.dice.DiceRoll;
import models.dice.IDice;

public class PlaceBallOnPlayerProcess extends GameProcess {
	
	private static PlaceBallOnPlayerProcess instance;
	
	public static PlaceBallOnPlayerProcess getInstance(){
		if (instance == null)
			instance = new PlaceBallOnPlayerProcess();
		return instance;
	}

	@Override
	public void run(GameState state, Action action, RuleBook rulebook) throws IllegalActionException {
		
		// TODO: gamestage and correct team?
		Player player = extractPlayer(state, action, 0);
		Square square = player.getPosition();
		state.getPitch().getBall().setSquare(square);
		state.getPitch().getBall().setOnGround(true);
		state.getPitch().getBall().setUnderControl(true);
		EndPhaseProcess.getInstance().run(state, action, rulebook);
		
	}
	
}
