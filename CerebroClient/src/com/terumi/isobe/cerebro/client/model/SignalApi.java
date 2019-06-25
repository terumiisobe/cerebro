package com.terumi.isobe.cerebro.client.model;

import java.math.BigDecimal;
import java.util.List;

public class SignalApi {
	
	private String username;
	
	private List<BigDecimal> signal;
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String id) {
		this.username = id;
	}
	
	public List<BigDecimal> getSignal(){
		return signal;
	}
	
	public void setSignal(List<BigDecimal> signal) {
		this.signal = signal;
	}
}
