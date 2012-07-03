package fr.badgers.meeteo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MeeteoFragment extends Fragment {
	TextView tempview = null;
	Button b = null;
	ImageView image;
	Condition condition;
	Toast toast;
	Bundle bu;
	Location l;
	ListView cool;
	ConditionArrayAdapter ad;
	LocationSource mCallback;
	
	public interface LocationSource {
		public Location getSelectedLocation();
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		// This makes sure that the container activity has implemented
		// the callback interface. If not, it throws an exception
		try {
			mCallback = (LocationSource) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement LocationSource");
		}
	}
	

	private OnClickListener appuibouton = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			l = mCallback.getSelectedLocation();
			if(l == null)
				Toast.makeText(getActivity(), "Aucune ville sélectionnée",
						Toast.LENGTH_SHORT).show();
			else
				new ConditionDownloader().execute(l);
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		View v = inflater.inflate(R.layout.forecast, container, false);
		Button b = (Button) v.findViewById(R.id.button1);
		b.setOnClickListener(appuibouton);
		
		if(ad == null) {
		ad = new ConditionArrayAdapter(getActivity(), R.layout.condition,
				new ArrayList<Condition>());
		}
		((ListView) v.findViewById(R.id.listView1)).setAdapter(ad);
		return v;
	}

	protected Dialog onCreateDialog(int id) {
		return DialogFactory.createDialog(getActivity(), id);

	}

	private class ConditionDownloader extends
			AsyncTask<Location, Void, Condition> {

		@Override
		protected Condition doInBackground(Location... arg0) {
			ArrayList<Condition> entries;
			try {
				entries = Parser
						.getData("http://api.wunderground.com/api/336d055766c22b31/conditions/lang:FR/"
								+ arg0[0].getLink() + ".xml");
				Condition entry = entries.get(0);
				entry.setLastRefresh(new Date());
				entry.setImage(Downloader.getBitmap(entries.get(0)
						.getImageurlstring()));
				return entry;
			} catch (IOException e) {
				Toast.makeText(getActivity(),
						"Erreur lors de la récupération de données",
						Toast.LENGTH_SHORT).show();
				return null;
			} catch (ConnexionException e) {
				Toast.makeText(getActivity(),
						"Erreur lors de la récupération de données",
						Toast.LENGTH_SHORT).show();
				return null;
			}
		}

		protected void onPostExecute(Condition c) {
			ad.clear();
			ad.add(c);
			ad.notifyDataSetChanged();
			Toast.makeText(getActivity(), "Données actualisées",
					Toast.LENGTH_SHORT).show();
		}
	}
}
