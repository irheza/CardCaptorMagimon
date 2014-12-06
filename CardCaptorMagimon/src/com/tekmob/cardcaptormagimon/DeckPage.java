package com.tekmob.cardcaptormagimon;

import java.io.IOException;
import java.util.ArrayList;

import magicexception.InternetException;
import model.InternalStorage;
import model.MagimonModel;
import model.PersonalMagimonModel;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import entity.Magician;
import entity.Magimon;
import entity.PersonalMagimon;

public class DeckPage extends Activity {
	Magician magician;
	ArrayList<PersonalMagimon> partners = new ArrayList<PersonalMagimon>();
	TextView listMagimon;
	MagimonModel magimonmodel = new MagimonModel();
	PersonalMagimonModel pmmodel = new PersonalMagimonModel();
	final Context context = this;
	int atkModeCounter;
	int defModeCounter;
	final String ATK = "1";
	final String DEF = "2";
	final String NOPE = "0";
	Button magimon1; 
	Button magimon2;
	Button magimon3; 
	Button magimon4; 
	Button magimon5;
	Button magimon6; 
	Button remove1; 
	Button remove2;
	Button remove3; 
	Button remove4; 
	Button remove5;
	Button remove6; 
	Button submitDeck;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_deck_page);
		magician = (Magician)getApplicationContext();
		try {
			magician.setPersonalMagimon(pmmodel.getPersonalMagimonByMagician(magician.getId()));
		} catch (InternetException e) {
			try {
				magician = (Magician) InternalStorage.readObject(this, "MAGICIAN_DATA");
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		setButton();
		setAllMagimon();
		setMagimonButtonListener();
		setSubmitButtonListener();
		setRemoveButtonListener();
		
	}
	
	public void setButton()
	{
		magimon1 = (Button) findViewById(R.id.magimon1);
		magimon2 = (Button) findViewById(R.id.magimon2);
		magimon3 = (Button) findViewById(R.id.magimon3);
		magimon4 = (Button) findViewById(R.id.magimon4);
		magimon5 = (Button) findViewById(R.id.magimon5);
		magimon6 = (Button) findViewById(R.id.magimon6);
		remove1 = (Button) findViewById(R.id.remove1);
		remove2 = (Button) findViewById(R.id.remove2);
		remove3 = (Button) findViewById(R.id.remove3);
		remove4 = (Button) findViewById(R.id.remove4);
		remove5 = (Button) findViewById(R.id.remove5);
		remove6 = (Button) findViewById(R.id.remove6);	
		submitDeck = (Button) findViewById(R.id.btnSubmit);
		
	}
	
	public int countAtkMode()
	{
		int countAtk=0;
		for(PersonalMagimon pm : partners)
		{
			if(pm.getMode().equals(ATK))
			{
				countAtk++;
			}
		}
		return countAtk;
	}
	
	public int countDefMode()
	{
		int countDef=0;
		for(PersonalMagimon pm : partners)
		{
			if(pm.getMode().equals(DEF))
			{
				countDef++;
			}
		}
		return countDef;
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
			remove1.setVisibility(View.GONE);
			remove2.setVisibility(View.GONE);
			remove3.setVisibility(View.GONE);
			remove4.setVisibility(View.GONE);
			remove5.setVisibility(View.GONE);
			remove6.setVisibility(View.GONE);
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
			remove2.setVisibility(View.GONE);
			remove3.setVisibility(View.GONE);
			remove4.setVisibility(View.GONE);
			remove5.setVisibility(View.GONE);
			remove6.setVisibility(View.GONE);
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
			remove3.setVisibility(View.GONE);
			remove4.setVisibility(View.GONE);
			remove5.setVisibility(View.GONE);
			remove6.setVisibility(View.GONE);
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
			remove4.setVisibility(View.GONE);
			remove5.setVisibility(View.GONE);
			remove6.setVisibility(View.GONE);
		}
		if(partners.size()>=5)
		{
			refreshButtonText(4);
		}
		else
		{
			magimon5.setVisibility(View.GONE);
			magimon6.setVisibility(View.GONE);
			remove5.setVisibility(View.GONE);
			remove6.setVisibility(View.GONE);
		}
		if(partners.size()>=6)
		{
			refreshButtonText(5);
		}
		else
		{
			magimon6.setVisibility(View.GONE);
			remove6.setVisibility(View.GONE);
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
		if(partners.get(urutanpartner).getMode().equals(NOPE))
		{
			if(countAtkMode()>=3)
			{
				partners.get(urutanpartner).setMode(DEF);
			}
			else
			{
				partners.get(urutanpartner).setMode(ATK);
			}
   	 	}
		else if(partners.get(urutanpartner).getMode().equals(ATK))
		{
			if(countDefMode()>=3)
			{
				partners.get(urutanpartner).setMode(NOPE);
			}
			else
			{
				partners.get(urutanpartner).setMode(DEF);
			}
		}
		else
		{
			if(countAtkMode()>=3)
			{
				partners.get(urutanpartner).setMode(NOPE);
			}
			else
			{
				partners.get(urutanpartner).setMode(ATK);
			}
		}
	}
	
	public void setRemoveButtonListener()
    {
		remove1.setOnClickListener(new View.OnClickListener() {
	       	 
            @Override
            public void onClick(View view) {
            	 alertRemoveShow(remove1,magimon1,0);
             }
        });
		remove2.setOnClickListener(new View.OnClickListener() {
	       	 
            @Override
            public void onClick(View view) {
            	alertRemoveShow(remove2,magimon2,1);
             }
        });
		remove3.setOnClickListener(new View.OnClickListener() {
	       	 
            @Override
            public void onClick(View view) {
            	alertRemoveShow(remove3,magimon3,2);
             }
        });
		remove4.setOnClickListener(new View.OnClickListener() {
	       	 
            @Override
            public void onClick(View view) {
            	alertRemoveShow(remove4,magimon4,3);
             }
        });
		remove5.setOnClickListener(new View.OnClickListener() {
	       	 
            @Override
            public void onClick(View view) {
            	alertRemoveShow(remove5,magimon5,4);
             }
        });
		remove6.setOnClickListener(new View.OnClickListener() {
	       	 
            @Override
            public void onClick(View view) {
            	alertRemoveShow(remove6,magimon6,5);
             }
        });
		
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
	
	public void alertRemoveShow(Button buttonRemove,Button magimonButton, int numberRemoved)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("Remove Magimon?");
		final Button button = buttonRemove;
		final Button magimon = magimonButton;
		final int partnerNumber = numberRemoved;
		
		builder.setPositiveButton("Yes", new DialogInterface.OnClickListener()
		{
			
			public void onClick(DialogInterface dialog, int id)
			{
				boolean status = pmmodel.delete(partners.get(partnerNumber));
				System.out.println("Status delete :" +status);
				if(status)
				{
					 partners.remove(partnerNumber);
	            	 button.setVisibility(View.GONE);
	            	 magimon.setVisibility(View.GONE);
				}
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
