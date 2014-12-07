package com.tekmob.cardcaptormagimon;

import java.util.ArrayList;

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
import entity.Battle;
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

	private final int baseExpMultiplication = 200;

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

			for (Magimon m : magimonSelf) {
				totalAtk += m.getAttack();
			}

			for (Magimon m : magimonEnemy) {
				totalDef += m.getDefense();
			}
			double damageRatio = 0;
			
			Log.w("self exp", ""+self.getExp());
			Log.w("enemy exp", ""+enemy.getExperience());
			
			if (self.getExp() < enemy.getExperience()) {
				damageRatio = (getCurrentLevel(self.getExp()) + 1)*1.0
						/ (getCurrentLevel(enemy.getExperience()) + 1);
			} else {
				damageRatio = (getCurrentLevel(enemy.getExperience()) + 1)*1.0
						/ (getCurrentLevel(self.getExp()) + 1);
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
			
			Battle battle = new Battle();
			battle.setAttackerID(self.getId());
			battle.setDefenderID(enemy.getUserID());
			
			
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

	/**
	 * Do some awesome calculation here.
	 * 
	 * @param currentLevel
	 * @return total experience to reach next level
	 */
	private int getNextLevelParam(int currentLevel) {
		int ret = (int) (baseExpMultiplication * Math.pow(currentLevel, 1.5));

		return ret;
	}

	/**
	 * To get current level
	 * 
	 * @param currentExp
	 * @return
	 */
	private int getCurrentLevel(int currentExp) {
		int ret = 1;
		int temp = currentExp;
		boolean notFound = true;

		while (notFound) {
			if (temp < getNextLevelParam(ret)) {
				notFound = false;
			} else {
				temp = temp - getNextLevelParam(ret);
				ret++;
			}
		}

		return ret;
	}
}
