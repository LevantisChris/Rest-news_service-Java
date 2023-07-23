package net.testing.gr;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Test;

import jakarta.ws.rs.core.Response;
import net.topics.ws.DisplayArticlesOfATopicResource_auth;

public class TestingDisplayArticlesOfATopic_auth_not_auth {

	@Test
	public void testHandleStartPage_WithRoleVisitor_MustReturnServerError() {
		DisplayArticlesOfATopicResource_auth a = new DisplayArticlesOfATopicResource_auth();
	    Response response = a.handleStartPage("", "VISITOR");
	    assertEquals(Response.Status.NOT_ACCEPTABLE.getStatusCode(), response.getStatus());
	}
	
	@Test
	public void testHandleTopicArticles_WithCorectInputs_MustReturnOk() {
		DisplayArticlesOfATopicResource_auth a = new DisplayArticlesOfATopicResource_auth();
		Response response = a.handleTopicArticles("A_USERNAME", "CURATOR", "Cars");
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
	}
	
	@Test
	public void testHandleTopicArticles_WithNullParameters_MustReturnNotFound() {
		DisplayArticlesOfATopicResource_auth a = new DisplayArticlesOfATopicResource_auth();
		Response response = a.handleTopicArticles(null, null, null);
		assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
	}
	
	@Test
	public void testGetTopicId_WithTitleATitleThatExistsCar_MustReturnTheId_2() {
		Class<DisplayArticlesOfATopicResource_auth> clazz = DisplayArticlesOfATopicResource_auth.class;
        Method method;
		try {
			method = clazz.getDeclaredMethod("getTopicID", String.class);
			method.setAccessible(true);
			DisplayArticlesOfATopicResource_auth instance = new DisplayArticlesOfATopicResource_auth();
			int result = (int) method.invoke(instance, "Cars");
			assertEquals(2, result);
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetTopicId_WithTitleThatDontExist_MustReturnNegative_1() {
		Class<DisplayArticlesOfATopicResource_auth> clazz = DisplayArticlesOfATopicResource_auth.class;
        Method method;
		try {
			method = clazz.getDeclaredMethod("getTopicID", String.class);
			method.setAccessible(true);
			DisplayArticlesOfATopicResource_auth instance = new DisplayArticlesOfATopicResource_auth();
			int result = (int) method.invoke(instance, "A topic that dont exist");
			assertEquals(-1, result);
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
}
