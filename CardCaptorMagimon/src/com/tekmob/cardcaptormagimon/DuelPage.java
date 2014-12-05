package com.tekmob.cardcaptormagimon;

import java.util.ArrayList;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class DuelPage extends Activity {
	MagicianModel magicianModel = new MagicianModel();
	Magician magician;
	ArrayList<MagicianEnemy> enemies = new ArrayList<MagicianEnemy>();
	ArrayList<String>tes1;
	private Spinner list_enemies;
	private Button btnSubmit;
	
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
		addItemsOnSpinner();
		addListenerOnButton();
	}
	
	public void setEnemies() throws JSONException
	{
		enemies = magicianModel.getAllEnemyMagician(magician.getId());
	}
	
	public void addItemsOnSpinner() {
		 
		list_enemies = (Spinner) findViewById(R.id.list_enemies);
		ArrayList<String> listEnemiesInString= convertToStringList(enemies);
		List<String> list_spinner = listEnemiesInString;
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
			android.R.layout.simple_spinner_item, list_spinner);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		list_enemies.setAdapter(dataAdapter);
	  }
	 
	
	public void addListenerOnButton() {
		 
		list_enemies = (Spinner) findViewById(R.id.list_enemies);
		btnSubmit = (Button) findViewById(R.id.btnSubmit); 
		btnSubmit.setOnClickListener(new OnClickListener() { 
		  public void onClick(View v) {
	 
		    Toast.makeText(getApplicationContext(),
			"OnClickListener : " + 
	        "\nSpinner 1 : "+ String.valueOf(list_enemies.getSelectedItem()) ,
				Toast.LENGTH_SHORT).show();
		  }
	 
		});
	  }
	
	public ArrayList<String> convertToStringList(ArrayList<MagicianEnemy> enemies)
	{
		ArrayList<String> listEnemiesInString = new ArrayList<String>();
		for(MagicianEnemy enemy : enemies)
		{
			listEnemiesInString.add(enemy.getUserID());
		}
		return listEnemiesInString;
	}
}

