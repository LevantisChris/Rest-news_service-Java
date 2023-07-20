package net.testing.gr;

import static org.junit.Assert.*;

import java.util.Date;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.junit.Test;

import jakarta.ws.rs.core.Response;
import net.articles.ws.DisplayAllArticlesResourceResource_auth;
import net.articles.ws.manage_articles.Article;

/// Auth and not auth user share the same functions... 

public class TestingDisplayAllArticles_auth_not_auth {

	@Test
	public void testHandleKeyPhrasesAuthUserArticles_WithNulAndEmpty_MustReturnCorrectResponse() {
		DisplayAllArticlesResourceResource_auth h = new DisplayAllArticlesResourceResource_auth();
		
	    // We can not proceed if the role is null
	    Response response2 = h.handleKeyPhrasesAuthUserArticles("", null);
	    assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response2.getStatus());
	    
	    // A visitor should not be able to access this function
	    Response response1 = h.handleKeyPhrasesAuthUserArticles("", "VISITOR");
	    assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response1.getStatus());
	    
	    // If some other with a role not correct 
	    Response response3 = h.handleKeyPhrasesAuthUserArticles("", "ANOTHER_ROLE_NOT_KNOWN");
	    assertEquals(Response.Status.NOT_ACCEPTABLE.getStatusCode(), response3.getStatus());
	    
	}
	
	@Test
	public void testhandleSort_WithNullAndEmpty_MustReturnCorrectResponse() {
		DisplayAllArticlesResourceResource_auth h = new DisplayAllArticlesResourceResource_auth();
		
		/// The name and the role are null OR empty
		// The name is null and the role is empty
	    Response response1 = h.handleSort(true, true, null, "");
	    assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response1.getStatus());

		// the name is empty and the role is empty
	    Response response2 = h.handleSort(true, true, "", "");
	    assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response2.getStatus());
	    
	    ///Both sortByState and sortByDate are selected
	    Response response3 = h.handleSort(true, true, "A_NAME", "JOURNALIST");
	    assertEquals("CLICK_ONLY_ONE_CHECKBOX", response3.getEntity());
	    
	    ///Only Sort By state should return ok
	    Response response4 = h.handleSort(true, false, "A_NAME", "JOURNALIST");
	    assertEquals(Response.Status.OK.getStatusCode(), response4.getStatus());
	    
	    ///Only Sort By date should return ok
	    Response response5 = h.handleSort(false, true, "A_NAME", "JOURNALIST");
	    assertEquals(Response.Status.OK.getStatusCode(), response5.getStatus());
	    
	    ///If someone with role that is not Journalist or Curator try to access the function
	    Response response6 = h.handleSort(false, true, "A_NAME", "ANOTHER_ROLE");
	    assertEquals(Response.Status.UNAUTHORIZED.getStatusCode(), response6.getStatus());
	}
	
	@Test
	public void testHndleFilters_WithNullAndEmpty_MustReturnCorrectRespoonse() {
		DisplayAllArticlesResourceResource_auth h = new DisplayAllArticlesResourceResource_auth();

		///startDate and endDate are both null
		Response response1 = h.handleFilters(null, null, null, null);
	    assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response1.getStatus());
	    
	    ///startDate is empty and endDate is not empty
	    Response response2 = h.handleFilters("A_NAME", "3", "", "2023-09-25");
	    assertEquals("ADD_START_DATE", response2.getEntity());
	    
	    ///startDate is not empty and endDate is empty
	    Response response4 = h.handleFilters("A_NAME", "3", "2023-09-25", "");
	    assertEquals("ADD_END_DATE", response4.getEntity());
	    
	    ///state is not empty, and both dates are empty
	    h.handleSort(true, false, "A_NAME", "CURATOR"); // we run that because the ArrayList filteredArray cannot be null
	    Response response5 = h.handleFilters("A_NAME", "3", "", "");
	    assertEquals(Response.Status.OK.getStatusCode(), response5.getStatus());
	    
	    ///state is empty and dates are not
	    h.handleSort(true, false, "A_NAME", "CURATOR"); // we run that because the ArrayList filteredArray cannot be null
	    Response response6 = h.handleFilters("A_NAME", "", "07-20-2023", "07-25-2023");
	    assertEquals(Response.Status.OK.getStatusCode(), response6.getStatus());
	    
	    ///starDate is greater than endDate
	    h.handleSort(true, false, "A_NAME", "CURATOR"); // we run that because the ArrayList filteredArray cannot be null
	    Response response7 = h.handleFilters("A_NAME", "", "07-20-2023", "07-19-2023");
	    assertEquals("START_DATE_GREATER_END_DATE", response7.getEntity());
	    
	    ///starDate is lesser than endDate
	    h.handleSort(true, false, "A_NAME", "CURATOR"); // we run that because the ArrayList filteredArray cannot be null
	    Response response8 = h.handleFilters("A_NAME", "", "07-20-2023", "07-21-2023");
	    assertNotEquals("START_DATE_GREATER_END_DATE", response8.getEntity());
	    
	    ///starDate is equal than endDate
	    h.handleSort(true, false, "A_NAME", "CURATOR"); // we run that because the ArrayList filteredArray cannot be null
	    Response response9 = h.handleFilters("A_NAME", "", "07-20-2023", "07-20-2023");
	    assertNotEquals("START_DATE_GREATER_END_DATE", response9.getEntity());
	    
	    ///State is equal to 5 (the states are: 1 2 3 4)
	    h.handleSort(true, false, "A_NAME", "CURATOR"); // we run that because the ArrayList filteredArray cannot be null
	    Response response10 = h.handleFilters("A_NAME", "5", "", "");
	    assertEquals("INCORRECT_STATE", response10.getEntity());
	    
	    ///State is equal to 3 (the states are: 1 2 3 4)
	    h.handleSort(true, false, "A_NAME", "CURATOR"); // we run that because the ArrayList filteredArray cannot be null
	    Response response11 = h.handleFilters("A_NAME", "3", "", "");
	    assertNotEquals("INCORRECT_STATE", response11.getEntity());
	}
	
	/* Start Date greater than the End Date */
	@Test
	public void testCheckDates_WithWrongDates_MustReturnFalse() {		
		Class<DisplayAllArticlesResourceResource_auth> clazz = DisplayAllArticlesResourceResource_auth.class;
        Method method;
		try {
			method = clazz.getDeclaredMethod("checkDates", String.class, String.class);
			method.setAccessible(true);
			
			DisplayAllArticlesResourceResource_auth instance = new DisplayAllArticlesResourceResource_auth();
	        boolean result1 = (boolean) method.invoke(instance, "2023-07-21", "2023-07-20");
	        assertEquals(false, result1);
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testCheckState_WithWrongState_MustReturnFalse() {
		Class<DisplayAllArticlesResourceResource_auth> clazz = DisplayAllArticlesResourceResource_auth.class;
        Method method;
		try {
			method = clazz.getDeclaredMethod("checkState", int.class);
			method.setAccessible(true);
			
			DisplayAllArticlesResourceResource_auth instance = new DisplayAllArticlesResourceResource_auth();
	        boolean result1 = (boolean) method.invoke(instance, 5);
	        assertEquals(false, result1);
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testFilterByState_WithTestingArticlesAndStateFilterEqualToFour_MustFilterThemCorrectly() {
		/// We create an array to test the functionality of the method
		Article  a1 = new Article(1, "This is the Title One", "This the content One", parseDate("2023-07-20"), 4, "TestUser1");
		Article  a2 = new Article(2, "This is the Title Two", "This the content Two", parseDate("2023-07-25"), 2, "TestUser2");
		Article  a3 = new Article(3, "This is the Title Three", "This the content Three", parseDate("2023-07-15"), 4, "TestUser3");
		ArrayList<Article> articles = new ArrayList<>();
		articles.add(a1);
		articles.add(a2);
		articles.add(a3);
		
		
		Class<DisplayAllArticlesResourceResource_auth> clazz = DisplayAllArticlesResourceResource_auth.class;
        Method method;
		try {
			method = clazz.getDeclaredMethod("filterByState", ArrayList.class, int.class);
			method.setAccessible(true);
			
			DisplayAllArticlesResourceResource_auth instance = new DisplayAllArticlesResourceResource_auth();
	        ArrayList<Article> result1 = (ArrayList<Article>) method.invoke(instance, articles, 4);
	    
	        assertEquals(2, result1.size()); // Must contain only 2 articles
	        
	        assertTrue(result1.stream().anyMatch(article -> article.getId() == 1)); // 1 and 3 must returned because they have state 4
	        assertTrue(result1.stream().anyMatch(article -> article.getId() == 3));
	        
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testFilterByDate_WithTestingArticlesAndDates_MustFilterThemCorrectly() {
		/// We create an array to test the functionality of the method
		Article  a1 = new Article(1, "This is the Title One", "This the content One", parseDate("2023-07-20"), 4, "TestUser1");
		Article  a2 = new Article(2, "This is the Title Two", "This the content Two", parseDate("2023-07-25"), 2, "TestUser2");
		Article  a3 = new Article(3, "This is the Title Three", "This the content Three", parseDate("2023-07-15"), 4, "TestUser3");				ArrayList<Article> articles = new ArrayList<>();
		articles.add(a1);
		articles.add(a2);
		articles.add(a3);
		
		Class<DisplayAllArticlesResourceResource_auth> clazz = DisplayAllArticlesResourceResource_auth.class;
        Method method;
		try {
			method = clazz.getDeclaredMethod("filterByDate", ArrayList.class, Date.class, Date.class);
			method.setAccessible(true);
			
			DisplayAllArticlesResourceResource_auth instance = new DisplayAllArticlesResourceResource_auth();
	        ArrayList<Article> result1 = (ArrayList<Article>) method.invoke(instance, articles, parseDate("2023-07-15"), parseDate("2023-07-20"));
	    
	        assertEquals(2, result1.size()); // Must contain only 2 articles
	        
	        assertTrue(result1.stream().anyMatch(article -> article.getId() == 1)); // 1 and 3 must returned because they have state 4
	        assertTrue(result1.stream().anyMatch(article -> article.getId() == 3));
	        
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testFilterByStateAndDate_WithTestingArticlesAndStatesAndDates_MustFilterThemCorrectly() {
		/// We create an array to test the functionality of the method
		Article  a1 = new Article(1, "This is the Title One", "This the content One", parseDate("2023-07-20"), 4, "TestUser1");
		Article  a2 = new Article(2, "This is the Title Two", "This the content Two", parseDate("2023-07-25"), 2, "TestUser2");
		Article  a3 = new Article(3, "This is the Title Three", "This the content Three", parseDate("2023-07-15"), 4, "TestUser3");				ArrayList<Article> articles = new ArrayList<>();
		articles.add(a1);
		articles.add(a2);
		articles.add(a3);
		
		Class<DisplayAllArticlesResourceResource_auth> clazz = DisplayAllArticlesResourceResource_auth.class;
        Method method;
		try {
			method = clazz.getDeclaredMethod("filterByStateAndDate", ArrayList.class, int.class, Date.class, Date.class);
			method.setAccessible(true);
			
			DisplayAllArticlesResourceResource_auth instance = new DisplayAllArticlesResourceResource_auth();
	        ArrayList<Article> result1 = (ArrayList<Article>) method.invoke(instance, articles, 2, parseDate("2023-07-20"), parseDate("2023-07-25"));
	    
	        assertEquals(1, result1.size()); // Must contain only 2 articless
	        
	        assertTrue(result1.stream().anyMatch(article -> article.getId() == 2));	        
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	
	
	private static Date parseDate(String dateString) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
	
}
