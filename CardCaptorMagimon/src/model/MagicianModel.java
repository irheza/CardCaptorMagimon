package model;

import java.net.URL;
import java.util.ArrayList;

import magicexception.InternetException;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;
import entity.Magician;
import entity.MagicianEnemy;


public class MagicianModel extends Model{
	
/*	public static void main(String[] arhs){
		MagicianModel mm = new MagicianModel();
	//	Magician m = new Magician("idku123123123", "jojoeffe", 0, 0);
	//	mm.registerMagician(m);
	}*/
	
/*	public MagicianModel(){
		//super();
	}*/
	
	
/*	public Magician getMagician(String id){
		return new Magician(super.getData("magician/select/"+id));
	}
	*/
	public ArrayList<MagicianEnemy> getAllEnemyMagician(String own_id) throws JSONException{
		//ArrayList<JSONObject> jorr = super.getArrayData("magician/select_all");
		
		ArrayList<JSONObject> jorr = super.getArrayDataNew("magician/select_all");
		ArrayList<MagicianEnemy> result = new ArrayList<MagicianEnemy>();
		for(JSONObject jo:jorr){
			if(!jo.get("id").toString().equalsIgnoreCase(own_id))
			{
				result.add(new MagicianEnemy(jo));
			}
		}
		return result;
	}
	public boolean checkMagician(String id) throws InternetException {
		JSONObject jo = super.getData("magician/select/" + id);
		if (jo == null) {
			throw new InternetException();
		}
		try {
			String idku = (String) jo.get("id");
			if (idku == null || idku.equals("")) {
				return false;
			}
		} catch (Exception cce) {
			return false;
		}

		return true;
	}
	public JSONObject getMagician(String id) throws InternetException {
		return super.getData("magician/select/" + id);
	}
	public int registerMagician(Magician magician){
		String data = String.format("id=%s&username=%s&hp=%s&exp=%s",
				magician.getId(),
				magician.getUsername(),
				magician.getHp(),
				magician.getExp());
		URL url;
		try {
			AsyncTask<String, String, String> asyncResult = new StringAsyncUploader().execute(super.URL_SERVER+"magician/insert", data);
			
			String result = asyncResult.get();
			Log.w("", "string berhasil di POST");
		}catch(Exception e){
			
		}
		return 1;
		
	}
	
	public boolean update(Magician m){
		String postStr = String.format("id=%s&username=%s&hp=%s&exp=%s", m.getId(), m.getUsername(), m.getHp(), m.getExp());
		try{
			return super.post("magician/update", postStr);
		}catch(Exception e){
			return false;
		}
	}
	
	
}
