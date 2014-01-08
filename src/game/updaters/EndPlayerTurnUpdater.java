package game.updaters;

import sound.Sound;
import game.GameLog;
import game.rulebooks.RuleBook;
import ai.actions.Action;
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
	public void update(GameState state, Action action, RuleBook rulebook) {
		
		if(state.getCurrentBlock() != null)
			return;
		
		if(state.getCurrentPass() != null)
			return;
		
		if(state.getCurrentDodge() != null)
			return;
		
		if(state.getCurrentPickUp() != null)
			return;
		
		if(state.getCurrentCatch() != null)
			return;
		
		if(state.getCurrentFoul() != null)
			return;
		
		if(state.getCurrentGoingForIt() != null)
			return;
		
		if(state.getCurrentHandOff() != null)
			return;
		
		Player player = extractPlayer(state, action, 0);
		player.getPlayerStatus().setTurn(PlayerTurn.USED);
		
	}
	
}
