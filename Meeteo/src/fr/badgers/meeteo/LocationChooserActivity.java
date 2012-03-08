package fr.badgers.meeteo;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class LocationChooserActivity extends Activity{
	
	private ArrayList <CharSequence> locations;
	private CharSequence currlocation;
	
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
				currlocation = s;
			}
		});
		
		
		findViewById(R.id.geolookupbutton).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//TODO :Download, Parse, and give a list of possible locations;
				
			}
		});
		
	}
}
