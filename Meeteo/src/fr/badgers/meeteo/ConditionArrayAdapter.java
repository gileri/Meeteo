package fr.badgers.meeteo;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ConditionArrayAdapter extends ArrayAdapter<Condition> {

	private Context context;
	private List<Condition> objects;
	private ImageView condIcon;
	private TextView condText;

	public ConditionArrayAdapter(Context context, int textViewResourceId,
			List<Condition> objects) {
		super(context, textViewResourceId, objects);
		this.context = context;
		this.objects = objects;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		if (row == null) {
			// Row Inflation
			LayoutInflater inflater = (LayoutInflater) this.getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.condition, parent, false);
		}

		// Get element to display
		
		Log.v("meeteo", getItem(position).toString());
		
		Condition cond = getItem(position);

		// Get references to view components
		condIcon = (ImageView) row.findViewById(R.id.condicon);
		condText = (TextView) row.findViewById(R.id.condtext);
		
		Log.v("meeteo", String.valueOf(cond.getImage()));
		condIcon.setImageBitmap(cond.getImage());
		condText.setText(cond.getConditionString());
		return row;
	}

}
