package model;

import java.util.ArrayList;

import magicexception.InternetException;

import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;
import entity.Battle;

public class BattleModel extends Model {
	
	public static boolean populated = false;
	
	public Battle getBattle(String id) throws InternetException{
		return new Battle(super.getData("battle/select/"+id));
	}
	
	public ArrayList<Battle> getAllBattle() throws InternetException{
		ArrayList<JSONObject> jorr = super.getArrayData("battle/select_all");
		ArrayList<Battle> result = new ArrayList<Battle>();
		if(jorr == null){
			throw new InternetException();
		}
		for(JSONObject jo:jorr){
			result.add(new Battle(jo));
		}
		return result;
	}
	
	public ArrayList<Battle> getBattleByAttacker(String idAttacker) throws InternetException{
		ArrayList<JSONObject> jorr = super.getArrayData("battle/select_by_attacker/"+idAttacker);
		ArrayList<Battle> result = new ArrayList<Battle>();
		if(jorr == null){
			throw new InternetException();
		}
		for(JSONObject jo:jorr){
			result.add(new Battle(jo));
		}
		return result;
	}
	
	public ArrayList<Battle> getBattleByDefender(String idDefender) throws InternetException{
		ArrayList<JSONObject> jorr = super.getArrayData("battle/select_by_defender/"+idDefender);
		ArrayList<Battle> result = new ArrayList<Battle>();
		if(jorr == null){
			throw new InternetException();
		}
		for(JSONObject jo:jorr){
			result.add(new Battle(jo));
		}
		return result;
	}
	
	public ArrayList<Battle> getUnseenBattle(String idMagician) throws InternetException{
		ArrayList<JSONObject> jorr = super.getArrayData("battle/select_unseen/"+idMagician);
		ArrayList<Battle> result = new ArrayList<Battle>();
		if(jorr == null){
			throw new InternetException();
		}
		for(JSONObject jo:jorr){
			result.add(new Battle(jo));
		}
		BattleModel.populated = true;
		return result;
	}
	
	public void insert(Battle battle){
		String data = String.format(
				"id=%s&" +
				"id_attacker=%s&" +
				"id_defender=%s&" +
				"exp=%s&" +
				"total_atk=%s&" +
				"total_def=%s&" +
				"id_atk_1=%s&" +
				"id_atk_2=%s&" +
				"id_atk_3=%s&" +
				"id_def_1=%s&" +
				"id_def_2=%s&" +
				"id_def_3=%s&" +
				"seen=%s&" +
				"status=%s", battle.getId(),
				battle.getAttackerID(),
				battle.getDefenderID(),
				battle.getExp(),
				battle.getTotalAttack(),
				battle.getTotalDefense(),
				battle.getFirstAttackerID(),
				battle.getSecondAttackerID(),
				battle.getThirdAttackerID(),
				battle.getFirstDefenderID(),
				battle.getSecondDefenderID(),
				battle.getThirdDefenderID(),
				((battle.isSeen()) ? "1" : "0"),
				battle.getStatus());
		try {
			AsyncTask<String, String, String> asyncResult = new StringAsyncUploader().execute(super.URL_SERVER+"battle/insert", data);
			
			String result = asyncResult.get();
			Log.w("", "string berhasil di POST");
		}catch(Exception e){
			
		}
	}
	
}
