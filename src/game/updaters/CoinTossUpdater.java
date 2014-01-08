package game.updaters;

import sound.Sound;
import game.GameLog;
import game.rulebooks.RuleBook;
import ai.actions.Action;
import ai.actions.MovePlayerAction;
import ai.actions.SelectCoinSideAction;
import ai.actions.SelectCoinTossEffectAction;
import models.GameStage;
import models.GameState;
import models.Player;
import models.Skill;
import models.Square;
import models.Team;
import models.actions.Dodge;
import models.dice.D6;
import models.dice.DiceRoll;
import models.dice.IDice;

public class CoinTossUpdater extends GameUpdater {
	
	private static CoinTossUpdater instance;
	
	public static CoinTossUpdater getInstance(){
		if (instance == null)
			instance = new CoinTossUpdater();
		return instance;
	}

	@Override
	public void update(GameState state, Action action, RuleBook rulebook) {
		
		// Legal action?
		if (state.getGameStage() == GameStage.COIN_TOSS && action instanceof SelectCoinSideAction){
			
			boolean heads = ((SelectCoinSideAction)action).isHeads();
			
			// Set coin side pick
			state.getCoinToss().setAwayPickedHeads(heads);
			
			// Toss the coin
			state.getCoinToss().Toss();
			
			// Go to pick coin toss effect
			state.setGameStage(GameStage.PICK_COIN_TOSS_EFFECT);
			
		} else if (state.getGameStage() == GameStage.PICK_COIN_TOSS_EFFECT && action instanceof SelectCoinTossEffectAction){
			
			boolean receive = ((SelectCoinTossEffectAction)action).isReceive();
			
			// If away picked correct
			if (state.getCoinToss().hasAwayPickedHeads() == state.getCoinToss().isResultHeads()){
				
				// Away chooses to receive or kick
				state.getCoinToss().setHomeReceives(!receive);
				if (receive){
					state.setReceivingTeam(state.getAwayTeam());
					state.setKickingTeam(state.getHomeTeam());
				} else {
					state.setKickingTeam(state.getAwayTeam());
					state.setReceivingTeam(state.getHomeTeam());
				}
				
			} else {
				
				// Away chooses to receive or kick
				state.getCoinToss().setHomeReceives(receive);
				if (receive){
					state.setReceivingTeam(state.getHomeTeam());
					state.setKickingTeam(state.getAwayTeam());
				} else {
					state.setKickingTeam(state.getHomeTeam());
					state.setReceivingTeam(state.getAwayTeam());
				}
				
			}
			
			state.setGameStage(GameStage.KICKING_SETUP);
			
		}
		
	}
	
}
