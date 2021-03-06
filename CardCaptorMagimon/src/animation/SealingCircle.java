package animation;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;

public class SealingCircle {
	private ImageView process_circ_outer, process_circ_mid_connect, process_circ_mid_clean;
	private ImageView process_circ_right_up, process_circ_right_down, process_circ_left_up, process_circ_left_down;
	private ImageView process_circ_core, process_circ_glow;
	private boolean process1, process2, process3, process4, process5, process6, process7, process8, process9;
	private int currentPrecentage = 0;
	
	public SealingCircle(ImageView process_circ_outer, ImageView process_circ_mid_connect, 
			ImageView process_circ_mid_clean, ImageView process_circ_right_up, 
			ImageView process_circ_right_down, ImageView process_circ_left_up, 
			ImageView process_circ_left_down, ImageView process_circ_core, ImageView process_circ_glow) {
		this.process_circ_outer = process_circ_outer;
		this.process_circ_mid_connect = process_circ_mid_connect;
		this.process_circ_mid_clean = process_circ_mid_clean;
		this.process_circ_right_up = process_circ_right_up;
		this.process_circ_right_down = process_circ_right_down;
		this.process_circ_left_up = process_circ_left_up;
		this.process_circ_left_down = process_circ_left_down;
		this.process_circ_core = process_circ_core;
		this.process_circ_glow = process_circ_glow;
		process1 = false;
		process2 = false;
		process3 = false;
		process4 = false;
		process5 = false;
		process6 = false;
		process7 = false;
		process8 = false;
		process9 = false;
		setGone();
	}
	
	private void setGone() {
		process_circ_outer.setVisibility(View.GONE);
		process_circ_mid_connect.setVisibility(View.GONE);
		process_circ_mid_clean.setVisibility(View.GONE);
		process_circ_right_up.setVisibility(View.GONE);
		process_circ_right_down.setVisibility(View.GONE);
		process_circ_left_up.setVisibility(View.GONE);
		process_circ_left_down.setVisibility(View.GONE);
		process_circ_core.setVisibility(View.GONE);
		process_circ_glow.setVisibility(View.GONE);
	}
	
	public void updateCircle(int percentage) {
		currentPrecentage = percentage;
		
		if (currentPrecentage >= 10 && process1 == false) {
			process1 = true;
			process_circ_mid_clean.setVisibility(View.VISIBLE);
		}

		if (currentPrecentage >= 20 && process2 == false) {
			process2 = true;
			process_circ_left_up.setVisibility(View.VISIBLE);
		}

		if (currentPrecentage >= 30 && process3 == false) {
			process3 = true;
			process_circ_right_up.setVisibility(View.VISIBLE);
		}

		if (currentPrecentage >= 40 && process4 == false) {
			process4 = true;
			process_circ_right_down.setVisibility(View.VISIBLE);
		}

		if (currentPrecentage >= 50 && process5 == false) {
			process5 = true;
			process_circ_left_down.setVisibility(View.VISIBLE);
		}

		if (currentPrecentage >= 65 && process6 == false) {
			process6 = true;
			process_circ_outer.setVisibility(View.VISIBLE);
		}

		if (currentPrecentage >= 75 && process7 == false) {
			process7 = true;
			process_circ_core.setVisibility(View.VISIBLE);
		}

		if (currentPrecentage >= 85 && process8 == false) {
			process8 = true;
			process_circ_mid_connect.setVisibility(View.VISIBLE);
		}

		if (currentPrecentage >= 95 && process9 == false) {
			process9 = true;
			process_circ_glow.setVisibility(View.VISIBLE);
		}
		
	}
}
