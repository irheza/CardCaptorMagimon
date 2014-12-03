package model;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class Model {
	//pake garis miring di akhirnya
	final String URL_SERVER = "http://johanes.tigasekawansolution.com/index.php/";
	private final String USER_AGENT = "Mozilla/5.0";
	
	public JSONObject getData(String subURL){
		try {
			String urlKonek = URL_SERVER+subURL;
			URL url = new URL(urlKonek);
			URLConnection urlConnection = url.openConnection();
			InputStream is = urlConnection.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			
			int numCharsRead;
			char[] charArray = new char[1024];
			StringBuffer sb = new StringBuffer();
			while ((numCharsRead = isr.read(charArray)) > 0) {
				sb.append(charArray, 0, numCharsRead);
			}
			String result = sb.toString();
			
			if(result == null || result.equals("[]") || result.equals("false")){
				return null;
			}
			
			JSONParser parser = new JSONParser();
			
			JSONObject jo = (JSONObject) parser.parse(result);
			
			return jo;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("kena exception");
			System.out.print("parse ex stacktrace: "+e.toString());
		} catch (Exception e){
			System.out.println("parsing json gagal");
			System.out.print("e stacktrace: "+e.toString());
		}
		
		return null;
	}
	
	public ArrayList<JSONObject> getArrayData(String subURL){
		try {
			String urlKonek = URL_SERVER+subURL;
			URL url = new URL(urlKonek);
			URLConnection urlConnection = url.openConnection();
			InputStream is = urlConnection.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			
			int numCharsRead;
			char[] charArray = new char[1024];
			StringBuffer sb = new StringBuffer();
			while ((numCharsRead = isr.read(charArray)) > 0) {
				sb.append(charArray, 0, numCharsRead);
			}
			String result = sb.toString();			
			JSONParser parser = new JSONParser();			
			JSONArray ja = (JSONArray) parser.parse(result);
			
			return convertJAtoArrJO(ja);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("kena exception");
			System.out.print("parse ex stacktrace: "+e.toString());
		} catch (Exception e){
			System.out.println("parsing json gagal");
			System.out.print("e stacktrace: "+e.toString());
		}
		
		return null;
	}
	
	private ArrayList<JSONObject> convertJAtoArrJO(JSONArray ja){
		ArrayList<JSONObject> arr = new ArrayList<JSONObject>();
		for(int i=0;i<ja.size();i++){
			arr.add((JSONObject)ja.get(i));
		}
		return arr;
	}
	
	public boolean post(String targetSubURL, String data) throws Exception{
		String url = URL_SERVER+targetSubURL;
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
 
		//add reuqest header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
 
		String urlParameters = "id_magician=123&exp_gained=450";
 
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();
 
		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Post parameters : " + urlParameters);
		System.out.println("Response Code : " + responseCode);
 
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
 
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
 
		//print result
		System.out.println(response.toString());
		if(response.toString().contains("Response Code : 200")){
			return true;
		}else{
			return false;
		}
	}
	
	
}
