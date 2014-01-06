package game.updaters;

import java.util.ArrayList;

import sound.Sound;
import game.GameLog;
import game.rulebooks.RuleBook;
import ai.actions.Action;
import models.GameStage;
import models.GameState;
import models.Player;
import models.Team;
import models.dice.D6;
import models.dice.IDice;

public class EndSetupUpdater extends GameUpdater {
	
	private static EndSetupUpdater instance;
	
	public static EndSetupUpdater getInstance(){
		if (instance == null)
			instance = new EndSetupUpdater();
		return instance;
	}

	@Override
	public void update(GameState state, Action action, RuleBook rulebook) {
		
		if (state.getGameStage() == GameStage.KICKING_SETUP){
			
			// Auto setup
			/*
			if (state.getPitch().teamPlayersOnPitch(state.getKickingTeam()) == 0){
				
				setupTeam(state.getKickingTeam());
				
				return;
			}*/
			
			// Kicking team	
			if (state.getPitch().isSetupLegal(state.getKickingTeam(), state.getHalf()))
				state.setGameStage(GameStage.RECEIVING_SETUP);
			
		} else if (state.getGameStage() == GameStage.RECEIVING_SETUP){
			
			// Auto setup
			/*
			if (state.getPitch().teamPlayersOnPitch(state.getReceivingTeam()) == 0 && 
					 AUTO_SETUP){
				
				setupTeam(state.getReceivingTeam());
				return;
			}
			*/
			
			// Receiving team	
			if (state.getPitch().isSetupLegal(state.getReceivingTeam(), state.getHalf()))
				state.setGameStage(GameStage.KICK_PLACEMENT);
			
		}
	}
	
}
