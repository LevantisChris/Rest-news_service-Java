package net.topics.ws;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import net.htmlhandler.ws.HtmlHandler;

/// Only curators and Journalist can use this function

@Path("/auth/auth_user/create_topic")
public class CreateTopicResource {

	@GET
	public Response handleFormCreation(@QueryParam("username") String username, @QueryParam("role") String role) {
		System.out.println("SERVER STATUS --> CREATE TOPIC CALLED BY USERNAME == " + username + " - ROLE == " + role);
		
		if(role.equals("JOURNALIST") || role.equals("CURATOR")) {
			ArrayList<String> TOPICS_LIST = takeTheAvailableTopics(); 
			return Response.status(Response.Status.UNAUTHORIZED)
	                .entity(HtmlHandler.getCREATE_TOPIC_HTML(TOPICS_LIST, username))
	                .type(MediaType.TEXT_HTML)
	                .build();
		} else {
			return Response.serverError().build();
		}
	}
	
	@Path("/create")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
    public Response handleFormSubmission(String JSON) {
		
		String title = null;
		String creator_username = null;
		String parent_topic_STRING = null;
		
		try {
	        
	    	JSONObject jsonObjectDecode = (JSONObject) JSONValue.parse(JSON);
	    	creator_username = (String) jsonObjectDecode.get("username");
	    	title = (String) jsonObjectDecode.get("title");
	    	parent_topic_STRING = (String) jsonObjectDecode.get("parentTopic");
	        
			System.out.println("SERVER STATUS: STRINGs EXTRACTED ARE "
					+ "\nTitle == " + title
					+ "\nCreator Username == " + creator_username
					+ "\nParent topic String == " + parent_topic_STRING);
	        
			if(!topicExists(title) == true) {
				return Response.ok("TOPIC_ALREADY_EXISTS").build();
			}
			
			if(crateTopic(title, creator_username, parent_topic_STRING) == true) {
				System.out.println("-------------------------------------");
				System.out.println("SERVER STATUS: TOPIC CREATED IS: "
						+ "\nTitle == " + title
						+ "\nCreator Username == " + creator_username
						+ "\nParent topic String == " + parent_topic_STRING);
				System.out.println("-------------------------------------");
				return Response.ok("CREATION_DONE_SUCCESFULLY").build();
			} else {
				return Response.serverError().build();
			}
	    } catch (Exception e) {
	        System.out.println("SERVER STATUS: --ERROR-- in parsing JSON");
	        e.printStackTrace();
	        return Response.serverError().build();
	    }
    }
	
	/*--------------------------------------------------------------------------------------------------------------*/
	
	/* NOTE: Check if the topic that the user want to create already exists 
	 * 
	 * SOSOSOS!!!! IN THE CREATION OF THE DATABASE I HAVE ALREADY ADD IN THE TITLE ATTRIBUTE THE CHARACTERISTIC --UNIQUE-- 
	 * But with this function we double check it and handle it better */
	private boolean topicExists(String title) {
		String url = "jdbc:mysql://localhost:3306/news_db";
	    String username_DB = "root";
	    String passwd = "kolos2020";
	    
	    Connection connection = null;
	    PreparedStatement selectStatement = null;
	    PreparedStatement insertStatement = null;
	    
	    String selectQuery = "SELECT TITLE FROM TOPIC WHERE TITLE = ?";
	    
	    try {
	    	
	    	connection = DriverManager.getConnection(url, username_DB, passwd);
	        System.out.println("\nSERVER STATUS: Connected to the database...");
	        
	        selectStatement = connection.prepareStatement(selectQuery);
	        selectStatement.setString(1, title);
	        ResultSet resultSet = selectStatement.executeQuery();
	        
	        if (resultSet.next()) {
	            System.out.println("SERVER STATUS: --ERROR-- The topic already exists");
	            return false;
	        } else {
	        	return true;
	        }
	    } catch(SQLException e) {
	    	System.out.println("SERVER STATUS: --ERROR-- in the check f the topic already exists");
	    	e.printStackTrace();
	    	return false;
	    } finally {
	        try {
	            if (selectStatement != null) {
	                selectStatement.close();
	            }
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
	}
	
	private boolean crateTopic(String title, String creator_username, String parent_topic_STRING) {
		String url = "jdbc:mysql://localhost:3306/news_db";
	    String username_DB = "root";
	    String passwd = "kolos2020";
	    
	    Connection connection = null;
	    PreparedStatement selectStatement = null;
	    PreparedStatement insertStatement = null;
	    
	    String selectQuery = "SELECT ID FROM TOPIC WHERE TITLE = ?";
	    String insertQuery = null;
	    if(!parent_topic_STRING.equals("Empty")) {
		    insertQuery = "INSERT INTO news_db.topic (TITLE, DATE_CREATION, STATE_ID, CREATOR_USERNAME, PARENT_TOPIC_ID) " +
		            "VALUES (?, ?, ?, ?, ?)"; 
	    } else {
	    	insertQuery = "INSERT INTO news_db.topic (TITLE, DATE_CREATION, STATE_ID, CREATOR_USERNAME) " +
		            "VALUES (?, ?, ?, ?)"; 
	    }
	    
	    try {
	    	
	    	connection = DriverManager.getConnection(url, username_DB, passwd);
	        System.out.println("\nSERVER STATUS: Connected to the database...");
	    	
	        int topicId;
	        if(parent_topic_STRING.equals("Empty")) { /// the user has not add any parent topic (2 query)
		        insertStatement = connection.prepareStatement(insertQuery);
		        insertStatement.setString(1, title);
		        insertStatement.setString(2, LocalDate.now().toString());
		        insertStatement.setInt(3, 1);
		        insertStatement.setString(4, creator_username);
		        
		        int rowsAffected = insertStatement.executeUpdate();

		        if (rowsAffected > 0) {
		            System.out.println("SERVER STATUS: Insert successful for the article");
		            return true;
		        } else {
		            System.out.println("SERVER STATUS: --ERROR-- (PARENT_TOPIC: NO) Insert failed for the article");
		            return false;
		        }
	        } else if(!parent_topic_STRING.isEmpty()) { /// the user has enter a parent topic (1 query)
	        	
	        	selectStatement = connection.prepareStatement(selectQuery);
		        selectStatement.setString(1, parent_topic_STRING);
		        ResultSet resultSet = selectStatement.executeQuery();
		        
		        if (!resultSet.next()) {
		            System.out.println("SERVER STATUS: --ERROR-- Parent topic does not exist, the creation failed");
		            return false;
		        }
		        topicId = resultSet.getInt("ID");
	        	
	        	insertStatement = connection.prepareStatement(insertQuery);
		        insertStatement.setString(1, title);
		        insertStatement.setString(2, LocalDate.now().toString());
		        insertStatement.setInt(3, 1);
		        insertStatement.setString(4, creator_username);
		        insertStatement.setInt(5, topicId);
		        
		        int rowsAffected = insertStatement.executeUpdate();

		        if (rowsAffected > 0) {
		            System.out.println("SERVER STATUS: Insert successful for the article");
		            return true;
		        } else {
		            System.out.println("SERVER STATUS: --ERROR-- (PARENT_TOPIC: YES) Insert failed for the article");
		            return false;
		        }
	        	
	        } else { /// if we get here an error occured
	        	return false;
	        }
	        
	    } catch(SQLException e) {
	    	System.out.println("SERVER STATUS: --ERROR-- IN createTopic");
	    	e.printStackTrace();
	    	return false;
	    } finally {
	        try {
	            if (selectStatement != null) {
	                selectStatement.close();
	            }
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
	} 
	
	private ArrayList<String> takeTheAvailableTopics() {
	    String url = "jdbc:mysql://localhost:3306/news_db";
	    String username_DB = "root";
	    String passwd = "kolos2020";
	    ArrayList<String> topics = new ArrayList<>();

	    try (Connection connection = DriverManager.getConnection(url, username_DB, passwd);
	         PreparedStatement statement = connection.prepareStatement("SELECT TITLE FROM TOPIC WHERE STATE_ID = 3");
	         ResultSet resultSet = statement.executeQuery()) {

	        while (resultSet.next()) {
	            String topic = resultSet.getString("TITLE");
	            topics.add(topic);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } 
	    return topics;
	}
}
