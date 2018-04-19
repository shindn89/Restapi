/**
 * 
 */
package com.appsdeveloperblog.app.ws.io.dao;

import java.util.List;

import com.appsdeveloperblog.app.ws.shared.dto.UserDTO;

/**
 * @author DN
 * data access object
 */
public interface DAO {
	void openConnection(); //open connection to db
	UserDTO getUserByUserName(String userName);
	UserDTO saveUser(UserDTO user);
	UserDTO getUser(String id);
	List<UserDTO> getUsers(int start, int limit);
	void closeConnection(); //close connection to db
	void updateUser(UserDTO userProfile);
	
	
}
