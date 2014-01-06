package game.updaters;

import sound.Sound;
import game.rulebooks.RuleBook;
import ai.actions.Action;
import models.GameStage;
import models.GameState;
import models.Team;
import models.dice.IDice;

public class EndKickOffUpdater extends GameUpdater {
	
	private static EndKickOffUpdater instance;
	
	public static EndKickOffUpdater getInstance(){
		if (instance == null)
			instance = new EndKickOffUpdater();
		return instance;
	}

	@Override
	public void update(GameState state, Action action, RuleBook rulebook) {
		
		if (state.getGameStage() == GameStage.PERFECT_DEFENSE && 
				!state.getPitch().isSetupLegal(state.getKickingTeam(), state.getHalf()))
			return;
		
		KickScatterUpdater.getInstance().update(state, action, rulebook);
		
		if (state.getGameStage() != GameStage.PLACE_BALL_ON_PLAYER)
			NewTurnUpdater.getInstance().update(state, action, rulebook);
		
	}
	
}
