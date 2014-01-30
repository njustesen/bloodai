package ui.buttons;

import java.awt.image.BufferedImage;

import game.GameMaster;
import ui.BloodBowlUI;
import ui.layers.GraphicsLayer;

public abstract class BBButton extends GraphicsLayer {
	
	protected BufferedImage image;
	protected BufferedImage imageHover;
	
	public BBButton(int centerX, int centerY, BufferedImage image, BufferedImage imageHover, BloodBowlUI ui) {
		super(	centerX - image.getWidth()/2, 
				centerY - image.getHeight()/2, 
				image.getWidth(), 
				image.getHeight(), 
				ui, 
				true);
		this.image = image;
		this.imageHover = imageHover;
	}

}
