package com.greg.moviestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

@ManagedBean
@SessionScoped
public class User {
	private int userId;
	private String firstName;
	private String lastName;
	private String phone;
	private String address;
	private String email;
	private String username;
	private String password;
	private List<String> updates;
	private String privacy;
	private String confirmOrUpdate;
	
	public User() {
		//default values for privacy and updates
		this.privacy = "public";
		updates = new ArrayList<>(Arrays.asList("monthly", "weekly"));
	}
	
	//args constructor for pre-rendering xhtml page
	public User(int userId, 
				String firstName,
				String lastName, 
				String phone, 
				String address, 
				String email,
				String username, 
				String password, 
				List<String> updates, 
				String privacy) {
		
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
		this.address = address;
		this.email = email;
		this.username = username;
		this.password = password;
		this.updates = updates;
		this.privacy = privacy;
		this.confirmOrUpdate = "confirm";
	}

	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<String> getUpdates() {
		return updates;
	}
	
	public void setUpdates(List<String> updates) {
		this.updates = updates;
	}

	public String getPrivacy() {
		return this.privacy;
	}

	public void setPrivacy(String privacy) {
		this.privacy = privacy;
	}
	
	public String getConfirmOrUpdate() {
		return this.confirmOrUpdate;
	}
	
	public void setConfirmOrUpdate(String confirmOrUpdate) {
		this.confirmOrUpdate = confirmOrUpdate;
	}
	
	public void validateUserName(FacesContext context, UIComponent component, Object value) 
		   throws ValidatorException {
		
		if(value == null)
			return;
		
		String data = value.toString();
		
		if(!data.startsWith("IPD17")) {
			FacesMessage message = new FacesMessage("Username must start with IPD17");
			throw new ValidatorException(message);
		}
	}
	
	public String confirmUserInformation(int theUserId) throws Exception {
		
		if(confirmOrUpdate.equalsIgnoreCase("confirm"))
			return "shopping";

		String redirect =  new UserController().loadUser(theUserId);

		return redirect;
		
	}
	
	public String toString() {
		return "[" + 
					 "Id: " 			+ userId 	+ "\n" + 
				     " First Name: " 	+ firstName + "\n" + 
				     " Last Name: " 	+ lastName 	+ "\n" + 
				     " Phone: " 		+ phone 	+ "\n" + 
				     " Address: " 		+ address 	+ "\n" + 
				     " Email: " 		+ email 	+ "\n" + 
				     " Username: " 		+ username 	+ "\n" + 
				     " Password: " 		+ password 	+ "\n" + 
				     " Updates: " 		+ updates 	+ "\n" + 
				     " Privacy: " 		+ privacy 	+ "\n" + 
			   "]";
	}
}

