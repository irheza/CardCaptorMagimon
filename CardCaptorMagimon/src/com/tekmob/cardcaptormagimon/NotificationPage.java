package com.tekmob.cardcaptormagimon;

import java.util.List;

import magicexception.InternetException;
import model.BattleModel;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.activity_notification_page);
		
		Log.w("","Setelah content view");
		
		magician = (Magician) getApplicationContext();
		try {
			battleDataCollection = battleModel
					.getUnseenBattle(magician.getId());
			BinderData bindingData = new BinderData(this, battleDataCollection);

			listNotif = (ListView) findViewById(R.id.listNotif);

			Log.w("BEFORE", "<<------------- Before SetAdapter-------------->>");

			listNotif.setAdapter(bindingData);

			Log.w("AFTER", "<<------------- After SetAdapter-------------->>");


		} catch (InternetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
