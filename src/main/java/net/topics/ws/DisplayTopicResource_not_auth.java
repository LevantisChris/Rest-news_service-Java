package net.topics.ws;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.CookieParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import net.exceptions.ws.NotIdentifiedRole;
import net.htmlhandler.ws.HtmlHandler;
import net.sessionExtractor.ws.SessionExtractor;

@Path("/auth/not_auth_user/display_topic")
public class DisplayTopicResource_not_auth {

	@GET
	public Response handleDisplatAllArticles(@CookieParam("session_id") String sessionId) {
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
		
		System.out.println("SERVER STATUS --> DISPLAY TOPIC (not auth) CALLED BY USERNAME == " + username + " - ROLE == " + role);
		if(role == null || role.isEmpty()) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		try {
			if(role.equals("VISITOR")) {

				ArrayList<String> TOPICS_IDs = getAllTopicsIDS(username);
				
				return Response.status(Response.Status.OK)
		                .entity(HtmlHandler.getIDS_DISPLAY_TOPIC_HTML(TOPICS_IDs, username, role))
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
    public Response handlegetTopic(@CookieParam("session_id") String sessionId, @PathParam("id") String id) {
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
		if(!role.equals("VISITOR")) {
			return Response.status(Response.Status.UNAUTHORIZED).entity("YOU_DONT_HAVE_PERMISSION").build();
		}
		System.out.println("SERVER STATUS: SESSION_ID NUM: " + sessionId +" USERNAME extracted is --> " + username + " and ROLE extracted is " + role);
		///
		///
		/* Check if the article can be viewed by the user of the session */
		///The article must be in the state 2 to be viewed
		if(sessionExtractor.checkIfTopicCanBeViewed(sessionId, id, 3) == false) {
			return Response.status(Response.Status.NOT_ACCEPTABLE).build();
		}
		///
		
		System.out.println("SERVER STATUS: ACTION IN APPROVED TOPIC: ID//" + id + "//");
		if(id == null || id.isEmpty()) {
			Response.status(Response.Status.NOT_FOUND)
    		.entity("SELECT_ID")
    		.build();
		}
		
		/* We do that only because we want to ensure that are going good */
		String ID_FROM_DB = getIdTopic_DB(id);
		if(ID_FROM_DB == null) {
			return Response.serverError().build();
		}
		
		String TITLE_FROM_DB = getTitleTopic_DB(id);
		if(TITLE_FROM_DB == null) {
			return Response.serverError().build();
		}
		String PARENT_TOPIC_FROM_DB = getParentTopicTopic_DB(id);
		if(PARENT_TOPIC_FROM_DB == null) {
			PARENT_TOPIC_FROM_DB = "Empty";
		}
		
		/* NOTE: Here we get the titles of the kids topics the topic has, that means we have to 
		 * find where the specific topic is PARENT. */
		ArrayList<String> KIDS_TOPICS_FROM_DB = getKidsTopic_DB(id);

		return   Response.status(Response.Status.OK)
                .entity(HtmlHandler.getDISPLAY_TOPIC_HTML(Integer.parseInt(id), TITLE_FROM_DB, PARENT_TOPIC_FROM_DB, KIDS_TOPICS_FROM_DB))
                .type(MediaType.TEXT_HTML)
                .build();
    }
	
	/*--------------------------------------------------------------------------------------------------------------------*/
	
	private String getIdTopic_DB(String id) {
		
		String url = "jdbc:mysql://localhost:3306/news_db";
	    String username_DB = "root";
	    String passwd = "kolos2020";
	    
	    Connection connection = null;
	    PreparedStatement selectStatement = null;
	    
	    String ID_FROM_DB = null;
	    
	    String selectQuery = "SELECT ID FROM news_db.topic WHERE ID = ?;";
	    
	    try {
	    	connection = DriverManager.getConnection(url, username_DB, passwd);
	        System.out.println("\nSERVER STATUS: Connected to the database...");
		    
	        selectStatement = connection.prepareStatement(selectQuery);
	        selectStatement.setString(1, id);
	        ResultSet resultSet = selectStatement.executeQuery();

	        while(resultSet.next()) {
	        	ID_FROM_DB = resultSet.getString("ID");
	        }
		    return ID_FROM_DB;
	    } catch(SQLException e) {
	    	e.printStackTrace();
	    	return ID_FROM_DB;
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
	private ArrayList<String> getKidsTopic_DB(String id) {
		
		String url = "jdbc:mysql://localhost:3306/news_db";
	    String username_DB = "root";
	    String passwd = "kolos2020";
	    
	    Connection connection = null;
	    PreparedStatement selectStatement = null;
	    ResultSet resultSet;
	    
	    ArrayList<String> KIDS_TOPICS_FROM_DB = new ArrayList<>();
	    
	    String selectQuery;
	    
	    try {
	    	connection = DriverManager.getConnection(url, username_DB, passwd);
	        System.out.println("\nSERVER STATUS: Connected to the database...");
		    selectQuery = "SELECT TITLE FROM news_db.topic where PARENT_TOPIC_ID = ? AND STATE_ID = 3;"; // THE KID MUST BE APPROVED TO BE DISPLAYED
	    	selectStatement = connection.prepareStatement(selectQuery);
	    	selectStatement.setString(1, id);
	    	resultSet = selectStatement.executeQuery();
	    	
	    	while(resultSet.next()) {
	        	String articleId = resultSet.getString("TITLE");
	        	KIDS_TOPICS_FROM_DB.add(articleId);
	        }
	    	return KIDS_TOPICS_FROM_DB;
	    } catch(SQLException e) {
	    	System.out.println("SERVER STATUS: --ERROR-- IN THE getKidsTopic_DB in DISPLAY TOPIC");
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
	/* Get the parent topic of an ID returns the TITLE) */
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
	    
	    String selectQuery;
	    Connection connection = null;
	    PreparedStatement selectStatement = null;
	    
	    ResultSet resultSet;
	   	    
	    ArrayList<String> TOPICS_IDs = new ArrayList<>();
	    
	    try {
	    	connection = DriverManager.getConnection(url, username_DB, passwd);
	        System.out.println("\nSERVER STATUS: Connected to the database...");
		    
	        selectQuery = "SELECT ID FROM news_db.topic WHERE STATE_ID = 3;";
	    	selectStatement = connection.prepareStatement(selectQuery);
	    	resultSet = selectStatement.executeQuery();

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
	                System.out.println("SERVER STATUS: Disconnected from the database...\n");
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}
}
