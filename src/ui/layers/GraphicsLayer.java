package ui.layers;

import game.GameMaster;

import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import ai.actions.IllegalActionException;

import ui.BloodBowlUI;
import ui.InputManager;
import ui.Listener;
import ui.buttons.BBButton;
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
	protected List<GraphicsLayer> layers;
	
	public abstract boolean clickedLayer(GameMaster master, BloodBowlUI ui, InputManager input) throws IllegalActionException;
	
	public abstract void paintLayer(Graphics g, GameState state, InputManager input);
	
	public abstract void checkLayerActivation(GameState state);

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
		this.layers = new ArrayList<GraphicsLayer>();
	}
	
	public boolean clicked(GameMaster master, BloodBowlUI ui, InputManager input) throws IllegalActionException{
		
		if (!clickedLayer(master, ui, input)){
			for(int i = layers.size()-1; i >= 0; i--)
				if(layers.get(i).isActive() && layers.get(i).inBounds(input.getMouseClickX(), input.getMouseClickY()))
					if (layers.get(i).clicked(master, ui, input))
						return true;
			return false;
		}
		
		return true;
					
	}
	
	public void paint(Graphics g, GameState state, InputManager input){
		
		paintLayer(g, state, input);
		
		for(GraphicsLayer layer : layers)
			if(layer.isActive())
				layer.paint(g, state, input);
		
	}
	
	public void checkActivation(GameState state){
		
		checkLayerActivation(state);
		
		for(GraphicsLayer layer : layers)
			layer.checkActivation(state);
		
	}
	
	public void activate(){
		active = true;
		for(GraphicsLayer layer : layers)
			layer.activate();
	}
	
	public void deactivate(){
		active = false;
		for(GraphicsLayer layer : layers)
			layer.deactivate();
	} 
	
	protected Point2D fitInSquare(int x, int y){ 
		return new Point2D(	(x / tilesize) * tilesize, 
							(y / tilesize) * tilesize);	 
	}
	
	public List<GraphicsLayer> getLayers() {
		return layers;
	}

	public void setLayers(List<GraphicsLayer> layers) {
		this.layers = layers;
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

	public boolean inBounds(int x, int y) {
		return (x >= origX && x <= origX + width && y >= origY && y <= origY + height);
	}

	
	
}
