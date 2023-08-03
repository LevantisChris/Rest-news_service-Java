package net.articles.ws;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import jakarta.ws.rs.CookieParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import net.articles.ws.manage_articles.Article;
import net.exceptions.ws.NotIdentifiedRole;
import net.htmlhandler.ws.HtmlHandler;
import net.sessionExtractor.ws.SessionExtractor;

@Path("/auth/auth_user/display_article")
public class DisplayArticleResource_auth {

	private static int ROLE_ID;
	
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
		
		System.out.println("SERVER STATUS --> ACCEPT ARTICLE CALLED BY USERNAME == " + username + " - ROLE == " + role);
		if(role == null || role.isEmpty()) {
			return Response.serverError().build();
		}
		try {
			if(role.equals("JOURNALIST")) {
				ROLE_ID = 2;
				
				ArrayList<String> ARTICLES_IDs = getAllArticleIDS(username);
				
				return Response.status(Response.Status.OK)
		                .entity(HtmlHandler.getIDS_DISPLAY_ARTICLE_HTML(ARTICLES_IDs, ROLE_ID))
		                .type(MediaType.TEXT_HTML)
		                .build();
				
			} else if(role.equals("CURATOR")){
				ROLE_ID = 3;
				
				ArrayList<String> ARTICLES_IDs = getAllArticleIDS(username);
				
				return Response.status(Response.Status.OK)
		                .entity(HtmlHandler.getIDS_DISPLAY_ARTICLE_HTML(ARTICLES_IDs, ROLE_ID))
		                .type(MediaType.TEXT_HTML)
		                .build();
				
			} else { throw new NotIdentifiedRole("ERROR: The role could not be identified.");}
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
		if(role.equals("JOURNALIST")) {
			/* Check if the article can be viewed by the user of the session */
			/// The article must be in the state 4 to be viewed
			if(sessionExtractor.checkIfArticleCanBeViewed(sessionId, id, 4, "display") == false) {
				return Response.status(Response.Status.NOT_ACCEPTABLE).build();
			}
			///
		}
		
		if(id == null || id.isEmpty()) {
			Response.status(Response.Status.NOT_FOUND)
    		.entity("SELECT_ID")
    		.build();
		}
		
		String CREATOR_USERNAME_FROM_DB = getCreatorUsernameArticle_DB(id); 
		if(CREATOR_USERNAME_FROM_DB == null) {
			return Response.serverError().build(); // if is null that means the query failed to execute, we must not run the other code ...
		}
		String TITLE_FROM_DB = getTitleArticle_DB(id); 
		if(TITLE_FROM_DB == null) {
			return Response.serverError().build(); 
		}
		String TOPIC_TITLE_FROM_DB = getTopicArticle_DB(id);
		if(TOPIC_TITLE_FROM_DB == null) {
			return Response.serverError().build();
		}
		String CONTENTS_FROM_DB = getContents_DB(id);
		if(CONTENTS_FROM_DB == null) {
			return Response.serverError().build();
		}
		/// We return him the same html page but filled with the contents of the article he clicked
		return   Response.status(Response.Status.OK)
                .entity(HtmlHandler.getDISPLAY_ARTICLE_HTML(username, role, CREATOR_USERNAME_FROM_DB, TITLE_FROM_DB, TOPIC_TITLE_FROM_DB, CONTENTS_FROM_DB))
                .type(MediaType.TEXT_HTML)
                .build();
    }
	
	/*-------------------------------------------------------------------------------------------------------------------------------------*/
	
	private ArrayList<String> getAllArticleIDS(String username) {
		String url = "jdbc:mysql://localhost:3306/news_db";
	    String username_DB = "root";
	    String passwd = "kolos2020";
	    
	    String selectQuery;
	    Connection connection = null;
	    PreparedStatement selectStatement = null;
	    
	    ResultSet resultSet;
	   	    
	    ArrayList<String> ARTICLES_IDs = new ArrayList<>();
	    
	    try {
	    	connection = DriverManager.getConnection(url, username_DB, passwd);
	        System.out.println("\nSERVER STATUS: Connected to the database...");
		    
	        if(ROLE_ID == 2) { // is JOURNALIST
	        	selectQuery = "SELECT ID, TITLE, CONTENT FROM articles WHERE STATE_ID = 4 OR CREATOR_USERNAME = ?;";
	        	selectStatement = connection.prepareStatement(selectQuery);
		    	selectStatement.setString(1, username);
		    	resultSet = selectStatement.executeQuery();
	        } else { // is a Curator	
		    	selectQuery = "SELECT ID, TITLE, CONTENT FROM articles;"; // can see everything
		    	selectStatement = connection.prepareStatement(selectQuery);
		    	resultSet = selectStatement.executeQuery();
	        }

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
	private String getCreatorUsernameArticle_DB(String id) {
		String url = "jdbc:mysql://localhost:3306/news_db";
	    String username_DB = "root";
	    String passwd = "kolos2020";
	    
	    Connection connection = null;
	    PreparedStatement selectStatement = null;
	    
	    String CONTENT_FROM_DB = null;
	    
	    String selectQuery = "SELECT CREATOR_USERNAME FROM articles WHERE ID = ?;";
	    
	    try {
	    	connection = DriverManager.getConnection(url, username_DB, passwd);
	        System.out.println("\nSERVER STATUS: Connected to the database...");
		    
	        selectStatement = connection.prepareStatement(selectQuery);
	        selectStatement.setString(1, id);
	        ResultSet resultSet = selectStatement.executeQuery();

	        while(resultSet.next()) {
	        	CONTENT_FROM_DB = resultSet.getString("CREATOR_USERNAME");
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
}
