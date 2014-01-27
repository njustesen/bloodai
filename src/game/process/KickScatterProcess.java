package game.process;

import sound.Sound;
import game.GameLog;
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
import models.dice.D8;
import models.dice.IDice;

public class KickScatterProcess extends GameProcess {
	
	private static KickScatterProcess instance;
	
	public static KickScatterProcess getInstance(){
		if (instance == null)
			instance = new KickScatterProcess();
		return instance;
	}

	@Override
	public void run(GameState state, Action action, RuleBook rulebook) throws IllegalActionException {
	
		D8 da = new D8();
		D6 db = new D6();
		da.roll();
		db.roll();
		int d8 = da.getResultAsInt();
		int d6 = db.getResultAsInt();
		
		Square ballOn = state.getPitch().getBall().getSquare();
		
		while(d6 > 0){
			ballOn = state.getPitch().getBall().getSquare();
			
			switch (d8){
				case 1 : ballOn = new Square(ballOn.getX() - 1, ballOn.getY() - 1); break;
				case 2 : ballOn = new Square(ballOn.getX(), ballOn.getY() - 1); break;
				case 3 : ballOn = new Square(ballOn.getX() + 1, ballOn.getY() - 1); break;
				case 4 : ballOn = new Square(ballOn.getX() - 1, ballOn.getY()); break;
				case 5 : ballOn = new Square(ballOn.getX() + 1, ballOn.getY()); break;
				case 6 : ballOn = new Square(ballOn.getX() - 1, ballOn.getY() + 1); break;
				case 7 : ballOn = new Square(ballOn.getX(), ballOn.getY() + 1); break;
				case 8 : ballOn = new Square(ballOn.getX() + 1, ballOn.getY() + 1); break;
			}
			
			state.getPitch().getBall().setSquare(ballOn);
			
			d6--;
			
		}
		
		// Gust of wind
		if (state.isGust())
			ScatterBallProcess.getInstance().run(state, action, rulebook);
		
		// Ball lands..
		state.getPitch().getBall().setOnGround(true);
		
		// Landed outside pitch
		if (!state.getPitch().isBallOnTeamSide(state.getReceivingTeam()) || 
				!state.getPitch().isBallInsidePitch()){
			
			state.setGameStage(GameStage.PLACE_BALL_ON_PLAYER);
			
			return;
			
		}

		// Land on player
		Player player = state.getPitch().getPlayerAt(ballOn);
		
		if (player != null){
			
			if (player.getPlayerStatus().getStanding() == Standing.UP)
				CatchProcess.getInstance().run(state, action, rulebook);
			else
				ScatterBallProcess.getInstance().run(state, action, rulebook);
			
		} else {
			
			ScatterBallProcess.getInstance().run(state, action, rulebook);
			
		}
	}
}
