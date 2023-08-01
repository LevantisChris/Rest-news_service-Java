package net.testing.gr;

import static org.junit.Assert.*;

import org.junit.Test;

import jakarta.ws.rs.core.Response;
import net.comments.ws.ModifyCommentResource;

public class TestingModifyCommentResource {
	
	@Test
	public void testStartMethod() {
		ModifyCommentResource a = new ModifyCommentResource();
	    Response response = a.handleDisplayAllArticles(null);
	    assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
	    
	    response = a.handleDisplayAllArticles("");
	    assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
	}
	
	@Test
	public void testHandleModifyButton_WithNullJSON_MustReturnServerError() {
		ModifyCommentResource a = new ModifyCommentResource();
	    Response response = a.handleModifyButton("123456", null);
	    assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
	}
	
	/* Before the Modification of the comment with ID 22 (that has state 1) is "This a test to use it in the testing (JUnit)" */
	@Test
	public void testHandleModifyButton_WithCorrectJSON_MustReturnOk() {
		ModifyCommentResource a = new ModifyCommentResource();
		
		String JSON = "{\"comment_id\": 22, \"new_contents\": \"Testing modify comment\"}";
		
	    Response response = a.handleModifyButton("123456", JSON);
	    assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
	}
	
	@Test
	public void testHandleModifyButton_WithInCorrectJSON_MustReturnOk() {
		ModifyCommentResource a = new ModifyCommentResource();
		
		String JSON = "{\"comment_id\": 22, \"new_contentsWrong\": \"Testing modify comment\"}";
		
	    Response response = a.handleModifyButton("123456", JSON);
	    assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
	}
	
	
}
