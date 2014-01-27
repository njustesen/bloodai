package game.process;

import java.util.ArrayList;

import sound.Sound;
import ai.actions.Action;
import ai.actions.DoublePlayerAction;
import ai.actions.IllegalActionException;
import ai.actions.PlayerAction;
import game.GameLog;
import game.rulebooks.RuleBook;
import models.GameStage;
import models.GameState;
import models.Player;
import models.PlayerTurn;
import models.Square;
import models.Standing;
import models.Team;
import models.Weather;
import models.actions.Dodge;
import models.actions.Push;
import models.dice.D6;
import models.dice.DiceRoll;

public abstract class GameProcess {

	public abstract void run(GameState state, Action action, RuleBook rulebook) throws IllegalActionException;
	protected Player extractPlayer(GameState state, Action action, int num){
		
		if (action instanceof PlayerAction && num == 0){
			return identifyPlayer(state, ((PlayerAction) action).getPlayer());
		} else if (action instanceof DoublePlayerAction && num == 1){
			return identifyPlayer(state, ((DoublePlayerAction) action).getPlayerB());
		}
		
		return null;
		
	}
	
	private Player identifyPlayer(GameState state, Player player) {
		
		String teamId = player.getTeamId();
		Team team = null;
		
		if (teamId.equals(state.getHomeTeam().getId()))
			team = state.getHomeTeam();
		else if (teamId.equals(state.getAwayTeam().getId()))
			team = state.getAwayTeam();
		
		if (team == null)
			return null;
		
		return team.getPlayer(player.getNumber());
	}
	
	protected void movePlayer(GameState state, Player player, Square square, boolean falling) throws IllegalActionException {

		// Use movement
		if (player.getPlayerStatus().getStanding() == Standing.DOWN){
			
			player.getPlayerStatus().useMovement(3 + 1);
			player.getPlayerStatus().setStanding(Standing.UP);
			
		} else {
			
			player.getPlayerStatus().useMovement(1);

		}
		
		if (state.isBallCarried(player)){
			
			// Move player
			state.removePlayerFromCurrentSquare(player);
			state.placePlayerOnSquare(player, square);
			state.getPitch().getBall().setSquare(square);
			
			if (state.getPitch().isBallInEndzone(state.oppositeTeam(state.owner(player)))){
				touchdown(state, state.owner(player));
				return;
			}
			
		} else {
			// Move player
			state.removePlayerFromCurrentSquare(player);
			state.placePlayerOnSquare(player, square);
		}
		
		// Pick up ball
		Square ballOn = state.getPitch().getBall().getSquare();
		boolean onBall = false;
		if (ballOn != null && 
				ballOn.getX() == square.getX() && 
				ballOn.getY() == square.getY() && 
				state.getPitch().getBall().isOnGround() && 
				!state.getPitch().getBall().isUnderControl()){
			
			onBall = true;
			
		}
		
		if (falling){
			knockDown(state, player, true);
			if (onBall){
				state.setRerollAllowed(false);
				ScatterBallProcess.getInstance().run(state, null, null);
			}
			EndTurnProcess.getInstance().run(state, null, null);
		} else if (onBall){
			PickupProcess.getInstance().run(state, null, null);
		}
		
	}
	
	protected boolean isPlayerTurn(GameState state, Player player) {
		
		boolean playerTurn = false;
		
		if (state.getGameStage() == GameStage.HOME_TURN && 
				state.getHomeTeam() == state.owner(player)){
			
			playerTurn = true;
			
		} else if (state.getGameStage() == GameStage.AWAY_TURN && 
				state.getAwayTeam() == state.owner(player)){
			
			playerTurn = true;
			
		} else if (state.getGameStage() == GameStage.BLITZ && 
				state.getKickingTeam() == state.owner(player)){
			
			playerTurn = true;
			
		} else if (state.getGameStage() == GameStage.QUICK_SNAP && 
				state.getReceivingTeam() == state.owner(player)){
			
			playerTurn = true;
			
		} else if (state.getGameStage() == GameStage.PERFECT_DEFENSE &&
				state.getKickingTeam() == state.owner(player)){
					
			//playerTurn = true;
			
		}
		
		return playerTurn;
	}
	
	protected ArrayList<Square> eliminatedPushSquares(GameState state, Push push) {
		
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
	
	protected boolean playerMovementLeft(GameState state, Player player) {

		boolean movementLeft = false;
		
		if (player.getPlayerStatus().getStanding() == Standing.UP){
			
			// Quick snap
			if (state.getGameStage() == GameStage.QUICK_SNAP){
				
				if (player.getPlayerStatus().getMovementUsed() == 0){
					
					return true;
					
				} 
				
				return false;
				
			} 

			// Normal turn
			if (player.getPlayerStatus().getMovementUsed() < player.getMA()){
				
				// Normal move
				movementLeft = true;
				
			}
			
		} else if (player.getPlayerStatus().getStanding() == Standing.DOWN && 
				player.getPlayerStatus().getMovementUsed() + 3 < player.getMA()){

			// Stand up and move
			movementLeft = true;
				
		}
		
		return movementLeft;
	}
	
	protected void dodgeToMovePlayer(GameState state, Player player, Square square) {

		int zones = state.numberOfTackleZones(player);
		
		int success = getDodgeSuccesRoll(player, zones);
		
		state.setCurrentDodge(new Dodge(player, square, success));
		
	}

	private int getDodgeSuccesRoll(Player player, int zones) {
		int roll = 6 - player.getAG() + zones;
		
		return Math.max( 2, Math.min(6, roll) );
	}

	protected void knockDown(GameState state, Player player, boolean armourRoll) throws IllegalActionException {
		
		player.getPlayerStatus().setTurn(PlayerTurn.USED);
		
		// Armour roll
		D6 da = new D6();
		D6 db = new D6();
		da.roll();
		db.roll();
		
		int result = da.getResultAsInt() + db.getResultAsInt();
		boolean knockedOut = false;
		boolean deadAndInjured = false;
		
		if (result > player.getAV() || !armourRoll){
			
			// Injury roll
			da.roll();
			db.roll();
			
			result = da.getResultAsInt() + db.getResultAsInt();
			
			if (result < 8){
				
				// Stunned
				player.getPlayerStatus().setStanding(Standing.STUNNED);
				
				// Fumble
				if (state.isBallCarried(player)){
					state.getPitch().getBall().setUnderControl(false);
					state.setRerollAllowed(false);
					ScatterBallProcess.getInstance().run(state, null, null);
				}
				
			} else if (result < 10){
				
				// Knocked out
				knockedOut = true;
				
			} else {
				
				// Dead and injured
				deadAndInjured = true;
				
			}
			
		} else {
		
			player.getPlayerStatus().setStanding(Standing.DOWN);
			
			// Fumble
			if (state.isBallCarried(player)){
				state.getPitch().getBall().setUnderControl(false);
				state.setRerollAllowed(false);
				ScatterBallProcess.getInstance().run(state, null, null);
			}
			
		}
		
		if (knockedOut){
			player.getPlayerStatus().setStanding(Standing.DOWN);
			
			// Fumble
			if (state.isBallCarried(player)){
				state.getPitch().getBall().setUnderControl(false);
				state.setRerollAllowed(false);
				ScatterBallProcess.getInstance().run(state, null, null);
			}
			
			player.getPlayerStatus().setStanding(Standing.UP);
			state.getPitch().removePlayer(player);
			state.getPitch().getDogout(state.owner(player)).getKnockedOut().add(player);
			
		} else if (deadAndInjured){
			player.getPlayerStatus().setStanding(Standing.DOWN);
			
			// Fumble
			if (state.isBallCarried(player)){
				state.getPitch().getBall().setUnderControl(false);
				state.setRerollAllowed(false);
				ScatterBallProcess.getInstance().run(state, null, null);
			}

			player.getPlayerStatus().setStanding(Standing.UP);
			state.getPitch().removePlayer(player);
			state.getPitch().getDogout(state.owner(player)).getDeadAndInjured().add(player);
			
		}
		
	}
	
	protected void touchdown(GameState state, Team team) {
		
		// Add score
		team.getTeamStatus().incScore();

		if ((team == state.getHomeTeam() && 
				state.getAwayTurn() == 8) || 
				(team == state.getAwayTeam() && 
				state.getHomeTurn() == 8)){
			
			EndHalfProcess.getInstance().run(state, null, null);
			return;
			
		}
		
		state.setAwaitFollowUp(false);
		state.setAwaitPush(false);
		state.setAwaitReroll(false);
		
		SetupForKickOffProcess.getInstance().run(state, null, null);
		
		// Who kicks?
		state.setKickingTeam(team);
		state.setReceivingTeam(state.oppositeTeam(team));
		
	}
	
	protected void endTurnForOtherPlayers(GameState state, Team team, Player player) {
		
		for(Player p : team.getPlayers()){
			if (p.getPlayerStatus().getTurn() != PlayerTurn.UNUSED){
				if (p != player){
					if (p.getPlayerStatus().getTurn() == PlayerTurn.BLITZ_ACTION)
						state.owner(p).getTeamStatus().setHasBlitzed(true);
					else if (p.getPlayerStatus().getTurn() == PlayerTurn.PASS_ACTION)
						state.owner(p).getTeamStatus().setHasPassed(true);
					else if (p.getPlayerStatus().getTurn() == PlayerTurn.HAND_OFF_ACTION)
						state.owner(p).getTeamStatus().setHasHandedOf(true);
					else if (p.getPlayerStatus().getTurn() == PlayerTurn.FOUL_ACTION)
						state.owner(p).getTeamStatus().setHasFouled(true);
					
					p.getPlayerStatus().setTurn(PlayerTurn.USED);
				}
			}
		}
		
	}
	
	private void setupUpForKickOff(GameState state) {
		
		ArrayList<Player> collapsedPlayers = new ArrayList<Player>();
		
		if (state.getWeather() == Weather.SWELTERING_HEAT){
			
			for (Player p : state.getAwayTeam().getPlayers()){
				if (state.getPitch().isOnPitch(p)){
					D6 d = new D6();
					d.roll();
					if (d.getResultAsInt() == 1){
						collapsedPlayers.add(p);
					}
				}
			}
			
			for (Player p : state.getHomeTeam().getPlayers()){
				if (state.getPitch().isOnPitch(p)){
					D6 d = new D6();
					d.roll();
					if (d.getResultAsInt() == 1){
						collapsedPlayers.add(p);
					}
				}
			}
			
		}
		
		clearField(state);
		fixStunnedPlayers(state.getHomeTeam());
		resetStatii(state.getAwayTeam(), true);
		resetStatii(state.getHomeTeam(), true);
		standUpAllPlayers(state);
		KnockedOutProcess.getInstance().run(state, null, null);
		
		state.getPitch().getBall().setOnGround(false);
		state.getPitch().getBall().setSquare(null);
		
		for (Player p : collapsedPlayers)
			p.getPlayerStatus().setStanding(Standing.DOWN);
		
		state.setGameStage(GameStage.KICKING_SETUP);
		
	}
	
	protected void standUpAllPlayers(GameState state) {
		
		for(Player p : state.getHomeTeam().getPlayers())
			p.getPlayerStatus().setStanding(Standing.UP);
		
		for(Player p : state.getAwayTeam().getPlayers())
			p.getPlayerStatus().setStanding(Standing.UP);
		
	}
	
	protected void fixStunnedPlayers(Team team) {
		
		for(Player p : team.getPlayers()){
			if (p.getPlayerStatus().getStanding() == Standing.STUNNED){
				p.getPlayerStatus().setStanding(Standing.DOWN);
				p.getPlayerStatus().setTurn(PlayerTurn.USED);
			}
		}
	}
	
	protected void clearField(GameState state) {
		
		// Home team
		for(Player player : state.getHomeTeam().getPlayers()){
			if (state.getPitch().isOnPitch(player)){
				state.getPitch().removePlayer(player);
				state.getPitch().getHomeDogout().getReserves().add(player);
			}
		}
		
		// Away team
		for(Player player : state.getAwayTeam().getPlayers()){
			if (state.getPitch().isOnPitch(player)){
				state.getPitch().removePlayer(player);
				state.getPitch().getAwayDogout().getReserves().add(player);
			}
		}
	}
	
	protected void resetStatii(Team team, boolean newRerolls) {
		
		if (newRerolls)
			team.reset();
		else 
			team.getTeamStatus().reset();
		
		for(Player p : team.getPlayers())
			p.getPlayerStatus().reset();
		
	}
}
