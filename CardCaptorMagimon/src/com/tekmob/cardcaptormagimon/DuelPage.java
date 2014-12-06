package com.tekmob.cardcaptormagimon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.MagicianModel;

import org.json.JSONException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import entity.Magician;
import entity.MagicianEnemy;

public class DuelPage extends Activity {
	MagicianModel magicianModel = new MagicianModel();
	Magician magician;
	ArrayList<MagicianEnemy> enemies = new ArrayList<MagicianEnemy>();
	ArrayList<String> tes1;
	private Spinner list_enemies;
	// private Button btnSubmit;
	ListView listEnemies;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.activity_duel_page);
		magician = (Magician) getApplicationContext();
		try {
			setEnemies();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setListView();
		// addItemsOnSpinner();
		// addListenerOnButton();
	}

	public void setEnemies() throws JSONException {
		enemies = magicianModel.getAllEnemyMagician(magician.getId());
		Collections.shuffle(enemies);

	}

	public void setListView() {
		listEnemies = (ListView) findViewById(R.id.list_enemy);
		ArrayList<String> listEnemiesInString = convertToStringList(enemies);
		List<String> random10 = listEnemiesInString;
		if (listEnemiesInString.size() > 10) {
			random10 = listEnemiesInString.subList(0, 10);
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, android.R.id.text1,
				random10);
		listEnemies.setAdapter(adapter);
		listEnemies.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				// ListView Clicked item index
				int itemPosition = position;

				// ListView Clicked item value
				String itemValue = (String) listEnemies
						.getItemAtPosition(position);
				
				MagicianEnemy em = enemies.get(itemPosition);
				
				navigateToBattlePage(em.getUserID());
			}

		});
	}

	public void navigateToBattlePage(String id){
		Intent i = new Intent(this, BattlePage.class);
		i.putExtra("id", id);
		startActivity(i);
	}
	
	/*
	 * public void addItemsOnSpinner() {
	 * 
	 * list_enemies = (Spinner) findViewById(R.id.list_enemies);
	 * ArrayList<String> listEnemiesInString= convertToStringList(enemies);
	 * List<String> list_spinner = listEnemiesInString; ArrayAdapter<String>
	 * dataAdapter = new ArrayAdapter<String>(this,
	 * android.R.layout.simple_spinner_item, list_spinner);
	 * dataAdapter.setDropDownViewResource
	 * (android.R.layout.simple_spinner_dropdown_item);
	 * list_enemies.setAdapter(dataAdapter);
	 * 
	 * }
	 */

	/*
	 * public void addListenerOnButton() {
	 * 
	 * list_enemies = (Spinner) findViewById(R.id.list_enemies); btnSubmit =
	 * (Button) findViewById(R.id.btnSubmit); btnSubmit.setOnClickListener(new
	 * OnClickListener() { public void onClick(View v) {
	 * 
	 * Toast.makeText(getApplicationContext(), "OnClickListener : " +
	 * "\nSpinner 1 : "+ String.valueOf(list_enemies.getSelectedItem()) ,
	 * Toast.LENGTH_SHORT).show();
	 * 
	 * for(MagicianEnemy enemy : enemies) {
	 * if(enemy.getUsername().equals(String.
	 * valueOf(list_enemies.getSelectedItem()))){ TextView textView = (TextView)
	 * findViewById(R.id.textView1); textView.setText(enemy.toString()); } } }
	 * 
	 * }); }
	 */
	public ArrayList<String> convertToStringList(
			ArrayList<MagicianEnemy> enemies) {
		ArrayList<String> listEnemiesInString = new ArrayList<String>();
		for (MagicianEnemy enemy : enemies) {
			listEnemiesInString.add(enemy.getUsername());
		}
		return listEnemiesInString;
	}

}
