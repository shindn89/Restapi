/**
 * 
 */
package com.appsdeveloperblog.app.ws.filters;

import java.io.IOException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;

import com.appsdeveloperblog.app.ws.annotations.Secured;
import com.appsdeveloperblog.app.ws.exceptions.AuthenticationException;
import com.appsdeveloperblog.app.ws.service.UserService;
import com.appsdeveloperblog.app.ws.service.impl.UserServiceImpl;
import com.appsdeveloperblog.app.ws.shared.dto.UserDTO;
import com.appsdeveloperblog.app.ws.utils.UserProfileUtils;

/**
 * @author DN
 *
 */
@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter{

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		//Extract Authorization header details
		String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
		
		//validate the header information
		if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer"))
		{
			throw new AuthenticationException("Authorazation header must be provided");
		}
			
		// Extract the token - token come from header 
		String token = authorizationHeader.substring("Bearer".length()).trim();
		
		//Extract user id - userid comes from uri
		String userId = requestContext.getUriInfo().getPathParameters().getFirst("id");
		
		validateToken(token, userId);
	}

	private void validateToken(String token, String userId) throws AuthenticationException{
		
		//Get user profile details
		UserService userService = new UserServiceImpl();
		UserDTO userProfile = userService.getUser(userId);
		
		//Asseble access token using two parts, One from DB and one from http request.
		String completeToken = userProfile.getToken() + token;
		
		//Create Access token material out of the userid received and salt kept databse
		String securePassword = userProfile.getEncryptedPassword();
		String salt = userProfile.getSalt();
		String accessTokenMaterial = userId + salt;
		byte[] encryptedAccessToken = null;
		
		try {
			encryptedAccessToken = new UserProfileUtils().encrypt(securePassword, accessTokenMaterial);
		} catch (InvalidKeySpecException ex)
		{
			Logger.getLogger(AuthenticationFilter.class.getName()).log(Level.SEVERE, null, ex);
			throw new AuthenticationException("Failed to issue secure access token");
		}
		
		String encryptedAccessTokenBase64Encoded = Base64.getEncoder().encodeToString(encryptedAccessToken);
		
		//compare two access tokens
		if(!encryptedAccessTokenBase64Encoded.equals(completeToken))
		{
			throw new AuthenticationException("Authorization token did not match");
		}
	}

}
