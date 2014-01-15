package game.updaters;

import sound.Sound;
import game.GameLog;
import game.rulebooks.RuleBook;
import game.updaters.kickoff.BlitzUpdater;
import game.updaters.kickoff.BrilliantCoachingUpdater;
import game.updaters.kickoff.ChangingWeatherUpdater;
import game.updaters.kickoff.CheeringFansUpdater;
import game.updaters.kickoff.GetTheRefUpdater;
import game.updaters.kickoff.HighKickUpdater;
import game.updaters.kickoff.PerfectDefenseUpdater;
import game.updaters.kickoff.PitchInvasionUpdater;
import game.updaters.kickoff.QuickSnapUpdater;
import game.updaters.kickoff.RiotUpdater;
import game.updaters.kickoff.ThrowARockUpdater;
import ai.actions.Action;
import ai.actions.IllegalActionException;
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
	public void update(GameState state, Action action, RuleBook rulebook) throws IllegalActionException {
	
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
			default: throw new IllegalActionException("Magic dice?!");
		}
		
	}
	
}
