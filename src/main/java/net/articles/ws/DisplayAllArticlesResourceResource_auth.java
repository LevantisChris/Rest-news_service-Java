package net.articles.ws;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.google.protobuf.TextFormat.ParseException;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import net.articles.ws.manage_articles.Article;
import net.exceptions.ws.NotIdentifiedRole;
import net.htmlhandler.ws.HtmlHandler;

@Path("/auth/auth_user/displayAll_article")
public class DisplayAllArticlesResourceResource_auth {
	
	private static ArrayList<Article> DATE_GET;
	
	@GET
	public Response handleKeyPhrasesAuthUserArticles(@QueryParam("username") String username, @QueryParam("role") String role) {
		System.out.println("SERVER STATUS: DISPLAY ALL ARTICLE CALLED BY USERNAME == " + username + " - ROLE == " + role);
		int ROLE_ID;
		try {
			if(role.equals("VISITOR")) { // if a visitor gets here we have a problem ...
				ROLE_ID = 1;
				return Response.serverError().build();
			} else if(role.equals("JOURNALIST")) {
				ROLE_ID = 2;
				
				return Response.status(Response.Status.OK)
		                .entity(HtmlHandler.getStartOptionsHTML(username, role))
		                .type(MediaType.TEXT_HTML)
		                .build();
				
			} else if(role.equals("CURATOR")){
				ROLE_ID = 3;

				return Response.status(Response.Status.OK)
		                .entity(HtmlHandler.getStartOptionsHTML(username, role))
		                .type(MediaType.TEXT_HTML)
		                .build();
				
			} else { throw new NotIdentifiedRole("ERROR: The role could not be identified.");}
		} catch(NotIdentifiedRole e) {
			System.out.print(e.getMessage());
			return Response.ok(e.getMessage()).build();
		}
	}
	
	/* NOTE: Here we handle the checkBox clicks */
	@POST
	@Path("/displayAll")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response handleSort(
			@FormParam("sortByState") boolean sortByState,
		    @FormParam("sortByDate") boolean sortByDate,
		    @FormParam("name") String name,
		    @FormParam("role") String role
	) {
		System.out.println("PRINT BY NAME --> " + name);
		System.out.println("PRINT BY role --> " + role);
	    if(sortByState == true && sortByDate == true) {
	    	return Response.ok("CLICK_ONLY_ONE_CHECKBOX").build();
	    }

	    // Process the selected values
	    if (sortByState) {
	    	System.out.println("SERVER STATUS: Sort by state, Clicked");
	    	
	    	DATE_GET = getArticlesAtStart("sortByState", name, role);
	    	printDateGet(DATE_GET);
	    	return Response.status(Response.Status.OK)
	                .entity(HtmlHandler.getArticlesFromSEARCH_ALL_ARTICLES_auth(DATE_GET))
	                .type(MediaType.TEXT_HTML)
	                .build();
	    } else if (sortByDate) {
	    	System.out.println("SERVER STATUS: Sort by date, Clicked");
	    	DATE_GET = getArticlesAtStart("sortByDate", name, role);
	    	printDateGet(DATE_GET);
	    	return Response.status(Response.Status.OK)
	                .entity(HtmlHandler.getArticlesFromSEARCH_ALL_ARTICLES_auth(DATE_GET))
	                .type(MediaType.TEXT_HTML)
	                .build();
	    } else {
	    	 return Response.ok("TRY_AGAIN").build();
	    }
	}

	/* NOTE: Here we handle the submit button that is in the filters */
	@POST
	@Path("/filAp")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response handleFilters(@FormParam("state") String state, 
								  @FormParam("startDate") String startDate, 
								  @FormParam("endDate") String endDate) {
		
		System.out.println("SERVER STATUS: Filters in Display all Articles (auth) PRESSED");
		System.out.println("SERVER STATUS: state: " + state);
		System.out.println("SERVER STATUS: startDate: " + startDate);
		System.out.println("SERVER STATUS: endDate: " + endDate);
		System.out.println("SERVER STATUS: ARRAY: --> " + DATE_GET);
		
		if(startDate.isEmpty() && !endDate.isEmpty()) {
			return Response.ok("ADD_START_DATE").build(); 
		} else if(!startDate.isEmpty() && endDate.isEmpty()) {
			return Response.ok("ADD_END_DATE").build(); 
		} else if(!state.isEmpty() && startDate.isEmpty() && endDate.isEmpty()) { // if the user has only add the //state//
			ArrayList<Article> filteredArray = filterByState(DATE_GET, Integer.parseInt(state));
			return Response.status(Response.Status.OK)
	                .entity(HtmlHandler.getArticlesFromSEARCH_ALL_ARTICLES_auth(filteredArray))
	                .type(MediaType.TEXT_HTML)
	                .build();
		} else if(state.isEmpty() && !startDate.isEmpty() && !endDate.isEmpty()){ // the user has add only the two //dates//
			ArrayList<Article> filteredArray = filterByDate(DATE_GET, stringToDate(startDate), stringToDate(endDate));
			return Response.status(Response.Status.OK)
	                .entity(HtmlHandler.getArticlesFromSEARCH_ALL_ARTICLES_auth(filteredArray))
	                .type(MediaType.TEXT_HTML)
	                .build(); 
		} else if(!state.isEmpty() && !startDate.isEmpty() && !endDate.isEmpty()) {
			ArrayList<Article> filteredArray = filterByStateAndDate(DATE_GET, Integer.parseInt(state), stringToDate(startDate), stringToDate(endDate));
			return Response.status(Response.Status.OK)
	                .entity(HtmlHandler.getArticlesFromSEARCH_ALL_ARTICLES_auth(filteredArray))
	                .type(MediaType.TEXT_HTML)
	                .build(); 
		} else {
			return Response.ok("NOT_FILTERS_ADDED").build(); 
		}
	}
	
	/*----------------------------------------------------------------------------------------------------------------------------------------------*/
	
	/* NOTE: In this function we will get the articles from the database. This articles will be displayed in the --START--, that means when the 
	 * user clicks the Link //Search all the articles// from the control center, without he add on of some filters.
	 * 
	 * WARNING: This filters will have the following !!DEFAULT SORT FILTERS!!. They will be sorted base on the DATE_CREATION and the STATE_ID. */
	private ArrayList<Article> getArticlesAtStart(String CLICKED, String username, String role) {
		ArrayList<Article> temp_list = new ArrayList<>();
		
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
	        		selectQuery = "SELECT ID, TITLE, CONTENT, DATE_CREATION, STATE_ID, CREATOR_USERNAME FROM articles WHERE STATE_ID = 4 OR CREATOR_USERNAME = ? ORDER BY STATE_ID;";
	        	else 
	        		selectQuery = "SELECT ID, TITLE, CONTENT, DATE_CREATION, STATE_ID, CREATOR_USERNAME FROM articles WHERE STATE_ID = 4 OR CREATOR_USERNAME = ? ORDER BY DATE_CREATION;";
	        	
	        	selectStatement = connection.prepareStatement(selectQuery);
		    	selectStatement.setString(1, username);
		    	resultSet = selectStatement.executeQuery();
	        } else { // is a Curator	
	        	if(CLICKED.equals("sortByState"))
		    		selectQuery = "SELECT ID, TITLE, CONTENT, DATE_CREATION, STATE_ID, CREATOR_USERNAME FROM ARTICLES ORDER BY STATE_ID;"; // can see everything
	        	else 
		    		selectQuery = "SELECT ID, TITLE, CONTENT, DATE_CREATION, STATE_ID, CREATOR_USERNAME FROM ARTICLES ORDER BY DATE_CREATION;"; // can see everything
	        	selectStatement = connection.prepareStatement(selectQuery);
		    	resultSet = selectStatement.executeQuery();
	        }

	        while(resultSet.next()) {
	        	int article_Id = resultSet.getInt("ID");
	        	String article_Title = resultSet.getString("TITLE");
	        	String article_Content = resultSet.getString("CONTENT");
	        	Date article_DateCreation = resultSet.getDate("DATE_CREATION");
	        	int article_StateId = resultSet.getInt("STATE_ID");
	        	String article_CreatorUsername = resultSet.getString("CREATOR_USERNAME");
	        	temp_list.add(new Article(article_Id, article_Title, 
	        			article_Content, article_DateCreation, 
	        			article_StateId ,article_CreatorUsername));
	        }
		    return temp_list;
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
	
	private void printDateGet(ArrayList<Article> DATA_GET) {
		System.out.println("----------------------------------------------------------------------------------------------");
		for(int i = 0;i < DATA_GET.size();i++) {
			System.out.println("ARTICLE: ID == " + DATA_GET.get(i).getId() + " TITLE == " + DATA_GET.get(i).getTitle());
		}
		System.out.println("----------------------------------------------------------------------------------------------");
	}
	
	private ArrayList<Article> filterByState(ArrayList<Article> arrayToFilter, int state_filter) {
		ArrayList<Article> filteredArray = new ArrayList<>();
		for(int i = 0;i < arrayToFilter.size();i++) {
			if(arrayToFilter.get(i).getState_id() == state_filter) {
				filteredArray.add(arrayToFilter.get(i));
			}
		}
		return filteredArray;
	}
	private ArrayList<Article> filterByDate(ArrayList<Article> arrayToFilter, Date date_start_filter, Date date_end_filter) {
		ArrayList<Article> filteredArray = new ArrayList<>();
		for(int i = 0;i < arrayToFilter.size();i++) {
			if(arrayToFilter.get(i).getDate_creation().compareTo(date_end_filter) <= 0 && arrayToFilter.get(i).getDate_creation().compareTo(date_start_filter) >= 0) {
				filteredArray.add(arrayToFilter.get(i));
			}
		}
		return filteredArray;
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
	private ArrayList<Article> filterByStateAndDate(ArrayList<Article> arrayToFilter, int state_filter, Date date_start_filter, Date date_end_filter) {
		ArrayList<Article> filteredArray = new ArrayList<>();
		for(int i = 0;i < arrayToFilter.size();i++) {
			if(arrayToFilter.get(i).getDate_creation().compareTo(date_end_filter) <= 0 
					&& arrayToFilter.get(i).getDate_creation().compareTo(date_start_filter) >= 0 
					&& arrayToFilter.get(i).getState_id() == state_filter) {
				filteredArray.add(arrayToFilter.get(i));
			}
		}
		return filteredArray;
	}
}