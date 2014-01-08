package game.updaters.kickoffevents;

import java.util.ArrayList;
import java.util.List;

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
import models.dice.D6;
import models.dice.D8;
import models.dice.IDice;

public class ThrowARockUpdater extends GameUpdater {
	
	private static ThrowARockUpdater instance;
	
	public static ThrowARockUpdater getInstance(){
		if (instance == null)
			instance = new ThrowARockUpdater();
		return instance;
	}

	@Override
	/**
	 * Throw a Rock: An enraged fan hurls a large rock at 
	 * one of the players on the opposing team.  Each 
	 * coach rolls a D6 and adds their FAME (see page 
	 * 18) to the roll. The fans of the team that rolls higher 
	 * are the ones that threw the rock. In the case of a tie 
	 * a rock is thrown at each team! Decide randomly 
	 * which player in the other team was hit (only players 
	 * on the pitch are eligible) and roll for the effects of 
	 * the injury straight away. No Armour roll is required.
	 */
	public void update(GameState state, Action action, RuleBook rulebook) {
	
		D6 home = new D6();
		D6 away = new D6();
		
		home.roll();
		away.roll();
		
		int homeResult = home.getResultAsInt() + 
				state.getHomeTeam().getTeamStatus().getFAME();
		
		int awayResult = away.getResultAsInt() + 
				state.getAwayTeam().getTeamStatus().getFAME();
		
		if (homeResult >= awayResult){
			
			// Injure random away player
			throwRockAt(state, state.getAwayTeam());
			
		}
		if (awayResult >= homeResult){
			
			// Injure random home player
			throwRockAt(state, state.getHomeTeam());
			
		}
		
	}
	
	private void throwRockAt(GameState state, Team team) {
		
		List<Player> targets = new ArrayList<Player>();
		
		for(Player p : team.getPlayers()){
			
			if (state.getPitch().isOnPitch(p)){
				targets.add(p);
			}
			
		}
		
		int ran = (int)(Math.random()*targets.size());
		
		Player target = targets.get(ran);
		
		// Knock down player with no armour save
		knockDown(state, target, false);
		
	}
}
