package com.terumi.isobe.cerebro.client.model;

import java.util.Date;

public class UltrasoundImage {
	
	private String fileReference;
	
	private Date reconstructionStartTime;
	
	private Date reconstructionEndTime;
	
	private Long size;
	
	private Long iterationsPerformed;
	

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
	
	public Long getSize() {
		return this.size;
	}
	
	public void setSize(Long size) {
		this.size = size;
	}
	
	public Long getIterationsPerformed() {
		return this.iterationsPerformed;
	}
	
	public void setIterationsPerformed(Long number) {
		this.iterationsPerformed = number;
	}
}
