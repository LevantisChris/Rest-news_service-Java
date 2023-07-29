package net.authentication.ws;

import java.time.Instant;

public class Session {

	private Instant lastActivity;
	
	private int SESSION_ID;
	private String USERNAME_BELONGS; //the username of the user that the session belongs
	private User USER_BELONGS;
	
	public Session(int SESSION_ID, String USERNAME_BELONGS, User USER_BELONGS) {
		this.SESSION_ID = SESSION_ID;
		this.USERNAME_BELONGS = USERNAME_BELONGS;
		this.USER_BELONGS = USER_BELONGS;
		this.lastActivity = Instant.now();
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
	
	//////////////////////////////////////////////////////////////////////////////////
	
	public void updateActivity() {
        this.lastActivity = Instant.now();
    }

	/* checking whether the time since the last activity in the session is greater than the timeout period. 
	 * If it is, the method returns true to indicate the session has expired. 
	 * If it's not, the method returns false to indicate the session is still active.*/
    public boolean isExpired() {
    	int timeoutMinutes = 30;
        return lastActivity.plusSeconds(30).isBefore(Instant.now());
    }
}
