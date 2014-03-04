package models.orcs;

import models.Player;
import models.Race;

public class OrcBlackOrc extends Player{

	public OrcBlackOrc(int number, String name, String teamName){
		super(Race.ORCS, name, number, teamName);
		this.cost = 80000;
		this.MA = 4;
		this.ST = 4;
		this.AG = 2;
		this.AV = 9;
		this.title = "Black Orc";
	}
}
