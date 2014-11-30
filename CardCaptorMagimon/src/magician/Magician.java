package magician;

import java.util.ArrayList;

import android.app.Application;
import android.content.Context;
import android.telephony.TelephonyManager;
import magimon.Magimon;

public class Magician extends Application {

	private ArrayList<Magimon> personalMagimon= new ArrayList<Magimon>();
	private String userID;
	private int level;
	private double experience;
	private boolean isSet=false;
	
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
	
	public boolean isSet()
	{
		return isSet;
	}
	
	public void doneSetting()
	{
		isSet = true;
	}
	
}
