package net.articles.ws;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/auth/auth_user/display_topic")
public class DisplayTopicResource {
	
	@GET
	public Response displayTopic() {
		return Response.ok("Display Topic").build();
	}
}
