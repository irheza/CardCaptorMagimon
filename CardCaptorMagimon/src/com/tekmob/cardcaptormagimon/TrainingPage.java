package com.tekmob.cardcaptormagimon;

import trainingsensor.AccelerometerListener;
import trainingsensor.AccelerometerManager;
import trainingsensor.TrainingSensorListener;
import trainingsensor.TrainingSensorManager;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class TrainingPage extends Activity implements TrainingSensorListener {

	// todo get from database
	private int currentExp = 0;
	private int currentLevel = 1;
	private int expInCurrentLevel;
	// end
	private final int baseExpMultiplication = 200;
	private int levelGap;
	private int maxExpParam;
	private TextView expGained, expDisplay;
	private ImageView expParameter, expContainer;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_page);

        expGained = (TextView) findViewById(R.id.exp);
        expDisplay = (TextView) findViewById(R.id.exp_display);
        expParameter = (ImageView) findViewById(R.id.exp_parameter);
        expContainer = (ImageView) findViewById(R.id.exp_container);
        
    	Display screenDisplay = getWindowManager().getDefaultDisplay();
    	Point size = new Point();
    	screenDisplay.getSize(size);
    	
    	int width = size.x;
    	int height = size.y;
    	
    	int exp_bar_width = (int)(width*0.8);
    	int exp_bar_height = (int)(height*0.1);
    	
    	maxExpParam = exp_bar_width;
    	levelGap = (getNextLevelParam(currentLevel)-getNextLevelParam(currentLevel-1));
		expInCurrentLevel = currentExp-getNextLevelParam(currentLevel-1);
		
    	if(currentExp == 0) {
    		expParameter.getLayoutParams().width = 1;
    	} else {
    		expParameter.getLayoutParams().width = (expInCurrentLevel/levelGap)*maxExpParam;
    	}
        expParameter.getLayoutParams().height = exp_bar_height;
        
        expContainer.getLayoutParams().width = exp_bar_width;
        expContainer.getLayoutParams().height = exp_bar_height;

        expContainer.setY(exp_bar_height);
        expParameter.setY(exp_bar_height);
        expParameter.setX((int)(width*0.1));
        
        expDisplay.setY((float)(exp_bar_height*1.2));
        expDisplay.setText(currentExp + " / " + getNextLevelParam(currentLevel));
        
        // Check onResume Method to start accelerometer listener
    }
    
    public void onAccelerationChanged(float x, float y, float z) {
        // TODO Auto-generated method stub
         
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
		// TODO Auto-generated method stub
	     // Do your stuff here
        // Called when Motion Detected
    	currentExp++;
    	
    	if(currentExp >= getNextLevelParam(currentLevel)) {
    		currentLevel++;
    		levelGap = (getNextLevelParam(currentLevel)-getNextLevelParam(currentLevel-1));
    		Toast.makeText(getBaseContext(), "Current level : "+currentLevel, 
    				Toast.LENGTH_SHORT).show();
    	}
    	expInCurrentLevel = currentExp-getNextLevelParam(currentLevel-1);
    	
        expGained.setText("Experience Total Gained :" +currentExp);
        //expParameter.getLayoutParams().height = expContainer.getHeight();
        expParameter.getLayoutParams().width = 500;//(int)(expInCurrentLevel/levelGap)*maxExpParam;
        expParameter.invalidate();
        
        expDisplay.setText(expInCurrentLevel + " / " + levelGap);
	}

	@Override
	public void changeProximity(float nilaiProximity) {
		// TODO Auto-generated method stub
		Toast.makeText(getApplicationContext(), ""+nilaiProximity+" cm", 
				   Toast.LENGTH_LONG).show();
		
	}
 
}
