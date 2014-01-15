package game.updaters.kickoff;

import sound.Sound;
import game.GameLog;
import game.rulebooks.RuleBook;
import game.updaters.CatchUpdater;
import game.updaters.EndHalfUpdater;
import game.updaters.GameUpdater;
import game.updaters.ScatterBallUpdater;
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

public class RiotUpdater extends GameUpdater {
	
	private static RiotUpdater instance;
	
	public static RiotUpdater getInstance(){
		if (instance == null)
			instance = new RiotUpdater();
		return instance;
	}

	@Override
	/**
	 * 	The trash talk between two opposing players 
	 *	explodes and rapidly degenerates, involving the rest 
	 *	of the players. Roll a D6. On a 1-3, the referee lets the 
	 *	clock run on during the fight; both teams’ turn markers 
	 *	are moved  forward  along the turn track a number of 
	 *	spaces equal to the D6 roll. If this takes the number of 
	 *	turns to 8 or more for both teams, then the half ends. 
	 *	On a roll of 4-6 the referee resets the clock back to 
	 *	before the fight started, so both teams turn markers 
	 *	are moved one space back along the track. The turn 
	 *	marker may not be moved back before turn 1; if this 
	 *	would happen do not move the Turn marker in either 
	 * 	direction. 
	 */
	public void update(GameState state, Action action, RuleBook rulebook) {
	
		D6 d = new D6();
		d.roll();
		if (d.getResultAsInt() <= 3){
			
			// End half?
			if (state.getHomeTurn() + d.getResultAsInt() >= 8 && 
					state.getAwayTurn() + d.getResultAsInt() >= 8){
				
				EndHalfUpdater.getInstance().update(state, action, rulebook);
				
			} else {
				
				// Move turn markers forward
				state.setHomeTurn(state.getHomeTurn() + d.getResultAsInt());
				state.setAwayTurn(state.getAwayTurn() + d.getResultAsInt());
				
			}
			
		} else {
			
			// Move turn makers one backwards
			if (state.getHomeTurn() != 0)
				state.setHomeTurn(Math.max(1, state.getHomeTurn() - 1));
			
			if (state.getAwayTurn() != 0)
				state.setAwayTurn(Math.max(1, state.getAwayTurn() - 1));
			
		}
		
	}
}
