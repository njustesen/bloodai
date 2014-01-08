package game.rulebooks;

import game.GameLog;
import models.GameState;
import models.Player;
import models.Square;
import models.Weather;
import models.dice.D6;

public class LVRB6 implements RuleBook {

	private final String version = "6.0";
	
	@Override
	public String getVersion() {
		return version;
	}

	@Override
	public void rollForFans(GameState state) {

		// Fans
		D6 a = new D6();
		D6 b = new D6();
		a.roll();
		b.roll();
		int homeFans = 1000 * (a.getResultAsInt() + b.getResultAsInt() + state.getHomeTeam().getFanFactor());
		a.roll();
		b.roll();
		int awayFans = 1000 * (a.getResultAsInt() + b.getResultAsInt() + state.getAwayTeam().getFanFactor());
		
		state.getHomeTeam().getTeamStatus().setFans(homeFans);
		state.getAwayTeam().getTeamStatus().setFans(awayFans);
		
		// FAME
		if (homeFans <= awayFans)
			state.getHomeTeam().getTeamStatus().setFAME(0);
		
		if (awayFans <= homeFans)
			state.getAwayTeam().getTeamStatus().setFAME(0);
		
		if (homeFans > awayFans)
			state.getHomeTeam().getTeamStatus().setFAME(1);
		
		if (awayFans > homeFans)
			state.getAwayTeam().getTeamStatus().setFAME(1);
		
		if (homeFans > awayFans)
			state.getHomeTeam().getTeamStatus().setFAME(1);
		
		if (awayFans > homeFans)
			state.getAwayTeam().getTeamStatus().setFAME(1);
		
		if (homeFans >= awayFans * 2)
			state.getHomeTeam().getTeamStatus().setFAME(2);
		
		if (awayFans >= homeFans * 2)
			state.getAwayTeam().getTeamStatus().setFAME(2);
		
	}

	@Override
	public void rollForWeather(GameState state) {
		D6 da = new D6();
		D6 db = new D6();
		da.roll(); 
		db.roll();
		int roll = da.getResultAsInt() + db.getResultAsInt();
		switch(roll){
			case 2: state.setWeather(Weather.SWELTERING_HEAT); break;
			case 3: state.setWeather(Weather.VERY_SUNNY); break;
			case 4: state.setWeather(Weather.NICE); break;
			case 5: state.setWeather(Weather.NICE); break;
			case 6: state.setWeather(Weather.NICE); break;
			case 7: state.setWeather(Weather.NICE); break;
			case 8: state.setWeather(Weather.NICE); break;
			case 9: state.setWeather(Weather.NICE); break;
			case 10: state.setWeather(Weather.NICE); break;
			case 11: state.setWeather(Weather.POURING_RAIN); break;
			case 12: state.setWeather(Weather.BLIZZARD); break;
		}
		
		switch(state.getWeather()){
			case SWELTERING_HEAT : GameLog.push("Weather changed to swealtering heat."); break;
			case VERY_SUNNY : GameLog.push("Weather changed to very sunny."); break;
			case NICE : GameLog.push("Weather changed to nice."); break;
			case POURING_RAIN : GameLog.push("Weather changed to pouring rain."); break;
			case BLIZZARD : GameLog.push("Weather changed to blizzard."); break;
		}
	}

	@Override
	public int dodgeSuccess(GameState state, Player player, Square square) {
	
		int roll = 6 - player.getAG() + state.numberOfTackleZones(player);
		
		return Math.max( 2, Math.min(6, roll) );
		
	}

	@Override
	public int calculateFoulSum(GameState state, Player fouler, Player target) {
		
		int attAss = state.assists(fouler, target);
		int defAss = state.assists(target, fouler);
		
		return attAss - defAss;
	}

}
