package fr.badgers.meeteo;

import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MeeteoActivity extends Activity {
	

	TextView tempview = null;
	Button b = null;
	ImageView image;
	Condition condition;
	Toast toast;
	Bundle bu;
	Location l;

	private OnClickListener appuibouton = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			try {
				getData();
			} catch (ConnexionException e) {
				showDialog(R.layout.alert);
			}
		}
	};
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if (this.getIntent().getExtras() != null) {
			bu = this.getIntent().getExtras();
			l = (Location) bu.getSerializable("location");
		}
		
		setContentView(R.layout.condition);
		tempview = (TextView) findViewById(R.id.temp);
		image = (ImageView) findViewById(R.id.imageView1);
		Button b = (Button) findViewById(R.id.button1);
		b.setOnClickListener(appuibouton);
		
	}

	protected Dialog onCreateDialog(int id) {
		Dialog dialog = null;
		switch (id) {

		// Ici on peut définir plusieurs boites de dialogue diférentes
		case R.layout.alert:
			AlertDialog.Builder builder = new AlertDialog.Builder(this);

			// On définit le message à afficher dans la boite de dialogue
			builder.setMessage("Problème lors du téléchargement des données")
					.setCancelable(false)
					.setPositiveButton("Ok",
							new DialogInterface.OnClickListener() {

								// On définit l'action pour le oui
								public void onClick(DialogInterface dialog,
										int id) {
									// On réessaie
									dialog.cancel();
								}
							});
			dialog = builder.create();
			break;

		default:
			dialog = null;
		}
		return dialog;

	}

	public void getData() throws ConnexionException {
		Date cDate = new Date();
		if (condition == null
				|| (cDate.getTime() - condition.getLastRefresh().getTime() > 20000)) {
			condition = Downloader.getCondition(l);
			condition.setLastRefresh(cDate);
			tempview.setText(condition.getConditionString());
			condition.setImage(Downloader.getBitmap(condition.getImageurlstring()));
			image.setImageBitmap(condition.getImage());
			Toast.makeText(getApplicationContext(), "Données actualisées",
					Toast.LENGTH_SHORT).show();
			;
		} else {
			Toast.makeText(getApplicationContext(), "Pas si vite !",
					Toast.LENGTH_SHORT).show();
			;
		}
	}
}