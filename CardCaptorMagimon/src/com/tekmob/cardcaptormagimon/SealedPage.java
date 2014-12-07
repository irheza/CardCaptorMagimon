package com.tekmob.cardcaptormagimon;

import entity.Magimon;
import magicexception.InternetException;
import model.MagimonModel;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import animation.WalkInOut;

public class SealedPage extends Activity {
	TextView successText;
	MagimonModel magimonModel = new MagimonModel();
	private ImageView sign_top, sign_bottom;
	private WalkInOut walkInOut;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        
		sign_top = (ImageView) findViewById(R.id.sign_top);
		sign_bottom = (ImageView) findViewById(R.id.sign_bottom);
		
		walkInOut = new WalkInOut();
		walkInOut.initSign(SealedPage.this, sign_top, sign_bottom);
        
		Intent intent = getIntent();
		String magimonSealedId = intent.getStringExtra("magimon");
		Magimon sealedMagimon=null;
		try {
			sealedMagimon = magimonModel.getMagimon(magimonSealedId);
		} catch (InternetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		setContentView(R.layout.activity_sealed_page);
		successText = (TextView) findViewById(R.id.success);
		successText.setText("Sealed Magimon : "+sealedMagimon.getName());
		
		setMenuListener();
		walkInOut.startMoving();
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
				startActivity(i);
				finish();
			}
		});
		menu.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				Intent i = new Intent(getApplicationContext(), MainMenu.class);
				startActivity(i);
				finish();
			}
		});

		
	}
}
