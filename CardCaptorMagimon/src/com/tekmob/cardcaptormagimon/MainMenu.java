package com.tekmob.cardcaptormagimon;


import org.json.JSONException;
import org.json.JSONObject;

import trainingsensor.TrainingSensorManager;

import entity.Magician;
import magimon.Magimon;
import model.MagicianModel;
import model.PersonalMagimonModel;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import animation.Bar;

 
public class MainMenu extends Activity {
	TextView userText;
	Magician magician;
	MagicianModel magicianModel = new MagicianModel();
	PersonalMagimonModel pmModel = new PersonalMagimonModel();
	private final ImageButton train = (ImageButton) findViewById(R.id.train);
	private final ImageButton peta = (ImageButton) findViewById(R.id.peta);
	private final ImageButton deck = (ImageButton) findViewById(R.id.deck);
	private final ImageButton duel = (ImageButton) findViewById(R.id.duel);
	private final ImageButton about = (ImageButton) findViewById(R.id.about);

	String userID = "";
	//String user = magician.getUserID();

	String user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);
		magician = (Magician) getApplicationContext();
		userID = getIMEI();

    	train.setClickable(true);
    	peta.setClickable(true);
    	deck.setClickable(true);
    	duel.setClickable(true);
    	about.setClickable(true);

		if (!magician.isSet()) {
			// cek dulu udah terdaftar apa belom
			Log.w("", "magician di app context blm ada");
			if (!magicianModel.checkMagician(userID)) {
				Log.w("", "magician belom ada di server");
				registerMagician();
			}else{
				JSONObject jo = magicianModel.getMagician(userID);
				try {
					magician.setId((String)jo.get("id"));
					magician.setUsername((String)jo.get("username"));
					magician.setExp(Integer.parseInt(((String)jo.get("exp"))));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			magician.setSet(true);
		}else{
			JSONObject jo = magicianModel.getMagician(userID);
			try {
				magician.setUsername((String)jo.get("username"));
				magician.setExp(Integer.parseInt(((String)jo.get("exp"))));
				userText = (TextView) findViewById(R.id.username);
				userText.setText(magician.toString());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		magician.setPersonalMagimon(pmModel.getPersonalMagimonByMagician(magician.getId()));
		
		// Log.w("", "setelah IF");
		userText = (TextView) findViewById(R.id.username);
		userText.setText(magician.toString());
		
		setMenuListener();
	}
	
	@Override
    public void onResume() {
    	train.setClickable(true);
    	peta.setClickable(true);
    	deck.setClickable(true);
    	duel.setClickable(true);
    	about.setClickable(true);
        super.onResume();
    }

    
    public String getIMEI(){
    	TelephonyManager tManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String deviceIMEI = tManager.getDeviceId(); 
        
        if(deviceIMEI!=null)
        {
        	return deviceIMEI;
        }
        else
        {
        	return Secure.getString(getApplicationContext().getContentResolver(), Secure.ANDROID_ID);
        }
    }
    
    public boolean checkMagicianInServer(String id){
		//magician = magicianModel.getMagician(id);
    	
		if(magician.getId()!=null) return true;
		else return false;
    }
    
    /*
     * Fungsi untuk mengkonfigurasi awal Magician player
     */
    public void registerMagician()
    {

    	magician.setId(userID);
    	magicianModel.registerMagician(magician);
    	
    	/*
    	TelephonyManager tManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String deviceIMEI = tManager.getDeviceId(); 
        
        
        
        if(deviceIMEI!=null)
        {
        	//magician.setUserID(deviceIMEI);
        }
        else
        {
        	String androidID = Secure.getString(getApplicationContext().getContentResolver(), Secure.ANDROID_ID);
        	//magician.setUserID(androidID);
        }
        //Magician user = new Magician(deviceIMEI);
        userText = (TextView) findViewById(R.id.username);
        //userText.setText("User ID: "+Magician.userID);
        //userText.setText("User ID: "+magician.getU.getUserID());
        //user = magician.getUserID();
        
        if(magician.isSet()==false)
		{
        	setMagimon();
		}
    	*/

    }
    
    public void setMagimon()
    {
    	//tester add Magimon, seharusnya semua parameter lengkap
    	Magimon firstPartner =  new Magimon("1"); 
    	Magimon secondPartner =  new Magimon("2"); 
    	Magimon thirdPartner =  new Magimon("3"); 
    	Magimon fourthPartner =  new Magimon("4");
    	//magician.addMagimon(firstPartner);
//    	magician.addMagimon(secondPartner);
//    	magician.addMagimon(thirdPartner);
//    	magician.addMagimon(fourthPartner);
    	//digunakan agar magimon yang sudah di set  tinggal di set lagi saat
    	//kembali ke menu utama
//    	magician.doneSetting();
    }
    
    public void setMenuListener()
    {
    	train.setOnClickListener(new View.OnClickListener() {
       	 
            @Override
            public void onClick(View view) {
            	train.setClickable(false);
            	peta.setClickable(false);
            	deck.setClickable(false);
            	duel.setClickable(false);
            	about.setClickable(false);
                Intent i = new Intent(getApplicationContext(), TrainingPage.class);
                startActivity(i);
            	overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
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
                Intent i = new Intent(getApplicationContext(), Peta.class);
                startActivity(i);
            	overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
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
                Intent i = new Intent(getApplicationContext(), DeckPage.class);
                startActivity(i);
            	overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
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
                Intent i = new Intent(getApplicationContext(), DuelPage.class);
                startActivity(i);
            	overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
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
                Intent i = new Intent(getApplicationContext(), AboutPage.class);
                startActivity(i);
            	overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
               // finish();
             }
        });
    }
    
}

