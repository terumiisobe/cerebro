package com.terumi.isobe.Cerebro.api;

import java.util.List;

public class SignalApi {
	
	private String username;
	
	private List<Float> signal;
	
	public String getUsername() {
		return username;
	}
	
	public void setUserusername(String username) {
		this.username = username;
	}
	
	public List<Float> getSignal(){
		return signal;
	}
	
	public void setSignal(List<Float> signal) {
		this.signal = signal;
	}
}
