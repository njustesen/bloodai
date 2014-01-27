package game.process;

import java.util.ArrayList;
import java.util.List;

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

public class SetupForKickOffProcess extends GameProcess {
	
	private static SetupForKickOffProcess instance;
	
	public static SetupForKickOffProcess getInstance(){
		if (instance == null)
			instance = new SetupForKickOffProcess();
		return instance;
	}

	@Override
	public void run(GameState state, Action action, RuleBook rulebook) {
		
		List<Player> collapsedPlayers = new ArrayList<Player>();
		
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
		KnockedOutProcess.getInstance().run(state, action, rulebook);
		
		state.getPitch().getBall().setOnGround(false);
		state.getPitch().getBall().setSquare(null);
		
		for (Player p : collapsedPlayers)
			p.getPlayerStatus().setStanding(Standing.DOWN);
		
		state.setGameStage(GameStage.KICKING_SETUP);
		
	}
	
}
