package net.topics.ws;

import net.topics.ws.manage_topics.Topic;


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
import jakarta.ws.rs.CookieParam;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import net.exceptions.ws.NotIdentifiedRole;
import net.htmlhandler.ws.HtmlHandler;
import net.sessionExtractor.ws.SessionExtractor;

@Path("/auth/auth_user/displayAll_topic")
public class DisplayAllTopicsResource_auth {

	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	public Response handleKeyPhrasesAuthUserTopics(@CookieParam("session_id") String sessionId) {
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
		
		System.out.println("SERVER STATUS: DISPLAY ALL TOPICS (auth_user) CALLED BY USERNAME == " + username + " - ROLE == " + role);
		if(role == null || role.isEmpty()) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		int ROLE_ID;
		try {
			if(role.equals("VISITOR")) { // if a visitor gets here we have a problem ...
				ROLE_ID = 1;
				return Response.serverError().build();
			} else if(role.equals("JOURNALIST")) {
				ROLE_ID = 2;
				
				return Response.status(Response.Status.OK)
		                .entity(HtmlHandler.getStartOptionsHTML_topics(username, role))
		                .type(MediaType.TEXT_HTML)
		                .build();
				
			} else if(role.equals("CURATOR")){
				ROLE_ID = 3;

				return Response.status(Response.Status.OK)
		                .entity(HtmlHandler.getStartOptionsHTML_topics(username, role))
		                .type(MediaType.TEXT_HTML)
		                .build();
				
			} else { throw new NotIdentifiedRole("ERROR: The role could not be identified.");}
		} catch(NotIdentifiedRole e) {
			System.out.print(e.getMessage());
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity("ROLE_NOT_IDENTIFIED").build();
		}
	}
	
	@POST
	@Path("/displayAll")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response handleSort(
			@CookieParam("session_id") String sessionId,
			@FormParam("sortByState") boolean sortByState,
		    @FormParam("sortByName") boolean sortByName
	) {
		if(sessionId == null || sessionId.isBlank()) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
		
		///
		/* Get the user that has the session and also the role */
		SessionExtractor sessionExtractor = new SessionExtractor();
		if(sessionExtractor.checkIfSessionExists(sessionId) == false) {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
		String name = sessionExtractor.getUsernameFromSession(sessionId);
		String role = sessionExtractor.getRoleFromSession(sessionId);
		System.out.println("SERVER STATUS: SESSION_ID NUM: " + sessionId +" USERNAME extracted is --> " + name + " and ROLE extracted is " + role);
		///
		
	    if(sortByState == false && sortByName == false) {
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity("CLICK_ONLY_ONE_CHECKBOX").build();
	    }

	    // Process the selected values
	    if (sortByState) {
	    	System.out.println("SERVER STATUS: Sort by state, Clicked");
	    	
	    	ArrayList<Topic> DATE_GET_TOPICS = getTopicsAtStart("sortByState", name, role);
	    	
	    	printTopicsGet(DATE_GET_TOPICS);
	    	
	    	return Response.status(Response.Status.OK)
	                .entity(HtmlHandler.getArticlesFromSEARCH_ALL_TOPICS_auth(name, role, DATE_GET_TOPICS))
	                .type(MediaType.TEXT_HTML)
	                .build();
	    	
	    } else if (sortByName) {
	    	System.out.println("SERVER STATUS: Sort by name, Clicked");
	    	
	    	ArrayList<Topic> DATE_GET_TOPICS = getTopicsAtStart("sortByName", name, role);
	    	
	    	printTopicsGet(DATE_GET_TOPICS);
	    	return Response.status(Response.Status.OK)
	                .entity(HtmlHandler.getArticlesFromSEARCH_ALL_TOPICS_auth(name, role, DATE_GET_TOPICS))
	                .type(MediaType.TEXT_HTML)
	                .build();
	    	
	    } else {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("TRY_AGAIN").build();
	    }
	}
	
	@POST
	@Path("/filAp")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response handleFilters(@CookieParam("session_id") String sessionId, String JSON) {
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
		System.out.println("SERVER STATUS: SESSION_ID NUM: " + sessionId +" USERNAME extracted is --> " + clickedByName + " and ROLE extracted is " + role);
		///
		
		if(JSON == null) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		
		System.out.println("SERVER STTUS: IN handleFilters (auth_user) in TOPICS JSON RECEIVED --> " + JSON);
		String state = null;
		String startDate = null;
		String endDate = null;
		ArrayList<String> topics = new ArrayList<>();
		
		try {
	        
	    	JSONObject jsonObjectDecode = (JSONObject) JSONValue.parse(JSON);
	    	state = (String) jsonObjectDecode.get("state");
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
			System.out.println("SERVER STATUS: state: " + state);
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
			} else if(!state.isEmpty() && startDate.isEmpty() && endDate.isEmpty()) { // if the user has only add the //state//
				ArrayList<Topic> filtered_topics = filterArrayWithState(topics, Integer.parseInt(state));
				return Response.status(Response.Status.OK)
		                .entity(HtmlHandler.getArticlesFromSEARCH_ALL_TOPICS_auth(clickedByName, role, filtered_topics))
		                .type(MediaType.TEXT_HTML)
		                .build();
			} else if(state.isEmpty() && !startDate.isEmpty() && !endDate.isEmpty()){ // the user has add only the two //dates//
				ArrayList<Topic> filtered_topics = filterArrayWithDate(topics, startDate, endDate);
				return Response.status(Response.Status.OK)
		                .entity(HtmlHandler.getArticlesFromSEARCH_ALL_TOPICS_auth(clickedByName, role, filtered_topics))
		                .type(MediaType.TEXT_HTML)
		                .build();
			} else if(!state.isEmpty() && !startDate.isEmpty() && !endDate.isEmpty()) {
				ArrayList<Topic> filtered_topics = filterArrayWithStateAndDate(topics, Integer.parseInt(state), startDate, endDate);
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

	private ArrayList<Topic> filterArrayWithStateAndDate(ArrayList<String> topics, int state, String startDate, String endDate) {
		ArrayList<Topic> filtered_topics = new ArrayList<>();
		
		String url = "jdbc:mysql://localhost:3306/news_db";
	    String username_DB = "root";
	    String passwd = "kolos2020";
	    
	    String selectQuery;
	    Connection connection = null;
	    PreparedStatement selectStatement = null;
	    ResultSet resultSet = null;
	    
	    selectQuery = "SELECT ID, TITLE, DATE_CREATION, STATE_ID, CREATOR_USERNAME, PARENT_TOPIC_ID FROM news_db.topic WHERE ID = ? AND STATE_ID = ? AND DATE_CREATION >= ? AND DATE_CREATION <= ?;";
	    
	    try {
	    	
	    	connection = DriverManager.getConnection(url, username_DB, passwd);
	        System.out.println("\nSERVER STATUS: Connected to the database...");
		    
	        for(int i = 0;i < topics.size();i++) {
	        	selectStatement = connection.prepareStatement(selectQuery);
		    	selectStatement.setInt(1, Integer.parseInt(topics.get(i)));
		    	selectStatement.setInt(2, state);
		    	selectStatement.setString(3, startDate);
		    	selectStatement.setString(4, endDate);
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
	    	System.out.println("SERVER STATUS: --ERROR-- IN THE filterArrayWithStateAndDate");
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
	
	/* NOTE: Here we filtered the array based on the STATE the user provided. We don't need to further do a categorization between the roles that called the function
	 * because the list we get is already filtered */
	private ArrayList<Topic> filterArrayWithState(ArrayList<String> topics, int state) {
		ArrayList<Topic> filtered_topics = new ArrayList<>();
		
		String url = "jdbc:mysql://localhost:3306/news_db";
	    String username_DB = "root";
	    String passwd = "kolos2020";
	    
	    String selectQuery;
	    Connection connection = null;
	    PreparedStatement selectStatement = null;
	    ResultSet resultSet = null;
	    
	    selectQuery = "SELECT ID, TITLE, DATE_CREATION, STATE_ID, CREATOR_USERNAME, PARENT_TOPIC_ID FROM news_db.topic WHERE ID = ? AND STATE_ID = ?;";
	    
	    try {
	    	
	    	connection = DriverManager.getConnection(url, username_DB, passwd);
	        System.out.println("\nSERVER STATUS: Connected to the database...");
		    
	        for(int i = 0;i < topics.size();i++) {
	        	selectStatement = connection.prepareStatement(selectQuery);
		    	selectStatement.setInt(1, Integer.parseInt(topics.get(i)));
		    	selectStatement.setInt(2, state);
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
	    	System.out.println("SERVER STATUS: --ERROR-- IN THE filterArrayWithState");
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
		ArrayList<Topic> temp_list = new ArrayList<>();
		
		String url = "jdbc:mysql://localhost:3306/news_db";
	    String username_DB = "root";
	    String passwd = "kolos2020";
	    
	    String selectQuery;
	    Connection connection = null;
	    PreparedStatement selectStatement = null;
	    ResultSet resultSet;
		
	    try {
	    	
	    	connection = DriverManager.getConnection(url, username_DB, passwd);
	        System.out.println("\nSERVER STATUS: Connected to the database...");
		    
	        if(role.equals("JOURNALIST")) { // is JOURNALIST
	        	if(CLICKED.equals("sortByState"))
	        		selectQuery = "SELECT ID, TITLE, DATE_CREATION, STATE_ID, CREATOR_USERNAME, PARENT_TOPIC_ID FROM news_db.topic WHERE STATE_ID = 3 OR CREATOR_USERNAME = ? ORDER BY STATE_ID DESC;";
	        	else 
	        		selectQuery = "SELECT ID, TITLE, DATE_CREATION, STATE_ID, CREATOR_USERNAME, PARENT_TOPIC_ID FROM news_db.topic WHERE STATE_ID = 3 OR CREATOR_USERNAME = ? ORDER BY TITLE DESC;";
	        	
	        	selectStatement = connection.prepareStatement(selectQuery);
		    	selectStatement.setString(1, username);
		    	resultSet = selectStatement.executeQuery();
	        } else { // is a Curator	
	        	if(CLICKED.equals("sortByState"))
	        		selectQuery = "SELECT ID, TITLE, DATE_CREATION, STATE_ID, CREATOR_USERNAME, PARENT_TOPIC_ID FROM news_db.topic ORDER BY STATE_ID DESC;"; // can see everything
	        	else 
	        		selectQuery = "SELECT ID, TITLE, DATE_CREATION, STATE_ID, CREATOR_USERNAME, PARENT_TOPIC_ID FROM news_db.topic ORDER BY TITLE DESC;"; // can see everything
	        	selectStatement = connection.prepareStatement(selectQuery);
		    	resultSet = selectStatement.executeQuery();
	        }
	    	
	        while(resultSet.next()) {
	        	int topic_Id = resultSet.getInt("ID");
	        	String topic_Title = resultSet.getString("TITLE");
	        	Date topic_DateCreation = resultSet.getDate("DATE_CREATION");
	        	int topic_StateId = resultSet.getInt("STATE_ID");
	        	String topic_CreatorUsername = resultSet.getString("CREATOR_USERNAME");
	        	int topic_Parent_topic_id = resultSet.getInt("PARENT_TOPIC_ID");
	        	temp_list.add(new Topic(topic_Id, topic_Title, 
	        			topic_DateCreation, topic_StateId, 
	        			topic_CreatorUsername ,topic_Parent_topic_id));
	        }
		    return temp_list;   
	    } catch(SQLException e) {
	    	System.out.println("SERVER STATUS: --ERROR-- IN THE getTopicsAtStart");
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
