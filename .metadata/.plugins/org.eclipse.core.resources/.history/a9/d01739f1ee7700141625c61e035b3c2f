package com.tekmob.cardcaptormagimon;

import trainingsensor.TrainingSensorListener;
import trainingsensor.TrainingSensorManager;
import magimon.Magimon;
import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class SealingPage extends Activity implements TrainingSensorListener {
	Magimon battledMonster;
	int sealingCount=0;
	boolean isSealed = false;
	private TextView sealingText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sealing_page);
		sealingText = (TextView) findViewById(R.id.currentSealCount);
		setMagimon();
		
	}
	
	public void setMagimon()
	{
		Intent intent = getIntent();
		battledMonster = new Magimon(intent.getStringExtra("magimon"),"Rare Leana",20,25,100,"leana.jpg");
		
	}

	@Override
	public void onShake() {

		if(battledMonster!=null && isSealed==false)
		{
			 
			if(sealingCount<battledMonster.sealingCount)
			{
				sealingCount++;
				sealingText.setText("Sealing Count : "+sealingCount);
			}
			else
			{
				isSealed=true;
				Intent i = new Intent(getApplicationContext(), SealedPage.class);
                startActivity(i);
                finish();
			}
		}
		
	}

	@Override
	public void changeProximity(float nilaiProximity) {
		// TODO Auto-generated method stub
		
	}
	
	 
    @Override
    public void onResume() {
            super.onResume();            
            //Check device supported Accelerometer senssor or not
            if (TrainingSensorManager.isSupported(this)) {
                 
                //Start Accelerometer Listening
            	TrainingSensorManager.startListening(this);
            }
    }
     
    @Override
    public void onStop() {
            super.onStop();
             
            //Check device supported Accelerometer senssor or not
            if (TrainingSensorManager.isListening()) {
                 
                //Start Accelerometer Listening
                TrainingSensorManager.stopListening();
                 
                Toast.makeText(getBaseContext(), "onStop Accelerometer Stoped", 
                         Toast.LENGTH_SHORT).show();
            }
            
    }
     
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("Sensor", "Service  distroy");
         
        //Check device supported Accelerometer senssor or not
        if (TrainingSensorManager.isListening()) {
             
            //Start Accelerometer Listening
        	TrainingSensorManager.stopListening();
             
            Toast.makeText(getBaseContext(), "onDestroy Accelerometer Stoped", 
                   Toast.LENGTH_SHORT).show();
        }
             
    }


}
