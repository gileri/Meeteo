package fr.badgers.meeteo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
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
	ConditionArrayAdapter ad;

	private OnClickListener appuibouton = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			new ConditionDownloader().execute(l);
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
		Button b = (Button) findViewById(R.id.button1);
		b.setOnClickListener(appuibouton);
		cool = (ListView) findViewById(R.id.listView1);
		
	}

	protected Dialog onCreateDialog(int id) {
		return DialogFactory.createDialog(this, id);

	}


private class ConditionDownloader extends AsyncTask<Location, Void, Condition> {
	
	@Override
	protected Condition doInBackground(Location... arg0) {
		ArrayList<Condition> entries;
		try {
			entries = Parser
					.getData("http://api.wunderground.com/api/336d055766c22b31/conditions/lang:FR/" + arg0[0].getLink() + ".xml");
			Condition entry = entries.get(0);
			entry.setLastRefresh(new Date());
			entry.setImage(Downloader.getBitmap(entries.get(0).getImageurlstring()));
			List<Condition> forecastlist = new ArrayList<Condition>();
			forecastlist.add(entry);
			forecastlist.add(entry);
			ad = new ConditionArrayAdapter(MeeteoActivity.this, R.layout.condition, forecastlist); 
			return entry;
		} catch (IOException e) {
			Toast.makeText(MeeteoActivity.this, "Erreur lors de la récupération de données",
					Toast.LENGTH_SHORT).show();
			return null;
		} catch (ConnexionException e) {
			Toast.makeText(MeeteoActivity.this, "Erreur lors de la récupération de données",
					Toast.LENGTH_SHORT).show();
			return null;
		}
	}
	
	protected void onPostExecute(Condition c)
	{
		Toast.makeText(MeeteoActivity.this, "Données actualisées", Toast.LENGTH_SHORT).show();
		cool.setAdapter(ad);
	}
}
}