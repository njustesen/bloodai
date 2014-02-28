package ui.layers;

import game.GameMaster;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.GameStage;
import models.GameState;
import models.dice.DiceFace;
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
import ui.buttons.dice.RerollButton;
import view.BBImage;

public class ActionPanelLayer extends GraphicsLayer {

	List<BBButton> buttons = new ArrayList<BBButton>();
	int actionButtonSize = 32;
	int diceWidth = 47;
	int diceHeight = 46;
	int historyRim = 12;
	int horiRim = 4;
	int rim = 3;
	int rerollRim = 6;
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
		
		buttons.add(new RerollButton(	origX + historyRim + (rim+diceWidth)*3 + rerollRim + diceWidth/2 -1, 
				origY + rim + actionButtonSize + horiRim + diceHeight/2, 
				ImageLoader.rerollActive.getImage(),
				ImageLoader.rerollInactive.getImage(), 
				ImageLoader.rerollHover.getImage(),
				ui, true));
		
		Map<DiceFace, BBImage> faceMap = new HashMap<DiceFace, BBImage>();
		faceMap.put(null, ImageLoader.dieNone);
		faceMap.put(DiceFace.ONE, ImageLoader.die1);
		faceMap.put(DiceFace.TWO, ImageLoader.die2);
		faceMap.put(DiceFace.THREE, ImageLoader.die3);
		faceMap.put(DiceFace.FOUR, ImageLoader.die4);
		faceMap.put(DiceFace.FIVE, ImageLoader.die5);
		faceMap.put(DiceFace.SIX, ImageLoader.die6);
		faceMap.put(DiceFace.SKULL, ImageLoader.dieSkull);
		faceMap.put(DiceFace.BOTH_DOWN, ImageLoader.dieBoth);
		faceMap.put(DiceFace.PUSH, ImageLoader.diePush);
		faceMap.put(DiceFace.DEFENDER_STUMBLES, ImageLoader.dieStumples);
		faceMap.put(DiceFace.DEFENDER_KNOCKED_DOWN, ImageLoader.dieDown);
		
		buttons.add(new DiceButton(	origX + historyRim + (div+diceWidth)*0 + diceWidth/2, 
				origY + rim + actionButtonSize + horiRim + diceHeight/2 +1, 
				0, faceMap,
				ui, true));
		
		buttons.add(new DiceButton(	origX + historyRim + (div+diceWidth)*1 + diceWidth/2, 
				origY + rim + actionButtonSize + horiRim + diceHeight/2 +1, 
				1, faceMap,
				ui, true));
		
		buttons.add(new DiceButton(	origX + historyRim + (div+diceWidth)*2 + diceWidth/2, 
				origY + rim + actionButtonSize + horiRim + diceHeight/2 +1, 
				2, faceMap,
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
