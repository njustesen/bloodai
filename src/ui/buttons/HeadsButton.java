package ui.buttons;

import game.GameMaster;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import ai.actions.IllegalActionException;
import ai.actions.SelectCoinSideAction;
import ai.actions.StartGameAction;

import models.GameState;
import ui.BloodBowlUI;
import ui.InputManager;

public class HeadsButton extends BBButton {
	
	public HeadsButton(int centerX, int centerY, BufferedImage image, BufferedImage imageHover, BloodBowlUI ui) {
		super(centerX, centerY, image, imageHover, ui);
	}

	@Override
	public void paintLayer(Graphics g, GameState state, InputManager input) {
		
		if(inBounds(input.getMouseX(), input.getMouseY()))
			g.drawImage(imageHover, origX, origY, width, height, null);
		else
			g.drawImage(image, origX, origY, width, height, null);
		
	}

	@Override
	public void clickedLayer(GameMaster master, BloodBowlUI ui, InputManager input) {
		try {
			master.act(new SelectCoinSideAction(true));
		} catch (IllegalActionException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void checkLayerActivation(GameState state) {
		// TODO Auto-generated method stub
		
	}

}
