package ui.buttons.actions;

import game.GameMaster;

import java.awt.image.BufferedImage;

import ai.actions.EndPhaseAction;
import ai.actions.IllegalActionException;
import ai.actions.PlaceBallAction;

import models.GameStage;
import models.GameState;
import ui.BloodBowlUI;
import ui.InputManager;
import ui.buttons.BBButton;

public class GoButton extends BBButton {
	
	private BufferedImage activeImage;
	private BufferedImage activeImageHover;
	
	public GoButton(int origX, int origY, BufferedImage image, BufferedImage imageHover, BloodBowlUI ui, boolean active) {
		super(origX, origY, image, imageHover, ui, active);
		this.activeImage = image;
		this.activeImageHover = imageHover;
	}

	@Override
	public boolean clickedLayer(GameMaster master, BloodBowlUI ui, InputManager input) {
		
		if (activeImage == null)
			return false;
		
		if (master.getState().getGameStage() == GameStage.KICK_PLACEMENT){
			try {
				master.act(new PlaceBallAction(ui.getTempBallPos()));
				ui.setSelectedPlayer(null);
				ui.setTempBallPos(null);
			} catch (IllegalActionException e) {
				e.printStackTrace();
			}
			return true;
		}
		
		boolean call = false;
		
		switch(master.getState().getGameStage()){
			case KICKING_SETUP : call = true; break;
			case RECEIVING_SETUP : call = true; break;
			case KICK_PLACEMENT : call = true; break;
			case HOME_TURN : call = true; break;
			case AWAY_TURN : call = true; break;
			case BLITZ : call = true; break;
			case QUICK_SNAP : call = true; break;
			case HIGH_KICK : call = true; break;
			case PERFECT_DEFENSE : call = true; break;
			case PLACE_BALL_ON_PLAYER : call = true; break;
			default: call = false;
		}
		
		if (call){
			try {
				master.act(new EndPhaseAction());
				ui.setSelectedPlayer(null);
			} catch (IllegalActionException e) {
				e.printStackTrace();
			}
		}
		
		return true;
	}

	@Override
	public void checkLayerActivation(GameState state, BloodBowlUI ui) {
		if (state.isAwaitingReroll() || 
				state.getGameStage() == GameStage.START_UP ||
				state.getGameStage() == GameStage.GAME_ENDED || 
				state.getGameStage() == GameStage.COIN_TOSS || 
				state.getGameStage() == GameStage.PICK_COIN_TOSS_EFFECT ||
				state.isAwaitingFollowUp() || 
				state.isAwaitingPush()){
			this.image = null;
			this.imageHover = null;
		} else {
			this.image = activeImage;
			this.imageHover = activeImageHover;
		}
		
		
	}

}
