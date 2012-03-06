package fr.badgers.meeteo;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

public class Condition {
	// private SimpleDateFormat DateFormater = new
	// SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
	private float temperature;
	private URL imageurl;
	Date lastRefresh;

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

	public void setImageurl(String imageurl) throws MalformedURLException {
		this.imageurl = new URL(imageurl);
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
}
