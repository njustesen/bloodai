package ui.buttons.actions;

import game.GameMaster;

import java.awt.image.BufferedImage;

import models.GameStage;
import models.GameState;
import models.PlayerTurn;
import ui.BloodBowlUI;
import ui.InputManager;

public class FoulButton extends ActionButton {

	public FoulButton(int origX, int origY, BufferedImage unavaiableImage, BufferedImage avaiableImage, BufferedImage avaiableImageHover, BufferedImage activeImage, BloodBowlUI ui, boolean active) {
		super(origX, origY, unavaiableImage, avaiableImage, avaiableImageHover, activeImage, ui, active);
	}

	@Override
	public boolean clickedLayer(GameMaster master, BloodBowlUI ui, InputManager input) {
		return true;
	}

	@Override
	public void checkLayerActivation(GameState state, BloodBowlUI ui) {
		if (state.getGameStage() == GameStage.START_UP || 
				state.getGameStage() == GameStage.KICK_OFF || 
				state.getGameStage() == GameStage.COIN_TOSS ||
				state.getGameStage() == GameStage.GAME_ENDED || 
				state.getGameStage() == GameStage.HIGH_KICK ||
				state.getGameStage() == GameStage.KICK_PLACEMENT ||
				state.getGameStage() == GameStage.KICKING_SETUP || 
				state.getGameStage() == GameStage.PERFECT_DEFENSE || 
				state.getGameStage() == GameStage.PICK_COIN_TOSS_EFFECT || 
				state.getGameStage() == GameStage.PLACE_BALL_ON_PLAYER || 
				state.getGameStage() == GameStage.QUICK_SNAP || 
				state.getGameStage() == GameStage.RECEIVING_SETUP){
			unavailable();
			return;
		} 
		
		if (ui.getSelectedPlayer() == null){
			unavailable();
		} else if (state.getMovingTeam() != ui.getSelectedPlayer().getTeam()){
			unavailable();
		} else if (ui.getSelectedPlayer().getPlayerStatus().getTurn() == PlayerTurn.UNUSED){
			available();
		} else if (ui.getSelectedPlayer().getPlayerStatus().getTurn() == PlayerTurn.FOUL_ACTION){
			active();
		}
		
		unavailable();
	}

}
