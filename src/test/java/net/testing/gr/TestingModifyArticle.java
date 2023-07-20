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
		
	    // We can not proceed if the role is null
	    Response response2 = m.handleDisplatAllArticles("", null);
	    assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response2.getStatus());
	    
	    // A visitor should not be able to access this function
	    Response response1 = m.handleDisplatAllArticles("", "VISITOR");
	    assertEquals(Response.Status.NOT_ACCEPTABLE.getStatusCode(), response1.getStatus());
	    
	    // If some other with a role not correct 
	    Response response3 = m.handleDisplatAllArticles("", "ANOTHER_ROLE_NOT_KNOWN");
	    assertEquals(Response.Status.NOT_ACCEPTABLE.getStatusCode(), response3.getStatus());    
	}
	
	@Test
	public void testModifyModifiedArticle_WithNullAndEmptyParameters_MustReturnCorrectResponses() {
		ModifyArticleResource m = new ModifyArticleResource();

		// null
		/* Here we should get INTERNAL_SERVER_ERROR (500) But we get NOT_FOUND (404)
		 * that is because the global variable ID_CLICKED is not initialised with an id of
		 * an article yet */
		Response response1 = m.modifyModifiedArticle(null, null, null, null);
	    assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response1.getStatus());
	    
	    /* If we want to test it correctly we need to first call the function getArticle
	     * to initialise the ID_CLICKED with an id of an article, lets say 2. */
	    m.getArticle("3", "A_USERNAME", "A_ROLE");
	    /// Now the ID_CLICKED is initialised with the value 3
	    /// So now we should get INTERNAL_SERVER_ERROR
	    Response response2 = m.modifyModifiedArticle(null, null, null, null);
	    assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response2.getStatus());
	    
	    Response response3 = m.modifyModifiedArticle("", null, null, null);
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
