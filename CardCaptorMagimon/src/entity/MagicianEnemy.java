package entity;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import magimon.Magimon;

public class MagicianEnemy {
	private ArrayList<Magimon> personalMagimon= new ArrayList<Magimon>();
	private String userID;
	private String username;

	//private int level;
	private double experience;
	
	//For Testing Purpose Only if JSONObject parameter is not working
	public MagicianEnemy(String userID)
	{
		this.userID = userID;
	}
	
	public MagicianEnemy(JSONObject jo) throws JSONException {
		this.userID = (String) jo.get("id");
		this.username = (String) jo.get("username");
		//this.level = Integer.parseInt((String) jo.get("level"));
		this.experience = Integer.parseInt((String) jo.get("exp"));
	}
	
	public void setUserID(String userID)
	{
		this.userID = userID; 
	}
	
	public String getUserID()
	{
		return userID;
	}
	
	public ArrayList<Magimon> getListMagimon()
	{
		return personalMagimon;
	}
	
	
	public void setListMagimon(ArrayList<Magimon> listMagimon)
	{
		personalMagimon = listMagimon;
	}
	
	public void addMagimon(Magimon newMagimon)
	{
		personalMagimon.add(newMagimon);
	}
	
	public boolean isPartyFull()
	{
		return personalMagimon.size()==6;
	}
}