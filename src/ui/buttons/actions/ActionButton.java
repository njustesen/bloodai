package ui.buttons.actions;


import java.awt.image.BufferedImage;

import ui.BloodBowlUI;
import ui.buttons.BBButton;

public abstract class ActionButton extends BBButton {
	
	protected BufferedImage unavaiableImage;
	protected BufferedImage avaiableImage;
	protected BufferedImage avaiableImageHover;
	protected BufferedImage activeImage;
	
	protected boolean active;
	
	public ActionButton(int origX, int origY, BufferedImage unavaiableImage, BufferedImage avaiableImage, BufferedImage avaiableImageHover, BufferedImage activeImage, BloodBowlUI ui, boolean active) {
		super(origX, origY, avaiableImage, avaiableImageHover, ui, active);
		this.unavaiableImage = unavaiableImage;
		this.avaiableImage = avaiableImage;
		this.activeImage = activeImage;
		this.avaiableImageHover = avaiableImageHover;
		this.active = true;
	}
	
	protected void unavailable(){
		this.image = unavaiableImage;
		this.imageHover = unavaiableImage;
	}
	
	protected void available(){
		this.image = avaiableImage;
		this.imageHover = avaiableImageHover;
	}
	
	protected void active(){
		this.image = activeImage;
		this.imageHover = activeImage;
	}

}
