package com.terumi.isobe.Cerebro.api;

import java.math.BigDecimal;
import java.util.List;

public class SignalApi {
	
	private Long userId;
	
	private List<BigDecimal> signal;
	
	public Long getUserId() {
		return userId;
	}
	
	public void setUserId(Long id) {
		this.userId = id;
	}
	
	public List<BigDecimal> getSignal(){
		return signal;
	}
	
	public void setSignal(List<BigDecimal> signal) {
		this.signal = signal;
	}
}
