package ui.layers;

import java.awt.Color;
import java.awt.Graphics;
import view.Point2D;

import models.GameState;
import ui.ImageLoader;

public class MouseOverPitchLayer extends GraphicsLayer {

	public MouseOverPitchLayer(int origX, int origY, int width, int height) {
		super(origX, origY, width, height);
		
	}

	@Override
	public void paint(Graphics g, GameState state, Point2D mouse, int tilesize) {
		
		if(mouse.getX() > origX && mouse.getX() < origX + width &&
			mouse.getY() > origY && mouse.getY() < origY + height){
			
			Point2D point = fitInSquare( (int)mouse.getX(), (int)mouse.getY(), tilesize);
			
			g.setColor(new Color(255,255,255,100));
			g.fillRect(point.getX(), point.getY(), tilesize, tilesize);
			
		}
	}
	

	public Point2D fitInSquare(int x, int y, int tilesize){ 
		int screenX = (x / tilesize) * tilesize;
		int screenY = (y / tilesize) * tilesize;
		return new Point2D(screenX, screenY);	 
	}
	
}
