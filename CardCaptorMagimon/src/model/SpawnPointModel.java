package model;

import java.util.ArrayList;

import magicexception.InternetException;

import org.json.*;

import entity.Magimon;
import entity.SpawnPoint;

public class SpawnPointModel extends Model {
	public SpawnPoint getSpawnPoint(String id) throws InternetException{
		return new SpawnPoint(super.getData("spawn_point/select/"+id));
	}
	
	public ArrayList<SpawnPoint> getAllSpawnPoint() throws JSONException{
		ArrayList<JSONObject> jorr = super.getArrayData("spawn_point/select_all");
		ArrayList<SpawnPoint> result = new ArrayList<SpawnPoint>();
		for(JSONObject jo:jorr){
			result.add(new SpawnPoint(jo));
		}
		return result;
	}
	
	public ArrayList<SpawnPoint> getSoonestSpawnPoint(int limit){
		ArrayList<JSONObject> jorr = super.getArrayData("spawn_point/select_soonest/"+limit);
		ArrayList<SpawnPoint> result = new ArrayList<SpawnPoint>();
		for(JSONObject jo:jorr){
			result.add(new SpawnPoint(jo));
		}
		return result;
	}
}
