package net.comments.ws;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import net.articles.ws.manage_articles.Article;
import net.comments.ws.manage_comments.Comments;
import net.exceptions.ws.NotIdentifiedRole;
import net.htmlhandler.ws.HtmlHandler;

@Path("/auth/auth_user/displayCommentsOfArticle_comment")
public class DisplayCommentsOfAnArticleResource_auth {
	
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	public Response handleDisplayAllArticles(@QueryParam("username") String username, @QueryParam("role") String role) {
		System.out.println("SERVER STATUS --> ACCEPT ARTICLE CALLED BY USERNAME == " + username + " - ROLE == " + role);
		if(role == null || role.isEmpty()) {
			return Response.serverError().build();
		}
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
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity("ROLE_NOT_IDENTIFIED").build();		
		}
	}
	
	@GET
	@Path("/{id}")
	@Consumes(MediaType.TEXT_PLAIN)
	public Response handleDisplayComments(@PathParam("id") String article_id, 
										  @QueryParam("username") String username, 
										  @QueryParam("role") String role) {		
		if(article_id == null || username == null || role == null) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		} else if(article_id.isEmpty()) {
			return Response.status(Response.Status.NO_CONTENT).build();
		}
		Article ARTICLE_SELECTED = getSelectedArticle(Integer.parseInt(article_id));
		ArrayList<Comments> COMMENTS_DATA = getCommentsOfArticle(Integer.parseInt(article_id));
		
		return Response.status(Response.Status.OK)
                .entity(HtmlHandler.getArticleComments(ARTICLE_SELECTED, COMMENTS_DATA, username))
                .type(MediaType.TEXT_HTML)
                .build();
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
	   
	    		selectQuery = "SELECT C.*\r\n"
	    				+ "FROM COMMENTS C\r\n"
	    				+ "INNER JOIN ARTICLES A ON C.ARTICLE_ID = A.ID\r\n"
	    				+ "WHERE C.STATE_ID = 3 AND A.STATE_ID = 4;";
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
	
	private Article getSelectedArticle(int article_id) {
		String url = "jdbc:mysql://localhost:3306/news_db";
	    String username_DB = "root";
	    String passwd = "kolos2020";
	    
	    String selectQuery = "SELECT * FROM news_db.articles WHERE ID = ?";
	    Connection connection = null;
	    PreparedStatement selectStatement = null;
	    
	    try {
	    	
	    	connection = DriverManager.getConnection(url, username_DB, passwd);
	        System.out.println("\nSERVER STATUS: Connected to the database...");
	        selectStatement = connection.prepareStatement(selectQuery);
	        selectStatement.setInt(1, article_id);
	        ResultSet resultSet = selectStatement.executeQuery();
	        
	        Article ARTICLE_SELECTED = new Article();
	        while(resultSet.next()) {
	        	ARTICLE_SELECTED.setId(resultSet.getInt("ID"));
	        	ARTICLE_SELECTED.setTitle(resultSet.getString("TITLE"));
	        	ARTICLE_SELECTED.setContents(resultSet.getString("CONTENT"));
	        	ARTICLE_SELECTED.setDate_creation(resultSet.getDate("DATE_CREATION"));
	        	ARTICLE_SELECTED.setState_id(resultSet.getInt("STATE_ID"));
	        	ARTICLE_SELECTED.setCreator_username(resultSet.getString("CREATOR_USERNAME"));
	        }	
	        return ARTICLE_SELECTED;
	    } catch(SQLException e) {
	    	System.out.println("SERVER STATUS: --ERROR-- IN THE getSelectedArticle IN THE DisplayCommentsOfAnArticleresource");
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
	
	/* Based on the ID of the article that the user select we will get the comment and put them in an ArrayList */
	private ArrayList<Comments> getCommentsOfArticle(int article_id) {
			String url = "jdbc:mysql://localhost:3306/news_db";
		    String username_DB = "root";
		    String passwd = "kolos2020";
		    
		    ArrayList<Comments> COMMENTS_DATA = new ArrayList<>();
		    
		    String selectQuery = "SELECT * FROM news_db.comments WHERE ARTICLE_ID = ?";
		    Connection connection = null;
		    PreparedStatement selectStatement = null;
		    
		    try {
		    	
		    	connection = DriverManager.getConnection(url, username_DB, passwd);
		        System.out.println("\nSERVER STATUS: Connected to the database...");
		        selectStatement = connection.prepareStatement(selectQuery);
		        selectStatement.setInt(1, article_id);
		        ResultSet resultSet = selectStatement.executeQuery();
		        
		        while(resultSet.next()) {
					Comments comment = new Comments(
							resultSet.getInt("ID"),
							resultSet.getString("CONTENT"),
							resultSet.getDate("DATE_CREATION"),
							resultSet.getInt("ARTICLE_ID"),
							resultSet.getInt("STATE_ID"), 
							resultSet.getString("CREATOR_USERNAME"));
					
					COMMENTS_DATA.add(comment);
		        }
		        return COMMENTS_DATA;
		    } catch(SQLException e) {
		    	System.out.println("SERVER STATUS: --ERROR-- IN THE getCommentsOfArticle IN THE DisplayCommentsOfAnArticleresource ");
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
