package ui.layers;

import game.GameMaster;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import models.GameState;
import ui.BloodBowlUI;
import ui.ImageLoader;
import ui.InputManager;
import view.BBImage;
import ai.actions.IllegalActionException;

public class TeamFameLayer extends GraphicsLayer {

	private boolean home;
	private int teamFame;
	private List<BBImage> letters;
	private static int letterWidth = 19;
	private BBImage space;
	
	public TeamFameLayer(int origX, int origY, int width, int height,
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
				home && state.getHomeTeam().getTeamStatus().getFAME() != teamFame || 
				!home && state.getAwayTeam().getTeamStatus().getFAME() != teamFame)
			initLetters(state);
		else 
			return;
		
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
		
	}

	private void initLetters(GameState state) {
		
		teamFame = home ? state.getHomeTeam().getTeamStatus().getFAME() : state.getAwayTeam().getTeamStatus().getFAME();
		letters = new ArrayList<BBImage>();
		
		String fameString = String.valueOf(teamFame);
		
		letters.add(ImageLoader.icons.get("fame"));
		letters.add(ImageLoader.letters.get(fameString.charAt(0)));
		
		if (home)
			Collections.reverse(letters);
		
	}

	@Override
	public void checkLayerActivation(GameState state, BloodBowlUI ui) {
		// TODO Auto-generated method stub

	}

}
