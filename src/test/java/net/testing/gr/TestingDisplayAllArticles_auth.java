package net.testing.gr;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import jakarta.ws.rs.core.Response;
import net.articles.ws.CreateArticleResource;
import net.articles.ws.DisplayAllArticlesResourceResource_auth;

public class TestingDisplayAllArticles_auth {

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
	    Response response6 = h.handleFilters("A_NAME", "", "2023-09-25", "2023-09-28");
	    assertEquals(Response.Status.OK.getStatusCode(), response6.getStatus());
	    
	    ///starDate is greater than endDate
	    //h.handleSort(true, false, "A_NAME", "CURATOR"); // we run that because the ArrayList filteredArray cannot be null
	    //Response response7 = h.handleFilters("A_NAME", "", "2023-09-20", "2023-09-10");
	    //assertEquals("START_DATE_GREATER_END_DATE", response7.getEntity());
	    
	}
	
}
