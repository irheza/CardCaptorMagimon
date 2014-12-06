package model;

import java.util.ArrayList;

import magicexception.InternetException;

import org.json.JSONObject;

import entity.ShakeEvent;

public class ShakeEventModel extends Model{
	public ShakeEvent getShakeEvent(String id) throws InternetException{
		return new ShakeEvent(super.getData("shake_event/select/"+id));
	}
	
	public ArrayList<ShakeEvent> getAllShakeEvent(){
		ArrayList<JSONObject> jorr = super.getArrayData("shake_event/select_all");
		ArrayList<ShakeEvent> result = new ArrayList<ShakeEvent>();
		for(JSONObject jo:jorr){
			result.add(new ShakeEvent(jo));
		}
		return result;
	}
	
	public boolean insertShakeEvent(ShakeEvent se) throws InternetException{
		String postStr = String.format("id_magician=%s&exp_gained=%s", se.getMagicianID(), se.getExpGained());
		try{
			return super.post("shake_event/insert", postStr);
		}catch(Exception e){
			return false;
		}
	}
}
