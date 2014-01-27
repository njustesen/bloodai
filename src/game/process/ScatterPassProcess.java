package game.process;

import java.util.ArrayList;

import sound.Sound;
import game.GameLog;
import game.rulebooks.RuleBook;
import ai.actions.Action;
import ai.actions.IllegalActionException;
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

public class ScatterPassProcess extends GameProcess {
	
	private static ScatterPassProcess instance;
	
	public static ScatterPassProcess getInstance(){
		if (instance == null)
			instance = new ScatterPassProcess();
		return instance;
	}

	@Override
	public void run(GameState state, Action action, RuleBook rulebook) throws IllegalActionException {
		
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
				CatchProcess.getInstance().run(state, action, rulebook);
			else
				ScatterBallProcess.getInstance().run(state, action, rulebook);
			
			return;
		}
			
		if (!state.getPitch().isBallInsidePitch())
			ThrowInProcess.getInstance().run(state, action, rulebook);
		
	}
	
}
