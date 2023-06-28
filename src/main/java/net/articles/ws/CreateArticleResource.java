package net.articles.ws;

import java.sql.Connection;
import java.sql.DriverManager;
import java.time.LocalDate;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Form;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import net.exceptions.ws.EmptyFields;
import net.exceptions.ws.NotIdentifiedRole;
import net.htmlhandler.ws.HtmlHandler;

@Path("/auth/auth_user/create_article")
public class CreateArticleResource {
	
	/// NOTE: This function only a JOURNALIST and a CURATOR can call it ...
	@GET
	public Response handleFormCreation(@QueryParam("username") String username, @QueryParam("role") String role) {
		
		System.out.println("SERVER STATUS --> CREATE ARTICLE CALLED BY USERNAME == " + username + " - ROLE == " + role);
		int ROLE_ID;
		try {
			if(role.equals("JOURNALIST")) {
				ROLE_ID = 2;
				
				return Response.status(Response.Status.UNAUTHORIZED)
                .entity(HtmlHandler.getCREATE_ARTICLE_HTML(username, role))
                .type(MediaType.TEXT_HTML)
                .build();
				
			} else if(role.equals("CURATOR")){
				ROLE_ID = 3;
				
				return Response.status(Response.Status.UNAUTHORIZED)
                .entity(HtmlHandler.getCREATE_ARTICLE_HTML(username, role))
                .type(MediaType.TEXT_HTML)
                .build();
				
			} else { throw new NotIdentifiedRole("ERROR: The role could not be identified.");}
		} catch(NotIdentifiedRole e) {
			System.out.print(e.getMessage());
			return Response.ok(e.getMessage()).build();
		}
	}
	
	@Path("/submit")
	@POST
	@Produces(MediaType.TEXT_HTML)
    public Response handleFormSubmission(@FormParam("topic") String topic, @FormParam("title") String title, @FormParam("content") String content) {
		
		System.out.println("-------------------------------------");
        System.out.println("THE ARTICLE THAT SUBMITED --> ");
		System.out.println("TITLE --> " + title);
        System.out.println("CONTENT --> " + content);
        System.out.println("TOPIC --> " + topic);
        System.out.println("-------------------------------------");
        
        ///We see if the fields are empty if one is empty then we send back an error ...
	    if(title.isEmpty() || content.isEmpty() || topic.isEmpty()) {
	    	  
	    	 return Response.status(Response.Status.BAD_REQUEST)
	    			.entity("Empty Fields...")
	    		    .type(MediaType.TEXT_HTML)
	                .build();
	    }	
        return Response.ok("Form submitted successfully").build();
    }
	
	/*public boolean addInTheDB(String title, String content, String topic) {
		String url = "jdbc:mysql://localhost:3306/news_db";
		String username = "root";
		String passwd = "kolos2020";
		
		Connection connection = DriverManager.getConnection(url, username, passwd);
		
		String query = "INSERT INTO ARTICLES (TITLE, CONTENT, DATE_CREATION, TOPIC_ID, STATE_ID, CREATOR_USERNAME) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
		
		/*PreparedStatement statement = connection.prepareStatement(query);
		statement.setString(1, title);
		statement.setString(2, content);
		statement.setString(3, LocalDate.now().toString());
		statement.setString(4, content);
	}*/
}
