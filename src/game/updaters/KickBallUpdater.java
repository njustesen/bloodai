package game.updaters;

import sound.Sound;
import game.GameLog;
import game.rulebooks.RuleBook;
import ai.actions.Action;
import ai.actions.MovePlayerAction;
import ai.actions.SelectCoinSideAction;
import ai.actions.SelectCoinTossEffectAction;
import models.GameStage;
import models.GameState;
import models.Player;
import models.Skill;
import models.Square;
import models.Team;
import models.actions.Dodge;
import models.dice.D6;
import models.dice.DiceRoll;
import models.dice.IDice;

public class KickBallUpdater extends GameUpdater {
	
	private static KickBallUpdater instance;
	
	public static KickBallUpdater getInstance(){
		if (instance == null)
			instance = new KickBallUpdater();
		return instance;
	}

	@Override
	public void update(GameState state, Action action, RuleBook rulebook) {
		
		if (state.getGameStage() != GameStage.KICK_PLACEMENT)
			return;
		
		// Ball not placed?
		if (state.getPitch().getBall().getSquare() == null)
			return;
		
		// Ball corectly placed?
		if (!state.getPitch().ballCorreclyPlaced(state.getKickingTeam()))
			return;
		
		//state.setGameStage(GameStage.KICK_OFF);
		
		KickOffUpdater.getInstance().update(state, action, rulebook);
		
		// If special kick off phase started return
		if (state.getGameStage() != GameStage.KICK_OFF)
			return;
		
		EndPhaseUpdater.getInstance().update(state, action, rulebook);
		
	}
	
}
