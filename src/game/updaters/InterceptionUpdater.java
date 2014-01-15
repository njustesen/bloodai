package game.updaters;

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

public class InterceptionUpdater extends GameUpdater {
	
	private static InterceptionUpdater instance;
	
	public static InterceptionUpdater getInstance(){
		if (instance == null)
			instance = new InterceptionUpdater();
		return instance;
	}

	@Override
	public void update(GameState state, Action action, RuleBook rulebook) throws IllegalActionException {
		
		Player player = extractPlayer(state, action, 0);
		
		if (!state.getCurrentPass().getInterceptionPlayers().contains(player))
			throw new IllegalActionException("Selected player is not among the allowed intercepters!");
		
		DiceRoll roll = new DiceRoll();
		D6 d = new D6();
		d.roll();
		roll.addDice(d);
		state.setCurrentDiceRoll(roll);
		int result = d.getResultAsInt();
		
		int zones = state.numberOfTackleZones(player);
		int success = 7 - player.getAG() + zones + 2;
		success = Math.max( 2, Math.min(6, success) );
		
		if (result >= success){
			
			state.getPitch().getBall().setSquare(player.getPosition());
			state.getPitch().getBall().setUnderControl(true);
			state.setCurrentPass(null);
			EndTurnUpdater.getInstance().update(state, action, rulebook);
			
		} else {
			
			state.getCurrentPass().setAwaitingInterception(false);
			state.getCurrentPass().setInterceptionPlayers(null);
			
			PassUpdater.getInstance().update(state, action, rulebook);
			
		}
		
	}
	
}
