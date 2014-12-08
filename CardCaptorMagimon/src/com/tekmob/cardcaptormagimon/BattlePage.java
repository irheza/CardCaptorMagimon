package com.tekmob.cardcaptormagimon;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import magicexception.InternetException;
import model.BattleModel;
import model.InternalStorage;
import model.MagicianModel;
import model.MagimonModel;
import model.PersonalMagimonModel;

import org.json.JSONException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import animation.WalkInOut;
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
	private TextView enemyTextview, selfTextview, enemyDefend, selfAttack, damage;
	private ImageView enemy1, enemy2, enemy3, self1, self2, self3;
	private ImageView sign_top, sign_bottom;
	private LinearLayout enemyParty, selfParty;

    private WalkInOut walkInOut;

	public final String ATK = "1";
	public final String DEF = "2";
	public final String NOPE = "0";
	
	private String selfUsername;
	private String enemyUsername;

	int totalAtk = 0;
	private int attackFromParty = 0;
	private int attackBonuses = 0;
	
	int totalDef = 0;
	private int defendFromParty = 0;
	private int defendBonuses = 0;

	int levelAtk = 0;
	int levelDef = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_battle_page);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		enemyTextview = (TextView) findViewById(R.id.enemyUsername);
		selfTextview = (TextView) findViewById(R.id.selfUsername);
		enemyDefend = (TextView) findViewById(R.id.enemyDefend);
		selfAttack = (TextView) findViewById(R.id.selfAttack);
		damage = (TextView) findViewById(R.id.damage);
		
		enemy1 = (ImageView) findViewById(R.id.enemy1);
		enemy2 = (ImageView) findViewById(R.id.enemy2);
		enemy3 = (ImageView) findViewById(R.id.enemy3);
		self1 = (ImageView) findViewById(R.id.self1);
		self2 = (ImageView) findViewById(R.id.self2);
		self3 = (ImageView) findViewById(R.id.self3);
		sign_top = (ImageView) findViewById(R.id.sign_top);
        sign_bottom = (ImageView) findViewById(R.id.sign_bottom);
		
		enemyParty = (LinearLayout) findViewById(R.id.enemyParty);
		selfParty = (LinearLayout) findViewById(R.id.selfParty);
		
		Bundle b = getIntent().getExtras();
		String id = b.get("id").toString();
		
		self = (Magician) getApplicationContext();
		try {
			enemy = new MagicianEnemy(magicianModel.getMagician(id));
			ArrayList<PersonalMagimon> pmSelf = personalMagModel
					.getPersonalMagimonByMagician(self.getId());
			ArrayList<PersonalMagimon> pmEnemy = personalMagModel
					.getPersonalMagimonByMagician(enemy.getUserID());
			
			selfUsername = self.getUsername();
			enemyUsername = enemy.getUsername();

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
			
			attackFromParty = countTotalAtkFromParty(magimonSelf);
			totalAtk+= attackFromParty;
			
			defendFromParty = countTotalDefFromParty(magimonEnemy);;
			totalDef+= defendFromParty;
			
			attackBonuses = totalAtk;
			totalAtk = totalAtk + experienceBonus(self.getExp());
			totalAtk = plusProbability(totalAtk);
			attackBonuses = totalAtk - attackBonuses;
			
			defendBonuses = totalDef;
			totalDef = totalDef + experienceBonus(enemy.getExperience());
			totalDef = plusProbability(totalDef);
			defendBonuses = totalDef - defendBonuses;
			
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

			
			String status = "";
			if (totalAtk > totalDef) {
				// menang
				self.setExp(self.getExp() + damageTotal);
				enemy.setExperience(enemy.getExperience() - damageTotal);
				sign_bottom.setImageResource(R.drawable.win);
				status = "WIN";
			} else if (totalAtk < totalDef) {
				// kalah
				self.setExp(self.getExp() - damageTotal);
				enemy.setExperience(enemy.getExperience() + damageTotal);
				sign_bottom.setImageResource(R.drawable.lose);
				status = "LOSE";
			} else {
				// seri
				self.setExp(self.getExp() + (int) (damageTotal * 0.3));
				enemy.setExperience(enemy.getExperience()
						+ (int) (damageTotal * 0.3));
				status = "DRAW";
			}

			if (self.getExp() < 0)
				self.setExp(0);
			if (enemy.getExperience() < 0)
				enemy.setExperience(0);
			
			Battle battle = new Battle();
			battle.setAttackerID(self.getId());
			battle.setDefenderID(enemy.getUserID());
			
			for(int i=0;i<magimonSelf.size();i++){
				if (i==0) battle.setFirstAttackerID(magimonSelf.get(i).getId());
				if (i==1) battle.setSecondAttackerID(magimonSelf.get(i).getId());
				if (i==2) battle.setThirdAttackerID(magimonSelf.get(i).getId());
			}
			for(int i=0;i<magimonEnemy.size();i++){
				if (i==0) battle.setFirstDefenderID(magimonEnemy.get(i).getId());
				if (i==1) battle.setSecondDefenderID(magimonEnemy.get(i).getId());
				if (i==2) battle.setThirdDefenderID(magimonEnemy.get(i).getId());
			}
			
			battle.setExp(damageTotal);
			battle.setStatus(status);
			battle.setTotalAttack(totalAtk);
			battle.setTotalDefense(totalDef);
			battle.setSeen(false);
			
			battleModel.insert(battle);
			
			magicianModel.update(self);
			magicianModel.update(enemy);
			
			cacheLastBattle(""+System.currentTimeMillis());

	        walkInOut = new WalkInOut();
			walkInOut.initSign(BattlePage.this, sign_top, sign_bottom);

			enemyTextview.setText(enemyUsername);
			selfTextview.setText(selfUsername);
			enemyDefend.setText("" + defendFromParty + " + " + defendBonuses);
			selfAttack.setText("" + attackFromParty + " + " + attackBonuses);
			damage.setText("Damage: "+damageTotal);
			
			for(int i=0;i<magimonSelf.size();i++){
				if (magimonSelf.get(i).getId().equals(String.valueOf(1))) {
					if (i==0) self1.setImageResource(R.drawable.neko);
					if (i==1) self2.setImageResource(R.drawable.neko);
					if (i==2) self3.setImageResource(R.drawable.neko);
				}
				else if (magimonSelf.get(i).getId().equals(String.valueOf(2))) {
					if (i==0) self1.setImageResource(R.drawable.egg);
					if (i==1) self2.setImageResource(R.drawable.egg);
					if (i==2) self3.setImageResource(R.drawable.egg);
				}
				else if (magimonSelf.get(i).getId().equals(String.valueOf(3))) {
					if (i==0) self1.setImageResource(R.drawable.dragon);
					if (i==1) self2.setImageResource(R.drawable.dragon);
					if (i==2) self3.setImageResource(R.drawable.dragon);
				}
			}
			for(int i=0;i<magimonEnemy.size();i++){
				if (magimonEnemy.get(i).getId().equals(String.valueOf(1))) {
					if (i==0) enemy1.setImageResource(R.drawable.neko);
					if (i==1) enemy2.setImageResource(R.drawable.neko);
					if (i==2) enemy3.setImageResource(R.drawable.neko);
				}
				else if (magimonEnemy.get(i).getId().equals(String.valueOf(2))) {
					if (i==0) enemy1.setImageResource(R.drawable.egg);
					if (i==1) enemy2.setImageResource(R.drawable.egg);
					if (i==2) enemy3.setImageResource(R.drawable.egg);
				}
				else if (magimonEnemy.get(i).getId().equals(String.valueOf(3))) {
					if (i==0) enemy1.setImageResource(R.drawable.dragon);
					if (i==1) enemy2.setImageResource(R.drawable.dragon);
					if (i==2) enemy3.setImageResource(R.drawable.dragon);
				}
			}
			
			walkInOut.startMoving();
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InternetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void finishItNow(){
		finish();
	}
	
	public void cacheLastBattle(String time) {
		// Save the list of entries to internal storage
		try {
			InternalStorage.writeObject(this, "LAST_BATTLE", time);
		} catch (IOException e) {
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
