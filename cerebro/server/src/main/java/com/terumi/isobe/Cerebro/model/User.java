package com.terumi.isobe.Cerebro.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class User {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@NotNull
	private String username;
	
//	private List<UltrasoundImage> images;
	
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
//	public List<UltrasoundImage> getUltrasoundImage(){
//		return images;
//	}
//	public void setUltrasoundImage(List<UltrasoundImage> images) {
//		this.images = images;
//	}
}
