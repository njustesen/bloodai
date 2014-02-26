package ui.buttons;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import models.GameState;

import game.GameMaster;
import ui.BloodBowlUI;
import ui.InputManager;
import ui.layers.GraphicsLayer;

public abstract class BBButton extends GraphicsLayer {
	
	protected BufferedImage image;
	protected BufferedImage imageHover;
	
	public BBButton(int centerX, int centerY, BufferedImage image, BufferedImage imageHover, BloodBowlUI ui, boolean active) {
		super(	centerX - image.getWidth()/2, 
				centerY - image.getHeight()/2, 
				image.getWidth(), 
				image.getHeight(), 
				ui, 
				active);
		this.image = image;
		this.imageHover = imageHover;
	}
	

	@Override
	public void paintLayer(Graphics g, GameState state, InputManager input) {
		
		if(inBounds(input.getMouseX(), input.getMouseY()))
			g.drawImage(imageHover, origX, origY, width, height, null);
		else
			g.drawImage(image, origX, origY, width, height, null);
		
	}

}
