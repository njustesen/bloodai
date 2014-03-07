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

public class TeamTurnLayer extends GraphicsLayer {

	private boolean home;
	private int teamTurn;
	private List<BBImage> letters;
	private static int letterWidth = 19;
	private GameStage lastStage;

	public TeamTurnLayer(int origX, int origY, int width, int height,
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
		
		if(letters == null || 
				home && state.getHomeTurn() != teamTurn || 
				!home && state.getAwayTurn() != teamTurn || 
				lastStage != state.getGameStage()){
			lastStage = state.getGameStage();
			initLetters(state);
			
			if (home){
				int x = 0;
				for(BBImage img : letters){
					g.drawImage(img.getImage(), origX + width - letterWidth + (x*letterWidth), origY, null);
					x--;
				}
			} else {
				int x = 0;
				for(BBImage img : letters){
					g.drawImage(img.getImage(), origX + x*letterWidth, origY, null);
					x++;
				}
			}
		} else {
			return;
		}
		
		
		
	}

	private void initLetters(GameState state) {
		
		teamTurn = home ? state.getHomeTurn() : state.getAwayTurn();
		letters = new ArrayList<BBImage>();
		
		String turnString = String.valueOf(teamTurn);
		if (turnString.length()<2)
			turnString = "0"+turnString;
		
		for(char c : turnString.toCharArray())
			letters.add(ImageLoader.letters.get(c));
		
		if (home)
			Collections.reverse(letters);
		
	}

	@Override
	public void checkLayerActivation(GameState state, BloodBowlUI ui) {
		// TODO Auto-generated method stub

	}

}
