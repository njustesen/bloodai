package game.updaters;

import java.util.ArrayList;

import javax.swing.text.html.HTMLDocument.HTMLReader.BlockAction;

import sound.Sound;
import game.GameLog;
import game.rulebooks.RuleBook;
import ai.actions.Action;
import ai.actions.FollowUpAction;
import ai.actions.IllegalActionException;
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

public class FollowUpUpdater extends PushUpdater {
	
	private static FollowUpUpdater instance;
	
	public static FollowUpUpdater getInstance(){
		if (instance == null)
			instance = new FollowUpUpdater();
		return instance;
	}

	@Override
	public void update(GameState state, Action action, RuleBook rulebook) throws IllegalActionException {
		
		boolean follow = ((FollowUpAction) action).isFollowUp();
		
		if (follow){
			
			// Move ball - TD?
			if (state.isBallCarried(state.getCurrentBlock().getAttacker())){
				
				// Move ball
				state.getPitch().getBall().setSquare(state.getCurrentBlock().getFollowUpSquare());
				
				if (state.getPitch().isBallInEndzone(state.oppositeTeam(state.owner(state.getCurrentBlock().getAttacker())))){
					touchdown(state, state.owner(state.getCurrentBlock().getAttacker()));
					return;
				}
			
			}
			
			state.removePlayerFromCurrentSquare(state.getCurrentBlock().getAttacker());
			state.placePlayerOnSquare(state.getCurrentBlock().getAttacker(), state.getCurrentBlock().getFollowUpSquare());
			
		}
		
		state.setAwaitFollowUp(false);
		
		endPushingBlock(state);
		
	}

	private void endPushingBlock(GameState state) throws IllegalActionException{
		
		if (state.getCurrentBlock().getResult() == DiceFace.DEFENDER_KNOCKED_DOWN){
			
			knockDown(state, state.getCurrentBlock().getDefender(), true);
			
		} else if (state.getCurrentBlock().getResult() == DiceFace.DEFENDER_STUMBLES){
			
			if (!state.getCurrentBlock().getDefender().getSkills().contains(Skill.DODGE)){
				
				knockDown(state, state.getCurrentBlock().getDefender(), true);
				
			}
		}
		
		if (state.getCurrentBlock().getAttacker().getPlayerStatus().getTurn() == PlayerTurn.BLITZ_ACTION){
			
			state.owner(state.getCurrentBlock().getAttacker()).getTeamStatus().setHasBlitzed(true);
			
		} else {
			
			state.getCurrentBlock().getAttacker().getPlayerStatus().setTurn(PlayerTurn.USED);
			
		}
		
		state.setCurrentBlock(null);
		
	}

	private void pushToSquare(GameState state, Square from, Square to) throws IllegalActionException {
		
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
	
	
	
}
