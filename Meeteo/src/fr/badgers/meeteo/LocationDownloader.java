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

public class LocationDownloader extends AsyncTask<String, Integer, ArrayList<Location>> {

	static public LocationDownloader downloader = null;
	
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
	protected ArrayList<Location> doInBackground(String... arg0) {
		try {
			return ParserGeoLookup
					.getData("http://autocomplete.wunderground.com/aq?query=" + arg0[0] + "&format=xml&c=FR");
		} catch (IOException e) {
			return null;
		}
	}
	
	protected void onPostExecute(ArrayList<Location> a)
	{
		if(a != null)
		{
			
		}
	}
}
