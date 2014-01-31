package ui.buttons;

import game.GameMaster;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import models.GameState;
import ui.BloodBowlUI;
import ui.ImageLoader;
import ui.InputManager;
import view.Point2D;

public class BlitzButton extends BBButton {
	
	public BlitzButton(int origX, int origY, BufferedImage image, BufferedImage imageHover, BloodBowlUI ui) {
		super(origX, origY, image, imageHover, ui);
	}

	@Override
	public void paintLayer(Graphics g, GameState state, InputManager input) {
		
		//g.drawImage(ImageLoader.actionOff.getImage(), origX, origY, width, height, null);
		g.drawImage(ImageLoader.blitz.getImage(), origX, origY, width, height, null);
		
	}

	@Override
	public boolean clickedLayer(GameMaster master, BloodBowlUI ui, InputManager input) {
		return true;
	}

	@Override
	public void checkLayerActivation(GameState state) {
		
	}

}
