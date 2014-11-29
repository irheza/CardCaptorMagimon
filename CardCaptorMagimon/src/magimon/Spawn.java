package magimon;


import com.google.android.gms.maps.model.LatLng;

public class Spawn {
	private LatLng position;
	private Magimon magimon;
	
	public Spawn(LatLng position, Magimon magimon)
	{
		this.position = position;
		this.magimon = magimon;
	}
	public LatLng getPosition()
	{
		return position;
	}
	public Magimon getMagimon()
	{
		return magimon;
	}
}
