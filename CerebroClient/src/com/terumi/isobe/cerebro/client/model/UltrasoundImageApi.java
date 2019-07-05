package com.terumi.isobe.cerebro.client.model;

import java.util.List;

public class UltrasoundImageApi {
	
	private String reference;
	
	private List<Float> image;
	
	public String getReference() {
		return reference;
	}
	
	public void setReference(String ref) {
		this.reference = ref;
	}
	
	public List<Float> getImage(){
		return image;
	}
	
	public void setImage(List<Float> image) {
		this.image = image;
	}
}
