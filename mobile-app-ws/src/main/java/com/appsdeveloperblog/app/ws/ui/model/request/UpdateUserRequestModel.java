/**
 * 
 */
package com.appsdeveloperblog.app.ws.ui.model.request;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author DN
 *
 */
@XmlRootElement
public class UpdateUserRequestModel {
	private String firstName;
	private String lastName;
	//private String email;
	//private String password;
	
	
	
	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

}
