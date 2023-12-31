package net.articles.ws;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import net.exceptions.ws.NotIdentifiedRole;
import net.htmlhandler.ws.HtmlHandler;
import net.sessionExtractor.ws.SessionExtractor;

///NOTE: The function decline article is only available for the Curator ...
/* NOTE: THE CURATOR CAN SEE ALL THE ARTICLES THAT BELONGS TO EVERY-ONE 
 * 
 * NOTE: We assume that the Curator can decline articles that are in the stage of 2 and 3. The 3 is because he might change his mind ... 
 * 
 * NOTE: In this function the Curator cannot decline the articles that have already being declined*/

@Path("/auth/auth_user/decline_article")
public class DeclineArticleResource {
	
	private static String ID_CLICKED;
	
	@GET
	public Response handleDisplatAllArticles(@CookieParam("session_id") String sessionId) {
		System.out.println("SESSION_ID RECEIVED 1 --> " + sessionId);
		
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
		
		System.out.println("SERVER STATUS --> DECLINE ARTICLE CALLED BY USERNAME == " + username + " - ROLE == " + role);
		ID_CLICKED = null;
		
		
		int ROLE_ID;
		try {
			if(role.equals("JOURNALIST")) {
				ROLE_ID = 2;
				
				ArrayList<String> ARTICLES_IDs = getAllArticleIDS(username);
				
				return Response.serverError().build(); // If for some reason we have a failure in out system and a JOURNALIST or some other access this function, we send a server error ... 
				
			} else if(role.equals("CURATOR")){
				ROLE_ID = 3;
				
				ArrayList<String> ARTICLES_IDs = getAllArticleIDS(username);
				
				return Response.status(Response.Status.OK)
		                .entity(HtmlHandler.getIDS_DECLINE_ARTICLE_HTML(ARTICLES_IDs))
		                .type(MediaType.TEXT_HTML)
		                .build();
				
			} else { throw new NotIdentifiedRole("ROLE_NOT_IDENTIFIED");}
		} catch(NotIdentifiedRole e) {
			System.out.print(e.getMessage());
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity(e.getMessage()).build();
		}
	}
	
	@GET
    @Path("/{id}")
    public Response getArticle(@CookieParam("session_id") String sessionId, @PathParam("id") String id) {
		if(sessionId == null || sessionId.isBlank()) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
		
		if(id == null || id.isEmpty()) {
			return Response.status(Response.Status.NOT_FOUND)
    		.entity("SELECT_ID")
    		.build();
		}
		
		System.out.println("SESSION_ID RECEIVED 1 --> " + sessionId);
		
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
		/* Check if the article can be viewed by the user of the session */
		/// The article must be in the state 2 and 3 to be viewed
		if(sessionExtractor.checkIfArticleCanBeViewed(sessionId, id, 2, 3) == false) { // the curator can decline article that are in the state 2 and also 3
			return Response.status(Response.Status.NOT_ACCEPTABLE).build();
		}
		///
		
		String TITLE_FROM_DB = getTitleArticle_DB(id); 
		if(TITLE_FROM_DB == null) {
			System.out.println("SERVER STATUS: --ERROR-- in the TITLE_FROM_DB in getArticle in the class DeclineArticleResource ");
			return Response.serverError().build(); // if is null that means the query failed to execute, we must not run the other code ...
		}
		String TOPIC_TITLE_FROM_DB = getTopicArticle_DB(id);
		if(TOPIC_TITLE_FROM_DB == null) {
			System.out.println("SERVER STATUS: --ERROR-- in the TOPIC_TITLE_FROM_DB in getArticle in the class DeclineArticleResource ");

			return Response.serverError().build();
		}
		String CONTENTS_FROM_DB = getContents_DB(id);
		if(CONTENTS_FROM_DB == null) {
			System.out.println("SERVER STATUS: --ERROR-- in the CONTENTS_FROM_DB in getArticle in the class DeclineArticleResource ");
			return Response.serverError().build();
		}
		ID_CLICKED = id;
		/// We return him the same html page but filled with the contents of the article he clicked
		return   Response.status(Response.Status.OK)
                .entity(HtmlHandler.getDECLINE_ARTICLE_HTML(username, role, TITLE_FROM_DB, TOPIC_TITLE_FROM_DB, CONTENTS_FROM_DB))
                .type(MediaType.TEXT_HTML)
                .build();
    }
	
	@Path("/decline")
	@PUT
	public Response declineArticle(@CookieParam("session_id") String sessionId, @QueryParam("cause") String cause) {
		System.out.println("SESSION_ID RECEIVED 1 --> " + sessionId);
		
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
		
		System.out.println("SERVER STATUS: ARTICLE THAT CLICKED FOR CHANGING STATE TO --CREATED-- //BECAUSE OF --DECLINED--// IS WITH THE ID == " + ID_CLICKED);
		if(cause == null) {
			return Response.status(Response.Status.NOT_FOUND)
		    		.entity("SELECT_ID")
		    		.build();
		}
		if(cause.isEmpty()) {
			return Response.notModified("ADD_CAUSE_NOW").build();
		}
		if(changeState() == true && setCause(cause) == true) {
	    	return Response.ok("DECLINED_DONE_SUCCESFULLY:ID_MODIFIED:" + ID_CLICKED).build();
	    } else
	    	return Response.serverError().build();
	}
	
	
	
	
	/*-------------------------------------------------------------------------------------------------------------------------------------*/
	private ArrayList<String> getAllArticleIDS(String username) {
		String url = "jdbc:mysql://localhost:3306/news_db";
	    String username_DB = "root";
	    String passwd = "kolos2020";
	    
	    Connection connection = null;
	    PreparedStatement selectStatement = null;
	    
	    String selectQuery = "SELECT ID FROM articles WHERE STATE_ID = 2 OR STATE_ID = 3 AND alert = 0;"; // we don't display the articles that have already being declined ...
	    
	    ArrayList<String> ARTICLES_IDs = new ArrayList<>();
	    
	    try {
	    	connection = DriverManager.getConnection(url, username_DB, passwd);
	        System.out.println("\nSERVER STATUS: Connected to the database...");
		    
	        selectStatement = connection.prepareStatement(selectQuery);
	        ResultSet resultSet = selectStatement.executeQuery();

	        while(resultSet.next()) {
	        	int articleId = resultSet.getInt("ID");
	        	ARTICLES_IDs.add(String.valueOf(articleId));
	        }
		    return ARTICLES_IDs;
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
	private String getTitleArticle_DB(String id) {
		String url = "jdbc:mysql://localhost:3306/news_db";
	    String username_DB = "root";
	    String passwd = "kolos2020";
	    
	    Connection connection = null;
	    PreparedStatement selectStatement = null;
	    
	    String TITLE_FROM_DB = null;
	    
	    String selectQuery = "SELECT TITLE FROM articles WHERE ID = ?;";
	    
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
	private String getTopicArticle_DB(String id) { 
		String url = "jdbc:mysql://localhost:3306/news_db";
	    String username_DB = "root";
	    String passwd = "kolos2020";
	    
	    Connection connection = null;
	    PreparedStatement selectStatement1 = null;
	    PreparedStatement selectStatement2 = null;
	    
	    String TOPIC_ID_DB = null;
	    String TOPIC_TITLE_FROM_DB = null;
	    
	    String selectQuery1 = "SELECT TOPIC_ID FROM articles WHERE ID = ?;";
	    String selectQuery2 = "SELECT TITLE FROM topic WHERE ID = ?;";
	    
	    try {
	    	connection = DriverManager.getConnection(url, username_DB, passwd);
	        System.out.println("\nSERVER STATUS: Connected to the database...");
		    
	        selectStatement1 = connection.prepareStatement(selectQuery1);
	        selectStatement1.setString(1, id);
	        ResultSet resultSet1 = selectStatement1.executeQuery();

	        if (resultSet1.next()) {
	            TOPIC_ID_DB = resultSet1.getString("TOPIC_ID");
	        }
	        
	        ///
	        
	        selectStatement2 = connection.prepareStatement(selectQuery2);
	        selectStatement2.setString(1, TOPIC_ID_DB);
	        ResultSet resultSet2 = selectStatement2.executeQuery();
	        
	        if (resultSet2.next()) {
	        	TOPIC_TITLE_FROM_DB = resultSet2.getString("TITLE");
	        }
		    
	        return TOPIC_TITLE_FROM_DB;
	    } catch(SQLException e) {
	    	e.printStackTrace();
	    	return TOPIC_TITLE_FROM_DB;
	    } finally {
	        try {
	            if (selectStatement1 != null) {
	            	selectStatement1.close();
	            }
	            if (selectStatement2 != null) {
	            	selectStatement2.close();
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
	private String getContents_DB(String id) { 
		String url = "jdbc:mysql://localhost:3306/news_db";
	    String username_DB = "root";
	    String passwd = "kolos2020";
	    
	    Connection connection = null;
	    PreparedStatement selectStatement = null;
	    
	    String CONTENT_FROM_DB = null;
	    
	    String selectQuery = "SELECT CONTENT FROM articles WHERE ID = ?;";
	    
	    try {
	    	connection = DriverManager.getConnection(url, username_DB, passwd);
	        System.out.println("\nSERVER STATUS: Connected to the database...");
		    
	        selectStatement = connection.prepareStatement(selectQuery);
	        selectStatement.setString(1, id);
	        ResultSet resultSet = selectStatement.executeQuery();

	        while(resultSet.next()) {
	        	CONTENT_FROM_DB = resultSet.getString("CONTENT");
	        }
		    return CONTENT_FROM_DB;
	    } catch(SQLException e) {
	    	e.printStackTrace();
	    	return CONTENT_FROM_DB;
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
	
	private boolean changeState() {
		String url = "jdbc:mysql://localhost:3306/news_db";
	    String username_DB = "root";
	    String passwd = "kolos2020";
	    
	    int rowsAffected;
	    
	    Connection connection = null;
	    PreparedStatement updateStatement = null;
	    
	    try {
	    	connection = DriverManager.getConnection(url, username_DB, passwd);
			System.out.println("\nSERVER STATUS: Connected to the database...");
			
		    String updateQuery = "UPDATE ARTICLES SET STATE_ID = 1, alert = true WHERE ID = ?;";
		    updateStatement = connection.prepareStatement(updateQuery);
		    updateStatement.setInt(1, Integer.parseInt(ID_CLICKED));
	        rowsAffected = updateStatement.executeUpdate();
	        if (rowsAffected > 0) {
                System.out.println("SERVER STATUS: Update successful (IN UPDATE ARTICLE:ID:" + ID_CLICKED + ") " + rowsAffected + " rows affected.");
                return true;
            } else {
                System.out.println("SERVER STATUS: Update failed (IN UPDATE ARTICLE:ID: " + ID_CLICKED + ") No rows affected.");
                return false;
            }
	    } catch(SQLException e) {
	    	e.printStackTrace();
	    	return false;
	    } finally {
	        try {
	            if (updateStatement != null) {
	            	updateStatement.close();
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
	
	/* Insert into the table the cause of the declined */
	private boolean setCause(String cause) {
		String url = "jdbc:mysql://localhost:3306/news_db";
	    String username_DB = "root";
	    String passwd = "kolos2020";
	    
	    int rowsAffected;
	    
	    Connection connection = null;
	    PreparedStatement insertStatement = null;
	    
	    try {
	    	connection = DriverManager.getConnection(url, username_DB, passwd);
			System.out.println("\nSERVER STATUS: Connected to the database...");
			
		    String insertQuery = "INSERT INTO ALERTS_CAUSES (ARTICLE_ID, CAUSE) VALUES (?, ?)";
		    insertStatement = connection.prepareStatement(insertQuery);
		    insertStatement.setInt(1, Integer.parseInt(ID_CLICKED));
		    insertStatement.setString(2, cause); 
	        rowsAffected = insertStatement.executeUpdate();
	        if (rowsAffected > 0) {
                System.out.println("SERVER STATUS: Insert successful (IN INSERT ARTICLE:ID:" + ID_CLICKED + ") " + rowsAffected + " rows affected.");
                return true;
            } else {
                System.out.println("SERVER STATUS: Insert failed (IN INSERT ARTICLE:ID: " + ID_CLICKED + ") No rows affected.");
                return false;
            }
	    } catch(SQLException e) {
	    	e.printStackTrace();
	    	return false;
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
	}
}
