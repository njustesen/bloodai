package ai.actions;

import models.Player;

public class PlacePlayerInReservesAction extends PlayerAction {

	Player player;
	
	public PlacePlayerInReservesAction(Player player) {
		super();
		this.player = player;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
	
}
