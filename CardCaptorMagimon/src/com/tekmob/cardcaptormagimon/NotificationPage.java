package com.tekmob.cardcaptormagimon;

import java.util.List;

import magicexception.InternetException;
import model.BattleModel;
import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import entity.Battle;
import entity.Magician;

public class NotificationPage extends Activity {

	ListView listNotif;
	BattleModel battleModel = new BattleModel();
	Magician magician;
	BinderData adapter = null;
	List<Battle> battleDataCollection;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.w("","sebelum content view");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notification_page);
		ActionBar actionBar = getActionBar();
		actionBar.hide();
		Log.w("","Setelah content view");
		
		magician = (Magician) getApplicationContext();
		try {
			battleDataCollection = battleModel
					.getUnseenBattle(magician.getId());
			
		} catch (InternetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	protected void onResume(){
		super.onResume();
		BinderData bindingData = new BinderData(this, battleDataCollection);
		
		listNotif = (ListView) findViewById(R.id.listNotif);

		Log.w("BEFORE", "<<------------- Before SetAdapter-------------->>");

		listNotif.setAdapter(bindingData);

		Log.w("AFTER", "<<------------- After SetAdapter-------------->>");
	}

}
