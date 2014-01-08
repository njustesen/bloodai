package game.updaters;

import sound.Sound;
import game.GameLog;
import game.rulebooks.RuleBook;
import ai.actions.Action;
import models.GameStage;
import models.GameState;
import models.Player;
import models.Square;
import models.Standing;
import models.Team;
import models.dice.D3;
import models.dice.D6;
import models.dice.D8;
import models.dice.IDice;

public class ThrowInUpdater extends GameUpdater {
	
	private static ThrowInUpdater instance;
	
	public static ThrowInUpdater getInstance(){
		if (instance == null)
			instance = new ThrowInUpdater();
		return instance;
	}

	@Override
	public void update(GameState state, Action action, RuleBook rulebook) {
	
		Square ballOn = state.getPitch().getBall().getSquare();
		
		if (ballOn == null)
			return;
			
		int x = 0;
		int y = 0;
		
		D3 d = new D3();
		d.roll();
		int roll = d.getResultAsInt();
		
		if (ballOn.getY() < 1){
			y = 1;
		} else if (ballOn.getY() > 15){
			y = -1;
		}
		
		if (ballOn.getX() < 1){
			x = 1;
		} else if (ballOn.getX() > 26){
			x = -1;
		}
		
		if (x != 0 && y == 0){
		
			// Move ball on square in
			state.getPitch().getBall().getSquare().setX(ballOn.getX() + x);
			
			// Left or right
			switch(roll){
			case 1: throwInDirection(state, x, -1); break;
			case 2: throwInDirection(state, x, 0); break;
			case 3: throwInDirection(state, x, 1); break;
			}
		
		} else if (x == 0 && y != 0){
			
			// Move ball on square in
			state.getPitch().getBall().getSquare().setY(ballOn.getY() + y);
			
			// Up or Down
			switch(roll){
			case 1: throwInDirection(state, -1, y); break;
			case 2: throwInDirection(state, 0, y); break;
			case 3: throwInDirection(state, 1, y); break;
			}
			
		} else if (x != 0 && y != 0){
			
			// Diagonal
			switch(roll){
			case 1: throwInDirection(state, x, 0); break;
			case 2: throwInDirection(state, x, y); break;
			case 3: throwInDirection(state, 0, y); break;
			}
			
		}
		
		// Landed on player?
		Square sq = state.getPitch().getBall().getSquare();
		
		if (ballOn == null)
			return;
			
		Player player = state.getPitch().getPlayerAt(sq);
		
		if (player != null){
			CatchUpdater.getInstance().update(state, action, rulebook);
		} else if (!state.getPitch().isBallInsidePitch()){
			ThrowInUpdater.getInstance().update(state, action, rulebook);
		} else {
			ScatterBallUpdater.getInstance().update(state, action, rulebook);
		}
	}
	
	private void throwInDirection(GameState state, int x, int y) {
		
		D6 da = new D6();
		da.roll();
		D6 db = new D6();
		db.roll();
		int distance = da.getResultAsInt() + db.getResultAsInt();
		
		while(distance > 0){
			
			Square ballOn = state.getPitch().getBall().getSquare();
			
			Square newBallOn = new Square(ballOn.getX() + x, ballOn.getY() + y);
			
			state.getPitch().getBall().setSquare(newBallOn);
			
			if (!state.getPitch().isBallInsidePitch()){
				correctOOBBallPosition(state, x, y);
				break;
			}
			
			distance--;
			
		}
		
	}
	
	private void correctOOBBallPosition(GameState state, int moveX, int moveY) {
		
		Square ballOn = state.getPitch().getBall().getSquare();
		
		if (ballOn.getX() == 0 || ballOn.getX() == 27){
			if (ballOn.getY() != 0 && ballOn.getY() != 16){
				ballOn.setY(ballOn.getY() - moveY);
			}
		} else if (ballOn.getY() == 0 || ballOn.getY() == 16){
			ballOn.setX(ballOn.getX() - moveX);
		}
		
	}
}
