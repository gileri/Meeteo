package fr.badgers.meeteo;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

public class LocationChooserActivity extends Activity implements View.OnClickListener{
	
	private List<Location> locations;
	private String currlocation;
	private ListView locationView;
	private List<String> lStrings;
	private Toast Toast;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.locationchooser);
		((EditText) findViewById(R.id.searchlocationfield)).addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				currlocation = s.toString();
			}
		});

		((ImageButton) findViewById(R.id.geolookupbutton)).setOnClickListener(this);
		locationView = ((ListView) findViewById(R.id.listView1));
		locationView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position,
					long id) {
				goToCC(locations.get(position));
				
			}
		});
	}

	@Override
	public void onClick(View v) {
		LocationDownloader ld = new LocationDownloader();
		ld.execute(currlocation);
	}
	
	private void goToCC(Location l)
	{
		Bundle bundle = new Bundle();
		bundle.putSerializable("location", (Serializable) l);
		Intent newIntent = new Intent(this.getApplicationContext(), MeeteoActivity.class);
		newIntent.putExtras(bundle);
		startActivityForResult(newIntent, 0);
	}
	
	private class LocationDownloader extends AsyncTask<String, Void, ArrayList<Location>>
	{

		@Override
		protected ArrayList<Location> doInBackground(String... params) {
			try {
				locations = ParserGeoLookup
				.getData("http://autocomplete.wunderground.com/aq?query=" + params[0] + "&format=xml&c=FR");
				lStrings = new ArrayList<String>();
				for (Location l : locations)
					lStrings.add(l.getName());
				return (ArrayList<Location>) locations;
			} catch (IOException e) {
				Log.v("meeteo", "fu u");
				return null;
			}
			
		}
		protected void onPostExecute(ArrayList<Location> result) {
			Log.v("meeteo", String.valueOf(lStrings.size()));
			if (lStrings.size() != 0)
				((ListView) findViewById(R.id.listView1)).setAdapter(
						new ArrayAdapter<String>(LocationChooserActivity.this, android.R.layout.simple_list_item_1, lStrings));
			else
				Toast.makeText(getApplicationContext(), "Aucune ville trouvée",
						Toast.LENGTH_SHORT).show();
		}
		
	}
}