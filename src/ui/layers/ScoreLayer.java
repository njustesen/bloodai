package ui.layers;

import game.GameMaster;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import models.GameStage;
import models.GameState;
import ui.BloodBowlUI;
import ui.ImageLoader;
import ui.InputManager;
import view.BBImage;
import ai.actions.IllegalActionException;

public class ScoreLayer extends GraphicsLayer {

	private boolean home;

	public ScoreLayer(int origX, int origY, int width, int height,
			BloodBowlUI ui, boolean active, boolean home) {
		super(origX, origY, width, height, ui, active);
		this.home = home;
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void paintLayer(Graphics g, GameState state, InputManager input) {
		
		BBImage img = null;
		
		if(home)
			img = ImageLoader.scores.get(state.getHomeTeam().getTeamStatus().getScore());
		else
			img = ImageLoader.scores.get(state.getAwayTeam().getTeamStatus().getScore());
		
		g.drawImage(img.getImage(), origX, origY, null);
		
	}

	@Override
	public boolean clickedLayer(GameMaster master, BloodBowlUI ui,
			InputManager input) throws IllegalActionException {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public void checkLayerActivation(GameState state, BloodBowlUI ui) {
		if (!active){
			activate();
		}
	}

}
