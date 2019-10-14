package com.greg.moviestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;


@ManagedBean
@SessionScoped
public class UserController {
	
	private List<User> users;
	private MovieStoreDbUtil movieStoreDbUtil;
	private Logger logger = Logger.getLogger(getClass().getName());
	private String theSearchName;
	
	public UserController() throws Exception {
		users = new ArrayList<>();
		
		//get singleton object
		movieStoreDbUtil = MovieStoreDbUtil.getInstance();
	}
	
	public List<User> getUsers() {
		return users;
	}

	public String getTheSearchName() {
		return theSearchName;
	}
	
	public void setTheSearchName(String theSearchName) {
		this.theSearchName = theSearchName;
	}
	
	public void loadUsers() {
		logger.info("Loading Users");
		logger.info("theSearchName = " + theSearchName);
		
		try {
			
			if(theSearchName != null && theSearchName.trim().length() > 0)
				users = movieStoreDbUtil.searchUsers(theSearchName);
			
			else
				users = movieStoreDbUtil.getUsers();
		}
		catch(Exception exc) {
			logger.log(Level.SEVERE, "Error loading users", exc);
			
			addErrorMessage(exc);
		}
		finally {
			theSearchName = null;
		}

	}
	
	public String addUser(User theUser) {
		
		logger.info("\n\n ------------------------------ Addiing User: " + theUser + "\n\n");
		
		try {
			movieStoreDbUtil.addUser(theUser);
		}
		catch(Exception exc) {
			logger.log(Level.SEVERE, "Error adding user", exc);
			
			addErrorMessage(exc);
			
			return null;
		}
		return "profile_preview.xhtml";
	}
	
	//Used for pre-populating form on update_user.xhtml
	public String loadUser(int theUserId) {
		
		logger.info("\n\n ----------------------- loading user with id: " + theUserId);
		
		try {
			User theUser = movieStoreDbUtil.getUser(theUserId);
			
			logger.info("\n\n ------------------ getUser() invoked \n theUser: " + theUser + "\n");
			
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			
			Map<String, Object> requestMap = externalContext.getRequestMap();

			requestMap.put("theUser", theUser);
			
			logger.info("\n\n ------------------ requestMap.toString() \n " + requestMap.toString() + "\n");
		}
		catch(Exception exc) {
			logger.log(Level.SEVERE, "error loading user id " + theUserId, exc);
			
			return null;
		}
		return "update_user.xhtml";

	}
	
	public String updateUser(User theUser) {
		
		logger.info("Updating student: " + theUser);
		
		try {
			movieStoreDbUtil.updateUser(theUser);
		}
		catch (Exception exc) {
			logger.log(Level.SEVERE, "Error updating user: " + theUser, exc);
			
			addErrorMessage(exc);
			
			return null;
		}
		return "user_management.xhtml?faces-redirect=true";
	}
	
	public String deleteUser(int userId) {
		
		logger.info("\n\n ------------------------------------------- Deleting user id: " + userId);
		
		try {
			movieStoreDbUtil.deleteUser(userId);
		}
		catch(Exception exc) {
			logger.log(Level.SEVERE, "Error deleting user id " + userId, exc);
			
			addErrorMessage(exc);
		}
		return "user_management";
	}
	
	//Error message on xhtml page
	private void addErrorMessage(Exception exc) {
		FacesMessage message = new FacesMessage("Error: " + exc.getMessage());
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
}







