package net.testing.gr;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.junit.Test;

import jakarta.ws.rs.core.Response;
import net.articles.ws.DisplayArticleResource_auth;

// We don't need to check everything, because we have already test some functions
// Auth and not auth user share the same functions...  

public class TestingDisplayArticle_auth_not_auth {

	@Test
	public void testHandleKeyPhrasesAuthUserArticles_WithNulAndEmpty_MustReturnCorrectResponse() {
		DisplayArticleResource_auth d = new DisplayArticleResource_auth();
		
	    // We can not proceed if the role is null
	    Response response2 = d.handleDisplatAllArticles("", null);
	    assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response2.getStatus());
	    
	    // A visitor should not be able to access this function
	    Response response1 = d.handleDisplatAllArticles("", "VISITOR");
	    assertEquals(Response.Status.NOT_ACCEPTABLE.getStatusCode(), response1.getStatus());
	    
	    // If some other with a role not correct 
	    Response response3 = d.handleDisplatAllArticles("", "ANOTHER_ROLE_NOT_KNOWN");
	    assertEquals(Response.Status.NOT_ACCEPTABLE.getStatusCode(), response3.getStatus());    
	}
	
	@Test
	public void testGetCreatorUsernameArticle_DB_WithWrongId_MustReturnCorrectError() {
		Class<DisplayArticleResource_auth> clazz = DisplayArticleResource_auth.class;
        Method method;
		try {
			method = clazz.getDeclaredMethod("getCreatorUsernameArticle_DB", String.class);
			method.setAccessible(true);
			
			String temp = null;
			DisplayArticleResource_auth instance = new DisplayArticleResource_auth();
	        String result1 = (String) method.invoke(instance, temp);
	        assertEquals(null, result1);
	        
	        /* If I add id equal to 13 I must get DimitraAlexa */
	        DisplayArticleResource_auth instance1 = new DisplayArticleResource_auth();
	        result1 = (String) method.invoke(instance1, "13");
	        assertEquals("DimitraAlexa", result1);
	               
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
}
