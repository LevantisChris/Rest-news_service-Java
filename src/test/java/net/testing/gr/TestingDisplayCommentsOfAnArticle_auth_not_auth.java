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
		
	    // We can not proceed if the session id is null
	    Response response2 = d.handleDisplayAllArticles(null);
	    assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response2.getStatus());
	    
	    // We can not procced if the session id is empty
	    Response response3 = d.handleDisplayAllArticles("");
	    assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response3.getStatus());
	}
	
	@Test
	public void testHandleDisplayComments_WithNullParameters_MustReturnServerError() {
		/// null in session
		DisplayCommentsOfAnArticleResource_auth d = new DisplayCommentsOfAnArticleResource_auth();
		String temp = null;
		Response response2 = d.handleDisplayComments(temp, temp);
	    assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response2.getStatus());
	    
	    /// null article id
	    Response response3 = d.handleDisplayComments("123456", temp);
	    assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response3.getStatus());
	}
	
	@Test
	public void testHandleDisplayComments_WithEmptyArticleID_MustReturnNoContent() {
		DisplayCommentsOfAnArticleResource_auth d = new DisplayCommentsOfAnArticleResource_auth();
		/// empty article id
	    Response response4 = d.handleDisplayComments("123456", "");
	    assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response4.getStatus());
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
