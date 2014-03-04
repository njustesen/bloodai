package models.orcs;

import models.Player;
import models.Race;

public class OrcLineman extends Player{

	public OrcLineman(int number, String name, String teamName){
		super(Race.ORCS, name, number, teamName);
		this.cost = 50000;
		this.MA = 5;
		this.ST = 3;
		this.AG = 3;
		this.AV = 9;
		this.title = "Lineman";
	}
}

