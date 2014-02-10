package main;


import ai.AIAgent;
import ai.actions.IllegalActionException;

import models.GameState;
import models.Team;
import models.TeamFactory;

import game.GameMaster;
import game.rulebooks.LVRB6;
import ui.BloodBowlUI;

public class Main {

	private static BloodBowlUI ui;
	private static GameMaster gameMaster;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		System.out.println("Welcome to Blood Bowl!");
		
		boolean h = false;
		boolean a = false;
		boolean restart = false;
		boolean fast = false;
		
		Team homeTeam = TeamFactory.getHumanTeam();
		Team awayTeam = TeamFactory.getOrcTeam();

		AIAgent homeAgent = null;
		AIAgent awayAgent = null;
		
		gameMaster = new GameMaster(new GameState(homeTeam, awayTeam), new LVRB6(), homeAgent, awayAgent);
		ui = new BloodBowlUI(gameMaster);
		
		loop();
		
	}
	
	private static void loop() {
		
		ui.repaint();
		
		while(true){
			
			try {
				ui.readInput();
			} catch (IllegalActionException e1) {
				e1.printStackTrace();
			}
			
			gameMaster.update();
			/*
			if (ui != null)
				ui.repaint();
			*/
			try {
				Thread.sleep(1000/24);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}
}
