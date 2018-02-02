package it.univaq.disim.sose.conferencemanager.manager.business.model;

import java.sql.Date;

public class Conference implements java.io.Serializable {
	
	private String conferenceId;
	private String name;
	private String conferenceAbstract;
	private String image;
	private byte[] pdf;
	private String city;
	private Date date;
	private double latitude;
	private double longitude;
	
	
	
	public String getConferenceId() {
		return conferenceId;
	}
	public void setConferenceId(String conferenceId) {
		this.conferenceId = conferenceId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getConferenceAbstract() {
		return conferenceAbstract;
	}
	public void setConferenceAbstract(String conferenceAbstract) {
		this.conferenceAbstract = conferenceAbstract;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public byte[] getPdf() {
		return pdf;
	}
	public void setPdf(byte[] pdf) {
		this.pdf = pdf;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}


}
