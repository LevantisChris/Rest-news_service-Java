package net.topics.ws;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import net.htmlhandler.ws.HtmlHandler;
import net.topics.ws.manage_topics.Topic;

@Path("/auth/not_auth_user/displayAll_topic")
public class DisplayAllTopicsResource_not_auth {

	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	public Response handleKeyPhrasesAuthUserArticles(@QueryParam("username") String username, @QueryParam("role") String role) {
		System.out.println("SERVER STATUS: DISPLAY ALL TOPICS (not_auth) CALLED BY USERNAME == " + username + " - ROLE == " + role);
		int ROLE_ID;
		if(role.equals("VISITOR")) {
			ROLE_ID = 1;
			return Response.status(Response.Status.OK)
	                .entity(HtmlHandler.getStartOptionsHTML_topics("null", role))
	                .type(MediaType.TEXT_HTML)
	                .build();
		} else { // if someone else get here we have a problem ...
			return Response.serverError().build();
		}
	}
	
	@POST
	@Path("/displayAll")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response handleSort(
		    @FormParam("sortByName") boolean sortByName,
		    @FormParam("name") String name,
		    @FormParam("role") String role
	) {
		System.out.println("PRINT BY NAME --> " + name);
		System.out.println("PRINT BY role --> " + role);
	    if(sortByName == false) {
	    	return Response.ok("CLICK_ONLY_ONE_CHECKBOX").build();
	    }

	    // Process the selected values
	    if (sortByName) {
	    	System.out.println("SERVER STATUS: Sort by name, Clicked");
	    	
	    	ArrayList<Topic> DATE_GET_TOPICS = getTopicsAtStart("sortByName", name, role);
	    	
	    	printTopicsGet(DATE_GET_TOPICS);
	    	return Response.status(Response.Status.OK)
	                .entity(HtmlHandler.getArticlesFromSEARCH_ALL_TOPICS_auth(name, role, DATE_GET_TOPICS))
	                .type(MediaType.TEXT_HTML)
	                .build();
	    	
	    } else {
	    	 return Response.ok("TRY_AGAIN").build();
	    }
	}
	@POST
	@Path("/filAp")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response handleFilters(String JSON) {
		
		System.out.println("SERVER STTUS: IN handleFilters (not_auth_user) in TOPICS JSON RECEIVED --> " + JSON);
		String clickedByName = null;
		String role = null;
		String startDate = null;
		String endDate = null;
		ArrayList<String> topics = new ArrayList<>();
		
		try {
	        
	    	JSONObject jsonObjectDecode = (JSONObject) JSONValue.parse(JSON);
	    	clickedByName = (String) jsonObjectDecode.get("clickedByName");
	    	role = (String) jsonObjectDecode.get("role");
	    	startDate = (String) jsonObjectDecode.get("startDate");
	    	endDate = (String) jsonObjectDecode.get("endDate");
	    	
	    	JSONArray topicsIdsArray = (JSONArray) jsonObjectDecode.get("topics");
	    	if (topicsIdsArray != null) {
	    	    for (int i = 0; i < topicsIdsArray.size(); i++) { 	
	    	        topics.add(topicsIdsArray.get(i).toString());
	    	    }
	    	}	
	    	System.out.println("---------------------------------------------------------------------------------------------------------------------");
			System.out.println("SERVER STATUS: Filters in Display all Topics (auth) PRESSED BY //" + clickedByName + "// and role //" + role + "//");
			System.out.println("SERVER STATUS: startDate: " + startDate);
			System.out.println("SERVER STATUS: endDate: " + endDate);	
			System.out.println("SERVER STATUS: topics-list: " + topics);
			System.out.println("---------------------------------------------------------------------------------------------------------------------");

			/* Here we extract the numbers (IDs) */
			topics = fixArrayList(topics);
			
			if(startDate.isEmpty() && !endDate.isEmpty()) {
				return Response.ok("ADD_START_DATE").build(); 
			} else if(!startDate.isEmpty() && endDate.isEmpty()) {
				return Response.ok("ADD_END_DATE").build(); 
			} else if(!startDate.isEmpty() && !endDate.isEmpty()){ // the user has add only the two //dates//
				ArrayList<Topic> filtered_topics = filterArrayWithDate(topics, startDate, endDate);
				return Response.status(Response.Status.OK)
		                .entity(HtmlHandler.getArticlesFromSEARCH_ALL_TOPICS_auth(clickedByName, role, filtered_topics))
		                .type(MediaType.TEXT_HTML)
		                .build();
			} else {
				return Response.ok("NOT_FILTERS_ADDED").build(); 
			}
		} catch (Exception e) {
	        System.out.println("SERVER STATUS: --ERROR-- in parsing JSON");
	        e.printStackTrace();
	        return Response.serverError().build();
	    }
	}

	/*-------------------------------------------------------------------------------------------------*/
	
	private ArrayList<Topic> filterArrayWithDate(ArrayList<String> topics, String startDate, String endDate) {
		ArrayList<Topic> filtered_topics = new ArrayList<>();
		
		String url = "jdbc:mysql://localhost:3306/news_db";
	    String username_DB = "root";
	    String passwd = "kolos2020";
	    
	    String selectQuery;
	    Connection connection = null;
	    PreparedStatement selectStatement = null;
	    ResultSet resultSet = null;
	    
	    selectQuery = "SELECT ID, TITLE, DATE_CREATION, STATE_ID, CREATOR_USERNAME, PARENT_TOPIC_ID FROM news_db.topic WHERE ID = ? AND DATE_CREATION >= ? AND DATE_CREATION <= ?;";
	    
	    try {
	    	
	    	connection = DriverManager.getConnection(url, username_DB, passwd);
	        System.out.println("\nSERVER STATUS: Connected to the database...");
		    
	        for(int i = 0;i < topics.size();i++) {
	        	selectStatement = connection.prepareStatement(selectQuery);
		    	selectStatement.setInt(1, Integer.parseInt(topics.get(i)));
		    	selectStatement.setString(2, startDate);
		    	selectStatement.setString(3, endDate);
		    	resultSet = selectStatement.executeQuery();
		    	if(resultSet != null) {
			        while(resultSet.next()) {
			        	int topic_Id = resultSet.getInt("ID");
			        	String topic_Title = resultSet.getString("TITLE");
			        	Date topic_DateCreation = resultSet.getDate("DATE_CREATION");
			        	int topic_StateId = resultSet.getInt("STATE_ID");
			        	String topic_CreatorUsername = resultSet.getString("CREATOR_USERNAME");
			        	int topic_Parent_topic_id = resultSet.getInt("PARENT_TOPIC_ID");
			        	filtered_topics.add(new Topic(topic_Id, topic_Title, 
			        			topic_DateCreation, topic_StateId, 
			        			topic_CreatorUsername ,topic_Parent_topic_id));
			        }
		    	}
	        }
		    	return filtered_topics;
	    } catch(SQLException e) {
	    	System.out.println("SERVER STATUS: --ERROR-- IN THE filterArrayWithDate");
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
	
	/* NOTE: The arraylist contains Strings like this {"id":14} we want to get only the number */
	private ArrayList<String> fixArrayList(ArrayList<String> topics) {
		
		for(int i = 0;i < topics.size();i++) {
			String[] topicArray = topics.get(i).toString().split("\\D+");
			String temp = null;
			for (String topic : topicArray) { temp = topic; }
		    topics.remove(i);
			topics.add(i, temp);
		}
		return topics;
	}
	
	private void printTopicsGet(ArrayList<Topic> topics) {
		System.out.println("-----------------------------------------------------------------------------");
		for(int i = 0;i < topics.size();i++) {
			System.out.println("--> ID: " + topics.get(i).getId() + " TITLE: " + topics.get(i).getTitle());
		}
		System.out.println("-----------------------------------------------------------------------------");
	}
	
	/* This func is converting a String that represents a date to Date ... */
	public Date stringToDate(String date_str) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = dateFormat.parse(date_str);
            return date;
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
	
	private ArrayList<Topic> getTopicsAtStart(String CLICKED, String username, String role) {
		ArrayList<Topic> filtered_topics = new ArrayList<>();
		
		String url = "jdbc:mysql://localhost:3306/news_db";
	    String username_DB = "root";
	    String passwd = "kolos2020";
	    
	    String selectQuery;
	    Connection connection = null;
	    PreparedStatement selectStatement = null;
	    ResultSet resultSet;
		
	    if(!CLICKED.equals("sortByName")) 
	    	return null;
	    
	    try {
	    	
	    	connection = DriverManager.getConnection(url, username_DB, passwd);
	        System.out.println("\nSERVER STATUS: Connected to the database...");
		    
    		selectQuery = "SELECT ID, TITLE, DATE_CREATION, STATE_ID, CREATOR_USERNAME, PARENT_TOPIC_ID FROM news_db.topic WHERE STATE_ID = 3 ORDER BY TITLE DESC;";
    		
    		    	        
    	    selectStatement = connection.prepareStatement(selectQuery);
    	    resultSet = selectStatement.executeQuery();
    	        
    	    while(resultSet.next()) {
    	        int topic_Id = resultSet.getInt("ID");
		        String topic_Title = resultSet.getString("TITLE");
		        Date topic_DateCreation = resultSet.getDate("DATE_CREATION");
		        int topic_StateId = resultSet.getInt("STATE_ID");
		        String topic_CreatorUsername = resultSet.getString("CREATOR_USERNAME");
		        int topic_Parent_topic_id = resultSet.getInt("PARENT_TOPIC_ID");
		        filtered_topics.add(new Topic(topic_Id, topic_Title, 
		        		topic_DateCreation, topic_StateId, 
		        		topic_CreatorUsername ,topic_Parent_topic_id));
    	        }
    		    return filtered_topics;
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

