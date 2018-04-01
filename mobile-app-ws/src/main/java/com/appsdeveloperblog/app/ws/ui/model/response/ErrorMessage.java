/**
 * 
 */
package com.appsdeveloperblog.app.ws.ui.model.response;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author DN
 *
 */
@XmlRootElement //require to convert to JSON?
public class ErrorMessage {
	
	private String errorMessage;
	private String errorMessageKey; // already implemented enum class
	private String href; //link to documentation page that defined for the error msg
	
	public ErrorMessage() {}
	
	public ErrorMessage(String errorMessage, String errorMessageKey, String herf)
	{
		this.errorMessage = errorMessage;
		this.errorMessageKey = errorMessageKey;
		this.href = herf;
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
	/**
	 * @return the errorMessageKey
	 */
	public String getErrorMessageKey() {
		return errorMessageKey;
	}
	/**
	 * @param errorMessageKey the errorMessageKey to set
	 */
	public void setErrorMessageKey(String errorMessageKey) {
		this.errorMessageKey = errorMessageKey;
	}
	/**
	 * @return the href
	 */
	public String getHref() {
		return href;
	}
	/**
	 * @param href the href to set
	 */
	public void setHref(String href) {
		this.href = href;
	}

}
