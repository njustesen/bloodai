package models.humans;

import models.Player;
import models.Race;

public class HumanLineman extends Player{

	public HumanLineman(int number, String name, String teamName){
		super(Race.HUMANS, name, number, teamName);
		this.cost = 50000;
		this.MA = 6;
		this.ST = 3;
		this.AG = 3;
		this.AV = 8;
		this.title = "Lineman";
	}
}
