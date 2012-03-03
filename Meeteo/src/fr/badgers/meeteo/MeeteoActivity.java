package fr.badgers.meeteo;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class MeeteoActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ArrayList<WeatherEntry> entries = Parser.getData();
        Log.e("meeteo",String.valueOf(entries.size()));
//        for(WeatherEntry e : entries)
//        	Log.e("meeteo",e.toString());
        Log.e("meeteo", entries.get(0).toString());
        
    }
}