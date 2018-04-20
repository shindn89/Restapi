/**
 * 
 */
package com.appsdeveloperblog.app.ws.service.impl;

import java.util.List;

import com.appsdeveloperblog.app.ws.exceptions.CouldNotCreateRecordException;
import com.appsdeveloperblog.app.ws.exceptions.CouldNotUpdateRecordException;
import com.appsdeveloperblog.app.ws.exceptions.NoRecordFoundException;
import com.appsdeveloperblog.app.ws.io.dao.DAO;
import com.appsdeveloperblog.app.ws.io.dao.impl.MySQLDAO;
import com.appsdeveloperblog.app.ws.service.UserService;
import com.appsdeveloperblog.app.ws.shared.dto.UserDTO;
import com.appsdeveloperblog.app.ws.ui.model.response.ErrorMessages;
import com.appsdeveloperblog.app.ws.utils.UserProfileUtils;

/**
 * @author DN
 *
 */
public class UserServiceImpl implements UserService{
	
	DAO database;
	
	public UserServiceImpl() {
		this.database = new MySQLDAO();
	}

	UserProfileUtils userProfileUtils = new UserProfileUtils();
	
	@Override
	public UserDTO createUser(UserDTO user) {
		UserDTO returnValue = null;
		
		//validate the required fields
		userProfileUtils.validateRequiredFields(user);
				
		//Check if user already exists
		UserDTO existingUser = this.getUserByUserName(user.getEmail());
		if(existingUser != null)
		{
			throw new CouldNotCreateRecordException(ErrorMessages.RECORD_ALREADY_EXIST.name());
		}
		
		//Generate secure public user id
		String userId = userProfileUtils.generateUserId(30);
		user.setUserId(userId);
				
		//Generate salt - generate secure psw and store value into db
		String salt = userProfileUtils.getSalt(30);	
		
		//Generate secure password
		//and salt and encryptedpsw will store in DB
		String encryptedPassword = userProfileUtils.generateSecurePassword(user.getPassword(), salt);
		user.setSalt(salt);
		user.setEncryptedPassword(encryptedPassword);
		
		//Record data into a database
		returnValue = this.saveUser(user);
		
		//Return back the user profile
		
		
		return returnValue;
	}
	
	public UserDTO getUser(String id) {
		UserDTO returnValue = null;
		try {
			this.database.openConnection();
			returnValue = this.database.getUser(id);
		} catch(Exception ex) {
			ex.printStackTrace();
			throw new NoRecordFoundException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
		} finally {
			this.database.closeConnection();
		}
		
		return returnValue;
	}
	
	@Override
	public UserDTO getUserByUserName(String userName)
	{
		UserDTO userDto = null;
		if(userName == null || userName.isEmpty()) {
			return userDto;
		}
			
		//connect to DB
		try {
			this.database.openConnection();
			userDto = this.database.getUserByUserName(userName);
		} finally {
			this.database.closeConnection();
		}
		
		return userDto;
	}
	
	private UserDTO saveUser(UserDTO user) {
		UserDTO returnValue = null;
		try {
			this.database.openConnection();
			returnValue = this.database.saveUser(user);
		} finally {
			this.database.closeConnection();
		}
		return returnValue;
	}

	public List<UserDTO> getUsers(int start, int limit) {
		List<UserDTO> users = null;
		
		// get users from database
		try {
			this.database.openConnection();
			users = this.database.getUsers(start,limit);
		} finally {
			this.database.closeConnection();
		}
		
		return users;
	}

	public void updateUserDetails(UserDTO userDetails) {
		try
		{
			// connect to db
			this.database.openConnection();
			//update user details
			this.database.updateUser(userDetails);
		}
		catch(Exception ex)
		{
			throw new CouldNotUpdateRecordException(ex.getMessage());
		}
		finally
		{
			this.database.closeConnection();
		}
	}

	

}
