package com.tekmob.cardcaptormagimon;

import java.io.IOException;
import java.util.ArrayList;

import magicexception.InternetException;
import model.*;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import entity.*;

public class DeckPage extends Activity {
	Magician magician;
	ArrayList<PersonalMagimon> partners = new ArrayList<PersonalMagimon>();
	TextView listMagimon;
	MagimonModel magimonmodel = new MagimonModel();
	PersonalMagimonModel pmmodel = new PersonalMagimonModel();
	final Context context = this;
	int atkModeCounter;
	int defModeCounter;
	final String ATK = "1";
	final String DEF = "2";
	final String NOPE = "0";
	ImageButton magimon1;
	ImageButton magimon2;
	ImageButton magimon3;
	ImageButton magimon4;
	ImageButton magimon5;
	ImageButton magimon6;
	ImageButton remove1;
	ImageButton remove2;
	ImageButton remove3;
	ImageButton remove4;
	ImageButton remove5;
	ImageButton remove6;
	ImageView mode1;
	ImageView mode2;
	ImageView mode3;
	ImageView mode4;
	ImageView mode5;
	ImageView mode6;
	TextView value1;
	TextView value2;
	TextView value3;
	TextView value4;
	TextView value5;
	TextView value6;
	TextView nama1;
	TextView nama2;
	TextView nama3;
	TextView nama4;
	TextView nama5;
	TextView nama6;
	ImageButton submitDeck;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.activity_deck_page);
		magician = (Magician) getApplicationContext();
		try {
			magician.setPersonalMagimon(pmmodel
					.getPersonalMagimonByMagician(magician.getId()));
		} catch (InternetException e) {
			try {
				magician = (Magician) InternalStorage.readObject(this,
						"MAGICIAN_DATA");
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		setButton();
		setAllMagimon();
		setMagimonButtonListener();
		setSubmitButtonListener();
		setRemoveButtonListener();

	}

	@Override
    public void onBackPressed() {
    	super.onBackPressed();
    	overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
    }
	
	public void setMagimonButtonListener() {
		magimon1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				changeModeFor(0);
				refreshButtonText(0);
			}
		});
		magimon2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				changeModeFor(1);
				refreshButtonText(1);
			}
		});
		magimon3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				changeModeFor(2);
				refreshButtonText(2);
			}
		});
		magimon4.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				changeModeFor(3);
				refreshButtonText(3);
			}
		});
		magimon5.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				changeModeFor(4);
				refreshButtonText(4);
			}
		});
		magimon6.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				changeModeFor(5);
				refreshButtonText(5);
			}
		});
	}

	
	public void setAllMagimon() {
		partners = magician.getPersonalMagimon();
		if (partners.size() >= 1) {
			refreshButtonText(0);
		} else {
			magimon1.setVisibility(View.GONE);
			magimon2.setVisibility(View.GONE);
			magimon3.setVisibility(View.GONE);
			magimon4.setVisibility(View.GONE);
			magimon5.setVisibility(View.GONE);
			magimon6.setVisibility(View.GONE);
			remove1.setVisibility(View.GONE);
			remove2.setVisibility(View.GONE);
			remove3.setVisibility(View.GONE);
			remove4.setVisibility(View.GONE);
			remove5.setVisibility(View.GONE);
			remove6.setVisibility(View.GONE);
			mode1.setVisibility(View.GONE);
			mode2.setVisibility(View.GONE);
			mode3.setVisibility(View.GONE);
			mode4.setVisibility(View.GONE);
			mode5.setVisibility(View.GONE);
			mode6.setVisibility(View.GONE);
			value1.setVisibility(View.GONE);
			value2.setVisibility(View.GONE);
			value3.setVisibility(View.GONE);
			value4.setVisibility(View.GONE);
			value5.setVisibility(View.GONE);
			value6.setVisibility(View.GONE);
			nama1.setVisibility(View.GONE);
			nama2.setVisibility(View.GONE);
			nama3.setVisibility(View.GONE);
			nama4.setVisibility(View.GONE);
			nama5.setVisibility(View.GONE);
			nama6.setVisibility(View.GONE);
		}
		if (partners.size() >= 2) {
			refreshButtonText(1);
		} else {
			magimon2.setVisibility(View.GONE);
			magimon3.setVisibility(View.GONE);
			magimon4.setVisibility(View.GONE);
			magimon5.setVisibility(View.GONE);
			magimon6.setVisibility(View.GONE);
			remove2.setVisibility(View.GONE);
			remove3.setVisibility(View.GONE);
			remove4.setVisibility(View.GONE);
			remove5.setVisibility(View.GONE);
			remove6.setVisibility(View.GONE);
			mode2.setVisibility(View.GONE);
			mode3.setVisibility(View.GONE);
			mode4.setVisibility(View.GONE);
			mode5.setVisibility(View.GONE);
			mode6.setVisibility(View.GONE);
			value2.setVisibility(View.GONE);
			value3.setVisibility(View.GONE);
			value4.setVisibility(View.GONE);
			value5.setVisibility(View.GONE);
			value6.setVisibility(View.GONE);
			nama2.setVisibility(View.GONE);
			nama3.setVisibility(View.GONE);
			nama4.setVisibility(View.GONE);
			nama5.setVisibility(View.GONE);
			nama6.setVisibility(View.GONE);
		}
		if (partners.size() >= 3) {
			refreshButtonText(2);
		} else {
			magimon3.setVisibility(View.GONE);
			magimon4.setVisibility(View.GONE);
			magimon5.setVisibility(View.GONE);
			magimon6.setVisibility(View.GONE);
			remove3.setVisibility(View.GONE);
			remove4.setVisibility(View.GONE);
			remove5.setVisibility(View.GONE);
			remove6.setVisibility(View.GONE);
			mode3.setVisibility(View.GONE);
			mode4.setVisibility(View.GONE);
			mode5.setVisibility(View.GONE);
			mode6.setVisibility(View.GONE);
			value3.setVisibility(View.GONE);
			value4.setVisibility(View.GONE);
			value5.setVisibility(View.GONE);
			value6.setVisibility(View.GONE);
			nama3.setVisibility(View.GONE);
			nama4.setVisibility(View.GONE);
			nama5.setVisibility(View.GONE);
			nama6.setVisibility(View.GONE);
		}
		if (partners.size() >= 4) {
			refreshButtonText(3);
		} else {
			magimon4.setVisibility(View.GONE);
			magimon5.setVisibility(View.GONE);
			magimon6.setVisibility(View.GONE);
			remove4.setVisibility(View.GONE);
			remove5.setVisibility(View.GONE);
			remove6.setVisibility(View.GONE);
			mode4.setVisibility(View.GONE);
			mode5.setVisibility(View.GONE);
			mode6.setVisibility(View.GONE);
			value4.setVisibility(View.GONE);
			value5.setVisibility(View.GONE);
			value6.setVisibility(View.GONE);
			nama4.setVisibility(View.GONE);
			nama5.setVisibility(View.GONE);
			nama6.setVisibility(View.GONE);
		}
		if (partners.size() >= 5) {
			refreshButtonText(4);
		} else {
			magimon5.setVisibility(View.GONE);
			magimon6.setVisibility(View.GONE);
			remove5.setVisibility(View.GONE);
			remove6.setVisibility(View.GONE);
			mode5.setVisibility(View.GONE);
			mode6.setVisibility(View.GONE);
			value5.setVisibility(View.GONE);
			value6.setVisibility(View.GONE);
			nama5.setVisibility(View.GONE);
			nama6.setVisibility(View.GONE);
		}
		if (partners.size() >= 6) {
			refreshButtonText(5);
		} else {
			magimon6.setVisibility(View.GONE);
			remove6.setVisibility(View.GONE);
			mode6.setVisibility(View.GONE);
			value6.setVisibility(View.GONE);
			nama6.setVisibility(View.GONE);
		}

	}

	public void setButton() {
		magimon1 = (ImageButton) findViewById(R.id.magimon1);
		magimon2 = (ImageButton) findViewById(R.id.magimon2);
		magimon3 = (ImageButton) findViewById(R.id.magimon3);
		magimon4 = (ImageButton) findViewById(R.id.magimon4);
		magimon5 = (ImageButton) findViewById(R.id.magimon5);
		magimon6 = (ImageButton) findViewById(R.id.magimon6);
		remove1 = (ImageButton) findViewById(R.id.remove1);
		remove2 = (ImageButton) findViewById(R.id.remove2);
		remove3 = (ImageButton) findViewById(R.id.remove3);
		remove4 = (ImageButton) findViewById(R.id.remove4);
		remove5 = (ImageButton) findViewById(R.id.remove5);
		remove6 = (ImageButton) findViewById(R.id.remove6);
		mode1 = (ImageView) findViewById(R.id.mode1);
		mode2 = (ImageView) findViewById(R.id.mode2);
		mode3 = (ImageView) findViewById(R.id.mode3);
		mode4 = (ImageView) findViewById(R.id.mode4);
		mode5 = (ImageView) findViewById(R.id.mode5);
		mode6 = (ImageView) findViewById(R.id.mode6);
		value1 = (TextView) findViewById(R.id.value1);
		value2 = (TextView) findViewById(R.id.value2);
		value3 = (TextView) findViewById(R.id.value3);
		value4 = (TextView) findViewById(R.id.value4);
		value5 = (TextView) findViewById(R.id.value5);
		value6 = (TextView) findViewById(R.id.value6);
		nama1 = (TextView) findViewById(R.id.nama1);
		nama2 = (TextView) findViewById(R.id.nama2);
		nama3 = (TextView) findViewById(R.id.nama3);
		nama4 = (TextView) findViewById(R.id.nama4);
		nama5 = (TextView) findViewById(R.id.nama5);
		nama6 = (TextView) findViewById(R.id.nama6);
		submitDeck = (ImageButton) findViewById(R.id.btnSubmit);

	}

	public int countAtkMode() {
		int countAtk = 0;
		for (PersonalMagimon pm : partners) {
			if (pm.getMode().equals(ATK)) {
				countAtk++;
			}
		}
		return countAtk;
	}

	public int countDefMode() {
		int countDef = 0;
		for (PersonalMagimon pm : partners) {
			if (pm.getMode().equals(DEF)) {
				countDef++;
			}
		}
		return countDef;
	}
	
	public void changeModeFor(int urutanpartner) {
		if (partners.get(urutanpartner).getMode().equals(NOPE)) {
			if (countAtkMode() >= 3) {
				partners.get(urutanpartner).setMode(DEF);
			} else {
				partners.get(urutanpartner).setMode(ATK);
			}
		} else if (partners.get(urutanpartner).getMode().equals(ATK)) {
			if (countDefMode() >= 3) {
				partners.get(urutanpartner).setMode(NOPE);
			} else {
				partners.get(urutanpartner).setMode(DEF);
			}
		} else {
			if (countAtkMode() >= 3) {
				partners.get(urutanpartner).setMode(NOPE);
			} else {
				partners.get(urutanpartner).setMode(ATK);
			}
		}
	}
	public void setRemoveButtonListener() {
		remove1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				alertRemoveShow(remove1, magimon1, mode1, value1, nama1, 0);
			}
		});
		remove2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				alertRemoveShow(remove2, magimon2, mode2, value2, nama2, 1);
			}
		});
		remove3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				alertRemoveShow(remove3, magimon3, mode3, value3, nama3, 2);
			}
		});
		remove4.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				alertRemoveShow(remove4, magimon4, mode4, value4, nama4, 3);
			}
		});
		remove5.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				alertRemoveShow(remove5, magimon5, mode5, value5, nama5, 4);
			}
		});
		remove6.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				alertRemoveShow(remove6, magimon6, mode6, value6, nama6, 5);
			}
		});

	}

	public void setSubmitButtonListener() {
		submitDeck.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {

				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				builder.setTitle("Submit Deck");

				builder.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog, int id) {
								try {
									magician.setPersonalMagimon(partners);
									for (PersonalMagimon pm : partners) {
										pmmodel.update(pm);
									}
								} catch (InternetException ie) {
									showNoInternetAlert();
								}
							}
						});
				builder.setNegativeButton("Nope",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();

							}
						});
				AlertDialog alertdialog = builder.create();
				alertdialog.show();

			}
		});

	}

	public void showNoInternetAlert() {
		new AlertDialog.Builder(this)
				.setTitle("No Internet Connection")
				.setMessage(
						"Items cannot be submitted because there is no internet connection right now.")
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// continue with delete
					}
				}).setIcon(android.R.drawable.ic_dialog_alert).show();
	}

	public void alertRemoveShow(ImageButton buttonRemove, ImageButton magimonButton,
			ImageView mode, TextView value, TextView nama, int numberRemoved) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("Remove Magimon?");
		final ImageButton button = buttonRemove;
		final ImageButton magimon = magimonButton;
		final ImageView modes = mode;
		final TextView values = value;
		final TextView names = nama;
		final int partnerNumber = numberRemoved;

		builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int id) {
				partners.remove(partnerNumber);
				button.setVisibility(View.GONE);
				magimon.setVisibility(View.GONE);
				modes.setVisibility(View.GONE);
				values.setVisibility(View.GONE);
				names.setVisibility(View.GONE);
			}
		});
		
		builder.setPositiveButton("Yes", new DialogInterface.OnClickListener()
		{
			
			public void onClick(DialogInterface dialog, int id)
			{
				boolean status = pmmodel.delete(partners.get(partnerNumber));
				System.out.println("Status delete :" + status);
				if (status) {
					partners.remove(partnerNumber);
					button.setVisibility(View.GONE);
					magimon.setVisibility(View.GONE);
					modes.setVisibility(View.GONE);
					values.setVisibility(View.GONE);
					names.setVisibility(View.GONE);
				}
			}
		});
		builder.setNegativeButton("Nope",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();

					}
				});
		AlertDialog alertdialog = builder.create();
		alertdialog.show();

	}

	// urutanpartner mulai dari 0
	
	public void refreshButtonText(int urutanpartner) {
		ImageButton refreshedButton;
		ImageView modes;
		TextView values;
		TextView names;
		if (urutanpartner == 0) {
			refreshedButton = magimon1;
			modes = mode1;
			values = value1;
			names = nama1;
		} else if (urutanpartner == 1) {
			refreshedButton = magimon2;
			modes = mode2;
			values = value2;
			names = nama2;
		} else if (urutanpartner == 2) {
			refreshedButton = magimon3;
			modes = mode3;
			values = value3;
			names = nama3;
		} else if (urutanpartner == 3) {
			refreshedButton = magimon4;
			modes = mode4;
			values = value4;
			names = nama4;
		} else if (urutanpartner == 4) {
			refreshedButton = magimon5;
			modes = mode5;
			values = value5;
			names = nama5;
		} else {
			refreshedButton = magimon6;
			modes = mode6;
			values = value6;
			names = nama6;
		}
		PersonalMagimon personalMagimon = partners.get(urutanpartner);

		try {
			Magimon partner = magimonmodel.getMagimon(personalMagimon
					.getMagimonID());
			refreshedButton.setImageResource(getResourceImage(partner.getId()));
			if (personalMagimon.getMode().equals("1")) {
				modes.setImageResource(R.drawable.attack);
				modes.setScaleType(ImageView.ScaleType.FIT_CENTER);
				values.setText(""+partner.getAttack());
				names.setText(partner.getName());
			} else if (personalMagimon.getMode().equals("2")) {
				modes.setImageResource(R.drawable.defend);
				modes.setScaleType(ImageView.ScaleType.FIT_CENTER);
				values.setText(""+partner.getDefense());
				names.setText(partner.getName());
			} else {
				modes.setImageResource(R.drawable.none);
				modes.setScaleType(ImageView.ScaleType.FIT_CENTER);
				values.setText("None");
				names.setText(partner.getName());
			}
		} catch (InternetException ie) {
			ArrayList<Magimon> magimons = magician.getMagimonCache();
			for (Magimon magimon : magimons) {
				if (magimon.getId().equals(personalMagimon.getMagimonID())) {
					Magimon partner = magimon;
					refreshedButton.setImageResource(getResourceImage(partner.getId()));
					if (personalMagimon.getMode().equals("1")) {
						modes.setImageResource(R.drawable.attack);
						modes.setScaleType(ImageView.ScaleType.FIT_CENTER);
						values.setText(""+partner.getAttack());
						names.setText(partner.getName());
					} else if (personalMagimon.getMode().equals("2")) {
						modes.setImageResource(R.drawable.defend);
						modes.setScaleType(ImageView.ScaleType.FIT_CENTER);
						values.setText(""+partner.getDefense());
						names.setText(partner.getName());
					} else {
						modes.setImageResource(R.drawable.none);
						modes.setScaleType(ImageView.ScaleType.FIT_CENTER);
						values.setText("None");
						names.setText(partner.getName());
					}
				}
			}

		}
	}
	
	private int getResourceImage(String id_magimon) {
		int ret = 0;
		if (id_magimon.equals(String.valueOf(1))) {
			ret = (R.drawable.neko);
		}
		else if (id_magimon.equals(String.valueOf(2))) {
			ret = (R.drawable.egg);
		}
		else if (id_magimon.equals(String.valueOf(3))) {
			ret = (R.drawable.dragon);
		} else {
			Toast.makeText(getBaseContext(), "Failed",
	                Toast.LENGTH_SHORT).show();
		}
		return ret;
	}
}
