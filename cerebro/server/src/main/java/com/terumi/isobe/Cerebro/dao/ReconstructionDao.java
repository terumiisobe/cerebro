package com.terumi.isobe.Cerebro.dao;

import java.util.List;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.terumi.isobe.Cerebro.model.UltrasoundImage;
import com.terumi.isobe.Cerebro.model.User;

@Stateful
public class ReconstructionDao {
	
	@PersistenceContext
	private EntityManager em;
	
	public List<UltrasoundImage> listUltrasoundImages(Long userId){
		StringBuilder sb = new StringBuilder();
		sb.append("select ui from UltrasoundImage ui where ui.userId = :userId");
		
		TypedQuery<UltrasoundImage> query = em.createQuery(sb.toString(), UltrasoundImage.class);
		query.setParameter("userId", userId);

		return query.getResultList();
	}
	
	/**
	 * Search user by id.
	 * 
	 */
	public User searchUserByUsername(String username) {
		StringBuilder sb = new StringBuilder();
		sb.append("select u from User u where u.username = :username");
		
		TypedQuery<User> query = em.createQuery(sb.toString(), User.class);
		query.setParameter("username", username);
		
		return query.getSingleResult();
	}
	
	/**
	 * Saves ultrasound image data in the database and saves image in a file.
	 * 
	 */
//	public void saveReconstructedImage(UltrasoundImage imageInfo, List<Float> image) {
//		
//	}
	
}
