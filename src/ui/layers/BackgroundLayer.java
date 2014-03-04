package ui.layers;

import game.GameMaster;

import java.awt.Color;
import java.awt.Graphics;

import models.GameState;
import ui.BloodBowlUI;
import ui.ImageLoader;
import ui.InputManager;

public class BackgroundLayer extends GraphicsLayer {

	public BackgroundLayer(int origX, int origY, int width, int height, BloodBowlUI bloodBowlUI, boolean active) {
		super(origX, origY, width, height, bloodBowlUI, active);
		
	}

	@Override
	public void paintLayer(Graphics g, GameState state, InputManager input) {
		
		if(!painted){
			g.drawImage(ImageLoader.background.getImage(), origX, origY, Color.black, null);
			painted = true;
		}
		
	}

	@Override
	public boolean clickedLayer(GameMaster master, BloodBowlUI ui, InputManager input) {
		return false;
	}

	@Override
	public void checkLayerActivation(GameState state, BloodBowlUI ui) {
		if (!active)
			activate();
	}
	
}
