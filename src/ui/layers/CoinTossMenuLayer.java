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
import ui.buttons.menu.HeadsButton;
import ui.buttons.menu.StartButton;
import ui.buttons.menu.TailsButton;

public class CoinTossMenuLayer extends GraphicsLayer {

	public CoinTossMenuLayer(int origX, int origY, int width, int height, BloodBowlUI ui, boolean active) {
		super(origX, origY, width, height, ui, active);
		
		int xa = origX + width/4;
		int xb = origX + width/4*3;
		int y = origY + height/4*3;
		
		layers.add(new HeadsButton(xa, y, ImageLoader.headsButton.getImage(), ImageLoader.headsButtonHover.getImage(), ui, true));
		layers.add(new TailsButton(xb, y, ImageLoader.tailsButton.getImage(), ImageLoader.tailsButtonHover.getImage(), ui, true));
		
	}

	@Override
	public void paintLayer(Graphics g, GameState state, InputManager input) {

		if (!painted){
		
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
			
			painted = true;
			
		}
		
	}

	@Override
	public boolean clickedLayer(GameMaster master, BloodBowlUI ui, InputManager input) {
		return false;
	}

	@Override
	public void checkLayerActivation(GameState state, BloodBowlUI ui) {
		
		if (state.getGameStage() == GameStage.COIN_TOSS){
			if (!active){
				activate();
			}
		}else if (active){
			deactivate();
		}
		
	}

}
