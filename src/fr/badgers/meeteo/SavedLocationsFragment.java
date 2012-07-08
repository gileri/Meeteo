package fr.badgers.meeteo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import fr.badgers.meeteo.SearchLocationFragment.UpdateSP;

public class SavedLocationsFragment extends Fragment implements UpdateSP {

	ListView v;
	private List<Location> l;
	private LocationReceiver mCallback;
	
	public static final String PREFS_NAME = "MeeteoSavedLocations";
	private SharedPreferences settings;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		v = (ListView) inflater.inflate(R.layout.savedlocations, container,
				false);
		v.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				mCallback.selectLocation(l.get(arg2));
			}
		});
		v.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				Log.v("meeteo", l.get(arg2).toString());
				return true;
			}
		});
		v.setAdapter(new SavedLocationAdapter());
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

	private class SavedLocationAdapter extends BaseAdapter {

		public SavedLocationAdapter() {
			super();
			initSP();
			updateSP();
		}

		@Override
		public int getCount() {
			return l.size();
		}

		@Override
		public Object getItem(int arg0) {
			return l.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			return 0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			TextView tv = new TextView(getActivity());
			tv.setText(((Location) l.get(arg0)).getName());
			return tv;
		}

		private void initSP() {
			if (settings == null)
				settings = getActivity().getSharedPreferences(PREFS_NAME, 0);
		}

	}

	@Override
	public void updateSP() {
		l = new ArrayList<Location>();
		int count = settings.getInt("count", 0);
		if (count > 0)
			for (int i = 0; i < count; ++i) {
				String link = settings.getString("link" + i, null);
				try {
					l.addAll(ParserGeolookup.getData(link));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
	}

}
