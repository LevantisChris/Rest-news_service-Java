package net.authentication.ws;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Authentication {
	
	private final String url = "jdbc:mysql://localhost:3306/news_db";
	private final String username = "root";
	private final String passwd = "kolos2020";
	
	////////////////////////////////////////////////////////////////////////////
	/// ALL THIS FOR THE CURATOR AND THE JOURNALIST ...
	public User checkCredentials(String givenUsername, String givenPassword) {
	    Connection connection = null;
	    PreparedStatement statement = null;
	    ResultSet resultSet = null;

	    try {
	        Class.forName("com.mysql.jdbc.Driver");
	        connection = DriverManager.getConnection(url, username, passwd);
	        System.out.println("\nConnected to the database...");
	        
	        String query = "SELECT * FROM USERS WHERE USERNAME = ? AND PASSWORD = ?";
	        statement = connection.prepareStatement(query);
	        statement.setString(1, givenUsername);
	        statement.setString(2, givenPassword);
	        resultSet = statement.executeQuery();

	        if (resultSet.next()) {
	        	return disiarializeResultSetAuthUser(resultSet); // return the user created/authenticated ...
	        }
	    } catch (ClassNotFoundException | SQLException e) {
	        System.out.println("An error occurred in the checkCredentials...");
	        e.printStackTrace();
	    } finally {
	        try {
	        	if(resultSet != null) {
	        		resultSet.close();
	        	}
	            if (statement != null) {
	                statement.close();
	            }
	            if (connection != null && !connection.isClosed()) {
	                connection.close();
	                System.out.println("Disconnected from the database...\n");
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    return null; // return a null because no user created/authenticated ...
	}
	/// This method takes a ResultSet and makes it a User (Journalist and a curator) ...
		private User disiarializeResultSetAuthUser(ResultSet resultSet) {
			String USERNAME = null;
			String PASSWORD = null;
			String NAME = null;
			String SURNAME = null;
			int ROLE_ID;
			User user = null;
			try {
				do {    
					    USERNAME = resultSet.getString("USERNAME");
			            PASSWORD = resultSet.getString("PASSWORD");
			            NAME = resultSet.getString("NAME");
			            SURNAME = resultSet.getString("SURNAME");
			            ROLE_ID = resultSet.getInt("ROLE_ID");
			            
			            System.out.println("-------------------------------------");
			            System.out.println("THE USER THAT AUTHENTICATED IS --> ");
			            System.out.println("USERNAME: " + USERNAME);
			            System.out.println("PASSWORD: " + PASSWORD);
			            System.out.println("NAME: " + NAME);
			            System.out.println("SURNAME: " + SURNAME);
			            System.out.println("ROLE_ID: " + ROLE_ID);
			            System.out.println("-------------------------------------");
			            
			            user = new User(USERNAME, PASSWORD, NAME, SURNAME, ROLE_ID);
		        } while (resultSet.next());
			} catch (SQLException e) {
				e.printStackTrace(); 
			}
			return user;
		}
		////////////////////////////////////////////////////////////////////////////
}
