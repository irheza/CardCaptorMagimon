package com.tekmob.cardcaptormagimon;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import org.json.JSONException;

import magicexception.InternetException;
import magimon.SpawnLocation;
import entity.Magimon;
import entity.SpawnPoint;
import model.MagimonModel;
import model.SpawnPointModel;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
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

public class Peta extends FragmentActivity implements LocationListener {
	private GoogleMap map;
	private LocationManager locationManager;
	private static final long MIN_TIME = 400;
	private static final float MIN_DISTANCE = 1000;
	// variabel battle_range untuk mengatur seberapa dekat magimon dengan player
	// sehingga dapat ditanggkap
	private static final double BATTLE_RANGE = 0.005;
	// posisi sekarang
	LatLng currentPosition = new LatLng(0, 0);
	final Context context = this;
	SpawnPointModel spawnModel = new SpawnPointModel();
	MagimonModel magimonModel = new MagimonModel();
	ArrayList<SpawnPoint> listSpawnPoint;
	HashMap<Marker, Magimon> tagMarkerWithMagimon;

	HashMap<Marker, SpawnPoint> tagMarkerWithSpawnPoint;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.activity_peta);
		map = ((SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map)).getMap();
		map.setMyLocationEnabled(true);

		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(
				LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DISTANCE, this); // You
																					// can
																					// also
																					// use
																					// LocationManager.GPS_PROVIDER
																					// and
																					// LocationManager.PASSIVE_PROVIDER
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
	public void onLocationChanged(Location location) {
		currentPosition = new LatLng(location.getLatitude(),
				location.getLongitude());
		SpawnLocation area = new SpawnLocation();
		for (SpawnPoint spawnPoint : listSpawnPoint) {
			LatLng spawnPointInLatLng = new LatLng(spawnPoint.getLatitude(),
					spawnPoint.getLongitude());
			if (isNear(currentPosition, spawnPointInLatLng)) {
				Toast.makeText(getBaseContext(),
						"Battle Magimon :" + spawnPoint.getMagimonID(),
						Toast.LENGTH_SHORT).show();
			}
		}
		/*
		 * if(isNear(currentPosition,area.getSpawn1().getPosition())) {
		 * Toast.makeText(getBaseContext(), "Battle Magimon 1",
		 * Toast.LENGTH_SHORT).show(); } else
		 * if(isNear(currentPosition,area.getSpawn2().getPosition())) {
		 * Toast.makeText(getBaseContext(), "Battle Magimon 2",
		 * Toast.LENGTH_SHORT).show(); } else
		 * if(isNear(currentPosition,area.getSpawn3().getPosition())) {
		 * Toast.makeText(getBaseContext(), "Battle Magimon 3",
		 * Toast.LENGTH_SHORT).show(); } else
		 * if(isNear(currentPosition,area.getSpawn4().getPosition())) {
		 * Toast.makeText(getBaseContext(), "Battle Magimon 4",
		 * Toast.LENGTH_SHORT).show(); } else
		 * if(isNear(currentPosition,area.getSpawn5().getPosition())) {
		 * Toast.makeText(getBaseContext(), "Battle Magimon 5",
		 * Toast.LENGTH_SHORT).show(); }
		 */
		CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(
				currentPosition, 17);
		map.animateCamera(cameraUpdate);
		locationManager.removeUpdates(this);

	}
	public void setSealingMagimon()
	{
		map.setOnMarkerClickListener(new OnMarkerClickListener()
        {

            @Override
            public boolean onMarkerClick(Marker marker) {
            	LatLng posisimagimon =marker.getPosition();
            	marker.showInfoWindow();
            	
        		Toast.makeText(getBaseContext(), "Klik poisi sekarang "+currentPosition.latitude+" "+currentPosition.longitude+"magimon "+ marker.getTitle() +" :"+posisimagimon.latitude+" "+posisimagimon.longitude, 
                        Toast.LENGTH_SHORT).show();
        		if(isNear(currentPosition,posisimagimon))
        		{
        			//cek waktu expired
        			SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        			Calendar rightNow = Calendar.getInstance();
        			//Date date = sdf.parse(dateInString);
					String expiredTime = tagMarkerWithSpawnPoint.get(marker).getTimeExpired();
					
        			Magimon battledMagimon = tagMarkerWithMagimon.get(marker);
        		    final String idMagimon = battledMagimon.getId();
        			Toast.makeText(getBaseContext(), "Magimon Battle "+battledMagimon.getName(), 
                            Toast.LENGTH_SHORT).show();
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
        		return true;
            }

        });

	}

	/*
	 * Fungsi untuk menempatkan marker magimon pada peta
	 */
	public void spawnMagimon() throws JSONException, InternetException
	{
		listSpawnPoint = spawnModel.getAllSpawnPoint();
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
	 */
	public boolean isNear(LatLng location1, LatLng location2) {
		if (Math.abs(location1.latitude - location2.latitude) < BATTLE_RANGE
				&& Math.abs(location1.longitude - location2.longitude) < BATTLE_RANGE) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}

	@Override
	public void onProviderEnabled(String provider) {
	}

	@Override
	public void onProviderDisabled(String provider) {
	}

}
