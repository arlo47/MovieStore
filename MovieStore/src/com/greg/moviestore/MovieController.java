package com.greg.moviestore;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;



@ManagedBean
@SessionScoped
public class MovieController {

	private List<Movie> movies;
	private MovieStoreDbUtil movieStoreDbUtil;
	private Logger logger = Logger.getLogger(getClass().getName());

	public MovieController() throws Exception {
		movies = new ArrayList<>();
		
		//get instance of singleton object
		movieStoreDbUtil = MovieStoreDbUtil.getInstance();
	}
	
	public List<Movie> getMovies() {
		return movies;
	}

	public void loadMovies() {
		
		logger.info("Loading Movies");
		
		try {
			movies = movieStoreDbUtil.getMovies();
		}
		catch(Exception exc) {
			logger.log(Level.SEVERE, "Error loading movies", exc);
			
			addErrorMessage(exc);
		}
	}

	
	
	private void addErrorMessage(Exception exc) {
		FacesMessage message = new FacesMessage("Error: " + exc.getMessage());
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
}
