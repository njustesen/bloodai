package models.humans;

import models.Player;
import models.Race;
import models.Skill;

public class HumanCatcher extends Player{

	public HumanCatcher(int number, String name, String teamName){
		super(Race.HUMANS, name, number, teamName);
		this.cost = 70000;
		this.MA = 8;
		this.ST = 2;
		this.AG = 3;
		this.AV = 7;
		this.skills.add(Skill.CATCH);
		this.skills.add(Skill.DODGE);
		this.title = "Catcher";
	}
}
