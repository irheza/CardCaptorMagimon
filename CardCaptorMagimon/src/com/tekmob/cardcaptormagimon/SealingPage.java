package com.tekmob.cardcaptormagimon;

import java.util.ArrayList;

import trainingsensor.TrainingSensorListener;
import trainingsensor.TrainingSensorManager;
import entity.Magician;
import entity.PersonalMagimon;
import entity.Magimon;
import magicexception.InternetException;
import model.MagimonModel;
import model.PersonalMagimonModel;
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
	final int MAGIMON_SEALING_COUNT = 100;
	final String NONE_MODE = "0";
	boolean isSealed = false;
	private TextView sealingText;
	MagimonModel magimonModel = new MagimonModel();
	PersonalMagimonModel pmModel= new PersonalMagimonModel();
	ArrayList<PersonalMagimon> newParty;
	Magician magician; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		magician = (Magician)getApplicationContext();
		setContentView(R.layout.activity_sealing_page);
		sealingText = (TextView) findViewById(R.id.currentSealCount);
		setMagimon();
		
	}
	
	public void setMagimon()
	{
		Intent intent = getIntent();
		String idMagimon = intent.getStringExtra("magimon");	
		battledMonster = magimonModel.getMagimon(idMagimon);	
	}

	@Override
	public void onShake() {

		if(battledMonster!=null && isSealed==false)
		{
			 
			if(sealingCount<MAGIMON_SEALING_COUNT)
			{
				sealingCount++;
				sealingText.setText("Sealing Count : "+sealingCount);
			}
			else
			{
				isSealed=true;
			
				if(addMagimon(battledMonster.getId()))
				{
					try {
						magician.setPersonalMagimon(pmModel.getPersonalMagimonByMagician(magician.getId()));
					} catch (InternetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Intent i = new Intent(getApplicationContext(), SealedPage.class);
					i.putExtra("magimon", battledMonster.getId());  
	                startActivity(i);
	                finish();
				}
				else
				{
					Toast.makeText(getBaseContext(), "Add Magimon Failed", 
		                    Toast.LENGTH_SHORT).show();
				}
				
			}
		}
		
	}
	
	public boolean addMagimon(String idMagimon)
	{
		
		return pmModel.insert(magician.getId(), idMagimon, NONE_MODE);
		
		//magician.addMagimon(battledMonster);
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
