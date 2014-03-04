package ui.buttons.menu;

import game.GameMaster;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import ai.actions.IllegalActionException;
import ai.actions.SelectCoinSideAction;

import models.GameState;
import ui.BloodBowlUI;
import ui.InputManager;
import ui.buttons.BBButton;

public class TailsButton extends BBButton {
	
	public TailsButton(int centerX, int centerY, BufferedImage image, BufferedImage imageHover, BloodBowlUI ui, boolean active) {
		super(centerX, centerY, image, imageHover, ui, active);
	}

	@Override
	public void paintLayer(Graphics g, GameState state, InputManager input) {
		
		if(inBounds(input.getMouseX(), input.getMouseY()))
			g.drawImage(imageHover, origX, origY, width, height, null);
		else
			g.drawImage(image, origX, origY, width, height, null);
		
	}

	@Override
	public boolean clickedLayer(GameMaster master, BloodBowlUI ui, InputManager input) {
		try {
			master.act(new SelectCoinSideAction(false));
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
