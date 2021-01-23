package ba.bootcamp.request;

import java.io.Serializable;

public class EmployeeRequest implements Serializable {
	
	private static final long serialVersionUID = 1L;	

	
	private String firstName;
	private String lastName;
	private String emailId;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

}
