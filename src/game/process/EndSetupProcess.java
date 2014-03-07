package game.process;

import java.util.ArrayList;

import sound.Sound;
import game.GameLog;
import game.rulebooks.RuleBook;
import ai.actions.Action;
import ai.actions.IllegalActionException;
import models.GameStage;
import models.GameState;
import models.Player;
import models.Team;
import models.dice.D6;
import models.dice.IDice;

public class EndSetupProcess extends GameProcess {
	
	private static EndSetupProcess instance;
	
	public static EndSetupProcess getInstance(){
		if (instance == null)
			instance = new EndSetupProcess();
		return instance;
	}

	@Override
	public void run(GameState state, Action action, RuleBook rulebook) throws IllegalActionException {
		
		if (state.getGameStage() == GameStage.KICKING_SETUP){

			// Kicking team	
			if (state.getPitch().isSetupLegal(state.getKickingTeam(), state.getHalf()))
				state.setGameStage(GameStage.RECEIVING_SETUP);
			else
				throw new IllegalActionException("Illegal setup!");
			
		} else if (state.getGameStage() == GameStage.RECEIVING_SETUP){
			
			// Receiving team	
			if (state.getPitch().isSetupLegal(state.getReceivingTeam(), state.getHalf()))
				state.setGameStage(GameStage.KICK_PLACEMENT);
			else
				throw new IllegalActionException("Illegal setup!");
		} else {
			throw new IllegalActionException("Illegal game stage!");
		}
			
	}
	
}
