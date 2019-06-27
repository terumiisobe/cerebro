package com.terumi.isobe.Cerebro.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class UltrasoundImage {
	
	@Id
	@GeneratedValue
	private Long userId;
	
	@NotNull
	private String fileReference;
	
	@NotNull
	private Date reconstructionStartTime;

	@NotNull
	private Date reconstructionEndTime;

	@NotNull
	private Integer size;

	@NotNull
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
