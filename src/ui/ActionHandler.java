package ui;

import ai.actions.Action;
import ai.actions.BlockPlayerAction;
import ai.actions.HandOffPlayerAction;
import ai.actions.IllegalActionException;
import ai.actions.MovePlayerAction;
import ai.actions.PassPlayerAction;
import ai.actions.PlaceBallAction;
import ai.actions.PlaceBallOnPlayerAction;
import ai.actions.PlacePlayerAction;
import ai.actions.StandPlayerUpAction;
import ai.actions.StartGameAction;
import models.GameStage;
import models.GameState;
import models.Player;
import models.Square;
import models.Standing;
import game.GameMaster;

public class ActionHandler {

	public void clickOnStart(GameMaster master, BloodBowlUI ui) throws IllegalActionException{
		
		master.act(new StartGameAction());
		
	}
	
	public void clickOnSquare(GameMaster master, Square square, BloodBowlUI ui) throws IllegalActionException{

		GameState state = master.getState();
		
		if(ui.getSelectedPlayer() != null){
			
			if (state.getGameStage() == GameStage.KICKING_SETUP){
				
				if (ui.getSelectedPlayer().getTeamId().equals(state.getKickingTeam())){
					
					if (state.getPitch().getPlayerAt(square) == null){
						
						Action action = new PlacePlayerAction(ui.getSelectedPlayer(), square);
						master.act(action);
						
					}
					
				}
				
			}
			
			if (state.getGameStage() == GameStage.RECEIVING_SETUP){
				
				if (ui.getSelectedPlayer().getTeamId().equals(state.getReceivingTeam())){
					
					if (state.getPitch().getPlayerAt(square) == null){
						
						Action action = new PlacePlayerAction(ui.getSelectedPlayer(), square);
						master.act(action);
						
					}
					
				}
				
			}
			
			if (state.getGameStage() == GameStage.PERFECT_DEFENSE){
				
				if (ui.getSelectedPlayer().getTeamId().equals(state.getKickingTeam())){
					
					if (state.getPitch().getPlayerAt(square) == null){
						
						Action action = new PlacePlayerAction(ui.getSelectedPlayer(), square);
						master.act(action);
						
					}
					
				}
				
			}
			
			if (state.getGameStage() == GameStage.HIGH_KICK){
				
				if (ui.getSelectedPlayer().getTeamId().equals(state.getReceivingTeam())){
					
					if (state.getPitch().getPlayerAt(square) == null){
						
						Action action = new PlacePlayerAction(ui.getSelectedPlayer(), square);
						master.act(action);
						
					}
					
				}
				
			}
			
			if (state.getGameStage() == GameStage.QUICK_SNAP){
				
				if (ui.getSelectedPlayer().getTeamId().equals(state.getReceivingTeam())){
					
					if (state.getPitch().getPlayerAt(square) == null){
						
						Action action = new MovePlayerAction(ui.getSelectedPlayer(), square);
						master.act(action);
						
					}
					
				}
				
			}
			
			// Normal turn
			if (state.getMovingTeam() == state.owner(ui.getSelectedPlayer())){
				
				Player player = state.getPitch().getPlayerAt(square);
				
				if (player == null){
					
					Action action = new MovePlayerAction(ui.getSelectedPlayer(), square);
					master.act(action);
					
				} else if (state.onDifferentTeams(ui.getSelectedPlayer(), player)){
					
					Action action = new BlockPlayerAction(ui.getSelectedPlayer(), player);
					master.act(action);
					
				} else {
					
					if (player == ui.getSelectedPlayer() && player.getPlayerStatus().getStanding() == Standing.DOWN){
						
						Action action = new StandPlayerUpAction(ui.getSelectedPlayer());
						master.act(action);
						
					} else if (state.nextToEachOther(ui.getSelectedPlayer(), player)){
						
						Action action = new HandOffPlayerAction(ui.getSelectedPlayer(), player);
						master.act(action);
						
					} else {
						
						Action action = new PassPlayerAction(ui.getSelectedPlayer(), player);
						master.act(action);
					
					}
					
				}
				
			}
			
			
		} else {
			
			if(state.getGameStage() == GameStage.KICK_PLACEMENT){
				
				Action action = new PlaceBallAction(square);
				master.act(action);
				
			} else if (state.getGameStage() == GameStage.PLACE_BALL_ON_PLAYER){
				
				Action action = new PlaceBallOnPlayerAction(state.getPitch().getPlayerAt(square));
				master.act(action);
				
			} else {
			
				ui.setSelectedPlayer(state.getPitch().getPlayerAt(square));
			
			}
		}
		
	}

	public void clickOnReserves(GameMaster master, boolean home, int num, BloodBowlUI ui) {
		
		ui.setSelectedPlayer(null);

		if (num > 24){
			clickOnDead(master, home, num-24, ui);
			return;
		} else if (num > 16){
			clickOnKnockedOut(master, home, num-16, ui);
		}
		
		if (home){
			if (num < master.getState().getPitch().getHomeDogout().getReserves().size())
				ui.setSelectedPlayer(master.getState().getPitch().getHomeDogout().getReserves().get(num));
		} else {
			if (num < master.getState().getPitch().getAwayDogout().getReserves().size())
				ui.setSelectedPlayer(master.getState().getPitch().getAwayDogout().getReserves().get(num));
		}
		
	}

	private void clickOnKnockedOut(GameMaster master, boolean home, int num, BloodBowlUI ui) {
		
		if (home){
			if (num < master.getState().getPitch().getHomeDogout().getKnockedOut().size())
				ui.setSelectedPlayer(master.getState().getPitch().getHomeDogout().getKnockedOut().get(num));
		} else {
			if (num < master.getState().getPitch().getAwayDogout().getKnockedOut().size())
				ui.setSelectedPlayer(master.getState().getPitch().getAwayDogout().getKnockedOut().get(num));
		}
		
	}

	private void clickOnDead(GameMaster master, boolean home, int num, BloodBowlUI ui) {
		
		if (home){
			if (num < master.getState().getPitch().getHomeDogout().getDeadAndInjured().size())
				ui.setSelectedPlayer(master.getState().getPitch().getHomeDogout().getDeadAndInjured().get(num));
		} else {
			if (num < master.getState().getPitch().getAwayDogout().getDeadAndInjured().size())
				ui.setSelectedPlayer(master.getState().getPitch().getAwayDogout().getDeadAndInjured().get(num));
		}
		
	}
	
}
