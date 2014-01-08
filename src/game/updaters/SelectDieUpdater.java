package game.updaters;

import java.util.ArrayList;

import sound.Sound;
import game.GameLog;
import game.rulebooks.RuleBook;
import ai.actions.Action;
import models.BlockSum;
import models.GameStage;
import models.GameState;
import models.Player;
import models.PlayerTurn;
import models.Skill;
import models.Square;
import models.Standing;
import models.Team;
import models.actions.Block;
import models.actions.Dodge;
import models.actions.GoingForIt;
import models.actions.Push;
import models.dice.BB;
import models.dice.D6;
import models.dice.DiceFace;
import models.dice.DiceRoll;
import models.dice.IDice;

public class SelectDieUpdater extends GameUpdater {
	
	private static SelectDieUpdater instance;
	
	public static SelectDieUpdater getInstance(){
		if (instance == null)
			instance = new SelectDieUpdater();
		return instance;
	}

	@Override
	public void update(GameState state, Action action, RuleBook rulebook) {
		
		if (state.getCurrentDiceRoll() != null){
			
			// Continue block/dodge/going/pass
			if (state.getCurrentBlock() != null && state.getCurrentBlock().getResult() == null){
				
				state.setAwaitReroll(false);
				BlockUpdater.getInstance().update(state, action, rulebook);
				return;
				
			}
			if (state.getCurrentPass() != null){
				
				state.setAwaitReroll(false);
				PassUpdater.getInstance().update(state, action, rulebook);
				return;
				
			}
			if (state.getCurrentPickUp() != null){
					
				state.setAwaitReroll(false);
				PickupUpdater.getInstance().update(state, action, rulebook);
				return;
				
			}
			if (state.getCurrentCatch() != null){
				
				state.setAwaitReroll(false);
				CatchUpdater.getInstance().update(state, action, rulebook);
				return;
				
			}
			if (state.getCurrentDodge() != null){
				
				state.setAwaitReroll(false);
				state.setCurrentDiceRoll(null);
				
				// Player fall
				movePlayer(state, state.getCurrentDodge().getPlayer(), state.getCurrentDodge().getSquare(), true);
				//knockDown(state.getCurrentDodge().getPlayer(), true);
				
				return;
				
			}	
			if (state.getCurrentGoingForIt() != null){
				
				state.setAwaitReroll(false);
				
				// Player fall
				movePlayer(state, state.getCurrentGoingForIt().getPlayer(), state.getCurrentGoingForIt().getSquare(), true);
				//knockDown(state.getCurrentGoingForIt().getPlayer(), true);
				state.setCurrentDiceRoll(null);
				
				return;
				
			}	
			
		}
		
	}
	
}
