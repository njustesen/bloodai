package game.updaters;

import sound.Sound;
import game.GameLog;
import game.rulebooks.RuleBook;
import ai.actions.Action;
import models.GameStage;
import models.GameState;
import models.Square;
import models.Team;
import models.dice.IDice;

public class EndHalfUpdater extends GameUpdater {
	
	private static EndHalfUpdater instance;
	
	public static EndHalfUpdater getInstance(){
		if (instance == null)
			instance = new EndHalfUpdater();
		return instance;
	}

	@Override
	public void update(GameState state, Action action, RuleBook rulebook) {
	
		if (state.getHalf() == 1)
			NextHalfUpdater.getInstance().update(state, action, rulebook);
		else
			EndGameUpdater.getInstance().update(state, action, rulebook);
		
	}
}
