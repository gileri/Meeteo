package fr.badgers.meeteo;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

public class ConditionDownloader extends AsyncTask<Location, Integer, Condition> {

	static public ConditionDownloader downloader = null;

	public static Condition getCondition(Location l) throws ConnexionException {
		try {
			ArrayList<Condition> entries;
			Log.v("meeteo", l.toString());
			Log.v("meeteo", l.getLink());
			entries = Parser
					.getData("http://api.wunderground.com/api/336d055766c22b31/conditions/lang:FR/" + l.getLink() + ".xml");
			return entries.get(0);
		} catch (IOException e) {
			throw new ConnexionException();
		}
	}
	
	public static ArrayList<Location> getLocations(String query) throws ConnexionException {
		try {
			return ParserGeoLookup
					.getData("http://autocomplete.wunderground.com/aq?query=" + query + "&format=xml&c=FR");
		} catch (IOException e) {
			throw new ConnexionException();
		}
	}

	public static Bitmap getBitmap(String url) throws ConnexionException {
		try {
			URL urlImage = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) urlImage
					.openConnection();
			InputStream inputStream = connection.getInputStream();
			return BitmapFactory.decodeStream(inputStream);

		} catch (MalformedURLException e) {
			throw new ConnexionException();

		} catch (IOException e) {
			throw new ConnexionException();
		}
	}

	@Override
	protected Condition doInBackground(Location... arg0) {
		ArrayList<Condition> entries = null;
		try {
			Log.v("meeteo", ((Location)arg0[0]).toString());
			Log.v("meeteo", ((Location) arg0[0]).getLink());
			entries = Parser
					.getData("http://api.wunderground.com/api/336d055766c22b31/conditions/lang:FR/" + arg0[0].getLink() + ".xml");
			return entries.get(0);
		} catch (IOException e) {
			entries = null;
			return null;
		}
	}
}
