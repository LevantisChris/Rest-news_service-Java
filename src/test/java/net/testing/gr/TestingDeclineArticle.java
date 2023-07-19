package net.testing.gr;

import static org.junit.Assert.*;

import org.junit.Test;

import jakarta.ws.rs.core.Response;
import net.articles.ws.CreateArticleResource;
import net.articles.ws.DeclineArticleResource;

public class TestingDeclineArticle {

	@Test
	public void testHandleDisplatAllArticles_WithNullAndEmpty_MustReturnCorrectResponse() {
		DeclineArticleResource d = new DeclineArticleResource();
		
		Response response1 = d.handleDisplatAllArticles("", "JOURNALIST");
		assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response1.getStatus());
		
	    Response response2 = d.handleDisplatAllArticles("", "VISITOR");
	    assertEquals("ROLE_NOT_IDENTIFIED", response2.getEntity());
	    
	    Response response3 = d.handleDisplatAllArticles("", "CURATOR");
	    assertEquals(Response.Status.OK.getStatusCode(), response3.getStatus());
	}
	
	@Test
	public void testGetArticle_WithNullAndEmpty_MustReturnCorrectResponse() {
		DeclineArticleResource d = new DeclineArticleResource();
		
		// null id
		Response response1 = d.getArticle(null, "", "");
		assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response1.getStatus());
		
		// empty id
		Response response2 = d.getArticle("", "", "");
		assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response2.getStatus());
	}
	
	@Test
	public void testDeclineArticle_WithNullAndEmpty_MustReturnCorrectResponse() {
		DeclineArticleResource d = new DeclineArticleResource();

		// null cause
		Response response1 = d.declineArticle(null);
		assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response1.getStatus());
		
		// Empty cause
		Response response2 = d.declineArticle("");
		assertEquals(Response.Status.NOT_MODIFIED.getStatusCode(), response2.getStatus());
	}
	
}
