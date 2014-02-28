package ui.layers;

import game.GameMaster;

import java.awt.Graphics;
import java.util.ArrayList;

import models.GameState;
import ui.BloodBowlUI;
import ui.InputManager;

public class MainLayer extends GraphicsLayer {

	private int tilesize = 30;
	private int topHeight = tilesize*2;
	private int menuWidth = 480;
	private int menuHieght = 280;
	private int actionPanelWidth = 218;
	private int actionPanelHeight = 92;
	
	public MainLayer(int origX, int origY, int width, int height, BloodBowlUI ui, boolean active) {
		super(origX, origY, width, height, ui, active);
		
		layers.add(new BackgroundLayer(0, 0, width, height, ui, false));
		layers.add(new ScoreBoardLayer(0, 0, width, topHeight, ui, false));
		layers.add(new PitchLayer(0, topHeight, 30*tilesize, 15*tilesize, ui, false));
		layers.add(new MouseOverPitchLayer(0, topHeight, 30*tilesize, 15*tilesize, ui, false));
		layers.add(new PlayerLayer(0, topHeight, width, height, ui, false));
		layers.add(new ActionPanelLayer(width/2 - actionPanelWidth/2, topHeight+15*tilesize, actionPanelWidth, actionPanelHeight, ui, true));
		layers.add(new IntroMenuLayer(width/2 - menuWidth/2, height/2 - menuHieght/2, menuWidth, menuHieght, ui, false));
		layers.add(new CoinTossMenuLayer(width/2 - menuWidth/2, height/2 - menuHieght/2, menuWidth, menuHieght, ui, false));
		layers.add(new CoinEffectMenuLayer(width/2 - menuWidth/2, height/2 - menuHieght/2, menuWidth, menuHieght, ui, false));
		
	}

	@Override
	public boolean clickedLayer(GameMaster master, BloodBowlUI ui, InputManager input) {
		return false;
	}

	@Override
	public void paintLayer(Graphics g, GameState state, InputManager input) {
		// TODO Auto-generated method stub

	}

	@Override
	public void checkLayerActivation(GameState state, BloodBowlUI ui) {
		// TODO Auto-generated method stub

	}

}
