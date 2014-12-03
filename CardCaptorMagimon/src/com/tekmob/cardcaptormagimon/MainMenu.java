package com.tekmob.cardcaptormagimon;


import entity.Magician;
import magimon.Magimon;
import model.MagicianModel;
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
	MagicianModel magicianModel = new MagicianModel();
	String userID = "";
	//String user = magician.getUserID();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        magician = (Magician)getApplicationContext();
        
        if(magician.getId()!=null)
        
        
        setMagician();
        
        setMenuListener();

        
    }
    
    public boolean checkMagicianInServer(String id){
		magician = magicianModel.getMagician(id);
    	
		if(magician!=null) return true;
		else return false;
    }
    
    /*
     * Fungsi untuk mengkonfigurasi awal Magician player
     */
    public void setMagician()
    {
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
        //userText.setText("User ID: "+magician.getUserID());
        if(magician.isSet()==false)
		{
        	setMagimon();
		}
    	
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
    	Button train = (Button) findViewById(R.id.train);
    	Button peta = (Button) findViewById(R.id.peta);
    	Button deck = (Button) findViewById(R.id.deck);
    	train.setOnClickListener(new View.OnClickListener() {
       	 
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), TrainingPage.class);
                //i.putExtra("user", user);
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
    	deck.setOnClickListener(new View.OnClickListener() {
         	 
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), DeckPage.class);
                startActivity(i);
               // finish();
             }
        });
    }
    
}

