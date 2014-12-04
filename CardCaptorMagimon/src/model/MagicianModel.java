package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.json.*;

import android.os.AsyncTask;
import android.util.Log;

import entity.Magician;
import entity.SpawnPoint;


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
	
	public ArrayList<Magician> getAllMagician(){
		ArrayList<JSONObject> jorr = super.getArrayData("magician/select_all");
		ArrayList<Magician> result = new ArrayList<Magician>();
		for(JSONObject jo:jorr){
			result.add(new Magician(jo));
		}
		return result;
	}*/
	
	public int registerMagician(Magician magician){
		JSONObject jo = new JSONObject();
		try{
			jo.put("id", magician.getId());
			jo.put("username", magician.getUsername());
			jo.put("hp", magician.getHp());
			jo.put("exp", magician.getExp());
		}catch(Exception ex){
			return -1;
		}
		URL url;
		try {
			AsyncTask<String, String, String> asyncResult = new StringAsyncUploader().execute(super.URL_SERVER+"magician/insert", jo.toString());
			
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
