package fr.badgers.meeteo;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class SearchLocationFragment extends Fragment implements OnClickListener {

	private String currlocation;
	private ListView locationView;
	private List<Location> locations;
	private LocationAdapter la;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		locations = new ArrayList<Location>();
		View v = inflater.inflate(R.layout.locationsearcher, container, false);
		((EditText) v.findViewById(R.id.searchlocationfield))
				.addTextChangedListener(new TextWatcher() {

					@Override
					public void onTextChanged(CharSequence s, int start,
							int before, int count) {

					}

					@Override
					public void beforeTextChanged(CharSequence s, int start,
							int count, int after) {
					}

					@Override
					public void afterTextChanged(Editable s) {
						currlocation = s.toString();
					}
				});

		((ImageButton) v.findViewById(R.id.geolookupbutton))
				.setOnClickListener(this);
		locationView = ((ListView) v.findViewById(R.id.listView1));
		locationView
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View view,
							int position, long id) {
						goToCC(locations.get(position));

					}
				});
		la = new LocationAdapter();
		locationView.setAdapter(la);
		return v;
	}

	@Override
	public void onClick(View v) {
		LocationDownloader ld = new LocationDownloader();
		ld.execute(currlocation);
	}

	private class LocationDownloader extends
			AsyncTask<String, Void, ArrayList<Location>> {

		@Override
		protected ArrayList<Location> doInBackground(String... params) {
			try {
				locations = ParserGeoLookup
						.getData("http://autocomplete.wunderground.com/aq?query="
								+ params[0] + "&format=xml&c=FR");
				return (ArrayList<Location>) locations;
			} catch (IOException e) {
				return null;
			}

		}

		protected void onPostExecute(ArrayList<Location> result) {
			la.notifyDataSetChanged();
		}

	}
	
	private void goToCC(Location l) {
		Bundle bundle = new Bundle();
		bundle.putSerializable("location", (Serializable) l);
		Intent newIntent = new Intent();
		newIntent.setClassName("fr.badgers.meeteo", "fr.badgers.meeteo.MeeteoActivity");
		newIntent.putExtras(bundle);
		startActivityForResult(newIntent, 0);
	}
	
	private class LocationAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return locations.size();
		}

		@Override
		public Object getItem(int position) {
			return locations.get(position);
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			TextView tv = new TextView(SearchLocationFragment.this.getActivity());
			tv.setText(locations.get(position).getName());
			tv.setTextAppearance(getActivity(), android.R.attr.textAppearanceLarge);
			return tv;
		}
		
	}
}
