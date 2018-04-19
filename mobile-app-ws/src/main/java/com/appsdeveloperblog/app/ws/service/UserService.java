/**
 * 
 */
package com.appsdeveloperblog.app.ws.service;

import java.util.List;

import com.appsdeveloperblog.app.ws.shared.dto.UserDTO;

/**
 * @author DN
 *
 */
public interface UserService {
	
	UserDTO createUser(UserDTO user);
	UserDTO getUser(String id);
	UserDTO getUserByUserName(String userName);
	List<UserDTO> getUsers(int start, int limit);
}
