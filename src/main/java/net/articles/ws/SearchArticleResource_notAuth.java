package net.articles.ws;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import net.htmlhandler.ws.HtmlHandler;

//// FOR VISITOR

@Path("/auth/not_auth_user/search_article")
public class SearchArticleResource_notAuth {
	
	@GET
	public Response handleKeyPhrasesNotAuthUserArticles(@QueryParam("role") String role) {
		System.out.println("SERVER STATUS --> SEARCH ARTICLE CALLED BY USERNAME == " + "--NULL--" + " - ROLE == " + role);
		int ROLE_ID;
		if(role.equals("VISITOR")) {
			ROLE_ID = 1;
			return Response.status(Response.Status.OK)
	                .entity(HtmlHandler.getSEARCH_ARTICLE_KEY_PHRASES_HTML("", ROLE_ID))
	                .type(MediaType.TEXT_HTML)
	                .build();
		} else { // if someone else get here we have a problem ...
			return Response.serverError().build();
		}
	}
	
	@GET
	@Path("/search")
	public Response sendData(@QueryParam("titleKeyPhrases") String titleKeyPhrases, @QueryParam("contentKeyPhrases") String contentKeyPhrases) {
		System.out.println("VISITOR, Title key phrases --> " + titleKeyPhrases);
		System.out.println("VISITOR, Content key phrases --> " + contentKeyPhrases);
		return Response.ok("OKK VISITOR!!").build();
	}
}
