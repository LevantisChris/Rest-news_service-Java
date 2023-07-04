package net.articles.ws;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import net.articles.ws.manage_articles.Article;
import net.htmlhandler.ws.HtmlHandler;

//// FOR VISITOR

@Path("/auth/not_auth_user/search_article")
public class SearchArticleResource_notAuth {
	
	private static int ROLE_ID;
	private ArrayList<Article> articles_list;
	private ArrayList<Article> GOAL_ARTICLES; // the articles that satisfy all the criteria ...
	
	@GET
	public Response handleKeyPhrasesNotAuthUserArticles(@QueryParam("role") String role) {
		System.out.println("SERVER STATUS: SEARCH ARTICLE CALLED BY USERNAME == " + "--NULL--" + " - ROLE == " + role);
		int ROLE_ID;
		if(role.equals("VISITOR")) {
			ROLE_ID = 1;
			return Response.status(Response.Status.OK)
	                .entity(HtmlHandler.getSEARCH_ARTICLE_KEY_PHRASES_HTML("", ROLE_ID))
	                .type(MediaType.TEXT_HTML)
	                .build();
		} else { // if someone else get here we have a problem ...
			return Response.serverError().build();
		}
	}
	
	@GET
	@Path("/search")
	public Response sendData(@QueryParam("titleKeyPhrases") String titleKeyPhrases, @QueryParam("contentKeyPhrases") String contentKeyPhrases) {
		System.out.println("SERVER STATUS: VISITOR, Title key phrases --> " + titleKeyPhrases);
		System.out.println("SERVER STATUS: VISITOR, Content key phrases --> " + contentKeyPhrases);
		
		getAllArticlesFromDB();
		
		if(hasWords(titleKeyPhrases) == false && hasWords(contentKeyPhrases) == false) {
			articlesGot(); // just to display what articles we have got ...
			searchTitleAndContentsOneWord(titleKeyPhrases, contentKeyPhrases);
			if(GOAL_ARTICLES.size() != 0) {
				printGoals();
				return Response.status(Response.Status.OK)
		                .entity(HtmlHandler.getArticlesFromSEARCH_ARTICLES(GOAL_ARTICLES))
		                .type(MediaType.TEXT_HTML)
		                .build();
			} else {
				 return Response.status(Response.Status.OK)
						.entity(HtmlHandler.getArticlesFromSEARCH_ARTICLES(GOAL_ARTICLES))
						.type(MediaType.TEXT_HTML)
		                .build();
			}
		}
		/* NOTE SOS: Here we will have to break the words in the key phrases the user add, and find for each of it, if it exists in an article
		 * WARNING: Both must be satisfied, for example if the user add for the title the key phrase --Review BMW-- both //Review// and //BMW// must be in the article/s */
		else {
			System.out.println("HAS MORE THAN ONE WORDS");
			articlesGot(); // just to display what articles we have got ...
			if(!titleKeyPhrases.isEmpty() && contentKeyPhrases.isEmpty()) { // title is not empty and contains more than one words
				System.out.println("title is not empty and contains more than one words");
				ArrayList<String> titleArray = splitStrings(titleKeyPhrases);
				System.out.println(titleArray);
				searchTitleTwoWords(titleArray);
				if(GOAL_ARTICLES.size() != 0) {
					printGoals();
					return Response.status(Response.Status.OK)
			               .entity(HtmlHandler.getArticlesFromSEARCH_ARTICLES(GOAL_ARTICLES))
			               .type(MediaType.TEXT_HTML)
			               .build();
				} else {
					 return Response.status(Response.Status.OK)
								.entity(HtmlHandler.getArticlesFromSEARCH_ARTICLES(GOAL_ARTICLES))
								.type(MediaType.TEXT_HTML)
				                .build();
				}
			} else if(titleKeyPhrases.isEmpty() && !contentKeyPhrases.isEmpty()) { // content is not empty and contains more than one words
				System.out.println("content is not empty and contains more than one words");
				ArrayList<String> contentArray = splitStrings(contentKeyPhrases);
				System.out.println(contentArray);
				searchContentTwoWords(contentArray);
				if(GOAL_ARTICLES.size() != 0) {
					printGoals();
					return Response.status(Response.Status.OK)
				               .entity(HtmlHandler.getArticlesFromSEARCH_ARTICLES(GOAL_ARTICLES))
				               .type(MediaType.TEXT_HTML)
				               .build();
				} else {
					return Response.status(Response.Status.OK)
							.entity(HtmlHandler.getArticlesFromSEARCH_ARTICLES(GOAL_ARTICLES))
							.type(MediaType.TEXT_HTML)
			                .build();
				}
			} else { // both are not empty and contain more than one word
				System.out.println("both are not empty and contain more than one word");
				ArrayList<String> titleArray = splitStrings(titleKeyPhrases);
				ArrayList<String> contentArray = splitStrings(contentKeyPhrases);
				System.out.println(titleArray);
				System.out.println(contentArray);
				/* WARNING: After this two functions we will have as Goal the articles that have in the satisfy the title OR the Content */
				searchTitleTwoWords(titleArray);
				searchContentTwoWords(contentArray);
				if(GOAL_ARTICLES.size() != 0) {
					printGoals();
					return Response.status(Response.Status.OK)
				               .entity(HtmlHandler.getArticlesFromSEARCH_ARTICLES(GOAL_ARTICLES))
				               .type(MediaType.TEXT_HTML)
				               .build();
				} else {
					return Response.status(Response.Status.OK)
							.entity(HtmlHandler.getArticlesFromSEARCH_ARTICLES(GOAL_ARTICLES))
							.type(MediaType.TEXT_HTML)
			                .build();
				}
			}
		}
	}
	
	
	/*--------------------------------------------------------------------------------------------------------------------------------------------------*/
	
	public boolean hasWords(String input) {
		String [] array = input.trim().split(" ");
		if(array.length == 1){
			return false; // it has only one word
		} else{
			return true; // it has more than one word
		}
	}
	
	private void articlesGot() {
		System.out.println("------------------------------------------------");
		for(int i = 0;i < articles_list.size();i++) {
			System.out.println("ARTICLE-GOT --> ID == " + articles_list.get(i).getId() + "// TITLE == " + articles_list.get(i).getTitle() + "// CONTENTS == " + articles_list.get(i).getContents().substring(0, Math.min(10, articles_list.get(i).getContents().length())) + "...");
		}
		System.out.println("------------------------------------------------");
	}
	private void printGoals() {
		System.out.println("------------------------------------------------");
		for(int i = 0;i < GOAL_ARTICLES.size();i++) {
			System.out.println("ARTICLE-GOAL-FOUND --> ID == " + GOAL_ARTICLES.get(i).getId());
		}
		System.out.println("------------------------------------------------");
	}
	
	private ArrayList<String> splitStrings(String str) {
		String[] words = str.split("\\s+");

		ArrayList<String> wordList = new ArrayList<>();
		Collections.addAll(wordList, words);

		return wordList;
	}
	
	private void searchTitleTwoWords(ArrayList<String> titleArray) {
		GOAL_ARTICLES = new ArrayList<>();
		boolean temp = false; // if all the words exists in an article 
		for(int i = 0;i < articles_list.size();i++) {
			for(int j = 0;j < titleArray.size();j++) {
				if(articles_list.get(i).getTitle().contains(titleArray.get(j))) {
					temp = true;
				} else {
					temp = false;
					break;
				}
			}
			if(temp == true) {
				GOAL_ARTICLES.add(articles_list.get(i));
			}
		}	
	}
	private void searchContentTwoWords(ArrayList<String> contentsArray) {
		GOAL_ARTICLES = new ArrayList<>();
		boolean temp = false; // if all the words exists in an article 
		for(int i = 0;i < articles_list.size();i++) {
			for(int j = 0;j < contentsArray.size();j++) {
				if(articles_list.get(i).getContents().contains(contentsArray.get(j))) {
					temp = true;
				} else {
					temp = false;
					break;
				}
			}
			if(temp == true) {
				GOAL_ARTICLES.add(articles_list.get(i));
			}
		}	
	}
	
	private void searchTitleAndContentsOneWord(String titleKeyPhrases, String contentKeyPhrases) {
		GOAL_ARTICLES = new ArrayList<>();
		if(!titleKeyPhrases.isEmpty() && contentKeyPhrases.isEmpty()) { // the user has only add key phrase about title
			for(int i = 0;i < articles_list.size();i++) {
				if(articles_list.get(i).getTitle().contains(titleKeyPhrases)) {
					GOAL_ARTICLES.add(articles_list.get(i));
				}
			}
		} else if(titleKeyPhrases.isEmpty() && !contentKeyPhrases.isEmpty()) { // the user has only add key phrase about content
			for(int i = 0;i < articles_list.size();i++) {
				if(articles_list.get(i).getContents().contains(contentKeyPhrases)) {
					GOAL_ARTICLES.add(articles_list.get(i));
				}
			}
		} else if(!titleKeyPhrases.isEmpty() && !contentKeyPhrases.isEmpty()) { /// NOTE: if he add title key phrase and also content key phrase, We return the articles that have at least each ...
			for(int i = 0;i < articles_list.size();i++) { // the user has add key phrase for both content and title
				if(articles_list.get(i).getContents().contains(contentKeyPhrases) || articles_list.get(i).getTitle().contains(titleKeyPhrases)) {
					GOAL_ARTICLES.add(articles_list.get(i));
				}
			}
		} 
	}
	
	private void getAllArticlesFromDB() {
			
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
		    	
	    		selectQuery = "SELECT ID, TITLE, CONTENT FROM articles WHERE STATE_ID = 4;"; // take all the articles that are published ...
		    	selectStatement = connection.prepareStatement(selectQuery);
		    	DATA_DB = selectStatement.executeQuery();
		    	
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
		                System.out.println("SERVER STATUS: Disconnected from the database...\n");
		            }
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
		    }
		}
	}
