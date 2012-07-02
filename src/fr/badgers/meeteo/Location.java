package fr.badgers.meeteo;

import java.io.Serializable;
import java.util.Date;

public class Location implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String link;
	private String name;
	private Date lastUpdate;
	private String country;
	
	@Override
	public String toString() {
		return "Location [link=" + link + ", name=" + name + ", lastUpdate="
				+ lastUpdate + ", country=" + country + "]";
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
}
