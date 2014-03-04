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
		this.skills.add(Skill.DODGE);
		this.skills.add(Skill.CATCH);
		this.skills.add(Skill.TACKLE);
		this.skills.add(Skill.SPRINT);
		this.skills.add(Skill.KICK);
		this.skills.add(Skill.SURE_FEET);
		this.skills.add(Skill.MIGHTY_BLOW);
		this.skills.add(Skill.DISTURBING_PRESENCE);
		this.title = "Thrower";
	}
}
