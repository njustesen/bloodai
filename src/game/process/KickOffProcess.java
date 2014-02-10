package game.process;

import sound.Sound;
import game.GameLog;
import game.process.kickoff.BlitzProcess;
import game.process.kickoff.BrilliantCoachingProcess;
import game.process.kickoff.ChangingWeatherProcess;
import game.process.kickoff.CheeringFansProcess;
import game.process.kickoff.GetTheRefProcess;
import game.process.kickoff.HighKickProcess;
import game.process.kickoff.PerfectDefenseProcess;
import game.process.kickoff.PitchInvasionProcess;
import game.process.kickoff.QuickSnapProcess;
import game.process.kickoff.RiotProcess;
import game.process.kickoff.ThrowARockProcess;
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
			case 2: GetTheRefProcess.getInstance().run(state, action, rulebook); break;
			case 3: RiotProcess.getInstance().run(state, action, rulebook);; break;
			case 4: PerfectDefenseProcess.getInstance().run(state, action, rulebook);; break;
			case 5: HighKickProcess.getInstance().run(state, action, rulebook);; break;
			case 6: CheeringFansProcess.getInstance().run(state, action, rulebook); break;
			case 7: ChangingWeatherProcess.getInstance().run(state, action, rulebook); break;
			case 8: BrilliantCoachingProcess.getInstance().run(state, action, rulebook); break;
			case 9: QuickSnapProcess.getInstance().run(state, action, rulebook); break;
			case 10: BlitzProcess.getInstance().run(state, action, rulebook); break;
			case 11: ThrowARockProcess.getInstance().run(state, action, rulebook); break;
			case 12: PitchInvasionProcess.getInstance().run(state, action, rulebook); break;
			default: throw new IllegalActionException("Magic dice?!");
		}
		
	}
	
}
