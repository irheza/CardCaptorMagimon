package com.tekmob.cardcaptormagimon;


import org.json.JSONException;
import org.json.JSONObject;

import entity.*;
import magicexception.InternetException;
import model.*;
import trainingsensor.TrainingSensorListener;
import trainingsensor.TrainingSensorManager;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import animation.Bar;
import animation.ProgressBar;


public class TrainingPage extends Activity implements TrainingSensorListener {

	// todo get from database
	private int currentExp = 0;
	private int currentLevel = 1;
	private int expInCurrentLevel;
	private String userIs = "Pffft";
	// end
	private int expNeededToLevelUp;
	private TextView expDisplay, username, level;
	private RelativeLayout expContainer, bar_parameter, magic_ball, black_screen;
	private RelativeLayout progress_bar_container, content;
	private ImageView glass_ball_magic_effect;
	private MagicianModel mm;
	private Magician magician;
	private Animation screen_off, screen_on;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        mm = new MagicianModel();
        setContentView(R.layout.activity_training_page);
        
        magician = (Magician)getApplicationContext();
        currentExp = magician.getExp();
        userIs = magician.getUsername();
        
        expDisplay = (TextView) findViewById(R.id.exp_display);
        username = (TextView) findViewById(R.id.username);
        level = (TextView) findViewById(R.id.level);
        bar_parameter = (RelativeLayout) findViewById(R.id.bar_parameter);
        expContainer = (RelativeLayout) findViewById(R.id.exp_container);
        magic_ball = (RelativeLayout) findViewById(R.id.magic_ball);
        black_screen = (RelativeLayout) findViewById(R.id.black_screen);
        progress_bar_container = (RelativeLayout) findViewById(R.id.progress_bar_container);
        content = (RelativeLayout) findViewById(R.id.content);
        glass_ball_magic_effect = (ImageView) findViewById(R.id.glass_ball_magic_effect);
        
        screen_on = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.turn_on_screen);
        screen_off = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.turn_off_screen);
        black_screen.setVisibility(View.GONE);
    	
        int[] levelAndExp = getCurrentLevel(currentExp);
        currentLevel = levelAndExp[0];
    	expNeededToLevelUp = (getNextLevelParam(currentLevel)-getNextLevelParam(currentLevel-1));
		expInCurrentLevel = levelAndExp[1];
		
		// Init bar views
        Bar.initBarVariables(TrainingPage.this);
        Bar.setCurrentExpInThisLevel(expInCurrentLevel);
        Bar.setExpNeededToLevelUp(expNeededToLevelUp);
        Bar.initMagicBall(magic_ball, glass_ball_magic_effect);
        Bar.initUsername(username, userIs);
        Bar.initLevel(level, currentLevel);
        Bar.initBarContainer(expContainer);
        Bar.initBarParameter(bar_parameter);
        Bar.initExpDisplay(expDisplay);
        ProgressBar.initBarVariables(TrainingPage.this);
        ProgressBar.initProgressBar(progress_bar_container);
        updateBar();
        // Check onResume Method to start accelerometer listener
    }
    
    public void onAccelerationChanged(float x, float y, float z) {
        // TODO Auto-generated method stub
         
    }
    
    @Override
    public void onResume() {
            super.onResume();
            
            MagicianModel magicianModel = new MagicianModel();
            try {
				JSONObject jo = magicianModel.getMagician(getIMEI());
				currentExp = jo.getInt("exp");
				userIs = jo.getString("username");
			} catch (InternetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
            
            
            //Check device supported Accelerometer senssor or not
            if (TrainingSensorManager.isSupported(this)) {
                //Start Accelerometer Listening
            	TrainingSensorManager.startListening(this);
            }
            Bar.setCurrentExpInThisLevel(expInCurrentLevel);
        	Bar.setExpNeededToLevelUp(expNeededToLevelUp);
            Bar.updateExpDisplay(expDisplay);
            Bar.updateBarParameter(bar_parameter);
                        
            int[] levelAndExp = getCurrentLevel(currentExp);
            currentLevel = levelAndExp[0];
        	expNeededToLevelUp = (getNextLevelParam(currentLevel)-getNextLevelParam(currentLevel-1));
    		expInCurrentLevel = levelAndExp[1];
    		
    		// Init bar views
            Bar.initBarVariables(TrainingPage.this);
            Bar.setCurrentExpInThisLevel(expInCurrentLevel);
            Bar.setExpNeededToLevelUp(expNeededToLevelUp);
            Bar.initMagicBall(magic_ball, glass_ball_magic_effect);
            Bar.initUsername(username, userIs);
            Bar.initLevel(level, currentLevel);
            Bar.initBarContainer(expContainer);
            Bar.initBarParameter(bar_parameter);
            Bar.initExpDisplay(expDisplay);
            ProgressBar.initBarVariables(TrainingPage.this);
            ProgressBar.initProgressBar(progress_bar_container);
            updateBar();
    }
    
    public String getIMEI() {
		TelephonyManager tManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		String deviceIMEI = tManager.getDeviceId();

		if (deviceIMEI != null) {
			return deviceIMEI;
		} else {
			return Secure.getString(getApplicationContext()
					.getContentResolver(), Secure.ANDROID_ID);
		}
	}
     
    @Override
    public void onStop() {
            super.onStop();
             
            //Check device supported Accelerometer senssor or not
            if (TrainingSensorManager.isListening()) {
                //Start Accelerometer Listening
                TrainingSensorManager.stopListening();
            }
            magician.setExp(currentExp);
            mm.update(magician);
    }
    
    @Override
    public void onBackPressed() {
    	super.onBackPressed();
    	ProgressBar.showProgressBar(progress_bar_container, content);
    	overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
    }
    
    @Override
    public void onPause() {
    	super.onPause();
    	magician.setExp(currentExp);
        mm.update(magician);
    }
     
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("Sensor", "Service  distroy");
         
        //Check device supported Accelerometer senssor or not
        if (TrainingSensorManager.isListening()) {
            //Start Accelerometer Listening
        	TrainingSensorManager.stopListening();
        }
    }

	@Override
	public void onShake() {
        // Called when Motion Detected
		updateBar();
        Bar.fadeInMagicEffect();
        Bar.fadeOutMagicEffect();
	}
	
	/**
	 * Update player's stats
	 */
	private void updateStatus() {
    	currentExp++;
    	if(currentExp >= getNextLevelParam(currentLevel)) {
    		currentLevel++;
    		Bar.updateLevel(level, currentLevel);
    		expNeededToLevelUp = (getNextLevelParam(currentLevel)-getNextLevelParam(currentLevel-1));
    	}
    	expInCurrentLevel = currentExp-getNextLevelParam(currentLevel-1);
	}
	
	private void updateBar() {
    	updateStatus();
    	Bar.setCurrentExpInThisLevel(expInCurrentLevel);
    	Bar.setExpNeededToLevelUp(expNeededToLevelUp);
        Bar.updateExpDisplay(expDisplay);
        Bar.updateBarParameter(bar_parameter);
	}

	@Override
	public void changeProximity(float nilaiProximity) {
		if (nilaiProximity == 0) {
			WindowManager.LayoutParams lp = getWindow().getAttributes();
			lp.screenBrightness = 0.0f;
			black_screen.setVisibility(View.VISIBLE);
			black_screen.startAnimation(screen_off);
			getWindow().setAttributes(lp);
		} else {
			WindowManager.LayoutParams lp = getWindow().getAttributes();
			lp.screenBrightness = -1.0f;
			screen_on.setAnimationListener(new AnimationListener(){
				@Override
				public void onAnimationEnd(Animation arg0) {
					black_screen.setVisibility(View.GONE);
				}
				@Override
				public void onAnimationRepeat(Animation arg0) {}
				@Override
				public void onAnimationStart(Animation arg0) {}
	        });
			black_screen.startAnimation(screen_on);
			getWindow().setAttributes(lp);
		}
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
