package main;

import java.util.Date;

import ai.AIAgent;
import ai.BaseLineAI;
import ai.RandomAI;
import ai.RandomMoveAI;
import ai.RandomMoveTouchdownAI;
import ai.RandomTouchdownAI;

import models.GameState;
import models.Pitch;
import models.Team;
import models.TeamFactory;

import game.GameMaster;
import sound.FakeSoundManager;
import sound.SoundManager;
import test.DiceTester;
import view.InputManager;
import view.Renderer;

public class Main {

	private static InputManager inputManager;
	private static Renderer renderer;
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
		
		homeAgent = new RandomMoveAI(true);
		awayAgent = new RandomMoveAI(false);
		
		fast = true;
		restart = true;
		initialize(homeTeam, awayTeam, homeAgent, awayAgent, fast, restart);
		startGame();
	}
	
	public static void test(){
		
		DiceTester.testDices();
		
	}

	public static void initialize(Team home, Team away, AIAgent homeAgent, AIAgent awayAgent, boolean fast, boolean restart){

		Pitch pitch = new Pitch(home, away);
		gameMaster = new GameMaster(new GameState(home, away, pitch), homeAgent, awayAgent, fast, restart);
		gameMaster.enableLogging();
		gameMaster.setSoundManager(new SoundManager());
		//gameMaster.setSoundManager(new FakeSoundManager());
		inputManager = new InputManager(gameMaster);
		renderer = new Renderer(600, gameMaster, inputManager);
	}
	
	public static void startGame(){

		long startTime = new Date().getTime();
		
		loop(startTime);
	
	}

	private static void loop(long startTime) {

		while(true){
			
			//startTime = new Date().getTime();
			if (renderer != null){
				renderer.renderFrame();
				renderer.paintComponent(renderer.getGraphics());
			}
			
			gameMaster.update();
			
		}
	}
}
