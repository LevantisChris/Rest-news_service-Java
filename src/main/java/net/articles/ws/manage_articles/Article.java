package net.articles.ws.manage_articles;

import java.util.Date;

/* This class is used int the functions:
 * Search Article */

public class Article {

	private int id;
	private String title;
	private String contents; 
	private Date date_creation;
	private int state_id;
	private String creator_username ;
	
	public Article(int id, String title, String contents) {
		this.id = id;
		this.title = title;
		this.contents = contents;
	}

	public Article(int id, String title, String contents
			,Date date_creation, int state_id, String creator_username) {
		this.id = id;
		this.title = title;
		this.contents = contents;
		this.date_creation = date_creation;
		this.state_id = state_id;
		this.creator_username = creator_username;
	}
	
	public int getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getContents() {
		return contents;
	}

	public Date getDate_creation() {
		return date_creation;
	}

	public int getState_id() {
		return state_id;
	}

	public String getCreator_username() {
		return creator_username;
	}
	
}
