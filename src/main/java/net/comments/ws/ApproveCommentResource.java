package net.comments.ws;

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
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import net.comments.ws.manage_comments.Comments;
import net.htmlhandler.ws.HtmlHandler;
import net.sessionExtractor.ws.SessionExtractor;

/// This function is only available for the Curator

@Path("/auth/auth_user/approve_comment")
public class ApproveCommentResource {
	
	@GET
	public Response handleDisplayAllComments(@CookieParam("session_id") String sessionId) {
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
		
		System.out.println("SERVER STATUS: A user with username //" + username + "// and role //" + role + "//");
		if(role == null || role.isEmpty()) {
			return Response.serverError().build();
		}
		if(role.equals("CURATOR")) {
			
			ArrayList<Comments> COMMENTS_DATA = getAllComments();
			
			return Response.status(Response.Status.OK)
	                .entity(HtmlHandler.getCommentsFromAPPROVE_COMMENTS_auth(COMMENTS_DATA, username))
	                .type(MediaType.TEXT_HTML)
	                .build();
		} else {
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity("ROLE_NOT_IDENTIFIED").build();		
		}
	}
	
	@POST
	@Path("/approve")
    @Consumes(MediaType.APPLICATION_JSON)
	public Response handleApproveButton(@CookieParam("session_id") String sessionId, String json) {
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
		
		System.out.println("SERVER STATUS: THE JSON WE GET FROM CLIENT IS " + json);
		if(json == null) {
			return Response.serverError().build();
		}
		Long commentId = null;

	    /* Extract from the JSON the contents */
	    try {
	        
	    	JSONObject jsonObjectDecode = (JSONObject) JSONValue.parse(json);
	        commentId = (Long) jsonObjectDecode.get("comment_id");
	        
			System.out.println("SERVER STATUS: STRING EXTRACTED ARE " + commentId);
	        
	        if(changeState(commentId.intValue()) == true) {
	        	return Response.ok("STATE_UPDATED_SUCESFULLY").build();
	        } else {
	        	return Response.serverError().build();
	        }
	        
	    } catch (Exception e) {
	        System.out.println("SERVER STATUS: --ERROR-- in parsing JSON");
	        e.printStackTrace();
	        return Response.serverError().build();
	    }
	}

	@GET
	@Path("/filCom")
	public Response handleFilters(@CookieParam("session_id") String sessionId,
								  @QueryParam("commentId") String commentId, 
								  @QueryParam("articleId") String articleId) {
		if(sessionId == null || sessionId.isBlank()) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
		
		///
		/* Get the user that has the session and also the role */
		SessionExtractor sessionExtractor = new SessionExtractor();
		if(sessionExtractor.checkIfSessionExists(sessionId) == false) {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
		String clickedByName = sessionExtractor.getUsernameFromSession(sessionId);
		String role = sessionExtractor.getRoleFromSession(sessionId);
		if(!role.equals("CURATOR")) {
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity("YOU_DONT_HAVE_PERMISSION").build();
		}
		System.out.println("SERVER STATUS: SESSION_ID NUM: " + sessionId +" USERNAME extracted is --> " + clickedByName + " and ROLE extracted is " + role);
		///
		
		System.out.println("SERVER STATUS: Filters clicked by name //" + clickedByName + "// the comment Id //" + commentId + "// and article Id //" + articleId + "//");
		if(commentId.isEmpty() && articleId.isEmpty()) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
	                .entity("ADD_COMMENT_ID_OR_ARTICLE_ID")
	                .type(MediaType.TEXT_HTML)
	                .build();
		} else {
			ArrayList<Comments> UPDATED_COMMENTS_DATA = getSpecificComments(articleId, commentId);
			if(UPDATED_COMMENTS_DATA == null) {
				return Response.serverError().build();
			}
			return Response.status(Response.Status.OK)
	                .entity(HtmlHandler.getCommentsFromAPPROVE_COMMENTS_auth(UPDATED_COMMENTS_DATA, clickedByName))
	                .type(MediaType.TEXT_HTML)
	                .build();
		}
	}
	
	/*-------------------------------------------------------------------------------------------------------------------------*/
	
	
	private ArrayList<Comments> getAllComments() {
		String url = "jdbc:mysql://localhost:3306/news_db";
	    String username_DB = "root";
	    String passwd = "kolos2020";
	    
	    ArrayList<Comments> COMMENTS_DATA = new ArrayList<>();
	    
	    Connection connection = null;
	    PreparedStatement selectStatement = null;
	    
	    String selectQuery = "SELECT * FROM news_db.comments WHERE STATE_ID = 1;"; // only the comments that are in the CREATED state can be modified (STATE_ID: 1)
	    
	    try {
	    	
	    	connection = DriverManager.getConnection(url, username_DB, passwd);
	    	System.out.println("\nSERVER STATUS: Connected to the database...");
	    	selectStatement = connection.prepareStatement(selectQuery);
	    	
	    	ResultSet resultSet = selectStatement.executeQuery();
	    	
	    	while(resultSet.next()) {
	    		COMMENTS_DATA.add(new Comments(resultSet.getInt("ID"),
	    										resultSet.getString("CONTENT"),
	    										resultSet.getDate("DATE_CREATION"),
	    										resultSet.getInt("ARTICLE_ID"),
	    										resultSet.getInt("STATE_ID"),
	    										resultSet.getString("CREATOR_USERNAME")));
	    	}
	    	return COMMENTS_DATA;
	    } catch(SQLException e) {
	    	System.out.println("SERVER STATUS: --ERROR-- occured in the getAllComents() in modify_comment");
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
	
	private ArrayList<Comments> getSpecificComments(String articleId, String commentId) {
		String url = "jdbc:mysql://localhost:3306/news_db";
	    String username_DB = "root";
	    String passwd = "kolos2020";
	    
	    ArrayList<Comments> COMMENTS_DATA = new ArrayList<>();
	    
	    Connection connection = null;
	    PreparedStatement selectStatement = null;
	    
	    try {
	    	if(!articleId.isEmpty() && !commentId.isEmpty()) {
	    		System.out.println("SERVER STATUS: FIRST QUERY EXECUTING ...");
		    	String selectQuery = "SELECT * FROM news_db.comments WHERE ID = ? AND ARTICLE_ID = ? AND STATE_ID = 1;";
		    	connection = DriverManager.getConnection(url, username_DB, passwd);
		    	System.out.println("\nSERVER STATUS: Connected to the database...");
		    	selectStatement = connection.prepareStatement(selectQuery);
		    	selectStatement.setInt(1, Integer.parseInt(commentId));
		    	selectStatement.setInt(2, Integer.parseInt(articleId));
		    	ResultSet resultSet = selectStatement.executeQuery();
		    	
		    	while(resultSet.next()) {
		    		COMMENTS_DATA.add(new Comments(resultSet.getInt("ID"),
		    										resultSet.getString("CONTENT"),
		    										resultSet.getDate("DATE_CREATION"),
		    										resultSet.getInt("ARTICLE_ID"),
		    										resultSet.getInt("STATE_ID"),
		    										resultSet.getString("CREATOR_USERNAME")));
		    	}
		    	return COMMENTS_DATA;
	    	} else if(articleId.isEmpty() && !commentId.isEmpty()) { // then we are going to do a select based on the commentId
	    		System.out.println("SERVER STATUS: SECOND QUERY EXECUTING ...");
	    		String selectQuery = "SELECT * FROM news_db.comments WHERE ID = ? AND STATE_ID = 1;;";
		    	connection = DriverManager.getConnection(url, username_DB, passwd);
		    	System.out.println("\nSERVER STATUS: Connected to the database...");
		    	selectStatement = connection.prepareStatement(selectQuery);
		    	selectStatement.setInt(1, Integer.parseInt(commentId));
		    	ResultSet resultSet = selectStatement.executeQuery();
		    	
		    	while(resultSet.next()) {
		    		COMMENTS_DATA.add(new Comments(resultSet.getInt("ID"),
		    										resultSet.getString("CONTENT"),
		    										resultSet.getDate("DATE_CREATION"),
		    										resultSet.getInt("ARTICLE_ID"),
		    										resultSet.getInt("STATE_ID"),
		    										resultSet.getString("CREATOR_USERNAME")));
		    	}
		    	return COMMENTS_DATA;
	    	} else if(!articleId.isEmpty() && commentId.isEmpty()) { // then we are going to do a select based on the articleId
	    		System.out.println("SERVER STATUS: THIRD QUERY EXECUTING ...");
	    		String selectQuery = "SELECT * FROM news_db.comments WHERE ARTICLE_ID = ? AND STATE_ID = 1;;";
		    	connection = DriverManager.getConnection(url, username_DB, passwd);
		    	System.out.println("\nSERVER STATUS: Connected to the database...");
		    	selectStatement = connection.prepareStatement(selectQuery);
		    	selectStatement.setInt(1, Integer.parseInt(articleId));
		    	ResultSet resultSet = selectStatement.executeQuery();
		    	
		    	while(resultSet.next()) {
		    		COMMENTS_DATA.add(new Comments(resultSet.getInt("ID"),
		    										resultSet.getString("CONTENT"),
		    										resultSet.getDate("DATE_CREATION"),
		    										resultSet.getInt("ARTICLE_ID"),
		    										resultSet.getInt("STATE_ID"),
		    										resultSet.getString("CREATOR_USERNAME")));
		    	}
		    	return COMMENTS_DATA;
	    	} else {
	    		System.out.println("SERVER STATUS: --ERROR-- NO QUERY EXECUTING ...");
	    		return null;
	    	}
	    } catch(SQLException e) {
	    	System.out.println("SERVER STATUS: --ERROR-- occured in the getSpecificComments() in approve_comment");
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
	
	/* NOTE: Comments have two states created (STATE_ID: 1) and submitted (STATE_ID: 3) */
	private boolean changeState(int commentId) {
		String url = "jdbc:mysql://localhost:3306/news_db";
	    String username_DB = "root";
	    String passwd = "kolos2020";
	    
	    int rowsAffected;
	    
	    Connection connection = null;
	    PreparedStatement updateStatement = null;
	    
	    try {
	    	connection = DriverManager.getConnection(url, username_DB, passwd);
			System.out.println("\nSERVER STATUS: Connected to the database...");
			
		    String updateQuery = "UPDATE news_db.comments SET STATE_ID = 3 WHERE ID = ?;";
		    updateStatement = connection.prepareStatement(updateQuery);
		    updateStatement.setInt(1, commentId);
	        rowsAffected = updateStatement.executeUpdate();
	        if (rowsAffected > 0) {
                System.out.println("SERVER STATUS: Update successful (IN APPROVE COMMENT:ID:" + commentId + ") " + rowsAffected + " rows affected.");
                return true;
            } else {
                System.out.println("SERVER STATUS: Update failed (IN APPROVE ARTICLE:ID: " + commentId + ") No rows affected.");
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
