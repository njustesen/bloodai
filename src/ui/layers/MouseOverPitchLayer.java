package ui.layers;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import view.Point2D;

import models.GameState;
import models.Square;
import ui.BloodBowlUI;
import ui.ImageLoader;

public class MouseOverPitchLayer extends GraphicsLayer {

	public MouseOverPitchLayer(int origX, int origY, int width, int height, BloodBowlUI bloodBowlUI, boolean active) {
		super(origX, origY, width, height, bloodBowlUI, active);
		
	}

	@Override
	public void paint(Graphics g, GameState state, Point2D mouse) {
		
		if(mouse.getX() > origX && mouse.getX() < origX + width &&
			mouse.getY() > origY && mouse.getY() < origY + height){
			
			Point2D point = fitInSquare( (int)mouse.getX(), (int)mouse.getY());
			
			g.setColor(new Color(255,255,255,100));
			g.fillRect(point.getX(), point.getY(), tilesize, tilesize);
			
		}

	}
	
}
