package net.topics.ws;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import net.exceptions.ws.NotIdentifiedRole;
import net.htmlhandler.ws.HtmlHandler;

@Path("/auth/auth_user/search_topic")
public class SearchTopicResource_auth {

	@GET
	public Response handleKeyPhrasesAuthUserArticles(@QueryParam("username") String username, @QueryParam("role") String role) {
		System.out.println("SERVER STATUS: SEARCH TOPIC (auth_user) CALLED BY USERNAME == " + username + " - ROLE == " + role);
		int ROLE_ID;
		try {
			if(role.equals("VISITOR")) { // if a visitor gets here we have a problem ...
				ROLE_ID = 1;
				return Response.serverError().build();
			} else if(role.equals("JOURNALIST")) {
				System.out.println("SERVER STATUS: ROLE: --JOURNALIST-- IN THE SEARCH TOPIC");
				ROLE_ID = 2;
				
				return Response.status(Response.Status.OK)
                .entity(HtmlHandler.getSEARCH_TOPIC_KEY_PHRASES_HTML(username, role, ROLE_ID))
                .type(MediaType.TEXT_HTML)
                .build();
				
			} else if(role.equals("CURATOR")){
				System.out.println("SERVER STATUS: ROLE: --CURATOR-- IN THE SEARCH TOPIC");
				ROLE_ID = 3;
				
				return Response.status(Response.Status.OK)
		                .entity(HtmlHandler.getSEARCH_TOPIC_KEY_PHRASES_HTML(username, role, ROLE_ID))
		                .type(MediaType.TEXT_HTML)
		                .build();
				
			} else { throw new NotIdentifiedRole("ERROR: The role could not be identified.");}
		} catch(NotIdentifiedRole e) {
			System.out.print(e.getMessage());
			return Response.ok(e.getMessage()).build();
		}
	}
	
	@GET
	@Path("/search")
	public Response sendData(@QueryParam("username") String username,
	                         @QueryParam("role") String role,
	                         @QueryParam("titleKeyPhrases") String titleKeyPhrases) {
	    System.out.println("username --> " + username);
	    System.out.println("role --> " + role);
	    System.out.println("titleKeyPhrases --> " + titleKeyPhrases);

	    if(username == null || role == null || titleKeyPhrases == null) { // If something is null return error
	    	return Response.serverError().build();
	    } else if(titleKeyPhrases.isEmpty()) { // if the key is empty we can not proceed
	    	return Response.ok("KEY_IS_EMPTY").build();
	    } else { 
	    	if(hasWords(titleKeyPhrases) == true) {
	    	    return Response.ok("MORE THAN ONE WORD").build();
	    	} else if(hasWords(titleKeyPhrases) == false) {
	    	    return Response.ok("ONLY ONE WORD").build();
	    	} else {
	    		return Response.serverError().build();
	    	}
	    }
	}

	
	/*---------------------------------------------------------------------------------------------------------------------------------------*/
	
	/* NOTE: This func checks if the a string has one or more words */ 
	public static boolean hasWords(String input) {
		String [] array = input.trim().split(" ");
		if(array.length == 1){
			return false; // it has only one word
		} else{
			return true; // it has more than one word
		}
	}
}
