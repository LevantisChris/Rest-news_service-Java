package net.testing.gr;

import static org.junit.Assert.*;

import org.junit.Test;

import jakarta.ws.rs.core.Response;
import net.comments.ws.ModifyCommentResource;

public class TestingModifyCommentResource {
	
	@Test
	public void testStartMethod() {
		ModifyCommentResource a = new ModifyCommentResource();
	    Response response = a.handleDisplayAllArticles("A_USERNAME", "VISITOR");
	    assertEquals("ROLE_NOT_IDENTIFIED", response.getEntity());
	    
	    response = a.handleDisplayAllArticles("A_USERNAME", "JOURNALIST");
	    assertEquals("ROLE_NOT_IDENTIFIED", response.getEntity());
	    
	    response = a.handleDisplayAllArticles("A_USERNAME", "ANOTHER_ROLE_NOT_KNOWN");
	    assertEquals(Response.Status.NOT_ACCEPTABLE.getStatusCode(), response.getStatus());
	}
	
	@Test
	public void testHandleModifyButton_WithNullJSON_MustReturnServerError() {
		ModifyCommentResource a = new ModifyCommentResource();
	    Response response = a.handleModifyButton(null);
	    assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
	}
	
	/* Before the Modification of the comment with ID 22 (that has state 1) is "This a test to use it in the testing (JUnit)" */
	@Test
	public void testHandleModifyButton_WithCorrectJSON_MustReturnOk() {
		ModifyCommentResource a = new ModifyCommentResource();
		
		String JSON = "{\"comment_id\": 22, \"new_contents\": \"Testing modify comment\"}";
		
	    Response response = a.handleModifyButton(JSON);
	    assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
	}
	
	@Test
	public void testHandleModifyButton_WithInCorrectJSON_MustReturnOk() {
		ModifyCommentResource a = new ModifyCommentResource();
		
		String JSON = "{\"comment_id\": 22, \"new_contentsWrong\": \"Testing modify comment\"}";
		
	    Response response = a.handleModifyButton(JSON);
	    assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
	}
	
	
}
