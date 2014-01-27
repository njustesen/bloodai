package game.process;

import sound.Sound;
import game.rulebooks.RuleBook;
import ai.actions.Action;
import ai.actions.IllegalActionException;
import models.GameStage;
import models.GameState;
import models.Team;
import models.dice.IDice;

public class EndKickOffProcess extends GameProcess {
	
	private static EndKickOffProcess instance;
	
	public static EndKickOffProcess getInstance(){
		if (instance == null)
			instance = new EndKickOffProcess();
		return instance;
	}

	@Override
	public void run(GameState state, Action action, RuleBook rulebook) throws IllegalActionException {
		
		if (state.getGameStage() == GameStage.PERFECT_DEFENSE && 
				!state.getPitch().isSetupLegal(state.getKickingTeam(), state.getHalf()))
			throw new IllegalActionException("Illegal setup!");
		
		KickScatterProcess.getInstance().run(state, action, rulebook);
		
		if (state.getGameStage() != GameStage.PLACE_BALL_ON_PLAYER)
			NewTurnProcess.getInstance().run(state, action, rulebook);
		
	}
	
}
