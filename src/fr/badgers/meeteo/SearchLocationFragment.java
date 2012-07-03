package fr.badgers.meeteo;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class SearchLocationFragment extends Fragment {

	private String currentSearch;
	private ListView locationView;
	private List<Location> locations;
	private LocationAdapter la;
	public Location currentLocation;
	private LocationReceiver mCallback;
	private View v;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		if (locations == null)
			locations = new ArrayList<Location>();
		if(v == null)
			v = inflater.inflate(R.layout.locationsearcher, container, false);
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
						currentSearch = s.toString();
					}
				});
		if (currentSearch != null)
			((EditText) v.findViewById(R.id.searchlocationfield))
					.setText(currentSearch);
		((ImageButton) v.findViewById(R.id.geolookupbutton))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						LocationDownloader ld = new LocationDownloader();
						ld.execute(currentSearch);
					}
				});
		((EditText) v.findViewById(R.id.searchlocationfield))
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView v, int actionId,
							KeyEvent event) {
						if (actionId == EditorInfo.IME_ACTION_SEARCH) {
							LocationDownloader ld = new LocationDownloader();
							ld.execute(currentSearch);
							return true;
						}
						return false;
					}
				});

		locationView = ((ListView) v.findViewById(R.id.listView1));
		locationView
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View view,
							int position, long id) {
						mCallback.selectLocation(locations.get(position));

					}
				});
		la = new LocationAdapter();
		locationView.setAdapter(la);
		return v;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		// This makes sure that the container activity has implemented
		// the callback interface. If not, it throws an exception
		try {
			mCallback = (LocationReceiver) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement LocationReceiver");
		}
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
		newIntent.setClassName("fr.badgers.meeteo",
				"fr.badgers.meeteo.MeeteoActivity");
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
			TextView tv = new TextView(
					SearchLocationFragment.this.getActivity());
			tv.setText(locations.get(position).getName());
			tv.setTextAppearance(getActivity(),
					android.R.style.TextAppearance_Large);
			tv.setTextAppearance(getActivity(),
					android.R.attr.textAppearanceLarge);
			return tv;
		}

	}
	
	@Override
	public void onDestroyView() {
		super.onDestroyView();
		v = null;
	}

}
