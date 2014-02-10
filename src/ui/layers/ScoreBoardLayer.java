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
	
	public ScoreBoardLayer(int origX, int origY, int width, int height, BloodBowlUI ui, boolean active) {
		super(origX, origY, width, height, ui, active);
		
		layers.add(new ScoreLayer(origX + width/2 - scoreWidth - div, origY + div, scoreWidth, scoreHeight, ui, active, true));
		layers.add(new ScoreLayer(origX + width/2 + div, origY + div, scoreWidth, scoreHeight, ui, active, false));
		
	}

	@Override
	public void paintLayer(Graphics g, GameState state, InputManager input) {
		
		g.drawImage(ImageLoader.scoreboard.getImage(), origX, origY, null);
		
		/*
		// Score
		g.setFont(font60);
		g.setColor(Color.blue);
		g.drawString("" + state.getHomeTeam().getTeamStatus().getScore(), origX + width/2 - 46 - 17, origY + 50);
		g.setColor(new Color(200,200,200));
		g.drawString("-", origX + width/2 - 11, origY + 50);
		g.setColor(Color.red);
		g.drawString("" + state.getAwayTeam().getTeamStatus().getScore(), origX + width/2 + 46 - 17, origY + 50);
		
		// Team names
		g.setFont(font32);
		g.setColor(Color.blue);
		g.drawString("" + state.getHomeTeam().getTeamName(), (int) (origX + width/2 - width/3.5 - state.getHomeTeam().getTeamName().length() * 9), origY + 42);
		g.setColor(Color.red);
		g.drawString("" + state.getAwayTeam().getTeamName(), (int) (origX + width/2 + width/3.5 - state.getAwayTeam().getTeamName().length() * 9), origY + 42);
		
		// Turn
		g.setFont(font25);
		g.setColor(Color.white);
		g.drawString("" + state.getHomeTurn() + "/16", origX + width/2 - 100 - 26, origY + 48);
		g.setColor(Color.white);
		g.drawString("" + state.getAwayTurn() + "/16", origX + width/2 + 100 - 26, origY + 48);
		*/
	}

	@Override
	public boolean clickedLayer(GameMaster master, BloodBowlUI ui, InputManager input) {
		return false;
	}

	@Override
	public void checkLayerActivation(GameState state) {
		if (state.getGameStage() == GameStage.START_UP || 
			state.getGameStage() == GameStage.COIN_TOSS || 
			state.getGameStage() == GameStage.PICK_COIN_TOSS_EFFECT){
			if (active){
				deactivate();
			}
		}else if (!active){
			activate();
		}
	}
	
}
