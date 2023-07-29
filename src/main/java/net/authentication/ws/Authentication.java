package net.authentication.ws;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Authentication {
	
	static ArrayList<Session> LIST_SESSIONS = new ArrayList<>(); // contains all the sessions created ...
	
	private final String url = "jdbc:mysql://localhost:3306/news_db";
	private final String username = "root";
	private final String passwd = "kolos2020";
	
	////////////////////////////////////////////////////////////////////////////
	/// ALL THIS FOR THE CURATOR AND THE JOURNALIST ...
	public Session checkCredentials(String givenUsername, String givenPassword) {
	    Connection connection = null;
	    PreparedStatement statement = null;
	    ResultSet resultSet = null;

	    try {
	        Class.forName("com.mysql.jdbc.Driver");
	        connection = DriverManager.getConnection(url, username, passwd);
	        System.out.println("\nSERVER STATUS: Connected to the database...");
	        
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
		private Session disiarializeResultSetAuthUser(ResultSet resultSet) {
			String USERNAME = null;
			String PASSWORD = null;
			String NAME = null;
			String SURNAME = null;
			int ROLE_ID;
			Session USER_SESSION = null;
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
			            ////////////////////////////////////////////////////////////////////////////
			            User user = new User(USERNAME, PASSWORD, NAME, SURNAME, ROLE_ID);
			            /* We need to check if the user is already active in the System.
			             * If yes we must not create another SESSION */
			            USER_SESSION = userAlreadyHaveSession(USERNAME);
			            if(USER_SESSION == null) {
			            	int rand_id = randomSessionID();
			            	USER_SESSION = new Session(rand_id, user.getUSERNAME(), user);
				            LIST_SESSIONS.add(USER_SESSION);
				            addSessionInTheDatabase(rand_id, user.getUSERNAME());
			            }
			            printUser();
			            return USER_SESSION;
			            ////////////////////////////////////////////////////////////////////////////
		        } while (resultSet.next());
			} catch (SQLException e) {
				e.printStackTrace(); 
			}
			return null;
		}
		
		/* Check if the user already have a session */
		private Session userAlreadyHaveSession(String username) {
		    for(Iterator<Session> iterator = LIST_SESSIONS.iterator(); iterator.hasNext(); ) {
		        Session session = iterator.next();
		        if(session.getUSERNAME_BELONGS().equals(username)) {
		            if(session.isExpired()) {
		                iterator.remove(); // remove expired session
		                deleteSessionFromDatabase(session.getSESSION_ID()); // remove expired session from database
		                return null; // expired session found and removed, return null to create a new one
		            } else {
		                return session; // valid session found, return it
		            }
		        }
		    }
		    return null; // no session found, return null to create a new one
		}
		
		/* Create a random session id */
		private int randomSessionID() {
			Random rand = new Random();
			int randomNum;
			while(true) {
	        	randomNum = rand.nextInt(9000) + 1000; // number between 1000 and 9000
	        	if(checkIfSessionIDExists(randomNum) == false) {
	        		break; // break if the session id is unique
	        	}
			}
	        System.out.println("SERVER STATUS: RANDOM NUMBER GENERATED FOR SESSION --> " + randomNum);
	        return randomNum;
		}
		/* Check if the session id created is unique */
		private boolean checkIfSessionIDExists(int randomNum) {
			
			String url = "jdbc:mysql://localhost:3306/news_db";
		    String username_DB = "root";
		    String passwd = "kolos2020";
		    
		    Connection connection = null;
		    PreparedStatement selectStatement = null;
		    
		    String selectQuery = "SELECT * FROM news_db.sessions WHERE ID = ?;";
		    
		    try {
		    	connection = DriverManager.getConnection(url, username_DB, passwd);
		        System.out.println("\nSERVER STATUS: Connected to the database...");
			    
		        selectStatement = connection.prepareStatement(selectQuery);
		        selectStatement.setInt(1, randomNum);
		        ResultSet resultSet = selectStatement.executeQuery();
		        if(resultSet.next()) {
		        	return true;
		        }
			    return false;
		    } catch(SQLException e) {
		    	e.printStackTrace();
		    } finally {
		        try {
		            if (selectStatement != null) {
		                selectStatement.close();
		            }
		            if (connection != null && !connection.isClosed()) {
		                connection.close();
		                System.out.println("Disconnected from the database...\n");
		            }
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
		    }
		    return false;			
	}
		
		
		/* Add/Create the session in the database */
		private boolean addSessionInTheDatabase(int id, String username_belongs) {
			String url = "jdbc:mysql://localhost:3306/news_db";
		    String username_DB = "root";
		    String passwd = "kolos2020";
		    
		    Connection connection = null;
		    PreparedStatement insertStatement = null;
		    String insertQuery = "INSERT INTO news_db.sessions (ID, USERNAME_BELONGS) " +
		            		     "VALUES (?, ?)"; 

		    try {
		    	connection = DriverManager.getConnection(url, username_DB, passwd);
		        System.out.println("\nSERVER STATUS: Connected to the database...");
		    	insertStatement = connection.prepareStatement(insertQuery);
			    insertStatement.setInt(1, id);
			    insertStatement.setString(2, username_belongs);
			        
			    int rowsAffected = insertStatement.executeUpdate();

			    if (rowsAffected > 0) {
			        System.out.println("SERVER STATUS: Insert successful for the session");
			        return true;
			    } else {
			        System.out.println("SERVER STATUS: --ERROR-- Session not inserted");
			        return false;
			    }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    } finally {
		        try {
		            if (insertStatement != null) {
		                insertStatement.close();
		            }
		            if (connection != null && !connection.isClosed()) {
		                connection.close();
		                System.out.println("Disconnected from the database...\n");
		            }
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
		    }
		    return false;
		}
		
		private boolean deleteSessionFromDatabase(int session_id) {
			String url = "jdbc:mysql://localhost:3306/news_db";
		    String username_DB = "root";
		    String passwd = "kolos2020";
		    
		    Connection connection = null;
		    PreparedStatement deleteStatement = null;
		    String deleteQuery = "DELETE FROM news_db.sessions WHERE ID = ?";

		    try {
		    	connection = DriverManager.getConnection(url, username_DB, passwd);
		        System.out.println("\nSERVER STATUS: Connected to the database...");
		        deleteStatement = connection.prepareStatement(deleteQuery);
		        deleteStatement.setInt(1, session_id);
			        
			    int rowsAffected = deleteStatement.executeUpdate();

			    if (rowsAffected > 0) {
			        System.out.println("SERVER STATUS: Deletion successful for the session");
			        return true;
			    } else {
			        System.out.println("SERVER STATUS: --ERROR-- Session not deleted");
			        return false;
			    }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    } finally {
		        try {
		            if (deleteStatement != null) {
		            	deleteStatement.close();
		            }
		            if (connection != null && !connection.isClosed()) {
		                connection.close();
		                System.out.println("Disconnected from the database...\n");
		            }
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
		    }
		    return false;
		}
		
		////////////////////////////////////////////////////////////////////////////
		
		/* Every time a User is entering the Service we will 
		 * display in the terminal all the users that are
		 * the system */
		private void printUser() {
			System.out.println("\n\n\n======================================================================ALL THE USER ACTIVE ONE SYSTEM======================================================================");
			for(int i = 0;i < LIST_SESSIONS.size();i++) {
				System.out.println("-------------------------------------------------------------------------------------");
				System.out.println("--> Session ID == " + LIST_SESSIONS.get(i).getSESSION_ID());
				System.out.println("--> Username belongs == " + LIST_SESSIONS.get(i).getUSERNAME_BELONGS());
				System.out.println("--> USER INFO ==> ");
				System.out.println("----> User username == " + LIST_SESSIONS.get(i).getUSER_BELONGS().getUSERNAME());
				System.out.println("----> Role ID == " + LIST_SESSIONS.get(i).getUSER_BELONGS().getROLE_ID());
				System.out.println("-------------------------------------------------------------------------------------");
			}
			System.out.println("==========================================================================================================================================================================\n\n\n");
		}
}
