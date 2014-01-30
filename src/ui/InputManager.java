package ui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class InputManager implements KeyListener, MouseListener, MouseMotionListener{
	
	private int mouseX;
	private int mouseY;
	private int mouseClickX;
	private int mouseClickY;
	private boolean mouseClicked;
	private boolean mouseDown;
	
	//boolean [] keysDown = new boolean [256];
	//boolean [] keysToggled = new boolean [256];
	
	public InputManager(){
		mouseX = 0;
		mouseY = 0;
		mouseClicked = false;
		mouseDown = false;
	}
	
	public void refresh(){
		mouseClicked = false;
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		
		
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
		mouseDown = true;
		mouseClickX = e.getX();
		mouseClickY = e.getY();
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		mouseDown = false;
		mouseClicked = true;
	}
	@Override
	public void keyPressed(KeyEvent e) {
		//e.getKeyChar();
	}
	@Override
	public void keyReleased(KeyEvent e) {
		//e.getKeyChar();
	}
	@Override
	public void keyTyped(KeyEvent e) {
		//e.getKeyChar();
	}

	public int getMouseX() {
		return mouseX;
	}

	public void setMouseX(int mouseX) {
		this.mouseX = mouseX;
	}

	public int getMouseY() {
		return mouseY;
	}

	public void setMouseY(int mouseY) {
		this.mouseY = mouseY;
	}
	
	public int getMouseClickX() {
		return mouseClickX;
	}

	public void setMouseClickX(int mouseClickX) {
		this.mouseClickX = mouseClickX;
	}

	public int getMouseClickY() {
		return mouseClickY;
	}

	public void setMouseClickY(int mouseClickY) {
		this.mouseClickY = mouseClickY;
	}

	public boolean isMouseClicked() {
		return mouseClicked;
	}

	public void setMouseClicked(boolean mouseClicked) {
		this.mouseClicked = mouseClicked;
	}

	public boolean isMouseDown() {
		return mouseDown;
	}

	public void setMouseDown(boolean mouseDown) {
		this.mouseDown = mouseDown;
	}
	
	

}
