package game.updaters;

import java.util.ArrayList;

import sound.Sound;
import game.rulebooks.RuleBook;
import ai.actions.Action;
import models.GameStage;
import models.GameState;
import models.Player;
import models.Standing;
import models.Team;
import models.Weather;
import models.dice.D6;
import models.dice.IDice;

public class SetupForKickOffUpdater extends GameUpdater {
	
	private static SetupForKickOffUpdater instance;
	
	public static SetupForKickOffUpdater getInstance(){
		if (instance == null)
			instance = new SetupForKickOffUpdater();
		return instance;
	}

	@Override
	public void update(GameState state, Action action, RuleBook rulebook) {
		
ArrayList<Player> collapsedPlayers = new ArrayList<Player>();
		
		if (state.getWeather() == Weather.SWELTERING_HEAT){
			
			for (Player p : state.getAwayTeam().getPlayers()){
				if (state.getPitch().isOnPitch(p)){
					D6 d = new D6();
					d.roll();
					if (d.getResultAsInt() == 1){
						collapsedPlayers.add(p);
					}
				}
			}
			
			for (Player p : state.getHomeTeam().getPlayers()){
				if (state.getPitch().isOnPitch(p)){
					D6 d = new D6();
					d.roll();
					if (d.getResultAsInt() == 1){
						collapsedPlayers.add(p);
					}
				}
			}
			
		}
		
		clearField(state);
		fixStunnedPlayers(state.getHomeTeam());
		resetStatii(state.getAwayTeam(), true);
		resetStatii(state.getHomeTeam(), true);
		standUpAllPlayers(state);
		KnockedOutUpdater.getInstance().update(state, action, rulebook);
		
		state.getPitch().getBall().setOnGround(false);
		state.getPitch().getBall().setSquare(null);
		
		for (Player p : collapsedPlayers){
			p.getPlayerStatus().setStanding(Standing.DOWN);
		}
		
		state.setGameStage(GameStage.KICKING_SETUP);
		
	}
	
}
