package net.comments.ws;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashSet;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import net.exceptions.ws.NotIdentifiedRole;
import net.htmlhandler.ws.HtmlHandler;

@Path("/auth/auth_user/displayCommentsOfArticle_comment")
public class DisplayCommentsOfAnArticleResource {
	
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	public Response handleDisplatAllArticles(@QueryParam("username") String username, @QueryParam("role") String role) {
		System.out.println("SERVER STATUS --> ACCEPT ARTICLE CALLED BY USERNAME == " + username + " - ROLE == " + role);
		String ID_CLICKED = null;
		int ROLE_ID;
		try {
			if(role.equals("JOURNALIST")) {
				ROLE_ID = 2;
				
				HashSet<String>  COMMENTS_ARTICLES_IDs = getAllArticleIDS(username, role);
				
				return Response.status(Response.Status.OK)
		                .entity(HtmlHandler.getIDS_DISPLAY_COMMENTS_OF_ARTICLE_ARTICLE_HTML(COMMENTS_ARTICLES_IDs, username, role))
		                .type(MediaType.TEXT_HTML)
		                .build();
				
			} else if(role.equals("CURATOR")){
				ROLE_ID = 3;
				
				HashSet<String>  COMMENTS_ARTICLES_IDs = getAllArticleIDS(username, role);
				
				return Response.status(Response.Status.OK)
		                .entity(HtmlHandler.getIDS_DISPLAY_COMMENTS_OF_ARTICLE_ARTICLE_HTML(COMMENTS_ARTICLES_IDs, username, role))
		                .type(MediaType.TEXT_HTML)
		                .build();
				
			} else { throw new NotIdentifiedRole("ERROR: The role could not be identified.");}
		} catch(NotIdentifiedRole e) {
			System.out.print(e.getMessage());
			return Response.ok(e.getMessage()).build();
		}
	}
	
	@GET
	@Path("/{id}")
	@Consumes(MediaType.TEXT_PLAIN)
	public Response handleDisplayComments(@PathParam("id") String id, @PathParam("username") String username, @PathParam("role") String role) {
		System.out.println("TEST ID --> " + id);
		return Response.ok("OKKKK!!").build();
	}
	
	/*-------------------------------------------------------------------------------------------------------------------------------------*/
	private HashSet<String>  getAllArticleIDS(String username, String role) {
		String url = "jdbc:mysql://localhost:3306/news_db";
	    String username_DB = "root";
	    String passwd = "kolos2020";
	    
	    Connection connection = null;
	    PreparedStatement selectStatement = null;
	    
	    String selectQuery = null;
	    
	    /* We are using a hashset because we don't want duplicated values */
	    HashSet<String> COMMENTS_ARTICLES_IDs = new HashSet<>();
	    
	    try {
	    	
	    	if(role.equals("CURATOR")) {
	    		
	    		selectQuery = "SELECT * FROM comments;"; // the curator can see everything 
	    		connection = DriverManager.getConnection(url, username_DB, passwd);
		        System.out.println("\nSERVER STATUS: Connected to the database...");
			    
		        selectStatement = connection.prepareStatement(selectQuery);
		        ResultSet resultSet = selectStatement.executeQuery();

		        while(resultSet.next()) {
		        	int comment_id = resultSet.getInt("ID");
					String comment_content = resultSet.getString("CONTENT");
					Date comment_date = resultSet.getDate("DATE_CREATION");
					int comment_article_id = resultSet.getInt("ARTICLE_ID");
					int comment_state_id = resultSet.getInt("STATE_ID");
					String comment_creator_username = resultSet.getString("CREATOR_USERNAME");
					COMMENTS_ARTICLES_IDs.add(String.valueOf(comment_article_id));
		        }
			    return COMMENTS_ARTICLES_IDs;
	    		
	    	} else if(role.equals("JOURNALIST")){
	    		
	    		selectQuery = "SELECT * FROM comments WHERE STATE_ID = 3;"; // the Journalist can see only approved comments 
	    		connection = DriverManager.getConnection(url, username_DB, passwd);
		        System.out.println("\nSERVER STATUS: Connected to the database...");
			    
		        selectStatement = connection.prepareStatement(selectQuery);
		        ResultSet resultSet = selectStatement.executeQuery();

		        while(resultSet.next()) {
		        	int comment_id = resultSet.getInt("ID");
					String comment_content = resultSet.getString("CONTENT");
					Date comment_date = resultSet.getDate("DATE_CREATION");
					int comment_article_id = resultSet.getInt("ARTICLE_ID");
					int comment_state_id = resultSet.getInt("STATE_ID");
					String comment_creator_username = resultSet.getString("CREATOR_USERNAME");
					COMMENTS_ARTICLES_IDs.add(String.valueOf(comment_article_id));
		        }
			    return COMMENTS_ARTICLES_IDs;
	    	} else {
	    		return null;
	    	}
	    	
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
