package fr.badgers.meeteo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class LocationChooserActivity extends Activity implements View.OnClickListener{
	
	private List <Location> locations;
	private String currlocation;
	private ListView locationView;
	
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
		
		try {
			locations = Downloader.getLocations(currlocation);
			List<String> lStrings = new ArrayList<String>();
			for (Location l : locations)
				lStrings.add(l.getName());
			((ListView) findViewById(R.id.listView1)).setAdapter(
					new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lStrings));
		} catch (ConnexionException e) {
			showDialog(ErrorDialog.CONNECTION);
		}
	}
	
	protected Dialog onCreateDialog(int id) {
		return ErrorDialog.createDialog(this, id);
	}

	private void goToCC(Location l)
	{
		Bundle bundle = new Bundle();
		bundle.putSerializable("location", (Serializable) l);
		Intent newIntent = new Intent(this.getApplicationContext(), MeeteoActivity.class);
		newIntent.putExtras(bundle);
		startActivityForResult(newIntent, 0);
	}
}
