package com.tekmob.cardcaptormagimon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SealedPage extends Activity {
	TextView successText;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		String magimonSealed = intent.getStringExtra("magimon");
		setContentView(R.layout.activity_sealed_page);
		successText = (TextView) findViewById(R.id.success);
		successText.setText("Sealed Magimon : "+magimonSealed);
		setMenuListener();
	}
	
	public void setMenuListener()
    {
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
