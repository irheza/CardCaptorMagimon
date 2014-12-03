package entity;

import org.json.simple.JSONObject;

public class Magimon {
	private String id;
	private String name;
	private int attack;
	private int defense;
	private String image;
	
	public Magimon(String id, String name, int attack, int defense, String image) {
		super();
		this.id = id;
		this.name = name;
		this.attack = attack;
		this.defense = defense;
		this.image = image;
	}

	public Magimon(String name, int attack, int defense, String image) {
		super();
		this.name = name;
		this.attack = attack;
		this.defense = defense;
		this.image = image;
	}
	
	public Magimon(JSONObject jo){
		this.id = (String) jo.get("id");
		this.name = (String) jo.get("name");
		this.attack = Integer.parseInt((String)jo.get("attack"));
		this.defense = Integer.parseInt((String)jo.get("defense"));
		this.image = (String) jo.get("image");
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}

	public int getDefense() {
		return defense;
	}

	public void setDefense(int defense) {
		this.defense = defense;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	
}
