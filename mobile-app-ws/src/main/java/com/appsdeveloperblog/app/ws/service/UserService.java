/**
 * 
 */
package com.appsdeveloperblog.app.ws.service;

import com.appsdeveloperblog.app.ws.shared.dto.UserDTO;

/**
 * @author DN
 *
 */
public interface UserService {
	
	UserDTO createUser(UserDTO user);
	UserDTO getUser(String id);

}
