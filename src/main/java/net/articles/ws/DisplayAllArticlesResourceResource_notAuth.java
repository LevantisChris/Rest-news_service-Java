package net.articles.ws;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import net.articles.ws.manage_articles.Article;
import net.comments.ws.manage_comments.Comments;
import net.htmlhandler.ws.HtmlHandler;

@Path("/auth/not_auth_user/displayAll_article")
public class DisplayAllArticlesResourceResource_notAuth {

	private static ArrayList<Article> DATE_GET_ARTICLES;
	private static ArrayList<Comments> DATE_GET_COMMENTS;
	
	@GET
	public Response handleKeyPhrasesNotAuthUserArticles(@QueryParam("role") String role) {
		System.out.println("SERVER STATUS: DISPLAY ALL ARTICLE CALLED BY USERNAME == " + "--NULL--" + " - ROLE == " + role);
		int ROLE_ID;
		if(role.equals("VISITOR")) {
			ROLE_ID = 1;
			
			return Response.status(Response.Status.OK)
	                .entity(HtmlHandler.getStartOptionsHTML("null", role))
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
		    @FormParam("sortByDate") boolean sortByDate,
		    @FormParam("name") String name,
		    @FormParam("role") String role
	) {
		System.out.println("PRINT BY NAME --> " + name);
		System.out.println("PRINT BY role --> " + role);
	    if(sortByDate == false) {
	    	return Response.ok("CLICK_THE_CHECKBOX").build();
	    }

	    // Process the selected values
	    if (sortByDate) {
	    	System.out.println("SERVER STATUS: Sort by date, Clicked");
	    	DATE_GET_ARTICLES = getArticlesAtStart("sortByDate", name, role);
	    	DATE_GET_COMMENTS = getCommentsAtStart();
	    	printDateGet(DATE_GET_ARTICLES);
	    	return Response.status(Response.Status.OK)
	                .entity(HtmlHandler.getArticlesFromSEARCH_ALL_ARTICLES_not_auth(DATE_GET_ARTICLES, DATE_GET_COMMENTS))
	                .type(MediaType.TEXT_HTML)
	                .build();
	    } else {
	    	 return Response.ok("TRY_AGAIN").build();
	    }
	}
	
	@POST
	@Path("/filAp")
	public Response handleFilters(@FormParam("startDate") String startDate, 
			  					  @FormParam("endDate") String endDate) {
		
		System.out.println("SERVER STATUS: Filters in Display all Articles (not_auth) PRESSED");
		System.out.println("SERVER STATUS: startDate: " + startDate);
		System.out.println("SERVER STATUS: endDate: " + endDate);
		System.out.println("SERVER STATUS: ARRAY: --> " + DATE_GET_ARTICLES);
		
		if(startDate.isEmpty() && !endDate.isEmpty()) {
			return Response.ok("ADD_START_DATE").build();
		} else if(!startDate.isEmpty() && endDate.isEmpty()) {
			return Response.ok("ADD_END_DATE").build(); 
		} else if(!startDate.isEmpty() && !endDate.isEmpty()){
			ArrayList<Article> filteredArray = filterByDate(DATE_GET_ARTICLES, stringToDate(startDate), stringToDate(endDate));
			return Response.status(Response.Status.OK)
	                .entity(HtmlHandler.getArticlesFromSEARCH_ALL_ARTICLES_not_auth(filteredArray, DATE_GET_COMMENTS))
	                .type(MediaType.TEXT_HTML)
	                .build(); 
		} else {
			return Response.ok().build();
		}
	}
	
	@POST
	@Path("/add_comment")
	public Response handleAddComment(@FormParam("name_visitor") String name_visitor,
									 @FormParam("articleId") String articleId,
									 @FormParam("comment") String comment,
									 @FormParam("commentVisibility") String commentVisibility) {
		
		if(comment.isEmpty()) {
			return Response.ok("ERROR_EMTPY_COMMENT").build();
		}
		
		if(name_visitor.isEmpty() && commentVisibility.equals("withName")) {
			return Response.ok("ADD_NAME_OR_SELECT_ANONYMOUS_COMMENT").build();
		}
		if(commentVisibility == null) {
			return Response.notModified("SELECT_NAME_VISIBILITY").build();
		}
		System.out.println("SERVER STATUS: //"+ commentVisibility + "// COMMENT SUBMITED FROM //" + name_visitor + "// WITH CONTENTS //" + comment + "// AND ARTICLE ID //" + articleId + "//");

		if(saveComment(Integer.parseInt(articleId), comment, commentVisibility, name_visitor) == true) {
			return Response.ok("COMMENT_ADDED").build();
		} else 
			return Response.ok("COMMENT_NOT_ADDED").build();
	}
	
	/*--------------------------------------------------------------------------------------------------------*/
	
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
		   
	        if(CLICKED.equals("sortByState"))
        		selectQuery = "SELECT ID, TITLE, CONTENT, DATE_CREATION, STATE_ID, CREATOR_USERNAME FROM articles WHERE STATE_ID = 4 ORDER BY STATE_ID;";
        	else 
        		selectQuery = "SELECT ID, TITLE, CONTENT, DATE_CREATION, STATE_ID, CREATOR_USERNAME FROM articles WHERE STATE_ID = 4 ORDER BY DATE_CREATION;";
	        
	        selectStatement = connection.prepareStatement(selectQuery);
	    	resultSet = selectStatement.executeQuery();
	        
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
	
	
	private ArrayList<Comments> getCommentsAtStart() {
		ArrayList<Comments> temp_list = new ArrayList<>();
		
		String url = "jdbc:mysql://localhost:3306/news_db";
	    String username_DB = "root";
	    String passwd = "kolos2020";
	    
	    String selectQuery;
	    Connection connection = null;
	    PreparedStatement selectStatement = null;
	    ResultSet resultSet;
	    
	    selectQuery = "SELECT * FROM comments WHERE STATE_ID = 3;";
	    
	    try {
	    	connection = DriverManager.getConnection(url, username_DB, passwd);
	        System.out.println("\nSERVER STATUS: Connected to the database...");
	        
	    	selectStatement = connection.prepareStatement(selectQuery);
	    	resultSet = selectStatement.executeQuery();
	    	
	    	while(resultSet.next()) {
	        	int comment_Id = resultSet.getInt("ID");
	        	String comment_Content = resultSet.getString("CONTENT");
	        	Date comment_Date_creation = resultSet.getDate("DATE_CREATION");
	        	int comment_Article_id = resultSet.getInt("ARTICLE_ID");
	        	int comment_State_id = resultSet.getInt("STATE_ID");
	        	String comment_Creator_username = resultSet.getString("CREATOR_USERNAME");
	        	temp_list.add(new Comments(comment_Id, comment_Content, comment_Date_creation, comment_Article_id, comment_State_id, comment_Creator_username));
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
	private boolean saveComment(int articleId, String comment, String commentVisibility, String name_visitor) {
		String url = "jdbc:mysql://localhost:3306/news_db";
	    String username_DB = "root";
	    String passwd = "kolos2020";
	    
	    String insertQuery;
	    Connection connection = null;
	    PreparedStatement selectStatement = null;
	    int rowsAffected;
	    
	    try {
	    	if(name_visitor.isEmpty() || commentVisibility.equals("anonymous")) {
		    	/// anonymous comment (null value)
			    insertQuery = "INSERT INTO COMMENTS (CONTENT, DATE_CREATION, ARTICLE_ID, STATE_ID)\r\n"
			    		+ "VALUES (?, ?, ?, 1);";
			    	
			    connection = DriverManager.getConnection(url, username_DB, passwd);
			    System.out.println("\nSERVER STATUS: Connected to the database...");
			        
			    selectStatement = connection.prepareStatement(insertQuery);
			    selectStatement.setString(1, comment);
			    	
			    LocalDate currentDate = LocalDate.now();
			    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			    String formattedDate = currentDate.format(formatter);
			    selectStatement.setString(2, formattedDate);
			        
			    selectStatement.setInt(3, articleId);
			    rowsAffected = selectStatement.executeUpdate();
		    	return rowsAffected > 0;
	    	} else {
	    		insertQuery = "INSERT INTO COMMENTS (CONTENT, DATE_CREATION, ARTICLE_ID, STATE_ID, CREATOR_USERNAME)\r\n"
		    			+ "VALUES (?, ?, ?, 1, ?);"; /// when a comment is created we assign to it the state 1 (CREATED)
		    	connection = DriverManager.getConnection(url, username_DB, passwd);
		        System.out.println("\nSERVER STATUS: Connected to the database...");
		        
		    	selectStatement = connection.prepareStatement(insertQuery);
		    	selectStatement.setString(1, comment);
		    	
		    	LocalDate currentDate = LocalDate.now();
		        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		        String formattedDate = currentDate.format(formatter);
		        selectStatement.setString(2, formattedDate);
		        
		    	selectStatement.setInt(3, articleId);
		    	selectStatement.setString(4, name_visitor);
		    	rowsAffected = selectStatement.executeUpdate();
		    	return rowsAffected > 0;
	    	}
	    } catch(SQLException e) {
	    	e.printStackTrace();
	    	return false;
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
