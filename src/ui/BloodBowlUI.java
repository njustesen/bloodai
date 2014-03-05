package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import ui.layers.GraphicsLayer;
import ui.layers.MainLayer;

import ai.actions.IllegalActionException;

import models.Player;

import game.GameMaster;

@SuppressWarnings("serial")
public class BloodBowlUI extends JPanel {

	private GameMaster master;
	private JFrame frame;
	private InputManager input;
	private ActionHandler handler;
	private boolean initialized = false;
	
	private int width = 900;
	private int height = 600;
	
	private GraphicsLayer mainLayer;
	
	protected Player selectedPlayer;
	
	public BloodBowlUI(GameMaster master) {
		super(new GridLayout(2,2,219,423));
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
		
		JTextArea ta = new JTextArea("", 5, 10);
		ta.setAutoscrolls(true);
		ta.setEditable(false);
		//ta.setText("dsfsdfsdfsfsdfsdfdsfdsfdsfsdfsdfsfsdfsdfdsfdsfddfsdfdsfdsfdsfsdfsdfsfsdfsdfdsfdsfdsfsdfsdfsdfdsfdsfdsfsdfsdfsfsdfsdfdsfdsfdsfsdfsdfsdfdsfdsfdsfsdfsdfsfsdfsdfdsfdsfdsfsdfsdfsdfdsfdsfdsfsdfsdfsfsdfsdfdsfdsfdsfsdfsdfsdfdsfdsfdsfsdfsdfsfsdfsdfdsfdsfdsfsdfsdfsdfdsfdsfdsfsdfsdfsfsdfsdfdsfdsfdsfsdfsdfsdfdsfdsfdsfsdfsdfsfsdfsdfdsfdsfdsfsdfsdfsdfdsfdsfdsfsdfsdfsfsdfsdfdsfdsfdsfsdfssfsdfsdfsfsdfsdfdsfdsfdsfsdfsdfsfsdfsdfdsfdsfdsfsdfsdfsfsdfsdfdsfdsfdsfsdfsdfsfsdfsdfdsfdsfdsfsdfsdfsfsdfsdfdsfdsf");
		ta.setWrapStyleWord(true);
		ta.setLineWrap(true);
		ta.setBackground(new Color(15,15,15));
		ta.setForeground(new Color(225,225,225));
		JScrollPane sp = new JScrollPane(ta);
		
		this.add(new JLabel(""));
		this.add(new JLabel(""));
		this.add(new JLabel(""));
		this.add(sp);
		
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
		
		boolean repaint = false;
		
		// Click
		if (input.isMouseClicked()){
			mainLayer.clicked(master, this, input);
			repaint = true;
		}
		
		if (input.hasMouseMoved())
			repaint = true;
		
		if (repaint)
			repaint();
		
		input.refresh();
		
	}

	public void paintComponent(Graphics g) {  
		
		if (!initialized)
			return;
		
		mainLayer.checkActivation(master.getState(), this);
	    
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
