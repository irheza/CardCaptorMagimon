package com.tekmob.cardcaptormagimon;

import java.io.IOException;

import entity.Magimon;
import magicexception.InternetException;
import model.InternalStorage;
import model.MagimonModel;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import animation.ProgressBar;
import animation.WalkInOut;

public class SealedPage extends Activity {
	TextView successText;
	MagimonModel magimonModel = new MagimonModel();
	private ImageView sign_top, sign_bottom;
    private WalkInOut walkInOut;
	BackgroundSoundSealed mBackgroundSound;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sealed_page);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        
        mBackgroundSound = new BackgroundSoundSealed(this);
        
        sign_top = (ImageView) findViewById(R.id.sign_top);
        sign_bottom = (ImageView) findViewById(R.id.sign_bottom);
        
        walkInOut = new WalkInOut();
		walkInOut.initSign(SealedPage.this, sign_top, sign_bottom);
        
		Intent intent = getIntent();
		String magimonSealedId = intent.getStringExtra("magimon");
		Magimon sealedMagimon=null;
		try {
			sealedMagimon = magimonModel.getMagimon(magimonSealedId);
			cacheLastSeal(""+System.currentTimeMillis());
		} catch (InternetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		successText = (TextView) findViewById(R.id.success);
		successText.setText("Sealed Magimon : "+sealedMagimon.getName());
		
		setMenuListener();
		walkInOut.startMoving();
	}

	@Override
	public void onResume() {
		super.onResume();
		mBackgroundSound = new BackgroundSoundSealed(this);
		mBackgroundSound.execute();
	}
	
	@Override
	public void onStop() {
		super.onStop();
		mBackgroundSound.stop();
		mBackgroundSound.cancel(true);
	}
	
	@Override
	public void onDestroy(){
		super.onDestroy();
	}
	
	@Override
	public void onPause() {
		super.onPause();
		mBackgroundSound.stop();
		mBackgroundSound.cancel(true);
		}
	
	public void cacheLastSeal(String time) {
		// Save the list of entries to internal storage
		try {
			InternalStorage.writeObject(this, "LAST_SEAL", time);
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
	
	public void setMenuListener() {
		Button peta = (Button) findViewById(R.id.peta);
		Button menu = (Button) findViewById(R.id.menu);
		peta.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				Intent i = new Intent(getApplicationContext(), Peta.class);

				mBackgroundSound.stop();
				mBackgroundSound.cancel(true);
				startActivity(i);
				finish();
			}
		});
		menu.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {

				mBackgroundSound.stop();
				mBackgroundSound.cancel(true);
				SealedPage.super.onBackPressed();
				finish();
			}
		});

		
	}
}

class BackgroundSoundSealed extends AsyncTask<Void, Void, Void> {
	Context context;
	MediaPlayer player;
	public BackgroundSoundSealed(Context context) {
		this.context = context;
	}

	@Override
	protected Void doInBackground(Void... params) {
		player = MediaPlayer.create(context, R.raw.sealed);
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

