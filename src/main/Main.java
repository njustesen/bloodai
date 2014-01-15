package main;

import java.util.Date;

import ai.AIAgent;

import models.GameState;
import models.Pitch;
import models.Team;
import models.TeamFactory;

import game.GameMaster;
import game.rulebooks.LVRB6;
import sound.FakeSoundManager;
import sound.SoundManager;

public class Main {

	//private static InputManager inputManager;
	//private static Renderer renderer;
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
		
		Team homeTeam = TeamFactory.getOrcTeam();
		Team awayTeam = TeamFactory.getHumanTeam();

		AIAgent homeAgent = null;
		AIAgent awayAgent = null;
		
	}
	
	private static void loop(long startTime) {

		while(true){
			
//			//startTime = new Date().getTime();
//			if (renderer != null){
//				renderer.renderFrame();
//				renderer.paintComponent(renderer.getGraphics());
//			}
			
			gameMaster.update();
			
		}
	}
}
