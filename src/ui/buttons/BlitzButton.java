package ui.buttons;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import models.GameState;
import ui.BloodBowlUI;
import ui.ImageLoader;
import view.Point2D;

public class BlitzButton extends BBButton {
	
	public BlitzButton(int origX, int origY, int width, int height, BloodBowlUI bloodBowlUI, boolean active) {
		super(origX, origY, width, height, bloodBowlUI, active);
	}

	@Override
	public void paint(Graphics g, GameState state, Point2D mouse) {
		
		g.drawImage(ImageLoader.actionOff.getImage(), origX, origY, width, height, null);
		g.drawImage(ImageLoader.blitz.getImage(), origX, origY, width, height, null);
		
	}

}
