package com.greg.moviestore;

import javax.faces.bean.ManagedBean;

@ManagedBean
public class Movie {
	private int movieId;
	private String title;
	private String yearReleased;
	private String director;
	private String imagePath;
	private String genre;
	
	public Movie() {}
	
	//args constructor for pre-rendering xhtml page
	public Movie(int movieId, 
				 String title, 
				 String yearReleased, 
				 String director, 
				 String imagePath, 
				 String genre) {
		
		this.movieId = movieId;
		this.title = title;
		this.yearReleased = yearReleased;
		this.director = director;
		this.imagePath = imagePath;
		this.genre = genre;
	}

	public int getMovieId() {
		return movieId;
	}

	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getYearReleased() {
		return yearReleased;
	}

	public void setYearReleased(String yearReleased) {
		this.yearReleased = yearReleased;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}
	
	public String toString() {
		return "[" + title + " by " + director + "]";
	}
	
	
}
