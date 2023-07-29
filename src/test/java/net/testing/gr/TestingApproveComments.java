package net.testing.gr;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.junit.Test;

import jakarta.ws.rs.core.Response;
import net.comments.ws.ApproveCommentResource;
import net.comments.ws.manage_comments.Comments;

public class TestingApproveComments {
	
	@Test
	public void testStartMethod() {
	    ApproveCommentResource a = new ApproveCommentResource();
	    Response response = a.handleDisplayAllComments("A_USERNAME", "VISITOR");
	    assertEquals("ROLE_NOT_IDENTIFIED", response.getEntity());
	    
	    response = a.handleDisplayAllComments("A_USERNAME", "ANOTHER_ROLE_NOT_KNOWN");
	    assertEquals(Response.Status.NOT_ACCEPTABLE.getStatusCode(), response.getStatus());
	}

	@Test
	public void testHandleApproveButton_WithValidJSON_MustReturnOk() {
	    ApproveCommentResource a = new ApproveCommentResource();
	    /* We add the ID of an article that at this time has STATE_ID 1, to test if 
	     * the approve method is working correctly */
	    String JSON_STR = "{\"comment_id\": 20}";
	    Response response = a.handleApproveButton(JSON_STR);
	    assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
	}
	
	@Test
	public void testHandleApproveButton_WithInValidJSON_MustReturnServerError() {
		ApproveCommentResource a = new ApproveCommentResource();
		/* Now the JSON we add is invalid because the ID does not exist */
		String JSON_STR = "{\"comment_id\": 53}";
	    Response response = a.handleApproveButton(JSON_STR);
	    assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
	}
	
	@Test
	public void testHandleApproveButton_WithNullJSON_MustReturnServerError() {
		ApproveCommentResource a = new ApproveCommentResource();
		Response response = a.handleApproveButton(null);
	    assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
	}
	
	@Test
	public void testHandleFilters_WithEmptyFields_MustReturn() {
		ApproveCommentResource a = new ApproveCommentResource();
		Response response = a.handleFilters("", "", "");
	    assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
	}
	
	@Test
	public void testGetAllComments_MustReturnAllTheCommentsThatInTheDatabaseThatHaveStateONE() {
		Class<ApproveCommentResource> clazz = ApproveCommentResource.class;
        Method method;
		try {
			method = clazz.getDeclaredMethod("getAllComments");
			method.setAccessible(true);
			
			ApproveCommentResource instance = new ApproveCommentResource();
	        ArrayList<Comments> result1 = (ArrayList<Comments>) method.invoke(instance);
	        /* At this time the comments that have state 1 and wait to be approved are only Two
	         * so the size of the ArrayList must be 2 */
	        assertEquals(2, result1.size());
	        /* Check if the comment returned is the comment with the id 21 */
	        assertEquals(21, result1.get(0).getId());
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	// First if statement
	@Test
	public void testGetSoecificComments_WithCorrectParametersArticleIdAndCommentId_MustReturnCorrectComments() {
		/* We will test the case both article and comment id added */
		Class<ApproveCommentResource> clazz = ApproveCommentResource.class;
        Method method;
		try {
			method = clazz.getDeclaredMethod("getSpecificComments", String.class, String.class);
			method.setAccessible(true);
			
			ApproveCommentResource instance = new ApproveCommentResource();
	        ArrayList<Comments> result1 = (ArrayList<Comments>) method.invoke(instance, "13", "22");
	        /* At this time there are two comments that wait to be approved, the first has CommentID 21 and
	         * articleID 2 and the second one has CommentID 22 and articleID 13, so we will add
	         * the parameters for the second comment and we wait to get this comment */
	        assertEquals(1, result1.size());
	        assertEquals(22, result1.get(0).getId());
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	//Second if-else statement
	@Test
	public void testGetSoecificComments_WithCorrectParametersOnlyCommentId_MustReturnCorrectComments() {
		Class<ApproveCommentResource> clazz = ApproveCommentResource.class;
        Method method;
		try {
			method = clazz.getDeclaredMethod("getSpecificComments", String.class, String.class);
			method.setAccessible(true);
			
			ApproveCommentResource instance = new ApproveCommentResource();
	        ArrayList<Comments> result1 = (ArrayList<Comments>) method.invoke(instance, "", "21");
	        /* Now we have added only the comment id of a comment that waits to be approved,
	         * again we want the list to have size 1, and the returned Comment to have CommentID
	         * 21 */
	        assertEquals(1, result1.size());
	        assertEquals(21, result1.get(0).getId());
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	//Third if-else statement
	@Test
	public void testGetSoecificComments_WithCorrectParametersOnlyArticleId_MustReturnCorrectComments() {
		Class<ApproveCommentResource> clazz = ApproveCommentResource.class;
        Method method;
		try {
			method = clazz.getDeclaredMethod("getSpecificComments", String.class, String.class);
			method.setAccessible(true);
			
			ApproveCommentResource instance = new ApproveCommentResource();
	        ArrayList<Comments> result1 = (ArrayList<Comments>) method.invoke(instance, "2", "");
	        /* Now we have added only the article id of a comment that waits to be approved,
	         * again we want the list to have size 1, and the returned Comment to have CommentID
	         * 21 */
	        assertEquals(1, result1.size());
	        assertEquals(21, result1.get(0).getId());
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	//Fourth if-else statement (NULL)
	@Test
	public void testGetSoecificComments_WithBothEmpty_MustReturnNullList() {
		Class<ApproveCommentResource> clazz = ApproveCommentResource.class;
        Method method;
		try {
			method = clazz.getDeclaredMethod("getSpecificComments", String.class, String.class);
			method.setAccessible(true);
			
			ApproveCommentResource instance = new ApproveCommentResource();
	        ArrayList<Comments> result1 = (ArrayList<Comments>) method.invoke(instance, "", "");
	       assertEquals(null, result1);
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
}
