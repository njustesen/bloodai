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
import ui.buttons.StartButton;

public class IntroMenuLayer extends GraphicsLayer {

	public IntroMenuLayer(int origX, int origY, int width, int height, BloodBowlUI ui, boolean active) {
		super(origX, origY, width, height, ui, active);
		
		int x = origX + width/2;
		int y = origY + height/4*3;
		
		layers.add(new StartButton(x, y, ImageLoader.startButton.getImage(), ImageLoader.startButtonHover.getImage(), ui));
		
	}

	@Override
	public void paintLayer(Graphics g, GameState state, InputManager input) {

		if(!painted){
			g.drawImage(ImageLoader.startMenu.getImage(), origX, origY, null);
			
			g.setFont(font32);
			g.setColor(Color.blue);
			g.drawString(state.getHomeTeam().getTeamName(), 
					(int) (origX + width/2 - state.getHomeTeam().getTeamName().length() * 8.2),  
					(int) (origY + height/8*2));
			g.setColor(Color.red);
			g.drawString(state.getAwayTeam().getTeamName(), 
					(int) (origX + width/2 - state.getAwayTeam().getTeamName().length() * 8.2), 
					(int) (origY + height/8*4.5));
			g.setColor(Color.white);
			g.setFont(font32);
			g.drawString("VS", 
					origX + width/2 - 22, 
					(int) (origY + height/8*3.3));
			
			painted = true;
			
		}
		
	}

	@Override
	public boolean clickedLayer(GameMaster master, BloodBowlUI ui, InputManager input) {
		return false;
	}

	@Override
	public void checkLayerActivation(GameState state) {
		
		if (state.getGameStage() == GameStage.START_UP){
			if (!active){
				activate();
			}
		}else if (active){
			deactivate();
		}
		
	}

}
