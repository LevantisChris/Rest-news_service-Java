package net.topics.ws;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.mysql.cj.jdbc.exceptions.MysqlDataTruncation;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import net.exceptions.ws.NotIdentifiedRole;
import net.htmlhandler.ws.HtmlHandler;

/// This function is available for the Journalist and the Curator

@Path("/auth/auth_user/modify_topic")
public class ModifyTopicResource {
	
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	public Response handleDisplatAllTopics(@QueryParam("username") String username, @QueryParam("role") String role) {
		if(role == null || role.isEmpty()) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		String ID_CLICKED = null;
		int ROLE_ID;
		try {
			if(role.equals("JOURNALIST")) {
				ROLE_ID = 2;
				
				ArrayList<String> TOPICS_IDs = getAllTopicsIDS(username, role);
				
				return Response.status(Response.Status.OK)
                .entity(HtmlHandler.getIDS_MODIFY_TOPIC_HTML(TOPICS_IDs, username, role))
                .type(MediaType.TEXT_HTML)
                .build();
				
			} else if(role.equals("CURATOR")){
				ROLE_ID = 3;
				
				ArrayList<String> TOPICS_IDs = getAllTopicsIDS(username, role);
				
				return Response.status(Response.Status.OK)
		                .entity(HtmlHandler.getIDS_MODIFY_TOPIC_HTML(TOPICS_IDs, username, role))
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
    public Response handlegetTopic(@PathParam("id") String id) {
		System.out.println("SERVER STATUS: ACTION IN MODIFIED TOPIC: ID//" + id + "//");
		if(id == null || id.isEmpty() || id.isBlank()) {
			Response.status(Response.Status.NOT_FOUND)
    		.entity("SELECT_ID")
    		.build();
		}
		
		String TITLE_FROM_DB = getTitleTopic_DB(id);
		if(TITLE_FROM_DB == null) {
			return Response.serverError().build();
		}
		String PARENT_TOPIC_FROM_DB = getParentTopicTopic_DB(id);
		if(PARENT_TOPIC_FROM_DB == null) {
			System.out.println("EINAI NULL ...");
			PARENT_TOPIC_FROM_DB = "Empty";
		}
		
		ArrayList<String> TOPICS_LIST = takeTheAvailableTopics(); 

		return   Response.status(Response.Status.OK)
                .entity(HtmlHandler.getMODIFY_TOPIC_HTML(TOPICS_LIST, TITLE_FROM_DB, PARENT_TOPIC_FROM_DB, Integer.parseInt(id)))
                .type(MediaType.TEXT_HTML)
                .build();
    }
	
	@POST
	@Path("/modify")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response handleModificationOfTopic(String JSON) {
		System.out.println("SERVER STATUS: JSON FOR MODIFICATION IS: " + JSON);
		
		String title = null;
		String parentTopic = null;
		Long topic_id_clicked = null;
		
		try {
	    	JSONObject jsonObjectDecode = (JSONObject) JSONValue.parse(JSON);
	    	title = (String) jsonObjectDecode.get("title");
	    	parentTopic = (String) jsonObjectDecode.get("parentTopic");
	    	topic_id_clicked = (Long) jsonObjectDecode.get("topic_id_clicked");
	    	
	    	System.out.println("-------------------------------------------------------------------");
			System.out.println("SERVER STATUS: STRINGs EXTRACTED ARE "
					+ "\nTitle == " + title
					+ "\nParent topic == " + parentTopic
					+ "\nTopic ID clicked == " + topic_id_clicked);
	    	System.out.println("-------------------------------------------------------------------");

	        
			String MSG = updateTopic(Integer.parseInt(topic_id_clicked.toString()), title, parentTopic);
			return Response.ok(MSG).build();
			
	    } catch (Exception e) {
	        System.out.println("SERVER STATUS: --ERROR-- in parsing JSON");
	        e.printStackTrace();
	        return Response.serverError().build();
	    }
		
	}
	/*--------------------------------------------------------------------------------------------------------------------*/
	
	private String updateTopic(int topic_id_clicked, String title, String parentTopic) {
		String url = "jdbc:mysql://localhost:3306/news_db";
	    String username_DB = "root";
	    String passwd = "kolos2020";
	    
	    Connection connection = null;
	    PreparedStatement selectTopicStatement = null;
	    PreparedStatement updateStatement = null;
	    
	    ResultSet resultSet = null;
	    
	    String selectQuery = "SELECT ID FROM news_db.topic WHERE TITLE = ?"; // this is to find the id of the parent topic (NOTE: the title is UNIQUE)
	    String updateQuery;
	    
	    if(!parentTopic.equals("Empty")) {
	    	
	    	System.out.println("PROTOOOO");
	    	
	    	String PARENT_TOPIC_ID = "";
	    	// Firstly we are going to find the ID of the parent topic
		    try {
		    	
		    	connection = DriverManager.getConnection(url, username_DB, passwd);
				System.out.println("\nSERVER STATUS: Connected to the database...");
				
				selectTopicStatement = connection.prepareStatement(selectQuery);
				selectTopicStatement.setString(1, parentTopic); /// NOTE: parentTopic is not int (ID) but it is the title of the parent topic
				
				resultSet = selectTopicStatement.executeQuery();
				
				while(resultSet.next()) {
					PARENT_TOPIC_ID = resultSet.getString("ID");
		        }
		    } 
		    catch(SQLException e) {
		    	System.out.println("SERVER STATUS: --ERROR-- occured in the updateArticle (1)");
		    	e.printStackTrace();
		    	return "ERROR_IN_MODIFICATION";
		    }
		    
		    updateQuery = "UPDATE news_db.topic SET TITLE = ?, PARENT_TOPIC_ID = ? WHERE ID = ?";
		    
		    // Now we are going to Update the contents of the topic
		    try {
		    	
		    	connection = DriverManager.getConnection(url, username_DB, passwd);
				System.out.println("\nSERVER STATUS: Connected to the database...");
				updateStatement = connection.prepareStatement(updateQuery);
				updateStatement.setString(1, title);
				updateStatement.setInt(2, Integer.parseInt(PARENT_TOPIC_ID));
				updateStatement.setInt(3, topic_id_clicked);
		    	int rowsAffected = updateStatement.executeUpdate();
		    	
		    	if(rowsAffected > 0) {
		    		return "MODIFICATION_DONE_SUCCESFULLY";
		    	} else {
		    		return "ERROR_IN_MODIFICATION";
		    	}
		    } catch(SQLException e) {
		    	e.printStackTrace();
		    	return "ERROR_IN_MODIFICATION";
		    } finally {
		    	if (resultSet != null) {
		            try {
		                resultSet.close();
		            } catch (SQLException e) {
		                e.printStackTrace();
		            }
		        }
		    }
		    
	    } else if (parentTopic != null && parentTopic.equals("Empty")) {
	    	
	    	System.out.println("DEUTERO");

	    	
		    updateQuery = "UPDATE news_db.topic SET TITLE = ?, PARENT_TOPIC_ID = null WHERE ID = ?";
	    	
	    	try {
	    		
	    		connection = DriverManager.getConnection(url, username_DB, passwd);
				System.out.println("\nSERVER STATUS: Connected to the database...");
				updateStatement = connection.prepareStatement(updateQuery);
				updateStatement.setString(1, title);
				updateStatement.setInt(2, topic_id_clicked);
		    	int rowsAffected = updateStatement.executeUpdate();
		    	
		    	if(rowsAffected > 0) {
		    		return "MODIFICATION_DONE_SUCCESFULLY";
		    	} else {
		    		return "ERROR_IN_MODIFICATION";
		    	}
	    		
	    	} catch(SQLException e) {
	    		e.printStackTrace();
		    	return "ERROR_IN_MODIFICATION";
		    } finally {
		        if (selectTopicStatement != null) {
		            try {
		                selectTopicStatement.close();
		            } catch (SQLException e) {
		                e.printStackTrace();
		            }
		        }
		        if (updateStatement != null) {
		            try {
		                updateStatement.close();
		            } catch (SQLException e) {
		                e.printStackTrace();
		            }
		        }
		        if (connection != null) {
		            try {
		                connection.close();
		            } catch (SQLException e) {
		                e.printStackTrace();
		            }
		        }
		    }
	    	
	    } else {
	    	return "ERROR_IN_MODIFICATION";
	    } 
	    
	    
	}
	
	private ArrayList<String> takeTheAvailableTopics() {
	    String url = "jdbc:mysql://localhost:3306/news_db";
	    String username_DB = "root";
	    String passwd = "kolos2020";
	    ArrayList<String> topics = new ArrayList<>();

	    try (Connection connection = DriverManager.getConnection(url, username_DB, passwd);
	         PreparedStatement statement = connection.prepareStatement("SELECT TITLE FROM TOPIC WHERE STATE_ID = 3");
	         ResultSet resultSet = statement.executeQuery()) {

	        while (resultSet.next()) {
	            String topic = resultSet.getString("TITLE");
	            topics.add(topic);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } 
	    return topics;
	}
	
	private String getTitleTopic_DB(String id) {
		String url = "jdbc:mysql://localhost:3306/news_db";
	    String username_DB = "root";
	    String passwd = "kolos2020";
	    
	    Connection connection = null;
	    PreparedStatement selectStatement = null;
	    
	    String TITLE_FROM_DB = null;
	    
	    String selectQuery = "SELECT TITLE FROM news_db.topic WHERE ID = ?;";
	    
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
	private String getParentTopicTopic_DB(String id) {
		String url = "jdbc:mysql://localhost:3306/news_db";
	    String username_DB = "root";
	    String passwd = "kolos2020";
	    
	    Connection connection = null;
	    PreparedStatement selectStatement = null;
	    
	    String ID_PARENT_TOPIC_FROM_DB = null;
	    String PARENT_TOPIC_FROM_DB = null;
	    
	    String selectQuery = "SELECT PARENT_TOPIC_ID FROM news_db.topic WHERE ID = ?;";
	    
	    try {
	    	connection = DriverManager.getConnection(url, username_DB, passwd);
	        System.out.println("\nSERVER STATUS: Connected to the database...");
		    
	        selectStatement = connection.prepareStatement(selectQuery);
	        selectStatement.setString(1, id);
	        ResultSet resultSet = selectStatement.executeQuery();

	        while(resultSet.next()) {
	        	ID_PARENT_TOPIC_FROM_DB = resultSet.getString("PARENT_TOPIC_ID");
	        }
	        
	        if(ID_PARENT_TOPIC_FROM_DB != null) {
	        	String selectQuery2 = "SELECT TITLE FROM news_db.topic WHERE ID = ?;";
	        	
	        	Connection connection2 = null;
	    	    PreparedStatement selectStatement2 = null;
	        	
	        	try {
	        		
	        		connection2 = DriverManager.getConnection(url, username_DB, passwd);
	    	        System.out.println("\nSERVER STATUS: Connected to the database...");
	    		    
	    	        selectStatement2 = connection.prepareStatement(selectQuery2);
	    	        selectStatement2.setString(1, ID_PARENT_TOPIC_FROM_DB);
	    	        ResultSet resultSet2 = selectStatement2.executeQuery();

	    	        while(resultSet2.next()) {
	    	        	PARENT_TOPIC_FROM_DB = resultSet2.getString("TITLE");
	    	        }
	        		
	        	} catch(SQLException e) {
	        		System.out.println("SERVER STATUS: --ERROR-- occured in second select in getParentTopicTopic_DB");
	        		e.printStackTrace();
	        	} finally {
	    	        try {
	    	            if (selectStatement2 != null) {
	    	                selectStatement.close();
	    	            }
	    	            if (connection2 != null && !connection2.isClosed()) {
	    	                connection2.close();
	    	                System.out.println("Disconnected from the database...\n");
	    	            }
	    	        } catch (SQLException e) {
	    	            e.printStackTrace();
	    	        }
	    	    }
	        }
	        
		    return PARENT_TOPIC_FROM_DB;
	    } catch(SQLException e) {
	    	e.printStackTrace();
	    	return PARENT_TOPIC_FROM_DB;
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

	
	private ArrayList<String> getAllTopicsIDS(String username, String role) {
		String url = "jdbc:mysql://localhost:3306/news_db";
	    String username_DB = "root";
	    String passwd = "kolos2020";
	    
	    Connection connection = null;
	    PreparedStatement selectStatement = null;
	    
	    String selectQuery = null;
	    
	    ArrayList<String> TOPICS_IDs = new ArrayList<>();
	    
	    try {
	    	connection = DriverManager.getConnection(url, username_DB, passwd);
	        System.out.println("\nSERVER STATUS: Connected to the database...");
	    	
	    	if(role.equals("JOURNALIST")) {
		    	selectQuery = "SELECT ID FROM news_db.topic WHERE CREATOR_USERNAME = ? AND STATE_ID = 1;";
		    	selectStatement = connection.prepareStatement(selectQuery);
		    	selectStatement.setString(1, username);
		    } else { // if the user is CURATOR, all the topics will can be modified ... 
		    	selectQuery = "SELECT ID FROM news_db.topic WHERE STATE_ID = 1;";
		    	selectStatement = connection.prepareStatement(selectQuery);
		    }
	    	
	        ResultSet resultSet = selectStatement.executeQuery();

	        while(resultSet.next()) {
	        	int articleId = resultSet.getInt("ID");
	        	TOPICS_IDs.add(String.valueOf(articleId));
	        }
		    return TOPICS_IDs;
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
