package br.com.google.android.pojos;

import java.io.Serializable;

public class Car implements Serializable {
	private static final long serialVersionUID = -8983391940282646847L;
	
	public static final String KEY = "car";
	public static final String TYPE = "type";
	
	public enum Type {
		TYPE_SPORT("esportivos"), TYPE_LUXURY("luxo"), TYPE_CLASSIC("classicos");
		
		private String type;
		
		private Type(String type) {
			this.type = type;
		}
		
		public String getType() {
			return this.type;
		}
	}
	
	private String name;
	private String description;
	private String urlInfo;
	private String urlPicture;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getUrlInfo() {
		return urlInfo;
	}
	public void setUrlInfo(String urlInfo) {
		this.urlInfo = urlInfo;
	}
	public String getUrlPicture() {
		return urlPicture;
	}
	public void setUrlPicture(String urlPicture) {
		this.urlPicture = urlPicture;
	}
}