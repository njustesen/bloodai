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

public class BlockUpdater extends GameUpdater {
	
	private static BlockUpdater instance;
	
	public static BlockUpdater getInstance(){
		if (instance == null)
			instance = new BlockUpdater();
		return instance;
	}

	@Override
	public void update(GameState state, Action action, RuleBook rulebook) {
		
		if (state.getCurrentBlock() == null)
			return;
		
		Player attacker = state.getCurrentBlock().getAttacker();
		Player defender = state.getCurrentBlock().getDefender();
		
		if (!allowedToBlock(state, attacker))
			return;
			
		if (!state.onDifferentTeams(attacker, defender))
			return;
		
		if (!state.nextToEachOther(attacker, defender))
			return;
				
		if (attacker.getPlayerStatus().getTurn() == PlayerTurn.UNUSED)
			attacker.getPlayerStatus().setTurn(PlayerTurn.BLOCK_ACTION);
		
		if (attacker.getPlayerStatus().getTurn() == PlayerTurn.BLITZ_ACTION && 
				attacker.getPlayerStatus().getStanding() == Standing.DOWN){
			attacker.getPlayerStatus().setStanding(Standing.UP);
			attacker.getPlayerStatus().useMovement(3);
		}
		
		endTurnForOtherPlayers(state, state.owner(attacker), attacker);
		
		// Blitz?
		if (attacker.getPlayerStatus().getTurn() == PlayerTurn.BLITZ_ACTION){
			if (attacker.getPlayerStatus().getMovementUsed() >= attacker.getMA()){
				if (!attacker.getPlayerStatus().hasMovedToBlock()){
					state.setCurrentBlock(new Block(attacker, defender, null));
					state.setCurrentGoingForIt(new GoingForIt(attacker, attacker.getPosition(), 2));
					GoingForItUpdater.getInstance().update(state, action, rulebook);
					return;
				}
			} else {
				attacker.getPlayerStatus().moveOneSquare();
			}
		}
		
		// Dice pick?
		DiceRoll roll = new DiceRoll();
		Team selectTeam = state.owner(attacker);
		if (action != null && action instanceof SelectDieAction){
			IDice d = state.getCurrentDiceRoll().getDices().get(((SelectDieAction) action).getDie());
			roll.addDice(d);
		} else {
		
			BlockSum sum = state.blockSum(attacker, defender);
			
			if (sum == BlockSum.EQUAL){
				
				BB ba = new BB();
				ba.roll();
				roll.addDice(ba);
				
			} else if(sum == BlockSum.ATTACKER_STRONGER){
				
				BB ba = new BB();
				BB bb = new BB();
				ba.roll();
				bb.roll();
				roll.addDice(ba);
				roll.addDice(bb);
				
			}  else if(sum == BlockSum.DEFENDER_STRONGER){
				
				BB ba = new BB();
				BB bb = new BB();
				ba.roll();
				bb.roll();
				roll.addDice(ba);
				roll.addDice(bb);
				
				selectTeam = state.owner(defender);
				
			} else if(sum == BlockSum.ATTACKER_DOUBLE_STRONG){
				
				BB ba = new BB();
				BB bb = new BB();
				BB bc = new BB();
				ba.roll();
				bb.roll();
				bc.roll();
				roll.addDice(ba);
				roll.addDice(bb);
				roll.addDice(bc);
				
			} else if(sum == BlockSum.DEFENDER_DOUBLE_STRONG){
				
				BB ba = new BB();
				BB bb = new BB();
				BB bc = new BB();
				ba.roll();
				bb.roll();
				bc.roll();
				roll.addDice(ba);
				roll.addDice(bb);
				roll.addDice(bc);
				
			}
		}
		
		state.setCurrentDiceRoll(roll);
		
		// Select or continue
		if (roll.getDices().size() == 1 && !state.isAbleToReroll(selectTeam)){
			state.setCurrentBlock(new Block(attacker, defender, selectTeam));
			continueBlock(state, roll.getFaces().get(0));
		} else {
			state.setCurrentBlock(new Block(attacker, defender, selectTeam));
			state.setAwaitReroll(true);
		}
		
	}
	
	private void continueBlock(GameState state, DiceFace face) {

		state.setAwaitReroll(false);
		
		state.getCurrentBlock().setResult(face);
		
		switch(face){
		case SKULL : attackerDown(state, state.getCurrentBlock()); break;
		case PUSH : defenderPushed(state, state.getCurrentBlock()); break;
		case BOTH_DOWN : bothDown(state, state.getCurrentBlock()); break;
		case DEFENDER_STUMBLES : defenderStumples(state, state.getCurrentBlock()); break;
		case DEFENDER_KNOCKED_DOWN : defenderKnockedDown(state, state.getCurrentBlock()); break;
		default:
			break;
		}
	}

	private void defenderKnockedDown(GameState state, Block block) {
		
		defenderPushed(state, block);
		
	}

	private void defenderStumples(GameState state, Block block) {
		
		defenderPushed(state, block);
		
	}

	private void bothDown(GameState state, Block block) {
		
		if (!block.getDefender().getSkills().contains(Skill.BLOCK)){
			knockDown(state, block.getDefender(), true);
		}
		
		if (!block.getAttacker().getSkills().contains(Skill.BLOCK)){
			knockDown(state, block.getAttacker(), true);
			state.setCurrentBlock(null);
			EndTurnUpdater.getInstance().update(state, null, null);
			return;
		} else {
			if (block.getAttacker().getPlayerStatus().getTurn() == PlayerTurn.BLOCK_ACTION){
				block.getAttacker().getPlayerStatus().setTurn(PlayerTurn.USED);
			}
			state.setCurrentBlock(null);
		}
		
	}

	private void defenderPushed(GameState state, Block block) {
		
		Square from = block.getAttacker().getPosition();
		Square to = block.getDefender().getPosition();
		
		Push push = new Push(block.getDefender(), from, to);
		
		push.setPushSquares( eliminatedPushSquares(state, push) );
		
		state.getCurrentBlock().setPush(push);
		
		state.setAwaitPush(true);
		
	}
	
	private void attackerDown(GameState state, Block block) {
		
		state.setCurrentBlock(null);
		
		knockDown(state, block.getAttacker(), true );
		
		EndTurnUpdater.getInstance().update(state, null, null);
		
	}

	private ArrayList<Square> eliminatedPushSquares(GameState state, Push push) {
		
		ArrayList<Square> squaresOOB = new ArrayList<Square>();
		ArrayList<Square> squaresWithPlayers = new ArrayList<Square>();
		ArrayList<Square> squaresWithoutPlayers = new ArrayList<Square>();
		for (Square sq : push.getPushSquares()){
			if (state.getPitch().isOnPitch(sq)){
				if (state.getPitch().getPlayerAt(sq) == null){
					squaresWithoutPlayers.add(sq);
				} else {
					squaresWithPlayers.add(sq);
				}
			} else {
				squaresOOB.add(sq);
			}
		}
		
		if (squaresWithoutPlayers.size() == 3){
			return squaresWithoutPlayers;
		} else if (squaresWithoutPlayers.size() == 0){
			if (squaresOOB.size() > 0){
				return squaresOOB;
			}
			return squaresWithPlayers;
		}
		
		return squaresWithoutPlayers;
	}

	private boolean allowedToBlock(GameState state, Player player) {
		
		boolean allowed = false;
		
		// Home turn
		if (state.getGameStage() == GameStage.HOME_TURN && 
				state.getHomeTeam() == state.owner(player)){
			
			allowed = true;
			
		}
		
		// Away turn
		if (state.getGameStage() == GameStage.AWAY_TURN && 
				state.getAwayTeam() == state.owner(player)){
			
			allowed = true;
			
		}
		
		// Blitz phase
		if (state.getGameStage() == GameStage.BLITZ && 
				state.getKickingTeam() == state.owner(player)){
			
			allowed = true;
			
		}
		
		if (!allowed)
			return false;
		
		allowed = false;
		
		// Player have had turn?
		if (player.getPlayerStatus().getTurn() == PlayerTurn.USED){
			
			return false;
			
		} else if (player.getPlayerStatus().getTurn() == PlayerTurn.BLITZ_ACTION && 
				!state.owner(player).getTeamStatus().hasBlitzed()){
			
			// Blitz
			if (player.getPlayerStatus().getMovementUsed() < player.getMA() + 2){
				allowed = true;
			}
			
		} else if (player.getPlayerStatus().getTurn() == PlayerTurn.UNUSED){
			
			allowed = true;
			
		}  else if (player.getPlayerStatus().getTurn() == PlayerTurn.BLOCK_ACTION){
			
			allowed = true;
			
		}
		
		// Standing
		if (allowed && player.getPlayerStatus().getStanding() == Standing.UP){
			
			return true;
			
		} else if (allowed && player.getPlayerStatus().getStanding() == Standing.DOWN && 
				player.getPlayerStatus().getTurn() == PlayerTurn.BLITZ_ACTION){
			
			if (player.getMA() > 3){
				
				return true;
			}
			
		}
			
		return false;
		
	}
	
}
