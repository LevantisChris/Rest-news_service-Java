package net.testing.gr;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.junit.Test;

import jakarta.ws.rs.core.Response;
import net.articles.ws.manage_articles.Article;
import net.comments.ws.DisplayCommentsOfAnArticleResource_auth;
import net.comments.ws.manage_comments.Comments;

/// The same functions exist also in the not auth user. No need to test them.

public class TestingDisplayCommentsOfAnArticle_auth_not_auth {

	@Test
	public void testHandleDisplayAllArticles_WithNulAndEmpty_MustReturnCorrectResponse() {
		DisplayCommentsOfAnArticleResource_auth d = new DisplayCommentsOfAnArticleResource_auth();
		
	    // We can not proceed if the role is null
	    Response response2 = d.handleDisplayAllArticles("", null);
	    assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response2.getStatus());
	    
	    // A visitor should not be able to access this function
	    Response response1 = d.handleDisplayAllArticles("", "VISITOR");
	    assertEquals(Response.Status.NOT_ACCEPTABLE.getStatusCode(), response1.getStatus());
	    
	    // If some other with a role not correct 
	    Response response3 = d.handleDisplayAllArticles("", "ANOTHER_ROLE_NOT_KNOWN");
	    assertEquals(Response.Status.NOT_ACCEPTABLE.getStatusCode(), response3.getStatus());    
	}
	
	@Test
	public void testHandleDisplayComments_WithNullParameters_MustReturnServerError() {
		DisplayCommentsOfAnArticleResource_auth d = new DisplayCommentsOfAnArticleResource_auth();
		String temp = null;
		Response response2 = d.handleDisplayComments(temp, temp, temp);
	    assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response2.getStatus());
	}
	
	@Test
	public void testHandleDisplayComments_WithEmptyArticleID_MustReturnNoContent() {
		DisplayCommentsOfAnArticleResource_auth d = new DisplayCommentsOfAnArticleResource_auth();
		Response response2 = d.handleDisplayComments("", "A_USERNAME", "A_ROLE");
	    assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response2.getStatus());
	}
	
	@Test
	public void testGetSelectedArticle_WithCorrectId_MustReturnTheCorrectArticleBasedOneTheIdGiven() {
		Class<DisplayCommentsOfAnArticleResource_auth> clazz = DisplayCommentsOfAnArticleResource_auth.class;
        Method method;
		try {
			method = clazz.getDeclaredMethod("getSelectedArticle", int.class);
			method.setAccessible(true);
			
			DisplayCommentsOfAnArticleResource_auth instance = new DisplayCommentsOfAnArticleResource_auth();
	        Article result1 = (Article) method.invoke(instance, 9);
	        /* Must return the article with the ID 9 Title "BMW new M5 2020 review" etc */
	        assertEquals(9, result1.getId());
	        assertEquals("BMW new M5 2020 review", result1.getTitle());
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetSelectedArticle_WithInCorrectId_MustReturnInCorrectArticle() {
		Class<DisplayCommentsOfAnArticleResource_auth> clazz = DisplayCommentsOfAnArticleResource_auth.class;
        Method method;
		try {
			method = clazz.getDeclaredMethod("getSelectedArticle", int.class);
			method.setAccessible(true);
			
			DisplayCommentsOfAnArticleResource_auth instance = new DisplayCommentsOfAnArticleResource_auth();
	        Article result1 = (Article) method.invoke(instance, -1);
	        assertNotEquals("BMW new M5 2020 review", result1.getTitle());
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetCommentsOfArticle_WithCorrectArticleId_MustReturnTheAssosiatedComments() {
		Class<DisplayCommentsOfAnArticleResource_auth> clazz = DisplayCommentsOfAnArticleResource_auth.class;
        Method method;
		try {
			method = clazz.getDeclaredMethod("getCommentsOfArticle", int.class);
			method.setAccessible(true);
			
			DisplayCommentsOfAnArticleResource_auth instance = new DisplayCommentsOfAnArticleResource_auth();
	        ArrayList<Comments> result1 = (ArrayList<Comments>) method.invoke(instance, 3);
	        /* The article with ID 3 has four Comments (we assume we are in the role of the Curator) */
	        assertEquals(4, result1.size());
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetCommentsOfArticle_WithInCorrectArticleId_MustReturnEmptyList() {
		Class<DisplayCommentsOfAnArticleResource_auth> clazz = DisplayCommentsOfAnArticleResource_auth.class;
        Method method;
		try {
			method = clazz.getDeclaredMethod("getCommentsOfArticle", int.class);
			method.setAccessible(true);
			
			DisplayCommentsOfAnArticleResource_auth instance = new DisplayCommentsOfAnArticleResource_auth();
	        ArrayList<Comments> result1 = (ArrayList<Comments>) method.invoke(instance, -1);
	        /* The article with ID 3 has four Comments (we assume we are in the role of the Curator) */
	        assertEquals(0, result1.size());
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
}
