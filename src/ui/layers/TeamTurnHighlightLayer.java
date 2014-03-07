package ui.layers;

import game.GameMaster;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import models.GameStage;
import models.GameState;
import ui.BBImage;
import ui.BloodBowlUI;
import ui.ImageLoader;
import ui.InputManager;
import ai.actions.IllegalActionException;

public class TeamTurnHighlightLayer extends GraphicsLayer {

	private boolean home;
	private int teamTurn;
	private GameStage lastStage;

	public TeamTurnHighlightLayer(int origX, int origY, int width, int height,
			BloodBowlUI ui, boolean active, boolean home) {
		super(origX, origY, width, height, ui, active);
		this.home = home;
	}

	@Override
	public boolean clickedLayer(GameMaster master, BloodBowlUI ui,
			InputManager input) throws IllegalActionException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void paintLayer(Graphics g, GameState state, InputManager input) {
		
		if (home && state.getActingTeam() == state.getHomeTeam()){
			g.drawImage(ImageLoader.turn_blue.getImage(), origX, origY, null);
		} else if (!home && state.getActingTeam() == state.getAwayTeam()){
			g.drawImage(ImageLoader.turn_red.getImage(), origX, origY, null);
		}
		
	}

	@Override
	public void checkLayerActivation(GameState state, BloodBowlUI ui) {
		// TODO Auto-generated method stub

	}

}
