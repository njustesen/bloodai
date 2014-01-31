package ui.layers;

import game.GameMaster;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import view.Point2D;

import models.GameStage;
import models.GameState;
import ui.BloodBowlUI;
import ui.ImageLoader;
import ui.InputManager;
import ui.buttons.BBButton;
import ui.buttons.HeadsButton;
import ui.buttons.StartButton;
import ui.buttons.TailsButton;

public class CoinTossMenuLayer extends GraphicsLayer {

	public CoinTossMenuLayer(int origX, int origY, int width, int height, BloodBowlUI ui, boolean active) {
		super(origX, origY, width, height, ui, active);
		
		int xa = origX + width/4;
		int xb = origX + width/4*3;
		int y = origY + height/4*3;
		
		layers.add(new HeadsButton(xa, y, ImageLoader.headsButton.getImage(), ImageLoader.headsButtonHover.getImage(), ui));
		layers.add(new TailsButton(xb, y, ImageLoader.tailsButton.getImage(), ImageLoader.tailsButtonHover.getImage(), ui));
		
	}

	@Override
	public void paintLayer(Graphics g, GameState state, InputManager input) {

		g.drawImage(ImageLoader.startMenu.getImage(), origX, origY, null);
		
		g.setFont(font32);
		g.setColor(Color.red);
		g.drawString(state.getAwayTeam().getTeamName(), 
				(int) (origX + width/2 - state.getAwayTeam().getTeamName().length() * 8.2), 
				(int) (origY + height/3));
		g.setFont(font25);
		g.setColor(Color.white);
		g.drawString("to select", 
				origX + width/2 - 48, 
				(int) (origY + height/2));
		
		
	}

	@Override
	public void clickedLayer(GameMaster master, BloodBowlUI ui, InputManager input) {
		
	}

	@Override
	public void checkLayerActivation(GameState state) {
		
		if (state.getGameStage() == GameStage.COIN_TOSS){
			if (!active){
				activate();
			}
		}else if (active){
			deactivate();
		}
		
	}

}
