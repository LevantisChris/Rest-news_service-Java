package net.sessionExtractor.ws;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SessionExtractor implements ExtractSession_ID {

	@Override
	public boolean checkIfSessionExists(String sessionId) {
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
	        selectStatement.setInt(1, Integer.parseInt(sessionId));
	        ResultSet resultSet = selectStatement.executeQuery();

	        if(resultSet.next()) {
	        	return true;
	        } else 
	        	return false;
	    } catch(SQLException e) {
	    	e.printStackTrace();
	    	return false;
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
	}
	
	
	/* Articles Control */
	@Override
	public boolean checkIfArticleCanBeViewed(String sessionId, 
											 String articleId,
											 int functionState) {
		String username = getUsernameFromSession(sessionId);
		String role = getRoleFromSession(sessionId);
		System.out.println("SERVER STATUS: AUTHORIZE for article with ID == " + articleId + " about username == " + username + " and role == " + role);
		
		String url = "jdbc:mysql://localhost:3306/news_db";
	    String username_DB = "root";
	    String passwd = "kolos2020";
	    
	    Connection connection = null;
	    PreparedStatement selectStatement = null;
	    
	    String selectQuery = "SELECT STATE_ID, CREATOR_USERNAME, alert FROM news_db.articles WHERE ID = ?;";
	    
	    String state_id = null, creator_username = null;
	    boolean alert = false;
	    try {
	    	connection = DriverManager.getConnection(url, username_DB, passwd);
	        System.out.println("\nSERVER STATUS: Connected to the database...");
		    
	        selectStatement = connection.prepareStatement(selectQuery);
	        selectStatement.setInt(1, Integer.parseInt(articleId));
	        ResultSet resultSet = selectStatement.executeQuery();

	        while(resultSet.next()) {
	        	state_id = resultSet.getString("STATE_ID");
	        	creator_username = resultSet.getString("CREATOR_USERNAME");
	        	alert = resultSet.getBoolean("alert");
	        }
	        if(state_id == null || creator_username == null) {
	        	return false;
	        }
	        /* At start, the article must be in the state the function allows to be */
	        if(Integer.parseInt(state_id) == functionState) {
	        	/* Secondly we must see if the article belongs to him */
		        if(role.equals("JOURNALIST")) {
		        	if(creator_username.equals(username)) {
		        		if(alert != true)
		        			return true;
		        	}
		        } else if(role.equals("CURATOR")) { // if the role is a Curator he can all the articles but only the one that have the correct state of the function requested
		        	if(alert != true)
		        		return true;
		        }
	        }
	        return false;
	    } catch(SQLException e) {
	    	e.printStackTrace();
	    	return false;
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
	}
	
	@Override
	public boolean checkIfArticleCanBeViewed(String sessionId, String articleId, int functionState1,
			int functionState2) {
		String username = getUsernameFromSession(sessionId);
		String role = getRoleFromSession(sessionId);
		System.out.println("SERVER STATUS: AUTHORIZE for article with ID == " + articleId + " about username == " + username + " and role == " + role);
		
		String url = "jdbc:mysql://localhost:3306/news_db";
	    String username_DB = "root";
	    String passwd = "kolos2020";
	    
	    Connection connection = null;
	    PreparedStatement selectStatement = null;
	    
	    String selectQuery = "SELECT STATE_ID, CREATOR_USERNAME, alert FROM news_db.articles WHERE ID = ?;";
	    
	    String state_id = null, creator_username = null;
	    boolean alert = false;
	    try {
	    	connection = DriverManager.getConnection(url, username_DB, passwd);
	        System.out.println("\nSERVER STATUS: Connected to the database...");
		    
	        selectStatement = connection.prepareStatement(selectQuery);
	        selectStatement.setInt(1, Integer.parseInt(articleId));
	        ResultSet resultSet = selectStatement.executeQuery();

	        while(resultSet.next()) {
	        	state_id = resultSet.getString("STATE_ID");
	        	creator_username = resultSet.getString("CREATOR_USERNAME");
	        	alert = resultSet.getBoolean("alert");
	        }
	        if(state_id == null || creator_username == null) {
	        	return false;
	        }
	        /* At start, the article must be in the state the function allows to be (here we have two states) */
	        if(Integer.parseInt(state_id) == functionState1  || Integer.parseInt(state_id) == functionState2) {
	        	/* Secondly we must see if the article belongs to him */
		        if(role.equals("JOURNALIST")) {
		        	if(creator_username.equals(username)) {
		        		if(alert != true)
		        			return true;
		        	}
		        } else if(role.equals("CURATOR")) { // if the role is a Curator he can all the articles but only the one that have the correct state of the function requested
		        	if(alert != true)
		        		return true;
		        }
	        }
	        return false;
	    } catch(SQLException e) {
	    	e.printStackTrace();
	    	return false;
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
	}
	
	@Override
	public boolean checkIfArticleCanBeViewed(String sessionId, String articleId, int functionState,
			String description) {
		String username = getUsernameFromSession(sessionId);
		String role = getRoleFromSession(sessionId);
		System.out.println("SERVER STATUS: AUTHORIZE for article with ID == " + articleId + " about username == " + username + " and role == " + role);
		
		String url = "jdbc:mysql://localhost:3306/news_db";
	    String username_DB = "root";
	    String passwd = "kolos2020";
	    
	    Connection connection = null;
	    PreparedStatement selectStatement = null;
	    
	    String selectQuery = "SELECT STATE_ID, CREATOR_USERNAME, alert FROM news_db.articles WHERE ID = ?;";
	    
	    String state_id = null, creator_username = null;
	    boolean alert = false;
	    try {
	    	connection = DriverManager.getConnection(url, username_DB, passwd);
	        System.out.println("\nSERVER STATUS: Connected to the database...");
		    
	        selectStatement = connection.prepareStatement(selectQuery);
	        selectStatement.setInt(1, Integer.parseInt(articleId));
	        ResultSet resultSet = selectStatement.executeQuery();

	        while(resultSet.next()) {
	        	state_id = resultSet.getString("STATE_ID");
	        	creator_username = resultSet.getString("CREATOR_USERNAME");
	        	alert = resultSet.getBoolean("alert");
	        }
	        if(state_id == null || creator_username == null) {
	        	return false;
	        }
	        
	        /* For the display article, we have to add a specific if statement 
	         * 
	         * We allow a JOURNALIST to view an article  */
	        if(description.equals("display")) {
		        if(role.equals("JOURNALIST")) {
		        		if(creator_username.equals(username) || Integer.parseInt(state_id) == functionState) {
			        		if(alert != true)
			        			return true;
		        		}
		        	}
		    } 
	        
	        if(description.equals("displayCommetsOfArticle")) {
	        	/* The curator can see all the articles that have inside them comments, but someone cannot view a comment 
	        	 * that has not any associated comment with it. So we need to check whether the id of the requested article
	        	 * is inside the table of the comments */
	        	if(checkArtcleIfHasComments(Integer.parseInt(articleId), role) == true) {
        			return true;
        		} else {
        			return false;
        		}
	        }
	        
	        /* At start, the article must be in the state the function allows to be */
	        if(Integer.parseInt(state_id) == functionState) {
	        	/* Secondly we must see if the article belongs to him */
		        if(role.equals("JOURNALIST")) {
		        	if(creator_username.equals(username)) {
		        		if(!description.equals("modify")) { // in the modify article we want to see the recourses that have alert
			        		if(alert != true)
			        			return true;
		        		} else 
			        		return true;
		        	}
		        } else if(role.equals("CURATOR")) { // if the role is a Curator he can all the articles but only the one that have the correct state of the function requested
		        	if(!description.equals("modify")) { // in the modify article we want to see the recourses that have alert
		        		if(alert != true)
			        		return true;
		        	} else 
		        		return true;
		        }
	        }
	        return false;
	    } catch(SQLException e) {
	    	e.printStackTrace();
	    	return false;
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
	}
	
	private boolean checkArtcleIfHasComments(int article_id, String role) {		
		String url = "jdbc:mysql://localhost:3306/news_db";
	    String username_DB = "root";
	    String passwd = "kolos2020";
	    
	    Connection connection = null;
	    PreparedStatement selectStatement = null;
	    
	    String selectQuery_Curator = "SELECT ID FROM news_db.comments WHERE ARTICLE_ID = ?;";
	    String selectQuery_journalist = "SELECT C.*\r\n"
				+ "FROM COMMENTS C\r\n"
				+ "INNER JOIN ARTICLES A ON C.ARTICLE_ID = A.ID\r\n"
				+ "WHERE ARTICLE_ID = ? AND (C.STATE_ID = 3 AND A.STATE_ID = 4);";
	    
	    try {
	    	connection = DriverManager.getConnection(url, username_DB, passwd);
	        System.out.println("\nSERVER STATUS: Connected to the database...");
		    
	        if(role.equals("CURATOR")) {
		        selectStatement = connection.prepareStatement(selectQuery_Curator);
		        selectStatement.setInt(1,article_id);
		        ResultSet resultSet = selectStatement.executeQuery();
	
		        if(resultSet.next()) {
			      	return true;
		        } else {
		        	return false;
		        }
	        } else { // journalist 
	        	selectStatement = connection.prepareStatement(selectQuery_journalist);
		        selectStatement.setInt(1,article_id);
		        ResultSet resultSet = selectStatement.executeQuery();
	
		        if(resultSet.next()) {
			      	return true;
		        } else {
		        	return false;
		        }
	        }  
	    } catch(SQLException e) {
	    	e.printStackTrace();
	    	return false;
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
	}
	
	@Override
	public boolean checkIfArticleCanBeViewed(String sessionId, String articleId, int functionState1, int functionState2,
			String description) {
		String username = getUsernameFromSession(sessionId);
		String role = getRoleFromSession(sessionId);
		System.out.println("SERVER STATUS: AUTHORIZE for article with ID == " + articleId + " about username == " + username + " and role == " + role);
		
		String url = "jdbc:mysql://localhost:3306/news_db";
	    String username_DB = "root";
	    String passwd = "kolos2020";
	    
	    Connection connection = null;
	    PreparedStatement selectStatement = null;
	    
	    String selectQuery = "SELECT STATE_ID, CREATOR_USERNAME, alert FROM news_db.articles WHERE ID = ?;";
	    
	    String state_id = null, creator_username = null;
	    boolean alert = false;
	    try {
	    	connection = DriverManager.getConnection(url, username_DB, passwd);
	        System.out.println("\nSERVER STATUS: Connected to the database...");
		    
	        selectStatement = connection.prepareStatement(selectQuery);
	        selectStatement.setInt(1, Integer.parseInt(articleId));
	        ResultSet resultSet = selectStatement.executeQuery();

	        while(resultSet.next()) {
	        	state_id = resultSet.getString("STATE_ID");
	        	creator_username = resultSet.getString("CREATOR_USERNAME");
	        	alert = resultSet.getBoolean("alert");
	        }
	        if(state_id == null || creator_username == null) {
	        	return false;
	        }
	        /* At start, the article must be in the state the function allows to be (here we have two states) */
	        if(Integer.parseInt(state_id) == functionState1  || Integer.parseInt(state_id) == functionState2) {
	        	/* Secondly we must see if the article belongs to him */
		        if(role.equals("JOURNALIST")) {
		        	if(creator_username.equals(username)) {
		        		if(!description.equals("modify")) {
			        		if(alert != true)
			        			return true;
		        		} else 
			        		return true;
		        	}
		        } else if(role.equals("CURATOR")) { // if the role is a Curator he can all the articles but only the one that have the correct state of the function requested
		        	if(!description.equals("modify")) {
		        		if(alert != true)
			        		return true;
		        	} else 
		        		return true;
		        }
	        }
	        return false;
	    } catch(SQLException e) {
	    	e.printStackTrace();
	    	return false;
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
	}
	
	/* We will just look up in the Database for the username */
	@Override
	public String getUsernameFromSession(String sessionId) {
		String url = "jdbc:mysql://localhost:3306/news_db";
	    String username_DB = "root";
	    String passwd = "kolos2020";
	    
	    Connection connection = null;
	    PreparedStatement selectStatement = null;
	    
	    String selectQuery = "SELECT USERNAME_BELONGS FROM news_db.sessions WHERE ID = ?;";
	    
	    try {
	    	connection = DriverManager.getConnection(url, username_DB, passwd);
	        System.out.println("\nSERVER STATUS: Connected to the database...");
		    
	        selectStatement = connection.prepareStatement(selectQuery);
	        selectStatement.setInt(1, Integer.parseInt(sessionId));
	        ResultSet resultSet = selectStatement.executeQuery();

	        String username_belongs = null;
	        while(resultSet.next()) {
	        	username_belongs = resultSet.getString("USERNAME_BELONGS");
	        }
		    return username_belongs;
	    } catch(SQLException e) {
	    	e.printStackTrace();
	    	return null;
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
	}
	
	/* We will just look up in the Database for the role 
	 * 
	 * First we get the username, then we get the role_id*/
	@Override
	public String getRoleFromSession(String sessionId) {
		String username = getUsernameFromSession(sessionId);
		
		String url = "jdbc:mysql://localhost:3306/news_db";
	    String username_DB = "root";
	    String passwd = "kolos2020";
	    
	    Connection connection = null;
	    PreparedStatement selectStatement = null;
	    
	    String selectQuery = "SELECT ROLE_ID FROM news_db.users WHERE USERNAME = ?;";
	    
	    int role_id = -1;
	    String role_definition = null;
	    try {
	    	connection = DriverManager.getConnection(url, username_DB, passwd);
	        System.out.println("\nSERVER STATUS: Connected to the database...");
		    
	        selectStatement = connection.prepareStatement(selectQuery);
	        selectStatement.setString(1, username);
	        ResultSet resultSet = selectStatement.executeQuery();

	        while(resultSet.next()) {
	        	role_id = resultSet.getInt("ROLE_ID");
	        }
	        if(role_id == -1) {
	        	role_definition = null;
	        } else if (role_id == 2) {
	        	role_definition = "JOURNALIST";
	        } else {
	        	role_definition = "CURATOR";
	        }
		    return role_definition;
	    } catch(SQLException e) {
	    	e.printStackTrace();
	    	return role_definition;
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
	}

	
	/* Topics Control */
	@Override
	public boolean checkIfTopicCanBeViewed(String sessionId, String topicId, int functionState) {
		String username = getUsernameFromSession(sessionId);
		String role = getRoleFromSession(sessionId);
		System.out.println("SERVER STATUS: AUTHORIZE for article with ID == " + topicId + " about username == " + username + " and role == " + role);
		
		String url = "jdbc:mysql://localhost:3306/news_db";
	    String username_DB = "root";
	    String passwd = "kolos2020";
	    
	    Connection connection = null;
	    PreparedStatement selectStatement = null;
	    
	    String selectQuery = "SELECT STATE_ID, CREATOR_USERNAME FROM news_db.topic WHERE ID = ?;";
	    
	    String state_id = null, creator_username = null;
	    boolean alert = false;
	    try {
	    	connection = DriverManager.getConnection(url, username_DB, passwd);
	        System.out.println("\nSERVER STATUS: Connected to the database...");
		    
	        selectStatement = connection.prepareStatement(selectQuery);
	        selectStatement.setInt(1, Integer.parseInt(topicId));
	        ResultSet resultSet = selectStatement.executeQuery();

	        while(resultSet.next()) {
	        	state_id = resultSet.getString("STATE_ID");
	        	creator_username = resultSet.getString("CREATOR_USERNAME");
	        }
	        if(state_id == null || creator_username == null) {
	        	return false;
	        }
	        /* At start, the topic must be in the state the function allows to be */
	        if(Integer.parseInt(state_id) == functionState) {
	        	return true;
	        }
	        return false;
	    } catch(SQLException e) {
	    	e.printStackTrace();
	    	return false;
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
	}
	
	@Override
	public boolean checkIfTopicCanBeViewed(String sessionId, String topicId, int functionState, String description) {
		String username = getUsernameFromSession(sessionId);
		String role = getRoleFromSession(sessionId);
		System.out.println("SERVER STATUS: AUTHORIZE for article with ID == " + topicId + " about username == " + username + " and role == " + role);
		
		String url = "jdbc:mysql://localhost:3306/news_db";
	    String username_DB = "root";
	    String passwd = "kolos2020";
	    
	    Connection connection = null;
	    PreparedStatement selectStatement = null;
	    
	    String selectQuery = "SELECT STATE_ID, CREATOR_USERNAME FROM news_db.topic WHERE ID = ?;";
	    
	    String state_id = null, creator_username = null;
	    boolean alert = false;
	    try {
	    	connection = DriverManager.getConnection(url, username_DB, passwd);
	        System.out.println("\nSERVER STATUS: Connected to the database...");
		    
	        selectStatement = connection.prepareStatement(selectQuery);
	        selectStatement.setInt(1, Integer.parseInt(topicId));
	        ResultSet resultSet = selectStatement.executeQuery();

	        while(resultSet.next()) {
	        	state_id = resultSet.getString("STATE_ID");
	        	creator_username = resultSet.getString("CREATOR_USERNAME");
	        }
	        if(state_id == null || creator_username == null) {
	        	return false;
	        }
	        
	        if(description.equals("display")) {
		        if(role.equals("JOURNALIST")) {
		        		if(creator_username.equals(username) || Integer.parseInt(state_id) == functionState) {
			        		return true;
		        		}
		        	}
		    }
	        
	        /* At start, the topic must be in the state the function allows to be */
	        if(Integer.parseInt(state_id) == functionState) {
	        	return true;
	        }
	        return false;
	    } catch(SQLException e) {
	    	e.printStackTrace();
	    	return false;
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
	}
}