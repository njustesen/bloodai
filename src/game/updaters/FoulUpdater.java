package game.updaters;

import java.util.ArrayList;

import javax.swing.text.html.HTMLDocument.HTMLReader.BlockAction;

import sound.Sound;
import game.GameLog;
import game.rulebooks.RuleBook;
import ai.actions.Action;
import ai.actions.MovePlayerAction;
import ai.actions.SelectDieAction;
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

public class FoulUpdater extends GameUpdater {
	
	private static FoulUpdater instance;
	
	public static FoulUpdater getInstance(){
		if (instance == null)
			instance = new FoulUpdater();
		return instance;
	}

	@Override
	public void update(GameState state, Action action, RuleBook rulebook) {
		
		Player fouler = extractPlayer(state, action, 0);
		Player target = extractPlayer(state, action, 1);
		
		if (!state.onDifferentTeams(fouler, target))
			return;
		
		if (!state.nextToEachOther(fouler, target))
			return;
		
		if (target.getPlayerStatus().getStanding() == Standing.UP)
			return;
		
		if (state.owner(fouler).getTeamStatus().hasFouled())
			return;
		
		if (fouler.getPlayerStatus().getTurn() == PlayerTurn.UNUSED)
			fouler.getPlayerStatus().setTurn(PlayerTurn.FOUL_ACTION);
		
		endTurnForOtherPlayers(state, state.owner(fouler), fouler);
		
		state.owner(fouler).getTeamStatus().setHasFouled(true);
		
		int foulSum = rulebook.calculateFoulSum(state, fouler, target);
		
		// Armour roll
		D6 da = new D6();
		D6 db = new D6();
		da.roll();
		db.roll();
		
		int result = da.getResultAsInt() + db.getResultAsInt() + foulSum;
		boolean knockedOut = false;
		boolean deadAndInjured = false;
		boolean sendOffField = false;
		
		if (da.getResultAsInt() == db.getResultAsInt())
			sendOffField = true;
		
		if (result > target.getAV()){
			
			// Injury roll
			da.roll();
			db.roll();
			
			if (da.getResultAsInt() == db.getResultAsInt())
				sendOffField = true;
			
			result = da.getResultAsInt() + db.getResultAsInt();
			
			if (result < 8){
				
				// Stunned
				target.getPlayerStatus().setStanding(Standing.STUNNED);
				
			} else if (result < 10){
				
				// Knocked out
				knockedOut = true;
				
			} else {
				
				// Dead and injured
				deadAndInjured = true;
				
			}
			
		}
		
		if (knockedOut){
			
			target.getPlayerStatus().setStanding(Standing.UP);
			state.getPitch().removePlayer(target);
			state.getPitch().getDogout(state.owner(target)).getKnockedOut().add(target);
			
		} else if (deadAndInjured){
			
			target.getPlayerStatus().setStanding(Standing.UP);
			state.getPitch().removePlayer(target);
			state.getPitch().getDogout(state.owner(target)).getDeadAndInjured().add(target);
			
		}
		
		if (sendOffField){
			
			if (state.owner(fouler) == state.getHomeTeam() && !state.isRefAgainstHomeTeam()){
				return;
			}
			if (state.owner(fouler) == state.getAwayTeam() && !state.isRefAgainstAwayTeam()){
				return;
			}
			
			// Fumble
			boolean fumble = false;
			if (state.isBallCarried(fouler)){
				fumble = true;
			}
			
			state.getPitch().removePlayer(fouler);
			state.getPitch().getDungeoun().add(fouler);
			
			if (fumble){
				state.getPitch().getBall().setUnderControl(false);
				ScatterBallUpdater.getInstance().update(state, action, rulebook);
			}
			
		}
	}
	
}
