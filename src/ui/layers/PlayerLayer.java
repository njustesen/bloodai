package ui.layers;

import game.GameMaster;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.PageAttributes.OriginType;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import view.Point2D;

import models.GameStage;
import models.GameState;
import models.Player;
import models.Race;
import ui.BloodBowlUI;
import ui.ImageLoader;
import ui.InputManager;

public class PlayerLayer extends GraphicsLayer {

	public PlayerLayer(int origX, int origY, int width, int height, BloodBowlUI bloodBowlUI, boolean active) {
		super(origX, origY, width, height, bloodBowlUI, active);
		
	}

	@Override
	public void paintLayer(Graphics g, GameState state, InputManager input) {
		
		// Draw players on pitch
		for(int y = 0; y < state.getPitch().getPlayerArr().length; y++){
			for(int x = 0; x < state.getPitch().getPlayerArr()[0].length; x++){
				
				if (state.getPitch().getPlayerArr()[y][x] != null){
					Player player = state.getPitch().getPlayerArr()[y][x];
					if (ui.getSelectedPlayer() == player){
						g.setColor(new Color(255,255,255,180));
						int screenX = (int) arrayToScreen(x, y).getX();
						int screenY = (int) arrayToScreen(x, y).getY();
						g.fillRect(screenX, screenY, tilesize, tilesize);
					}
					drawPlayer(g, player, x, y);
				}
				
			}
		}
		
		// Draw home dugout
		for (int i = 0; i < state.getPitch().getHomeDogout().getReserves().size(); i++){
			
			Player p = state.getPitch().getHomeDogout().getReserves().get(i);
			drawPlayer(g, p, i%2 - 1, i/2 + 1);
			
		}
		
		// Draw home knockedout
		for(Player p : state.getPitch().getHomeDogout().getKnockedOut()){
					
			int index = state.getPitch().getHomeDogout().getKnockedOut().indexOf(p);
					
			drawPlayer(g, p, index%2 - 1, index/2 + 9);
					
		}
		
		// Draw home dead and injured
		for(Player p : state.getPitch().getHomeDogout().getDeadAndInjured()){
			
			int index = state.getPitch().getHomeDogout().getDeadAndInjured().indexOf(p);
			
			drawPlayer(g, p, index%2 + 1 -1, index/2 + 13);
			
		}
		
		// Draw away dugout
		for (int i = 0; i < state.getPitch().getAwayDogout().getReserves().size(); i++){
			
			Player p = state.getPitch().getAwayDogout().getReserves().get(i);
			drawPlayer(g, p, i%2 + 1 + 26, i/2 + 1);
			
		}

		// Draw away knockedout
		for(Player p : state.getPitch().getAwayDogout().getKnockedOut()){
			
			int index = state.getPitch().getAwayDogout().getKnockedOut().indexOf(p);
			
			drawPlayer(g, p, index%2 + 1 + 26, index/2 + 9);
			
		}
		
		// Draw away dead and injured
		for(Player p : state.getPitch().getAwayDogout().getDeadAndInjured()){
			
			int index = state.getPitch().getAwayDogout().getDeadAndInjured().indexOf(p);
			
			drawPlayer(g, p, index%2 + 1 + 26, index/2 + 13);
			
		}
	}

	private void drawPlayer(Graphics g, Player p, int x, int y){
		int screenX = (int) arrayToScreen(x, y).getX();
		int screenY = (int) arrayToScreen(x, y).getY();
		
		if (ui.getSelectedPlayer() != null && p == ui.getSelectedPlayer()){
			g.setColor(new Color(255,255,255,160));
			g.fillRect(screenX, screenY, tilesize, tilesize);
		}
		
		if(p.getRace() == Race.HUMANS){
			if(p.getTitle() == "Blitzer"){
				g.drawImage(ImageLoader.hblitzer.getBufferedImage(), screenX, screenY, null);
			}else if(p.getTitle() == "Lineman"){
				g.drawImage(ImageLoader.hlineman.getBufferedImage(), screenX, screenY, null);
			}else if(p.getTitle() == "Thrower"){
				g.drawImage(ImageLoader.hthrower.getBufferedImage(), screenX, screenY, null);
			}else if(p.getTitle() == "Catcher"){
				g.drawImage(ImageLoader.hcatcher.getBufferedImage(), screenX, screenY, null);
			}
		}else if(p.getRace() == Race.ORCS){
			if(p.getTitle() == "Blitzer"){
				g.drawImage(ImageLoader.oblitzer.getBufferedImage(), screenX, screenY, null);
			}else if(p.getTitle() == "Lineman"){
				g.drawImage(ImageLoader.olineman.getBufferedImage(), screenX, screenY, null);
			}else if(p.getTitle() == "Thrower"){
				g.drawImage(ImageLoader.othrower.getBufferedImage(), screenX, screenY, null);
			}else if(p.getTitle() == "black orc"){
				g.drawImage(ImageLoader.oblackorc.getBufferedImage(), screenX, screenY, null);
			}
		}
	}
	
	public Point2D arrayToScreen(int x, int y){ 
		int screenX = (x*30+30) + origX;
		int screenY = (y*30-30) + origY;
		return new Point2D(screenX, screenY);	 
	}

	@Override
	public boolean clickedLayer(GameMaster master, BloodBowlUI ui, InputManager input) {
		return false;
	}

	@Override
	public void checkLayerActivation(GameState state) {
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
