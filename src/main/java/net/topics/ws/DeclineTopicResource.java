package net.topics.ws;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.CookieParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import net.exceptions.ws.NotIdentifiedRole;
import net.htmlhandler.ws.HtmlHandler;
import net.sessionExtractor.ws.SessionExtractor;

@Path("/auth/auth_user/decline_topic")
public class DeclineTopicResource {

	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	public Response handleDisplayAllArticles(@CookieParam("session_id") String sessionId) {
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
		
		System.out.println("SERVER STATUS --> DECLINE TOPIC CALLED BY USERNAME == " + username + " - ROLE == " + role);
		if(role == null || role.isEmpty()) {
			return Response.serverError().build();
		}
		String ID_CLICKED = null;
		int ROLE_ID;
		try {
			if(role.equals("JOURNALIST")) {
				ROLE_ID = 2;
				
				return Response.serverError().build(); 
				
			} else if(role.equals("CURATOR")){
				ROLE_ID = 3;
				
				ArrayList<String> TOPIC_IDs = getAllTopicsIDS(username);
				
				return Response.status(Response.Status.OK)
		                .entity(HtmlHandler.getIDS_DECLINE_TOPIC_HTML(TOPIC_IDs, username ,role))
		                .type(MediaType.TEXT_HTML)
		                .build();
				
			} else { throw new NotIdentifiedRole("ERROR: The role could not be identified.");}
		} catch(NotIdentifiedRole e) {
			System.out.print(e.getMessage());
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity("ROLE_NOT_IDENTIFIED").build();
		}
	}
	
	@GET
    @Path("/{id}")
	@Consumes(MediaType.TEXT_PLAIN)
    public Response handlegetTopic(@CookieParam("session_id") String sessionId, 
    							   @PathParam("id") String id) {
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
		if(!role.equals("CURATOR")) {
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity("YOU_DONT_HAVE_PERMISSION").build();
		}
		System.out.println("SERVER STATUS: SESSION_ID NUM: " + sessionId +" USERNAME extracted is --> " + username + " and ROLE extracted is " + role);
		///
		///
		/* Check if the topic can be viewed by the user of the session */
		/// The topic must be in the state 1 to be viewed
		if(sessionExtractor.checkIfTopicCanBeViewed(sessionId, id, 1) == false) {
			return Response.status(Response.Status.NOT_ACCEPTABLE).build();
		}
		///
		
		System.out.println("SERVER STATUS: ACTION IN DECLINE TOPIC: ID//" + id + "//");
		if(id == null || id.isEmpty() || id.isBlank()) {
			Response.status(Response.Status.NOT_FOUND)
    		.entity("SELECT_ID")
    		.build();
		}
		
		String TITLE_FROM_DB = getTitleTopic_DB(id);
		if(TITLE_FROM_DB == null) {
			return Response.serverError().build();
		}
		String PARENT_TOPIC_FROM_DB = getParentTopicTopic_DB(id);
		if(PARENT_TOPIC_FROM_DB == null) {
			System.out.println("EINAI NULL ...");
			PARENT_TOPIC_FROM_DB = "Empty";
		}
		
		//ArrayList<String> TOPICS_LIST = takeTheAvailableTopics(); 

		return   Response.status(Response.Status.OK)
                .entity(HtmlHandler.getDECLINE_TOPIC_HTML(TITLE_FROM_DB, PARENT_TOPIC_FROM_DB, Integer.parseInt(id)))
                .type(MediaType.TEXT_HTML)
                .build();
    }
	
	@POST
	@Path("/decline")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response handleModificationOfTopic(@CookieParam("session_id") String sessionId, String JSON) {
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
		if(!role.equals("CURATOR")) {
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity("YOU_DONT_HAVE_PERMISSION").build();
		}
		System.out.println("SERVER STATUS: SESSION_ID NUM: " + sessionId +" USERNAME extracted is --> " + username + " and ROLE extracted is " + role);
		///
		
		System.out.println("SERVER STATUS: JSON FOR MODIFICATION IS: " + JSON);
		
		String title = null;
		String parentTopic = null;
		Long topic_id_clicked = null;
		
		try {
	    	JSONObject jsonObjectDecode = (JSONObject) JSONValue.parse(JSON);
	    	title = (String) jsonObjectDecode.get("title");
	    	parentTopic = (String) jsonObjectDecode.get("parentTopic");
	    	topic_id_clicked = (Long) jsonObjectDecode.get("topic_id_clicked");
	    	
	    	System.out.println("-------------------------------------------------------------------");
			System.out.println("SERVER STATUS: STRINGs EXTRACTED ARE "
					+ "\nTitle == " + title
					+ "\nParent topic == " + parentTopic
					+ "\nTopic ID clicked == " + topic_id_clicked);
	    	System.out.println("-------------------------------------------------------------------");

	        
			String MSG = deleteTopic(Integer.parseInt(topic_id_clicked.toString()));
			return Response.ok(MSG).build();
			
	    } catch (Exception e) {
	        System.out.println("SERVER STATUS: --ERROR-- in parsing JSON");
	        e.printStackTrace();
	        return Response.serverError().build();
	    }
		
	}
	
	/*------------------------------------------------------------------------------------------------------------------*/
	
	private String deleteTopic(int topic_id_clicked) {
		
		String url = "jdbc:mysql://localhost:3306/news_db";
	    String username_DB = "root";
	    String passwd = "kolos2020";
	    
	    Connection connection = null;
	    PreparedStatement deleteStatement = null;
	    
	    String deleteQuery = "DELETE FROM news_db.topic WHERE ID = ?;";
	    
	    try {
	    	
	    	connection = DriverManager.getConnection(url, username_DB, passwd);
			System.out.println("\nSERVER STATUS: Connected to the database...");
			
			deleteStatement = connection.prepareStatement(deleteQuery);
			deleteStatement.setInt(1, topic_id_clicked);
			
			int rowsAffected = deleteStatement.executeUpdate();
	    	
	    	if(rowsAffected > 0) {
	    		return "DELETE_DONE_SUCCESFULLY";
	    	} else {
	    		return "ERROR_IN_DELETE";
	    	}
	    	
	    } catch(SQLException e) {
	    	System.out.println("SERVER STATUS: --ERROR-- IN THE DECLINED/DELETION OF THE ARTICLE");
	    	e.printStackTrace();
	    	return "ERROR_IN_APPROVE";
	    }
		
	}
	
	private String getTitleTopic_DB(String id) {
		String url = "jdbc:mysql://localhost:3306/news_db";
	    String username_DB = "root";
	    String passwd = "kolos2020";
	    
	    Connection connection = null;
	    PreparedStatement selectStatement = null;
	    
	    String TITLE_FROM_DB = null;
	    
	    String selectQuery = "SELECT TITLE FROM news_db.topic WHERE ID = ?;";
	    
	    try {
	    	connection = DriverManager.getConnection(url, username_DB, passwd);
	        System.out.println("\nSERVER STATUS: Connected to the database...");
		    
	        selectStatement = connection.prepareStatement(selectQuery);
	        selectStatement.setString(1, id);
	        ResultSet resultSet = selectStatement.executeQuery();

	        while(resultSet.next()) {
	        	TITLE_FROM_DB = resultSet.getString("TITLE");
	        }
		    return TITLE_FROM_DB;
	    } catch(SQLException e) {
	    	e.printStackTrace();
	    	return TITLE_FROM_DB;
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
	private String getParentTopicTopic_DB(String id) {
		String url = "jdbc:mysql://localhost:3306/news_db";
	    String username_DB = "root";
	    String passwd = "kolos2020";
	    
	    Connection connection = null;
	    PreparedStatement selectStatement = null;
	    
	    String ID_PARENT_TOPIC_FROM_DB = null;
	    String PARENT_TOPIC_FROM_DB = null;
	    
	    String selectQuery = "SELECT PARENT_TOPIC_ID FROM news_db.topic WHERE ID = ?;";
	    
	    try {
	    	connection = DriverManager.getConnection(url, username_DB, passwd);
	        System.out.println("\nSERVER STATUS: Connected to the database...");
		    
	        selectStatement = connection.prepareStatement(selectQuery);
	        selectStatement.setString(1, id);
	        ResultSet resultSet = selectStatement.executeQuery();

	        while(resultSet.next()) {
	        	ID_PARENT_TOPIC_FROM_DB = resultSet.getString("PARENT_TOPIC_ID");
	        }
	        
	        if(ID_PARENT_TOPIC_FROM_DB != null) {
	        	String selectQuery2 = "SELECT TITLE FROM news_db.topic WHERE ID = ?;";
	        	
	        	Connection connection2 = null;
	    	    PreparedStatement selectStatement2 = null;
	        	
	        	try {
	        		
	        		connection2 = DriverManager.getConnection(url, username_DB, passwd);
	    	        System.out.println("\nSERVER STATUS: Connected to the database...");
	    		    
	    	        selectStatement2 = connection.prepareStatement(selectQuery2);
	    	        selectStatement2.setString(1, ID_PARENT_TOPIC_FROM_DB);
	    	        ResultSet resultSet2 = selectStatement2.executeQuery();

	    	        while(resultSet2.next()) {
	    	        	PARENT_TOPIC_FROM_DB = resultSet2.getString("TITLE");
	    	        }
	        		
	        	} catch(SQLException e) {
	        		System.out.println("SERVER STATUS: --ERROR-- occured in second select in getParentTopicTopic_DB");
	        		e.printStackTrace();
	        	} finally {
	    	        try {
	    	            if (selectStatement2 != null) {
	    	                selectStatement.close();
	    	            }
	    	            if (connection2 != null && !connection2.isClosed()) {
	    	                connection2.close();
	    	                System.out.println("Disconnected from the database...\n");
	    	            }
	    	        } catch (SQLException e) {
	    	            e.printStackTrace();
	    	        }
	    	    }
	        }
	        
		    return PARENT_TOPIC_FROM_DB;
	    } catch(SQLException e) {
	    	e.printStackTrace();
	    	return PARENT_TOPIC_FROM_DB;
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
	
	private ArrayList<String> getAllTopicsIDS(String username) {
		String url = "jdbc:mysql://localhost:3306/news_db";
	    String username_DB = "root";
	    String passwd = "kolos2020";
	    
	    Connection connection = null;
	    PreparedStatement selectStatement = null;
	    
	    String selectQuery = "SELECT ID FROM news_db.topic WHERE STATE_ID = 1;";
	    
	    ArrayList<String> TOPICS_IDs = new ArrayList<>();
	    
	    try {
	    	connection = DriverManager.getConnection(url, username_DB, passwd);
	        System.out.println("\nSERVER STATUS: Connected to the database...");
		    
	        selectStatement = connection.prepareStatement(selectQuery);
	        ResultSet resultSet = selectStatement.executeQuery();

	        while(resultSet.next()) {
	        	int articleId = resultSet.getInt("ID");
	        	TOPICS_IDs.add(String.valueOf(articleId));
	        }
		    return TOPICS_IDs;
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
}
