package model;

import java.util.ArrayList;
import org.json.simple.JSONObject;
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
	
	public ArrayList<PersonalMagimon> getPersonalMagimonByMagician(String magicianID){
		ArrayList<JSONObject> jorr = super.getArrayData("personal_magimon/select_by_magician/"+magicianID);
		ArrayList<PersonalMagimon> result = new ArrayList<PersonalMagimon>();
		for(JSONObject jo:jorr){
			result.add(new PersonalMagimon(jo));
		}
		return result;
	}
}
