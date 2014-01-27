package ui.layers;

import java.awt.Graphics;
import view.Point2D;

import models.GameState;

public abstract class GraphicsLayer {

	protected int origX;
	protected int origY;
	protected int width;
	protected int height;
	
	public abstract void paint(Graphics g, GameState state, Point2D mouse, int tilesize);
	
	public GraphicsLayer(int origX, int origY, int width, int height) {
		super();
		this.origX = origX;
		this.origY = origY;
		this.width = width;
		this.height = height;
	}

	public int getOrigX() {
		return origX;
	}

	public void setOrigX(int origX) {
		this.origX = origX;
	}

	public int getOrigY() {
		return origY;
	}
	
	public void setOrigY(int origY) {
		this.origY = origY;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

}
