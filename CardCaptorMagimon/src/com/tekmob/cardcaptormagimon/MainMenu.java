package com.tekmob.cardcaptormagimon;

import java.io.IOException;

import magicexception.InternetException;
import magimon.Magimon;
import model.InternalStorage;
import model.MagicianModel;
import model.MagimonModel;
import model.PersonalMagimonModel;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import animation.ProgressBar;
import entity.Magician;

public class MainMenu extends Activity {
	TextView userText;
	Magician magician;
	MagicianModel magicianModel = new MagicianModel();
	MagimonModel magimonModel = new MagimonModel();
	PersonalMagimonModel pmModel = new PersonalMagimonModel();
	private RelativeLayout progress_bar_container, content;

	String userID = "";

	String user;
	BackgroundSound mBackgroundSound;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.activity_main_menu);

		mBackgroundSound = new BackgroundSound(this);

		magician = (Magician) getApplicationContext();
		userID = getIMEI();

		ImageButton train = (ImageButton) findViewById(R.id.train);
		ImageButton peta = (ImageButton) findViewById(R.id.peta);
		ImageButton deck = (ImageButton) findViewById(R.id.deck);
		ImageButton duel = (ImageButton) findViewById(R.id.duel);
		ImageButton about = (ImageButton) findViewById(R.id.about);
		ImageButton report = (ImageButton) findViewById(R.id.report);
        progress_bar_container = (RelativeLayout) findViewById(R.id.progress_bar_container);
        content = (RelativeLayout) findViewById(R.id.content);
        ProgressBar.initBarVariables(MainMenu.this);
        ProgressBar.initProgressBar(progress_bar_container);
        ProgressBar.showProgressBar(progress_bar_container, content);
		train.setClickable(true);
		peta.setClickable(true);
		deck.setClickable(true);
		duel.setClickable(true);
		about.setClickable(true);
		report.setClickable(true);
		
		magician = (Magician) getApplicationContext();
		userID = getIMEI();

		if (!magician.isSet()) {
			// cek dulu udah terdaftar apa belom
			Log.w("", "magician di app context blm ada");
			try {
				if (!magicianModel.checkMagician(userID)) {
					Log.w("", "magician belom ada di server");
					registerMagician();
				} else {
					JSONObject jo = magicianModel.getMagician(userID);
					try {
						magician.setId((String) jo.get("id"));
						magician.setUsername((String) jo.get("username"));
						magician.setExp(Integer.parseInt(((String) jo
								.get("exp"))));
						magician.setPersonalMagimon(pmModel
								.getPersonalMagimonByMagician(magician.getId()));
						magician.setMagimonCache(magimonModel
								.getSeveralMagimon(magician
										.getPersonalMagimon()));
						cacheMagician();
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			} catch (InternetException ie) {
				try {
					magician = (Magician) InternalStorage.readObject(this,
							"MAGICIAN_DATA");
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			magician.setSet(true);
		} else {
			try {
				JSONObject jo = magicianModel.getMagician(userID);
				if (jo == null) {
					throw new InternetException();
				} else {
					magician.setUsername((String) jo.get("username"));
					magician.setExp(Integer.parseInt(((String) jo.get("exp"))));
					magician.setPersonalMagimon(pmModel
							.getPersonalMagimonByMagician(magician.getId()));
					userText = (TextView) findViewById(R.id.username);
					userText.setText(magician.toString());
					cacheMagician();
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InternetException ie) {
				try {
					magician = (Magician) InternalStorage.readObject(this,
							"MAGICIAN_DATA");
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		// Log.w("", "setelah IF");
		userText = (TextView) findViewById(R.id.username);
		userText.setText("Welcome, "+magician.getUsername());

		setMenuListener();
		ProgressBar.hideProgressBar(progress_bar_container, content);
		
	}

	@Override
	public void onResume() {
		ProgressBar.showProgressBar(progress_bar_container, content);
		ImageButton train = (ImageButton) findViewById(R.id.train);
		ImageButton peta = (ImageButton) findViewById(R.id.peta);
		ImageButton deck = (ImageButton) findViewById(R.id.deck);
		ImageButton duel = (ImageButton) findViewById(R.id.duel);
		ImageButton about = (ImageButton) findViewById(R.id.about);
		ImageButton report = (ImageButton) findViewById(R.id.report);
		
		train.setClickable(true);
		peta.setClickable(true);
		deck.setClickable(true);
		duel.setClickable(true);
		about.setClickable(true);
		report.setClickable(true);
        ProgressBar.hideProgressBar(progress_bar_container, content);
		super.onResume();
		mBackgroundSound = new BackgroundSound(this);
		mBackgroundSound.execute();

	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
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

	public boolean checkMagicianInServer(String id) {
		// magician = magicianModel.getMagician(id);

		if (magician.getId() != null)
			return true;
		else
			return false;
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

		alert.setPositiveButton("register",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						magician.setUsername(input.getText().toString());
						magicianModel.registerMagician(magician);

						cacheMagician();
					}
				});

		alert.show();

	}

	public void cacheMagician() {
		// Save the list of entries to internal storage
		try {
			InternalStorage.writeObject(this, "MAGICIAN_DATA", magician);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setMagimon() {
		// tester add Magimon, seharusnya semua parameter lengkap
		Magimon firstPartner = new Magimon("1");
		Magimon secondPartner = new Magimon("2");
		Magimon thirdPartner = new Magimon("3");
		Magimon fourthPartner = new Magimon("4");
		// magician.addMagimon(firstPartner);
		// magician.addMagimon(secondPartner);
		// magician.addMagimon(thirdPartner);
		// magician.addMagimon(fourthPartner);
		// digunakan agar magimon yang sudah di set tinggal di set lagi saat
		// kembali ke menu utama
		// magician.doneSetting();
	}

	public void setMenuListener() {
		final ImageButton train = (ImageButton) findViewById(R.id.train);
		final ImageButton peta = (ImageButton) findViewById(R.id.peta);
		final ImageButton deck = (ImageButton) findViewById(R.id.deck);
		final ImageButton duel = (ImageButton) findViewById(R.id.duel);
		final ImageButton about = (ImageButton) findViewById(R.id.about);
		final ImageButton report = (ImageButton) findViewById(R.id.report);

		train.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				train.setClickable(false);
				peta.setClickable(false);
				deck.setClickable(false);
				duel.setClickable(false);
				about.setClickable(false);
				report.setClickable(false);
				Intent i = new Intent(getApplicationContext(),
						TrainingPage.class);
				mBackgroundSound.stop();
				mBackgroundSound.cancel(true);
				startActivity(i);
				overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
				// finish();
			}
		});
		peta.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				train.setClickable(false);
				peta.setClickable(false);
				deck.setClickable(false);
				duel.setClickable(false);
				about.setClickable(false);
				report.setClickable(false);
				Intent i = new Intent(getApplicationContext(), Peta.class);
				mBackgroundSound.stop();
				mBackgroundSound.cancel(true);
				startActivity(i);
				overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
				// finish();
			}
		});
		deck.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				train.setClickable(false);
				peta.setClickable(false);
				deck.setClickable(false);
				duel.setClickable(false);
				about.setClickable(false);
				report.setClickable(false);
				
				Intent i = new Intent(getApplicationContext(), DeckPage.class);
				mBackgroundSound.stop();
				mBackgroundSound.cancel(true);
				startActivity(i);
				overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
				
				// finish();
			}
		});
		duel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				train.setClickable(false);
				peta.setClickable(false);
				deck.setClickable(false);
				duel.setClickable(false);
				about.setClickable(false);
				report.setClickable(false);
				Intent i = new Intent(getApplicationContext(), DuelPage.class);
				mBackgroundSound.stop();
				mBackgroundSound.cancel(true);
				startActivity(i);
				overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
				// finish();
			}
		});
		about.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				
				train.setClickable(false);
				peta.setClickable(false);
				deck.setClickable(false);
				duel.setClickable(false);
				about.setClickable(false);
				report.setClickable(false);
				Intent i = new Intent(getApplicationContext(), AboutPage.class);
				startActivity(i);
				overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
		
				
				// finish();
			}
		});
		report.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				train.setClickable(false);
				peta.setClickable(false);
				deck.setClickable(false);
				duel.setClickable(false);
				about.setClickable(false);
				report.setClickable(false);
				Intent i = new Intent(getApplicationContext(), NotificationPage.class);
				startActivity(i);
				overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
				// finish();
			}
		});
	}

	@Override
	public void onStop() {
		super.onStop();
	
		mBackgroundSound.stop();
		mBackgroundSound.cancel(true);
		
		
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	

	@Override
	public void onPause() {
		super.onPause();
		
		mBackgroundSound.stop();
		mBackgroundSound.cancel(true);
		
		
	}

}

class BackgroundSound extends AsyncTask<Void, Void, Void> {
	Context context;
	MediaPlayer player;
	public BackgroundSound(Context context) {
		this.context = context;
	}

	@Override
	protected Void doInBackground(Void... params) {
		player = MediaPlayer.create(context, R.raw.bgm);
		player.setLooping(true); // Set looping
		player.setVolume(100, 100);
		player.start();
		return null;
	}
	
	
	public Void stop(){
		player.stop();
		return null;
	}
	
}
