package net.exceptions.ws;

/// NOTE: This exception is for when the role cannot be identified, this will lead to --wrong--  of the program ... 
public class NotIdentifiedRole extends Exception {
	public NotIdentifiedRole(String message) {
        super(message);
    }
}
