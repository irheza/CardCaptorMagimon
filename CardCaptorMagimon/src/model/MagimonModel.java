package model;

import java.util.ArrayList;

import magicexception.InternetException;

import org.json.JSONObject;

import entity.Magimon;
import entity.PersonalMagimon;

public class MagimonModel extends Model {
	
	public Magimon getMagimon(String id) throws InternetException{
		return new Magimon(super.getData("magimon/select/"+id));
	}
	
	public ArrayList<Magimon> getAllMagimon() throws InternetException{
		ArrayList<JSONObject> jorr = super.getArrayData("magimon/select_all");
		ArrayList<Magimon> result = new ArrayList<Magimon>();
		if(jorr == null){
			throw new InternetException();
		}
		for(JSONObject jo:jorr){
			result.add(new Magimon(jo));
		}
		return result;
	}
	
	public ArrayList<Magimon> getSeveralMagimon(ArrayList<PersonalMagimon> ids) throws InternetException{
		ArrayList<Magimon> result = new ArrayList<Magimon>();
		for(PersonalMagimon id:ids){
			result.add(getMagimon(id.getMagimonID()));
		}
		return result;
	}
}
