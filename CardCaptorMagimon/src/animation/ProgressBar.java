package animation;

import android.app.Activity;
import android.graphics.Point;
import android.view.Display;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.RelativeLayout;

public class ProgressBar {
	private static int screenWidth = 0;
	private static int screenheight = 0;
	
	public static void initBarVariables(Activity act) {
		Display screenDisplay = act.getWindowManager().getDefaultDisplay();
		Point size = new Point();
    	screenDisplay.getSize(size);
    	screenWidth = size.x;
    	screenheight = size.y;
	}
	
	public static void initProgressBar(RelativeLayout progress_bar_container) {
		progress_bar_container.getLayoutParams().width = (screenWidth*1)/4;
		progress_bar_container.setVisibility(View.GONE);
	}
	
	public static void showProgressBar(RelativeLayout progress_bar_container, RelativeLayout content) {
		AlphaAnimation preHide = new AlphaAnimation(1.0f, 0.0f);
		preHide.setFillAfter(true);
		progress_bar_container.startAnimation(preHide);
		progress_bar_container.setVisibility(View.VISIBLE);
		
		AlphaAnimation hide = new AlphaAnimation(1.0f, 0.0f);
		hide.setDuration(1000);
		hide.setFillAfter(true);
		
		AlphaAnimation show = new AlphaAnimation(0.0f, 1.0f);
		show.setDuration(1000);
		show.setStartOffset(1000);
		show.setFillAfter(true);

		progress_bar_container.startAnimation(show);
		content.startAnimation(hide);
		
		content.setVisibility(View.GONE);
	}
	
	/**
	 * Use with caution. Will be rarely used!
	 * 
	 * @param progress_bar_container
	 * @param content
	 */
	public static void hideProgressBar(RelativeLayout progress_bar_container, RelativeLayout content) {
		AlphaAnimation preHide = new AlphaAnimation(1.0f, 0.0f);
		preHide.setFillAfter(true);
		content.startAnimation(preHide);
		content.setVisibility(View.VISIBLE);
		
		AlphaAnimation hide = new AlphaAnimation(1.0f, 0.0f);
		hide.setDuration(1000);
		hide.setFillAfter(true);
		
		AlphaAnimation show = new AlphaAnimation(0.0f, 1.0f);
		show.setDuration(1000);
		show.setStartOffset(1000);
		show.setFillAfter(true);

		progress_bar_container.startAnimation(hide);
		content.startAnimation(show);
		
		progress_bar_container.setVisibility(View.GONE);
	}
}
