package net.testing.gr;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Test;

import jakarta.ws.rs.core.Response;
import net.articles.ws.ModifyArticleResource;

public class TestingModifyArticle {

	@Test
	public void testHandleKeyPhrasesAuthUserArticles_WithNulAndEmpty_MustReturnCorrectResponse() {
		ModifyArticleResource m = new ModifyArticleResource();
		
	    // We can not proceed if the session id is null
	    Response response2 = m.handleDisplatAllArticles(null);
	    assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response2.getStatus());
	    
	    // We can not proceed if the session id is empty
	    Response response3 = m.handleDisplatAllArticles("");
	    assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response3.getStatus());
	}
	
	@Test
	public void testModifyModifiedArticle_WithNullAndEmptyParameters_MustReturnCorrectResponses() {
		ModifyArticleResource m = new ModifyArticleResource();

		/* The user that have session id 12345, does not exist  */
		Response response1 = m.modifyModifiedArticle("12345", null, null, null, null);
	    assertEquals(Response.Status.UNAUTHORIZED.getStatusCode(), response1.getStatus());
	    
	    /* If we want to test it correctly we need to first call the function getArticle
	     * to initialise the ID_CLICKED with an id of an article, lets say 3. */
	    m.getArticle("123456", "3");
	    /// Now the ID_CLICKED is initialised with the value 3
	    /// So now we should get INTERNAL_SERVER_ERROR
	    Response response2 = m.modifyModifiedArticle("123456", null, null, null, null);
	    assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response2.getStatus());
	    
	    Response response3 = m.modifyModifiedArticle("123456", "", null, null, null);
	    assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response3.getStatus());
	}
	
	/* We are gonna use the article with ID 14 to test this,
	 * The function Must return "This the cause" */
	@Test
	public void testGetCause_WithArticleId_MustReturnCorrectCause() {
		ModifyArticleResource m = new ModifyArticleResource();

		Class<ModifyArticleResource> clazz = ModifyArticleResource.class;
        Method method;
		try {
			method = clazz.getDeclaredMethod("getCAUSE", int.class);
			method.setAccessible(true);
			
			ModifyArticleResource instance = new ModifyArticleResource();
	        String result1 = (String) method.invoke(instance, 14);
	        System.out.println("SERVER TESTING: TEST --MODIFY ARTICLE-- --> " + result1);
	        assertEquals("This the cause", result1);
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
}
