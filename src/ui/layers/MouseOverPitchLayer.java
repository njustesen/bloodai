package ui.layers;

import game.GameMaster;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import view.Point2D;

import models.GameStage;
import models.GameState;
import models.Square;
import ui.BloodBowlUI;
import ui.ImageLoader;
import ui.InputManager;

public class MouseOverPitchLayer extends GraphicsLayer {

	public MouseOverPitchLayer(int origX, int origY, int width, int height, BloodBowlUI bloodBowlUI, boolean active) {
		super(origX, origY, width, height, bloodBowlUI, active);
		
	}

	@Override
	public void paintLayer(Graphics g, GameState state, InputManager input) {
		
		if(input.getMouseX() > origX && input.getMouseX() < origX + width &&
				input.getMouseY() > origY && input.getMouseY() < origY + height){
			
			Point2D point = fitInSquare( (int)input.getMouseX(), (int)input.getMouseY());
			
			g.setColor(new Color(255,255,255,100));
			g.fillRect(point.getX(), point.getY(), tilesize, tilesize);
			
		}

	}

	@Override
	public boolean clickedLayer(GameMaster master, BloodBowlUI ui, InputManager input) {
		return false;
	}

	@Override
	public void checkLayerActivation(GameState state, BloodBowlUI ui) {
		
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
