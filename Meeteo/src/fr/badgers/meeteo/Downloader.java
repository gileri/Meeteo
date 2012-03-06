package fr.badgers.meeteo;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Downloader {

	static public Downloader downloader = null;

	public static Condition getCondition() throws ConnexionException {
		try {
			ArrayList<Condition> entries;
			entries = Parser
					.getData("http://88.190.232.239/Aix-en-Provence.xml");
			return entries.get(0);
		} catch (IOException e) {
			throw new ConnexionException();
		}
	}

	public static Bitmap getBitmap(String url) throws ConnexionException {

		if (downloader == null) {
			downloader = new Downloader();
		}
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
}
