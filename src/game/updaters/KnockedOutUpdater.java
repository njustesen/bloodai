package game.updaters;

import java.util.ArrayList;

import sound.Sound;
import game.rulebooks.RuleBook;
import ai.actions.Action;
import models.GameStage;
import models.GameState;
import models.Player;
import models.Team;
import models.dice.D6;
import models.dice.IDice;

public class KnockedOutUpdater extends GameUpdater {
	
	private static KnockedOutUpdater instance;
	
	public static KnockedOutUpdater getInstance(){
		if (instance == null)
			instance = new KnockedOutUpdater();
		return instance;
	}

	@Override
	public void update(GameState state, Action action, RuleBook rulebook) {
		
		D6 da = new D6();
		
		// Home team
		ArrayList<Player> ready = new ArrayList<Player>();
		for(Player player : state.getPitch().getHomeDogout().getKnockedOut()){
			
			da.roll();
			
			if (da.getResultAsInt()> 3){
				ready.add(player);
				
			}
			
		}
		
		for(Player player : ready){
			state.getPitch().getHomeDogout().getKnockedOut().remove(player);
			state.getPitch().getHomeDogout().getReserves().add(player);
		}
		
		// Away team
		ready = new ArrayList<Player>();
		for(Player player : state.getPitch().getAwayDogout().getKnockedOut()){
			
			da.roll();
			
			if (da.getResultAsInt() > 3){
				ready.add(player);
			}
			
		}
		
		for(Player player : ready){
			state.getPitch().getAwayDogout().getKnockedOut().remove(player);
			state.getPitch().getAwayDogout().getReserves().add(player);
		}
	}
	
}
