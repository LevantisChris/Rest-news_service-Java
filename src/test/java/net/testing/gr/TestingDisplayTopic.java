package net.testing.gr;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.junit.Test;

import net.topics.ws.DisplayTopicResource_auth;

public class TestingDisplayTopic {

	
	/* The important functions to test here is the functions that
	 * give us the Parent and the kids of a topic (getParentTopicTopic_DB, getKidsTopic_DB) */
	

	@Test
	public void testGetParentTopicTopic_DB_WithTopicId13Football_MustReturnTheParentWhichIsSports() {
		Class<DisplayTopicResource_auth> clazz = DisplayTopicResource_auth.class;
        Method method;
		try {
			method = clazz.getDeclaredMethod("getParentTopicTopic_DB", String.class);
			method.setAccessible(true);
			DisplayTopicResource_auth instance = new DisplayTopicResource_auth();
			String result = (String) method.invoke(instance, String.valueOf(13));
			assertEquals("Sports", result);
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	/* The kids at this time is  13, 14, 15, 16, 21*/
	@Test
	public void testGetKidsTopic_DB_WithTopicIdSports6_MustReturnAllTheKids() {
		
		//Expected ArrayList that contains the correct/expected values
		// Topics that have as parent the topic with id 6 and state id 3
		ArrayList<String> EXPECTED_RESULT = new ArrayList<>();
		EXPECTED_RESULT.add("Football");
		EXPECTED_RESULT.add("Basketball");
		EXPECTED_RESULT.add("Tennis");
		EXPECTED_RESULT.add("Volley");
		
		Class<DisplayTopicResource_auth> clazz = DisplayTopicResource_auth.class;
        Method method;
		try {
			method = clazz.getDeclaredMethod("getKidsTopic_DB", String.class);
			method.setAccessible(true);
			DisplayTopicResource_auth instance = new DisplayTopicResource_auth();
			ArrayList<String> result = (ArrayList<String>) method.invoke(instance, String.valueOf(6));
			
			assertEquals(EXPECTED_RESULT.size(), result.size());
			
			for(int i = 0;i < result.size();i++) {
				assertEquals(EXPECTED_RESULT.get(i), result.get(i));
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
