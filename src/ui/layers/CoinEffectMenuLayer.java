package ui.layers;

import game.GameMaster;

import java.awt.Color;
import java.awt.Graphics;

import models.GameStage;
import models.GameState;
import ui.BloodBowlUI;
import ui.ImageLoader;
import ui.InputManager;
import ui.buttons.menu.KickButton;
import ui.buttons.menu.ReceiveButton;

public class CoinEffectMenuLayer extends GraphicsLayer {

	public CoinEffectMenuLayer(int origX, int origY, int width, int height, BloodBowlUI ui, boolean active) {
		super(origX, origY, width, height, ui, active);
		
		int xa = origX + width/4;
		int xb = origX + width/4*3;
		int y = origY + height/4*3;
		
		layers.add(new KickButton(xa, y, ImageLoader.kickButton.getImage(), ImageLoader.kickButtonHover.getImage(), ui, true));
		layers.add(new ReceiveButton(xb, y, ImageLoader.receiveButton.getImage(), ImageLoader.receiveButtonHover.getImage(), ui, true));
		
	}

	@Override
	public void paintLayer(Graphics g, GameState state, InputManager input) {

		if (!painted){
		
			g.drawImage(ImageLoader.startMenu.getImage(), origX, origY, null);
			
			if (state.getCoinToss().hasAwayPickedHeads() == state.getCoinToss().isResultHeads()){
				g.setFont(font32);
				g.setColor(Color.red);
				g.drawString(state.getAwayTeam().getTeamName(), 
						(int) (origX + width/2 - state.getAwayTeam().getTeamName().length() * 8.2), 
						(int) (origY + height/3));
			} else {
				g.setFont(font32);
				g.setColor(Color.blue);
				g.drawString(state.getHomeTeam().getTeamName(), 
						(int) (origX + width/2 - state.getHomeTeam().getTeamName().length() * 8.2), 
						(int) (origY + height/3));
			}
			g.setFont(font25);
			g.setColor(Color.white);
			g.drawString("won the toss and must select", 
					origX + width/2 - 172, 
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
		
		if (state.getGameStage() == GameStage.PICK_COIN_TOSS_EFFECT){
			if (!active){
				activate();
			}
		}else if (active){
			deactivate();
		}
		
	}

}
