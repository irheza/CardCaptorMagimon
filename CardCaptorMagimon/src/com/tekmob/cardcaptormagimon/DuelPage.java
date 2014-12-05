package com.tekmob.cardcaptormagimon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONException;

import entity.Magician;
import entity.MagicianEnemy;
import model.MagicianModel;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class DuelPage extends Activity {
	MagicianModel magicianModel = new MagicianModel();
	Magician magician;
	ArrayList<MagicianEnemy> enemies = new ArrayList<MagicianEnemy>();
	ArrayList<String>tes1;
	private Spinner list_enemies;
	//private Button btnSubmit;
	ListView listEnemies;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_duel_page);
		magician = (Magician)getApplicationContext();
		try {
			setEnemies();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setListView();
		//addItemsOnSpinner();
		//addListenerOnButton();
	}
	
	public void setEnemies() throws JSONException
	{
		enemies = magicianModel.getAllEnemyMagician(magician.getId());
		Collections.shuffle(enemies);

	}
	
	public void setListView()
	{
		listEnemies = (ListView) findViewById(R.id.list_enemy);
		ArrayList<String> listEnemiesInString= convertToStringList(enemies);
		List<String> random10=listEnemiesInString;
		if(listEnemiesInString.size()>10)
		{
			random10 = listEnemiesInString.subList(0, 10);
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
	              android.R.layout.simple_list_item_1, android.R.id.text1, random10);
		listEnemies.setAdapter(adapter); 
		listEnemies.setOnItemClickListener(new OnItemClickListener() {
			  
            public void onItemClick(AdapterView<?> parent, View view,
               int position, long id) {
              
             // ListView Clicked item index
             int itemPosition     = position;
             
             // ListView Clicked item value
             String  itemValue    = (String) listEnemies.getItemAtPosition(position);
                
              // Show Alert 
              Toast.makeText(getApplicationContext(),
                "Position :"+itemPosition+"  ListItem : " +itemValue , Toast.LENGTH_LONG)
                .show();
           
            }

       }); 
	}
	
/*	public void addItemsOnSpinner() {
		 
		list_enemies = (Spinner) findViewById(R.id.list_enemies);
		ArrayList<String> listEnemiesInString= convertToStringList(enemies);
		List<String> list_spinner = listEnemiesInString;
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
			android.R.layout.simple_spinner_item, list_spinner);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		list_enemies.setAdapter(dataAdapter);
		
	  }
	 */
	
	/*public void addListenerOnButton() {
		 
		list_enemies = (Spinner) findViewById(R.id.list_enemies);
		btnSubmit = (Button) findViewById(R.id.btnSubmit); 
		btnSubmit.setOnClickListener(new OnClickListener() { 
		  public void onClick(View v) {
	 
		    Toast.makeText(getApplicationContext(),
			"OnClickListener : " + 
	        "\nSpinner 1 : "+ String.valueOf(list_enemies.getSelectedItem()) ,
				Toast.LENGTH_SHORT).show();
		    
		    for(MagicianEnemy enemy : enemies)
			{
				if(enemy.getUsername().equals(String.valueOf(list_enemies.getSelectedItem()))){
					TextView textView = (TextView) findViewById(R.id.textView1);
					textView.setText(enemy.toString());
				}
			}
		  }
	 
		});
	  }
	*/
	public ArrayList<String> convertToStringList(ArrayList<MagicianEnemy> enemies)
	{
		ArrayList<String> listEnemiesInString = new ArrayList<String>();
		for(MagicianEnemy enemy : enemies)
		{
			listEnemiesInString.add(enemy.getUsername());
		}
		return listEnemiesInString;
	}
	
	
}

