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

import models.GameStage;
import models.GameState;
import models.Player;
import models.Square;

import ui.buttons.BBButton;
import ui.layers.ActionButtonLayer;
import ui.layers.BackgroundLayer;
import ui.layers.GraphicsLayer;
import ui.layers.IntroMenuLayer;
import ui.layers.MainLayer;
import ui.layers.MouseOverPitchLayer;
import ui.layers.PitchLayer;
import ui.layers.PlayerLayer;
import ui.layers.ScoreBoardLayer;
import view.Point2D;

import game.GameMaster;

public class BloodBowlUI extends JPanel {

	private GameMaster master;
	private JFrame frame;
	private InputManager input;
	private ActionHandler handler;
	private boolean initialized = false;
	
	private int width = 900;
	private int height = 600;
	
	private PitchLayer pitchLayer;
	private GraphicsLayer mainLayer;
	
	protected Player selectedPlayer;
	private IntroMenuLayer introMenu;
	
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
		mainLayer = new MainLayer(0, 0, width, height, this, true);
		
		selectedPlayer = null;
		
		initialized = true;
		
	}

	public void readInput() throws IllegalActionException{
		
		// Click
		if (input.isMouseClicked())
			mainLayer.clicked(master, this, input);
		
		input.refresh();
		
	}

	public void paintComponent(Graphics g) {  
		
		if (!initialized)
			return;
		
		mainLayer.checkActivation(master.getState());
	    
		mainLayer.paint(g, master.getState(), input);
	    
	}

	public ActionHandler getHandler() {
		return handler;
	}

	public void setHandler(ActionHandler handler) {
		this.handler = handler;
	}

	public Player getSelectedPlayer() {
		return selectedPlayer;
	}

	public void setSelectedPlayer(Player selectedPlayer) {
		this.selectedPlayer = selectedPlayer;
	}
	
}
