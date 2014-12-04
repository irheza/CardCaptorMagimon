package com.tekmob.cardcaptormagimon;

import org.json.JSONException;
import org.json.JSONObject;

import magimon.Magimon;
import model.MagicianModel;
import model.PersonalMagimonModel;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import entity.Magician;

public class MainMenu extends Activity {
	TextView userText;
	Magician magician;
	MagicianModel magicianModel = new MagicianModel();
	PersonalMagimonModel pmModel = new PersonalMagimonModel();

	String userID = "";

	String user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);
		magician = (Magician) getApplicationContext();
		userID = getIMEI();

		if (!magician.isSet()) {
			// cek dulu udah terdaftar apa belom
			Log.w("", "magician di app context blm ada");
			if (!magicianModel.checkMagician(userID)) {
				Log.w("", "magician belom ada di server");
				registerMagician();
			}else{
				JSONObject jo = magicianModel.getMagician(userID);
				try {
					magician.setId((String)jo.get("id"));
					magician.setUsername((String)jo.get("username"));
					magician.setExp(Integer.parseInt(((String)jo.get("exp"))));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			magician.setSet(true);
		}
		
		magician.setPersonalMagimon(pmModel.getPersonalMagimonByMagician(magician.getId()));
		
		// Log.w("", "setelah IF");
		userText = (TextView) findViewById(R.id.username);
		userText.setText(magician.toString());
		
		
		
		setMenuListener();
	}

	public String getIMEI() {
		TelephonyManager tManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		String deviceIMEI = tManager.getDeviceId();

		if (deviceIMEI != null) {
			return deviceIMEI;
		} else {
			return Secure.getString(getApplicationContext()
					.getContentResolver(), Secure.ANDROID_ID);
		}
	}

	/*
	 * Fungsi untuk mengkonfigurasi awal Magician player
	 */
	public void registerMagician() {
		magician.setId(userID);
		AlertDialog.Builder alert = new AlertDialog.Builder(this);

		alert.setTitle("What's your name?");
		alert.setMessage("Welcome! Magimon catcher! How should I call you?");

		// Set an EditText view to get user input
		final EditText input = new EditText(this);
		alert.setView(input);
		
		alert.setPositiveButton("Register", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				magician.setUsername(input.getText().toString());
				magicianModel.registerMagician(magician);
			}
		});

		alert.show();
	}
	

	public void setMenuListener() {
		Button train = (Button) findViewById(R.id.train);
		Button peta = (Button) findViewById(R.id.peta);
		Button deck = (Button) findViewById(R.id.deck);
		train.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				Intent i = new Intent(getApplicationContext(),
						TrainingPage.class);
				// i.putExtra("user", user);
				startActivity(i);
				overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
				// finish();
			}
		});
		peta.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				Intent i = new Intent(getApplicationContext(), Peta.class);
				startActivity(i);
				overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
				// finish();
			}
		});
		deck.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				Intent i = new Intent(getApplicationContext(), DeckPage.class);
				startActivity(i);
				overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
				// finish();
			}
		});
	}

}
