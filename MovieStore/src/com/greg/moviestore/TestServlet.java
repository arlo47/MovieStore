package com.greg.moviestore;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;							//be sure to use this import if you are getting a casting issue in the try.catch block
import java.sql.Statement;							//be sure to use this import if you are getting a casting issue in the try.catch block

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;



@WebServlet("/TestServlet")
public class TestServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	//1- Inject connection pool
	@Resource(name = "jdbc/moviestoredb")
	private DataSource dataSource;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			  throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		response.setContentType("text/plain");
		
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			//2- Use dataSource to get connection to database
			myConn = dataSource.getConnection();
			
			//3- Define a SQL query
			String sql = "SELECT * FROM MovieStoreDb.users;";
			
			//4- Create a SQL query statement
			myStmt = myConn.createStatement();
			
			//5- execute query
			myRs = myStmt.executeQuery(sql);
			
			//6- Iterate on result list and extract string contents by key
			while(myRs.next()) {
				String email = myRs.getString("email");
				out.println(email);
				System.out.println(email);
			}
		}
		catch(Exception exc) {
			exc.printStackTrace();
			out.println(exc.getMessage());
		}
	}
}
