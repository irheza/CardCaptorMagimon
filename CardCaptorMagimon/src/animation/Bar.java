package animation;

import android.app.Activity;
import android.graphics.Point;
import android.view.Display;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Bar {
	private static int screenWidth = 0;
	private static int screenheight = 0;
	private static int exp_bar_width = 0;
	private static int exp_bar_height = 0;
	private static int currentExpInThisLevel = 0;
	private static int expNeededToLevelUp = 0;

	public static void initBarVariables(Activity act) {
		Display screenDisplay = act.getWindowManager().getDefaultDisplay();
		Point size = new Point();
    	screenDisplay.getSize(size);
    	screenWidth = size.x;
    	screenheight = size.y;
    	exp_bar_width = (int)(screenWidth*0.8);
    	exp_bar_height = (int)(screenheight*0.1);
	}
	
	public static void initBarContainer(RelativeLayout container) {
		container.getLayoutParams().width = exp_bar_width;
		container.getLayoutParams().height = exp_bar_height;
		container.setY(exp_bar_height);
	}
	
	public static void initBarParameter(RelativeLayout bar_parameter) {
		bar_parameter.setY(exp_bar_height);
		bar_parameter.setX((int)((screenWidth*0.1)));
		bar_parameter.getLayoutParams().width = 1;
		bar_parameter.getLayoutParams().height = exp_bar_height;
		int toX = 1;
		
		if (currentExpInThisLevel != 0) {
			toX = (int)((currentExpInThisLevel*exp_bar_width)/expNeededToLevelUp);
		}

		ScaleAnimation initBarScalation = new ScaleAnimation(1, toX, 1, 1, bar_parameter.getX(), bar_parameter.getY());
		initBarScalation.setDuration(1000);
		initBarScalation.setFillAfter(true);
		bar_parameter.startAnimation(initBarScalation);
	}
	
	public static void initExpDisplay(TextView expDisplay) {
		expDisplay.setY((float)(exp_bar_height*1.3));
        expDisplay.setText(currentExpInThisLevel + " / " + expNeededToLevelUp);
	}
	
	public static void updateBarParameter(RelativeLayout parameter) {
		int toX = 1;
		
		if (currentExpInThisLevel != 0) {
			toX = (int)((currentExpInThisLevel*exp_bar_width)/expNeededToLevelUp);
		}

		ScaleAnimation barScalation = new ScaleAnimation(1, toX, 1, 1, parameter.getX(), parameter.getY());
		barScalation.setFillAfter(true);
		parameter.startAnimation(barScalation);
	}
	
	public static void updateExpDisplay(TextView expDisplay) {
		expDisplay.setText(currentExpInThisLevel + " / " + expNeededToLevelUp);
	}
	
	public static void setExpNeededToLevelUp(int expNeededToLevelUp) {
		Bar.expNeededToLevelUp = expNeededToLevelUp;
	}
	
	public static void setCurrentExpInThisLevel(int currentExpInThisLevel) {
		Bar.currentExpInThisLevel = currentExpInThisLevel;
	}
}
