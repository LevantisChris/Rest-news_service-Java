package net.topics.ws;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import net.articles.ws.manage_articles.Article;
import net.htmlhandler.ws.HtmlHandler;

@Path("/auth/auth_user/displayArticlesOfTopic_topic")
public class DisplayArticlesOfATopicResource_auth {

	@GET
	@Consumes(MediaType.TEXT_HTML)
	public Response handleStartPage(@QueryParam("username") String username, @QueryParam("role") String role) {
		if(role.equals("JOURNALIST")) {
			System.out.println("SERVER STATUS: ACTION IN DISPLAY ARTCICLES OF A TOPIC (auth_user) BY USERNAME --" + username + "-- AND ROLE --" + role + "--");
			
			ArrayList<String> TOPICS_DB = getTopics(username, role);
			
			return Response.status(Response.Status.UNAUTHORIZED)
	                .entity(HtmlHandler.getDISPLAY_ARTICLES_OF_A_TOPIC_START_CODE(TOPICS_DB, username, role))
	                .type(MediaType.TEXT_HTML)
	                .build();
		} else if(role.equals("CURATOR")) { 
			System.out.println("SERVER STATUS: ACTION IN DISPLAY ARTCICLES OF A TOPIC BY USERNAME --" + username + "-- AND ROLE --" + role + "--");
			
			ArrayList<String> TOPICS_DB = getTopics(username, role);
			
			return Response.status(Response.Status.UNAUTHORIZED)
	                .entity(HtmlHandler.getDISPLAY_ARTICLES_OF_A_TOPIC_START_CODE(TOPICS_DB, username, role))
	                .type(MediaType.TEXT_HTML)
	                .build();
		} else {
			return Response.serverError().build();
		}
	}
	
	@GET
	@Path("/display")
	public Response handleTopicArticles(@QueryParam("username") String username, 
									    @QueryParam("role") String role,
									    @QueryParam("topic_clicked") String topic_clicked) {
		System.out.println("SERVER STATUS: ACTION IN THE DISPLAY_ARTICLES_OF_A_TOPIC BY USERNAME --" + username + "-- AND ROLE --" + role + "-- WITH TOPIC CLICKED --" + topic_clicked + "--");
		
		ArrayList<Article> ARTICLES_OF_TOPIC = getArticleOfTopic(username, role, topic_clicked);
		printArticlesGot(ARTICLES_OF_TOPIC, topic_clicked);
		
		StringBuilder htmlCode = new StringBuilder();
		if(!ARTICLES_OF_TOPIC.isEmpty()) {
			for (Article article : ARTICLES_OF_TOPIC) {
		        htmlCode.append("    <div class=\"article-generated\">\n");
		        htmlCode.append("        <h2>ID: ").append(article.getId()).append("</h2>\n");
		        htmlCode.append("        <p>Creator username: ").append(article.getCreator_username()).append("</p>\n");
		        htmlCode.append("        <p>State ID: ").append(article.getState_id()).append("</p>\n");
		        htmlCode.append("        <p>Date creation: ").append(article.getDate_creation()).append("</p>\n");
		        htmlCode.append("        <p>Title: ").append(article.getTitle()).append("</p>\n");
		        htmlCode.append("        <p style=\"font-size: 25px;\">Content: ").append(article.getContents()).append("</p>\n");
		        htmlCode.append("    </div>\n");
			}
		} else {
			htmlCode.append("    <div class=\"article-generated\">\n");
	        htmlCode.append("        <h1>ID: ").append("No articles have created for the topic --" + topic_clicked + "--").append("</h1>\n");
		}
		return Response.ok(htmlCode.toString(), MediaType.TEXT_HTML).build(); 
	}
	
	/*------------------------------------------------------------------------------------------------------------------------------------------------------------*/
	
	private void printArticlesGot(ArrayList<Article> ARTICLES_OF_TOPIC, String topic_clicked) {
		System.out.println("---------------------Articles got in the DISPLAY ARTICLES OF A TOPIC----------------------------------------");
		if(!ARTICLES_OF_TOPIC.isEmpty()) {
			for(int i = 0;i < ARTICLES_OF_TOPIC.size();i++) {
				System.out.println("--> " + ARTICLES_OF_TOPIC.get(i).getTitle());
			}
		} else {
			System.out.println("SERVER STATUS: EMPTY LIST, NO ARTICLES FOUND FOR THE TOPIC --" + topic_clicked + "--");
		}
		System.out.println("------------------------------------------------------------------------------------------------------------");
	}
	
	private ArrayList<Article> getArticleOfTopic(String username, String role, String topic_clicked) {
		ArrayList<Article> ARTICLES_OF_TOPIC = new ArrayList<>();
		
		int TOPIC_ID = getTopicID(topic_clicked); // to run the following code we need to know the ID of the topic ...
		if(TOPIC_ID == -1) {
			return null;
		}
		
		String url = "jdbc:mysql://localhost:3306/news_db";
	    String username_DB = "root";
	    String passwd = "kolos2020";
	    
	    String selectQuery;
	    Connection connection = null;
	    PreparedStatement selectStatement = null;
	    ResultSet resultSet;
	    
	    if(role.equals("CURATOR")) {
	    	selectQuery = "SELECT ID, TITLE, CONTENT, DATE_CREATION, CREATOR_USERNAME, STATE_ID "
		    			+ "FROM news_db.articles "
		    			+ "WHERE TOPIC_ID = ?;"; // Can see everything 
	    	try {
	    		
	    		connection = DriverManager.getConnection(url, username_DB, passwd);
	    		System.out.println("\nSERVER STATUS: Connected to the database...");
	    		selectStatement = connection.prepareStatement(selectQuery);
	    		selectStatement.setInt(1, TOPIC_ID);
	    		resultSet = selectStatement.executeQuery();
	    		
	    		while(resultSet.next()) {
	    			int id = resultSet.getInt("ID");
	    			String title = resultSet.getString("TITLE");
	    			String content = resultSet.getString("CONTENT");
	    			Date date_creation = resultSet.getDate("DATE_CREATION");
	    			String creator_username = resultSet.getString("CREATOR_USERNAME");
	    			int state_id = resultSet.getInt("STATE_ID");
	    			ARTICLES_OF_TOPIC.add(new Article(id, title, content, date_creation, state_id, creator_username));
		        }
	    		return ARTICLES_OF_TOPIC;
	    	} catch(SQLException e) {
	    		System.out.println("SERVER STATUS: --ERROR-- IN THE DISPLAY_ALL_ARTICLES_OF_A_TOPIC in the getTopics IN ROLE JOURNALIST");
	    		e.printStackTrace();
	    		return null;
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
	    	
	    } else if(role.equals("JOURNALIST")) {
	    	selectQuery = "SELECT ID, TITLE, CONTENT, DATE_CREATION, CREATOR_USERNAME, STATE_ID "
                    + "FROM news_db.articles "
                    + "WHERE (CREATOR_USERNAME = ? OR STATE_ID = 4) AND TOPIC_ID = ?;";

	    	try {
	    		
	    		connection = DriverManager.getConnection(url, username_DB, passwd);
	    		System.out.println("\nSERVER STATUS: Connected to the database...");
	    		selectStatement = connection.prepareStatement(selectQuery);
	    		selectStatement.setString(1, username);
	    		selectStatement.setInt(2, TOPIC_ID);
	    		resultSet = selectStatement.executeQuery();
	    		
	    		while(resultSet.next()) {
	    			int id = resultSet.getInt("ID");
	    			String title = resultSet.getString("TITLE");
	    			String content = resultSet.getString("CONTENT");
	    			Date date_creation = resultSet.getDate("DATE_CREATION");
	    			String creator_username = resultSet.getString("CREATOR_USERNAME");
	    			int state_id = resultSet.getInt("STATE_ID");
	    			ARTICLES_OF_TOPIC.add(new Article(id, title, content, date_creation, state_id, creator_username));
		        }
	    		return ARTICLES_OF_TOPIC;
	    	} catch(SQLException e) {
	    		System.out.println("SERVER STATUS: --ERROR-- IN THE DISPLAY_ALL_ARTICLES_OF_A_TOPIC in the getTopics IN ROLE JOURNALIST");
	    		e.printStackTrace();
	    		return null;
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
	    } else {
	    	return null;
	    }
	}
	
	private int getTopicID(String topic_clicked) {
		String url = "jdbc:mysql://localhost:3306/news_db";
	    String username_DB = "root";
	    String passwd = "kolos2020";
	    
	    String selectQuery = "SELECT ID FROM news_db.topic WHERE TITLE = ?;";
	    Connection connection = null;
	    PreparedStatement selectStatement = null;
	    ResultSet resultSet;
	   
	    int ID = -1;
	    try {
	    	connection = DriverManager.getConnection(url, username_DB, passwd);
    		System.out.println("\nSERVER STATUS: Connected to the database...");
    		selectStatement = connection.prepareStatement(selectQuery);
    		selectStatement.setString(1, topic_clicked);
    		resultSet = selectStatement.executeQuery();
    		
    		while(resultSet.next()) {
    			ID = resultSet.getInt("ID");
	        }
    		return ID;
	    } catch(SQLException e) {
	    	System.out.println("SERVER STATUS: --ERROR-- IN THE DISPLAY_ALL_ARTICLES_OF_A_TOPIC in the getTopicID");
    		e.printStackTrace();
    		return ID;
	    }
	}
	
	private ArrayList<String> getTopics(String username, String role) {
		ArrayList<String> TOPICS_DB = new ArrayList<>();
		
		String url = "jdbc:mysql://localhost:3306/news_db";
	    String username_DB = "root";
	    String passwd = "kolos2020";
	    
	    String selectQuery;
	    Connection connection = null;
	    PreparedStatement selectStatement = null;
	    ResultSet resultSet;
	    
	    if(role.equals("CURATOR")) {
	    	selectQuery = "SELECT TITLE FROM news_db.topic;"; // Can see everything 
	    	try {
	    		
	    		connection = DriverManager.getConnection(url, username_DB, passwd);
	    		System.out.println("\nSERVER STATUS: Connected to the database...");
	    		selectStatement = connection.prepareStatement(selectQuery);
	    		resultSet = selectStatement.executeQuery();
	    		
	    		while(resultSet.next()) {
		        	TOPICS_DB.add(resultSet.getString("TITLE"));
		        }
	    		return TOPICS_DB;
	    	} catch(SQLException e) {
	    		System.out.println("SERVER STATUS: --ERROR-- IN THE DISPLAY_ALL_ARTICLES_OF_A_TOPIC in the getTopics IN ROLE JOURNALIST");
	    		e.printStackTrace();
	    		return null;
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
	    	
	    } else if(role.equals("JOURNALIST")) {
	    	selectQuery = "SELECT TITLE FROM news_db.topic WHERE CREATOR_USERNAME = ? OR STATE_ID = 3;"; // Can see topics that belongs to him and also the topics that belongs to him
	    	try {
	    		
	    		connection = DriverManager.getConnection(url, username_DB, passwd);
	    		System.out.println("\nSERVER STATUS: Connected to the database...");
	    		selectStatement = connection.prepareStatement(selectQuery);
	    		selectStatement.setString(1, username);
	    		resultSet = selectStatement.executeQuery();
	    		
	    		while(resultSet.next()) {
		        	TOPICS_DB.add(resultSet.getString("TITLE"));
		        }
	    		return TOPICS_DB;
	    	} catch(SQLException e) {
	    		System.out.println("SERVER STATUS: --ERROR-- IN THE DISPLAY_ALL_ARTICLES_OF_A_TOPIC in the getTopics IN ROLE JOURNALIST");
	    		e.printStackTrace();
	    		return null;
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
	    	
	    } else {
	    	return null;
	    }
	}
	
}
