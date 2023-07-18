package net.testing.gr;

import java.lang.reflect.Method;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import jakarta.ws.rs.core.Response;
import net.articles.ws.ApproveArticleResource;

public class TestingApproveArticle {

	/* In the approve article we do not want a VISITOR to access.
	 * We test the case a visitor is trying to access it. We see that 
	 * the error is throwing correctly */
	@Test
	public void testStartMethod() {
	    ApproveArticleResource a = new ApproveArticleResource();

	    Response response = a.handleDisplatAllArticles("", "VISITOR");
	    assertEquals("ROLE_NOT_IDENTIFIED", response.getEntity());
	}
	
	/* We don't want the id to be null or empty */
    @Test
    public void testGetArticle_WithNullId_ShouldReturnNotFound() {
        ApproveArticleResource articleResource = new ApproveArticleResource();
        Response response = articleResource.getArticle(null, null, null);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }
    @Test
    public void testGetArticle_WithEmptyId_ShouldReturnNotFound() {
        ApproveArticleResource articleResource = new ApproveArticleResource();
        Response response = articleResource.getArticle("", null, null);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }
    
    /* Now lets test the other functions */
    
    /* WARNING!! WE ARE GOING TO TEST ONLY THE SELECT 
     * FUNCTIONS SO WE DONT CREATE ANY PROBLEM
     * IN THE DATABASE */
    
    /* The parameters we pass must not be null or empty */
    @Test
    public void testGetAllArticlesIDS_WithEmptyUsername_ShouldReturnNull() throws Exception {
        Class<ApproveArticleResource> clazz = ApproveArticleResource.class;
        Method method = clazz.getDeclaredMethod("getAllArticleIDS", String.class);
        method.setAccessible(true);

        ApproveArticleResource instance = new ApproveArticleResource();
        
        // NULL in username
        ArrayList<String> result1 = (ArrayList<String>) method.invoke(instance, "");

        assertEquals(null, result1);
        
        // EMPTY in username
        String temp = null;
        ArrayList<String> result2 = (ArrayList<String>) method.invoke(instance, temp);

        assertEquals(null, result2);
    }
    @Test
    public void testGetTitleArticle_WithEmptyId_ShouldReturnNull() throws Exception {
        Class<ApproveArticleResource> clazz = ApproveArticleResource.class;
        Method method = clazz.getDeclaredMethod("getTitleArticle_DB", String.class);
        method.setAccessible(true);

        ApproveArticleResource instance = new ApproveArticleResource();
        
        // NULL in id
        ArrayList<String> result1 = (ArrayList<String>) method.invoke(instance, "");

        assertEquals(null, result1);
        
        // EMPTY in id
        String temp = null;
        ArrayList<String> result2 = (ArrayList<String>) method.invoke(instance, temp);

        assertEquals(null, result2);
    }
    @Test
    public void testGetTopicArticle_WithEmptyId_ShouldReturnNull() throws Exception {
        Class<ApproveArticleResource> clazz = ApproveArticleResource.class;
        Method method = clazz.getDeclaredMethod("getTopicArticle_DB", String.class);
        method.setAccessible(true);

        ApproveArticleResource instance = new ApproveArticleResource();
        
        // NULL in id
        ArrayList<String> result1 = (ArrayList<String>) method.invoke(instance, "");

        assertEquals(null, result1);
        
        // EMPTY in id
        String temp = null;
        ArrayList<String> result2 = (ArrayList<String>) method.invoke(instance, temp);

        assertEquals(null, result2);
    }
    @Test
    public void testGetContents_WithEmptyId_ShouldReturnNull() throws Exception {
        Class<ApproveArticleResource> clazz = ApproveArticleResource.class;
        Method method = clazz.getDeclaredMethod("getContents_DB", String.class);
        method.setAccessible(true);

        ApproveArticleResource instance = new ApproveArticleResource();
        
        // NULL in id
        ArrayList<String> result1 = (ArrayList<String>) method.invoke(instance, "");

        assertEquals(null, result1);
        
        // EMPTY in id
        String temp = null;
        ArrayList<String> result2 = (ArrayList<String>) method.invoke(instance, temp);

        assertEquals(null, result2);
    }
}
