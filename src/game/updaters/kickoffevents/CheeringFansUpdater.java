package game.updaters.kickoffevents;

import sound.Sound;
import game.GameLog;
import game.rulebooks.RuleBook;
import game.updaters.CatchUpdater;
import game.updaters.GameUpdater;
import game.updaters.ScatterBallUpdater;
import ai.actions.Action;
import models.GameStage;
import models.GameState;
import models.Player;
import models.Square;
import models.Standing;
import models.Team;
import models.dice.D3;
import models.dice.D6;
import models.dice.D8;
import models.dice.IDice;

public class CheeringFansUpdater extends GameUpdater {
	
	private static CheeringFansUpdater instance;
	
	public static CheeringFansUpdater getInstance(){
		if (instance == null)
			instance = new CheeringFansUpdater();
		return instance;
	}

	@Override
	public void update(GameState state, Action action, RuleBook rulebook) {
	
		D3 home = new D3();
		D3 away = new D3();
		
		home.roll();
		away.roll();
		
		int homeResult = home.getResultAsInt() + 
				state.getHomeTeam().getTeamStatus().getFAME() + 
				state.getHomeTeam().getCheerleaders();
		
		int awayResult = away.getResultAsInt() + 
				state.getAwayTeam().getTeamStatus().getFAME() + 
				state.getAwayTeam().getCheerleaders();
		
		if (homeResult >= awayResult){
			int rr = state.getHomeTeam().getTeamStatus().getRerolls() + 1;
			state.getHomeTeam().getTeamStatus().setRerolls(rr);
		}
		if (awayResult >= homeResult){
			int rr = state.getAwayTeam().getTeamStatus().getRerolls() + 1;
			state.getAwayTeam().getTeamStatus().setRerolls(rr);
		}
		
	}
}
