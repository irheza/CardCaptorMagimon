package com.tekmob.cardcaptormagimon;

import magimon.Magimon;
import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class SealingPage extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sealing_page);
		Intent intent = getIntent();
		Magimon battlemonster = new Magimon(intent.getStringExtra("magimon"));
	}

}
