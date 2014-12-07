package com.tekmob.cardcaptormagimon;

import java.util.List;

import org.json.JSONException;

import magicexception.InternetException;
import model.MagicianModel;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import entity.Battle;
import entity.Magician;
import entity.MagicianEnemy;

public class BinderData extends BaseAdapter {

	LayoutInflater inflater;
	ImageView thumb_image;
	List<Battle> battleDataCollection;
	ViewHolder holder;
	MagicianModel magicianModel = new MagicianModel();

	public BinderData() {
		// TODO Auto-generated constructor stub
	}

	public BinderData(Activity act, List<Battle> battles) {
		this.battleDataCollection = battles;

		inflater = (LayoutInflater) act
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public int getCount() {
		// TODO Auto-generated method stub
		// return idlist.size();
		return battleDataCollection.size();
	}

	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		View vi = convertView;
		if (convertView == null) {

			vi = inflater.inflate(R.layout.list_row, null);
			holder = new ViewHolder();

			holder.tvPlayer = (TextView) vi.findViewById(R.id.tvPlayer); // city
																			// name
			holder.tvInfo = (TextView) vi.findViewById(R.id.tvInfo); // city
																		// weather
																		// overview
			holder.tvImage = (ImageView) vi.findViewById(R.id.list_image); // thumb
																			// image

			vi.setTag(holder);
		} else {

			holder = (ViewHolder) vi.getTag();
		}

		// Setting all values in listview

		try {
			MagicianEnemy mgcAtk = new MagicianEnemy(
					magicianModel.getMagician(battleDataCollection
							.get(position).getAttackerID()));
			MagicianEnemy mgcDef = new MagicianEnemy(
					magicianModel.getMagician(battleDataCollection
							.get(position).getDefenderID()));

			holder.tvPlayer.setText(""+mgcAtk.getUsername()+" attacks "+mgcDef.getUsername());
			holder.tvInfo.setText("EXP GAINED: "+ battleDataCollection.get(position).getExp()+ 
					"\nTIME: "+battleDataCollection.get(position).getCreatedAt());

			String status = "" + battleDataCollection.get(position).getStatus();
			int iconName = 0;
			if (status.equals("WIN")) {
				iconName = R.drawable.img_win;
			} else if (status.equals("LOSE")) {
				iconName = R.drawable.img_lose;
			} else if (status.equals("DRAW")) {
				iconName = R.drawable.img_draw;
			}
			Drawable image = vi.getContext().getResources()
					.getDrawable(iconName);
			holder.tvImage.setImageDrawable(image);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InternetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return vi;
	}

	/*
	 * 
	 * */
	static class ViewHolder {

		TextView tvPlayer;
		TextView tvInfo;
		ImageView tvImage;
	}

}
