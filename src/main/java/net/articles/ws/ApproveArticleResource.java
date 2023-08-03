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

///NOTE: The function accept article is only available for the Curator ...
/* NOTE: THE CURATOR CAN SEE ALL THE ARTICLES THAT BELONGS TO EVERY-ONE */

/// Done

@Path("/auth/auth_user/approve_article")
public class ApproveArticleResource {
	
	private static String ID_CLICKED;
	
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
		
		if(role == null || role.isEmpty()) {
			return Response.serverError().build();
		}
		ID_CLICKED = null;
		try {
			if(role.equals("JOURNALIST")) {
				return Response.serverError().build(); // If for some reason we have a failure in out system and a JOURNALIST or some other access this function, we send a server error ... 
			} else if(role.equals("CURATOR")) {
				ArrayList<String> ARTICLES_IDs = getAllArticleIDS(username);
				return Response.status(Response.Status.OK)
		                .entity(HtmlHandler.getIDS_APPROVE_ARTICLE_HTML(ARTICLES_IDs))
		                .type(MediaType.TEXT_HTML)
		                .build();
			} else {
				throw new NotIdentifiedRole("ROLE_NOT_IDENTIFIED");
			}
		} catch(NotIdentifiedRole e) {
			System.out.print(e.getMessage());
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity(e.getMessage()).build();
		}
	}
	
	@GET
    @Path("/{id}")
    public Response getArticle(@CookieParam("session_id") String sessionId,
    						   @PathParam("id") String id) {
		
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
				/* Check if the article can be viewed by the user of the session */
				/// The article must be in the state 2 to be viewed
				if(sessionExtractor.checkIfArticleCanBeViewed(sessionId, id, 2) == false) {
					return Response.status(Response.Status.NOT_ACCEPTABLE).build();
				}
				///
		
		String TITLE_FROM_DB = getTitleArticle_DB(id); 
		if(TITLE_FROM_DB == null) {
			return Response.serverError().build(); // if is null that means the query failed to execute, we must not run the other code ...
		}
		String TOPIC_TITLE_FROM_DB = getTopicArticle_DB(id);
		if(TOPIC_TITLE_FROM_DB == null) {
			return Response.serverError().build();
		}
		String CONTENTS_FROM_DB = getContents_DB(id);
		if(CONTENTS_FROM_DB == null) {
			return Response.serverError().build();
		}
		ID_CLICKED = id;
		/// We return him the same html page but filled with the contents of the article he clicked
		return   Response.status(Response.Status.OK)
                .entity(HtmlHandler.getAPPROVE_ARTICLE_HTML(username, role, TITLE_FROM_DB, TOPIC_TITLE_FROM_DB, CONTENTS_FROM_DB))
                .type(MediaType.TEXT_HTML)
                .build();
    }
	
	@Path("/approve")
	@PUT
	public Response approveArticle(@CookieParam("session_id") String sessionId) {
		System.out.println("SESSION_ID RECEIVED 3 --> " + sessionId);
		System.out.println("SERVER STATUS: ARTICLE THAT CLICKED FOR CHANGING STATE TO --APPROVED-- IS WITH THE ID == " + ID_CLICKED);
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
	    if(changeState() == true) {
	    	return Response.ok("APPROVE_DONE_SUCCESFULLY:ID_MODIFIED:" + ID_CLICKED).build();
	    } else
	    	return Response.serverError().build();
	}
	
	/*-------------------------------------------------------------------------------------------------------------------------------------*/
	private ArrayList<String> getAllArticleIDS(String username) {
		
		if(username== null || username.isEmpty()) {
			return null;
		}
		
		String url = "jdbc:mysql://localhost:3306/news_db";
	    String username_DB = "root";
	    String passwd = "kolos2020";
	    
	    Connection connection = null;
	    PreparedStatement selectStatement = null;
	    
	    String selectQuery = "SELECT ID FROM articles WHERE STATE_ID = 2;"; // we display the articles that are in the state submitted ...
	    
	    ArrayList<String> ARTICLES_IDs = new ArrayList<>();
	    
	    try {
	    	connection = DriverManager.getConnection(url, username_DB, passwd);
	        System.out.println("\nSERVER STATUS: Connected to the database...");
		    
	        selectStatement = connection.prepareStatement(selectQuery);
	        //selectStatement.setString(1, username);
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
			
		    String updateQuery = "UPDATE ARTICLES SET STATE_ID = 3 WHERE ID = ?;";
		    updateStatement = connection.prepareStatement(updateQuery);
		    updateStatement.setInt(1, Integer.parseInt(ID_CLICKED)); // 2 is the state for submited
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
}
