package com.tekmob.cardcaptormagimon;


import trainingsensor.TrainingSensorListener;
import trainingsensor.TrainingSensorManager;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import animation.Bar;


public class TrainingPage extends Activity implements TrainingSensorListener {

	// todo get from database
	private int currentExp = 150;
	private int currentLevel = 1;
	private int expInCurrentLevel;
	// end
	private final int baseExpMultiplication = 200;
	private int expNeededToLevelUp;
	private TextView expDisplay;
	private RelativeLayout expContainer;
	private RelativeLayout bar_parameter;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_page);

        expDisplay = (TextView) findViewById(R.id.exp_display);
        bar_parameter = (RelativeLayout) findViewById(R.id.bar_parameter);
        expContainer = (RelativeLayout) findViewById(R.id.exp_container);
    	
    	expNeededToLevelUp = (getNextLevelParam(currentLevel)-getNextLevelParam(currentLevel-1));
		expInCurrentLevel = currentExp-getNextLevelParam(currentLevel-1);

		// Init bar views
        Bar.initBarVariables(TrainingPage.this);
        Bar.setCurrentExpInThisLevel(expInCurrentLevel);
        Bar.setExpNeededToLevelUp(expNeededToLevelUp);
        Bar.initBarContainer(expContainer);
        Bar.initBarParameter(bar_parameter);
        Bar.initExpDisplay(expDisplay);

        // Check onResume Method to start accelerometer listener
    }
    
    public void onAccelerationChanged(float x, float y, float z) {
        // TODO Auto-generated method stub
         
    }
    
    @Override
    public void onResume() {
            super.onResume();
            Toast.makeText(getBaseContext(), "onResume Accelerometer Started", 
                    Toast.LENGTH_SHORT).show();
             
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

	@Override
	public void onShake() {
        // Called when Motion Detected
		double asd = (int)((expInCurrentLevel*528)/expNeededToLevelUp);
		
    	Toast.makeText(getBaseContext(), "expNeededToLevelUp : "+expNeededToLevelUp
    			+ "expInCurrentLevel : "+expInCurrentLevel
    			+"hahahaha : "+asd, 
				Toast.LENGTH_SHORT).show();
    	
    	updateStatus();
    	Bar.setCurrentExpInThisLevel(expInCurrentLevel);
    	Bar.setExpNeededToLevelUp(expNeededToLevelUp);
        Bar.updateExpDisplay(expDisplay);
        Bar.updateBarParameter(bar_parameter);
	}
	
	/**
	 * Update player's stats
	 */
	public void updateStatus() {
    	currentExp++;
    	if(currentExp >= getNextLevelParam(currentLevel)) {
    		currentLevel++;
    		expNeededToLevelUp = (getNextLevelParam(currentLevel)-getNextLevelParam(currentLevel-1));
    		Toast.makeText(getBaseContext(), "Current level : "+currentLevel, 
    				Toast.LENGTH_SHORT).show();
    	}
    	expInCurrentLevel = currentExp-getNextLevelParam(currentLevel-1);
	}

	@Override
	public void changeProximity(float nilaiProximity) {
		// TODO Auto-generated method stub
		Toast.makeText(getApplicationContext(), ""+nilaiProximity+" cm", 
				   Toast.LENGTH_LONG).show();
		
	}

    /**
     * Do some awesome calculation here.
     * 
     * @param currentLevel
     * @return total experience to reach next level
     */
    public int getNextLevelParam(int currentLevel) {
    	int ret = (int)(baseExpMultiplication*Math.pow(currentLevel, 1.5));
    	
    	return ret;
    }
 
}
