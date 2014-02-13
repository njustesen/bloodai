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

public class TeamCheerleaderLayer extends GraphicsLayer {

	private boolean home;
	private int teamCheerleaders;
	private List<BBImage> letters;
	private static int letterWidth = 19;
	private BBImage space;
	
	public TeamCheerleaderLayer(int origX, int origY, int width, int height,
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
				home && state.getHomeTeam().getCheerleaders() != teamCheerleaders || 
				!home && state.getAwayTeam().getCheerleaders() != teamCheerleaders)
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
		
		teamCheerleaders = home ? state.getHomeTeam().getCheerleaders() : state.getAwayTeam().getCheerleaders();
		letters = new ArrayList<BBImage>();
		
		String cheerleaderString = String.valueOf(teamCheerleaders);
		
		letters.add(ImageLoader.icons.get("cheerleader"));
		letters.add(ImageLoader.letters.get(cheerleaderString.charAt(0)));
		
		if (home)
			Collections.reverse(letters);
		
	}

	@Override
	public void checkLayerActivation(GameState state) {
		// TODO Auto-generated method stub

	}

}
