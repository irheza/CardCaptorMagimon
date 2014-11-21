package com.tekmob.cardcaptormagimon;

import magimon.SpawnLocation;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Context;
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
	private double ltnow=0;
	private double lngnow=0;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_peta);
	    map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
	    map.setMyLocationEnabled(true);
	    
	    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DISTANCE, this); //You can also use LocationManager.GPS_PROVIDER and LocationManager.PASSIVE_PROVIDER        
	    spawnMagimon();
	    map.setOnMarkerClickListener(new OnMarkerClickListener()
        {

            @Override
            public boolean onMarkerClick(Marker arg0) {
            	LatLng posisimarker =arg0.getPosition();
        		Toast.makeText(getBaseContext(), "Klik poisi sekarang "+ltnow+" "+lngnow+"magimon "+ arg0.getTitle() +" :"+posisimarker.latitude+" "+posisimarker.longitude, 
                        Toast.LENGTH_SHORT).show();
        		if(Math.abs(ltnow-posisimarker.latitude)<0.01
        				    	&& Math.abs(lngnow-posisimarker.longitude)<0.01 )
        		{
        			Toast.makeText(getBaseContext(), "Magimon Battle "+arg0.getTitle(), 
                            Toast.LENGTH_SHORT).show();
        		}
        		return true;
            }

        });       
	}

	@Override
	public void onLocationChanged(Location location) {
	    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
	    ltnow=location.getLatitude();
	    lngnow=location.getLongitude();
	    SpawnLocation area = new SpawnLocation();
	    if(Math.abs(latLng.latitude-area.getSpawn1().latitude)<0.01
	    	&& Math.abs(latLng.longitude-area.getSpawn1().longitude)<0.01 )
	    {
	    	 Toast.makeText(getBaseContext(), "Battle Magimon 1", 
	                    Toast.LENGTH_SHORT).show();
	    }
	    else if(Math.abs(latLng.latitude-area.getSpawn2().latitude)<0.01
		    	&& Math.abs(latLng.longitude-area.getSpawn2().longitude)<0.01 )
		{
		    	 Toast.makeText(getBaseContext(), "Battle Magimon 2", 
		                    Toast.LENGTH_SHORT).show();
		}
	    else if(Math.abs(latLng.latitude-area.getSpawn3().latitude)<0.01
		    	&& Math.abs(latLng.longitude-area.getSpawn3().longitude)<0.01 )
		{
		    	 Toast.makeText(getBaseContext(), "Battle Magimon 3", 
		                    Toast.LENGTH_SHORT).show();
		}
	    else if(Math.abs(latLng.latitude-area.getSpawn4().latitude)<0.01
		    	&& Math.abs(latLng.longitude-area.getSpawn4().longitude)<0.01 )
		{
		    	 Toast.makeText(getBaseContext(), "Battle Magimon Barel", 
		                    Toast.LENGTH_SHORT).show();
		}
	    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 17);
	    map.animateCamera(cameraUpdate);
	    locationManager.removeUpdates(this);
	    
	}
	
	public void spawnMagimon()
	{
		SpawnLocation area = new SpawnLocation();
		LatLng spawn1 = area.getSpawn1();
		Marker monster1 = map.addMarker(new MarkerOptions().position(spawn1).title("Rare Leana"));
		LatLng spawn2 = area.getSpawn2();
		Marker monster2 = map.addMarker(new MarkerOptions().position(spawn2).title("Moemon"));
		LatLng spawn3 = area.getSpawn3();
		Marker monster3 = map.addMarker(new MarkerOptions().position(spawn3).title("Senpai"));
		LatLng spawn4 = area.getSpawn4();
		Marker monster4 = map.addMarker(new MarkerOptions().position(spawn4).title("Barelmon"));
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) { }

	@Override
	public void onProviderEnabled(String provider) { }

	@Override
	public void onProviderDisabled(String provider) { }

}