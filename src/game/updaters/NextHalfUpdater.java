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

public class NextHalfUpdater extends GameUpdater {
	
	private static NextHalfUpdater instance;
	
	public static NextHalfUpdater getInstance(){
		if (instance == null)
			instance = new NextHalfUpdater();
		return instance;
	}

	@Override
	public void update(GameState state, Action action, RuleBook rulebook) {
	
		// set to next half
		state.setHalf(state.getHalf() + 1);
		state.setHomeTurn(0);
		state.setAwayTurn(0);
		
		// Who kicks?
		if ( state.getCoinToss().isHomeReceives() ){
			
			state.setKickingTeam(state.getHomeTeam());
			state.setReceivingTeam(state.getAwayTeam());
			
		} else {
			
			state.setKickingTeam(state.getAwayTeam());
			state.setReceivingTeam(state.getHomeTeam());
			
		}
		
		SetupForKickOffUpdater.getInstance().update(state,action,rulebook);
	}
}
