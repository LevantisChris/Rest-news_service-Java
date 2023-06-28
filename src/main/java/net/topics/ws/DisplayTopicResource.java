package net.topics.ws;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

@Path("/auth/auth_user/display_topic")
public class DisplayTopicResource {
	
	@GET
	public Response displayTopic(@QueryParam("username") String username, @QueryParam("role") String role) {
		System.out.println("SERVER STATUS --> DISPLAY TOPIC CALLED BY USERNAME == " + username + " - ROLE == " + role);
		return Response.ok("Display Topic --> " + username + " - ROLE --> " + role).build();	
	}
}