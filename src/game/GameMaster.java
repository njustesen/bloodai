package game;

import java.util.HashMap;

import game.process.BlockProcess;
import game.process.CoinTossProcess;
import game.process.EndPhaseProcess;
import game.process.EndPlayerTurnProcess;
import game.process.EndSetupProcess;
import game.process.FollowUpProcess;
import game.process.FoulProcess;
import game.process.GameStartProcess;
import game.process.GameProcess;
import game.process.HandOffProcess;
import game.process.InterceptionProcess;
import game.process.KickBallProcess;
import game.process.MovePlayerProcess;
import game.process.PassProcess;
import game.process.PlaceBallOnPlayerProcess;
import game.process.PlaceBallProcess;
import game.process.PlacePlayerProcess;
import game.process.PushProcess;
import game.process.RerollProcess;
import game.process.SelectActionProcess;
import game.process.SelectDieProcess;
import game.process.StandUpProcess;
import game.rulebooks.RuleBook;

import ai.AIAgent;
import ai.actions.Action;
import ai.actions.BlockPlayerAction;
import ai.actions.EndPhaseAction;
import ai.actions.EndPlayerTurnAction;
import ai.actions.EndSetupAction;
import ai.actions.FollowUpAction;
import ai.actions.FoulPlayerAction;
import ai.actions.HandOffPlayerAction;
import ai.actions.IllegalActionException;
import ai.actions.KickBallAction;
import ai.actions.SelectInterceptionAction;
import ai.actions.MovePlayerAction;
import ai.actions.PassPlayerAction;
import ai.actions.PlaceBallAction;
import ai.actions.PlaceBallOnPlayerAction;
import ai.actions.PlacePlayerAction;
import ai.actions.RerollAction;
import ai.actions.SelectCoinSideAction;
import ai.actions.SelectCoinTossEffectAction;
import ai.actions.SelectDieAction;
import ai.actions.SelectPlayerTurnAction;
import ai.actions.SelectPushSquareAction;
import ai.actions.StandPlayerUpAction;
import ai.actions.StartGameAction;
import ai.util.GameStateCloner;

import models.GameStage;
import models.GameState;
import models.Turn;

public class GameMaster {
	
	private GameState state;
	private RuleBook rulebook;
	private AIAgent homeAgent;
	private AIAgent awayAgent;
	private HashMap<Class<? extends Action>, GameProcess> processes;
	
	public GameMaster(GameState gameState, RuleBook rulebook, AIAgent homeAgent, AIAgent awayAgent) {
		super();
		this.state = gameState;
		this.rulebook = rulebook;
		this.homeAgent = homeAgent;
		this.awayAgent = awayAgent;
		init();
	}
	
	private void init() {
		processes = new HashMap<Class<? extends Action>, GameProcess>();
		processes.put(StartGameAction.class, 			new GameStartProcess());
		processes.put(RerollAction.class, 				new RerollProcess());
		processes.put(SelectDieAction.class, 			new SelectDieProcess());
		processes.put(PlacePlayerAction.class, 			new PlacePlayerProcess());
		processes.put(SelectPlayerTurnAction.class, 	new SelectActionProcess());
		processes.put(PlaceBallOnPlayerAction.class, 	new PlaceBallOnPlayerProcess());
		processes.put(MovePlayerAction.class, 			new MovePlayerProcess());
		processes.put(StandPlayerUpAction.class, 		new StandUpProcess());
		processes.put(BlockPlayerAction.class, 			new BlockProcess());
		processes.put(PassPlayerAction.class, 			new PassProcess());
		processes.put(HandOffPlayerAction.class, 		new HandOffProcess());
		processes.put(FoulPlayerAction.class, 			new FoulProcess());
		processes.put(FollowUpAction.class, 			new FollowUpProcess());
		processes.put(SelectPushSquareAction.class, 	new PushProcess());
		processes.put(EndPlayerTurnAction.class, 		new EndPlayerTurnProcess());
		processes.put(EndPhaseAction.class, 			new EndPhaseProcess());
		processes.put(SelectInterceptionAction.class, 	new InterceptionProcess());
		processes.put(KickBallAction.class, 			new KickBallProcess());
		processes.put(SelectCoinSideAction.class, 		new CoinTossProcess());
		processes.put(SelectCoinTossEffectAction.class, new CoinTossProcess());
		processes.put(EndSetupAction.class, 			new EndSetupProcess());
		processes.put(PlaceBallAction.class, 			new PlaceBallProcess());
	}

	/**
	 * Starts the blood bowl game.
	 */
	public void start(){
		while(true)
			update();
	}
	
	/**
	 * Updates the Blood Bowl game.
	 * Decide whether to:
	 * 1) await UI input or
	 * 2) call AI agent
	 */
	public void update(){
		
		// Begin game if not started
		/*
		if (state.getGameStage() == GameStage.START_UP){
			try {
				GameStartProcess.getInstance().run(state, null, rulebook);
			} catch (IllegalActionException e) {
				e.printStackTrace();
			}
			return;
		}
		*/
		
		// If game is ended
		if (state.getGameStage() == GameStage.GAME_ENDED)
			return;

		boolean home = Turn.isHomeTurn(state);
		
		Action action = null;
		GameState clone = new GameStateCloner().clone(state);
		
		// Call AI agent or await UI
		if (home && homeAgent != null)
			action = homeAgent.takeAction(this, clone);
		else if (!home && awayAgent != null)
			action = awayAgent.takeAction(this, clone);
		
		if (action != null){
			
			try {
				act(action);
			} catch (IllegalActionException e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
	public void act(Action action) throws IllegalActionException {

		if (action == null)
			throw new IllegalActionException("Action is null!");
		
		GameProcess process = processes.get(action.getClass());
		
		if (process == null)
			throw new IllegalActionException("Unsupported action!");
		
		process.run(state, action, rulebook);
		
	}

	public GameState getState() {
		return state;
	}

}