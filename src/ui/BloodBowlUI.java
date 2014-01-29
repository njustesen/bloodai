package ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.im.InputContext;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import ai.actions.IllegalActionException;

import models.Player;
import models.Square;

import ui.layers.ActionButtonLayer;
import ui.layers.BackgroundLayer;
import ui.layers.GraphicsLayer;
import ui.layers.MouseOverPitchLayer;
import ui.layers.PitchLayer;
import ui.layers.PlayerLayer;
import ui.layers.ScoreBoardLayer;
import ui.layers.StartGameLayer;
import view.Point2D;

import game.GameMaster;

public class BloodBowlUI extends JPanel {

	private GameMaster master;
	private JFrame frame;
	private InputManager input;
	private ActionHandler handler;
	
	private int width = 900;
	private int height = 600;
	private int tilesize = 30;
	private int topHeight = tilesize*2;
	private int menuWidth = 400;
	private int menuHieght = 250;
	
	private List<GraphicsLayer> layers;
	
	protected Player selectedPlayer;
	private PitchLayer pitchLayer;
	private ActionButtonLayer actionButtonLayer;
	
	public BloodBowlUI(GameMaster master) {
		super();
		this.master = master;
		this.frame = new JFrame();
		init();
	}

	private void init() {
		
		frame.setTitle("BLOODAI");
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setPreferredSize(new Dimension(width, height));
		frame.add(this);
		frame.setResizable(false);
		frame.pack();
		input = new InputManager();
		handler = new ActionHandler();
		this.addKeyListener(input);
		this.addMouseListener(input);
		this.addMouseMotionListener(input);
		
		// Layers
		layers = new ArrayList<GraphicsLayer>();
		layers.add(new BackgroundLayer(0, 0, width, height, this, true));
		layers.add(new ScoreBoardLayer(0, 0, width, topHeight, this, true));
		pitchLayer = new PitchLayer(0, topHeight, 30*tilesize, 15*tilesize, this, true);
		layers.add(pitchLayer);
		layers.add(new MouseOverPitchLayer(0, topHeight, 30*tilesize, 15*tilesize, this, true));
		layers.add(new PlayerLayer(0, topHeight, width, height, this, true));
		actionButtonLayer = new ActionButtonLayer(width/2 - 60*6, topHeight+15*tilesize, 60*6, 60, this, true);
		layers.add(actionButtonLayer);
		layers.add(new StartGameLayer(width/2 - menuWidth/2, height/2 - menuHieght/2, menuWidth, menuHieght, this, true));
		
		selectedPlayer = null;
		
	}

	public void readInput() throws IllegalActionException{
		
		if (input.isMouseClicked()){
			
			// Over pitch
			if (input.getMouseX() >= pitchLayer.getOrigX() && input.getMouseX() <= pitchLayer.getOrigX() + pitchLayer.getWidth()){
				if (input.getMouseY() >= pitchLayer.getOrigY() && input.getMouseY() <= pitchLayer.getOrigY() + pitchLayer.getHeight()){
					clickOnPitchLayer();
				}	
			}
			
			// Action button
			if (input.getMouseX() >= actionButtonLayer.getOrigX() && input.getMouseX() <= actionButtonLayer.getOrigX() + actionButtonLayer.getWidth()){
				if (input.getMouseY() >= actionButtonLayer.getOrigY() && input.getMouseY() <= actionButtonLayer.getOrigY() + actionButtonLayer.getHeight()){
					clickOnActionButtonLayer();
				}	
			}
			
		}
		
		input.refresh();
		
	}
	
	private void clickOnActionButtonLayer() {
		// TODO Auto-generated method stub
		
	}

	private void clickOnPitchLayer() throws IllegalActionException {
		
		int x = (input.getMouseX() - pitchLayer.getOrigX()) / tilesize;
		int y = (input.getMouseY() - pitchLayer.getOrigY()) / tilesize;
		
		// Home reserves
		if (x < 2){
			int i = y * 2 + x;
			handler.clickOnReserves(master, false, i, this);
		}
		
		// Away reserves
		if (x >= 28){
			int i = y * 2 + (x - 2 - 26);
			handler.clickOnReserves(master, false, i, this);
		}
		
		// On actual pitch
		x -= 2;
		handler.clickOnSquare(master, new Square(x, y), this);
		
	}	

	public void paintComponent(Graphics g) {  
	    
	    for(GraphicsLayer layer : layers){
	    	if (layer.isActive())
	    		layer.paint(g, master.getState(), new Point2D(input.getMouseX(), input.getMouseY()));
	    }
	}
	
	public Player getSelectedPlayer() {
		return selectedPlayer;
	}

	public void setSelectedPlayer(Player selectedPlayer) {
		this.selectedPlayer = selectedPlayer;
	}
	
}
