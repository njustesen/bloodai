package ui.layers;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import view.Point2D;

import models.GameState;
import ui.BloodBowlUI;
import ui.ImageLoader;

public class PitchLayer extends GraphicsLayer {

	public PitchLayer(int origX, int origY, int width, int height, BloodBowlUI bloodBowlUI, boolean active) {
		super(origX, origY, width, height, bloodBowlUI, active);
		
	}

	@Override
	public void paint(Graphics g, GameState state, Point2D mouse) {
		
		g.drawImage(ImageLoader.pitch.getImage(), origX, origY, Color.black, null);
//		g.setColor(Color.black);
//		g.fillRect(origX, origY, width, height);
//		g.drawRect(origX, origY, width, height);
		
	}

}
