package com.tekmob.cardcaptormagimon;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;
import animation.ProgressBar;

public class AboutPage extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.activity_about_page);
	}
	
	@Override
    public void onBackPressed() {
    	super.onBackPressed();
    	overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
    }
}
