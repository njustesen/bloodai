package ui.layers;

import game.GameMaster;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import view.Point2D;

import models.GameStage;
import models.GameState;
import ui.BloodBowlUI;
import ui.InputManager;
import ui.buttons.BBButton;
import ui.buttons.BlitzButton;

public class ActionButtonLayer extends GraphicsLayer {

	List<BBButton> buttons = new ArrayList<BBButton>();
	int actionButtonSize = 45;
	
	public ActionButtonLayer(int origX, int origY, int width, int height, BloodBowlUI bloodBowlUI, boolean active) {
		super(origX, origY, width, height, bloodBowlUI, active);
		
		//buttons.add(new BlitzButton(origX, origY, actionButtonSize, actionButtonSize, bloodBowlUI, active));
		//buttons.add(new BlitzButton(origX, origY, width, height));
		
	}

	@Override
	public void paintLayer(Graphics g, GameState state, InputManager input) {
		
		for(BBButton button : buttons)
			button.paint(g, state, input);
		
	}

	@Override
	public void clickedLayer(GameMaster master, BloodBowlUI ui, InputManager input) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void checkLayerActivation(GameState state) {
		
		if (state.getGameStage() == GameStage.AWAY_TURN ||
				state.getGameStage() == GameStage.HOME_TURN ||
				state.getGameStage() == GameStage.BLITZ)
			if (!active)
				activate();
		else if (active)
			deactivate();
		
	}

}
