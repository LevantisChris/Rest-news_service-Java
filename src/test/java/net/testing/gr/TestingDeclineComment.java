
package net.testing.gr;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Test;

import net.comments.ws.DeclineCommentResource;

/* We don't need to test all the functions because they have the same
 * functionality as the functions we test in the Approve comments, 
 * we will only test the function deleteComment(...), because the deletion is 
 * an important and risky function */

public class TestingDeclineComment {

	/* We will try to delete the comment with the ID 23 */
	
	@Test
	public void testDeleteComment_WithCorrectCommentID_MustReturnTrue() {
		Class<DeclineCommentResource> clazz = DeclineCommentResource.class;
        Method method;
		try {
			method = clazz.getDeclaredMethod("deleteComment", int.class);
			method.setAccessible(true);
			
			DeclineCommentResource instance = new DeclineCommentResource();
	        boolean result1 = (boolean) method.invoke(instance, 23);
	        assertEquals(true, result1);
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
}
