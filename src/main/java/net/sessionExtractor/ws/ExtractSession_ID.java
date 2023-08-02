package net.sessionExtractor.ws;

public interface ExtractSession_ID {
	boolean checkIfSessionExists(String sessionId);
	
	public boolean checkIfArticleCanBeViewed(String sessionId, String articleId, int functionState); // functionState is about the default state the articles should be based on the function
	public boolean checkIfArticleCanBeViewed(String sessionId, String articleId, int functionState, String description); // with description to identify the function
	public boolean checkIfArticleCanBeViewed(String sessionId, String articleId, int functionState1, int functionState2); // the same but for functions that have 2 default states
	public boolean checkIfArticleCanBeViewed(String sessionId, String articleId, int functionState1, int functionState2, String description);
	
	public boolean checkIfTopicCanBeViewed(String sessionId, String topicId, int functionState);
	public boolean checkIfTopicCanBeViewed(String sessionId, String topicId, int functionState, String description);
	
	String getUsernameFromSession(String sessionId);
    String getRoleFromSession(String sessionId); // it will return the definition
}