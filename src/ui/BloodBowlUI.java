package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import ui.layers.BackgroundLayer;
import ui.layers.GraphicsLayer;
import ui.layers.MouseOverPitchLayer;
import ui.layers.PitchLayer;
import ui.layers.PlayerLayer;
import view.Point2D;

import game.GameMaster;
import models.GameState;

public class BloodBowlUI extends JPanel implements KeyListener, MouseListener, MouseMotionListener {

	private GameMaster master;
	private JFrame frame;
	
	private int width = 900;
	private int height = 600;
	private int tilesize = 30;
	private int topHeight = tilesize*2;
	
	
	Font fontStandard = new Font("Arial", Font.PLAIN, 25);
	private List<GraphicsLayer> layers;
	private int mouseX = 0;
	private int mouseY = 0;
	
	
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
		this.addKeyListener(this);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		
		// Layers
		layers = new ArrayList<GraphicsLayer>();
		layers.add(new BackgroundLayer(0, 0, width, height));
		layers.add(new PitchLayer(0, topHeight, 30*tilesize, 15*tilesize));
		layers.add(new MouseOverPitchLayer(0, topHeight, 30*tilesize, 15*tilesize));
		layers.add(new PlayerLayer(0, topHeight, width, height));
		
	}

	public void paintComponent(Graphics g) {  
	    g.setFont(fontStandard); //<--
	    g.setColor(Color.WHITE);
	    
	    g.fillRect(0, 0, width, height);
	    
	    for(GraphicsLayer layer : layers)
	    	layer.paint(g, master.getState(), new Point2D(mouseX, mouseY), tilesize);
	    
	}
	
	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
