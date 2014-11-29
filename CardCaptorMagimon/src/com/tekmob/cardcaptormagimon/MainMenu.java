package com.tekmob.cardcaptormagimon;


import magician.Magician;
import android.os.Bundle;
import android.provider.Settings;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;

 
public class MainMenu extends Activity {
	TextView userText;
	Magician magician;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        magician = (Magician)getApplicationContext();
        setMagician();
        setMenuListener();

        
    }
    
    public void setMagician()
    {
    	TelephonyManager tManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        
    	String deviceIMEI = tManager.getDeviceId(); 
        if(deviceIMEI!=null)
        {
        	magician.setUserID(deviceIMEI);
        }
        else
        {
        	String androidID = Secure.getString(getApplicationContext().getContentResolver(), Secure.ANDROID_ID);
        	magician.setUserID(androidID);
        }
        //Magician user = new Magician(deviceIMEI);
        userText = (TextView) findViewById(R.id.username);
        //userText.setText("User ID: "+Magician.userID);
        userText.setText("User ID: "+magician.getUserID());
    	
    }
    
    public void setMenuListener()
    {
    	Button train = (Button) findViewById(R.id.train);
    	Button peta = (Button) findViewById(R.id.peta);
    	train.setOnClickListener(new View.OnClickListener() {
       	 
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), TrainingPage.class);
                startActivity(i);
               // finish();
             }
        });
    	peta.setOnClickListener(new View.OnClickListener() {
          	 
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Peta.class);
                startActivity(i);
               // finish();
             }
        });
    }
    
}

