/**
 * 
 */
package com.appsdeveloperblog.app.ws.ui.model.response;

/**
 * @author DN
 *
 */
public enum ErrorMessages {
	
	MISSING_REQUIRED_FIELD("Missing required field. Please check documentation for required field"),
	RECORD_ALREADY_EXIST("Record already exists"),
	INTERNAL_SERVER_ERROR("Internal server error"),
	NO_RECORD_FOUND("No Record Found"), 
	AUTHENTICATION_FAILED("Authentication failed"),
	COULD_NOT_UPDATE_RECORD("Could not update record");
	
	private String errorMessage;
	
	ErrorMessages(String errorMessage){
		this.errorMessage = errorMessage;
	}

	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * @param errorMessage the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
