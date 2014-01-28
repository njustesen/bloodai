package ui.buttons;

import java.awt.Graphics;

import models.GameState;
import ui.ImageLoader;
import view.Point2D;

public class BlitzButton extends BBButton {
	
	public BlitzButton(int origX, int origY, int width, int height) {
		super(origX, origY, width, height);
	}

	@Override
	public void paint(Graphics g, GameState state, Point2D mouse, int tilesize) {
		
		g.drawImage(ImageLoader.actionOff.getImage(), origX, origY, width, height, null);
		g.drawImage(ImageLoader.blitz.getImage(), origX, origY, width, height, null);
		
	}
	
	

}
