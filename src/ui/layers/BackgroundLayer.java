package ui.layers;

import java.awt.Color;
import java.awt.Graphics;
import view.Point2D;

import models.GameState;
import ui.ImageLoader;

public class BackgroundLayer extends GraphicsLayer {

	public BackgroundLayer(int origX, int origY, int width, int height) {
		super(origX, origY, width, height);
		
	}

	@Override
	public void paint(Graphics g, GameState state, Point2D mouse, int tilesize) {
		
		//g.drawImage(ImageLoader.background.getImage(), origX, origY, Color.black, null);
		g.setColor(Color.black);
		g.fillRect(origX, origY, width, height);
		g.drawRect(origX, origY, width, height);
		
	}
	
}
