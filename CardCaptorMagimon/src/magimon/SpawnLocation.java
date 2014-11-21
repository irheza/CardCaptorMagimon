package magimon;

import com.google.android.gms.maps.model.LatLng;

public class SpawnLocation {
	static final LatLng spawn1 = new LatLng(-6.364100, 106.828544);
	static final LatLng spawn2 = new LatLng(-6.365355, 106.831881);
	static final LatLng spawn3 = new LatLng(-6.349702, 106.831344);
	static final LatLng spawn4 = new LatLng(-6.364374 ,106.832653);
	
	public LatLng getSpawn1()
	{
		return spawn1;
	}
	
	public LatLng getSpawn2()
	{
		return spawn2;
	}
	
	public LatLng getSpawn3()
	{
		return spawn3;
	}
	
	public LatLng getSpawn4()
	{
		return spawn4;
	}
}