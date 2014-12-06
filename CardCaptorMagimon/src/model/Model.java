package model;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.net.HttpURLConnection;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.*;

import android.os.AsyncTask;
import android.util.Log;

public class Model {
	// pake garis miring di akhirnya
	public final String URL_SERVER = "http://johanes.tigasekawansolution.com/index.php/";

	public JSONObject getData(String subURL) {
		JSONObject jo = null;
		try {
			AsyncTask<String, String, String> asyncResult = new StringAsyncDownloader()
					.execute(URL_SERVER + subURL);
			String result = asyncResult.get();
			if(result!=null){
			jo = new JSONObject(result);
			}
			System.out.println("sampe sini");
		}catch(JSONException je){
			System.out.println(je.toString());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.toString());
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.toString());
		}

		return jo;
	}
	
	public ArrayList<JSONObject> getArrayDataNew(String subURL) throws JSONException
	{
		AsyncTask<String, String, String> asyncResult = new StringAsyncDownloader()
		.execute(URL_SERVER + subURL);
		String result="";
		try {
			result = asyncResult.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONArray ja = new JSONArray(result);
		return convertJAtoArrJO(ja);
	}

	public ArrayList<JSONObject> getArrayData(String subURL) {
		try {
			AsyncTask<String, String, String> asyncResult = new StringAsyncDownloader()
			.execute(URL_SERVER + subURL);
			String result = asyncResult.get();
			JSONArray ja = new JSONArray(result);

			return convertJAtoArrJO(ja);
		} catch (Exception e) {
			System.out.println("parsing json array gagal");
			System.out.print("e stacktrace: " + e.toString());
		}

		return null;
	}

	private ArrayList<JSONObject> convertJAtoArrJO(JSONArray ja) {
		ArrayList<JSONObject> arr = new ArrayList<JSONObject>();
		for (int i = 0; i < ja.length(); i++) {
			try {
				arr.add((JSONObject) ja.getJSONObject(i));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return arr;
	}

	public boolean post(String targetSubURL, String data) throws Exception {
		String url = URL_SERVER + targetSubURL;
		AsyncTask<String, String, String> asyncResult = new StringAsyncUploader().execute(url, data);
		String response = asyncResult.get();
		// print result
		/*if (response.toString().contains("Response Code : 200")) {
			return true;
		} else {
			return false;
		}*/
		if(response.equalsIgnoreCase("200"))
		{
			return true;
		}
		else
		{
			return false;
		}
	}


}

class StringAsyncUploader extends AsyncTask<String, String, String> {
	private final String USER_AGENT = "Mozilla/5.0";

	protected String doInBackground(String... args) {
		if (args[0] == null && args[1] == null) {
			return null;
		}
		String urlKonek = args[0];
		String data = args[1];

		URL obj;
		try {
			obj = new URL(urlKonek);

			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			// add reuqest header
			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", USER_AGENT);
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

			String urlParameters = data;

			// Send post request
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(urlParameters);
			wr.flush();
			wr.close();

			int responseCode = con.getResponseCode();
			System.out.println("\nSending 'POST' request to URL : " + urlKonek);
			System.out.println("Post parameters : " + urlParameters);
			System.out.println("Response Code : " + responseCode);

			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			//return response.toString();
			return String.valueOf(responseCode);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
}

class StringAsyncDownloader extends AsyncTask<String, String, String> {
	protected String doInBackground(String... args) {
		if (args[0] == null) {
			return null;
		}
		String urlKonek = args[0];
		StringBuilder builder = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(urlKonek);
		HttpResponse response;
		try {
			response = client.execute(httpGet);

			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (statusCode == 200) {
				HttpEntity entity = response.getEntity();
				InputStream content = entity.getContent();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(content));
				String line;
				while ((line = reader.readLine()) != null) {
					builder.append(line);
				}
			}
			String result = builder.toString();
			if (result == null || result.equals("[]") || result.equals("false")) {
				return null;
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
