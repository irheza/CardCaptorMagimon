package com.tekmob.cardcaptormagimon;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import model.InternalStorage;
import model.MagicianModel;

import org.json.JSONException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
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

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
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
				checkLastBattle(em);
			}

		});
	}

	public void checkLastBattle(MagicianEnemy em) {
		try {
			String strLastBattle = (String) InternalStorage.readObject(this,
					"LAST_BATTLE");
			long lastBattle = Long.parseLong(strLastBattle);
			long now = System.currentTimeMillis();

			if ((now - lastBattle) < 3600000) {
				Date date = new Date(lastBattle);
				String lastBTL = date.toLocaleString();
				AlertDialog.Builder alert = new AlertDialog.Builder(this);

				alert.setTitle("Cool down!");
				alert.setMessage("Dear magician, your last battle is at "
						+ lastBTL
						+ " and it is not too long ago. Chill, you can train yourself and get a life first. Please come back later!");

				alert.setPositiveButton("Okay",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
							}
						});

				alert.show();
			} else {
				navigateToBattlePage(em.getUserID());
			}

		} catch (Exception e){
			navigateToBattlePage(em.getUserID());
		}
	}

	public void navigateToBattlePage(String id) {
		Intent i = new Intent(this, BattlePage.class);
		i.putExtra("id", id);
		startActivity(i);
		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
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
			int enemyLevel = getCurrentLevel(enemy.getExperience())[0];
			listEnemiesInString.add("Lv." + enemyLevel + " > " + enemy.getUsername());
		}
		return listEnemiesInString;
	}
	
    /**
     * Do some awesome calculation here.
     * 
     * @param currentLevel
     * @return total experience to reach next level
     */
    private int getNextLevelParam(int currentLevel) {
    	int ret = (int)(200*(Math.pow(currentLevel, 1.5)));
    	
    	return ret;
    }
    
    /**
     * To get current level
     * 
     * @param currentExp
     * @return
     */
    private int[] getCurrentLevel(int currentExp) {
    	int[] ret = new int[2];
    	ret[0] = 1;
    	int temp = currentExp;
    	boolean notFound = true;
    	
    	while (notFound) {
    		int temp_exp_needed = (getNextLevelParam(ret[0])-getNextLevelParam(ret[0]-1));
    		if (temp < temp_exp_needed) {
    			notFound = false;
    		} else {
    			temp = temp - temp_exp_needed;
    			ret[0]++;
    		}
    	}
    	ret[1] = temp;
    	return ret;
    }

}
