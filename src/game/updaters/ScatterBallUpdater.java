package game.updaters;

import java.util.ArrayList;

import sound.Sound;
import game.rulebooks.RuleBook;
import ai.actions.Action;
import ai.actions.IllegalActionException;
import models.GameStage;
import models.GameState;
import models.Player;
import models.Square;
import models.Standing;
import models.Team;
import models.dice.D6;
import models.dice.IDice;

public class ScatterBallUpdater extends GameUpdater {
	
	private static ScatterBallUpdater instance;
	
	public static ScatterBallUpdater getInstance(){
		if (instance == null)
			instance = new ScatterBallUpdater();
		return instance;
	}

	@Override
	public void update(GameState state, Action action, RuleBook rulebook) throws IllegalActionException {
		
		int result = (int) (Math.random() * 8 + 1);
		Square ballOn = state.getPitch().getBall().getSquare();
		
		if (ballOn == null)
			throw new IllegalActionException("Ball is out of bounds!");
			
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
		
		Player player = state.getPitch().getPlayerAt( ballOn );
		
		// Land on player
		if (player != null){
			
			if (player.getPlayerStatus().getStanding() == Standing.UP)
				CatchUpdater.getInstance().update(state, action, rulebook);
			else
				ScatterBallUpdater.getInstance().update(state, action, rulebook);
			
			return;
		}
			
		if (state.getGameStage() == GameStage.KICK_OFF){
			
			// Outside pitch
			if (!state.getPitch().isBallInsidePitch() || 
					!state.getPitch().isBallOnTeamSide(state.getReceivingTeam())){
				
				state.setGameStage(GameStage.PLACE_BALL_ON_PLAYER);
				
			}
			
		} else if (!state.getPitch().isBallInsidePitch()){

			ThrowInUpdater.getInstance().update(state, action, rulebook);
			
		}
	}
	
}
