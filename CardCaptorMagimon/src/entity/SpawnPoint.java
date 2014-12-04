package entity;

import org.json.*;

public class SpawnPoint {
	private String id;
	private Double latitude;
	private Double longitude;
	private String timeExpired;
	private String magimonID;
	
	public SpawnPoint(String id, Double latitude, Double longitude,
			String timeExpired, String magimonID) {
		super();
		this.id = id;
		this.latitude = latitude;
		this.longitude = longitude;
		this.timeExpired = timeExpired;
		this.magimonID = magimonID;
	}
	
	public SpawnPoint(JSONObject jo){
		try{
		this.id = (String) jo.get("id");
		this.latitude = Double.parseDouble((String) jo.get("latitude"));
		this.longitude = Double.parseDouble((String) jo.get("longitude"));
		this.timeExpired = (String) jo.get("time_expired");
		this.magimonID = (String) jo.get("magimon_id");
		}catch(Exception e){
			
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public String getTimeExpired() {
		return timeExpired;
	}

	public void setTimeExpired(String timeExpired) {
		this.timeExpired = timeExpired;
	}

	public String getMagimonID() {
		return magimonID;
	}

	public void setMagimonID(String magimonID) {
		this.magimonID = magimonID;
	}
	
	
}
