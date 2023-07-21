package net.testing.gr;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.junit.Test;

import jakarta.ws.rs.core.Response;
import net.articles.ws.SearchArticleResource_auth;
import net.articles.ws.SearchArticleResource_notAuth;
import net.articles.ws.manage_articles.Article;

//Same for both auth and not auth user

public class TestingSearchArticle {

	/* We test for both an auth user and also for a not auth
	 * (handleKeyPhrasesAuthUserArticles and handleKeyPhrasesNotAuthUserArticles) */
	@Test
	public void testStartMethod() {
		SearchArticleResource_auth s_auth_user = new SearchArticleResource_auth();
	    Response response = s_auth_user.handleKeyPhrasesAuthUserArticles("", "VISITOR");
	    assertEquals("ROLE_NOT_IDENTIFIED", response.getEntity());
	    
	    SearchArticleResource_notAuth s_not_auth_user = new SearchArticleResource_notAuth();
	    response = s_not_auth_user.handleKeyPhrasesNotAuthUserArticles("JOURNALIST");
	    assertEquals("ROLE_NOT_IDENTIFIED", response.getEntity());
	    response = s_not_auth_user.handleKeyPhrasesNotAuthUserArticles("CURATOR");
	    assertEquals("ROLE_NOT_IDENTIFIED", response.getEntity());
	}
	
	@Test
	public void testSendDate_WithNullValues_MustReturnInternalServerError() {
		SearchArticleResource_auth s_auth_user = new SearchArticleResource_auth();
		Response response = s_auth_user.sendData(null, null, null);
	    assertEquals(Response.Status.INTERNAL_SERVER_ERROR, response.getStatusInfo());
	    
	    SearchArticleResource_notAuth s_not_auth_user = new SearchArticleResource_notAuth();
	    response = s_not_auth_user.sendData(null, null);
	}
	
	/* Here we are going to test the functionality 
	 * of the hasWords that tells us
	 * of a string has one or more words */
	@Test
	public void testhasWords_WithStringOneWordAndStringTwoOrMoreWords_MustReturnCorrectBoolean() {
		SearchArticleResource_auth s_auth_user = new SearchArticleResource_auth();

		Class<SearchArticleResource_auth> clazz = SearchArticleResource_auth.class;
        Method method;
		try {
			method = clazz.getDeclaredMethod("hasWords", String.class);
			method.setAccessible(true);
			
			// First Test with a String that contains only one word
			SearchArticleResource_auth instance = new SearchArticleResource_auth();
	        boolean result1 = (boolean) method.invoke(instance, "Word");
	        assertEquals(false, result1); // one word
	        
	        // Now test it with string that contains more than one (two) String
	        instance = new SearchArticleResource_auth();
	        boolean result2 = (boolean) method.invoke(instance, "Two Words");
	        assertEquals(true, result2); // more than one word
	        
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	/* Now we are going to test the function that searches and finds the ones that corresponds to the 
	 * KeyPhrases the user add */
	@Test
	public void testSearchTitleAndContentsOneWord_WithKeyPhrasesThatContainOneWord_MustReturnCorrectArticles() {
		Class<SearchArticleResource_auth> clazz = SearchArticleResource_auth.class;
        Method method;
		try {
			// We need to initialise the ROLE_ID with 3 (CURATOR), so we are trying to access it
			try {
			    Field roleIdField = SearchArticleResource_auth.class.getDeclaredField("ROLE_ID");
			    roleIdField.setAccessible(true);
			    SearchArticleResource_auth instance1 = new SearchArticleResource_auth();
			    // Set the value of ROLE_ID to 3 (CURATOR)
			    roleIdField.set(instance1, 3);
			    int roleIdValue = (int) roleIdField.get(instance1);
			    System.out.println("ROLE_ID value: " + roleIdValue);
			} catch (NoSuchFieldException e) {
			    System.out.println("ERROR: The ROLE_ID field was not found in the class.");
			    e.printStackTrace();
			} catch (IllegalAccessException e) {
			    System.out.println("ERROR: Access to the ROLE_ID field was denied.");
			    e.printStackTrace();
			}
			
			//
			// Run the method getAllArticlesFromDB to initialise the ArrayList articles_list
			method = clazz.getDeclaredMethod("getAllArticlesFromDB", String.class);
			method.setAccessible(true);
			SearchArticleResource_auth instance = new SearchArticleResource_auth();
	        method.invoke(instance, "DimitraAlexa");
	        //
			// Run searchTitleAndContentsOneWord
	        method = clazz.getDeclaredMethod("searchTitleAndContentsOneWord", String.class, String.class);
			method.setAccessible(true);
			method.invoke(instance, "Ferrari", "");
	        //
	        // Check if the GOAL_ARTICLES have now the correct values
	        Field goalArticlesField;
			try {
				goalArticlesField = SearchArticleResource_auth.class.getDeclaredField("GOAL_ARTICLES");
				goalArticlesField.setAccessible(true);
		        ArrayList<Article> goalArticles = (ArrayList<Article>) goalArticlesField.get(instance);   
		        assertEquals(1, goalArticles.size()); // Must have size 1 
		        assertEquals(8, goalArticles.get(0).getId());
			} catch (NoSuchFieldException e) {
				System.out.println("SERVER STATUS: --ERROR-- OCCURED IN ACCESING PRIVATE ARRAYLIST IN TESTINGSEARCHARTICLE AT testSearchTitleAndContentsOneWord_WithKeyPhrasesThatContainOneWord_MustReturnCorrectArticles");
				e.printStackTrace();
			}
			//
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
		    Throwable cause = e.getCause();
		    if (cause != null) {
		        cause.printStackTrace();
		    } else {
		        e.printStackTrace();
		    }
		}
	}
	
	@Test
	public void testSplitStrings_WithStr_MustReturnAllTheWordsInAnArrayList() {
		Class<SearchArticleResource_auth> clazz = SearchArticleResource_auth.class;
        Method method;
		try {
			method = clazz.getDeclaredMethod("splitStrings", String.class);
			method.setAccessible(true);
			SearchArticleResource_auth instance = new SearchArticleResource_auth();
	        ArrayList<String> result1 = (ArrayList<String>) method.invoke(instance, "This is a test");
	        assertEquals(4, result1.size()); // the arrayList that is returned must have size 4 ...
	        assertEquals("This", result1.get(0)); // the first element must be equal to this
	        assertEquals("test", result1.get(3)); // the last must be equal to test
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSearchTitleTwoWords_WithATitleArrayMoreThanOneWord_MustAddCorrectArticles() {
		Class<SearchArticleResource_auth> clazz = SearchArticleResource_auth.class;
        Method method;
		try {
			// We need to initialise the ROLE_ID with 3 (CURATOR), so we are trying to access it
			try {
			    Field roleIdField = SearchArticleResource_auth.class.getDeclaredField("ROLE_ID");
			    roleIdField.setAccessible(true);
			    SearchArticleResource_auth instance1 = new SearchArticleResource_auth();
			    // Set the value of ROLE_ID to 3 (CURATOR)
			    roleIdField.set(instance1, 3);
			    int roleIdValue = (int) roleIdField.get(instance1);
			    System.out.println("ROLE_ID value: " + roleIdValue);
			} catch (NoSuchFieldException e) {
			    System.out.println("ERROR: The ROLE_ID field was not found in the class.");
			    e.printStackTrace();
			} catch (IllegalAccessException e) {
			    System.out.println("ERROR: Access to the ROLE_ID field was denied.");
			    e.printStackTrace();
			}
			
			//
			// Run the method getAllArticlesFromDB to initialise the ArrayList articles_list
			method = clazz.getDeclaredMethod("getAllArticlesFromDB", String.class);
			method.setAccessible(true);
			SearchArticleResource_auth instance = new SearchArticleResource_auth();
	        method.invoke(instance, "DimitraAlexa");
	        //
	        //Create a titleArray
	        ArrayList<String> titleArray = new ArrayList<>();
	        titleArray.add("Toyota");
	        titleArray.add("Corolla");
	        //
			// Run searchTitleTwoWords
	        method = clazz.getDeclaredMethod("searchTitleTwoWords", ArrayList.class);
			method.setAccessible(true);
			method.invoke(instance, titleArray);
	        //
	        // Check if the GOAL_ARTICLES have now the correct values
	        Field goalArticlesField;
			try {
				goalArticlesField = SearchArticleResource_auth.class.getDeclaredField("GOAL_ARTICLES");
				goalArticlesField.setAccessible(true);
		        ArrayList<Article> goalArticles = (ArrayList<Article>) goalArticlesField.get(instance);   
		        assertEquals(2, goalArticles.size()); // Must have size 2 
		        assertEquals(6, goalArticles.get(0).getId());
		        assertEquals(15, goalArticles.get(1).getId());
			} catch (NoSuchFieldException e) {
				System.out.println("SERVER STATUS: --ERROR-- OCCURED IN ACCESING PRIVATE ARRAYLIST IN TESTINGSEARCHARTICLE AT testSearchTitleAndContentsOneWord_WithKeyPhrasesThatContainOneWord_MustReturnCorrectArticles");
				e.printStackTrace();
			}
			//
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
		    Throwable cause = e.getCause();
		    if (cause != null) {
		        cause.printStackTrace();
		    } else {
		        e.printStackTrace();
		    }
		}
	}
	
	@Test
	public void testSearchContentTwoWords_WithATitleArrayMoreThanOneWord_MustAddCorrectArticles() {
		Class<SearchArticleResource_auth> clazz = SearchArticleResource_auth.class;
        Method method;
		try {
			// We need to initialise the ROLE_ID with 3 (CURATOR), so we are trying to access it
			try {
			    Field roleIdField = SearchArticleResource_auth.class.getDeclaredField("ROLE_ID");
			    roleIdField.setAccessible(true);
			    SearchArticleResource_auth instance1 = new SearchArticleResource_auth();
			    // Set the value of ROLE_ID to 3 (CURATOR)
			    roleIdField.set(instance1, 3);
			    int roleIdValue = (int) roleIdField.get(instance1);
			    System.out.println("ROLE_ID value: " + roleIdValue);
			} catch (NoSuchFieldException e) {
			    System.out.println("ERROR: The ROLE_ID field was not found in the class.");
			    e.printStackTrace();
			} catch (IllegalAccessException e) {
			    System.out.println("ERROR: Access to the ROLE_ID field was denied.");
			    e.printStackTrace();
			}
			
			//
			// Run the method getAllArticlesFromDB to initialise the ArrayList articles_list
			method = clazz.getDeclaredMethod("getAllArticlesFromDB", String.class);
			method.setAccessible(true);
			SearchArticleResource_auth instance = new SearchArticleResource_auth();
	        method.invoke(instance, "DimitraAlexa");
	        //
	        //Create a titleArray
	        ArrayList<String> titleArray = new ArrayList<>();
	        titleArray.add("SportsCar");
	        titleArray.add("Music");
	        //
			// Run searchTitleTwoWords
	        method = clazz.getDeclaredMethod("searchContentTwoWords", ArrayList.class);
			method.setAccessible(true);
			method.invoke(instance, titleArray);
	        //
	        // Check if the GOAL_ARTICLES have now the correct values
	        Field goalArticlesField;
			try {
				goalArticlesField = SearchArticleResource_auth.class.getDeclaredField("GOAL_ARTICLES");
				goalArticlesField.setAccessible(true);
		        ArrayList<Article> goalArticles = (ArrayList<Article>) goalArticlesField.get(instance);   
		        assertEquals(1, goalArticles.size()); // Must have size 2 
		        assertEquals(16, goalArticles.get(0).getId());
			} catch (NoSuchFieldException e) {
				System.out.println("SERVER STATUS: --ERROR-- OCCURED IN ACCESING PRIVATE ARRAYLIST IN TESTINGSEARCHARTICLE AT testSearchTitleAndContentsOneWord_WithKeyPhrasesThatContainOneWord_MustReturnCorrectArticles");
				e.printStackTrace();
			}
			//
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
		    Throwable cause = e.getCause();
		    if (cause != null) {
		        cause.printStackTrace();
		    } else {
		        e.printStackTrace();
		    }
		}
	}
}
