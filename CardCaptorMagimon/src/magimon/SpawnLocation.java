package magimon;

import com.google.android.gms.maps.model.LatLng;


public class SpawnLocation {
	static final LatLng posisi1 = new LatLng(-6.364100, 106.828544);
	static final LatLng posisi2 = new LatLng(-6.365355, 106.831881);
	static final LatLng posisi3 = new LatLng(-6.349702, 106.831344);
	static final LatLng posisi4 = new LatLng(-6.364374 ,106.832653);
	//Posisi rumah Rhesa
	static final LatLng posisi5 = new LatLng(-6.174827, 106.910913);
	static final Magimon rareLeana = new Magimon("1");	
	static final Magimon moemon = new Magimon("2");
	static final Magimon senpai = new Magimon("3");
	static final Magimon barel = new Magimon("4");
	Spawn area1;
	Spawn area2;
	Spawn area3;
	Spawn area4;
	Spawn area5;
	
	public SpawnLocation()
	{
		addMagimonToArea();	
	}
	
	public Spawn getSpawn1()
	{
		return area1;
	}
	
	public Spawn getSpawn2()
	{
		return area2;
	}
	
	public Spawn getSpawn3()
	{
		return area3;
	}
	
	public Spawn getSpawn4()
	{
		return area4;
	}
	
	public Spawn getSpawn5()
	{
		return area5;
	}
	
	public void addMagimonToArea()
	{
		area1 = new Spawn(posisi1,rareLeana);
		area2 = new Spawn(posisi2,moemon);
		area3 = new Spawn(posisi3,senpai);
		area4 = new Spawn(posisi4,barel);
		area5 = new Spawn(posisi5,moemon);
	}
}
