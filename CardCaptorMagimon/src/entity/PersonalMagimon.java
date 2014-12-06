package entity;

import java.io.Serializable;

import org.json.*;

public class PersonalMagimon implements Serializable{
	private String id;
	private String magicianID;
	private String magimonID;
	private String mode;
	
	public PersonalMagimon(String id, String magicianID, String magimonID,
			String mode) {
		super();
		this.id = id;
		this.magicianID = magicianID;
		this.magimonID = magimonID;
		this.mode = mode;
	}
	
	public PersonalMagimon(JSONObject jo){
		try{
			this.id = (String) jo.get("id");
			this.magicianID = (String) jo.get("id_magician");
			this.magimonID = (String) jo.get("id_magimon");
			this.mode = (String) jo.get("mode");
		}catch(Exception e){
			
		}
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

	public String getMagimonID() {
		return magimonID;
	}

	public void setMagimonID(String magimonID) {
		this.magimonID = magimonID;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}
	
	
}
