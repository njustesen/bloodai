package ai.actions;

import models.Player;

public class EndPlayerTurnAction extends PlayerAction {

	private Player player;

	public EndPlayerTurnAction(Player player) {
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
