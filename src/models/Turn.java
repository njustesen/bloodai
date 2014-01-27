package models;

public class Turn {

	/**
	 * Returns true if it is home turn to take action and false if not.
	 * @param state
	 * @return
	 */
	public static boolean isHomeTurn(GameState state) {
		
		boolean home = false;
		
		if (state.getGameStage() == GameStage.HOME_TURN){
			home = true;
			if (state.getCurrentPass() != null &&
					state.getCurrentPass().getInterceptionPlayers() != null &&
					state.getCurrentPass().getInterceptionPlayers().size() > 0){
				home = false;
			}
			if (state.isAwaitingReroll() && state.getCurrentDiceRoll() != null && state.getCurrentBlock() != null && state.getCurrentGoingForIt() == null){
				if (!state.isAwaitingFollowUp() && !state.isAwaitingPush())
					home = (state.getHomeTeam() == state.getCurrentBlock().getSelectTeam());
			}
		} else if (state.getGameStage() == GameStage.AWAY_TURN){
			home = false;
			if (state.getCurrentPass() != null &&
					state.getCurrentPass().getInterceptionPlayers() != null &&
					state.getCurrentPass().getInterceptionPlayers().size() > 0){
				home = true;
			}
			if (state.isAwaitingReroll() && state.getCurrentDiceRoll() != null && state.getCurrentBlock() != null && state.getCurrentGoingForIt() == null){
				if (!state.isAwaitingFollowUp() && !state.isAwaitingPush())
					home = (state.getHomeTeam() == state.getCurrentBlock().getSelectTeam());
			}
		} else if (state.getGameStage() == GameStage.COIN_TOSS){
			home = false;
		} else if (state.getGameStage() == GameStage.PICK_COIN_TOSS_EFFECT){
			if (state.getCoinToss().hasAwayPickedHeads() == state.getCoinToss().isResultHeads())
				home = false;
			else 
				home = true;
		} else if (state.getGameStage() == GameStage.KICK_OFF){
			home = false;
		} else if (state.getGameStage() == GameStage.KICKING_SETUP){
			if (state.getKickingTeam() == state.getHomeTeam())
				home = true;
			else if (state.getKickingTeam() == state.getAwayTeam())
				home = false;
			
		} else if (state.getGameStage() == GameStage.RECEIVING_SETUP){
			if (state.getReceivingTeam() == state.getHomeTeam())
				home = true;
			else if (state.getReceivingTeam() == state.getAwayTeam())
				home = false;
		} else if (state.getGameStage() == GameStage.KICK_PLACEMENT){
			if (state.getKickingTeam() == state.getHomeTeam())
				home = true;
			else if (state.getKickingTeam() == state.getAwayTeam())
				home = false;
		} else if (state.getGameStage() == GameStage.PLACE_BALL_ON_PLAYER){
			if (state.getReceivingTeam() == state.getHomeTeam())
				home = true;
			else if (state.getReceivingTeam() == state.getAwayTeam())
				home = false;
		} else if (state.getGameStage() == GameStage.BLITZ){
			if (state.getKickingTeam() == state.getHomeTeam())
				home = true;
			else if (state.getKickingTeam() == state.getAwayTeam())
				home = false;
		} else if (state.getGameStage() == GameStage.QUICK_SNAP){
			if (state.getReceivingTeam() == state.getHomeTeam())
				home = true;
			else if (state.getReceivingTeam() == state.getAwayTeam())
				home = false;
		} else if (state.getGameStage() == GameStage.HIGH_KICK){
			if (state.getReceivingTeam() == state.getHomeTeam())
				home = true;
			else if (state.getReceivingTeam() == state.getAwayTeam())
				home = false;
		} else if (state.getGameStage() == GameStage.PERFECT_DEFENSE){
			if (state.getKickingTeam() == state.getHomeTeam())
				home = true;
			else if (state.getKickingTeam() == state.getAwayTeam())
				home = false;
		}
		return home;
	}
}
