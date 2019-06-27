package com.terumi.isobe.cerebro.client.model;

import java.util.Date;

public class UltrasoundImage {
	
	private Long userId;
	
	private String fileReference;
	
	private Date reconstructionStartTime;
	
	private Date reconstructionEndTime;
	
	private Integer size;
	
	private Integer iterationsPerformed;
	
	public Long getUserId() {
		return userId;
	}
	
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public String getFileReference() {
		return fileReference;
	}
	
	public void setFileReference(String ref) {
		this.fileReference = ref;
	}
	
	public Date getReconstructionStartTime() {
		return this.reconstructionStartTime;
	}
	
	public void setReconstructionStartTime(Date startTime) {
		this.reconstructionStartTime = startTime;
	}
	
	public Date getReconstructionEndTime() {
		return this.reconstructionEndTime;
	}
	
	public void setReconstructionEndTime(Date endTime) {
		this.reconstructionEndTime = endTime;
	}
	
	public Integer getSize() {
		return this.size;
	}
	
	public void setSize(Integer size) {
		this.size = size;
	}
	
	public Integer getIterationsPerformed() {
		return this.iterationsPerformed;
	}
	
	public void setIterationsPerformed(Integer number) {
		this.iterationsPerformed = number;
	}
}
