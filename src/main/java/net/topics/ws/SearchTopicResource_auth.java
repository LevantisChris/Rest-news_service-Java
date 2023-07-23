package net.topics.ws;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import net.exceptions.ws.NotIdentifiedRole;
import net.htmlhandler.ws.HtmlHandler;
import net.topics.ws.manage_topics.Topic;

@Path("/auth/auth_user/search_topic")
public class SearchTopicResource_auth {

	@GET
	public Response handleKeyPhrasesAuthUserArticles(@QueryParam("username") String username, @QueryParam("role") String role) {
		System.out.println("SERVER STATUS: SEARCH TOPIC (auth_user) CALLED BY USERNAME == " + username + " - ROLE == " + role);
		if(role == null || role.isEmpty()) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		int ROLE_ID;
		try {
			if(role.equals("VISITOR")) { // if a visitor gets here we have a problem ...
				ROLE_ID = 1;
				return Response.status(Response.Status.NOT_ACCEPTABLE).entity("ROLE_NOT_IDENTIFIED").build();
			} else if(role.equals("JOURNALIST")) {
				System.out.println("SERVER STATUS: ROLE: --JOURNALIST-- IN THE SEARCH TOPIC");
				ROLE_ID = 2;
				
				return Response.status(Response.Status.OK)
                .entity(HtmlHandler.getSEARCH_TOPIC_KEY_PHRASES_HTML(username, role, ROLE_ID))
                .type(MediaType.TEXT_HTML)
                .build();
				
			} else if(role.equals("CURATOR")){
				System.out.println("SERVER STATUS: ROLE: --CURATOR-- IN THE SEARCH TOPIC");
				ROLE_ID = 3;
				
				return Response.status(Response.Status.OK)
		                .entity(HtmlHandler.getSEARCH_TOPIC_KEY_PHRASES_HTML(username, role, ROLE_ID))
		                .type(MediaType.TEXT_HTML)
		                .build();
				
			} else { throw new NotIdentifiedRole("ERROR: The role could not be identified.");}
		} catch(NotIdentifiedRole e) {
			System.out.print(e.getMessage());
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity("ROLE_NOT_IDENTIFIED").build();
		}
	}
	
	@GET
	@Path("/search")
	public Response sendData(@QueryParam("username") String username,
	                         @QueryParam("role") String role,
	                         @QueryParam("titleKeyPhrases") String titleKeyPhrases) {
	    System.out.println("SERVER STATUS: In Search Topic (in sendDate) username --> " + username);
	    System.out.println("SERVER STATUS: In Search Topic (in sendDate) role --> " + role);
	    System.out.println("SERVER STATUS: In Search Topic (in sendDate) titleKeyPhrases --> " + titleKeyPhrases);

	    if(username == null || role == null || titleKeyPhrases == null) { // If something is null return error
	    	return Response.serverError().build();
	    } else if(titleKeyPhrases.isEmpty()) { // if the key is empty we can not proceed
			return Response.status(Response.Status.NOT_FOUND).entity("KEY_IS_EMPTY").build();
	    } else { 
	    	if(hasWords(titleKeyPhrases) == false) {
	    		ArrayList<Topic> GOAL_TOPICS = searchOneWord(username, role, titleKeyPhrases);
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

	
	/*---------------------------------------------------------------------------------------------------------------------------------------*/
	
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
	    	
	    	if(role.equals("JOURNALIST")) {
	    	    selectQuery = "SELECT ID, TITLE, DATE_CREATION, "
		    	    		+ "STATE_ID, CREATOR_USERNAME, "
		    	    		+ "PARENT_TOPIC_ID "
		    	    		+ "FROM news_db.topic "
		    	    		+ "WHERE (STATE_ID = 3 OR CREATOR_USERNAME = ?)";
	    	    
	    	    for(int i = 0;i < titleKeyPhrasesArray.size();i++) {
	    	    	selectQuery = selectQuery + " AND TITLE LIKE ?";
	    	    }
	    	    
	    	    selectStatement = connection.prepareStatement(selectQuery);
		    	selectStatement.setString(1, username);
		    	for(int i = 0;i < titleKeyPhrasesArray.size();i++) {
		    		selectStatement.setString(i + 2, "%" + titleKeyPhrasesArray.get(i) + "%");
		    	}
		    	resultSet = selectStatement.executeQuery();
	    	} else if(role.equals("CURATOR")) {
	    		selectQuery = "SELECT ID, TITLE, DATE_CREATION, "
	    	    		+ "STATE_ID, CREATOR_USERNAME, "
	    	    		+ "PARENT_TOPIC_ID "
	    	    		+ "FROM news_db.topic "
	    	    		+ "WHERE "; // The curator can see everything
	    		selectQuery = selectQuery + " TITLE LIKE ?";
	    	    for(int i = 1;i < titleKeyPhrasesArray.size();i++) {
	    	    	selectQuery = selectQuery + " AND TITLE LIKE ?";
	    	    }
	    		
	    	    selectStatement = connection.prepareStatement(selectQuery);
	    	    for(int i = 0;i < titleKeyPhrasesArray.size();i++) {
	    	    	selectStatement.setString(i + 1, "%" + titleKeyPhrasesArray.get(i) + "%");
	    	    }
		    	resultSet = selectStatement.executeQuery();
	    	} else {
	    		return null;
	    	}
	    	
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
	
	private ArrayList<Topic> searchOneWord(String username, String role, String keyPhrase) {
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
	    	
	    	if(role.equals("JOURNALIST")) {
	    	    selectQuery = "SELECT ID, TITLE, DATE_CREATION, "
	    	    		+ "STATE_ID, CREATOR_USERNAME, "
	    	    		+ "PARENT_TOPIC_ID "
	    	    		+ "FROM news_db.topic "
	    	    		+ "WHERE (STATE_ID = 3 OR CREATOR_USERNAME = ?) AND TITLE LIKE ?;";
	    	    
	    	    selectStatement = connection.prepareStatement(selectQuery);
		    	selectStatement.setString(1, username);
		    	selectStatement.setString(2, "%" + keyPhrase + "%");
		    	resultSet = selectStatement.executeQuery();
	    	} else if(role.equals("CURATOR")) {
	    		selectQuery = "SELECT ID, TITLE, DATE_CREATION, "
	    	    		+ "STATE_ID, CREATOR_USERNAME, "
	    	    		+ "PARENT_TOPIC_ID "
	    	    		+ "FROM news_db.topic "
	    	    		+ "WHERE TITLE LIKE ?;"; // The curator can see everything
	    	    
	    	    selectStatement = connection.prepareStatement(selectQuery);
		    	selectStatement.setString(1, "%" + keyPhrase + "%");
		    	resultSet = selectStatement.executeQuery();
	    	} else {
	    		return null;
	    	}
	    	
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
