package com.greg.moviestore;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;




public class MovieStoreDbUtil {
	
	//Singleton Variable
	private static MovieStoreDbUtil instance;
	private DataSource dataSource;
	private String jndiName = "java:comp/env/jdbc/moviestoredb";
	private Logger logger = Logger.getLogger(getClass().getName());
	
	//Singleton Method
	public static MovieStoreDbUtil getInstance() throws Exception {
		if(instance == null)
			instance = new MovieStoreDbUtil();
		
		return instance;
	}
	
	//private constructor due to singleton design pattern
	private MovieStoreDbUtil() throws Exception {
		dataSource = getDataSource();
	}
	
	private DataSource getDataSource() throws NamingException {
		Context context = new InitialContext();
		
		DataSource theDataSource = (DataSource) context.lookup(jndiName);
		
		return theDataSource;
	}
	
	private Connection getConnection() throws Exception {
		Connection theConn = dataSource.getConnection();
		
		return theConn;
	}
	
	private void close(Connection theConn, Statement theStmt) {
		close(theConn, theStmt, null);
	}
	
	private void close(Connection theConn, Statement theStmt, ResultSet theRs) {
		
		try {
			if(theRs != null)
				theRs.close();
			
			if(theStmt != null)
				theStmt.close();
		
			if(theConn != null)
				theConn.close();
		}
		catch(Exception exc) {
			exc.printStackTrace();
		}
	}
	
	//gets movies from database
	public List<Movie> getMovies() throws Exception {
		
		List<Movie> movies = new ArrayList<>();
		
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myConn = getConnection();
			
			String sql = "select * from movies";
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery(sql);
			
			while(myRs.next()) {
				
				int movieId = myRs.getInt("movieId");
				String title = myRs.getString("title");
				String yearReleased = myRs.getString("yearReleased");
				String director = myRs.getString("director");
				String imagePath = myRs.getString("imagePath");
				String genre = myRs.getString("genre");
				
				Movie movie = new Movie(movieId, title, yearReleased, director, imagePath, genre);
				
				movies.add(movie);
			}
			return movies;	
		}
		finally {
			close(myConn, myStmt, myRs);
		}
	}
	
	//gets users from database
	public List<User> getUsers() throws Exception {
		
		List<User> users = new ArrayList<>();
		
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myConn = getConnection();
			
			String sql = "select * from users";
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery(sql);
			
			while(myRs.next()) {
				
				int userId = myRs.getInt("userId");
				String firstName = myRs.getString("firstName");
				String lastName = myRs.getString("lastName");
				String phone = myRs.getString("phone");
				String address = myRs.getString("address");
				String email = myRs.getString("email");
				String username = myRs.getString("username");
				String password = myRs.getString("password");
				String updates = myRs.getString("updates");
				String privacy = myRs.getString("privacy");
									
				List<String> updatesList = Arrays.asList(updates.split("\\s*,\\s*"));
				
				User user = new User(userId, firstName, lastName, phone, address, email, username, password, updatesList, privacy);
				
				users.add(user);
			}
			return users;	
		}
		finally {
			close(myConn, myStmt, myRs);
		}
	}

	public void addUser(User theUser) throws Exception {
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		
		try {
			
			logger.info("\n\n --------------------- USER TO ADD: " + theUser + "\n\n");
			
			myConn = getConnection();
			
			String sql = "insert into users(userId, firstName, lastName, phone, address, email, username, password, updates, privacy) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			
			myStmt = myConn.prepareStatement(sql);
			
			myStmt.setInt(1, theUser.getUserId());
			myStmt.setString(2, theUser.getFirstName());
			myStmt.setString(3, theUser.getLastName());
			myStmt.setString(4, theUser.getPhone());
			myStmt.setString(5, theUser.getAddress());
			myStmt.setString(6, theUser.getEmail());
			myStmt.setString(7, theUser.getUsername());
			myStmt.setString(8, theUser.getPassword());
			myStmt.setString(9, theUser.getUpdates().toString());
			myStmt.setString(10, theUser.getPrivacy());
									
			myStmt.execute();
		}
		finally {
			close(myConn, myStmt);
		}
	}

	public void updateUser(User theUser) throws Exception {
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		
		try {
			myConn = getConnection();
			
			String sql = "update users "
							+ "set userId=?, "
								+ "firstName=?, "
								+ "lastName=?, "
								+ "phone=?, "
								+ "address=?, "
								+ "email=?, "
								+ "username=?, "
								+ "password=?, "
								+ "updates=?, "
								+ "privacy=? "
							+ "where userId=?";
			
			myStmt = myConn.prepareStatement(sql);
			
			myStmt.setInt(1, theUser.getUserId());
			myStmt.setString(2, theUser.getFirstName());
			myStmt.setString(3, theUser.getLastName());
			myStmt.setString(4, theUser.getPhone());
			myStmt.setString(5, theUser.getAddress());
			myStmt.setString(6, theUser.getEmail());
			myStmt.setString(7, theUser.getUsername());
			myStmt.setString(8, theUser.getPassword());
			myStmt.setString(9, theUser.getUpdates().toString());
			myStmt.setString(10, theUser.getPrivacy());
			myStmt.setInt(11, theUser.getUserId());
			
			myStmt.execute();
		}
		finally {
			close(myConn, myStmt);
		}
	}

	public User getUser(int theUserId) throws Exception {
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myConn = getConnection();
			
			String sql = "select * from users where userId=?";
			
			myStmt = myConn.prepareStatement(sql);
			
			myStmt.setInt(1, theUserId);
			
			myRs = myStmt.executeQuery();
			
			//set variable to null before using it
			User theUser = null;
			
			if(myRs.next()) {
				int userId = myRs.getInt("userId");
				String firstName = myRs.getString("firstName");
				String lastName = myRs.getString("lastName");
				String phone = myRs.getString("phone");
				String address = myRs.getString("address");
				String email = myRs.getString("email");
				String username = myRs.getString("username");
				String password = myRs.getString("password");
				String updates = myRs.getString("updates");
				String privacy = myRs.getString("privacy");
				
				List<String> updatesList = Arrays.asList(updates.split("\\s*,\\s*"));
				
				theUser = new User(userId, firstName, lastName, phone, address, email, username, password, updatesList, privacy);
			}
			else {
				throw new Exception("Could not find user id " + theUserId);
			}
			return theUser;
		}
		finally {
			close(myConn, myStmt, myRs);
		}
	}

	public void deleteUser(int userId) throws Exception {
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		
		try {
			myConn = getConnection();
			
			String sql = "delete from users where userId=?";
			
			myStmt = myConn.prepareStatement(sql);
			
			myStmt.setInt(1, userId);
			
			myStmt.execute();
		}
		finally {
			close(myConn, myStmt);
		}
	}

	public List<User> searchUsers(String theSearchName) throws Exception {
		
		List<User> users = new ArrayList<>();
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		
		try {
			
			myConn = dataSource.getConnection();
			
			if(theSearchName != null && theSearchName.trim().length() > 0) {
				String sql = "select * from users where lower(firstName) like ? or lower(lastName) like ?";
				
				myStmt = myConn.prepareStatement(sql);
				
				String theSearchNameLike = "%" + theSearchName.toLowerCase() + "%";
				
				myStmt.setString(1,  theSearchNameLike);
				myStmt.setString(2,  theSearchNameLike);
			}
			else {
				String sql = "select * from users";
				
				myStmt = myConn.prepareStatement(sql);
			}
			
			myRs = myStmt.executeQuery();
			
			while(myRs.next()) {
				
				int userId = myRs.getInt("userId");
				String firstName = myRs.getString("firstName");
				String lastName = myRs.getString("lastName");
				String phone = myRs.getString("phone");
				String address = myRs.getString("address");
				String email = myRs.getString("email");
				String username = myRs.getString("username");
				String password = myRs.getString("password");
				String updates = myRs.getString("updates");
				String privacy = myRs.getString("privacy");
				
				List<String> updatesList = Arrays.asList(updates.split("\\s*,\\s*"));
				
				User user = new User(userId, firstName, lastName, phone, address, email, username, password, updatesList, privacy);
				
				users.add(user);
			}
			return users;
		}
		finally {
			close(myConn, myStmt, myRs);
		}
	}

}







