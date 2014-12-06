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
import android.view.WindowManager;
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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.activity_battle_page);
		
		Bundle b = getIntent().getExtras();
		String id = b.get("id").toString();
		
		self = (Magician) getApplicationContext();
		try {
			enemy = new MagicianEnemy(magicianModel.getMagician(id));
			ArrayList<PersonalMagimon> pmSelf = personalMagModel.getPersonalMagimonByMagician(self.getId());
			ArrayList<PersonalMagimon> pmEnemy = personalMagModel.getPersonalMagimonByMagician(enemy.getUserID());
			
			ArrayList<Magimon> magimonSelf = new ArrayList<Magimon>(); //attack
			ArrayList<Magimon> magimonEnemy = new ArrayList<Magimon>(); //defense
			
			for(PersonalMagimon pm : pmSelf){
				if(pm.getMode().equals(ATK)){
					magimonSelf.add(magimonModel.getMagimon(pm.getMagimonID()));
				}
			}
			for(PersonalMagimon pm : pmEnemy){
				if(pm.getMode().equals(DEF)){
					magimonEnemy.add(magimonModel.getMagimon(pm.getMagimonID()));
				}
			}
			
			for(Magimon m:magimonSelf){
				totalAtk += m.getAttack();
			}
			
			for(Magimon m:magimonEnemy){
				totalDef += m.getDefense();
			}
			TextView userText = (TextView) findViewById(R.id.textView);
			userText.setText("atk: "+totalAtk+"\ndef: "+totalDef);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InternetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
