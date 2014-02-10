package game.process.kickoff;

import sound.Sound;
import game.GameLog;
import game.process.CatchProcess;
import game.process.GameProcess;
import game.process.ScatterBallProcess;
import game.rulebooks.RuleBook;
import ai.actions.Action;
import models.GameStage;
import models.GameState;
import models.Player;
import models.Square;
import models.Standing;
import models.Team;
import models.dice.D6;
import models.dice.D8;
import models.dice.IDice;

public class PitchInvasionProcess extends GameProcess {
	
	private static PitchInvasionProcess instance;
	
	public static PitchInvasionProcess getInstance(){
		if (instance == null)
			instance = new PitchInvasionProcess();
		return instance;
	}

	@Override
	/**
	 * Pitch Invasion:  Both coaches roll a D6 for each 
	 * opposing player on the pitch and add their FAME 
	 * (see page 18) to the roll. If a roll is 6 or more after 
	 * modification then the player is Stunned (players with 
	 * the Ball & Chain skill are KO'd). A roll of 1 before 
	 * adding FAME will always have no effect.
	 */
	public void run(GameState state, Action action, RuleBook rulebook) {
	
		invadeTeam(state, state.getHomeTeam());
		
		invadeTeam(state, state.getAwayTeam());
		
	}

	private void invadeTeam(GameState state, Team team) {
		
		for(Player p : team.getPlayers()){
			
			if (state.getPitch().isOnPitch(p)){
				
				D6 d = new D6();
				
				d.roll();
				
				int result = d.getResultAsInt() + 
						state.getHomeTeam().getTeamStatus().getFAME();
				
				if (d.getResultAsInt() == 1){
					
					continue;
					
				} else if (result >= 6){
					
					p.getPlayerStatus().setStanding(Standing.STUNNED);
					
				}
				
			}
			
		}
		
	}
}
