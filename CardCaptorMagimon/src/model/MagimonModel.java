package model;

import java.util.ArrayList;

import org.json.simple.JSONObject;

import entity.Magimon;

public class MagimonModel extends Model {
	
	public Magimon getMagimon(String id){
		return new Magimon(super.getData("magimon/select/"+id));
	}
	
	public ArrayList<Magimon> getAllMagimon(){
		ArrayList<JSONObject> jorr = super.getArrayData("magimon/select_all");
		ArrayList<Magimon> result = new ArrayList<Magimon>();
		for(JSONObject jo:jorr){
			result.add(new Magimon(jo));
		}
		return result;
	}
}
