package magimon;

public class Magimon {
	public String id;
	public String name;
	public double attack;
	public double defense;
	public int sealingCount;
	public String image;
	
	
	public Magimon(String id)
	{
		this.id=id;
	}
	
	public Magimon(String id, String name, double attack, double defense,int sealingCount, String image)
	{
		this.id=id;
		this.name = name;
		this.attack = attack;
		this.defense = defense;
		this.sealingCount = sealingCount;
		this.image = image;
	}
}
