/**
 * 
 */
package com.appsdeveloperblog.app.ws.io.dao.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.BeanUtils;

import com.appsdeveloperblog.app.ws.io.dao.DAO;
import com.appsdeveloperblog.app.ws.io.entity.UserEntity;
import com.appsdeveloperblog.app.ws.shared.dto.UserDTO;
import com.appsdeveloperblog.app.ws.utils.HibernateUtils;

/**
 * @author DN
 * Actual connection with Database and working with DB
 */
public class MySQLDAO implements DAO {

	Session session;
	
	@Override
	public void openConnection() {
		SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
		session = sessionFactory.openSession();
	}

	@Override
	public UserDTO getUserByUserName(String userName) {
		UserDTO userDto = null;
		
		CriteriaBuilder cb = session.getCriteriaBuilder();
		
		//Create criteria against a particular persistence class
		CriteriaQuery<UserEntity> criteria = cb.createQuery(UserEntity.class);
		
		//Query roots always reference entities
		Root<UserEntity> profileRoot = criteria.from(UserEntity.class);
		criteria.select(profileRoot);
		criteria.where(cb.equal(profileRoot.get("email"), userName));
		
		//fetch single result
		Query<UserEntity> query = session.createQuery(criteria);
		List<UserEntity> resultList = query.getResultList();
		if( resultList != null && resultList.size() > 0)
		{
			UserEntity userEntity = resultList.get(0);
			userDto = new UserDTO();
			BeanUtils.copyProperties(userEntity, userDto);
		}
		
		return userDto;
	}
	//exception will thrown if user does not exist
	public UserDTO getUser(String id) {
		CriteriaBuilder cb = session.getCriteriaBuilder();
		
		//create criteria against a particular persistent class
		CriteriaQuery<UserEntity> criteria = cb.createQuery(UserEntity.class);
		
		//Query roots always reference entitie
		Root<UserEntity> profileRoot = criteria.from(UserEntity.class);
		criteria.select(profileRoot);
		criteria.where(cb.equal(profileRoot.get("userId"), id));
		
		//fetch single result
		UserEntity userEntity = session.createQuery(criteria).getSingleResult();
		
		UserDTO userDto = new UserDTO();
		BeanUtils.copyProperties(userEntity, userDto);
		
		return userDto;
	}
	
	
	public UserDTO saveUser(UserDTO user) {
		UserDTO returnValue = null;
		UserEntity userEntity = new UserEntity();
		BeanUtils.copyProperties(user, userEntity);
		
		session.beginTransaction();
		session.save(userEntity);
		session.getTransaction().commit();
		
		returnValue = new UserDTO();
		BeanUtils.copyProperties(userEntity, returnValue);
		
		return returnValue;
	}

	@Override
	public void closeConnection() {
		if(session != null)
		{
			session.close();
		}
		
	}

	public void updateUser(UserDTO userProfile) {
		UserEntity userEntity = new UserEntity();
		BeanUtils.copyProperties(userProfile, userEntity);
		
		session.beginTransaction();
		session.update(userProfile);
		session.getTransaction().commit();
	
	}

	

}
