package fr.badgers.meeteo;

import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MeeteoActivity extends Activity{
    /** Called when the activity is first created. */
	
	TextView tempview = null;
	Button b = null;
	ImageView image;
	
	
	private OnTouchListener appuibouton = new OnTouchListener() {
		
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			ArrayList<Condition> entries;
			tempview = (TextView) findViewById(R.id.temp);
			image = (ImageView) findViewById(R.id.imageView1);
			try {
				entries = Parser.getData("http://api.wunderground.com/api/336d055766c22b31/geolookup/conditions/lang:FR/q/France/Aix-en-Provence.xml");
				Log.e("meeteo", "PONEY");
				tempview.setText(String.valueOf(entries.get(0).getTemperature()));
		        downloadImage((ImageView) findViewById(R.id.imageView1), "http://icons.wxug.com/i/c/c/sunny.gif");
			} catch (ConnectException e1) {
				// TODO Connection Problem Catching
				tempview.setText("N/A");
				image.setImageDrawable(getResources().getDrawable(R.drawable.error));
			} catch (UnknownHostException e) {
				tempview.setText("N/A");
				image.setImageDrawable(getResources().getDrawable(R.drawable.error));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		}
	};
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        b = (Button) findViewById(R.id.button1);
        b.setOnTouchListener(appuibouton);
    }
    
    private void downloadImage(ImageView iv, String url) {

    	Bitmap bitmap = null;

    	try {

	    	URL urlImage = new URL(url);
	
	    	HttpURLConnection connection = (HttpURLConnection) urlImage.openConnection();
	
	    	InputStream inputStream = connection.getInputStream();
	
	    	bitmap = BitmapFactory.decodeStream(inputStream);
	    	
	    	iv.setImageBitmap(bitmap);

    	} catch (MalformedURLException e) {

    	e.printStackTrace();

    	} catch (IOException e) {

    	e.printStackTrace();

    	}

    	}
}