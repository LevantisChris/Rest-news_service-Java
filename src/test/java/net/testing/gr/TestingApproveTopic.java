package net.testing.gr;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Test;

import jakarta.ws.rs.core.Response;
import net.topics.ws.ApproveTopicResource;

public class TestingApproveTopic {

	@Test
	public void testStartMethod() {
		ApproveTopicResource a = new ApproveTopicResource();
	    Response response = a.handleDisplayAllArticles(null);
	    assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
	    
	    response = a.handleDisplayAllArticles("");
	    assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
	}
	
	
	@Test
	public void testHandlegetTopic_WithCorrectId_MustReturnOk() {
		ApproveTopicResource a = new ApproveTopicResource();
		Response response = a.handlegetTopic("123456", "19"); // 19 is a ID for a topic
	    assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
	}
	
	@Test
	public void testHandlegetTopic_WithNullId_MustReturnNotFound() {
		ApproveTopicResource a = new ApproveTopicResource();
		String temp = null;
		Response response = a.handlegetTopic("123456", temp); 
	    assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
	}
	
	@Test
	public void testHandleModificatiOnOfTopic_WithNullJson_MustReturnServerError() {
		ApproveTopicResource a = new ApproveTopicResource();
		Response response = a.handleModificatiOnOfTopic("123456", null);
		assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
	}
	
	@Test
	public void testHandleModificatiOnOfTopic_WithInCorrectJson_MustReturnServerError() {
		ApproveTopicResource a = new ApproveTopicResource();
		String JSON_STR = "{\"titleINCORRECT\": \"This is the title\", \"parentTopic\": \"This is the parent Topic\", \"topic_id_picked\": \"This is the topic picked\"}";
		Response response = a.handleModificatiOnOfTopic("123456", JSON_STR);
		assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
	}
	
	@Test
	public void testGetParentTopicTopic_DB_WithCorrectId_MustReturnCorrectParentId() {
		Class<ApproveTopicResource> clazz = ApproveTopicResource.class;
        Method method;
		try {
			method = clazz.getDeclaredMethod("getParentTopicTopic_DB", String.class);
			method.setAccessible(true);
			
			ApproveTopicResource instance = new ApproveTopicResource();
	        String result1 = (String) method.invoke(instance, "13");
	        assertEquals("Sports", result1);
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetParentTopicTopic_D_WithTopicThatDontHaveParentId_MustReturnNullString() {
		Class<ApproveTopicResource> clazz = ApproveTopicResource.class;
        Method method;
		try {
			method = clazz.getDeclaredMethod("getParentTopicTopic_DB", String.class);
			method.setAccessible(true);
			
			ApproveTopicResource instance = new ApproveTopicResource();
	        String result1 = (String) method.invoke(instance, "6");
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
