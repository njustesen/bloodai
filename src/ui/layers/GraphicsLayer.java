package ui.layers;

import java.awt.Font;
import java.awt.Graphics;

import ui.BloodBowlUI;
import ui.Listener;
import view.Point2D;

import models.GameState;

public abstract class GraphicsLayer {

	protected boolean active;
	protected int origX;
	protected int origY;
	protected int width;
	protected int height;
	protected int tilesize = 30;
	protected Font font25;
	protected Font font32;
	protected Font font60;
	protected BloodBowlUI ui;
	
	public abstract void paint(Graphics g, GameState state, Point2D mouse);
	
	public GraphicsLayer(int origX, int origY, int width, int height, BloodBowlUI ui, boolean active) {
		super();
		this.origX = origX;
		this.origY = origY;
		this.width = width;
		this.height = height;
		this.font25 = new Font("Arial", Font.PLAIN, 25);
		this.font32 = new Font("Arial", Font.PLAIN, 32);
		this.font60 = new Font("Arial", Font.PLAIN, 60);
		this.ui = ui;
		this.active = active;
	}
	
	protected Point2D fitInSquare(int x, int y){ 
		int screenX = (x / tilesize) * tilesize;
		int screenY = (y / tilesize) * tilesize;
		return new Point2D(screenX, screenY);	 
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
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

	public int getTilesize() {
		return tilesize;
	}

	public void setTilesize(int tilesize) {
		this.tilesize = tilesize;
	}
	
}
