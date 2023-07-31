package net.sessionExtractor.ws;

public interface ExtractSession_ID {
	boolean checkIfSessionExists(String sessionId);
    String getUsernameFromSession(String sessionId);
    String getRoleFromSession(String sessionId); // it will return the definition
}