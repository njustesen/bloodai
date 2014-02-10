package ui.layers;

import game.GameMaster;

import java.awt.Color;
import java.awt.Graphics;
import view.Point2D;

import models.GameStage;
import models.GameState;
import ui.BloodBowlUI;
import ui.ImageLoader;
import ui.InputManager;

public class ScoreBoardLayer extends GraphicsLayer {

	static int scoreWidth = 30;
	static int scoreHeight = 48;
	static int div = 6;
	private int teamNameWidth = 128;
	private int teamNameHeight = 22;
	
	public ScoreBoardLayer(int origX, int origY, int width, int height, BloodBowlUI ui, boolean active) {
		super(origX, origY, width, height, ui, active);
		
		layers.add(new ScoreLayer(origX + width/2 - scoreWidth - div, origY + div, scoreWidth, scoreHeight, ui, active, true));
		layers.add(new ScoreLayer(origX + width/2 + div, origY + div, scoreWidth, scoreHeight, ui, active, false));
		
		layers.add(new TeamNameLayer(	origX + width/2 + div + scoreWidth + div + div/2, 
				origY + div, teamNameWidth, teamNameHeight, ui, active, false, 21));
		
		layers.add(new TeamNameLayer(	origX + width/2 - div - scoreWidth - div - div/2 - teamNameWidth, 
				origY + div, teamNameWidth, teamNameHeight, ui, active, true, 21));
		
		layers.add(new ScoreBoardFrameLayer(origX, origY, width, height, ui, active));
		
	}

	@Override
	public void paintLayer(Graphics g, GameState state, InputManager input) {
		
	}

	@Override
	public boolean clickedLayer(GameMaster master, BloodBowlUI ui, InputManager input) {
		return false;
	}

	@Override
	public void checkLayerActivation(GameState state) {
		if (!active){
			activate();
		}
	}
	
}
