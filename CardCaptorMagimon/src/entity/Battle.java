package entity;

import org.json.*;

public class Battle {
	private String id;
	private String attackerID;
	private String defenderID;
	private int exp;
	private int totalAttack;
	private int totalDefense;
	private String firstAttackerID;
	private String secondAttackerID;
	private String thirdAttackerID;
	private String firstDefenderID;
	private String secondDefenderID;
	private String thirdDefenderID;
	
	public Battle(String id, String attackerID, String defenderID, int exp,
			int totalAttack, int totalDefense, String firstAttackerID,
			String secondAttackerID, String thirdAttackerID,
			String firstDefenderID, String secondDefenderID,
			String thirdDefenderID) {
		super();
		this.id = id;
		this.attackerID = attackerID;
		this.defenderID = defenderID;
		this.exp = exp;
		this.totalAttack = totalAttack;
		this.totalDefense = totalDefense;
		this.firstAttackerID = firstAttackerID;
		this.secondAttackerID = secondAttackerID;
		this.thirdAttackerID = thirdAttackerID;
		this.firstDefenderID = firstDefenderID;
		this.secondDefenderID = secondDefenderID;
		this.thirdDefenderID = thirdDefenderID;
	}

	public Battle(JSONObject jo){
		try{
		this.id = (String) jo.get("id");
		this.attackerID = (String) jo.get("id_attacker");
		this.defenderID = (String) jo.get("id_defender");
		this.exp = Integer.parseInt((String) jo.get("exp"));
		this.totalAttack = Integer.parseInt((String) jo.get("total_atk"));
		this.totalDefense = Integer.parseInt((String) jo.get("total_def"));
		this.firstAttackerID = (String) jo.get("id_atk_1");
		this.firstAttackerID = (String) jo.get("id_atk_2");
		this.firstAttackerID = (String) jo.get("id_atk_3");
		
		this.firstDefenderID = (String) jo.get("id_atk_1");
		this.firstDefenderID = (String) jo.get("id_def_2");
		this.firstDefenderID = (String) jo.get("id_def_3");
		}catch(Exception e){
			
		}
		
	}
}

