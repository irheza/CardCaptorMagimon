package com.tekmob.cardcaptormagimon;

import java.util.ArrayList;

import entity.Magician;
import entity.PersonalMagimon;
import entity.Magimon;
import model.MagicianModel;
import model.MagimonModel;
import model.PersonalMagimonModel;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DeckPage extends Activity {
	Magician magician;
	ArrayList<PersonalMagimon> partners = new ArrayList<PersonalMagimon>();
	TextView listMagimon;
	MagimonModel magimonmodel = new MagimonModel();
	PersonalMagimonModel pmmodel = new PersonalMagimonModel();
	final Context context = this;
	Button magimon1; 
	Button magimon2;
	Button magimon3; 
	Button magimon4; 
	Button magimon5;
	Button magimon6; 
	Button submitDeck;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_deck_page);
		magician = (Magician)getApplicationContext();
		setButton();
		setAllMagimon();
		setMagimonButtonListener();
		setSubmitButtonListener();
		
	}
	
	public void setButton()
	{
		magimon1 = (Button) findViewById(R.id.magimon1);
		magimon2 = (Button) findViewById(R.id.magimon2);
		magimon3 = (Button) findViewById(R.id.magimon3);
		magimon4 = (Button) findViewById(R.id.magimon4);
		magimon5 = (Button) findViewById(R.id.magimon5);
		magimon6 = (Button) findViewById(R.id.magimon6);
		submitDeck = (Button) findViewById(R.id.btnSubmit);
		
	}
	
	public void setAllMagimon()
	{
		partners = magician.getPersonalMagimon();
		if(partners.size()>=1)
		{
			refreshButtonText(0);
		}
		else
		{
			magimon1.setVisibility(View.GONE);
			magimon2.setVisibility(View.GONE);
			magimon3.setVisibility(View.GONE);
			magimon4.setVisibility(View.GONE);
			magimon5.setVisibility(View.GONE);
			magimon6.setVisibility(View.GONE);
		}
		if(partners.size()>=2)
		{
			refreshButtonText(1);
		}
		else
		{
			magimon2.setVisibility(View.GONE);
			magimon3.setVisibility(View.GONE);
			magimon4.setVisibility(View.GONE);
			magimon5.setVisibility(View.GONE);
			magimon6.setVisibility(View.GONE);
		}
		if(partners.size()>=3)
		{
			refreshButtonText(2);
		}
		else
		{
			magimon3.setVisibility(View.GONE);
			magimon4.setVisibility(View.GONE);
			magimon5.setVisibility(View.GONE);
			magimon6.setVisibility(View.GONE);
		}
		if(partners.size()>=4)
		{
			refreshButtonText(3);
		}
		else
		{
			magimon4.setVisibility(View.GONE);
			magimon5.setVisibility(View.GONE);
			magimon6.setVisibility(View.GONE);
		}
		if(partners.size()>=5)
		{
			refreshButtonText(4);
		}
		else
		{
			magimon5.setVisibility(View.GONE);
			magimon6.setVisibility(View.GONE);
		}
		if(partners.size()>=6)
		{
			refreshButtonText(5);
		}
		else
		{
			magimon6.setVisibility(View.GONE);
		}

	}
	
	public void setMagimonButtonListener()
    {
		magimon1.setOnClickListener(new View.OnClickListener() {
	       	 
            @Override
            public void onClick(View view) {
            	 changeModeFor(0);
            	 refreshButtonText(0);
             }
        });
		magimon2.setOnClickListener(new View.OnClickListener() {
	       	 
            @Override
            public void onClick(View view) {
            	 changeModeFor(1);
            	 refreshButtonText(1);
             }
        });
		magimon3.setOnClickListener(new View.OnClickListener() {
	       	 
            @Override
            public void onClick(View view) {
            	 changeModeFor(2);
            	 refreshButtonText(2);
             }
        });
		magimon4.setOnClickListener(new View.OnClickListener() {
	       	 
            @Override
            public void onClick(View view) {
            	 changeModeFor(3);
            	 refreshButtonText(3);
             }
        });
		magimon5.setOnClickListener(new View.OnClickListener() {
	       	 
            @Override
            public void onClick(View view) {
            	 changeModeFor(4);
            	 refreshButtonText(4);
             }
        });
		magimon6.setOnClickListener(new View.OnClickListener() {
	       	 
            @Override
            public void onClick(View view) {
            	 changeModeFor(5);
            	 refreshButtonText(5);
             }
        });
    }
    
	public void changeModeFor(int urutanpartner)
	{
		if(partners.get(urutanpartner).getMode().equals("0"))
		{
			partners.get(urutanpartner).setMode("1");
   	 	}
		else if(partners.get(urutanpartner).getMode().equals("1"))
		{
			partners.get(urutanpartner).setMode("2");
		}
		else
		{
			partners.get(urutanpartner).setMode("0");
		}
	}
	public void setSubmitButtonListener()
    {
		submitDeck.setOnClickListener(new View.OnClickListener() {
	       	 
            @Override
            public void onClick(View view) {
            	
            	
            	AlertDialog.Builder builder = new AlertDialog.Builder(context);
    			builder.setTitle("Submit Deck");
    			
    			
    			builder.setPositiveButton("Yes", new DialogInterface.OnClickListener()
    			{
    				
    				public void onClick(DialogInterface dialog, int id)
    				{
    					magician.setPersonalMagimon(partners);
    	            	for(PersonalMagimon pm : partners)
    	            	{
    	            		pmmodel.update(pm);
    	            	}
    					Intent intent = new Intent(getBaseContext(), MainMenu.class);
            			startActivity(intent);
            			finish();
    				}
    			});
    			builder.setNegativeButton("Nope", new DialogInterface.OnClickListener()
    			{
    				public void onClick(DialogInterface dialog, int id)
    				{
    					dialog.cancel();
    					
    				}
    			});
    			AlertDialog alertdialog = builder.create();
    			alertdialog.show();
            	
             }
        });
		
    }
	//urutanpartner mulai dari 0
	public void refreshButtonText(int urutanpartner)
	{
		Button refreshedButton;
		if(urutanpartner==0)
		{
			refreshedButton=magimon1;
		}
		else if(urutanpartner==1)
		{
			refreshedButton=magimon2;
		}
		else if(urutanpartner==2)
		{
			refreshedButton=magimon3;
		}
		else if(urutanpartner==3)
		{
			refreshedButton=magimon4;
		}
		else if(urutanpartner==4)
		{
			refreshedButton=magimon5;
		}
		else
		{
			refreshedButton=magimon6;
		}
		PersonalMagimon personalMagimon = partners.get(urutanpartner);
		Magimon partner = magimonmodel.getMagimon(personalMagimon.getMagimonID());
		if(personalMagimon.getMode().equals("1"))
		{
			refreshedButton.setText(partner.getName()+" ATK:"+partner.getAttack());
		}
		else if(personalMagimon.getMode().equals("2"))
		{
			refreshedButton.setText(partner.getName()+" DEF:"+partner.getDefense());
		}
		else
		{
			refreshedButton.setText(partner.getName()+" None");
		}
	}
	
}
