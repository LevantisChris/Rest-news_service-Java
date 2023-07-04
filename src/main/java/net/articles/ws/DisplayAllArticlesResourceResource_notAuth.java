package net.articles.ws;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import net.htmlhandler.ws.HtmlHandler;

@Path("/auth/not_auth_user/displayAll_article")
public class DisplayAllArticlesResourceResource_notAuth {

	private static int ROLE_ID;
	
	@GET
	public Response handleKeyPhrasesNotAuthUserArticles(@QueryParam("role") String role) {
		System.out.println("SERVER STATUS: DISPLAY ALL ARTICLE CALLED BY USERNAME == " + "--NULL--" + " - ROLE == " + role);
		int ROLE_ID;
		if(role.equals("VISITOR")) {
			ROLE_ID = 1;
			
			return Response.ok("VISITOR").build();
			
		} else { // if someone else get here we have a problem ...
			return Response.serverError().build();
		}
	}
	
}
