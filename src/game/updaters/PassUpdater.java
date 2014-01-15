package game.updaters;

import java.util.ArrayList;

import sound.Sound;
import game.GameLog;
import game.rulebooks.RuleBook;
import ai.actions.Action;
import ai.actions.IllegalActionException;
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
import models.actions.Pass;
import models.actions.Push;
import models.dice.BB;
import models.dice.D6;
import models.dice.DiceFace;
import models.dice.DiceRoll;
import models.dice.IDice;

public class PassUpdater extends GameUpdater {
	
	private static PassUpdater instance;
	
	public static PassUpdater getInstance(){
		if (instance == null)
			instance = new PassUpdater();
		return instance;
	}

	@Override
	public void update(GameState state, Action action, RuleBook rulebook) throws IllegalActionException {
		
		Player passer = null;
		Player catcher = null;
		
		if (state.getCurrentPass() == null){
			passer = state.getCurrentPass().getPasser();
			catcher = state.getCurrentPass().getCatcher();
		} else {
			passer = extractPlayer(state, action, 0);
			catcher = extractPlayer(state, action, 1);
		}
	
		if (passer.getPlayerStatus().getTurn() != PlayerTurn.PASS_ACTION)
			throw new IllegalActionException("Passer is not passing!");
			
		if (state.onDifferentTeams(passer, catcher))
			throw new IllegalActionException("Passer and catcher not on same team!");
		
		if (!state.isBallCarried(passer))
			throw new IllegalActionException("Passer does not control the ball!");
		
		if (passingRange(state, passer, catcher) == PassRange.OUT_OF_RANGE)
			throw new IllegalActionException("Catcher is out of range!");
		
		state.owner(passer).getTeamStatus().setHasPassed(true);
		int success = getPassSuccessRoll(state, passer, passingRange(state, passer, catcher));
		state.setCurrentPass(new Pass(passer, catcher, success));
		state.getPitch().getBall().setUnderControl(false);
		
		ArrayList<Player> interceptionPlayers = state.getPitch().interceptionPlayers(state.getCurrentPass());
		
		if (interceptionPlayers.size() != 0){
			state.getCurrentPass().setAwaitingInterception(true);
			state.getCurrentPass().setInterceptionPlayers(interceptionPlayers);
			return;
		}
		
		// Dice pick?
		DiceRoll roll = new DiceRoll();
		if (action != null && action instanceof SelectDieAction){
			IDice d = state.getCurrentDiceRoll().getDices().get(((SelectDieAction) action).getDie());
			roll.addDice(d);
		} else {
		
			roll = new DiceRoll();
			D6 d = new D6();
			d.roll();
			roll.addDice(d);
			
		}
		
		state.setCurrentDiceRoll(roll);
		int result = roll.getDices().get(0).getResultAsInt();
		continuePass(state, result, false);
		
	}
	
	private void continuePass(GameState state, int result, boolean execute) throws IllegalActionException {
		
		state.setAwaitReroll(false);
		
		Player passer = state.getCurrentPass().getPasser();
		Player catcher = state.getCurrentPass().getCatcher();
		int success = state.getCurrentPass().getSuccess();
		
		PassRange range = passingRange(state, passer, catcher);
		String rangeStr = range.getName();
		char[] stringArray = rangeStr.toCharArray();
		stringArray[0] = Character.toUpperCase(stringArray[0]);
		rangeStr = new String(stringArray);
		
		if (result >= success){
			
			state.getPitch().getBall().setSquare(catcher.getPosition());
			state.getCurrentPass().setAccurate(true);
			CatchUpdater.getInstance().update(state, null, null);
			
			return;
			
		} else {
			
			if (passer.getSkills().contains(Skill.PASS)){
				 
				DiceRoll roll = new DiceRoll();
				roll.addDice(new D6());
				state.setCurrentDiceRoll(roll);
				state.getCurrentDiceRoll().getDices().get(0).roll();
				result = state.getCurrentDiceRoll().getDices().get(0).getResultAsInt();
				state.setCurrentPass(new Pass(passer, catcher, success));
				
				if (result >= success){
					
					Square newSquare = catcher.getPosition();
					state.getPitch().getBall().setSquare(newSquare);
					state.getCurrentPass().setAccurate(true);
					CatchUpdater.getInstance().update(state, null, null);
					
					return;
					
				}
				
			} else if (!execute && state.isAbleToReroll(state.owner(passer))){
				
				// Prepare for reroll usage
				state.setCurrentPass(new Pass(passer, catcher, success));
				state.setAwaitReroll(true);
				return;
				
			}
			
		}
		
		// FAIL
		if (result == 1){
			// Fumble
			ScatterBallUpdater.getInstance().update(state, null, null);
			EndTurnUpdater.getInstance().update(state, null, null);
			
		} else {
			state.getCurrentPass().setAccurate(false);
			state.getPitch().getBall().setSquare(catcher.getPosition());
			ScatterPassUpdater.getInstance().update(state, null, null);
			if (state.getPitch().getBall().isUnderControl() && state.getPitch().getBall().getSquare() != null){
				Player ballCarrier = state.getPitch().getPlayerAt(state.getPitch().getBall().getSquare());
				if (state.owner(ballCarrier) != state.owner(passer))
					EndTurnUpdater.getInstance().update(state, null, null);
			} else {
				EndTurnUpdater.getInstance().update(state, null, null);
			}
		}
		
	}
	
	private void scatterPass(GameState state) throws IllegalActionException {
		
		int scatters = 3;
		Square ballOn = state.getPitch().getBall().getSquare();
		if (ballOn == null)
			throw new IllegalActionException("Ball is out of bounds!");
		
		while(scatters > 0){
			int result = (int) (Math.random() * 8 + 1);
			
			switch (result){
			case 1 : ballOn = new Square(ballOn.getX() - 1, ballOn.getY() - 1); break;
			case 2 : ballOn = new Square(ballOn.getX(), ballOn.getY() - 1); break;
			case 3 : ballOn = new Square(ballOn.getX() + 1, ballOn.getY() - 1); break;
			case 4 : ballOn = new Square(ballOn.getX() - 1, ballOn.getY()); break;
			case 5 : ballOn = new Square(ballOn.getX() + 1, ballOn.getY()); break;
			case 6 : ballOn = new Square(ballOn.getX() + 1, ballOn.getY() + 1); break;
			case 7 : ballOn = new Square(ballOn.getX() + 1, ballOn.getY() + 1); break;
			case 8 : ballOn = new Square(ballOn.getX() + 1, ballOn.getY() + 1); break;
			}
			
			state.getPitch().getBall().setSquare(ballOn);
			scatters--;
			
			if (!state.getPitch().isBallInsidePitch()){
				//correctOOBBallPosition(moveX, moveY)
				break;
			}
			
		}
		
		Player player = state.getPitch().getPlayerAt(ballOn);
		
		// Land on player
		if (player != null){
			
			if (player.getPlayerStatus().getStanding() == Standing.UP)
				CatchUpdater.getInstance().update(state, null, null);
			else
				ScatterBallUpdater.getInstance().update(state, null, null);
			
			return;
		}
			
		if (!state.getPitch().isBallInsidePitch()){

			ThrowInUpdater.getInstance().update(state, null, null);
			
		}
		
	}

	private int getPassSuccessRoll(GameState state, Player passer, PassRange passingRange) {
		
		int zones = state.numberOfTackleZones(passer);
		int success = 7 - passer.getAG() + zones;
		success += passingRange.getModifier();
		if (state.getWeather() == Weather.VERY_SUNNY)
			success++;
		
		return Math.max( 2, Math.min(6, success) );
		
	}

	public PassRange passingRange(GameState state, Player passer, Player catcher) {
		
		Square a = passer.getPosition();
		Square b = catcher.getPosition();
		if (a == null || b == null)
			return PassRange.OUT_OF_RANGE;
		
		int x = (a.getX() - b.getX()) * (a.getX() - b.getX());
		int y = (a.getY() - b.getY()) * (a.getY() - b.getY());
		int distance = (int) Math.sqrt(x + y);
		PassRange range = RangeRuler.getPassRange(distance);
		if (state.getWeather() == Weather.BLIZZARD){
			if (range == PassRange.LONG_BOMB || range == PassRange.LONG_PASS){
				return PassRange.OUT_OF_RANGE;
			}
		}
		
		return range;
		
	}
	
}
