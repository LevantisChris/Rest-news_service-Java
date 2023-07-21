package net.articles.ws;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import net.exceptions.ws.NotIdentifiedRole;
import net.htmlhandler.ws.HtmlHandler;

@Path("/auth/auth_user/submit_article")
public class SubmitArticleResource {
	
	private static String ID_CLICKED;
	
	@GET
	public Response handleDisplatAllArticles(@QueryParam("username") String username, @QueryParam("role") String role) {
		System.out.println("SERVER STATUS --> SUBMIT ARTICLE CALLED BY USERNAME == " + username + " - ROLE == " + role);
		if(role == null || role.isEmpty()) {
			return Response.serverError().build();
		}
		ID_CLICKED = null;
		int ROLE_ID;
		try {
			if(role.equals("JOURNALIST")) {
				ROLE_ID = 2;
				
				ArrayList<String> ARTICLES_IDs = getAllArticleIDS(username, role);
				
				return Response.status(Response.Status.OK)
                .entity(HtmlHandler.getIDS_SUBMIT_ARTICLE_HTML(ARTICLES_IDs))
                .type(MediaType.TEXT_HTML)
                .build();
				
			} else if(role.equals("CURATOR")){
				ROLE_ID = 3;
				
				ArrayList<String> ARTICLES_IDs = getAllArticleIDS(username, role);
				
				return Response.status(Response.Status.OK)
		                .entity(HtmlHandler.getIDS_SUBMIT_ARTICLE_HTML(ARTICLES_IDs))
		                .type(MediaType.TEXT_HTML)
		                .build();
				
			} else { throw new NotIdentifiedRole("ERROR: The role could not be identified.");}
		} catch(NotIdentifiedRole e) {
			System.out.print(e.getMessage());
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity(e.getMessage()).build();		
		}
	}
	
	/* NOTE: We do not display articles that are in alert mode because the user must go in the modify function to change it ... */
	private ArrayList<String> getAllArticleIDS(String username, String role) {
		String url = "jdbc:mysql://localhost:3306/news_db";
	    String username_DB = "root";
	    String passwd = "kolos2020";
	    
	    Connection connection = null;
	    PreparedStatement selectStatement = null;
	    
	    String selectQuery = null;
	    
	    
	    
	    ArrayList<String> ARTICLES_IDs = new ArrayList<>();
	    
	    try {
	    	
	    	connection = DriverManager.getConnection(url, username_DB, passwd);
	        System.out.println("\nSERVER STATUS: Connected to the database...");
	    	
	    	if(role.equals("JOURNALIST")) {
		    	selectQuery = "SELECT ID FROM articles WHERE CREATOR_USERNAME = ? AND STATE_ID = 1 AND alert = 0;";
		    	selectStatement = connection.prepareStatement(selectQuery);
		    	 selectStatement.setString(1, username);
		    } else { // if the user is CURATOR, all the articles will be displayed ... 
		    	selectQuery = "SELECT ID FROM articles WHERE STATE_ID = 1 AND alert = 0;";
		    	selectStatement = connection.prepareStatement(selectQuery);
		    }
		   
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
	
	@GET
    @Path("/{id}")
    public Response getArticle(@PathParam("id") String id, @PathParam("username") String username, @PathParam("role") String role, @PathParam("title") String title, @PathParam("topic") String topic, @PathParam("content") String content) {
		if(id == null || id.isEmpty() || id.isBlank()) {
			Response.status(Response.Status.NOT_FOUND)
    		.entity("SELECT_ID")
    		.build();
		}
		
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
                .entity(HtmlHandler.getSUBMIT_ARTICLE_HTML(username, role, TITLE_FROM_DB, TOPIC_TITLE_FROM_DB, CONTENTS_FROM_DB))
                .type(MediaType.TEXT_HTML)
                .build();
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
	
	/// NOTE: we are going to UPDATE the STATE_ID based on the title of the 
	@Path("/submit")
	@PUT
	public Response submitArticle() {
		System.out.println("SERVER STATUS: ARTICLE THAT CLICKED FOR CHANGING STATE TO --SUBMITTED-- IS WITH THE ID == " + ID_CLICKED);
	    if(changeState() == true) {
	    	return Response.ok("SUBMIT_DONE_SUCCESFULLY:ID_MODIFIED:" + ID_CLICKED).build();
	    } else
	    	return Response.serverError().build();
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
			
		    String updateQuery = "UPDATE ARTICLES SET STATE_ID = 2 WHERE ID = ?;";
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
