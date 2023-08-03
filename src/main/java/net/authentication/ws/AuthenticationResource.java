package net.authentication.ws;

import net.htmlhandler.ws.*;


import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import jakarta.ws.rs.core.NewCookie;


@Path("/auth")
public class AuthenticationResource {
	
	/// Server --> HTML code --> client
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String getHello() {
		return HtmlHandler.AUTH_HTML; /// take the HTML code from the static class HtmlHandler
	}
	
	/// NOTE: Theoretically we need to make this function to not allowed 
	/// the user after the authentication to go back, but for now we will allowed the user
	/// to go back ...
	/// Client --> sends credentials --> Server
	@POST
    @Path("/auth_user")
    @Produces(MediaType.TEXT_HTML)
    public Response processLoginAuthUser(@FormParam("username") String username, @FormParam("password") String password) {
		   System.out.println("CALLED: /auth_user");
		   Authentication auth = new Authentication();
           Session SESSION_USER_AUTH = auth.checkCredentials(username, password);
           
           if(SESSION_USER_AUTH != null) {
        	   System.out.println("User created-authenticated ...");
        	   
        	   // Create a cookie that stores the SESSION_ID
               NewCookie sessionCookie = new NewCookie("session_id", String.valueOf(SESSION_USER_AUTH.getSESSION_ID()), "/", null, null, NewCookie.DEFAULT_MAX_AGE, false);
        	   
        	   if(SESSION_USER_AUTH.getUSER_BELONGS().getROLE_ID() == 2) { /// If ROLE_ID is equal with 2 that means the user is a journalist ...
        		   return Response.status(Response.Status.UNAUTHORIZED)
        				   .cookie(sessionCookie)
                           .entity(HtmlHandler.getJOURNALIST_HTML(SESSION_USER_AUTH.getSESSION_ID(), SESSION_USER_AUTH.getUSER_BELONGS().getUSERNAME(), SESSION_USER_AUTH.getUSER_BELONGS().getNAME(), SESSION_USER_AUTH.getUSER_BELONGS().getSURNAME()))
                           .type(MediaType.TEXT_HTML)
                           .build();
        	   } else { /// else the user is a curator ...
	        	   return   Response.status(Response.Status.UNAUTHORIZED)
	        			   .cookie(sessionCookie)
	        			   .entity(HtmlHandler.getCURATOR_HTML(SESSION_USER_AUTH.getSESSION_ID(), SESSION_USER_AUTH.getUSER_BELONGS().getUSERNAME(), SESSION_USER_AUTH.getUSER_BELONGS().getNAME(), SESSION_USER_AUTH.getUSER_BELONGS().getSURNAME()))
		                   .type(MediaType.TEXT_HTML)
		                   .build();
        	   }
           } else {
        	   System.out.println("User NOT created-authenticated (auth) ...");
        	   return Response.status(Response.Status.OK).entity("The credentials are NOT correct ...").build();
           }
    }
	
	/* NOTE: We are not store or need to authenticate a visitor, because the visitor is not-named */
	@POST
	@Path("/not_auth_user")
	@Produces(MediaType.TEXT_HTML)
	public Response processLoginNotAuthUser() {
		System.out.println("CALLED: /not_auth_user");
		Authentication auth = new Authentication();
        Session SESSION_USER_NOT_AUTH = auth.createSessionNotAuthUser();
		
        if(SESSION_USER_NOT_AUTH != null) {
        	NewCookie sessionCookie = new NewCookie("session_id", String.valueOf(SESSION_USER_NOT_AUTH.getSESSION_ID()), "/", null, null, NewCookie.DEFAULT_MAX_AGE, false);
        	return   Response.status(Response.Status.UNAUTHORIZED)
        			.cookie(sessionCookie)
                    .entity(HtmlHandler.getVISITOR_HTML())
                    .type(MediaType.TEXT_HTML)
                    .build();
        } else {
     	   System.out.println("User NOT created-authenticated (not-auth) ...");
     	   return Response.status(Response.Status.OK).entity("Cannot create a Session for Visitor ...").build();
        }
	}
}
