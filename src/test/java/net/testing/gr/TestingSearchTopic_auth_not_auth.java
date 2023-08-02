package net.testing.gr;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.junit.Test;

import jakarta.ws.rs.core.Response;
import net.topics.ws.DisplayTopicResource_auth;
import net.topics.ws.SearchTopicResource_auth;
import net.topics.ws.manage_topics.Topic;

public class TestingSearchTopic_auth_not_auth {

	@Test
	public void testHandleKeyPhrasesAuthUserArticles_WithNullAndEmpty_MustReturnCorrectResponse() {
		SearchTopicResource_auth s = new SearchTopicResource_auth();
		
		Response response1 = s.handleKeyPhrasesAuthUserArticles("");
		assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response1.getStatus());
		
		Response response2 = s.handleKeyPhrasesAuthUserArticles(null);
		assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response2.getStatus());
	}
	
	@Test
	public void testSendDate_WithNullValues_MustReturnServerError() {
		SearchTopicResource_auth s = new SearchTopicResource_auth();
		Response response1 = s.sendData("123456", null);
		assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response1.getStatus());
	}
	
	@Test
	public void testSendDate_WithEmptyKeyPhrase_MustReturnNotFound() {
		SearchTopicResource_auth s = new SearchTopicResource_auth();
		Response response1 = s.sendData("123456", "");
		assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response1.getStatus());
	}
	
	@Test
	public void testSendData_WithCorrectKeyPhrase_MustReturnOk() {
		SearchTopicResource_auth s = new SearchTopicResource_auth();
		Response response1 = s.sendData("123456", "BMW");
		assertEquals(Response.Status.OK.getStatusCode(), response1.getStatus());
	}
	
	/* We will add the key phrase BMW, and expect to get only one item 
	 * 
	 * Same for Journalist*/
	@Test
	public void testSearchTwoWords_WithCorrectKeyPhrase_MustReturnAppropriateTopics() {
		Class<SearchTopicResource_auth> clazz = SearchTopicResource_auth.class;
        Method method;
		try {
			method = clazz.getDeclaredMethod("searchTwoWord", String.class, String.class, ArrayList.class);
			method.setAccessible(true);
			SearchTopicResource_auth instance = new SearchTopicResource_auth();
			
			ArrayList<String> temp = new ArrayList<>();
			temp.add("Citroen C1");
			
			ArrayList<Topic> result = (ArrayList<Topic>) method.invoke(instance, "A_USERNAME", "CURATOR", temp);
			assertEquals("Citroen C1", result.get(0).getTitle());
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	/* NOTE: With role Curator all the topics are returned */
	@Test
	public void testSearchOneWord_RoleCurator_WithCorrectKeyPhrase_MustReturnAppropriateTopics() {
		
		ArrayList<String> EXPECTED_TITLES = new ArrayList<>();
		EXPECTED_TITLES.add("Citroen C1");
		EXPECTED_TITLES.add("Citroen C4");
		EXPECTED_TITLES.add("Citroen C2");
		
		Class<SearchTopicResource_auth> clazz = SearchTopicResource_auth.class;
        Method method;
		try {
			method = clazz.getDeclaredMethod("searchOneWord", String.class, String.class, String.class);
			method.setAccessible(true);
			SearchTopicResource_auth instance = new SearchTopicResource_auth();
			
			ArrayList<Topic> result = (ArrayList<Topic>) method.invoke(instance, "A_USERNAME", "CURATOR", "Citroen");
			
			assertEquals(EXPECTED_TITLES.size(), result.size());
			
			for(int i = 0;i < result.size();i++) {
				assertEquals(EXPECTED_TITLES.get(i), result.get(i).getTitle());
			}
			
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	/* NOTE: the Journalist can see only the approved topics */
	@Test
	public void testSearchOneWord_RoleJournalist_WithCorrectKeyPhrase_MustReturnAppropriateTopics() {
		
		ArrayList<String> EXPECTED_TITLES = new ArrayList<>();
		EXPECTED_TITLES.add("Citroen C2");
		
		Class<SearchTopicResource_auth> clazz = SearchTopicResource_auth.class;
        Method method;
		try {
			method = clazz.getDeclaredMethod("searchOneWord", String.class, String.class, String.class);
			method.setAccessible(true);
			SearchTopicResource_auth instance = new SearchTopicResource_auth();
			
			ArrayList<Topic> result = (ArrayList<Topic>) method.invoke(instance, "A_USERNAME", "JOURNALIST", "Citroen");
			
			assertEquals(EXPECTED_TITLES.size(), result.size());
			
			for(int i = 0;i < result.size();i++) {
				assertEquals(EXPECTED_TITLES.get(i), result.get(i).getTitle());
			}
			
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
}
