package net.topics.ws;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import jakarta.ws.rs.CookieParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import net.htmlhandler.ws.HtmlHandler;
import net.sessionExtractor.ws.SessionExtractor;
import net.topics.ws.manage_topics.Topic;

@Path("/auth/not_auth_user/search_topic")
public class SearchTopic_Resource_not_auth {
	
	@GET
	public Response handleKeyPhrasesNotAuthUserArticles(@CookieParam("session_id") String sessionId) {
		if(sessionId == null || sessionId.isBlank()) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
		
		///
		/* Get the user that has the session and also the role */
		SessionExtractor sessionExtractor = new SessionExtractor();
		if(sessionExtractor.checkIfSessionExists(sessionId) == false) {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
		String username = sessionExtractor.getUsernameFromSession(sessionId);
		String role = sessionExtractor.getRoleFromSession(sessionId);
		System.out.println("SERVER STATUS: SESSION_ID NUM: " + sessionId +" USERNAME extracted is --> " + username + " and ROLE extracted is " + role);
		///
		
		System.out.println("SERVER STATUS: SEARCH TOPIC (not_auth) CALLED BY USERNAME == " + "--NULL--" + " - ROLE == " + role);
		if(role == null || role.isEmpty()) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		int ROLE_ID;
		if(role.equals("VISITOR")) {
			ROLE_ID = 1;
			return Response.status(Response.Status.OK)
	                .entity(HtmlHandler.getSEARCH_TOPIC_KEY_PHRASES_HTML("Visitor", role, ROLE_ID))
	                .type(MediaType.TEXT_HTML)
	                .build();
		} else { // if someone else get here we have a problem ...
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity("ROLE_NOT_IDENTIFIED").build();
		}
	}
	
	@GET
	@Path("/search")
	public Response sendData(@CookieParam("session_id") String sessionId,
	                         @QueryParam("titleKeyPhrases") String titleKeyPhrases) {
		if(sessionId == null || sessionId.isBlank()) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
		
		///
		/* Get the user that has the session and also the role */
		SessionExtractor sessionExtractor = new SessionExtractor();
		if(sessionExtractor.checkIfSessionExists(sessionId) == false) {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
		String username = sessionExtractor.getUsernameFromSession(sessionId);
		String role = sessionExtractor.getRoleFromSession(sessionId);
		System.out.println("SERVER STATUS: SESSION_ID NUM: " + sessionId +" USERNAME extracted is --> " + username + " and ROLE extracted is " + role);
		///
		
		System.out.println("username --> " + username);
	    System.out.println("role --> " + role);
	    System.out.println("titleKeyPhrases --> " + titleKeyPhrases);

	    if(role == null || titleKeyPhrases == null) { // If something is null return error
	    	return Response.serverError().build();
	    } else if(titleKeyPhrases.isEmpty()) { // if the key is empty we can not proceed
	    	return Response.ok("KEY_IS_EMPTY").build();
	    } else { 
	    	if(hasWords(titleKeyPhrases) == false) {
	    		ArrayList<Topic> GOAL_TOPICS = searchOneWord(titleKeyPhrases);
	    		printTopicsGet(GOAL_TOPICS);
	    		return Response.status(Response.Status.OK)
		                .entity(HtmlHandler.getTopicsFromSEARCH_ARTICLES(GOAL_TOPICS))
		                .type(MediaType.TEXT_HTML)
		                .build();
	    	} else if(hasWords(titleKeyPhrases) == true) {
	    		ArrayList<String> titleKeyPhrasesArray = splitStrings(titleKeyPhrases);
	    		ArrayList<Topic> GOAL_TOPICS = searchTwoWord(username, role, titleKeyPhrasesArray);		
	    		printTopicsGet(GOAL_TOPICS);
	    		return Response.status(Response.Status.OK)
		                .entity(HtmlHandler.getTopicsFromSEARCH_ARTICLES(GOAL_TOPICS))
		                .type(MediaType.TEXT_HTML)
		                .build();
	    	} else {
	    		return Response.serverError().build();
	    	}
	    }
	}
	
	/*------------------------------------------------------------------------------------------------------------------------*/
	
	private void printTopicsGet(ArrayList<Topic> topics) {
		System.out.println("------------------------------------------------");
		for(int i = 0;i < topics.size();i++) {
			System.out.println("--> " + topics.get(i).getTitle());
		}
		System.out.println("------------------------------------------------");
	}
	
	private ArrayList<Topic> searchTwoWord(String username, String role, ArrayList<String> titleKeyPhrasesArray) {
		
		ArrayList<Topic> GOAL_TOPICS = new ArrayList<>();
		
		String url = "jdbc:mysql://localhost:3306/news_db";
	    String username_DB = "root";
	    String passwd = "kolos2020";
	    
	    String selectQuery;
	    Connection connection = null;
	    PreparedStatement selectStatement = null;
	    ResultSet resultSet = null;
	    
	    try {
	    	connection = DriverManager.getConnection(url, username_DB, passwd);
    		System.out.println("\nSERVER STATUS: Connected to the database...");
	    	
    		selectQuery = "SELECT ID, TITLE, DATE_CREATION, "
    	    		+ "STATE_ID, CREATOR_USERNAME, "
    	    		+ "PARENT_TOPIC_ID "
    	    		+ "FROM news_db.topic "
    	    		+ "WHERE "; // The curator can see everything
    		selectQuery = selectQuery + " TITLE LIKE ?";
    	    for(int i = 1;i < titleKeyPhrasesArray.size();i++) {
    	    	selectQuery = selectQuery + " AND TITLE LIKE ?";
    	    }
    	    
    	    selectQuery += "AND STATE_ID = 3;";
    		
    	    selectStatement = connection.prepareStatement(selectQuery);
    	    for(int i = 0;i < titleKeyPhrasesArray.size();i++) {
    	    	selectStatement.setString(i + 1, "%" + titleKeyPhrasesArray.get(i) + "%");
    	    }
	    	resultSet = selectStatement.executeQuery();
	    	
	    	if(resultSet != null) {
		        while(resultSet.next()) {
		        	int topic_Id = resultSet.getInt("ID");
		        	String topic_Title = resultSet.getString("TITLE");
		        	Date topic_DateCreation = resultSet.getDate("DATE_CREATION");
		        	int topic_StateId = resultSet.getInt("STATE_ID");
		        	String topic_CreatorUsername = resultSet.getString("CREATOR_USERNAME");
		        	int topic_Parent_topic_id = resultSet.getInt("PARENT_TOPIC_ID");
		        	GOAL_TOPICS.add(new Topic(topic_Id, topic_Title, 
		        			topic_DateCreation, topic_StateId, 
		        			topic_CreatorUsername ,topic_Parent_topic_id));
		        }
		        return GOAL_TOPICS;
	    	} else {
	    		return null;
	    	}
	    } catch(SQLException e) {
	    	System.out.println("SERVER STATUS: --ERROR-- OCCURED IN THE searchTwoWord");
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
	
	private ArrayList<String> splitStrings(String str) {
		String[] words = str.split("\\s+");

		ArrayList<String> wordList = new ArrayList<>();
		Collections.addAll(wordList, words);

		return wordList;
	}
	
	private ArrayList<Topic> searchOneWord(String keyPhrase) {
		ArrayList<Topic> GOAL_TOPICS = new ArrayList<>();
		
		String url = "jdbc:mysql://localhost:3306/news_db";
	    String username_DB = "root";
	    String passwd = "kolos2020";
	    
	    String selectQuery;
	    Connection connection = null;
	    PreparedStatement selectStatement = null;
	    ResultSet resultSet = null;
	    
	    try {
	    	
	    	connection = DriverManager.getConnection(url, username_DB, passwd);
    		System.out.println("\nSERVER STATUS: Connected to the database...");
    		
	    	selectQuery = "SELECT ID, TITLE, DATE_CREATION, "
    	    		+ "STATE_ID, CREATOR_USERNAME, "
    	    		+ "PARENT_TOPIC_ID "
    	    		+ "FROM news_db.topic "
    	    		+ "WHERE STATE_ID = 3 AND TITLE LIKE ?;";
    	    
    	    selectStatement = connection.prepareStatement(selectQuery);
	    	selectStatement.setString(1, "%" + keyPhrase + "%");
	    	resultSet = selectStatement.executeQuery();
	    	
	    	if(resultSet != null) {
		        while(resultSet.next()) {
		        	int topic_Id = resultSet.getInt("ID");
		        	String topic_Title = resultSet.getString("TITLE");
		        	Date topic_DateCreation = resultSet.getDate("DATE_CREATION");
		        	int topic_StateId = resultSet.getInt("STATE_ID");
		        	String topic_CreatorUsername = resultSet.getString("CREATOR_USERNAME");
		        	int topic_Parent_topic_id = resultSet.getInt("PARENT_TOPIC_ID");
		        	GOAL_TOPICS.add(new Topic(topic_Id, topic_Title, 
		        			topic_DateCreation, topic_StateId, 
		        			topic_CreatorUsername ,topic_Parent_topic_id));
		        }
		        return GOAL_TOPICS;
	    	} else {
	    		return null;
	    	}
	    } catch(SQLException e) {
	    	System.out.println("SERVER STATUS: --ERROR-- OCCURED IN THE searchOneWord");
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
	
	/* NOTE: This func checks if the a string has one or more words */ 
	private static boolean hasWords(String input) {
		String [] array = input.trim().split(" ");
		if(array.length == 1){
			return false; // it has only one word
		} else{
			return true; // it has more than one word
		}
	}

	
}
