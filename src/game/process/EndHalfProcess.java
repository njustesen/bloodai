package game.process;

import sound.Sound;
import game.GameLog;
import game.rulebooks.RuleBook;
import ai.actions.Action;
import models.GameStage;
import models.GameState;
import models.Square;
import models.Team;
import models.dice.IDice;

public class EndHalfProcess extends GameProcess {
	
	private static EndHalfProcess instance;
	
	public static EndHalfProcess getInstance(){
		if (instance == null)
			instance = new EndHalfProcess();
		return instance;
	}

	@Override
	public void run(GameState state, Action action, RuleBook rulebook) {
	
		if (state.getHalf() == 1)
			NextHalfProcess.getInstance().run(state, action, rulebook);
		else
			EndGameProcess.getInstance().run(state, action, rulebook);
		
	}
}
