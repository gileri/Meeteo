package fr.badgers.meeteo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WeatherEntryMetno {
	private SimpleDateFormat DateFormater = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'"); 
	private Date from;
	private Date to;
	private float temperature;
	
	public WeatherEntryMetno(Date from, Date to, float temperature) {
		super();
		this.from = from;
		this.to = to;
		this.temperature = temperature;
	}
	
	public WeatherEntryMetno() {
		super();
	}

	public Date getFrom() {
		return from;
	}

	public void setFrom(Date from) {
		this.from = from;
	}
	
	public void setFrom(String from) {
		try {
			this.from = this.DateFormater.parse(from);
		} catch (ParseException e) {
			e.printStackTrace();
			this.from = new Date();
		}
	}

	public Date getTo() {
		return to;
	}

	public void setTo(Date to) {
		this.to = to;
	}
	
	public void setTo(String to) {
		try {
			this.to = this.DateFormater.parse(to);
		} catch (ParseException e) {
			e.printStackTrace();
			this.to = new Date();
		}
		
	}

	public float getTemperature() {
		return temperature;
	}

	public void setTemperature(float temperature) {
		this.temperature = temperature;
	}

	@Override
	public String toString() {
		return "WeatherEntry [from=" + from + ", to=" + to + ", temperature="
				+ temperature + "]";
	}
}
