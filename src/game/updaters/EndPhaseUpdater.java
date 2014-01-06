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
			case KICKING_SETUP : endSetup(); break;
			case RECEIVING_SETUP : endSetup(); break;
			case HOME_TURN : endTurn(); break;
			case AWAY_TURN : endTurn(); break;
			case KICK_PLACEMENT : kickBall(); break;
			case BLITZ : endTurn(); break;
			case QUICK_SNAP : endTurn(); break;
			case HIGH_KICK : endTurn(); break;
			case PERFECT_DEFENSE : endTurn(); break;
			case PLACE_BALL_ON_PLAYER : endTurn(); break;
			default:
			return;
		}
		
	}
	
}
