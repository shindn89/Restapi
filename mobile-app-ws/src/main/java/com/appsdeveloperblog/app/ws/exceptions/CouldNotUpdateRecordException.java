/**
 * 
 */
package com.appsdeveloperblog.app.ws.exceptions;

/**
 * @author DN
 *
 */
public class CouldNotUpdateRecordException extends RuntimeException {

	private static final long serialVersionUID = -4918718529490931746L;
	
	public CouldNotUpdateRecordException (String message)
	{
		super(message);
	}

}
