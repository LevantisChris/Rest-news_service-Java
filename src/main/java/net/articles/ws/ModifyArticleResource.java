package net.articles.ws;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import net.exceptions.ws.NotIdentifiedRole;
import net.htmlhandler.ws.HtmlHandler;

@Path("/auth/auth_user/modify_article")
public class ModifyArticleResource {
	
	private static String ID_CLICKED;
	
	/* NOTE SOS!!!!: We can say that in order to select what article to modify, we will need to execute the display
	 * all articles and provide him ALL THE ARTICLES THAT HAVE STATE == 1 (CREATED) 
	 * THE USER WILL CHOOSE WHAT ARTICLE HE WANT TO MODIFY. ALSO NOTE THAT WE WIL RETURN THE ARTICLES
	 * THAT BELONGS TO HIM ...*/
	@GET
	public Response handleDisplatAllArticles(@QueryParam("username") String username, @QueryParam("role") String role) {
		System.out.println("IN HERE 1 USERNAME --> " + username);
		System.out.println("IN HERE 2 ROLE --> " + role);
		int ROLE_ID;
		try {
			if(role.equals("JOURNALIST")) {
				ROLE_ID = 2;
				
				ArrayList<String> ARTICLES_IDs = getAllArticleIDS(username);
				
				return Response.status(Response.Status.OK)
                .entity(HtmlHandler.getMODIFY_ARTICLE_HTML(ARTICLES_IDs, username, role, "SELECT ID", "SELECT ID", "SELECT ID"))
                .type(MediaType.TEXT_HTML)
                .build();
				
			} else if(role.equals("CURATOR")){
				ROLE_ID = 3;
				
				ArrayList<String> ARTICLES_IDs = getAllArticleIDS(username);
				
				return Response.status(Response.Status.OK)
                .entity(HtmlHandler.getMODIFY_ARTICLE_HTML(ARTICLES_IDs, username, role, "SELECT ID", "SELECT ID", "SELECT ID"))
                .type(MediaType.TEXT_HTML)
                .build();
				
			} else { throw new NotIdentifiedRole("ERROR: The role could not be identified.");}
		} catch(NotIdentifiedRole e) {
			System.out.print(e.getMessage());
			return Response.ok(e.getMessage()).build();
		}
	}
	
	/* Here we handle the clicks in the IDs (links) */ 
	@GET
    @Path("/{id}")
    public Response getArticle(@PathParam("id") String id, @PathParam("username") String username, @PathParam("role") String role, @PathParam("title") String title, @PathParam("topic") String topic, @PathParam("content") String content) {
		ArrayList<String> ARTICLES_IDs = getAllArticleIDS(username);
		
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
		System.out.print("BEFORE ID --> " + id);
		ID_CLICKED = id;
		System.out.print("AFTER ID_CLICKED --> " + ID_CLICKED);
		/// We return him the same html page but filled with the contents of the article he clicked
		return   Response.status(Response.Status.OK)
                .entity(HtmlHandler.getMODIFY_ARTICLE_HTML(ARTICLES_IDs, username, role, TITLE_FROM_DB, TOPIC_TITLE_FROM_DB, CONTENTS_FROM_DB))
                .type(MediaType.TEXT_HTML)
                .build();
    }
	
	@POST
	@Path("/modify")
	public Response submitModifiedArticle(@FormParam("id") String id, @FormParam("title") String title, @FormParam("topic") String topic, @FormParam("content") String content) {
		System.out.println("FINAL ID_CLICKED --> " + ID_CLICKED);
		return Response.ok(ID_CLICKED).build();
	}
	
	/* NOTE: In this function we will get all the IDs of the articles that the user has clicked ... */
	private ArrayList<String> getAllArticleIDS(String username) {
		String url = "jdbc:mysql://localhost:3306/news_db";
	    String username_DB = "root";
	    String passwd = "kolos2020";
	    
	    Connection connection = null;
	    PreparedStatement selectStatement = null;
	    
	    String selectQuery = "SELECT ID FROM articles WHERE CREATOR_USERNAME = ?;";
	    
	    ArrayList<String> ARTICLES_IDs = new ArrayList<>();
	    
	    try {
	    	connection = DriverManager.getConnection(url, username_DB, passwd);
	        System.out.println("\nSERVER STATUS: Connected to the database...");
		    
	        selectStatement = connection.prepareStatement(selectQuery);
	        selectStatement.setString(1, username);
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
	
	/* NOTE: Get the title from the DB to display it in the user, based on the id of the article the 
	 * user pick */
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
 }
