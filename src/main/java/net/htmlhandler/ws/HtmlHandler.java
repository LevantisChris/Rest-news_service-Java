package net.htmlhandler.ws;

import java.util.ArrayList;

/* HERE WE ARE GOING TO HAVE ALL THE HTML CODES THAT WE WILL NEED IN OUR SERVICE */

public class HtmlHandler {
	public static String AUTH_HTML = "<!DOCTYPE html>\n" +
	        "<html>\n" +
	        "<head>\n" +
	        "  <title>Log in</title>\n" +
	        "  <style>\n" +
	        "    body {\n" +
	        "      text-align: center;\n" +
	        "    }\n" +
	        "  </style>\n" +
	        "</head>\n" +
	        "<body>\n" +
	        "  <h1>Log in to our service</h1>\n" +
	        "  <p>Type your username and password</p>\n" +
	        "  <form action=\"/RESTstart/rest/auth/auth_user\" method=\"post\">\n" +
	        "    <input type=\"text\" name=\"username\" placeholder=\"Username\"><br>\n" +
	        "    <input type=\"password\" name=\"password\" placeholder=\"Password\"><br>\n" +
	        "    <input type=\"submit\" value=\"Login\">\n" +
	        "  </form>\n" +
	        "  <form action=\"/RESTstart/rest/auth/not_auth_user\" method=\"post\">\n" +
	        "    <input type=\"submit\" value=\"Visitor\">\n" +
	        "  </form>\n" +
	        "</body>\n" +
	        "</html>";
	
	public static String getJOURNALIST_HTML(String username, String name, String surname) {
		return  "<!DOCTYPE html>\n" +
		        "<html>\n" +
		        "<head>\n" +
		        "  <title>Journalist Control Center</title>\n" +
		        "  <style>\n" +
		        "    body {\n" +
		        "      text-align: center;\n" +
		        "    }\n" +
		        "    .center {\n" +
		        "      display: flex;\n" +
		        "      flex-direction: column;\n" +
		        "      justify-content: center;\n" +
		        "      align-items: center;\n" +
		        "      height: 100vh;\n" +
		        "    }\n" +
		        "    .link {\n" +
		        "      margin-bottom: 10px;\n" +
		        "    }\n" +
		        "  </style>\n" +
		        "</head>\n" +
		        "<body>\n" +
		        "  <div class=\"center\">\n" +
		        "    <h1>Welcome " + name + " " + surname + " - Role: " + "JOURNALIST" + "</h1>\n" +
		        "	 <a class=\"link\" href=\"/RESTstart/rest/auth/auth_user/create_article?username=" + username + "&role=" + "JOURNALIST" + "\">Create Article</a>\n" +
		        "	 <a class=\"link\" href=\"/RESTstart/rest/auth/auth_user/modify_article?username=" + username + "&role=" + "JOURNALIST" + "\">Modify Article</a>\n" +
		        "    <a class=\"link\" href=\"#\">Submit Article</a>\n" +
		        "    <a class=\"link\" href=\"#\">Search Article</a>\n" +
		        "    <a class=\"link\" href=\"#\">Display Article</a>\n" +
		        "    <a class=\"link\" href=\"#\">Display all the Articles</a>\n" +
		        "    <hr>\n" +
		        "    <a class=\"link\" href=\"#\">Add Comment</a>\n" +
		        "    <a class=\"link\" href=\"#\">Display Comments of an Article</a>\n" +
		        "    <hr>\n" +
		        "	 <a class=\"link\" href=\"/RESTstart/rest/auth/auth_user/display_topic?username=" + username + "&role=" + "JOURNALIST" + "\">Display Topic</a>\n" +
		        "    <a class=\"link\" href=\"#\">Display all the Topics</a>\n" +
		        "    <a class=\"link\" href=\"#\">Search Topic</a>\n" +
		        "    <a class=\"link\" href=\"#\">Create Topic</a>\n" +
		        "    <a class=\"link\" href=\"#\">Modify Topic</a>\n" +
		        "  </div>\n" +
		        "</body>\n" +
		        "</html>";
	}
	
	/// NOTE: We send back the username so we can know which user is requesting ...
	public static String getCURATOR_HTML(String username, String name, String surname) {
		return  "<!DOCTYPE html>\n" +
		        "<html>\n" +
		        "<head>\n" +
		        "  <title>Curator Control Center</title>\n" +
		        "  <style>\n" +
		        "    body {\n" +
		        "      text-align: center;\n" +
		        "    }\n" +
		        "    .center {\n" +
		        "      display: flex;\n" +
		        "      flex-direction: column;\n" +
		        "      justify-content: center;\n" +
		        "      align-items: center;\n" +
		        "      height: 100vh;\n" +
		        "    }\n" +
		        "    .link {\n" +
		        "      margin-bottom: 10px;\n" +
		        "    }\n" +
		        "  </style>\n" +
		        "</head>\n" +
		        "<body>\n" +
		        "  <div class=\"center\">\n" +
		        "    <h1>Welcome " + name + " " + surname + " - Role: " + "CURATOR" + "</h1>\n" +
		        "	 <a class=\"link\" href=\"/RESTstart/rest/auth/auth_user/create_article?username=" + username + "&role=" + "CURATOR" + "\">Create Article</a>\n" +
		        "	 <a class=\"link\" href=\"/RESTstart/rest/auth/auth_user/modify_article?username=" + username + "&role=" + "CURATOR" + "\">Modify Article</a>\n" +
		        "    <a class=\"link\" href=\"#\">Submit Article</a>\n" +
		        "    <a class=\"link\" href=\"#\">Accept Article</a>\n" +
		        "    <a class=\"link\" href=\"#\">Decline Article</a>\n" +
		        "    <a class=\"link\" href=\"#\">Article Publication</a>\n" +
		        "    <a class=\"link\" href=\"#\">Search Article</a>\n" +
		        "    <a class=\"link\" href=\"#\">Display Article</a>\n" +
		        "    <a class=\"link\" href=\"#\">Display all the Articles</a>\n" +
		        "    <hr>\n" +
		        "    <a class=\"link\" href=\"#\">Add Comment</a>\n" +
		        "    <a class=\"link\" href=\"#\">Modify Comment</a>\n" +
		        "    <a class=\"link\" href=\"#\">Accept Comment</a>\n" +
		        "    <a class=\"link\" href=\"#\">Decline Comment</a>\n" +
		        "    <a class=\"link\" href=\"#\">Display Comment of an Article</a>\n" +
		        "    <hr>\n" +
		        "    <a class=\"link\" href=\"#\">Create Topic</a>\n" +
		        "    <a class=\"link\" href=\"#\">Modify Topic</a>\n" +
		        "    <a class=\"link\" href=\"#\">Accept Topic</a>\n" +
		        "    <a class=\"link\" href=\"#\">Decline Topic</a>\n" +
		        "	 <a class=\"link\" href=\"/RESTstart/rest/auth/auth_user/display_topic?username=" + username + "&role=" + "CURATOR" + "\">Display Topic</a>\n" +
		        "    <a class=\"link\" href=\"#\">Display all the Topics</a>\n" +
		        "    <a class=\"link\" href=\"#\">Search Topics</a>\n" +
		        "    <a class=\"link\" href=\"#\">Display Articles of a Topic</a>\n" +
		        "  </div>\n" +
		        "</body>\n" +
		        "</html>";
	}
	
	public static String getVISITOR_HTML() {
		return  "<!DOCTYPE html>\n" +
	              "<html>\n" +
	              "<head>\n" +
	              "  <title>Visitor Control Center</title>\n" +
	              "  <style>\n" +
	              "    body {\n" +
	              "      text-align: center;\n" +
	              "    }\n" +
	              "    .center {\n" +
	              "      display: flex;\n" +
	              "      flex-direction: column;\n" +
	              "      justify-content: center;\n" +
	              "      align-items: center;\n" +
	              "      height: 100vh;\n" +
	              "    }\n" +
	              "    .link {\n" +
	              "      margin-bottom: 10px;\n" +
	              "    }\n" +
	              "  </style>\n" +
	              "</head>\n" +
	              "<body>\n" +
	              "  <div class=\"center\">\n" +
	              "    <h1>Welcome - Role: " + "VISITOR" + "</h1>\n" +
	              "    <a class=\"link\" href=\"#\">Search Article</a>\n" +
	              "    <a class=\"link\" href=\"#\">Display Article</a>\n" +
	              "    <a class=\"link\" href=\"#\">Display all the Articles</a>\n" +
	              "    <hr>\n" +
	              "    <a class=\"link\" href=\"#\">Add Comment</a>\n" +
	              "    <a class=\"link\" href=\"#\">Display Comment of an Article</a>\n" +
	              "    <hr>\n" +
	              "<a class=\"link\" href=\"/RESTstart/rest/auth/auth_user/display_topic?role=" + "VISITOR" + "\">Display Topic</a>\n" +
	              "    <a class=\"link\" href=\"#\">Display all the Topics</a>\n" +
	              "    <a class=\"link\" href=\"#\">Search Topics</a>\n" +
	              "  </div>\n" +
	              "</body>\n" +
	              "</html>";
	}
	
	
	/// NOTE: In this HTML code in the POST I also include the username (hidden) because later in the 
	/// insertion in the database I will need to add also the CREATOR of the article
	///
	/// NOTE: FOR CONVENIENCE WE WILL ASSUME THAT THERE ARE ONLY TWO TOPICS 
	public static String getCREATE_ARTICLE_HTML(ArrayList<String> TOPICS_LIST, String username, String role) {
		
		StringBuilder topicsString;
		if(TOPICS_LIST.size() != 0) {
			topicsString = new StringBuilder();
		    for (String topic : TOPICS_LIST) {
		        topicsString.append(topic).append(", ");
		    }
		    // Remove the trailing comma and space
		    if (topicsString.length() > 0) {
		        topicsString.setLength(topicsString.length() - 2);
		    }
		} else {
			topicsString = new StringBuilder();
			topicsString.append("NO TOPICS CREATED"); // if it is empty we assume that there are not topics inside the database ...
		}
		
		return  "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <title>Create Article</title>\n" +
                "    <style>\n" +
                "        body {\n" +
                "            display: flex;\n" +
                "            justify-content: center;\n" +
                "            align-items: center;\n" +
                "            height: 100vh;\n" +
                "        }\n" +
                "        .container {\n" +
                "            text-align: center;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div class=\"container\">\n" +
                "        <h1>USERNAME: " + username + " - ROLE: " + role + "</h1>\n" +
                "        <h1>Create an Article {THE TOPICS ARE: " + topicsString + "} </h1>\n" +
                "        <form action=\"/RESTstart/rest/auth/auth_user/create_article/submit\" method=\"post\">\n" +
                "            <input type=\"hidden\" id=\"username\" name=\"username\" value=\"" + username + "\">\n" +
                "            <label for=\"topic\">Topic:</label>\n" +
                "            <input type=\"text\" id=\"topic\" name=\"topic\">\n" +
                "            <br>\n" +
                "            <label for=\"title\">Title:</label>\n" +
                "            <input type=\"text\" id=\"title\" name=\"title\">\n" +
                "            <br>\n" +
                "            <label for=\"content\">Content:</label>\n" +
                "            <br>\n" +
                "            <textarea id=\"content\" name=\"content\" rows=\"10\" cols=\"30\"></textarea>\n" +
                "            <br>\n" +
                "            <button type=\"submit\">Create</button>\n" +
                "        </form>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>";

	}
	
	public static String getMODIFY_ARTICLE_HTML(ArrayList<String> ARTICLES_IDs, String username, String role, String title, String topic, String content) {
		String frameHTML = "<div class=\"ids-frame\">";
		
		for (int i = 0; i < ARTICLES_IDs.size(); i++) {
			frameHTML += "<a href=\"/RESTstart/rest/auth/auth_user/modify_article/" + ARTICLES_IDs.get(i) + "?method=GET\">" + ARTICLES_IDs.get(i) + "</a> ";
		}
		
		frameHTML += "</div>";
		
		String htmlCode = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <title>Modify an Article</title>\n" +
                "    <style>\n" +
                "        body {\n" +
                "            display: flex;\n" +
                "            justify-content: center;\n" +
                "            align-items: center;\n" +
                "            height: 100vh;\n" +
                "        }\n" +
                "        .container {\n" +
                "            text-align: center;\n" +
                "        }\n" +
                "        .ids-frame {\n" +
                "            margin-bottom: 20px;\n" +
                "        }\n" +
                "        .ids-frame a {\n" +
                "            display: inline-block;\n" +
                "            margin-right: 5px;\n" +
                "            text-decoration: underline;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div class=\"container\">\n" +
                "        <h1>Modify an Article</h1>\n" +
                "        " + frameHTML + "\n" +
                "        <form action=\"/RESTstart/rest/auth/auth_user/modify_article/modify\" method=\"post\">\n" +
                "            <label for=\"topic\">Topic:</label>\n" +
                "            <input type=\"text\" id=\"topic\" name=\"topic\" value=\"" + topic + "\">\n" +
                "            <br>\n" +
                "            <label for=\"title\">Title:</label>\n" +
                "            <input type=\"text\" id=\"title\" name=\"title\" value=\"" + title + "\">\n" +
                "            <br>\n" +
                "            <label for=\"content\">Content:</label>\n" +
                "            <br>\n" +
                "            <textarea id=\"content\" name=\"content\" rows=\"10\" cols=\"30\">" + content + "</textarea>\n" +
                "            <br>\n" +
                "            <button type=\"submit\">Modify</button>\n" +
                "        </form>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>";
		return htmlCode;

	}
}
