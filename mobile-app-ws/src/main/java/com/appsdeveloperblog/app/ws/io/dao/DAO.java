/**
 * 
 */
package com.appsdeveloperblog.app.ws.io.dao;

import com.appsdeveloperblog.app.ws.shared.dto.UserDTO;

/**
 * @author DN
 * data access object
 */
public interface DAO {
	void openConnection(); //open connection to db
	UserDTO getUserByUserName(String userName);
	void closeConnection(); //close connection to db
}
