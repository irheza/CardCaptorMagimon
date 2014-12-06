package model;

import java.util.ArrayList;

import magicexception.InternetException;

import org.json.JSONObject;

import entity.PersonalMagimon;

public class PersonalMagimonModel extends Model {
	public PersonalMagimon getPersonalMagimon(String id){
		return new PersonalMagimon(super.getData("personal_magimon/select/"+id));
	}
	
	public ArrayList<PersonalMagimon> getAllPersonalMagimon(){
		ArrayList<JSONObject> jorr = super.getArrayData("personal_magimon/select_all");
		ArrayList<PersonalMagimon> result = new ArrayList<PersonalMagimon>();
		for(JSONObject jo:jorr){
			result.add(new PersonalMagimon(jo));
		}
		return result;
	}
	
	public ArrayList<PersonalMagimon> getPersonalMagimonByMagician(String magicianID) throws InternetException{
		ArrayList<JSONObject> jorr = super.getArrayData("personal_magimon/select_by_magician/"+magicianID);
		ArrayList<PersonalMagimon> result = new ArrayList<PersonalMagimon>();
		if(jorr==null){
			throw new InternetException();
		}
		for(JSONObject jo:jorr){
			result.add(new PersonalMagimon(jo));
		}
		return result;
	}
	
	public boolean update(PersonalMagimon pm){
		String postStr = String.format("id=%s&id_magician=%s&id_magimon=%s&mode=%s", pm.getId(), pm.getMagicianID(), pm.getMagimonID(), pm.getMode());
		try{
			return super.post("personal_magimon/update", postStr);
		}catch(Exception e){
			return false;
		}
	}
}
