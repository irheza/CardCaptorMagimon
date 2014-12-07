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
	private String seen;

	public Battle(){}
	
	public Battle(String id, String attackerID, String defenderID, int exp,
			int totalAttack, int totalDefense, String firstAttackerID,
			String secondAttackerID, String thirdAttackerID,
			String firstDefenderID, String secondDefenderID,
			String thirdDefenderID) {
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
		this.seen = "0";  
	}

	public Battle(JSONObject jo) {
		try {
			this.id = (String) jo.get("id");
			this.attackerID = (String) jo.get("id_attacker");
			this.defenderID = (String) jo.get("id_defender");
			this.exp = Integer.parseInt((String) jo.get("exp"));
			this.totalAttack = Integer.parseInt((String) jo.get("total_atk"));
			this.totalDefense = Integer.parseInt((String) jo.get("total_def"));
			this.firstAttackerID = (String) jo.get("id_atk_1");
			this.secondAttackerID = (String) jo.get("id_atk_2");
			this.thirdAttackerID = (String) jo.get("id_atk_3");

			this.firstDefenderID = (String) jo.get("id_def_1");
			this.secondDefenderID = (String) jo.get("id_def_2");
			this.thirdDefenderID = (String) jo.get("id_def_3");
			
			this.seen = (String) jo.get("seen");
		} catch (Exception e) {

		}
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAttackerID() {
		return attackerID;
	}

	public void setAttackerID(String attackerID) {
		this.attackerID = attackerID;
	}

	public String getDefenderID() {
		return defenderID;
	}

	public void setDefenderID(String defenderID) {
		this.defenderID = defenderID;
	}

	public int getExp() {
		return exp;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}

	public int getTotalAttack() {
		return totalAttack;
	}

	public void setTotalAttack(int totalAttack) {
		this.totalAttack = totalAttack;
	}

	public int getTotalDefense() {
		return totalDefense;
	}

	public void setTotalDefense(int totalDefense) {
		this.totalDefense = totalDefense;
	}

	public String getFirstAttackerID() {
		return firstAttackerID;
	}

	public void setFirstAttackerID(String firstAttackerID) {
		this.firstAttackerID = firstAttackerID;
	}

	public String getSecondAttackerID() {
		return secondAttackerID;
	}

	public void setSecondAttackerID(String secondAttackerID) {
		this.secondAttackerID = secondAttackerID;
	}

	public String getThirdAttackerID() {
		return thirdAttackerID;
	}

	public void setThirdAttackerID(String thirdAttackerID) {
		this.thirdAttackerID = thirdAttackerID;
	}

	public String getFirstDefenderID() {
		return firstDefenderID;
	}

	public void setFirstDefenderID(String firstDefenderID) {
		this.firstDefenderID = firstDefenderID;
	}

	public String getSecondDefenderID() {
		return secondDefenderID;
	}

	public void setSecondDefenderID(String secondDefenderID) {
		this.secondDefenderID = secondDefenderID;
	}

	public String getThirdDefenderID() {
		return thirdDefenderID;
	}

	public void setThirdDefenderID(String thirdDefenderID) {
		this.thirdDefenderID = thirdDefenderID;
	}
	
	public boolean isSeen(){
		if(this.seen.equals("1")){
			return true;
		}else{
			return false;
		}
	}

	public String toString(){
		return String.format(
						"id=%s\n" +
						"attacker_id=%s\n" +
						"defender_id=%s\n" +
						"exp=%s\n" +
						"totalAtk=%s\n" +
						"totalDef=%s\n" +
						"1AtkID=%s\n" +
						"2AtkID=%s\n" +
						"3AtkID=%s\n" +
						"1DefID=%s\n" +
						"2DefID=%s\n" +
						"3DefID=%s\n",
						getId(),
						getAttackerID(),
						getDefenderID(),
						getExp(),
						getTotalAttack(),
						getTotalDefense(),
						getFirstAttackerID(),
						getSecondAttackerID(),
						getThirdAttackerID(),
						getFirstDefenderID(),
						getSecondDefenderID(),
						getThirdDefenderID());
	}
}
