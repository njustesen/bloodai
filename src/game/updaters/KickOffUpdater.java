package game.updaters;

import sound.Sound;
import game.GameLog;
import game.rulebooks.RuleBook;
import game.updaters.kickoffevents.BlitzUpdater;
import game.updaters.kickoffevents.BrilliantCoachingUpdater;
import game.updaters.kickoffevents.ChangingWeatherUpdater;
import game.updaters.kickoffevents.CheeringFansUpdater;
import game.updaters.kickoffevents.GetTheRefUpdater;
import game.updaters.kickoffevents.HighKickUpdater;
import game.updaters.kickoffevents.PerfectDefenseUpdater;
import game.updaters.kickoffevents.PitchInvasionUpdater;
import game.updaters.kickoffevents.QuickSnapUpdater;
import game.updaters.kickoffevents.RiotUpdater;
import game.updaters.kickoffevents.ThrowARockUpdater;
import ai.actions.Action;
import models.GameStage;
import models.GameState;
import models.Square;
import models.Team;
import models.dice.D6;
import models.dice.IDice;

public class KickOffUpdater extends GameUpdater {
	
	private static KickOffUpdater instance;
	
	public static KickOffUpdater getInstance(){
		if (instance == null)
			instance = new KickOffUpdater();
		return instance;
	}

	@Override
	public void update(GameState state, Action action, RuleBook rulebook) {
	
		D6 da = new D6();
		D6 db = new D6();
		da.roll(); 
		db.roll();
		int roll = da.getResultAsInt() + db.getResultAsInt();
		
		// DEBUGGING
		//blitz();
		//throwARock();
		//highKick();
		//perfectDefense();
		//quickSnap();
		
		switch(roll){
			case 2: GetTheRefUpdater.getInstance().update(state, action, rulebook); break;
			case 3: RiotUpdater.getInstance().update(state, action, rulebook);; break;
			case 4: PerfectDefenseUpdater.getInstance().update(state, action, rulebook);; break;
			case 5: HighKickUpdater.getInstance().update(state, action, rulebook);; break;
			case 6: CheeringFansUpdater.getInstance().update(state, action, rulebook); break;
			case 7: ChangingWeatherUpdater.getInstance().update(state, action, rulebook); break;
			case 8: BrilliantCoachingUpdater.getInstance().update(state, action, rulebook); break;
			case 9: QuickSnapUpdater.getInstance().update(state, action, rulebook); break;
			case 10: BlitzUpdater.getInstance().update(state, action, rulebook); break;
			case 11: ThrowARockUpdater.getInstance().update(state, action, rulebook); break;
			case 12: PitchInvasionUpdater.getInstance().update(state, action, rulebook); break;
		}
		
	}
	
}
