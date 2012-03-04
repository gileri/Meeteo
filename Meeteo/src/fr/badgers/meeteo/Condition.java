package fr.badgers.meeteo;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Condition {
	//private SimpleDateFormat DateFormater = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'"); 
	private float temperature;
	private URL imageurl;
	
	public Condition(float temperature) {
		super();
		this.temperature = temperature;
	}
	
	public URL getImageurl() {
		return imageurl;
	}

	public void setImageurl (URL imageurl) {
		this.imageurl = imageurl;
	}
	
	public void setImageurl (String imageurl) throws MalformedURLException
	{
			this.imageurl = new URL (imageurl);
	}
	
	public Condition() {
		super();
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
}
