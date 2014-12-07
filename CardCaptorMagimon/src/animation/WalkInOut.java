package animation;

import android.app.Activity;
import android.graphics.Point;
import android.view.Display;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;

public class WalkInOut {
	private int screenWidth = 0;
	private int screenheight = 0;
	private int imageWidth = 0;
	private TranslateAnimation right_mid, left_mid, mid_right, mid_left;
	private AlphaAnimation fade_in, fade_out;
	private ImageView leftImage, rightImage;
	
	public void initSign(Activity act, ImageView leftImage, ImageView rightImage) {
		Display screenDisplay = act.getWindowManager().getDefaultDisplay();
		Point size = new Point();
    	screenDisplay.getSize(size);
    	screenWidth = size.x;
    	screenheight = size.y;
		
    	this.leftImage = leftImage;
    	this.rightImage = rightImage;
    	
    	imageWidth = (int) (screenWidth*0.5);
    	leftImage.getLayoutParams().width = imageWidth;
    	rightImage.getLayoutParams().width = imageWidth;
    	
    	initTopAnimation();
    	initBottomAnimation();
    	//init_fade_out();
    	//init_fade_in();
    	//setInvisible();
	}
	
	public void startMoving() {
		leftImage.startAnimation(left_mid);
		rightImage.startAnimation(right_mid);
	}
	
	private void initTopAnimation() {
		left_mid = new TranslateAnimation((0-imageWidth), (screenWidth-imageWidth)/2, 0, 0);
		left_mid.setDuration(3000);
		left_mid.setFillAfter(true);
		left_mid.setInterpolator(new DecelerateInterpolator((float) 0.8));
		left_mid.setAnimationListener(new AnimationListener(){
			@Override
			public void onAnimationEnd(Animation arg0) {
				leftImage.startAnimation(mid_right);
			}
			@Override
			public void onAnimationRepeat(Animation arg0) {}
			@Override
			public void onAnimationStart(Animation arg0) {}
        });
		
		mid_right = new TranslateAnimation((screenWidth-imageWidth)/2, screenWidth, 0, 0);
		mid_right.setDuration(2000);
		mid_right.setFillAfter(true);
		mid_right.setInterpolator(new AccelerateInterpolator((float) 3));
	}
	
	private void initBottomAnimation() {
		right_mid = new TranslateAnimation(screenWidth, (screenWidth-imageWidth)/2, 0, 0);
		right_mid.setDuration(3000);
		right_mid.setFillAfter(true);
		right_mid.setInterpolator(new DecelerateInterpolator((float) 0.8));
		right_mid.setAnimationListener(new AnimationListener(){
			@Override
			public void onAnimationEnd(Animation arg0) {
				rightImage.startAnimation(mid_left);
			}
			@Override
			public void onAnimationRepeat(Animation arg0) {}
			@Override
			public void onAnimationStart(Animation arg0) {}
        });
		
		mid_left = new TranslateAnimation((screenWidth-imageWidth)/2, (0-imageWidth), 0, 0);
		mid_left.setDuration(2000);
		mid_left.setFillAfter(true);
		mid_left.setInterpolator(new AccelerateInterpolator((float) 3));
	}
	
	private void init_fade_out() {
		fade_out = new AlphaAnimation(1.0f, 0.0f);
		fade_out.setStartOffset(6000);
		fade_out.setFillAfter(true);
	}
	
	private void init_fade_in() {
		fade_in = new AlphaAnimation(0.0f, 1.0f);
		fade_in.setFillAfter(true);
	}
	
	private void setInvisible() {
		AlphaAnimation hide = new AlphaAnimation(1.0f, 0.0f);
		hide.setFillAfter(true);
		leftImage.startAnimation(hide);
		rightImage.startAnimation(hide);
		leftImage.setVisibility(View.VISIBLE);
		rightImage.setVisibility(View.VISIBLE);
	}
}
