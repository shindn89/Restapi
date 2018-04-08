/**
 * 
 */
package com.appsdeveloperblog.app.ws.ui.entrypoints;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.appsdeveloperblog.app.ws.service.AuthenticationService;
import com.appsdeveloperblog.app.ws.service.impl.AuthenticationServiceImpl;
import com.appsdeveloperblog.app.ws.shared.dto.UserDTO;
import com.appsdeveloperblog.app.ws.ui.model.request.LoginCredentials;
import com.appsdeveloperblog.app.ws.ui.model.response.AuthenticationDetails;



/**
 * @author DN
 *
 */
@Path("/authentication")
public class AuthenticationEntryPoint {
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public AuthenticationDetails userLogin(LoginCredentials loginCredentials)
	{
		AuthenticationDetails  returnValue = new AuthenticationDetails();
		
		AuthenticationService authenticationService = new AuthenticationServiceImpl();
		UserDTO authenticationUser = authenticationService.authenticate(loginCredentials.getUserName(), loginCredentials.getUserPassword());
		
		String accessToken = authenticationService.issueAccessToken(authenticationUser);
		
		returnValue.setId(authenticationUser.getUserId());
		returnValue.setToken(accessToken);
		
		return returnValue;
	}

}
