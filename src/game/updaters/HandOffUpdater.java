package game.updaters;

import java.util.ArrayList;

import sound.Sound;
import game.GameLog;
import game.rulebooks.RuleBook;
import ai.actions.Action;
import ai.actions.SelectDieAction;
import models.BlockSum;
import models.GameStage;
import models.GameState;
import models.PassRange;
import models.Player;
import models.PlayerTurn;
import models.RangeRuler;
import models.Skill;
import models.Square;
import models.Standing;
import models.Team;
import models.Weather;
import models.actions.Block;
import models.actions.Dodge;
import models.actions.GoingForIt;
import models.actions.HandOff;
import models.actions.Pass;
import models.actions.Push;
import models.dice.BB;
import models.dice.D6;
import models.dice.DiceFace;
import models.dice.DiceRoll;
import models.dice.IDice;

public class HandOffUpdater extends GameUpdater {
	
	private static HandOffUpdater instance;
	
	public static HandOffUpdater getInstance(){
		if (instance == null)
			instance = new HandOffUpdater();
		return instance;
	}

	@Override
	public void update(GameState state, Action action, RuleBook rulebook) {
		
		Player passer = null;
		Player catcher = null;
		
		if (state.getCurrentHandOff() == null){
			passer = state.getCurrentHandOff().getPasser();
			catcher = state.getCurrentHandOff().getCatcher();
		} else {
			passer = extractPlayer(state, action, 0);
			catcher = extractPlayer(state, action, 1);
		}
	
		if (passer.getPlayerStatus().getTurn() != PlayerTurn.HAND_OFF_ACTION)
			return;
			
		if (state.onDifferentTeams(passer, catcher))
			return;
		
		if (!state.isBallCarried(passer))
			return;
		
		if (!state.nextToEachOther(passer, catcher))
			return;
		
		state.owner(passer).getTeamStatus().setHasHandedOf(true);
		
		state.setCurrentHandOff(new HandOff(passer, catcher));
		
		state.getPitch().getBall().setSquare(catcher.getPosition());
		passer.getPlayerStatus().setTurn(PlayerTurn.USED);
		
		CatchUpdater.getInstance().update(state, action, rulebook);
		
	}
	
}
