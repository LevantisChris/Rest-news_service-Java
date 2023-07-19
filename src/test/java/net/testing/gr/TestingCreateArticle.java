package net.testing.gr;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.junit.Test;

import jakarta.ws.rs.core.Response;
import net.articles.ws.ApproveArticleResource;
import net.articles.ws.CreateArticleResource;

public class TestingCreateArticle {
	
	@Test
	public void testStartMethod() {
		CreateArticleResource c = new CreateArticleResource();
	    Response response = c.handleFormCreation("", "VISITOR");
	    assertEquals("ROLE_NOT_IDENTIFIED", response.getEntity());
	}
	
	/* NOTE: With the following test we also check the function addInTheDB(...) because 
	 * this function is called by the function handleFormSubmission(...) */
	@Test
	public void testingHandleFormSubmission_WithNullOrEmptyParameters_MustReturnCorrectResponse() {
		CreateArticleResource c = new CreateArticleResource();
		Response response1 = c.handleFormSubmission(null, null, null, null);
		assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response1.getStatus());
		
		Response response2 = c.handleFormSubmission("", "", "", "");
		assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response2.getStatus());
	}
	
	/* NOTE: Here we test if the function takeTheAvailableTopics are returning null or not. */
	@Test
	public void testingTakeTheAvailableTopics_MustReturnNotNull() {
		Class<CreateArticleResource> clazz = CreateArticleResource.class;
        Method method;
		try {
			method = clazz.getDeclaredMethod("takeTheAvailableTopics");
			method.setAccessible(true);
			
			CreateArticleResource instance = new CreateArticleResource();
	        ArrayList<String> result1 = (ArrayList<String>) method.invoke(instance);
	        assertNotNull(result1);
		} catch (NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
