package game.updaters;

import java.util.ArrayList;

import javax.swing.text.html.HTMLDocument.HTMLReader.BlockAction;

import sound.Sound;
import game.GameLog;
import game.rulebooks.RuleBook;
import ai.actions.Action;
import ai.actions.IllegalActionException;
import ai.actions.MovePlayerAction;
import ai.actions.SelectDieAction;
import ai.actions.SelectPushSquareAction;
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

public class PushUpdater extends GameUpdater {
	
	private static PushUpdater instance;
	
	public static PushUpdater getInstance(){
		if (instance == null)
			instance = new PushUpdater();
		return instance;
	}

	@Override
	public void update(GameState state, Action action, RuleBook rulebook) throws IllegalActionException {
		
		if (state.getCurrentBlock() == null)
			throw new IllegalActionException("No current block!");
		
		Square from = state.getCurrentBlock().getCurrentPush().getFrom();
		Square to = ((SelectPushSquareAction) action).getSquare();
		
		if (state.getCurrentBlock().getCurrentPush().isAmongSquares(to)){
			
			Player player = state.getPitch().getPlayerAt(to);
			
			if (player != null && !state.getCurrentBlock().isAmongPlayers(player)){
				
				Push push = new Push(player, from, to);
				
				push.setPushSquares(eliminatedPushSquares(state, push));
				
				state.getCurrentBlock().getCurrentPush().setFollowingPush(push);
				
			} else {
				
				performPush(state, to);
				state.setAwaitPush(false);
				
			}
			
		}
		
		if (state.getCurrentBlock().getCurrentPush() == null){
			endPush(state);
			return;
		}
		
	}
	
	protected void endPush(GameState state) {
		
		state.setAwaitPush(false);
		
		state.setAwaitFollowUp(true);
		
	}
	
	protected void performPush(GameState state, Square to) throws IllegalActionException {
		
		boolean scatterBall = false;
		boolean throwIn = false;
		
		while(state.getCurrentBlock().getCurrentPush() != null){
			
			Player player = state.getCurrentBlock().getCurrentPush().getPushedPlayer();
			
			// Move ball - TD?
			if (state.isBallCarried(player)){
				
				// Move ball
				state.getPitch().getBall().setSquare(to);
				
				if (state.getPitch().isBallInEndzone(state.oppositeTeam(state.owner(player)))){
					touchdown(state, state.owner(player));
					return;
				}
			
			}
			
			Square before = player.getPosition();
			state.removePlayerFromCurrentSquare(player);
			state.placePlayerOnSquare(player, to);
			
			// Scatter ball?
			if (state.getPitch().getBall().getSquare() != null &&
					state.getPitch().getBall().getSquare().getX() == to.getX() && 
					state.getPitch().getBall().getSquare().getY() == to.getY() && 
					!state.getPitch().getBall().isUnderControl() && 
					state.getPitch().getBall().isOnGround()){
				
				scatterBall = true;
				
			}
			
			// Out of bounds?
			if (!state.getPitch().isOnPitch(to)){
				
				if (state.isBallCarried(player)){
					state.getPitch().getBall().setUnderControl(false);
					throwIn = true;
				}
				
				knockDown(state, player, false);
				
				if (player.getPlayerStatus().getStanding() == Standing.STUNNED || player.getPlayerStatus().getStanding() == Standing.DOWN){
					player.getPlayerStatus().setStanding(Standing.UP);
					state.removePlayerFromCurrentSquare(player);
					state.movePlayerToReserves(player, (state.owner(player) == state.getHomeTeam()));
				}
				
			}
			
			to = before;
			state.getCurrentBlock().removeCurrentPush();
			
		}
		
		if (throwIn){
			ThrowInUpdater.getInstance().update(state, null, null);
			return;
		}
		
		if (scatterBall)
			ScatterBallUpdater.getInstance().update(state, null, null);
		
	}
	
}
