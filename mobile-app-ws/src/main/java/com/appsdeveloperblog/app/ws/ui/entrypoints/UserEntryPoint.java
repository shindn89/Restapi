package com.appsdeveloperblog.app.ws.ui.entrypoints;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.BeanUtils;

import com.appsdeveloperblog.app.ws.annotations.Secured;
import com.appsdeveloperblog.app.ws.service.UserService;
import com.appsdeveloperblog.app.ws.service.impl.UserServiceImpl;
import com.appsdeveloperblog.app.ws.shared.dto.UserDTO;
import com.appsdeveloperblog.app.ws.ui.model.request.CreateUserRequestModel;
import com.appsdeveloperblog.app.ws.ui.model.response.UserProfileRest;


@Path("/users")
public class UserEntryPoint {
	
	//require to allow http request
	//JSON payload? that contains the information
	//Framwork helps us to figure out the JSON to object?
	//reponse msg also need to JSON
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public UserProfileRest createUser(CreateUserRequestModel requestObject) {
		UserProfileRest returnValue = new UserProfileRest();
		
		//prepare userDTO
		UserDTO userDto = new UserDTO();
		BeanUtils.copyProperties(requestObject, userDto);
		
		//create new User
		UserService userService = new UserServiceImpl();
		UserDTO createUserProfile = userService.createUser(userDto);
		
		//prepare response
		BeanUtils.copyProperties(createUserProfile, returnValue);
					
		return returnValue;
	}
	
	@Secured
	@GET
	@Path("/{id}") //replace the actual value of id?
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public UserProfileRest getUserProfile(@PathParam("id") String id) {
		UserProfileRest returnValue = null;
		UserService userService = new UserServiceImpl();
		UserDTO userProfile = userService.getUser(id);
		
		returnValue = new UserProfileRest();
		BeanUtils.copyProperties(userProfile, returnValue);
		
		return returnValue;
	}
	
	//QueryPram annotaions - allows us to retireved information sent by user using query
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public List<UserProfileRest> getUsers(@DefaultValue("0")@QueryParam("start") int start, @DefaultValue("50")@QueryParam("limit") int limit)
	{
		UserService userService = new UserServiceImpl();
		List<UserDTO> users = userService.getUsers(start, limit);
		
		// Prepare return value
		List<UserProfileRest> returnValue = new ArrayList<UserProfileRest>();
		for(UserDTO userDto : users)
		{
			UserProfileRest userModel = new UserProfileRest();
			BeanUtils.copyProperties(userDto, userModel);
			userModel.setHref("/users/" + userDto.getUserId());
			returnValue.add(userModel);
		}
		
		return returnValue;
	}
	
}
