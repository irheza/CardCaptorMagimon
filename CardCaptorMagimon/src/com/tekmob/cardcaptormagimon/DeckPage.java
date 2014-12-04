package com.tekmob.cardcaptormagimon;

import java.util.ArrayList;

import entity.Magician;
import magimon.Magimon;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class DeckPage extends Activity {
	Magician magician;
	ArrayList<Magimon> partners = new ArrayList<Magimon>();
	TextView listMagimon;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_deck_page);
		magician = (Magician)getApplicationContext();
		
			setAllMagimon();
		
	}
	
	public void setAllMagimon()
	{
		listMagimon = (TextView) findViewById(R.id.listMagimon);
		//partners = magician.getListMagimon();
		//Coba-coba
		String all="Partners: ";
		for(Magimon magimon: partners)
		{
			all=all+magimon.id;
		}
		listMagimon.setText(all);
		
	}
}
