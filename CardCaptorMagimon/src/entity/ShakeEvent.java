package entity;

import org.json.simple.JSONObject;

public class ShakeEvent {
	private String id;
	private String magicianID;
	private int expGained;
	
	public ShakeEvent(String id, String magicianID, int expGained) {
		super();
		this.id = id;
		this.magicianID = magicianID;
		this.expGained = expGained;
	}
	
	public ShakeEvent(JSONObject jo){
		this.id = (String) jo.get("id");
		this.magicianID = (String) jo.get("id_magician");
		this.expGained = Integer.parseInt((String) jo.get("exp_gained"));
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMagicianID() {
		return magicianID;
	}

	public void setMagicianID(String magicianID) {
		this.magicianID = magicianID;
	}

	public int getExpGained() {
		return expGained;
	}

	public void setExpGained(int expGained) {
		this.expGained = expGained;
	}
	
	
}
