package net.authentication.ws;

public class User {
	// Curator and Journalist 
	private String USERNAME;
	private String PASSWORD;
	private String NAME;
	private String SURNAME;
	private int ROLE_ID;
	
	// Visitor
	public User(int ROLE_ID) {
		this.ROLE_ID = ROLE_ID;
	}
	
	// Curator and Journalist
	public User(String USERNAME, String PASSWORD, String NAME, String SURNAME, int ROLE_ID) {
		this.USERNAME = USERNAME;
		this.PASSWORD = PASSWORD;
		this.NAME = NAME;
		this.SURNAME = SURNAME;
		this.ROLE_ID = ROLE_ID;
	}

	public String getUSERNAME() {
		return USERNAME;
	}

	public String getPASSWORD() {
		return PASSWORD;
	}

	public String getNAME() {
		return NAME;
	}

	public String getSURNAME() {
		return SURNAME;
	}

	public int getROLE_ID() {
		return ROLE_ID;
	}
}
