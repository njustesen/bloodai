package ui.layers;

import game.GameMaster;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import models.GameStage;
import models.GameState;
import ui.BloodBowlUI;
import ui.ImageLoader;
import ui.InputManager;
import ui.buttons.BBButton;
import ui.buttons.actions.BlitzButton;
import ui.buttons.actions.BlockButton;
import ui.buttons.actions.FoulButton;
import ui.buttons.actions.HandoffButton;
import ui.buttons.actions.MoveButton;
import ui.buttons.actions.PassButton;

public class ActionPanelLayer extends GraphicsLayer {

	List<BBButton> buttons = new ArrayList<BBButton>();
	int actionButtonSize = 32;
	int rim = 3;
	int div = 4;
	
	public ActionPanelLayer(int origX, int origY, int width, int height, BloodBowlUI ui, boolean active) {
		super(origX, origY, width, height, ui, active);
		
		buttons.add(new MoveButton(	origX + rim + actionButtonSize/2, 
									origY + rim + actionButtonSize/2, 
									ImageLoader.moveUnavail.getImage(),
									ImageLoader.moveAvail.getImage(),
									ImageLoader.moveHover.getImage(),
									ImageLoader.moveActive.getImage(),
									ui, true));
		buttons.add(new BlockButton(origX + rim + (actionButtonSize+div)*1 + actionButtonSize/2, 
									origY + rim + actionButtonSize/2, 
									ImageLoader.blockUnavail.getImage(),
									ImageLoader.blockAvail.getImage(),
									ImageLoader.blockHover.getImage(),
									ImageLoader.blockActive.getImage(),
									ui, true));
		buttons.add(new BlitzButton(origX + rim + (actionButtonSize+div)*2 + actionButtonSize/2, 
									origY + rim + actionButtonSize/2, 
									ImageLoader.blitzUnavail.getImage(),
									ImageLoader.blitzAvail.getImage(), 
									ImageLoader.blitzHover.getImage(),
									ImageLoader.blitzActive.getImage(), 
									ui, true));
		buttons.add(new HandoffButton(	origX + rim + (actionButtonSize+div)*3 + actionButtonSize/2, 
										origY + rim + actionButtonSize/2, 
										ImageLoader.handoffUnavail.getImage(),
										ImageLoader.handoffAvail.getImage(), 
										ImageLoader.handoffHover.getImage(),
										ImageLoader.handoffActive.getImage(),
										ui, true));
		buttons.add(new PassButton(	origX + rim + (actionButtonSize+div)*4 + actionButtonSize/2, 
									origY + rim + actionButtonSize/2, 
									ImageLoader.passUnavail.getImage(),
									ImageLoader.passAvail.getImage(), 
									ImageLoader.passHover.getImage(),
									ImageLoader.passActive.getImage(),
									ui, true));
		buttons.add(new FoulButton(	origX + rim + (actionButtonSize+div)*5 + actionButtonSize/2, 
									origY + rim + actionButtonSize/2, 
									ImageLoader.foulUnavail.getImage(),
									ImageLoader.foulAvail.getImage(), 
									ImageLoader.foulHover.getImage(),
									ImageLoader.foulActive.getImage(), 
									ui, true));
		
		layers.addAll(buttons);
		
	}

	@Override
	public void paintLayer(Graphics g, GameState state, InputManager input) {
		
		g.drawImage(ImageLoader.actionPanel.getImage(), origX, origY, null);
		
		for(BBButton button : buttons)
			button.paint(g, state, input);
		
	}

	@Override
	public boolean clickedLayer(GameMaster master, BloodBowlUI ui, InputManager input) {
		return false;
	}

	@Override
	public void checkLayerActivation(GameState state, BloodBowlUI ui) {
		/*
		if (state.getGameStage() == GameStage.AWAY_TURN ||
				state.getGameStage() == GameStage.HOME_TURN ||
				state.getGameStage() == GameStage.BLITZ)
			if (!active)
				activate();
		else if (active)
			deactivate();
		*/
		if (!active)
			activate();
	}

}
