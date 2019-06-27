package com.terumi.isobe.cerebro.client.model;

import java.util.List;

public class SignalApi {
	
	private String username;
	
	private List<Float> signal;
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public List<Float> getSignal(){
		return signal;
	}
	
	public void setSignal(List<Float> signal) {
		this.signal = signal;
	}
}
