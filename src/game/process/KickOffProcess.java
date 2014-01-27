package game.process;

import sound.Sound;
import game.GameLog;
import game.process.kickoff.BlitzUpdater;
import game.process.kickoff.BrilliantCoachingUpdater;
import game.process.kickoff.ChangingWeatherUpdater;
import game.process.kickoff.CheeringFansUpdater;
import game.process.kickoff.GetTheRefUpdater;
import game.process.kickoff.HighKickUpdater;
import game.process.kickoff.PerfectDefenseUpdater;
import game.process.kickoff.PitchInvasionUpdater;
import game.process.kickoff.QuickSnapUpdater;
import game.process.kickoff.RiotUpdater;
import game.process.kickoff.ThrowARockUpdater;
import game.rulebooks.RuleBook;
import ai.actions.Action;
import ai.actions.IllegalActionException;
import models.GameStage;
import models.GameState;
import models.Square;
import models.Team;
import models.dice.D6;
import models.dice.IDice;

public class KickOffProcess extends GameProcess {
	
	private static KickOffProcess instance;
	
	public static KickOffProcess getInstance(){
		if (instance == null)
			instance = new KickOffProcess();
		return instance;
	}

	@Override
	public void run(GameState state, Action action, RuleBook rulebook) throws IllegalActionException {
	
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
			case 2: GetTheRefUpdater.getInstance().run(state, action, rulebook); break;
			case 3: RiotUpdater.getInstance().run(state, action, rulebook);; break;
			case 4: PerfectDefenseUpdater.getInstance().run(state, action, rulebook);; break;
			case 5: HighKickUpdater.getInstance().run(state, action, rulebook);; break;
			case 6: CheeringFansUpdater.getInstance().run(state, action, rulebook); break;
			case 7: ChangingWeatherUpdater.getInstance().run(state, action, rulebook); break;
			case 8: BrilliantCoachingUpdater.getInstance().run(state, action, rulebook); break;
			case 9: QuickSnapUpdater.getInstance().run(state, action, rulebook); break;
			case 10: BlitzUpdater.getInstance().run(state, action, rulebook); break;
			case 11: ThrowARockUpdater.getInstance().run(state, action, rulebook); break;
			case 12: PitchInvasionUpdater.getInstance().run(state, action, rulebook); break;
			default: throw new IllegalActionException("Magic dice?!");
		}
		
	}
	
}
