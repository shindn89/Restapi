/**
 * 
 */
package com.appsdeveloperblog.app.ws.io.dao.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.BeanUtils;

import com.appsdeveloperblog.app.ws.io.dao.DAO;
import com.appsdeveloperblog.app.ws.io.entity.UserEntity;
import com.appsdeveloperblog.app.ws.shared.dto.UserDTO;

/**
 * @author DN
 *
 */
public class MySQLDAO implements DAO {

	Session session;
	
	@Override
	public void openConnection() {
		// TODO Auto-generated method stub
		
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

	@Override
	public void closeConnection() {
		// TODO Auto-generated method stub
		
	}

}
