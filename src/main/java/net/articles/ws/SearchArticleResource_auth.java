package net.articles.ws;

import net.articles.ws.manage_articles.Article;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import net.exceptions.ws.NotIdentifiedRole;
import net.htmlhandler.ws.HtmlHandler;

/// FOR CURATOR AND JOURNALIST

@Path("/auth/auth_user/search_article")
public class SearchArticleResource_auth {

	private static int ROLE_ID;
	private ArrayList<Article> articles_list;
	private ArrayList<Article> GOAL_IDs; // the id of the article that satisfies all the criteria
	
	@GET
	public Response handleKeyPhrasesAuthUserArticles(@QueryParam("username") String username, @QueryParam("role") String role) {
		System.out.println("SERVER STATUS --> SEARCH ARTICLE CALLED BY USERNAME == " + username + " - ROLE == " + role);
		try {
			if(role.equals("VISITOR")) { // if a visitor gets here we have a problem ...
				ROLE_ID = 1;
				return Response.serverError().build();
			} else if(role.equals("JOURNALIST")) {
				System.out.println("MPIKEEE");
				ROLE_ID = 2;
				
				return Response.status(Response.Status.OK)
                .entity(HtmlHandler.getSEARCH_ARTICLE_KEY_PHRASES_HTML(username, ROLE_ID))
                .type(MediaType.TEXT_HTML)
                .build();
				
			} else if(role.equals("CURATOR")){
				ROLE_ID = 3;
				
				return Response.status(Response.Status.OK)
		                .entity(HtmlHandler.getSEARCH_ARTICLE_KEY_PHRASES_HTML(username, ROLE_ID))
		                .type(MediaType.TEXT_HTML)
		                .build();
				
			} else { throw new NotIdentifiedRole("ERROR: The role could not be identified.");}
		} catch(NotIdentifiedRole e) {
			System.out.print(e.getMessage());
			return Response.ok(e.getMessage()).build();
		}
	}
	
	
	@GET
	@Path("/search")
	public Response sendData(@QueryParam("username") String username, @QueryParam("titleKeyPhrases") String titleKeyPhrases, @QueryParam("contentKeyPhrases") String contentKeyPhrases) {
		System.out.println("CURATOR AND JOURNALIST, Username --> " + username);
		System.out.println("CURATOR AND JOURNALIST, Title key phrases --> " + titleKeyPhrases);
		System.out.println("CURATOR AND JOURNALIST, Content key phrases --> " + contentKeyPhrases);
		
		getAllArticlesFromDB(username); // first we get all the articles ...
		
		if(hasWords(titleKeyPhrases) == false) {
			test(); // just to display what articles we have got ...
			searchTitleAndContents(titleKeyPhrases, contentKeyPhrases);
			if(GOAL_IDs.size() != 0) {
				printGoals();
				return Response.ok("TO EXOYN TA: " + GOAL_IDs).build();
			} else {
				return Response.ok("DENNNN TO EXIIII KANENA").build();
			}
		}
		else {
			System.out.println("HAS MORE THAN ONE WORDS");
			return Response.ok("PARAPANO APO MIA LEZIS").build();
		}
	}
	
	/*------------------------------------------------------------------------------------------------------------------------------------------------*/
	
	/* NOTE: This func checks if the a string has one or more words */ 
	public static boolean hasWords(String input) {
		String [] array = input.trim().split(" ");
		if(array.length == 1){
			return false; // it has only one word
		} else{
			return true; // it has more than one word
		}
	}
	
	private void getAllArticlesFromDB(String username) {
		
		System.out.println("TESTTT --> " + ROLE_ID);
		
		articles_list = new ArrayList<>();
		
		String url = "jdbc:mysql://localhost:3306/news_db";
	    String username_DB = "root";
	    String passwd = "kolos2020";
	    
	    ResultSet DATA_DB;
	    
	    String selectQuery;
	    Connection connection = null;
	    PreparedStatement selectStatement = null;

	    try {
	    	
	    	connection = DriverManager.getConnection(url, username_DB, passwd);
    		System.out.println("\nSERVER STATUS: Connected to the database...");
	    	
    		if(ROLE_ID == 2) { // is JOURNALIST
		    	selectQuery = "SELECT ID, TITLE, CONTENT FROM articles WHERE STATE_ID = 3 OR STATE_ID = 4 AND CREATOR_USERNAME = ?;"; // can see only the articles that are in the stage APPROVED and PUBLISHED
		    	selectStatement = connection.prepareStatement(selectQuery);
		    	selectStatement.setString(1, username);
		    	DATA_DB = selectStatement.executeQuery();
    		} else { // is a Curator	
		    	selectQuery = "SELECT ID, TITLE, CONTENT FROM articles;"; // can see everything
		    	selectStatement = connection.prepareStatement(selectQuery);
		    	DATA_DB = selectStatement.executeQuery();
    		}
	    	
	    	while(DATA_DB.next()) {
	        	Article new_ar = new Article(DATA_DB.getInt("ID"), DATA_DB.getString("TITLE"), DATA_DB.getString("CONTENT"));
	        	articles_list.add(new_ar);
	        }
	    	
	    } catch(SQLException e) {
	    	e.printStackTrace();
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
	
	private void test() {
		for(int i = 0;i < articles_list.size();i++) {
			System.out.println("TEST --> ID == " + articles_list.get(i).getId() + "// TITLE == " + articles_list.get(i).getTitle() + "// CONTENTS == " + articles_list.get(i).getContents().substring(0, Math.min(10, articles_list.get(i).getContents().length())));

		}
	}
	private void printGoals() {
		for(int i = 0;i < GOAL_IDs.size();i++) {
			System.out.println("TEST GOALLLL --> ID == " + GOAL_IDs.get(i).getId());

		}
	}
	
	/* NOTE: Now we have in the ArrayList all the Articles, we are going to find the articles that have in there titles 
	 * the key word the user add (titleKeyPhrases) */
	private void searchTitleAndContents(String titleKeyPhrases, String contentKeyPhrases) {
		GOAL_IDs = new ArrayList<>();
		if(!titleKeyPhrases.isEmpty() && contentKeyPhrases.isEmpty()) { // the user has only add key phrase about title
			System.out.println("EDOO 1");
			for(int i = 0;i < articles_list.size();i++) {
				if(articles_list.get(i).getTitle().contains(titleKeyPhrases)) {
					GOAL_IDs.add(articles_list.get(i));
				}
			}
		} else if(titleKeyPhrases.isEmpty() && !contentKeyPhrases.isEmpty()) { // the user has only add key phrase about content
			System.out.println("EDOO 2");
			for(int i = 0;i < articles_list.size();i++) {
				if(articles_list.get(i).getContents().contains(contentKeyPhrases)) {
					GOAL_IDs.add(articles_list.get(i));
				}
			}
		} else if(!titleKeyPhrases.isEmpty() && !contentKeyPhrases.isEmpty()) { /// NOTE: if he add two key phrases, both must be contained ...
			System.out.println("EDOO 3");
			for(int i = 0;i < articles_list.size();i++) { // the user has add key phrase for both content and title
				if(articles_list.get(i).getContents().contains(contentKeyPhrases) && articles_list.get(i).getTitle().contains(titleKeyPhrases)) {
					GOAL_IDs.add(articles_list.get(i));
				}
			}
		} 
	}
}
