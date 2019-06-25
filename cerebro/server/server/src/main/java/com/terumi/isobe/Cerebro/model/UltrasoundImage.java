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
	private Long id;
	
	@NotNull
	private Long userId;
	
	@NotNull
	private String fileReference;
	
	@NotNull
	private Date reconstructionStartTime;
	
	@NotNull
	private Date reconstructionEndTime;
	
	@NotNull
	private Long size;
	
	@NotNull
	private Long iterationsPerformed;
	
//	public UltrasoundImage() {
//		
//		this.userId = userId;
//		this.fileReference = fileReference;
//		this.reconstructionStartTime = reconstructionStartTime;
//		this.reconstructionEndTime = reconstructionEndTime;
//		this.size = size;
//		this.iterationsPerformed = iterationsPerformed;
//		
//	}
	
	public Long getId() {
		return this.id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getUserId() {
		return this.userId;
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
