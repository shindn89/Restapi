/**
 * 
 */
package com.appsdeveloperblog.app.ws.exceptions;

/**
 * @author DN
 *
 */
public class AuthenticationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2373664764845434623L;
	
	public AuthenticationException (String message)
	{
		super(message);
	}
}
