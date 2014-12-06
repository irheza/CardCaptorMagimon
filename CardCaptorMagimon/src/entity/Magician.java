package entity;

import java.io.Serializable;
import java.util.ArrayList;

import android.app.Application;

public class Magician extends Application implements Serializable {
	private String id;
	private String username = "";
	private int hp;
	private int exp;
	private boolean isSet = false;
	private ArrayList<PersonalMagimon> pms = new ArrayList<PersonalMagimon>();

	//public Magician() {
		//super();
	//}

/*	public Magician(String id, String username, int hp, int exp) {
		//super();
		this.id = id;
		this.username = username;
		this.hp = hp;
		this.exp = exp;
	}*/
/*
	public Magician(JSONObject jo) {
		try {
			this.id = (String) jo.get("id");
			this.username = (String) jo.get("username");
			this.hp = Integer.parseInt((String) jo.get("hp"));
			this.exp = Integer.parseInt((String) jo.get("exp"));
		} catch (Exception e) {

		}
	}*/
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public int getExp() {
		return exp;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}

	public boolean isSet() {
		return isSet;
	}

	public void setSet(boolean isSet) {
		this.isSet = isSet;
	}
	
	public String toString(){
		return String.format(
				"id: %s\n" +
				"username: %s\n" +
				"exp: %s\n" +
				"jumlah magimon: %s\n", 
				getId(),
				getUsername(),
				getExp(),
				pms.size());
	}

	public ArrayList<PersonalMagimon> getPersonalMagimon() {
		return pms;
	}

	public void setPersonalMagimon(ArrayList<PersonalMagimon> pms) {
		this.pms = pms;
	}
	
	public void addPersonalMagimon(PersonalMagimon pm)
	{
		this.pms.add(pm);
	}
	
	

}
