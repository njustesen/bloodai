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

public class TeamApothecaryLayer extends GraphicsLayer {

	private enum ApothecaryStatus {
		UNUSED, USED, NONE;
	}
	
	private boolean home;
	private ApothecaryStatus status;
	private List<BBImage> letters;
	private static int letterWidth = 19;
	
	public TeamApothecaryLayer(int origX, int origY, int width, int height,
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
		
		ApothecaryStatus newStatus = getStatus(state);
		
		if(letters == null || status != newStatus){
			status = newStatus;
			initLetters(state);
		}else {
			return;
		}
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

	private ApothecaryStatus getStatus(GameState state) {
		
		ApothecaryStatus s = null;
		
		if ((home && state.getHomeTeam().hasApothecary()) || 
				(!home && state.getAwayTeam().hasApothecary())){
			
			if ((home && state.getHomeTeam().getTeamStatus().hasUsedApothecary()) || 
					(!home && state.getAwayTeam().getTeamStatus().hasUsedApothecary())){
				
				s = ApothecaryStatus.USED;
			} else {
				s = ApothecaryStatus.UNUSED;
			}
		} else {
			s = ApothecaryStatus.NONE;
		}
			
		return s;
	}

	private void initLetters(GameState state) {
		
		letters = new ArrayList<BBImage>();
		
		switch(status){
		case NONE: letters.add(ImageLoader.letters.get(' '));return;
		case UNUSED: letters.add(ImageLoader.icons.get("apothecary"));return;
		case USED: letters.add(ImageLoader.icons.get("apothecary_used"));return;
		}
		
	}

	@Override
	public void checkLayerActivation(GameState state) {
		// TODO Auto-generated method stub

	}

}
