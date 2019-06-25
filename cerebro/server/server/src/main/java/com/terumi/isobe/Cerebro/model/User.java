package com.terumi.isobe.Cerebro.model;

import java.util.List;

public class User {
	
	private Long id;
	private String username;
	private List<UltrasoundImage> images;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return username;
	}
	public void setName(String username) {
		this.username = username;
	}
	public List<UltrasoundImage> getUltrasoundImage(){
		return images;
	}
	public void setUltrasoundImage(List<UltrasoundImage> images) {
		this.images = images;
	}
}
