package net.topics.ws.manage_topics;

import java.util.Date;

public class Topic {

	private int id;
	private String title;
	private Date date_creation;
	private int state_id;
	private String creator_username;
	private int parent_topic_id;
	
	public Topic(int id, String title, Date date_creation, int state_id, String creator_username, int parent_topic_id) {
		this.setId(id);
		this.setTitle(title);
		this.setDate_creation(date_creation);
		this.setState_id(state_id);
		this.setCreator_username(creator_username);
		this.setParent_topic_id(parent_topic_id);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getDate_creation() {
		return date_creation;
	}

	public void setDate_creation(Date date_creation) {
		this.date_creation = date_creation;
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

	public int getParent_topic_id() {
		return parent_topic_id;
	}

	public void setParent_topic_id(int parent_topic_id) {
		this.parent_topic_id = parent_topic_id;
	}
	
	
}
