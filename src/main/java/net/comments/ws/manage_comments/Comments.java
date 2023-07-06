package net.comments.ws.manage_comments;

import java.util.Date;

public class Comments {
	private int id;
	private String content;
	private Date date_creation;
	private int article_id;
	private int state_id;
	private String creator_username;
	
	
	public Comments(int id, 
					String content, 
					Date date_creation, 
					int article_id, 
					int state_id,
					String creator_username) {
		this.id = id;
		this.content = content;
		this.date_creation = date_creation;
		this.article_id = article_id;
		this.state_id = state_id;
		this.creator_username = creator_username;
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public Date getDate_creation() {
		return date_creation;
	}
	public void setDate_creation(Date date_creation) {
		this.date_creation = date_creation;
	}
	
	public int getArticle_id() {
		return article_id;
	}
	public void setArticle_id(int article_id) {
		this.article_id = article_id;
	}
	
	public int getState_id() {
		return state_id;
	}
	public void setState_id(int state_id) {
		this.state_id = state_id;
	}
	
	public String getCreator_username() {
		return creator_username;
	}
	public void setCreator_username(String creator_username) {
		this.creator_username = creator_username;
	}
	
	
}
