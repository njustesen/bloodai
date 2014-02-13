package models;

import java.util.ArrayList;
import java.util.Arrays;

import models.humans.HumanBlitzer;
import models.humans.HumanCatcher;
import models.humans.HumanLineman;
import models.humans.HumanThrower;
import models.orcs.OrcBlackOrc;
import models.orcs.OrcBlitzer;
import models.orcs.OrcLineman;
import models.orcs.OrcThrower;

public class TeamFactory {

	public static Team getHumanTeam() {
		
		String teamId = "reg3489rh3frut";
		
		Player p1 = new HumanThrower(1, teamId); 
		Player p2 = new HumanBlitzer(2, teamId); 
		Player p3 = new HumanBlitzer(3, teamId); 
		Player p4 = new HumanBlitzer(4, teamId); 
		Player p5 = new HumanBlitzer(5, teamId); 
		Player p6 = new HumanCatcher(6, teamId); 
		Player p7 = new HumanCatcher(7, teamId); 
		Player p8 = new HumanLineman(8, teamId); 
		Player p9 = new HumanLineman(9, teamId); 
		Player p10 = new HumanLineman(10, teamId); 
		Player p11 = new HumanLineman(11, teamId); 
		Player p12 = new HumanLineman(12, teamId); 
		ArrayList<Player> players = new ArrayList<Player>(
				Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12)
			);
		

		return new Team(teamId, players, 4, 5, 0, true, "Reikland Reavers");
	}

	public static Team getOrcTeam() {
		
		String teamId = "3irjwingu32332r";
		
		Player p1 = new OrcThrower(1, teamId); 
		Player p2 = new OrcBlackOrc(2, teamId); 
		Player p3 = new OrcBlackOrc(3, teamId); 
		Player p4 = new OrcBlitzer(4, teamId); 
		Player p5 = new OrcBlitzer(5, teamId); 
		Player p6 = new OrcLineman(6, teamId); 
		Player p7 = new OrcLineman(7, teamId); 
		Player p8 = new OrcLineman(8, teamId); 
		Player p9 = new OrcLineman(9, teamId); 
		Player p10 = new OrcLineman(10, teamId); 
		Player p11 = new OrcLineman(11, teamId); 
		Player p12 = new OrcLineman(12, teamId); 
		ArrayList<Player> players = new ArrayList<Player>(
				Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12)
			);
		

		return new Team(teamId, players, 3, 6, 0, false, "Gouged Eye");
	}
	
}