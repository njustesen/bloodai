package ui;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import view.BBImage;

public class ImageLoader {

	//action panel
	public static BBImage actionPanel = new BBImage("action_panel/action_panel.png");
	
	public static BBImage moveAvail = new BBImage("action_panel/move_avail.png");
	public static BBImage blockAvail = new BBImage("action_panel/block_avail.png");
	public static BBImage blitzAvail = new BBImage("action_panel/blitz_avail.png");
	public static BBImage foulAvail = new BBImage("action_panel/foul_avail.png");
	public static BBImage passAvail = new BBImage("action_panel/pass_avail.png");
	public static BBImage handoffAvail = new BBImage("action_panel/handoff_avail.png");
	
	public static BBImage moveHover = new BBImage("action_panel/move_hover.png");
	public static BBImage blockHover = new BBImage("action_panel/block_hover.png");
	public static BBImage blitzHover = new BBImage("action_panel/blitz_hover.png");
	public static BBImage foulHover = new BBImage("action_panel/foul_hover.png");
	public static BBImage passHover = new BBImage("action_panel/pass_hover.png");
	public static BBImage handoffHover = new BBImage("action_panel/handoff_hover.png");
	
	public static BBImage moveUnavail = new BBImage("action_panel/move_unavail.png");
	public static BBImage blockUnavail = new BBImage("action_panel/block_unavail.png");
	public static BBImage blitzUnavail = new BBImage("action_panel/blitz_unavail.png");
	public static BBImage foulUnavail = new BBImage("action_panel/foul_unavail.png");
	public static BBImage passUnavail = new BBImage("action_panel/pass_unavail.png");
	public static BBImage handoffUnavail = new BBImage("action_panel/handoff_unavail.png");
	
	public static BBImage moveActive = new BBImage("action_panel/move_active.png");
	public static BBImage blockActive = new BBImage("action_panel/block_active.png");
	public static BBImage blitzActive = new BBImage("action_panel/blitz_active.png");
	public static BBImage foulActive = new BBImage("action_panel/foul_active.png");
	public static BBImage passActive = new BBImage("action_panel/pass_active.png");
	public static BBImage handoffActive = new BBImage("action_panel/handoff_active.png");
	
	//buttons
	public static BBImage startButton = new BBImage("buttons/start.png");
	public static BBImage startButtonHover = new BBImage("buttons/start_hover.png");
	public static BBImage headsButton = new BBImage("buttons/heads.png");
	public static BBImage headsButtonHover = new BBImage("buttons/heads_hover.png");
	public static BBImage tailsButton = new BBImage("buttons/tails.png");
	public static BBImage tailsButtonHover = new BBImage("buttons/tails_hover.png");
	public static BBImage kickButton = new BBImage("buttons/kick.png");
	public static BBImage kickButtonHover = new BBImage("buttons/kick_hover.png");
	public static BBImage receiveButton = new BBImage("buttons/receive.png");
	public static BBImage receiveButtonHover = new BBImage("buttons/receive_hover.png");
	
	//menu
	public static BBImage startMenu = new BBImage("menu/start_menu.png");
		
	//ball
	public static BBImage ball = new BBImage("pitch/ball.png");
	
	//dice-images
	public static BBImage bbDice1 = new BBImage("dice/bbdiceSkull.png");
	public static BBImage bbDice2 = new BBImage("dice/bbdiceBothDown.png");
	public static BBImage bbDice3 = new BBImage("dice/bbdiceArrow.png");
	public static BBImage bbDice4 = new BBImage("dice/bbdiceEx.png");
	public static BBImage bbDice5 = new BBImage("dice/bbdiceBoom.png");
	public static BBImage dice1 = new BBImage("dice/dice1.png");
	public static BBImage dice2 = new BBImage("dice/dice2.png");
	public static BBImage dice3 = new BBImage("dice/dice3.png");
	public static BBImage dice4 = new BBImage("dice/dice4.png");
	public static BBImage dice5 = new BBImage("dice/dice5.png");
	public static BBImage dice6 = new BBImage("dice/dice6.png");
	
	//pitch
	public static BBImage pitch = new BBImage("pitch/pitch.jpg");	
	
	//orcs
	public static BBImage olineman = new BBImage("players/orcs/olineman.png");
	public static BBImage oblackorc = new BBImage("players/orcs/oblackorc.png");
	public static BBImage othrower = new BBImage("players/orcs/othrower.png");
	public static BBImage oblitzer = new BBImage("players/orcs/oblitzer.png");
	
	//humans
	public static BBImage hlineman = new BBImage("players/humans/hlineman.png");
	public static BBImage hthrower = new BBImage("players/humans/hthrower.png");
	public static BBImage hcatcher = new BBImage("players/humans/hcatcher1b.png");
	public static BBImage hblitzer = new BBImage("players/humans/hblitzer.png");
	
	// Board
	public static BBImage scoreboardframe = new BBImage("board/frame.png");
	public static BBImage weather = new BBImage();
	public static BBImage background = new BBImage("background/background.png");
	public static Map<Integer, BBImage> scores = new HashMap<Integer, BBImage>();
	static{
		for(int i = 0; i < 10; i++)
			scores.put(i, new BBImage("board/score/"+i+".png"));
    }
	public static Map<Character, BBImage> letters = new HashMap<Character, BBImage>();
	private static String chars = "ABCDEFGHIJKLMNOPQRSTUVXYZW";
	static{
		for(Character c : chars.toCharArray())
			letters.put(c, new BBImage("board/letters/"+c+".png"));
		
		for(int i = 0; i < 10; i++){
			char c = String.valueOf(i).charAt(0);
			letters.put(c, new BBImage("board/letters/"+c+"i.png"));
		}
		
		letters.put(new Character('.'), new BBImage("board/letters/PERIOD.png"));
		letters.put(new Character(','), new BBImage("board/letters/COMMA.png"));
		letters.put(new Character('/'), new BBImage("board/letters/SLASH.png"));
		letters.put(new Character('-'), new BBImage("board/letters/DASH.png"));
		letters.put(new Character('_'), new BBImage("board/letters/UNDER.png"));
		letters.put(new Character(' '), new BBImage("board/letters/SPACE.png"));
		
    }
	
	public static Map<String, BBImage> icons = new HashMap<String, BBImage>();
	static{
		icons.put("reroll", new BBImage("board/letters/reroll.png"));
		icons.put("cheerleader", new BBImage("board/letters/cheerleader.png"));
		icons.put("apothecary", new BBImage("board/letters/apothecary.png"));
		icons.put("apothecary_used", new BBImage("board/letters/apothecary_used.png"));
		icons.put("fame", new BBImage("board/letters/fame.png"));
		icons.put("coach", new BBImage("board/letters/coach.png"));
    }
	
}
