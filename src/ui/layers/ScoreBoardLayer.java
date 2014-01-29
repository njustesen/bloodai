package ui.layers;

import java.awt.Color;
import java.awt.Graphics;
import view.Point2D;

import models.GameState;
import ui.BloodBowlUI;
import ui.ImageLoader;

public class ScoreBoardLayer extends GraphicsLayer {

	public ScoreBoardLayer(int origX, int origY, int width, int height, BloodBowlUI bloodBowlUI, boolean active) {
		super(origX, origY, width, height, bloodBowlUI, active);
		
	}

	@Override
	public void paint(Graphics g, GameState state, Point2D mouse) {
		
		// Score
		g.setFont(font60);
		g.setColor(Color.blue);
		g.drawString("" + state.getHomeTeam().getTeamStatus().getScore(), origX + width/2 - 46 - 17, origY + 50);
		g.setColor(Color.red);
		g.drawString("" + state.getAwayTeam().getTeamStatus().getScore(), origX + width/2 + 46 - 17, origY + 50);
		
		// Team names
		g.setFont(font32);
		g.setColor(Color.blue);
		g.drawString("" + state.getHomeTeam().getTeamName(), origX + width/2 + width/4 - 40, origY + 42);
		g.setColor(Color.red);
		g.drawString("" + state.getAwayTeam().getTeamName(), origX + width/2 - width/4 - 40, origY + 42);
		
		// Turn
		g.setFont(font25);
		g.setColor(Color.white);
		g.drawString("" + state.getHomeTurn() + "/16", origX + width/2 - 100 - 26, origY + 48);
		g.setColor(Color.white);
		g.drawString("" + state.getAwayTurn() + "/16", origX + width/2 + 100 - 26, origY + 48);
		
		
		
	}
	
}
