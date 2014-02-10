package ui.layers;

import game.GameMaster;

import java.awt.Graphics;

import models.GameState;
import ui.BloodBowlUI;
import ui.ImageLoader;
import ui.InputManager;
import ai.actions.IllegalActionException;

public class ScoreBoardFrameLayer extends GraphicsLayer {

	public ScoreBoardFrameLayer(int origX, int origY, int width, int height,
			BloodBowlUI ui, boolean active) {
		super(origX, origY, width, height, ui, active);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean clickedLayer(GameMaster master, BloodBowlUI ui,
			InputManager input) throws IllegalActionException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void paintLayer(Graphics g, GameState state, InputManager input) {
		g.drawImage(ImageLoader.scoreboardframe.getImage(), origX, origY, null);
		

	}

	@Override
	public void checkLayerActivation(GameState state) {
		if (!active){
			activate();
		}
	}

}
