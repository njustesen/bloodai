package ui.layers;

import game.GameMaster;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import models.GameState;
import models.Player;
import models.Skill;
import ui.BloodBowlUI;
import ui.ImageLoader;
import ui.InputManager;

public class PlayerPanelLayer extends GraphicsLayer {

	//List<BBButton> buttons = new ArrayList<BBButton>();
	Player selectedPlayer = null;
	boolean homeTeam = false;
	private int numberX = 22;
	private int numberY = 32;
	private int nameX = 46;
	private int nameY = 31;
	private int maY = 58;
	private int maX = 79;
	private int stX = maX + 72;
	private int agX = stX + 72;
	private int avX = agX + 72;
	private int skillX = 8;
	private int skillY = 74;
	private int skillDiv = 2;
	private int skillPadding = 3;
	private int skillHeight = 10;
	
	public PlayerPanelLayer(int origX, int origY, int width, int height, BloodBowlUI ui, boolean active) {
		super(origX, origY, width, height, ui, active);
		
	}

	@Override
	public void paintLayer(Graphics g, GameState state, InputManager input) {
		
		Color teamColor = null;
		
		if (selectedPlayer != null && homeTeam){
			g.drawImage(ImageLoader.playerPanelBlue.getImage(), origX, origY, null);
			teamColor = Color.BLUE;
		} else if (selectedPlayer != null && !homeTeam){
			g.drawImage(ImageLoader.playerPanelRed.getImage(), origX, origY, null);
			teamColor = Color.RED;
		} else {
			g.drawImage(ImageLoader.playerPanelEmpty.getImage(), origX, origY, null);
			return;
		}
		
		// Draw number
		g.setFont(new Font("Arial", 0, 26));
		String number = ""+selectedPlayer.getNumber();
		int stringLen = (int) g.getFontMetrics().getStringBounds(number, g).getWidth();  
        int x = origX + numberX - stringLen/2; 
        g.setColor(Color.BLACK);
		g.drawString(number, x-1, origY + numberY-1);
		g.setColor(teamColor);
		g.drawString(number, x, origY + numberY);
		
		// Draw name and position
		g.setFont(new Font("Arial", 0, 14));
		String shortName = selectedPlayer.getName().substring(0, Math.min(24, selectedPlayer.getName().length()));
		String name = shortName + " - " + selectedPlayer.getTitle();
        x = origX + nameX; 
		g.setColor(Color.WHITE);
		g.drawString(name, x, origY + nameY);
		
		// MA
		g.setFont(new Font("Arial", 0, 22));
		String ma = ""+selectedPlayer.getMA();
		stringLen = (int) g.getFontMetrics().getStringBounds(ma, g).getWidth();  
        x = origX + maX - stringLen/2; 
        g.setColor(Color.WHITE);
		g.drawString(ma, x, origY + maY);
		
		// ST
		String st = ""+selectedPlayer.getST();
		stringLen = (int) g.getFontMetrics().getStringBounds(st, g).getWidth();  
        x = origX + stX - stringLen/2; 
        g.setColor(Color.WHITE);
		g.drawString(st, x, origY + maY);
		
		// AG
		String ag = ""+selectedPlayer.getAG();
		stringLen = (int) g.getFontMetrics().getStringBounds(ag, g).getWidth();  
        x = origX + agX - stringLen/2; 
        g.setColor(Color.WHITE);
		g.drawString(ag, x, origY + maY);
		
		// AV
		String av = ""+selectedPlayer.getAV();
		stringLen = (int) g.getFontMetrics().getStringBounds(av, g).getWidth();  
        x = origX + avX - stringLen/2; 
        g.setColor(Color.WHITE);
		g.drawString(av, x, origY + maY);
		
		// Skills
		g.setFont(new Font("Arial", 0, skillHeight));
		int xx = skillX;
		int yy = skillY;
		for(Skill skill : selectedPlayer.getSkills()){
			
			String str = skill.getName();
			str = str.substring(0, 1).toUpperCase() + str.substring(1, str.length()).toLowerCase();
			stringLen = (int) g.getFontMetrics().getStringBounds(str, g).getWidth();  
			if (xx + stringLen > width){
				xx = skillX;
				yy += skillDiv + skillPadding*2 + skillHeight - 1;
			}
			g.setColor(new Color(25,100,25));
			g.fillRoundRect(origX + xx - skillPadding, origY + yy - skillHeight, stringLen + skillPadding*2, 14, 4, 4);
			g.setColor(new Color(125,175,125));
			g.drawRoundRect(origX + xx - skillPadding, origY + yy - skillHeight, stringLen + skillPadding*2, 14, 4, 4);
			g.setColor(Color.WHITE);
			g.drawString(str, origX + xx, origY + yy +1);
			xx += stringLen + skillDiv + skillPadding*2;
			if (xx > width){
				xx = skillX;
				yy += skillDiv + skillPadding*2 + skillHeight - 1;
			}
			
		}
		
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
