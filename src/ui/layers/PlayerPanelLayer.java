package ui.layers;

import game.GameMaster;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.GameStage;
import models.GameState;
import models.Player;
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

public class PlayerPanelLayer extends GraphicsLayer {

	//List<BBButton> buttons = new ArrayList<BBButton>();
	Player selectedPlayer = null;
	boolean homeTeam = false;
	
	public PlayerPanelLayer(int origX, int origY, int width, int height, BloodBowlUI ui, boolean active) {
		super(origX, origY, width, height, ui, active);
		
		
	}

	@Override
	public void paintLayer(Graphics g, GameState state, InputManager input) {
		
		if (selectedPlayer != null && homeTeam){
			g.drawImage(ImageLoader.playerPanelBlue.getImage(), origX, origY, null);
		} else if (selectedPlayer != null && !homeTeam){
			g.drawImage(ImageLoader.playerPanelRed.getImage(), origX, origY, null);
		} else {
			g.drawImage(ImageLoader.playerPanelEmpty.getImage(), origX, origY, null);
		}
		
		
		/*
		for(BBButton button : buttons)
			button.paint(g, state, input);
		*/
	}

	@Override
	public boolean clickedLayer(GameMaster master, BloodBowlUI ui, InputManager input) {
		return false;
	}

	@Override
	public void checkLayerActivation(GameState state, BloodBowlUI ui) {
		
		selectedPlayer = ui.getSelectedPlayer();
		
		if (selectedPlayer != null && selectedPlayer.getTeamId().equals(state.getHomeTeam().getId()))
			homeTeam = true;
		else if (selectedPlayer != null)
			homeTeam = false;
		
		if (!active)
			activate();
	}

}
