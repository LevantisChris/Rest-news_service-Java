package net.articles.ws;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Form;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import net.exceptions.ws.EmptyFields;
import net.exceptions.ws.NotIdentifiedRole;
import net.htmlhandler.ws.HtmlHandler;

@Path("/auth/auth_user/create_article")
public class CreateArticleResource {
	
	/// NOTE: This function only a JOURNALIST and a CURATOR can call it ...
	@GET
	public Response handleFormCreation(@QueryParam("username") String username, @QueryParam("role") String role) {
		
		System.out.println("SERVER STATUS --> CREATE ARTICLE CALLED BY USERNAME == " + username + " - ROLE == " + role);
		int ROLE_ID;
			if(role.equals("JOURNALIST")) {
				ROLE_ID = 2;
				
				ArrayList<String> TOPICS_LIST = takeTheAvailableTopics(); 
				
				return Response.status(Response.Status.OK)
                .entity(HtmlHandler.getCREATE_ARTICLE_HTML(TOPICS_LIST, username, role))
                .type(MediaType.TEXT_HTML)
                .build();
				
			} else if(role.equals("CURATOR")){
				ROLE_ID = 3;
				
				ArrayList<String> TOPICS_LIST = takeTheAvailableTopics();
				
				return Response.status(Response.Status.OK)
                .entity(HtmlHandler.getCREATE_ARTICLE_HTML(TOPICS_LIST, username, role))
                .type(MediaType.TEXT_HTML)
                .build();
				
			} else { 
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
		    			.entity("ROLE_NOT_IDENTIFIED")
		    		    .type(MediaType.TEXT_HTML)
		                .build();
			} 
	}
	
	/// NOTE: WE USE POST BECAUSE WE WANT TO CREATE A NEW RESOURCE (AN ARTICLE)
	/// ALSO THE RESOURCE DOES NOT EXIST ...
	@Path("/create")
	@POST
	@Produces(MediaType.TEXT_HTML)
    public Response handleFormSubmission(@FormParam("username") String username, @FormParam("topic") String topic, @FormParam("title") String title, @FormParam("content") String content) {
		System.out.println("-------------------------------------");
        System.out.println("THE ARTICLE THAT SUBMITED FROM THE USER //" + username + "// IS --> ");
		System.out.println("TITLE --> " + title);
        System.out.println("CONTENT --> " + content);
        System.out.println("TOPIC --> " + topic);
        System.out.println("-------------------------------------");
        
        if(username == null || title == null || content == null || topic == null) {
        	System.out.println("SERVER ERROR: CREATION --CANCELED--");
        	return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        
        ///We see if the fields are empty if one is empty then we send back an error ...
	    if(username.isEmpty() || title.isEmpty() || content.isEmpty() || topic.isEmpty()) {
	    	System.out.println("SERVER ERROR: CREATION CAN NOT CONTINUE FURTHER (EMPTY FIELDS) ...");
	    	 return Response.status(Response.Status.NOT_FOUND)
	    			.entity("EMPTY_FIELDS")
	    		    .type(MediaType.TEXT_HTML)
	                .build();
	    } else {
	    	String MSG = addInTheDB(username, title, content, topic);
	    	System.out.println("SERVER STATUS: FINAL MSG IN CREATE TOPIC --( " + MSG + " )--");
	    	if(MSG.equals("THE_CREATION_OF_THE_ARTICLE_DONE")) {
	    		return Response.ok(MSG).build();
	    	} else if (MSG.equals("TOPIC_DOES_NOT_EXIST")) {
	    		return Response.status(Response.Status.NOT_FOUND).entity(MSG).build();
	    	} else {
	    		return Response.serverError().build();
	    	}
	    } 
    }
	
	/// NOTE: in this function, firstly we will execute the select statement to see if the 
	/// TOPIC (title) exists, if the title does not exists we will return false. We understand this by see if the ResultSet
	/// is empty or not.
	/// if the title exists we can execute the insertion and insert in the topic_id field the output of the select query.
	private String addInTheDB(String username, String title, String content, String topic) {
	    String url = "jdbc:mysql://localhost:3306/news_db";
	    String username_DB = "root";
	    String passwd = "kolos2020";
	    Connection connection = null;
	    PreparedStatement selectStatement = null;
	    PreparedStatement insertStatement = null;

	    String selectQuery = "SELECT ID FROM TOPIC WHERE TITLE = ?";
	    String insertQuery = "INSERT INTO ARTICLES (TITLE, CONTENT, DATE_CREATION, TOPIC_ID, STATE_ID, CREATOR_USERNAME) " +
	            "VALUES (?, ?, ?, ?, ?, ?)";

	    try {
	        connection = DriverManager.getConnection(url, username_DB, passwd);
	        System.out.println("\nSERVER STATUS: Connected to the database...");
	        
	        selectStatement = connection.prepareStatement(selectQuery);
	        selectStatement.setString(1, topic);
	        ResultSet resultSet = selectStatement.executeQuery();

	        if (!resultSet.next()) {
	            System.out.println("SERVER STATUS: --ERROR-- Topic does not exist, the creation failed");
	            return "TOPIC_DOES_NOT_EXIST";
	        }

	        int topicId = resultSet.getInt("ID"); // the ID that we take from the select query ...
	        insertStatement = connection.prepareStatement(insertQuery);
	        insertStatement.setString(1, title);
	        insertStatement.setString(2, content);
	        insertStatement.setString(3, LocalDate.now().toString());
	        insertStatement.setInt(4, topicId);
	        insertStatement.setInt(5, 1);
	        insertStatement.setString(6, username);

	        int rowsAffected = insertStatement.executeUpdate();

	        if (rowsAffected > 0) {
	            System.out.println("SERVER STATUS: Insert successful for the article");
	            return "THE_CREATION_OF_THE_ARTICLE_DONE";
	        } else {
	            System.out.println("SERVER STATUS: --ERROR-- Insert failed for the article");
	            return "ERROR_IN_INSERTION";
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        System.out.println("SERVER STATUS: --ERROR-- Insert failed for the article");
	        return "FATAL_ERROR_IN_CREATE_TOPIC";
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

	
	/// NOTE: In this code we will get the available topics that has been created in the past.
	/// We will display them in the html code that has to do with the creation of an article.
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
