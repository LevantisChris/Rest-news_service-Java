package net.sessionExtractor.ws;

public interface ExtractSession_ID {
	boolean checkIfSessionExists(String sessionId);
	public boolean checkIfArticleCanBeViewed(String sessionId, String articleId, int functionState); // functionState is about the default state the articles should be based on the function
    String getUsernameFromSession(String sessionId);
    String getRoleFromSession(String sessionId); // it will return the definition
}