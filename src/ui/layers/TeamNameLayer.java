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

public class TeamNameLayer extends GraphicsLayer {

	private boolean home;
	private String teamName;
	private List<BBImage> letters;
	private int maxLength;
	private static int letterWidth = 19;
	private BBImage space;
	
	public TeamNameLayer(int origX, int origY, int width, int height,
			BloodBowlUI ui, boolean active, boolean home, int maxLength) {
		super(origX, origY, width, height, ui, active);
		this.home = home;
		this.maxLength = maxLength;
	}

	@Override
	public boolean clickedLayer(GameMaster master, BloodBowlUI ui,
			InputManager input) throws IllegalActionException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void paintLayer(Graphics g, GameState state, InputManager input) {
		
		if (!painted){
			if(letters == null)
				initLetters(state);
			
			if (home){
				int x = 0;
				for(BBImage img : letters){
					g.drawImage(img.getImage(), origX + width - letterWidth + (x*letterWidth), origY, null);
					x--;
				}
				while(x > -maxLength){
					g.drawImage(space.getImage(), origX + width - letterWidth + (x*letterWidth), origY, null);
					x--;
				}
			} else {
				int x = 0;
				for(BBImage img : letters){
					g.drawImage(img.getImage(), origX + x*letterWidth, origY, null);
					x++;
				}
				while(x < maxLength){
					g.drawImage(space.getImage(), origX + x*letterWidth, origY, null);
					x++;
				}
			}
			painted = true;
		}
		
	}

	private void initLetters(GameState state) {
		
		teamName = home ? state.getHomeTeam().getTeamName() : state.getAwayTeam().getTeamName();
		letters = new ArrayList<BBImage>();
		space = ImageLoader.letters.get(' ');
		
		for(char c : teamName.toUpperCase().toCharArray())
			letters.add(ImageLoader.letters.get(c));
		
		// Shorten
		letters = letters.subList(0, Math.min(maxLength, letters.size()));
		
		if (home)
			Collections.reverse(letters);
		
	}

	@Override
	public void checkLayerActivation(GameState state, BloodBowlUI ui) {
		// TODO Auto-generated method stub

	}

}
