package ui.layers;

import game.GameMaster;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import ai.actions.IllegalActionException;

import view.Point2D;

import models.GameState;
import models.Square;
import ui.BloodBowlUI;
import ui.ImageLoader;
import ui.InputManager;

public class PitchLayer extends GraphicsLayer {

	public PitchLayer(int origX, int origY, int width, int height, BloodBowlUI bloodBowlUI, boolean active) {
		super(origX, origY, width, height, bloodBowlUI, active);
		
	}

	@Override
	public void paintLayer(Graphics g, GameState state, InputManager input) {
		
		g.drawImage(ImageLoader.pitch.getImage(), origX, origY, Color.black, null);
//		g.setColor(Color.black);
//		g.fillRect(origX, origY, width, height);
//		g.drawRect(origX, origY, width, height);
		
	}

	@Override
	public boolean clickedLayer(GameMaster master, BloodBowlUI ui, InputManager input) throws IllegalActionException {
		int x = (input.getMouseClickX() - origX) / tilesize;
		int y = (input.getMouseClickY() - origY) / tilesize;
		
		// Home reserves
		if (x < 2){
			int i = y * 2 + x;
			ui.getHandler().clickOnReserves(master, true, i, ui);
			return true;
		}
		
		// Away reserves
		if (x >= 28){
			int i = y * 2 + (x - 2 - 26);
			ui.getHandler().clickOnReserves(master, false, i, ui);
			return true;
		}
		
		// On actual pitch
		x -= 2;
		y += 1;
		x += 1;
		try {
			ui.getHandler().clickOnSquare(master, new Square(x, y), ui);
		} catch (IllegalActionException e) {
			e.printStackTrace();
		}
		
		return true;
	}

	@Override
	public void checkLayerActivation(GameState state) {
		if (!active)
			activate();
	}

}
