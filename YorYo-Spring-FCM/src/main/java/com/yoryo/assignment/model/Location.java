package com.yoryo.assignment.model;

public class Location {

	String lat;
	String lang;

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	@Override
	public String toString() {
		return "Location [lat=" + lat + ", lang=" + lang + "]";
	}

	
	
}
