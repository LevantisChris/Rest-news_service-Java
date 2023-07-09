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
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import net.comments.ws.manage_comments.Comments;
import net.htmlhandler.ws.HtmlHandler;

/// This function is only for a Curator

@Path("/auth/auth_user/modify_comment")
public class ModifyCommentResource {

	@GET
	public Response handleDisplayAllArticles(@QueryParam("username") String username, @QueryParam("role") String role) {
		System.out.println("SERVER STATUS: A user with username //" + username + "// and role //" + role + "//");
		if(role.equals("CURATOR")) {
			
			ArrayList<Comments> COMMENTS_DATA = getAllComments();
			
			return Response.status(Response.Status.OK)
	                .entity(HtmlHandler.getCommentsFromMODIFY_COMMENTS_auth(COMMENTS_DATA, username))
	                .type(MediaType.TEXT_HTML)
	                .build();
		} else {
			return Response.serverError().build();
		}
	}
	
	@POST
	@Path("/modify")
    @Consumes(MediaType.APPLICATION_JSON)
	public Response handleModifyButton(String json) {
		System.out.println("SERVER STATUS: THE JSON WE GET FROM CLIENT IS " + json);

		Long commentId = null;
	    String newContents = null;

	    /* Extract from the JSON the contents */
	    try {
	        JSONObject jsonObjectDecode = (JSONObject) JSONValue.parse(json);
	        commentId = (Long) jsonObjectDecode.get("comment_id");
	        newContents = (String) jsonObjectDecode.get("new_contents");
	    } catch (Exception e) {
	        System.out.println("SERVER STATUS: --ERROR-- in parsing JSON");
	        e.printStackTrace();
	        return Response.serverError().build();
	    }
		
		System.out.println("SERVER STATUS: STRINGD EXTRACTED ARE " + commentId + " AND " + newContents);
		
		if(updateCommentContent(commentId.intValue(), newContents) == true) {
			return Response.ok("MODIFICATION_DONE_CORRECTLY").build();
		} else {
		    return Response.serverError().build();
		}
	}
	
	/* ------------------------------------------------------------------------------------------------------------------ */
	
	private ArrayList<Comments> getAllComments() {
		String url = "jdbc:mysql://localhost:3306/news_db";
	    String username_DB = "root";
	    String passwd = "kolos2020";
	    
	    ArrayList<Comments> COMMENTS_DATA = new ArrayList<>();
	    
	    Connection connection = null;
	    PreparedStatement selectStatement = null;
	    
	    String selectQuery = "SELECT * FROM news_db.comments WHERE STATE_ID = 1;"; // only the comments that are in the CREATED state can be mofified (STATE_ID: 1)
	    
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
	
	private boolean updateCommentContent(int comment_id, String new_content) {
		String url = "jdbc:mysql://localhost:3306/news_db";
	    String username_DB = "root";
	    String passwd = "kolos2020";
	    
	    Connection connection = null;
	    PreparedStatement updateStatement = null;
	    int rowsAffected;
	    
	    String updateQuery = "UPDATE news_db.comments SET CONTENT = ? WHERE ID = ?;";
	    
	    try {
	    	
	    	connection = DriverManager.getConnection(url, username_DB, passwd);
			System.out.println("\nSERVER STATUS: Connected to the database...");
			updateStatement = connection.prepareStatement(updateQuery);
		    updateStatement.setString(1, new_content);
		    updateStatement.setInt(2, comment_id);
	        rowsAffected = updateStatement.executeUpdate();
	        if (rowsAffected > 0) {
                System.out.println("SERVER STATUS: Update successful (IN MODIFY (updateAlert) COMMENT:ID:" + comment_id + ") " + rowsAffected + " rows affected.");
                return true;
            } else {
                System.out.println("SERVER STATUS: Update failed (IN MODIFY (updateAlert) COMMENT:ID: " + comment_id + ") No rows affected.");
                return false;
            }
	    } catch(SQLException e) {
	    	System.out.println("SERVER STATUS: --ERROR--occured in updateCommentContent");
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
