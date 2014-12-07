package com.tekmob.cardcaptormagimon;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import magicexception.InternetException;
import magimon.SpawnLocation;
import model.InternalStorage;
import model.MagimonModel;
import model.SpawnPointModel;

import org.json.JSONException;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import entity.Magician;
import entity.Magimon;
import entity.SpawnPoint;
public class Peta extends FragmentActivity implements LocationListener {
	private GoogleMap map;
	private LocationManager locationManager;
	private static final long MIN_TIME = 400;
	private static final float MIN_DISTANCE = 1000;
	//variabel battle_range untuk mengatur seberapa dekat magimon dengan player
	//sehingga dapat ditanggkap
	private static final double BATTLE_RANGE = 0.005;
	final int SPAWN_LIMIT_NUMBER=9;
	//posisi sekarang
	LatLng currentPosition = new LatLng(0,0);
	final Context context = this;
	SpawnPointModel spawnModel = new SpawnPointModel();
	MagimonModel magimonModel = new MagimonModel();
	ArrayList<SpawnPoint> listSpawnPoint;
	HashMap<Marker, Magimon> tagMarkerWithMagimon;
	HashMap<Marker, SpawnPoint> tagMarkerWithSpawnPoint;
	Magician magician;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	    setContentView(R.layout.activity_peta);
	    magician = (Magician) getApplicationContext();
	    map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
	    map.setMyLocationEnabled(true);
	    
	    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DISTANCE, this); //You can also use LocationManager.GPS_PROVIDER and LocationManager.PASSIVE_PROVIDER        
	    try {
			spawnMagimon();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InternetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    setSealingMagimon();
	           
	}

	@Override
    public void onBackPressed() {
    	super.onBackPressed();
    	overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
    }
	
	@Override
	public void onLocationChanged(Location location) {
	    currentPosition = new LatLng(location.getLatitude(), location.getLongitude());
	    SpawnLocation area = new SpawnLocation();
	    for(SpawnPoint spawnPoint : listSpawnPoint)
	    {
	    	LatLng spawnPointInLatLng = new LatLng(spawnPoint.getLatitude(),spawnPoint.getLongitude());
	    	/*if(isNear(currentPosition,spawnPointInLatLng))
	    	{
	    		 Toast.makeText(getBaseContext(), "Battle Magimon :"+spawnPoint.getMagimonID(), 
		                    Toast.LENGTH_SHORT).show();
	    	}*/
	    }
	    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(currentPosition, 17);
	    map.animateCamera(cameraUpdate);
	    locationManager.removeUpdates(this);
	    
	}
	
	public boolean checkLastSeal(){
		String strLastSeal;
		try {
			strLastSeal = (String) InternalStorage.readObject(this,"LAST_SEAL");
			long lastSeal = Long.parseLong(strLastSeal);
			long now = System.currentTimeMillis();
			
			if(now-lastSeal > 3600000){
				showMarukAlert(strLastSeal);
				return false;
			}else{
				return true;
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	public void setSealingMagimon()
	{
		map.setOnMarkerClickListener(new OnMarkerClickListener()
        {

            @Override
            public boolean onMarkerClick(Marker marker) {
            	Log.w("","masuk on marker click");
            	LatLng posisimagimon =marker.getPosition();
            	marker.showInfoWindow();
            	
        		if(isNear(currentPosition,posisimagimon))
        		{
        			if(!isPartyMaxed())
        			{
        				Log.w("", "party is not maxed");
        				if(!checkExpiredMarker(marker))
            			{
        					if(checkLastSeal()){
        						Log.w("LAST SEAL", "TRUE");
        						showSealingAlert(marker);
        					}else{
        						Log.w("LAST SEAL", "FALSE");
        					}
            			}
            			else
            			{
            				Toast.makeText(getBaseContext(), "Magimon already expired", 
                                    Toast.LENGTH_SHORT).show();
            				marker.remove();
            			}
        			}
        			else
        			{
        				Toast.makeText(getBaseContext(), "Deck Full, please remove a Magimon", 
                                Toast.LENGTH_SHORT).show();
        				
        			}
        			
        			
        		}else{
        			Toast.makeText(getBaseContext(), "Magimon is too far, get close", 
                            Toast.LENGTH_SHORT).show();
        			Log.w("", "is not near");
        		}
        		return true;
            }

			

        });
	}
	private void showMarukAlert(String lastSeal) {
		Date date = new Date(lastSeal);
		String lastBTL = date.toLocaleString();
		AlertDialog.Builder alert = new AlertDialog.Builder(this);

		alert.setTitle("Cool down!");
		alert.setMessage("Dear magician, your last sealing is at "
				+ lastBTL
				+ " and it is not too long ago. Chill, you can train your Magimon first and train yourself first. Please come back later!");

		alert.setPositiveButton("Okay",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,
							int whichButton) {
					}
				});

		alert.show();
	}
	/*
	 * Fungsi untuk menempatkan marker magimon pada peta
	 * 
	 */
	public void spawnMagimon() throws JSONException, InternetException
	{
		listSpawnPoint = spawnModel.getSoonestSpawnPoint(SPAWN_LIMIT_NUMBER);
		//listSpawnPoint = spawnModel.getAllSpawnPoint();
		tagMarkerWithMagimon = new HashMap<Marker,Magimon>();
		tagMarkerWithSpawnPoint = new HashMap<Marker,SpawnPoint>();
		for(SpawnPoint spawnPoint : listSpawnPoint)
		{
			LatLng latlng = new LatLng(spawnPoint.getLatitude(),spawnPoint.getLongitude());
			Magimon magimon= magimonModel.getMagimon(spawnPoint.getMagimonID());
			System.out.println(magimon.getName());
			Marker magimonMark = map.addMarker(new MarkerOptions().position(latlng).title(magimon.getName()).snippet("Expired Time "+spawnPoint.getTimeExpired()));
			tagMarkerWithMagimon.put(magimonMark, magimon);
			tagMarkerWithSpawnPoint.put(magimonMark, spawnPoint);
		}

	}

	
	/*
	 * Fungsi untuk membandingkan kedekatan antara 2 posisi
	 * 
	 */
	public boolean isNear(LatLng location1, LatLng location2)
	{
		 if(Math.abs(location1.latitude-location2.latitude)<BATTLE_RANGE
			    	&& Math.abs(location1.longitude-location2.longitude)<BATTLE_RANGE )
		 {
			 return true;
		 }
		 else
		 {
			 return false;
		 }
	}
	
	public boolean checkExpiredMarker(Marker marker)
	{
		//cek waktu expired
		Date nowDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
		Date expiredDate = new Date();
		String expiredTime = tagMarkerWithSpawnPoint.get(marker).getTimeExpired();
		try {
				expiredDate = sdf.parse(expiredTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if(nowDate.after(expiredDate))
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	public void showSealingAlert(Marker marker)
	{
		Magimon battledMagimon = tagMarkerWithMagimon.get(marker);
	    final String idMagimon = battledMagimon.getId();
		/*Toast.makeText(getBaseContext(), "Magimon Battle "+battledMagimon.getName(), 
                Toast.LENGTH_SHORT).show();*/
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("Sealing Magimon");
		builder.setMessage(marker.getTitle()).setCancelable(true);
		
		builder.setPositiveButton("Seal", new DialogInterface.OnClickListener()
		{
			
			public void onClick(DialogInterface dialog, int id)
			{
				
				Intent intent = new Intent(getBaseContext(), SealingPage.class);
    			intent.putExtra("magimon", idMagimon);  
    			startActivity(intent);
    			finish();
			}
		});
		builder.setNegativeButton("Nope", new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int id)
			{
				dialog.cancel();
				
			}
		});
		AlertDialog alertdialog = builder.create();
		alertdialog.show();
		//Magimon battleMonster = new Magimon(marker.getId());
	}
	
	
	
	public boolean isPartyMaxed()
	{
		if(magician.getPersonalMagimon().size()>=6)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) { }

	@Override
	public void onProviderEnabled(String provider) { }

	@Override
	public void onProviderDisabled(String provider) { }

}
