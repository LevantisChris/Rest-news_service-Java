package net.testing.gr;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.junit.Test;

import jakarta.ws.rs.core.Response;
import net.topics.ws.CreateTopicResource;

public class TestingCreateTopic {
	
	@Test
	public void testHandleFormCreation_WithRoleVisitor_MustReturnServerError() {
		CreateTopicResource c = new CreateTopicResource();
	    Response response = c.handleFormCreation("", "VISITOR");
	    assertEquals("ROLE_NOT_IDENTIFIED", response.getEntity());
	}
	
	@Test
	public void testHandleFormCreation_WithNullParamters_MustReturnServerError() {
		CreateTopicResource c = new CreateTopicResource();
	    Response response = c.handleFormCreation(null, null);
	    assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
	}
	
	/* NOTE: the function topicExists checks if a topic exists based on the title
	 * which is defined in the database as a UNIQUE attribute */
	@Test
	public void testTopicExists_WithTitleThatExists_MustReturnFalse() {
		Class<CreateTopicResource> clazz = CreateTopicResource.class;
        Method method;
		try {
			method = clazz.getDeclaredMethod("topicExists", String.class);
			method.setAccessible(true);
			
			CreateTopicResource instance = new CreateTopicResource();
	        boolean result1 = (boolean) method.invoke(instance, "BMW");
	        assertEquals(false, result1);
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testTopicExists_WithTitleThatNotExists_MustReturnTrue() {
		Class<CreateTopicResource> clazz = CreateTopicResource.class;
        Method method;
		try {
			method = clazz.getDeclaredMethod("topicExists", String.class);
			method.setAccessible(true);
			
			CreateTopicResource instance = new CreateTopicResource();
	        boolean result1 = (boolean) method.invoke(instance, "A title of a topic that does not exists");
	        assertEquals(true, result1);
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	/* At this time the DB have 9 approved (STATE_ID == 3) topics */
	@Test
	public void testTakeTheAvailableTopics_MustReturnAllTheAvailableTopics() {
		Class<CreateTopicResource> clazz = CreateTopicResource.class;
        Method method;
		try {
			method = clazz.getDeclaredMethod("takeTheAvailableTopics");
			method.setAccessible(true);
			
			CreateTopicResource instance = new CreateTopicResource();
	        ArrayList<String> result1 = (ArrayList<String>) method.invoke(instance);
	        assertEquals(9, result1.size());
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
}
