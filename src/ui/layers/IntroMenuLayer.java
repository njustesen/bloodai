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

		g.setColor(new Color(0,0,0,180));
		g.fillRect(origX, origY, width, height);
		g.setColor(new Color(255,255,255,255));
		g.drawRect(origX, origY, width, height);
		
	}

	@Override
	public void clickedLayer(GameMaster master, BloodBowlUI ui, InputManager input) {
		
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
