/**
 * 
 */
package com.appsdeveloperblog.app.ws.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.appsdeveloperblog.app.ws.ui.model.response.ErrorMessage;
import com.appsdeveloperblog.app.ws.ui.model.response.ErrorMessages;

/**
 * @author DN
 *	need to implement 
 */
//since not using try catch, so this exception handled by framework
@Provider
public class MissingRequiredFieldExceptionMapper implements ExceptionMapper<MissingRequiredFieldException>{

	@Override
	public Response toResponse(MissingRequiredFieldException exception) {
		ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(), ErrorMessages.MISSING_REQUIRED_FIELD.name(),
				"http://appsdeveloperblog.com");
		
		return Response.status(Response.Status.BAD_REQUEST).entity(errorMessage).build();
	}

}
