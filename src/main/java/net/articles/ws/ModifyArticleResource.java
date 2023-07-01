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

/* NOTE: IF THERE IS A ALERT ON AN ARTICLE THE USER WILL SEE THE CAUSE OF IT ... */ 

@Path("/auth/auth_user/modify_article")
public class ModifyArticleResource {
	
	private static String ID_CLICKED;
	
	/* NOTE SOS!!!!: We can say that in order to select what article to modify, we will need to execute the display
	 * all articles and provide him ALL THE ARTICLES THAT HAVE STATE == 1 (CREATED) 
	 * THE USER WILL CHOOSE WHAT ARTICLE HE WANT TO MODIFY. ALSO NOTE THAT WE WIL RETURN THE ARTICLES
	 * THAT BELONGS TO HIM ...*/
	@GET
	public Response handleDisplatAllArticles(@QueryParam("username") String username, @QueryParam("role") String role) {
		ID_CLICKED = null;
		int ROLE_ID;
		try {
			if(role.equals("JOURNALIST")) {
				ROLE_ID = 2;
				
				ArrayList<String> ARTICLES_IDs = getAllArticleIDS(username, role);
				
				return Response.status(Response.Status.OK)
                .entity(HtmlHandler.getIDS_MODIFY_ARTICLE_HTML(ARTICLES_IDs))
                .type(MediaType.TEXT_HTML)
                .build();
				
			} else if(role.equals("CURATOR")){
				ROLE_ID = 3;
				
				ArrayList<String> ARTICLES_IDs = getAllArticleIDS(username, role);
				
				return Response.status(Response.Status.OK)
		                .entity(HtmlHandler.getIDS_MODIFY_ARTICLE_HTML(ARTICLES_IDs))
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
		if(id == null || id.isEmpty() || id.isBlank()) {
			Response.status(Response.Status.NOT_FOUND)
    		.entity("SELECT_ID")
    		.build();
		}
		
		/// Find if a curator has --DECLINED-- the article that clicked, and if it has, bring the cause to display it in the 
		/// user.
		String CAUSE = getCAUSE(Integer.parseInt(id)); 
		
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
                .entity(HtmlHandler.getMODIFY_ARTICLE_HTML(username, role, TITLE_FROM_DB, TOPIC_TITLE_FROM_DB, CONTENTS_FROM_DB, CAUSE))
                .type(MediaType.TEXT_HTML)
                .build();
    }
	
	/// NOTE: Here based of the id of the article the user has modified, we submit those changes in the DB 
	@POST
	@Path("/modify")
	public Response modifyModifiedArticle(@FormParam("id") String id, @FormParam("title") String title, @FormParam("topic") String topic, @FormParam("content") String content) {
		
		if(ID_CLICKED == null) {
			return Response.status(Response.Status.NOT_FOUND)
            		.entity("SELECT_ID")
            		.build();
		}
		if(title.equals("SELECT ID") || topic.equals("SELECT ID") || content.equals("SELECT ID")) {
			return Response.status(Response.Status.NOT_FOUND)
            		.entity("SELECT_ID")
            		.build();
		} else if(title.isEmpty() || content.isEmpty() || topic.isEmpty()) {
			return Response.status(Response.Status.NOT_FOUND)
            		.entity("EMPTY_FIELDS")
            		.build();
		}
		
		String url = "jdbc:mysql://localhost:3306/news_db";
	    String username_DB = "root";
	    String passwd = "kolos2020";
	    
	    Connection connection = null;
	    PreparedStatement selectTopicStatement = null;
	    PreparedStatement updateStatement = null;
	    int rowsAffected = 0; // to see if the update has been done successfully ...
		
	    String selectTopicQuery = "SELECT ID FROM TOPIC WHERE TITLE = ?"; // see if the topic title that the user add exists
		try {
				connection = DriverManager.getConnection(url, username_DB, passwd);
				System.out.println("\nSERVER STATUS: Connected to the database...");
				
				selectTopicStatement = connection.prepareStatement(selectTopicQuery);
				selectTopicStatement.setString(1, topic);
				
				ResultSet resultSet = selectTopicStatement.executeQuery();
	
				//
		        if (!resultSet.next()) {
		            System.out.println("SERVER STATUS: --ERROR-- Topic does not exist, the modification failed");
		            return Response.status(Response.Status.NOT_FOUND)
		            		.entity("TOPIC_DOES_NOT_EXIST")
		            		.build();
		        }
		        int topic_id = resultSet.getInt("ID");
		        System.out.println("TEST --> " + topic_id);
		        //
	        
		        /// Now we are sure that the topic exists and we can insert the modified article ...
		        String updateQuery = "UPDATE ARTICLES SET TITLE = ?, CONTENT = ?, TOPIC_ID = ? WHERE ID = ?";
	        
		        //
	            System.out.println("\nSERVER STATUS: Connected to the database...");
	            updateStatement = connection.prepareStatement(updateQuery);
	            updateStatement.setString(1, title);
	            updateStatement.setString(2, content);
	            updateStatement.setInt(3, topic_id);
	            updateStatement.setInt(4, Integer.parseInt(ID_CLICKED)); 
	            rowsAffected = updateStatement.executeUpdate();
	            if (rowsAffected > 0) {
	                System.out.println("SERVER STATUS: Update successful (IN MOFIFY ARTICLE:ID:" + ID_CLICKED + ") " + rowsAffected + " rows affected.");
	                
	                /// Now we are going to remove the alert and the cause, if exists ...
	                removeCause(Integer.parseInt(ID_CLICKED));
	                updateAlert(Integer.parseInt(ID_CLICKED));
	                
	                return Response.ok("MODIFICATION_DONE_SUCCESFULLY:ID_MODIFIED:" + ID_CLICKED).build();
	            } else {
	                System.out.println("SERVER STATUS: Update failed (IN MOFIFY ARTICLE:ID: " + ID_CLICKED + ") No rows affected.");
	                return Response.serverError().build();
	            }
	            //
	        } catch(SQLException e) {
	            e.printStackTrace();
	            return Response.serverError().build();
	        } finally {
		        try {
		            if (selectTopicStatement != null) {
		            	selectTopicStatement.close();
		            }
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
	
	private String getCAUSE(int article_id_clicked) {
		String url = "jdbc:mysql://localhost:3306/news_db";
	    String username_DB = "root";
	    String passwd = "kolos2020";
	    
	    Connection connection = null;
	    PreparedStatement selectStatement = null;
	    
	    String selectQuery = "SELECT CAUSE FROM alerts_causes WHERE ARTICLE_ID = ?;";
	    
	    try { 
	    	connection = DriverManager.getConnection(url, username_DB, passwd);
	    	System.out.println("\nSERVER STATUS: Connected to the database...");
	    	selectStatement = connection.prepareStatement(selectQuery);
	    	selectStatement.setInt(1, article_id_clicked);
	      
	    	ResultSet resultSet = selectStatement.executeQuery();

	    	String CAUSE = "";
	        while(resultSet.next()) {
	        	CAUSE = resultSet.getString("CAUSE");
	        }
	        return CAUSE;
	    }catch(SQLException e) {
	    	e.printStackTrace();
	    	return "";
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
	
	/// Here we remove the alert and also the cause ...
	private void updateAlert(int id) {
		String url = "jdbc:mysql://localhost:3306/news_db";
	    String username_DB = "root";
	    String passwd = "kolos2020";
	    
	    int rowsAffected;
	    
	    Connection connection = null;
	    PreparedStatement updateStatement = null;
	    
	    try {
	    	connection = DriverManager.getConnection(url, username_DB, passwd);
			System.out.println("\nSERVER STATUS: Connected to the database...");
			
		    String updateQuery = "UPDATE ARTICLES SET alert = 0 WHERE ID = ?;";
		    updateStatement = connection.prepareStatement(updateQuery);
		    updateStatement.setInt(1, Integer.parseInt(ID_CLICKED));
	        rowsAffected = updateStatement.executeUpdate();
	        if (rowsAffected > 0) {
                System.out.println("SERVER STATUS: Update successful (IN MODIFY (updateAlert) ARTICLE:ID:" + ID_CLICKED + ") " + rowsAffected + " rows affected.");
            } else {
                System.out.println("SERVER STATUS: Update failed (IN MODIFY (updateAlert) ARTICLE:ID: " + ID_CLICKED + ") No rows affected.");
            }
	    } catch(SQLException e) {
	    	e.printStackTrace();
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
	private void removeCause(int id) {
		// First we remove the cause from the table alerts_cause based on the ID of the article the modify pressed
		String url = "jdbc:mysql://localhost:3306/news_db";
	    String username_DB = "root";
	    String passwd = "kolos2020";
	    
	    int rowsAffected;
	    
	    Connection connection = null;
	    PreparedStatement deleteStatement = null;
	    
	    try {
	    	connection = DriverManager.getConnection(url, username_DB, passwd);
			System.out.println("\nSERVER STATUS: Connected to the database...");
			
		    String deleteQuery = "DELETE FROM ALERTS_CAUSES WHERE ARTICLE_ID = ?;";
		    deleteStatement = connection.prepareStatement(deleteQuery);
		    deleteStatement.setInt(1, Integer.parseInt(ID_CLICKED));
	        rowsAffected = deleteStatement.executeUpdate();
	        if (rowsAffected > 0) {
                System.out.println("SERVER STATUS: Delete successful (IN MODIFY (removeAlert) ARTICLE:ID:" + ID_CLICKED + ") " + rowsAffected + " rows affected.");
            } else {
                System.out.println("SERVER STATUS: Delete failed (IN MODIFY (removeAlert) ARTICLE:ID: " + ID_CLICKED + ") No rows affected.");
            }
	    } catch(SQLException e) {
	    	e.printStackTrace();
	    } finally {
	        try {
	            if (deleteStatement != null) {
	            	deleteStatement.close();
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
	
	/* NOTE: In this function we will get all the IDs of the articles that the user has create ... 
	 * NOTE: WE ONLY RETURN THE ARTICLES THAT HAVE STATE == 1 */
	private ArrayList<String> getAllArticleIDS(String username, String role) {
		String url = "jdbc:mysql://localhost:3306/news_db";
	    String username_DB = "root";
	    String passwd = "kolos2020";
	    
	    Connection connection = null;
	    PreparedStatement selectStatement = null;
	    
	    String selectQuery = "SELECT ID FROM articles WHERE CREATOR_USERNAME = ? AND STATE_ID = 1;";
	    
	    ArrayList<String> ARTICLES_IDs = new ArrayList<>();
	    
	    try {
	    	connection = DriverManager.getConnection(url, username_DB, passwd);
	        System.out.println("\nSERVER STATUS: Connected to the database...");
	    	
	    	if(role.equals("JOURNALIST")) {
		    	selectQuery = "SELECT ID FROM articles WHERE CREATOR_USERNAME = ? AND STATE_ID = 1;";
		    	selectStatement = connection.prepareStatement(selectQuery);
		    	selectStatement.setString(1, username);
		    } else { // if the user is CURATOR, all the articles will be displayed ... 
		    	selectQuery = "SELECT ID FROM articles WHERE STATE_ID = 1;";
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
