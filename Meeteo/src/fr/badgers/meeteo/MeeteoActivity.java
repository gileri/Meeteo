package fr.badgers.meeteo;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
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
	ListView cool;

	private OnClickListener appuibouton = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			try {
				getData();
			} catch (ConnexionException e) {
				showDialog(ErrorDialog.CONNECTION);
			}
		}
	};
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if (this.getIntent().getExtras() != null) {
			bu = this.getIntent().getExtras();
			l = (Location) bu.get("location");
		}
		
		setContentView(R.layout.forecast);
//		tempview = (TextView) findViewById(R.id.temp);
//		image = (ImageView) findViewById(R.id.imageView1);
		Button b = (Button) findViewById(R.id.button1);
		b.setOnClickListener(appuibouton);
		cool = (ListView) findViewById(R.id.listView1);	
		
	}

	protected Dialog onCreateDialog(int id) {
		return ErrorDialog.createDialog(this, id);

	}

	public void getData() throws ConnexionException {
		Date cDate = new Date();
		if (condition == null
				|| (cDate.getTime() - condition.getLastRefresh().getTime() > 20000)) {
			condition = ConditionDownloader.getCondition(l);
			condition.setLastRefresh(cDate);
<<<<<<< HEAD
			condition.setImage(Downloader.getBitmap(condition.getImageurlstring()));
=======
			tempview.setText(condition.getConditionString());
			condition.setImage(ConditionDownloader.getBitmap(condition.getImageurlstring()));
			image.setImageBitmap(condition.getImage());
>>>>>>> 9f871493edee854df12d01d9db33970ec8daada1
			Toast.makeText(getApplicationContext(), "Données actualisées",
					Toast.LENGTH_SHORT).show();
			List<Condition> forecastlist = new ArrayList<Condition>();
			forecastlist.add(condition);
			forecastlist.add(condition);
			ConditionArrayAdapter ad = new ConditionArrayAdapter(this, R.layout.condition, forecastlist); 
			cool.setAdapter(ad);
		} else {
			Toast.makeText(getApplicationContext(), "Pas si vite !",
					Toast.LENGTH_SHORT).show();
			;
		}
	}

}