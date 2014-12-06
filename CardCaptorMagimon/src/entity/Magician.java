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
	private ArrayList<Magimon> ms = new ArrayList<Magimon>();

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

	public ArrayList<Magimon> getMagimonCache() {
		return ms;
	}

	public void setMagimonCache(ArrayList<Magimon> ms) {
		this.ms = ms;
	}
	
	

}
