package models;

public enum Skill{

	SURE_HANDS("sure hands"),
	PASS("pass"),
	BLOCK("block"),
	CATCH("catch"),
	DODGE("dodge"), 
	TACKLE("tackle"),
	SPRINT("sprint"),
	KICK("kick"), 
	SURE_FEET("sure feet"), 
	MIGHTY_BLOW("mighty blow"), 
	DISTURBING_PRESENCE("disturbing presence");
	
	private final String name;
	
	Skill(String name) {
		this.name = name;
    }
	
	public String getName() { return name; }
	
}
