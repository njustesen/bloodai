package ui.buttons;

import java.awt.Font;
import java.awt.Graphics;

import ui.BloodBowlUI;
import ui.layers.GraphicsLayer;
import view.Point2D;

import models.GameState;

public abstract class BBButton extends GraphicsLayer {
	
	//public abstract void paint(Graphics g, GameState state, Point2D mouse, int tilesize);
	
	public BBButton(int origX, int origY, int width, int height, BloodBowlUI bloodBowlUI, boolean active) {
		super(origX, origY, width, height, bloodBowlUI, active);
	}

}
