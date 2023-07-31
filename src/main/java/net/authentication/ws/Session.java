package net.authentication.ws;

public class Session {
	private int SESSION_ID;
	private String USERNAME_BELONGS; //the username of the user that the session belongs
	private User USER_BELONGS;
	
	public Session(int SESSION_ID, String USERNAME_BELONGS, User USER_BELONGS) {
		this.SESSION_ID = SESSION_ID;
		this.USERNAME_BELONGS = USERNAME_BELONGS;
		this.USER_BELONGS = USER_BELONGS;
	}

	public int getSESSION_ID() {
		return SESSION_ID;
	}

	public String getUSERNAME_BELONGS() {
		return USERNAME_BELONGS;
	}
	
	public User getUSER_BELONGS() {
		return USER_BELONGS;
	}
}
