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

public class NextHalfProcess extends GameProcess {
	
	private static NextHalfProcess instance;
	
	public static NextHalfProcess getInstance(){
		if (instance == null)
			instance = new NextHalfProcess();
		return instance;
	}

	@Override
	public void run(GameState state, Action action, RuleBook rulebook) {
	
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
		
		SetupForKickOffProcess.getInstance().run(state,action,rulebook);
	}
}
