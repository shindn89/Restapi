/**
 * 
 */
package com.appsdeveloperblog.app.ws.service.impl;

import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.appsdeveloperblog.app.ws.exceptions.AuthenticationException;
import com.appsdeveloperblog.app.ws.io.dao.DAO;
import com.appsdeveloperblog.app.ws.io.dao.impl.MySQLDAO;
import com.appsdeveloperblog.app.ws.service.AuthenticationService;
import com.appsdeveloperblog.app.ws.service.UserService;
import com.appsdeveloperblog.app.ws.shared.dto.UserDTO;
import com.appsdeveloperblog.app.ws.ui.model.response.ErrorMessages;
import com.appsdeveloperblog.app.ws.utils.UserProfileUtils;

/**
 * @author DN
 *
 */
public class AuthenticationServiceImpl implements AuthenticationService {
	DAO database;

	public UserDTO authenticate(String userName, String password) throws AuthenticationException {
		UserService userService = new UserServiceImpl();
		UserDTO storedUser = userService.getUserByUserName(userName);
		
		if(storedUser == null)
		{
			throw new AuthenticationException(ErrorMessages.AUTHENTICATION_FAILED.getErrorMessage());
		}
		
		String encryptedPassword = null;
		encryptedPassword = new UserProfileUtils().generateSecurePassword(password, storedUser.getSalt());
		
		
		boolean authenticated = false;
		if(encryptedPassword != null && encryptedPassword.equalsIgnoreCase(storedUser.getEncryptedPassword()))
		{
			if(userName != null && userName.equalsIgnoreCase((storedUser.getEmail())))
			{
				authenticated = true;
			}
		}
		
		if (!authenticated)
		{
			throw new AuthenticationException(ErrorMessages.AUTHENTICATION_FAILED.getErrorMessage());
		}		
		
		return storedUser;
	}

	public String issueAccessToken(UserDTO userProfile) throws AuthenticationException {
		String returnValue = null;
		
		String newSaltAsPostfix = userProfile.getSalt();
		String accessTokenmaterial = userProfile.getUserId() + newSaltAsPostfix;
		byte[] encryptedAccessToken = null;
		
		try {
			encryptedAccessToken = new UserProfileUtils().encrypt(userProfile.getEncryptedPassword(), accessTokenmaterial);
		} catch(InvalidKeySpecException ex)
		{
			Logger.getLogger(AuthenticationServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
			throw new AuthenticationException("Failed to issue secure access token");
		}
		
		String encryptedAccessTokenBase64Encoded = Base64.getEncoder().encodeToString(encryptedAccessToken);
		
		//split token into equal parts
		int tokenLength = encryptedAccessTokenBase64Encoded.length();
		
		String tokenToSaveToDatabase = encryptedAccessTokenBase64Encoded.substring(0, tokenLength / 2);
		returnValue = encryptedAccessTokenBase64Encoded.substring(tokenLength / 2, tokenLength);
		
		userProfile.setToken(tokenToSaveToDatabase);
		
		updateUserProfile(userProfile);
		
		return returnValue;
	}
	
	public void resetSecurityCridentials(String userPassword, UserDTO authenticationUser) {
		//Generate new salt
		UserProfileUtils userUtils = new UserProfileUtils();
		String salt = userUtils.getSalt(30);
		
		//Generate new secure password
		String securePassword = userUtils.generateSecurePassword(userPassword, salt);
		authenticationUser.setSalt(salt);
		authenticationUser.setEncryptedPassword(securePassword);		
		
		//Update UserProfile with new salt and password
		updateUserProfile(authenticationUser);
	}
	
	
	//store token to DB
	private void updateUserProfile(UserDTO userProfile)
	{
		this.database = new MySQLDAO();
		try {
			database.openConnection();
			database.updateUser(userProfile);
		}
		finally {
			database.closeConnection();
		}
	}

	
	

}
