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
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import animation.SealingCircle;

public class SealingPage extends Activity implements TrainingSensorListener {
	Magimon battledMonster;
	int sealingCount=0;
	int percentage = 0;
	final int MAGIMON_SEALING_COUNT = 10;
	final String NONE_MODE = "0";
	boolean isSealed = false;
	private TextView sealingText, currentMagimonName;
	private ImageView process_circ_outer, process_circ_mid_connect, process_circ_mid_clean;
	private ImageView process_circ_right_up, process_circ_right_down, process_circ_left_up, process_circ_left_down;
	private ImageView process_circ_core, process_circ_glow;
	private SealingCircle sealingCircle;
	MagimonModel magimonModel = new MagimonModel();
	PersonalMagimonModel pmModel= new PersonalMagimonModel();
	ArrayList<PersonalMagimon> newParty;
	Magician magician;
	private String magimonName = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		magician = (Magician)getApplicationContext();
		setContentView(R.layout.activity_sealing_page);
		sealingText = (TextView) findViewById(R.id.currentSealCount);
		currentMagimonName = (TextView) findViewById(R.id.magimonName);
		
		// init imageview to animate
		process_circ_outer = (ImageView) findViewById(R.id.process_circ_outer);
		process_circ_mid_connect = (ImageView) findViewById(R.id.process_circ_mid_connect);
		process_circ_mid_clean = (ImageView) findViewById(R.id.process_circ_mid_clean);
		process_circ_right_up = (ImageView) findViewById(R.id.process_circ_right_up);
		process_circ_right_down = (ImageView) findViewById(R.id.process_circ_right_down);
		process_circ_left_up = (ImageView) findViewById(R.id.process_circ_left_up);
		process_circ_left_down = (ImageView) findViewById(R.id.process_circ_left_down);
		process_circ_core = (ImageView) findViewById(R.id.process_circ_core);
		process_circ_glow = (ImageView) findViewById(R.id.process_circ_glow);
		sealingCircle = new SealingCircle(process_circ_outer, process_circ_mid_connect, 
				process_circ_mid_clean, process_circ_right_up, process_circ_right_down, process_circ_left_up, 
				process_circ_left_down, process_circ_core, process_circ_glow);
		// end of initiation
		
		try {
			setMagimon();
			currentMagimonName.setText(magimonName);
		} catch (InternetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
    public void onBackPressed() {
    	super.onBackPressed();
    	overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
    }
	
	public void setMagimon() throws InternetException
	{
		Intent intent = getIntent();
		String idMagimon = intent.getStringExtra("magimon");	
		try {
			battledMonster = magimonModel.getMagimon(idMagimon);
			magimonName = battledMonster.getName();
		} catch (InternetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	@Override
	public void onShake() {

		if(battledMonster!=null && isSealed==false)
		{
			 
			if(sealingCount<MAGIMON_SEALING_COUNT)
			{
				sealingCount++;
				percentage = (int) (sealingCount*100)/MAGIMON_SEALING_COUNT;
				sealingCircle.updateCircle(percentage);
				sealingText.setText("Complete : "+percentage+"%");
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
