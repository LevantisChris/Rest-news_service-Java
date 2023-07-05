package net.articles.ws.manage_articles;

import java.util.Date;


/* This class is used int the functions:
 * Search Article */

public class Article {

	private int id;
	private String title;
	private String content; 
	private Date date_creation;
	private int state_id;
	private String creator_username ;
	
	public Article() {}
	
	public Article(int id, String title, String contents) {
		this.id = id;
		this.title = title;
		this.content = contents;
	}

	public Article(int id, String title, String contents
			,Date date_creation, int state_id, String creator_username) {
		this.id = id;
		this.title = title;
		this.content = contents;
		this.date_creation = date_creation;
		this.state_id = state_id;
		this.creator_username = creator_username;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	public String getTitle() {
		return title;
	}

	public void setContents(String Contents) {
		this.content = Contents;
	}
	public String getContents() {
		return content;
	}

	public void setDate_creation(Date Date_creation) {
		this.date_creation = Date_creation;
	}
	public Date getDate_creation() {
		return date_creation;
	}

	public void setState_id(int State_id) {
		this.state_id = State_id;
	}
	public int getState_id() {
		return state_id;
	}

	public void setCreator_username(String Creator_username) {
		this.creator_username = Creator_username;
	}
	public String getCreator_username() {
		return creator_username;
	}
	
}
