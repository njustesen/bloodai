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
import ai.actions.PlacePlayerInReservesAction;
import ai.actions.StandPlayerUpAction;
import ai.actions.StartGameAction;
import models.Dugout;
import models.GameStage;
import models.GameState;
import models.Player;
import models.Square;
import models.Standing;
import models.Team;
import game.GameMaster;

public class ActionHandler {

	public void clickOnStart(GameMaster master, BloodBowlUI ui) throws IllegalActionException{
		
		master.act(new StartGameAction());
		
	}
	
	public void clickOnSquare(GameMaster master, Square square, BloodBowlUI ui) throws IllegalActionException{

		GameState state = master.getState();
		
		if (state.getGameStage() == GameStage.KICK_PLACEMENT){
			ui.setTempBallPos(square);
			return;
		}
		
		if(ui.getSelectedPlayer() != null){
			
			if (state.getGameStage() == GameStage.KICKING_SETUP){
				
				if (state.getPitch().getPlayerAt(square) == null){
					
					if (ui.getSelectedPlayer().getTeamId() == state.getKickingTeam().getId()){
					
						Action action = new PlacePlayerAction(ui.getSelectedPlayer(), square);
						master.act(action);
						
					}
					
				} else if (state.getPitch().getPlayerAt(square).equals(ui.getSelectedPlayer())){
					
					ui.setSelectedPlayer(null);
					
				} else {
					
					ui.setSelectedPlayer(state.getPitch().getPlayerAt(square));
				}
				
			}
			
			if (state.getGameStage() == GameStage.RECEIVING_SETUP){
				
				if (state.getPitch().getPlayerAt(square) == null){
					
					if (ui.getSelectedPlayer().getTeamId() == state.getReceivingTeam().getId()){
						
						Action action = new PlacePlayerAction(ui.getSelectedPlayer(), square);
						master.act(action);
						
					}
					
				} else if (state.getPitch().getPlayerAt(square).equals(ui.getSelectedPlayer())){
					
					ui.setSelectedPlayer(null);
					
				} else {
					
					ui.setSelectedPlayer(state.getPitch().getPlayerAt(square));
				}
				
			}
			
			if (state.getGameStage() == GameStage.PERFECT_DEFENSE){
				
				if (state.getPitch().getPlayerAt(square) == null){
					
					if (ui.getSelectedPlayer().getTeamId() == state.getKickingTeam().getId()){
					
						Action action = new PlacePlayerAction(ui.getSelectedPlayer(), square);
						master.act(action);
						
					}
					
				} else if (state.getPitch().getPlayerAt(square).equals(ui.getSelectedPlayer())){
					
					ui.setSelectedPlayer(null);
					
				} else {
					
					ui.setSelectedPlayer(state.getPitch().getPlayerAt(square));
				}
				
			}
			
			if (state.getGameStage() == GameStage.HIGH_KICK){
				
				 if (state.getPitch().getPlayerAt(square).equals(ui.getSelectedPlayer())){
						
					ui.setSelectedPlayer(null);
						
				} else {
						
					ui.setSelectedPlayer(state.getPitch().getPlayerAt(square));
				
				}
				
			}
			
			if (state.getGameStage() == GameStage.QUICK_SNAP){
				
				if (state.getPitch().getPlayerAt(square) == null){
					
					if (ui.getSelectedPlayer().getTeamId() == state.getReceivingTeam().getId()){
					
						Action action = new MovePlayerAction(ui.getSelectedPlayer(), square);
						master.act(action);
						
					}
					
				} else if (state.getPitch().getPlayerAt(square).equals(ui.getSelectedPlayer())){
					
					ui.setSelectedPlayer(null);
					
				} else {
					
					ui.setSelectedPlayer(state.getPitch().getPlayerAt(square));
				}
				
			}
			
			// Normal turn
			if (state.getMovingTeam() == state.owner(ui.getSelectedPlayer())){
				
				Player player = state.getPitch().getPlayerAt(square);
				
				if (player == null){
					
					Action action = new MovePlayerAction(ui.getSelectedPlayer(), square);
					master.act(action);
					
				} else if (	state.onDifferentTeams(ui.getSelectedPlayer(), player) && 
							state.nextToEachOther(ui.getSelectedPlayer(), player) && 
							player.getPlayerStatus().getStanding() == Standing.UP){
					
					Action action = new BlockPlayerAction(ui.getSelectedPlayer(), player);
					master.act(action);
					
				} else {
					
					if (player == ui.getSelectedPlayer() && player.getPlayerStatus().getStanding() == Standing.DOWN){
						
						Action action = new StandPlayerUpAction(ui.getSelectedPlayer());
						master.act(action);
						
					} else if (	state.onSameTeam(ui.getSelectedPlayer(), player) && 
								state.nextToEachOther(ui.getSelectedPlayer(), player) && 
								state.getPitch().getBall().isUnderControl() && 
								state.getPitch().getBall().getSquare().equals(ui.getSelectedPlayer().getPosition())){
						
						Action action = new HandOffPlayerAction(ui.getSelectedPlayer(), player);
						master.act(action);
						
					} else if (	state.onSameTeam(ui.getSelectedPlayer(), player) && 
								state.getPitch().getBall().isUnderControl() && 
								state.getPitch().getBall().getSquare().equals(ui.getSelectedPlayer().getPosition()) && 
								player.getPlayerStatus().getStanding() == Standing.UP){
						
						Action action = new PassPlayerAction(ui.getSelectedPlayer(), player);
						master.act(action);
					
					} else {
						
						ui.setSelectedPlayer(player);
						
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

	public void clickOnReserves(GameMaster master, boolean home, int num, BloodBowlUI ui) throws IllegalActionException {
		
		GameState state = master.getState();
		
		if (num > 24){
			clickOnDead(master, home, num-24, ui);
			return;
		} else if (num > 16){
			clickOnKnockedOut(master, home, num-16, ui);
		}
		
		Team team = home ? state.getHomeTeam() : state.getAwayTeam();
		Dugout dugout = state.getPitch().getDogout(team);
		
		if (num < dugout.getReserves().size()){
			Player player = dugout.getReserves().get(num);
			if (player != ui.getSelectedPlayer())
				ui.setSelectedPlayer(player);
			else
				ui.setSelectedPlayer(null);
		} else if (state.getGameStage() == GameStage.KICKING_SETUP && 
					state.owner(ui.getSelectedPlayer()) == state.getKickingTeam() && 
					dugout == state.getPitch().getDogout(state.getKickingTeam())){
				
				master.act(new PlacePlayerInReservesAction(ui.getSelectedPlayer()));
				
		} else if (state.getGameStage() == GameStage.RECEIVING_SETUP && 
					ui.getSelectedPlayer().getTeamId() == state.getReceivingTeam().getId() && 
					dugout == state.getPitch().getDogout(state.getReceivingTeam())){
			
			master.act(new PlacePlayerInReservesAction(ui.getSelectedPlayer()));
		
		} else {
			ui.setSelectedPlayer(null);
		}
		
	}

	private void clickOnKnockedOut(GameMaster master, boolean home, int num, BloodBowlUI ui) {
		
		if (home){
			if (num < master.getState().getPitch().getHomeDogout().getKnockedOut().size()){
				ui.setSelectedPlayer(master.getState().getPitch().getHomeDogout().getKnockedOut().get(num));
			} else {
				ui.setSelectedPlayer(null);
			}
		} else {
			if (num < master.getState().getPitch().getAwayDogout().getKnockedOut().size()){
				ui.setSelectedPlayer(master.getState().getPitch().getAwayDogout().getKnockedOut().get(num));
			} else {
				ui.setSelectedPlayer(null);
			}
		}
		
	}

	private void clickOnDead(GameMaster master, boolean home, int num, BloodBowlUI ui) {
		
		if (home){
			if (num < master.getState().getPitch().getHomeDogout().getDeadAndInjured().size()){
				ui.setSelectedPlayer(master.getState().getPitch().getHomeDogout().getDeadAndInjured().get(num));
			} else {
				ui.setSelectedPlayer(null);
			}
		} else {
			if (num < master.getState().getPitch().getAwayDogout().getDeadAndInjured().size()){
				ui.setSelectedPlayer(master.getState().getPitch().getAwayDogout().getDeadAndInjured().get(num));
			} else {
				ui.setSelectedPlayer(null);
			}
		}
		
	}
	
}
