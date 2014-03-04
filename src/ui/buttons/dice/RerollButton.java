package ui.buttons.dice;

import game.GameMaster;

import java.awt.image.BufferedImage;

import ai.actions.IllegalActionException;
import ai.actions.RerollAction;

import models.GameState;
import ui.BloodBowlUI;
import ui.InputManager;
import ui.buttons.BBButton;

public class RerollButton extends BBButton {
	
	BufferedImage unavailableImage;
	BufferedImage availableImage;
	BufferedImage availableImageHover;

	public RerollButton(int origX, int origY, BufferedImage availableImage, BufferedImage unavailableImage, BufferedImage availableImageHover, BloodBowlUI ui, boolean active) {
		super(origX, origY, availableImage, availableImageHover, ui, active);
		this.unavailableImage = unavailableImage;
		this.availableImageHover = availableImageHover;
		this.availableImage = availableImage;
	}

	@Override
	public boolean clickedLayer(GameMaster master, BloodBowlUI ui, InputManager input) {
		if (master.getState().isAwaitingReroll()){
			try {
				master.act(new RerollAction());
			} catch (IllegalActionException e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	@Override
	public void checkLayerActivation(GameState state, BloodBowlUI ui) {
		if (state.isAwaitingReroll()){
			this.image = availableImage;
			this.imageHover = availableImageHover;
		} else {
			this.image = unavailableImage;
			this.imageHover = unavailableImage;
		}
	}

}
