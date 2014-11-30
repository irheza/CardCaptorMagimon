package com.tekmob.cardcaptormagimon;

import magimon.Magimon;
import magimon.SpawnLocation;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;
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
	//variabel battle_range untuk mengatur seberapa dekat magimon dengan player
	//sehingga dapat ditanggkap
	private static final double BATTLE_RANGE = 0.005;
	//posisi sekarang
	LatLng currentPosition = new LatLng(0,0);
	final Context context = this;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_peta);
	    map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
	    map.setMyLocationEnabled(true);
	    
	    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DISTANCE, this); //You can also use LocationManager.GPS_PROVIDER and LocationManager.PASSIVE_PROVIDER        
	    spawnMagimon();
	    setSealingMagimon();
	           
	}

	@Override
	public void onLocationChanged(Location location) {
	    currentPosition = new LatLng(location.getLatitude(), location.getLongitude());
	    SpawnLocation area = new SpawnLocation();
	    if(isNear(currentPosition,area.getSpawn1().getPosition()))
	    {
	    	 Toast.makeText(getBaseContext(), "Battle Magimon 1", 
	                    Toast.LENGTH_SHORT).show();
	    }
	    else if(isNear(currentPosition,area.getSpawn2().getPosition()))
	    {
	    	 Toast.makeText(getBaseContext(), "Battle Magimon 2", 
	                    Toast.LENGTH_SHORT).show();
	    }
	    else if(isNear(currentPosition,area.getSpawn3().getPosition()))
	    {
	    	 Toast.makeText(getBaseContext(), "Battle Magimon 3", 
	                    Toast.LENGTH_SHORT).show();
	    }
	    else if(isNear(currentPosition,area.getSpawn4().getPosition()))
	    {
	    	 Toast.makeText(getBaseContext(), "Battle Magimon 4", 
	                    Toast.LENGTH_SHORT).show();
	    }
	    else if(isNear(currentPosition,area.getSpawn5().getPosition()))
	    {
	    	 Toast.makeText(getBaseContext(), "Battle Magimon 5", 
	                    Toast.LENGTH_SHORT).show();
	    }
	    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(currentPosition, 17);
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
            	
        		Toast.makeText(getBaseContext(), "Klik poisi sekarang "+currentPosition.latitude+" "+currentPosition.longitude+"magimon "+ marker.getTitle() +" :"+posisimagimon.latitude+" "+posisimagimon.longitude, 
                        Toast.LENGTH_SHORT).show();
        		if(isNear(currentPosition,posisimagimon))
        		{
     
        		    final String idMagimon = marker.getTitle();
        			Toast.makeText(getBaseContext(), "Magimon Battle "+marker.getTitle(), 
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
	 * 
	 */
	public void spawnMagimon()
	{
		SpawnLocation area = new SpawnLocation();
		LatLng spawn1 = area.getSpawn1().getPosition();
		Marker monster1 = map.addMarker(new MarkerOptions().position(spawn1).title(area.getSpawn1().getMagimon().id));
		LatLng spawn2 = area.getSpawn2().getPosition();
		Marker monster2 = map.addMarker(new MarkerOptions().position(spawn2).title(area.getSpawn2().getMagimon().id));
		LatLng spawn3 = area.getSpawn3().getPosition();
		Marker monster3 = map.addMarker(new MarkerOptions().position(spawn3).title(area.getSpawn3().getMagimon().id));
		LatLng spawn4 = area.getSpawn4().getPosition();
		Marker monster4 = map.addMarker(new MarkerOptions().position(spawn4).title(area.getSpawn4().getMagimon().id));
		LatLng spawn5 = area.getSpawn5().getPosition();
		Marker monster5 = map.addMarker(new MarkerOptions().position(spawn5).title(area.getSpawn5().getMagimon().id));
		
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
	
	

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) { }

	@Override
	public void onProviderEnabled(String provider) { }

	@Override
	public void onProviderDisabled(String provider) { }

}
