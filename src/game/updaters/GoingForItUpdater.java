package game.updaters;

import sound.Sound;
import game.GameLog;
import game.rulebooks.RuleBook;
import ai.actions.Action;
import models.GameStage;
import models.GameState;
import models.Player;
import models.Skill;
import models.Square;
import models.Team;
import models.Weather;
import models.actions.Dodge;
import models.actions.GoingForIt;
import models.dice.D6;
import models.dice.DiceRoll;
import models.dice.IDice;

public class GoingForItUpdater extends GameUpdater {
	
	private static GoingForItUpdater instance;
	
	public static GoingForItUpdater getInstance(){
		if (instance == null)
			instance = new GoingForItUpdater();
		return instance;
	}

	@Override
	public void update(GameState state, Action action, RuleBook rulebook) {
		
		if (state.getCurrentGoingForIt() == null)
			return;
		
		DiceRoll roll = new DiceRoll();
		D6 d = new D6();
		roll.addDice(d);
		d.roll();
		state.setCurrentDiceRoll(roll);
		int success = 2;
		if (state.getWeather() == Weather.BLIZZARD)
			success++;
		
		Player player = state.getCurrentGoingForIt().getPlayer();
		Square square = state.getCurrentGoingForIt().getSquare();
		
		if (d.getResultAsInt() >= success){
			
			// Going for it to Blitz?
			if (player.getPosition().equals(square) && 
					state.getCurrentBlock() != null && 
					state.getCurrentBlock().getAttacker() == player){
				player.getPlayerStatus().setMovedToBlock(true);
				
				BlockUpdater.getInstance().update(state, action, rulebook);
				return;
			}
			
			if (state.isInTackleZone(player))
				dodgeToMovePlayer(state, player, square);
			else 
				movePlayer(state, player, square, false);
			
		} else {
			
			if (state.isAbleToReroll(state.owner(player))){
				state.setCurrentGoingForIt(new GoingForIt(player, square, success));
				state.setAwaitReroll(true);
			} else {
				movePlayer(state, player, square, true);
			}
			
		}
		
	}
	
}
