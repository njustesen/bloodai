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

public class EndPhaseUpdater extends GameUpdater {
	
	private static EndPhaseUpdater instance;
	
	public static EndPhaseUpdater getInstance(){
		if (instance == null)
			instance = new EndPhaseUpdater();
		return instance;
	}

	@Override
	public void update(GameState state, Action action, RuleBook rulebook) {
	
		// Check if reroll?
		if (state.isAwaitingReroll())
			return;
		
		switch(state.getGameStage()){
			case KICKING_SETUP : EndSetupUpdater.getInstance().update(state, action, rulebook); break;
			case RECEIVING_SETUP : EndSetupUpdater.getInstance().update(state, action, rulebook); break;
			case HOME_TURN : EndTurnUpdater.getInstance().update(state, action, rulebook); break;
			case AWAY_TURN : EndTurnUpdater.getInstance().update(state, action, rulebook); break;
			case BLITZ : EndTurnUpdater.getInstance().update(state, action, rulebook); break;
			case QUICK_SNAP : EndTurnUpdater.getInstance().update(state, action, rulebook); break;
			case HIGH_KICK : EndTurnUpdater.getInstance().update(state, action, rulebook); break;
			case PERFECT_DEFENSE :EndTurnUpdater.getInstance().update(state, action, rulebook); break;
			case PLACE_BALL_ON_PLAYER : EndTurnUpdater.getInstance().update(state, action, rulebook); break;
			default:
			return;
		}
		
	}
	
}
