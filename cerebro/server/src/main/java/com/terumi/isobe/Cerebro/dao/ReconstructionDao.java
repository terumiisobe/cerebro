package com.terumi.isobe.Cerebro.dao;

import java.util.List;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.terumi.isobe.Cerebro.model.UltrasoundImage;

@Stateful
public class ReconstructionDao {
	
	@PersistenceContext
	private EntityManager em;
	
	public List<UltrasoundImage> listUltrasoundImages(Long userId){
		StringBuilder sb = new StringBuilder();
		sb.append("select ui from UltrasoundImage ui");
		
		TypedQuery<UltrasoundImage> query = em.createQuery(sb.toString(), UltrasoundImage.class);
		
		return query.getResultList();
	}
	
}
