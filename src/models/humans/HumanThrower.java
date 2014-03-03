package models.humans;

import models.Player;
import models.Race;
import models.Skill;

public class HumanThrower extends Player{
	
	public HumanThrower(int number, String name, String teamName){
		super(Race.HUMANS, name, number, teamName);
		this.cost = 70000;
		this.MA = 6;
		this.ST = 3;
		this.AG = 3;
		this.AV = 8;
		this.skills.add(Skill.SURE_HANDS);
		this.skills.add(Skill.PASS);
		this.title = "Thrower";
	}
}
