package com.tekmob.cardcaptormagimon;

import java.util.ArrayList;
import java.util.Random;

import magicexception.InternetException;
import model.BattleModel;
import model.MagicianModel;
import model.MagimonModel;
import model.PersonalMagimonModel;

import org.json.JSONException;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import entity.Magician;
import entity.MagicianEnemy;
import entity.Magimon;
import entity.PersonalMagimon;

public class BattlePage extends Activity {
	BattleModel battleModel = new BattleModel();
	MagicianModel magicianModel = new MagicianModel();
	MagimonModel magimonModel = new MagimonModel();
	PersonalMagimonModel personalMagModel = new PersonalMagimonModel();

	MagicianEnemy enemy;
	Magician self;

	public final String ATK = "1";
	public final String DEF = "2";
	public final String NOPE = "0";

	int totalAtk = 0;
	int totalDef = 0;

	int levelAtk = 0;
	int levelDef = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_battle_page);

		Bundle b = getIntent().getExtras();
		String id = b.get("id").toString();

		self = (Magician) getApplicationContext();
		try {
			enemy = new MagicianEnemy(magicianModel.getMagician(id));
			ArrayList<PersonalMagimon> pmSelf = personalMagModel
					.getPersonalMagimonByMagician(self.getId());
			ArrayList<PersonalMagimon> pmEnemy = personalMagModel
					.getPersonalMagimonByMagician(enemy.getUserID());

			ArrayList<Magimon> magimonSelf = new ArrayList<Magimon>(); // attack
			ArrayList<Magimon> magimonEnemy = new ArrayList<Magimon>(); // defense

			for (PersonalMagimon pm : pmSelf) {
				if (pm.getMode().equals(ATK)) {
					magimonSelf.add(magimonModel.getMagimon(pm.getMagimonID()));
				}
			}
			for (PersonalMagimon pm : pmEnemy) {
				if (pm.getMode().equals(DEF)) {
					magimonEnemy
							.add(magimonModel.getMagimon(pm.getMagimonID()));
				}
			}
			totalAtk+= countTotalAtkFromParty(magimonSelf);
			totalDef+= countTotalDefFromParty(magimonEnemy);
			totalAtk = totalAtk + experienceBonus(self.getExp());
			totalAtk = plusProbability(totalAtk);
			totalDef = totalDef + experienceBonus(enemy.getExperience());
			totalDef = plusProbability(totalDef);
			double damageRatio = 0;
			
			Log.w("self exp", ""+self.getExp());
			Log.w("enemy exp", ""+enemy.getExperience());
			
			int[] selfy = getCurrentLevel(self.getExp());
			int[] enemye = getCurrentLevel(enemy.getExperience());
			
			if (self.getExp() < enemy.getExperience()) {
				damageRatio = (selfy[0] + 1)*1.0
						/ (enemye[0] + 1);
			} else {
				damageRatio = (enemye[0] + 1)*1.0
						/ (selfy[0] + 1);
			}
			
			Log.w("damageRatio", ""+damageRatio);

			int damageTotal = (int) ((totalAtk + totalDef) * damageRatio);
			
			Log.w("damageTotal", ""+damageTotal);
			int gainSelf = 0;
			int gainEnemy = 0;

			if (totalAtk > totalDef) {
				// menang
				self.setExp(self.getExp() + damageTotal);
				enemy.setExperience(enemy.getExperience() - damageTotal);
			} else if (totalAtk < totalDef) {
				// kalah
				self.setExp(self.getExp() - damageTotal);
				enemy.setExperience(enemy.getExperience() + damageTotal);
			} else {
				// seri
				self.setExp(self.getExp() + (int) (damageTotal * 0.3));
				enemy.setExperience(enemy.getExperience()
						+ (int) (damageTotal * 0.3));
			}

			if (self.getExp() < 0)
				self.setExp(0);
			if (enemy.getExperience() < 0)
				enemy.setExperience(0);

			magicianModel.update(self);
			magicianModel.update(enemy);

			TextView userText = (TextView) findViewById(R.id.textView);
			userText.setText("atk: " + totalAtk + "\ndef: " + totalDef
					+ "\ndamageTotal: " + damageTotal);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InternetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
    public void onBackPressed() {
    	super.onBackPressed();
    	overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
    }
	
	/**
	 * Do some awesome calculation here.
	 * 
	 * @param currentLevel
	 * @return total experience to reach next level
	 */
	private int getNextLevelParam(int currentLevel) {
		int ret = (int)(200*(Math.pow(currentLevel, 1.5)));

		return ret;
	}

	/**
	 * To get current level
	 * 
	 * @param currentExp
	 * @return
	 */
	private int[] getCurrentLevel(int currentExp) {
    	int[] ret = new int[2];
    	ret[0] = 1;
    	int temp = currentExp;
    	boolean notFound = true;
    	
    	while (notFound) {
    		int temp_exp_needed = (getNextLevelParam(ret[0])-getNextLevelParam(ret[0]-1));
    		if (temp < temp_exp_needed) {
    			notFound = false;
    		} else {
    			temp = temp - temp_exp_needed;
    			ret[0]++;
    		}
    	}
    	ret[1] = temp;
    	return ret;
    }

	public int experienceBonus(int exp)
	{
		return (int)Math.round(exp/10);
	}
	
	public int plusProbability(int totalAtkOrDef)
	{
		Random r = new Random();
		double randomValue = 0.5 + (1.5 - 0.5) * r.nextDouble();
		return (int)Math.round(totalAtkOrDef*randomValue);
	}
	
	public int countTotalAtkFromParty(ArrayList<Magimon> party)
	{
		int totalAtk=0;
		for (Magimon m : party) {
			totalAtk += m.getAttack();
		}
		return totalAtk;
	}
	
	public int countTotalDefFromParty(ArrayList<Magimon> party)
	{
		int totalDef=0;
		for (Magimon m : party) {
			totalDef += m.getDefense();
		}
		return totalDef;
	}
}
