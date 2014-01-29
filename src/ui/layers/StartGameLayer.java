package ui.layers;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import view.Point2D;

import models.GameState;
import ui.BloodBowlUI;
import ui.ImageLoader;

public class StartGameLayer extends GraphicsLayer {

	public StartGameLayer(int origX, int origY, int width, int height, BloodBowlUI bloodBowlUI, boolean active) {
		super(origX, origY, width, height, bloodBowlUI, active);
		
	}

	@Override
	public void paint(Graphics g, GameState state, Point2D mouse) {

		g.setColor(new Color(0,0,0,180));
		g.fillRect(origX, origY, width, height);
		g.setColor(new Color(255,255,255,255));
		g.drawRect(origX, origY, width, height);
		
	}

}
