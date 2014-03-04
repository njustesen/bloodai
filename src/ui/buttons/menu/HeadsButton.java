package ui.buttons.menu;

import game.GameMaster;

import java.awt.image.BufferedImage;

import ai.actions.IllegalActionException;
import ai.actions.SelectCoinSideAction;

import models.GameState;
import ui.BloodBowlUI;
import ui.InputManager;
import ui.buttons.BBButton;

public class HeadsButton extends BBButton {
	
	public HeadsButton(int centerX, int centerY, BufferedImage image, BufferedImage imageHover, BloodBowlUI ui, boolean active) {
		super(centerX, centerY, image, imageHover, ui, active);
	}


	@Override
	public boolean clickedLayer(GameMaster master, BloodBowlUI ui, InputManager input) {
		try {
			master.act(new SelectCoinSideAction(true));
		} catch (IllegalActionException e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public void checkLayerActivation(GameState state, BloodBowlUI ui) {
		// TODO Auto-generated method stub
		
	}

}
