package ui.layers;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import view.Point2D;

import models.GameState;
import ui.buttons.BBButton;
import ui.buttons.BlitzButton;

public class ActionButtonLayer extends GraphicsLayer {

	List<BBButton> buttons = new ArrayList<BBButton>();
	int actionButtonSize = 45;
	
	public ActionButtonLayer(int origX, int origY, int width, int height) {
		super(origX, origY, width, height);
		
		buttons.add(new BlitzButton(origX, origY, actionButtonSize, actionButtonSize));
		//buttons.add(new BlitzButton(origX, origY, width, height));
		
	}

	@Override
	public void paint(Graphics g, GameState state, Point2D mouse, int tilesize) {
		
		for(BBButton button : buttons){
			button.paint(g, state, mouse, tilesize);
		}
		
	}
	
}
