package net.testing.gr;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.junit.Test;

import jakarta.ws.rs.core.Response;
import net.topics.ws.ApproveTopicResource;
import net.topics.ws.DisplayAllTopicsResource_auth;

public class TestingDisplayAllTopics_auth_not_auth {

	@Test
	public void testHandleKeyPhrasesAuthUserArticles_WithRoleVisitor_MustReturnServerError() {
		DisplayAllTopicsResource_auth a = new DisplayAllTopicsResource_auth();
	    Response response = a.handleKeyPhrasesAuthUserArticles("", "VISITOR");
	    assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
	}
	
	@Test
	public void testHandleKeyPhrasesAuthUserArticles_withNullParameters_MustReturnNotFound() {
		DisplayAllTopicsResource_auth a = new DisplayAllTopicsResource_auth();
		Response response = a.handleKeyPhrasesAuthUserArticles(null, null);
	    assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
	}
	
	@Test
	public void testHandleSort_WithBothCheckBoxesClicked_MustReturnNotAcceptable() {
		DisplayAllTopicsResource_auth a = new DisplayAllTopicsResource_auth();
		Response repsonse = a.handleSort(false, false, "A_NAME", "A_ROLE");
		assertEquals(Response.Status.NOT_ACCEPTABLE.getStatusCode(), repsonse.getStatus());
	}
	
	@Test
	public void testHandleSort_WithSortByStateTrue_MustReturnOk() {
		DisplayAllTopicsResource_auth a = new DisplayAllTopicsResource_auth();
		Response repsonse = a.handleSort(false, true, "A_NAME", "CURATOR");
		assertEquals(Response.Status.OK.getStatusCode(), repsonse.getStatus());
	}
	
	@Test
	public void testHandleSort_WithSortByNameTrue_MustReturnOk() {
		DisplayAllTopicsResource_auth a = new DisplayAllTopicsResource_auth();
		Response repsonse = a.handleSort(true, false, "A_NAME", "CURATOR");
		assertEquals(Response.Status.OK.getStatusCode(), repsonse.getStatus());
	}
	
	@Test
	public void testHandleFilters_WithNullJson_MustReturnServerError() {
		DisplayAllTopicsResource_auth a = new DisplayAllTopicsResource_auth();
		Response repsonse = a.handleFilters(null);
		assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), repsonse.getStatus());
	}

	@Test
	public void testHandleFilters_withInCorrectJson_MustReturnServerError() {
		DisplayAllTopicsResource_auth a = new DisplayAllTopicsResource_auth();
		
		String JSON_STR = "{\"clickedByName\": \"A_NAME\", \"WrongRole\": \"CURATOR\", \"state\": \"2\", \"startDate\": 2023-05-25, \"endDate\": 2023-05-20}";

		Response repsonse = a.handleFilters(JSON_STR);
		assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), repsonse.getStatus());
	}
	
	/* The following test is about the function ficArrayString, this function tries to
	 * fix a String that has the following format " {"id":14} ". It fixes it by extracting
	 * the number of that is inside it, for this example is 14 */
	@Test
	public void testFixArrayList_WithCorrectFormatStrings_MustFixItAndTakeTheNumbers() {
		Class<DisplayAllTopicsResource_auth> clazz = DisplayAllTopicsResource_auth.class;
        Method method;
		try {
			method = clazz.getDeclaredMethod("fixArrayList", ArrayList.class);
			method.setAccessible(true);
			
			ArrayList<String> temp = new ArrayList<>();
			temp.add("{\"id\":14}");
			temp.add("{\"id\":17}");
			temp.add("{\"id\":20}");
			temp.add("{\"id\":25}");
			
			DisplayAllTopicsResource_auth instance = new DisplayAllTopicsResource_auth();
			ArrayList<String> result = (ArrayList<String>) method.invoke(instance, temp);
			
			/* We expect the values to be only number */
	        assertEquals(14, Integer.parseInt(result.get(0)));
	        assertEquals(25, Integer.parseInt(result.get(3)));
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
}
