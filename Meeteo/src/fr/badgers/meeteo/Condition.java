package fr.badgers.meeteo;

import java.net.URL;
import java.util.Date;

import android.graphics.Bitmap;

public class Condition {
	private float temperature;
	private URL imageurl;
	private Bitmap Image;
	Date lastRefresh;
	private String imageurlstring;
	private String description;
	private String conditionString;
	private Date date;
	
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getConditionString() {
		if (conditionString == null)
			conditionString = new String(description + ", " + temperature + " °C");
		return conditionString;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImageurlstring() {
		return imageurlstring;
	}

	public void setImageurlstring(String imageurlstring) {
		this.imageurlstring = imageurlstring;
	}

	public Bitmap getImage() {
		return Image;
	}

	public void setImage(Bitmap image) {
		Image = image;
	}

	public Date getLastRefresh() {
		return lastRefresh;
	}

	public void setLastRefresh(Date lastRefresh) {
		this.lastRefresh = lastRefresh;
	}

	public Condition(float temperature) {
		super();
		this.temperature = temperature;
	}

	public URL getImageurl() {
		return imageurl;
	}

	public void setImageurl(URL imageurl) {
		this.imageurl = imageurl;
	}

	public Condition() {
		super();
		this.lastRefresh = new Date();
	}

	public float getTemperature() {
		return temperature;
	}

	public void setTemperature(float temperature) {
		this.temperature = temperature;
	}

	@Override
	public String toString() {
		return "WeatherEntry [temperature=" + temperature + "]";
	}

	public void setImageUrlString(String string) {
		this.imageurlstring = string;		
	}
}
