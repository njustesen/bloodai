package game.updaters;

import sound.Sound;
import game.GameLog;
import game.rulebooks.RuleBook;
import ai.actions.Action;
import ai.actions.IllegalActionException;
import ai.actions.MovePlayerAction;
import models.GameStage;
import models.GameState;
import models.Player;
import models.PlayerTurn;
import models.Skill;
import models.Square;
import models.Team;
import models.actions.Dodge;
import models.dice.D6;
import models.dice.DiceRoll;
import models.dice.IDice;

public class EndPlayerTurnUpdater extends GameUpdater {
	
	private static EndPlayerTurnUpdater instance;
	
	public static EndPlayerTurnUpdater getInstance(){
		if (instance == null)
			instance = new EndPlayerTurnUpdater();
		return instance;
	}

	@Override
	public void update(GameState state, Action action, RuleBook rulebook) throws IllegalActionException {
		
		if(state.getCurrentBlock() != null)
			throw new IllegalActionException("Game has an ongoing block and cannot end players turn!");
		
		if(state.getCurrentPass() != null)
			throw new IllegalActionException("Game has an ongoing pass and cannot end players turn!");
		
		if(state.getCurrentDodge() != null)
			throw new IllegalActionException("Game has an ongoing dodge and cannot end players turn!");
		
		if(state.getCurrentPickUp() != null)
			throw new IllegalActionException("Game has an ongoing pickup and cannot end players turn!");
		
		if(state.getCurrentCatch() != null)
			throw new IllegalActionException("Game has an ongoing catch and cannot end players turn!");
		
		if(state.getCurrentFoul() != null)
			throw new IllegalActionException("Game has an ongoing foul and cannot end players turn!");
		
		if(state.getCurrentGoingForIt() != null)
			throw new IllegalActionException("Game has an ongoing going for it and cannot end players turn!");
		
		if(state.getCurrentHandOff() != null)
			throw new IllegalActionException("Game has an ongoing hand off and cannot end players turn!");
		
		Player player = extractPlayer(state, action, 0);
		player.getPlayerStatus().setTurn(PlayerTurn.USED);
		
	}
	
}
