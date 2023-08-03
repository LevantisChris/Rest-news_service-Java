package net.htmlhandler.ws;

import java.util.ArrayList;
import java.util.HashSet;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import net.articles.ws.manage_articles.Article;
import net.comments.ws.manage_comments.Comments;
import net.topics.ws.manage_topics.Topic;

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
	
	public static String getJOURNALIST_HTML(int SESSION_ID, String username, String name, String surname) {
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
		        "	 <a class=\"link\" href=\"/RESTstart/rest/auth/auth_user/create_article\">Create Article</a>\n" +
		        "	 <a class=\"link\" href=\"/RESTstart/rest/auth/auth_user/modify_article\">Modify Article</a>\n" +
		        "	 <a class=\"link\" href=\"/RESTstart/rest/auth/auth_user/submit_article\">Submit Article</a>\n" +
		        "	 <a class=\"link\" href=\"/RESTstart/rest/auth/auth_user/search_article\">Search Article</a>\n" +
		        "	 <a class=\"link\" href=\"/RESTstart/rest/auth/auth_user/display_article\">Display Article</a>\n" +
		        "	 <a class=\"link\" href=\"/RESTstart/rest/auth/auth_user/displayAll_article\">Display all the Articles/Add Comment</a>\n" +
		        "    <hr>\n" +
		        "	 <a class=\"link\" href=\"/RESTstart/rest/auth/auth_user/displayCommentsOfArticle_comment\">Display Comments of an article</a>\n" +
		        "    <hr>\n" +
		        "	 <a class=\"link\" href=\"/RESTstart/rest/auth/auth_user/create_topic\">Create topic</a>\n" +
		        "	 <a class=\"link\" href=\"/RESTstart/rest/auth/auth_user/modify_topic\">Modify topic</a>\n" +
		        "	 <a class=\"link\" href=\"/RESTstart/rest/auth/auth_user/display_topic\">Display Topic</a>\n" +
		        "	 <a class=\"link\" href=\"/RESTstart/rest/auth/auth_user/displayAll_topic\">Display all the Topics</a>\n" +
		        "	 <a class=\"link\" href=\"/RESTstart/rest/auth/auth_user/search_topic\">Search topic</a>\n" +
		        "	 <a class=\"link\" href=\"/RESTstart/rest/auth/auth_user/displayArticlesOfTopic_topic\">Display articles of a topic</a>\n" +
		        "  </div>\n" +
		        "</body>\n" +
		        "</html>";
	}
	
	/// NOTE: We send back the username so we can know which user is requesting ...
	public static String getCURATOR_HTML(int SESSION_ID, String username, String name, String surname) {
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
		        "	 <a class=\"link\" href=\"/RESTstart/rest/auth/auth_user/create_article\">Create Article</a>\n" +
		        "	 <a class=\"link\" href=\"/RESTstart/rest/auth/auth_user/modify_article\">Modify Article</a>\n" +
		        "	 <a class=\"link\" href=\"/RESTstart/rest/auth/auth_user/submit_article\">Submit Article</a>\n" +
		        "	 <a class=\"link\" href=\"/RESTstart/rest/auth/auth_user/approve_article\">Approve Article</a>\n" +
		        "	 <a class=\"link\" href=\"/RESTstart/rest/auth/auth_user/decline_article\">Decline Article</a>\n" +
		        "	 <a class=\"link\" href=\"/RESTstart/rest/auth/auth_user/publish_article\">Article publication</a>\n" +
		        "	 <a class=\"link\" href=\"/RESTstart/rest/auth/auth_user/search_article\">Search Article</a>\n" +
		        "	 <a class=\"link\" href=\"/RESTstart/rest/auth/auth_user/display_article\">Display Article</a>\n" +
		        "	 <a class=\"link\" href=\"/RESTstart/rest/auth/auth_user/displayAll_article\">Display all the Articles/Add Comment</a>\n" +
		        "    <hr>\n" +
		        "	 <a class=\"link\" href=\"/RESTstart/rest/auth/auth_user/modify_comment\">Modify Comment</a>\n" +
		        "	 <a class=\"link\" href=\"/RESTstart/rest/auth/auth_user/approve_comment\">Approve Comment</a>\n" +
		        "	 <a class=\"link\" href=\"/RESTstart/rest/auth/auth_user/decline_comment\">Decline Comment</a>\n" +
		        "	 <a class=\"link\" href=\"/RESTstart/rest/auth/auth_user/displayCommentsOfArticle_comment\">Display Comments of an article</a>\n" +
		        "    <hr>\n" +
		        "	 <a class=\"link\" href=\"/RESTstart/rest/auth/auth_user/create_topic\">Create topic</a>\n" +
		        "	 <a class=\"link\" href=\"/RESTstart/rest/auth/auth_user/modify_topic\">Modify topic</a>\n" +
		        "	 <a class=\"link\" href=\"/RESTstart/rest/auth/auth_user/approve_topic\">Approve topic</a>\n" +
		        "	 <a class=\"link\" href=\"/RESTstart/rest/auth/auth_user/decline_topic\">Decline topic</a>\n" +
		        "	 <a class=\"link\" href=\"/RESTstart/rest/auth/auth_user/display_topic\">Display Topic</a>\n" +
		        "	 <a class=\"link\" href=\"/RESTstart/rest/auth/auth_user/displayAll_topic\">Display all the Topics</a>\n" +
		        "	 <a class=\"link\" href=\"/RESTstart/rest/auth/auth_user/search_topic\">Search topic</a>\n" +
		        "	 <a class=\"link\" href=\"/RESTstart/rest/auth/auth_user/displayArticlesOfTopic_topic\">Display articles of a topic</a>\n" +
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
			      "	 <a class=\"link\" href=\"/RESTstart/rest/auth/not_auth_user/search_article\">Search Article</a>\n" +
			      "	 <a class=\"link\" href=\"/RESTstart/rest/auth/not_auth_user/display_article\">Display Article</a>\n" +
			      "	 <a class=\"link\" href=\"/RESTstart/rest/auth/not_auth_user/displayAll_article\">Display all the Articles/Add Comment</a>\n" +
	              "    <hr>\n" +
			      "	 <a class=\"link\" href=\"/RESTstart/rest/auth/not_auth_user/displayCommentsOfArticle_comment\">Display Comments of an article</a>\n" +
	              "    <hr>\n" +
			        "	 <a class=\"link\" href=\"/RESTstart/rest/auth/not_auth_user/display_topic\">Display Topic</a>\n" +
			      "	 <a class=\"link\" href=\"/RESTstart/rest/auth/not_auth_user/displayAll_topic\">Display all the Topics</a>\n" +
			        "	 <a class=\"link\" href=\"/RESTstart/rest/auth/not_auth_user/search_topic\">Search topic</a>\n" +
			      "	 <a class=\"link\" href=\"/RESTstart/rest/auth/not_auth_user/displayArticlesOfTopic_topic\">Display articles of a topic</a>\n" +
			      "	 <a class=\"link\" href=\"/RESTstart/rest/auth/not_auth_user/displayArticlesOfTopic_topic\">Display articles of a topic</a>\n" +
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
                "        <form action=\"/RESTstart/rest/auth/auth_user/create_article/create\" method=\"post\">\n" +
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
	
	/// NOTE: For modify article ...
	public static String getIDS_MODIFY_ARTICLE_HTML(ArrayList<String> ARTICLES_IDs) {
		String frameHTML = "<div class=\"ids-frame\">";
		
		for (int i = 0; i < ARTICLES_IDs.size(); i++) {
			frameHTML += "<a href=\"/RESTstart/rest/auth/auth_user/modify_article/" + ARTICLES_IDs.get(i) + "\">" + ARTICLES_IDs.get(i) + "</a> ";		}
		
		frameHTML += "</div>";
		
		String htmlCode = "<!DOCTYPE html>\n" +
		        "<html>\n" +
		        "<head>\n" +
		        "    <title>Modify Article</title>\n" +
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
		        "        <h1>Modify an Article. Choose ID of an article.</h1>\n" +
		        "        <h2>The articles that you see, have state CREATED (STATE_ID: 1)<p><h3>AND FOR A CURATOR WE DISPLAY ALSO ARTICLES THAT ARE IN THE STATE APPROVED (STATE_ID: 3)</h3></h2>\n" +
                "        " + frameHTML + "\n" +
		        "    </div>\n" +
		        "</body>\n" +
		        "</html>";
		return htmlCode;
	}
	public static String getMODIFY_ARTICLE_HTML(String username, String role, String title, String topic, String content, String cause) {
		
		String CAUSE_LABEL;
		if(cause.isEmpty()) {
			CAUSE_LABEL = "You have no alerts in this article at this time ...";
		} else {
			CAUSE_LABEL = "ALERT: " + cause;
		}
		
		String htmlCode = "<!DOCTYPE html>\n" +
		        "<html>\n" +
		        "<head>\n" +
		        "    <title>Modify Article</title>\n" +
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
		        "        .cause-label {\n" +
		        "            color: red;\n" +
		        "            font-weight: bold;\n" +
		        "        }\n" +
		        "    </style>\n" +
		        "</head>\n" +
		        "<body>\n" +
		        "    <div class=\"container\">\n" +
		        "        <h1>Modify an Article</h1>\n" +
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
		        "            <label class=\"cause-label\">" + CAUSE_LABEL + "</label>\n" +
		        "            <br>\n" +
		        "            <button type=\"submit\">Modify</button>\n" +
		        "        </form>\n" +
		        "    </div>\n" +
		        "</body>\n" +
		        "</html>";
		return htmlCode;
	}
	///
	
	
	/// NOTE: For submit article ...
	public static String getIDS_SUBMIT_ARTICLE_HTML(ArrayList<String> ARTICLES_IDs) {
		String frameHTML = "<div class=\"ids-frame\">";
		
		for (int i = 0; i < ARTICLES_IDs.size(); i++) {
			frameHTML += "<a href=\"/RESTstart/rest/auth/auth_user/submit_article/" + ARTICLES_IDs.get(i) + "\">" + ARTICLES_IDs.get(i) + "</a> ";
		}
		
		frameHTML += "</div>";
		
		String htmlCode = "<!DOCTYPE html>\n" +
		        "<html>\n" +
		        "<head>\n" +
		        "    <title>Submit Article</title>\n" +
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
		        "        <h1>Submit an Article. Choose ID of an article.</h1>\n" +
		        "        <h2>The articles that you see, have state CREATED (STATE_ID: 1)and also are not in --alert-- mode.<p>After the submition the article will go to the state of SUBMITED (STATE_ID: 2)<p>A curator will check it and accepted it.</h2>\n" +
                "        " + frameHTML + "\n" +
		        "    </div>\n" +
		        "</body>\n" +
		        "</html>";
		return htmlCode;
	}
	/// NOTE: FOR SOME REASON I CAN NOT HANDLE PUT REQUEST WITHOUT INCLUDE THE SCRIPT, IT READS IT AS POST, WITH THE SCRIPT
	/// I HANDLE THEM AS PUT. This happens maybe because the form/button are by default POST ...
	public static String getSUBMIT_ARTICLE_HTML(String username, String role, String title, String topic, String content) {
		String htmlCode = "<!DOCTYPE html>\n" +
		        "<html>\n" +
		        "<head>\n" +
		        "    <title>Submit Article</title>\n" +
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
		        "        <h1>Submit an Article</h1>\n" +
		        "        <h2>You cannot modify the code here</h2>\n" +
		        "        <form id=\"submitForm\" method=\"put\">\n" +
		        "            <label for=\"topic\">Topic:</label>\n" +
		        "            <input type=\"text\" id=\"topic\" name=\"topic\" value=\"" + topic + "\" readonly>\n" +
		        "            <br>\n" +
		        "            <label for=\"title\">Title:</label>\n" +
		        "            <input type=\"text\" id=\"title\" name=\"title\" value=\"" + title + "\" readonly>\n" +
		        "            <br>\n" +
		        "            <label for=\"content\">Content:</label>\n" +
		        "            <br>\n" +
		        "            <textarea id=\"content\" name=\"content\" rows=\"10\" cols=\"30\" readonly>" + content + "</textarea>\n" +
		        "            <br>\n" +
		        "            <button type=\"button\" onclick=\"submitForm()\">Submit</button>\n" +
		        "        </form>\n" +
		        "        <div id=\"responseDiv\"></div>\n" + 
		        "    </div>\n" +
		        "\n" +
		        "    <script>\n" +
		        "        function submitForm() {\n" +
		        "            var form = document.getElementById(\"submitForm\");\n" +
		        "            var formData = new FormData(form);\n" +
		        "\n" +
		        "            var xhr = new XMLHttpRequest();\n" +
		        "            xhr.open(\"PUT\", \"/RESTstart/rest/auth/auth_user/submit_article/submit\", true);\n" +
		        "            xhr.setRequestHeader(\"Content-Type\", \"application/x-www-form-urlencoded\");\n" +
		        "\n" +
		        "            xhr.onreadystatechange = function() {\n" +
		        "                if (xhr.readyState === XMLHttpRequest.DONE) {\n" +
		        "                    if (xhr.status === 200) {\n" +
		        "                        console.log(\"Success\");\n" +
		        "                        var response = xhr.responseText; // Get the response from the server\n" +
		        "                        var responseDiv = document.getElementById(\"responseDiv\"); // Get the <div> element to display the response\n" +
		        "                        responseDiv.textContent = response; // Update the content of the <div> with the response\n" +
		        "                    } else {\n" +
		        "                        console.log(\"Error\");\n" +
		        "                    }\n" +
		        "                }\n" +
		        "            };\n" +
		        "\n" +
		        "            xhr.send(new URLSearchParams(formData));\n" +
		        "        }\n" +
		        "    </script>\n" +
		        "</body>\n" +
		        "</html>";
		return htmlCode;
	}
	
	///This is for the APPROVE Article
	public static String getIDS_APPROVE_ARTICLE_HTML(ArrayList<String> ARTICLES_IDs) {
		String frameHTML = "<div class=\"ids-frame\">";
		
		for (int i = 0; i < ARTICLES_IDs.size(); i++) {
			frameHTML += "<a href=\"/RESTstart/rest/auth/auth_user/approve_article/" + ARTICLES_IDs.get(i) + "\">" + ARTICLES_IDs.get(i) + "</a> ";
		}
		
		frameHTML += "</div>";
		
		String htmlCode = "<!DOCTYPE html>\n" +
		        "<html>\n" +
		        "<head>\n" +
		        "    <title>Approve Article</title>\n" +
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
		        "        <h1>Approve an Article. Choose ID of an article.</h1>\n" +
		        "        <h2>The articles that you see, have state SUBMITTED (STATE_ID: 2)<p>After the acceptance (approve) the article will go to the state of APPROVED (STATE_ID: 3)<p>A curator will check it and publish it.</h2>\n" +
                "        " + frameHTML + "\n" +
		        "    </div>\n" +
		        "</body>\n" +
		        "</html>";
		return htmlCode;
	}
	public static String getAPPROVE_ARTICLE_HTML(String username, String role, String title, String topic, String content) {
		String htmlCode = "<!DOCTYPE html>\n" +
		        "<html>\n" +
		        "<head>\n" +
		        "    <title>Approve Article</title>\n" +
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
		        "        <h1>Approve an Article</h1>\n" +
		        "        <h2>You cannot modify the code here</h2>\n" +
		        "        <form id=\"submitForm\" method=\"put\">\n" +
		        "            <label for=\"topic\">Topic:</label>\n" +
		        "            <input type=\"text\" id=\"topic\" name=\"topic\" value=\"" + topic + "\" readonly>\n" +
		        "            <br>\n" +
		        "            <label for=\"title\">Title:</label>\n" +
		        "            <input type=\"text\" id=\"title\" name=\"title\" value=\"" + title + "\" readonly>\n" +
		        "            <br>\n" +
		        "            <label for=\"content\">Content:</label>\n" +
		        "            <br>\n" +
		        "            <textarea id=\"content\" name=\"content\" rows=\"10\" cols=\"30\" readonly>" + content + "</textarea>\n" +
		        "            <br>\n" +
		        "            <button type=\"button\" onclick=\"submitForm()\">Approve</button>\n" +
		        "        </form>\n" +
		        "        <div id=\"responseDiv\"></div>\n" + 
		        "    </div>\n" +
		        "\n" +
		        "    <script>\n" +
		        "        function submitForm() {\n" +
		        "            var form = document.getElementById(\"submitForm\");\n" +
		        "            var formData = new FormData(form);\n" +
		        "\n" +
		        "            var xhr = new XMLHttpRequest();\n" +
		        "            xhr.open(\"PUT\", \"/RESTstart/rest/auth/auth_user/approve_article/approve\", true);\n" +
		        "            xhr.setRequestHeader(\"Content-Type\", \"application/x-www-form-urlencoded\");\n" +
		        "\n" +
		        "            xhr.onreadystatechange = function() {\n" +
		        "                if (xhr.readyState === XMLHttpRequest.DONE) {\n" +
		        "                    if (xhr.status === 200) {\n" +
		        "                        console.log(\"Success\");\n" +
		        "                        var response = xhr.responseText; // Get the response from the server\n" +
		        "                        var responseDiv = document.getElementById(\"responseDiv\"); // Get the <div> element to display the response\n" +
		        "                        responseDiv.textContent = response; // Update the content of the <div> with the response\n" +
		        "                    } else {\n" +
		        "                        console.log(\"Error\");\n" +
		        "                    }\n" +
		        "                }\n" +
		        "            };\n" +
		        "\n" +
		        "            xhr.send(new URLSearchParams(formData));\n" +
		        "        }\n" +
		        "    </script>\n" +
		        "</body>\n" +
		        "</html>";
		return htmlCode;
	}
	
	///This is for the DECLINE Article
	public static String getIDS_DECLINE_ARTICLE_HTML(ArrayList<String> ARTICLES_IDs) {
		String frameHTML = "<div class=\"ids-frame\">";
		
		for (int i = 0; i < ARTICLES_IDs.size(); i++) {
			frameHTML += "<a href=\"/RESTstart/rest/auth/auth_user/decline_article/" + ARTICLES_IDs.get(i) + "\">" + ARTICLES_IDs.get(i) + "</a> ";		
		}
		
		frameHTML += "</div>";
		
		String htmlCode = "<!DOCTYPE html>\n" +
		        "<html>\n" +
		        "<head>\n" +
		        "    <title>Decline Article</title>\n" +
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
		        "        <h1>Decline an Article. Choose ID of an article.</h1>\n" +
		        "        <h2>The articles that you see, have state SUBMITTED (STATE_ID: 2) and state APPROVED (STATE_ID: 3)<p>If you decline the article you choose you have to give an explanation.</h2>\n" +
                "        " + frameHTML + "\n" +
		        "    </div>\n" +
		        "</body>\n" +
		        "</html>";
		return htmlCode;
	}
	public static String getDECLINE_ARTICLE_HTML(String username, String role, String title, String topic, String content) {
		String htmlCode = "<!DOCTYPE html>\n" +
			    "<html>\n" +
			    "<head>\n" +
			    "    <title>Decline Article</title>\n" +
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
			    "        <h1>Decline an Article</h1>\n" +
			    "        <h2>You cannot modify the code here</h2>\n" +
			    "        <form id=\"submitForm\" method=\"put\">\n" +
			    "            <label for=\"topic\">Topic:</label>\n" +
			    "            <input type=\"text\" id=\"topic\" name=\"topic\" value=\"" + topic + "\" readonly>\n" +
			    "            <br>\n" +
			    "            <label for=\"title\">Title:</label>\n" +
			    "            <input type=\"text\" id=\"title\" name=\"title\" value=\"" + title + "\" readonly>\n" +
			    "            <br>\n" +
			    "            <label for=\"content\">Content:</label>\n" +
			    "            <br>\n" +
			    "            <textarea id=\"content\" name=\"content\" rows=\"10\" cols=\"30\" readonly>" + content + "</textarea>\n" +
			    "            <br>\n" +
			    "            <label for=\"cause\">Cause:</label>\n" +
			    "            <br>\n" +
			    "            <textarea id=\"cause\" name=\"cause\" rows=\"5\" cols=\"30\"></textarea>\n" +
			    "            <br>\n" +
			    "            <button type=\"button\" onclick=\"submitForm()\">Decline</button>\n" +
			    "        </form>\n" +
			    "        <div id=\"responseDiv\"></div>\n" +
			    "    </div>\n" +
			    "\n" +
			    "    <script>\n" +
			    "        function submitForm() {\n" +
			    "            var form = document.getElementById(\"submitForm\");\n" +
			    "            var formData = new FormData(form);\n" +
			    "\n" +
			    "            var cause = document.getElementById(\"cause\").value;\n" +
			    "\n" +
			    "            var xhr = new XMLHttpRequest();\n" +
			    "            xhr.open(\"PUT\", \"/RESTstart/rest/auth/auth_user/decline_article/decline?cause=\" + encodeURIComponent(cause), true);\n" +
			    "            xhr.setRequestHeader(\"Content-Type\", \"application/x-www-form-urlencoded\");\n" +
			    "\n" +
			    "            xhr.onreadystatechange = function() {\n" +
			    "                if (xhr.readyState === XMLHttpRequest.DONE) {\n" +
			    "                    if (xhr.status === 200) {\n" +
			    "                        console.log(\"Success\");\n" +
			    "                        var response = xhr.responseText;\n" +
			    "                        var responseDiv = document.getElementById(\"responseDiv\");\n" +
			    "                        responseDiv.textContent = response;\n" +
			    "                    } else {\n" +
			    "                        console.log(\"Error\");\n" +
			    "                    }\n" +
			    "                }\n" +
			    "            };\n" +
			    "\n" +
			    "            xhr.send(new URLSearchParams(formData));\n" +
			    "        }\n" +
			    "    </script>\n" +
			    "</body>\n" +
			    "</html>";

		return htmlCode;
	}
	
	
	///This is for the PUBLISH Article
		public static String getIDS_PUBLISH_ARTICLE_HTML(ArrayList<String> ARTICLES_IDs) {
			String frameHTML = "<div class=\"ids-frame\">";
			
			for (int i = 0; i < ARTICLES_IDs.size(); i++) {
		        frameHTML += "<a href=\"/RESTstart/rest/auth/auth_user/publish_article/" + ARTICLES_IDs.get(i) + "?method=GET\">" + ARTICLES_IDs.get(i) + "</a> ";
			}
			
			frameHTML += "</div>";
			
			String htmlCode = "<!DOCTYPE html>\n" +
			        "<html>\n" +
			        "<head>\n" +
			        "    <title>Publish Article</title>\n" +
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
			        "        <h1>Publish an Article. Choose ID of an article.</h1>\n" +
			        "        <h2>The articles that you see, have state APPROVED (STATE_ID: 3)<p>If you publish the article you choose then this article is available for display for everyone</h2>\n" +
	                "        " + frameHTML + "\n" +
			        "    </div>\n" +
			        "</body>\n" +
			        "</html>";
			return htmlCode;
		}
	
		public static String getPUBLISH_ARTICLE_HTML(String username, String role, String title, String topic, String content) {
			String htmlCode = "<!DOCTYPE html>\n" +
			        "<html>\n" +
			        "<head>\n" +
			        "    <title>Publish Article</title>\n" +
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
			        "        <h1>Publish an Article</h1>\n" +
			        "        <h2>You cannot modify the code here</h2>\n" +
			        "        <form id=\"submitForm\" method=\"put\">\n" +
			        "            <label for=\"topic\">Topic:</label>\n" +
			        "            <input type=\"text\" id=\"topic\" name=\"topic\" value=\"" + topic + "\" readonly>\n" +
			        "            <br>\n" +
			        "            <label for=\"title\">Title:</label>\n" +
			        "            <input type=\"text\" id=\"title\" name=\"title\" value=\"" + title + "\" readonly>\n" +
			        "            <br>\n" +
			        "            <label for=\"content\">Content:</label>\n" +
			        "            <br>\n" +
			        "            <textarea id=\"content\" name=\"content\" rows=\"10\" cols=\"30\" readonly>" + content + "</textarea>\n" +
			        "            <br>\n" +
			        "            <button type=\"button\" onclick=\"submitForm()\">Publish</button>\n" +
			        "        </form>\n" +
			        "        <div id=\"responseDiv\"></div>\n" + 
			        "    </div>\n" +
			        "\n" +
			        "    <script>\n" +
			        "        function submitForm() {\n" +
			        "            var form = document.getElementById(\"submitForm\");\n" +
			        "            var formData = new FormData(form);\n" +
			        "\n" +
			        "            var xhr = new XMLHttpRequest();\n" +
			        "            xhr.open(\"PUT\", \"/RESTstart/rest/auth/auth_user/publish_article/publish\", true);\n" +
			        "            xhr.setRequestHeader(\"Content-Type\", \"application/x-www-form-urlencoded\");\n" +
			        "\n" +
			        "            xhr.onreadystatechange = function() {\n" +
			        "                if (xhr.readyState === XMLHttpRequest.DONE) {\n" +
			        "                    if (xhr.status === 200) {\n" +
			        "                        console.log(\"Success\");\n" +
			        "                        var response = xhr.responseText; // Get the response from the server\n" +
			        "                        var responseDiv = document.getElementById(\"responseDiv\"); // Get the <div> element to display the response\n" +
			        "                        responseDiv.textContent = response; // Update the content of the <div> with the response\n" +
			        "                    } else {\n" +
			        "                        console.log(\"Error\");\n" +
			        "                    }\n" +
			        "                }\n" +
			        "            };\n" +
			        "\n" +
			        "            xhr.send(new URLSearchParams(formData));\n" +
			        "        }\n" +
			        "    </script>\n" +
			        "</body>\n" +
			        "</html>";
			return htmlCode;
		}

		///This is for the SEARCH Article
		/* NOTE: IF THE USER IS A VISITOR WE HAVE THE FIRST URL THAT CORRESPOND TO THE GET METHOD, ELSE WE HAVE THE SECOND ONE */
		public static String getSEARCH_ARTICLE_KEY_PHRASES_HTML(String username, int USER_ROLE_ID) {
		    String temp_str, temp_username, temp_input;
		    if (USER_ROLE_ID == 1) {
		    	 temp_str = "        <form action=\"/RESTstart/rest/auth/not_auth_user/search_article/search\" method=\"get\">\n";
		    	 temp_username = "<h2>The articles you see have state PUBLISHED (STATE_ID: 4)";
		    	 temp_input = "";
		    } else if(USER_ROLE_ID == 2){ // Journalist can not see all the articles 
		    	 temp_str = "        <form action=\"/RESTstart/rest/auth/auth_user/search_article/search\" method=\"get\">\n";
		    	 temp_username =  "		   <h2>The articles you see belongs to " + username + " OR have state PUBLISHED (STATE_ID: 4)";
		         temp_input = "    <input type=\"hidden\" name=\"username\" value=\"" + username + "\">\n";
		    } else { // Curator can see all the articles 
		    	 temp_str = "        <form action=\"/RESTstart/rest/auth/auth_user/search_article/search\" method=\"get\">\n";
		    	 temp_username =  "		   <h2>The articles you see belongs to " + username + " and have have all the states possible";
		         temp_input = "    <input type=\"hidden\" name=\"username\" value=\"" + username + "\">\n";
		    }
		    String html_code = "<!DOCTYPE html>\n"
		        + "<html>\n"
		        + "<head>\n"
		        + "    <title>Search Article</title>\n"
		        + "    <style>\n"
		        + "        body {\n"
		        + "            display: flex;\n"
		        + "            justify-content: center;\n"
		        + "            align-items: center;\n"
		        + "            height: 100vh;\n"
		        + "        }\n"
		        + "        .container {\n"
		        + "            text-align: center;\n"
		        + "        }\n"
		        + "        form {\n"
		        + "            margin-top: 20px;\n"
		        + "        }\n"
		        + "        textarea {\n"
                + "            resize: none;\n"
                + "            overflow: auto;\n"
                + "            height: 150px;\n"
                + "            width: 300px;\n"
                + "        }\n"
		        + "    </style>\n"
		        + "</head>\n"
		        + "<body>\n"
		        + "    <div class=\"container\">\n"
		        + "        <h1>Search Article</h1>\n"
		        + "			<h2>Warning: If you add key phrase for the title AND for the content, both must be satisfied"	
		        + temp_username
		        + "\n"
		        + temp_str
		        + "            <label for=\"titleKeyPhrases\">Title Key Phrases:</label>\n"
		        + "            <br>\n"
		        + "            <textarea id=\"titleKeyPhrases\" name=\"titleKeyPhrases\" rows=\"5\" cols=\"30\"></textarea>\n"
		        + "            <br>\n"
		        + "\n"
		        + "            <label for=\"contentKeyPhrases\">Content Key Phrases:</label>\n"
		        + "            <br>\n"
		        + "            <textarea id=\"contentKeyPhrases\" name=\"contentKeyPhrases\" rows=\"5\" cols=\"30\"></textarea>\n"
		        + "            <br>\n"
		        + "\n"
		        + temp_input
		        + "            <button type=\"submit\">Search</button>\n"
		        + "        </form>\n"
		        + "    </div>\n"
		        + "</body>\n"
		        + "</html>";
		    return html_code;
		}
		public static String getArticlesFromSEARCH_ARTICLES(ArrayList<Article> GOAL_ARTICLES) {
			
		    if(GOAL_ARTICLES.isEmpty()) {
		    	String htmlCode = "<!DOCTYPE html>\n"
		                + "<html>\n"
		                + "<head>\n"
		                + "    <title>Articles Not Found</title>\n"
		                + "</head>\n"
		                + "<body>\n"
		                + "    <h1>ARTICLES_NOT_FOUND</h1>\n"
		                + "</body>\n"
		                + "</html>";

		        return htmlCode;
		    }
			
			StringBuilder htmlCode = new StringBuilder();

		    htmlCode.append("<!DOCTYPE html>\n");
		    htmlCode.append("<html>\n");
		    htmlCode.append("<head>\n");
		    htmlCode.append("    <title>Articles</title>\n");
		    htmlCode.append("    <style>\n");
		    htmlCode.append("        .article {\n");
		    htmlCode.append("            margin-bottom: 20px;\n");
		    htmlCode.append("            padding: 10px;\n");
		    htmlCode.append("            border: 1px solid #ccc;\n");
		    htmlCode.append("        }\n");
		    htmlCode.append("        .article h2 {\n");
		    htmlCode.append("            margin-top: 0;\n");
		    htmlCode.append("        }\n");
		    htmlCode.append("        .article p {\n");
		    htmlCode.append("            margin-bottom: 0;\n");
		    htmlCode.append("        }\n");
		    htmlCode.append("    </style>\n");
		    htmlCode.append("</head>\n");
		    htmlCode.append("<body>\n");

		    for (Article article : GOAL_ARTICLES) {
		        htmlCode.append("    <div class=\"article\">\n");
		        htmlCode.append("        <h2>ID: ").append(article.getId()).append("</h2>\n");
		        htmlCode.append("        <p>Title: ").append(article.getTitle()).append("</p>\n");
		        htmlCode.append("        <p>Content: ").append(article.getContents()).append("</p>\n");
		        htmlCode.append("    </div>\n");
		    }

		    htmlCode.append("</body>\n");
		    htmlCode.append("</html>");

		    return htmlCode.toString();
		}

		///This is for the Display Article
		public static String getIDS_DISPLAY_ARTICLE_HTML(ArrayList<String> ARTICLES_IDs, int ROLE_ID) {
			
			
			
			String h2;
			if(ROLE_ID == 2)  { /// JOURNALIST
				h2 = "<h2>Here you see the ids of the aricles that belong to you and also the aritcles that have state PUBLISHED (STATE_ID: 4)</h2>";
			} else if(ROLE_ID == 3) {
				h2 = "<h2>Here you see the ids of the aricles of all the users that have all the states possible</h2>";
			} else {
				h2 = "<h2>Here you see articles that have state PUBLISHED (STATE_ID: 4)</h2>";
			}
			
			String frameHTML = "<div class=\"ids-frame\">";
			
			for (int i = 0; i < ARTICLES_IDs.size(); i++) {
				if(ROLE_ID == 2 || ROLE_ID == 3) {
		        	frameHTML += "<a href=\"/RESTstart/rest/auth/auth_user/display_article/" + ARTICLES_IDs.get(i) + "?method=GET\">" + ARTICLES_IDs.get(i) + "</a> ";
				} else {
		        	frameHTML += "<a href=\"/RESTstart/rest/auth/not_auth_user/display_article/" + ARTICLES_IDs.get(i) + "?method=GET\">" + ARTICLES_IDs.get(i) + "</a> ";
				}
			}
			
			frameHTML += "</div>";
			
			String htmlCode = "<!DOCTYPE html>\n" +
			        "<html>\n" +
			        "<head>\n" +
			        "    <title>Display Article</title>\n" +
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
			        "        <h1>Display an Article. Choose ID of an article.</h1>\n" +
			        h2 +
	                "        " + frameHTML + "\n" +
			        "    </div>\n" +
			        "</body>\n" +
			        "</html>";
			return htmlCode;
		}
		public static String getDISPLAY_ARTICLE_HTML(String username, String role, String creator_username, String title, String topic, String content) {
		    String htmlCode = "<!DOCTYPE html>\n" +
		        "<html>\n" +
		        "<head>\n" +
		        "    <title>Display Article</title>\n" +
		        "    <style>\n" +
		        "        body {\n" +
		        "            display: flex;\n" +
		        "            justify-content: center;\n" +
		        "            align-items: center;\n" +
		        "            height: 100vh;\n" +
		        "        }\n" +
		        "        .container {\n" +
		        "            text-align: center;\n" +
		        "            display: flex;\n" +
		        "            flex-direction: column;\n" +
		        "            align-items: center;\n" +
		        "        }\n" +
		        "        .form-group {\n" +
		        "            margin-bottom: 20px;\n" +
		        "            display: flex;\n" +
		        "            flex-direction: column;\n" +
		        "            align-items: flex-start;\n" +
		        "        }\n" +
		        "        .form-group label {\n" +
		        "            margin-bottom: 5px;\n" +
		        "        }\n" +
		        "        .form-group input[type=\"text\"],\n" +
		        "        .form-group textarea {\n" +
		        "            width: 300px;\n" +
		        "        }\n" +
		        "        .form-group button {\n" +
		        "            margin-top: 10px;\n" +
		        "        }\n" +
		        "    </style>\n" +
		        "</head>\n" +
		        "<body>\n" +
		        "    <div class=\"container\">\n" +
		        "        <h1>Display an Article</h1>\n" +
		        "        <h2>You cannot modify the article here</h2>\n" +
		        "        <form id=\"submitForm\" method=\"put\">\n" +
		        "            <div class=\"form-group\">\n" +
		        "                <label for=\"creator_username\">Creator username:</label>\n" +
		        "                <input type=\"text\" id=\"creator_username\" name=\"creator_username\" value=\"" + creator_username + "\" readonly>\n" +
		        "            </div>\n" +
		        "            <div class=\"form-group\">\n" +
		        "                <label for=\"topic\">Topic:</label>\n" +
		        "                <input type=\"text\" id=\"topic\" name=\"topic\" value=\"" + topic + "\" readonly>\n" +
		        "            </div>\n" +
		        "            <div class=\"form-group\">\n" +
		        "                <label for=\"title\">Title:</label>\n" +
		        "                <input type=\"text\" id=\"title\" name=\"title\" value=\"" + title + "\" readonly>\n" +
		        "            </div>\n" +
		        "            <div class=\"form-group\">\n" +
		        "                <label for=\"content\">Content:</label>\n" +
		        "                <textarea id=\"content\" name=\"content\" rows=\"10\" cols=\"30\" readonly>" + content + "</textarea>\n" +
		        "            </div>\n" +
		        "        </form>\n" +
		        "        <div id=\"responseDiv\"></div>\n" +
		        "    </div>\n" +
		        "\n" +
		        "</body>\n" +
		        "</html>";

		    return htmlCode;
		}

		///This is for the Display All the articles
		/// This is the html code for the user to choose which, option wants. Sort by Date or Sort by State
		public static String getStartOptionsHTML(String name, String role) {
			
			String temp_intro, temp_link_action, temp_check_boxes;
			if(!role.equals("VISITOR")) {
				temp_intro = "  <h3>USERNAME: " + name + " - ROLE: " + role + "</h3>\r\n";
				temp_link_action = "  <form action=\"/RESTstart/rest/auth/auth_user/displayAll_article/displayAll\" method=\"post\">\r\n";
				temp_check_boxes = "    <label for=\"sortByState\">\r\n"
			            + "      <input type=\"checkbox\" id=\"sortByState\" name=\"sortByState\" value=\"true\" onclick=\"handleCheckbox(this)\">\r\n"
			            + "      Sort by State\r\n"
			            + "    </label>\r\n"
			            + "    <br>\r\n";
			} else {
				temp_intro = "  <h3>ROLE: " + role + "</h3>\r\n";
				temp_link_action = "  <form action=\"/RESTstart/rest/auth/not_auth_user/displayAll_article/displayAll\" method=\"post\">\r\n";
				temp_check_boxes = "";
			}
			
		    String htmlCode = "<!DOCTYPE html>\r\n"
		            + "<html>\r\n"
		            + "<head>\r\n"
		            + "  <title>Display all the Articles</title>\r\n"
		            + "  <style>\r\n"
		            + "    body {\r\n"
		            + "      display: flex;\r\n"
		            + "      justify-content: center;\r\n"
		            + "      align-items: center;\r\n"
		            + "      flex-direction: column;\r\n"
		            + "      height: 100vh;\r\n"
		            + "    }\r\n"
		            + "    form {\r\n"
		            + "      text-align: center;\r\n"
		            + "    }\r\n"
		            + "  </style>\r\n"
		            + "</head>\r\n"
		            + "<body>\r\n"
		            + "  <h1>Display all the Articles</h1>\r\n"
		            + "  <h2>Choose only one option</h2>"
		            + temp_intro
		            + temp_link_action
		            + temp_check_boxes
		            + "    <label for=\"sortByDate\">\r\n"
		            + "      <input type=\"checkbox\" id=\"sortByDate\" name=\"sortByDate\" value=\"true\" onclick=\"handleCheckbox(this)\">\r\n"
		            + "      Sort by Date\r\n"
		            + "    </label>\r\n"
		            + "    <br>\r\n"
		            + "    <input type=\"hidden\" id=\"name\" name=\"name\" value=\"" + name + "\">\r\n"
		            + "    <input type=\"hidden\" id=\"role\" name=\"role\" value=\"" + role + "\">\r\n"
		            + "    <input type=\"submit\" value=\"Submit\">\r\n"
		            + "  </form>\r\n"
		            + "</body>\r\n"
		            + "</html>\r\n";

		    return htmlCode;
		}
		public static String getArticlesFromSEARCH_ALL_ARTICLES_auth(String clickedByName, ArrayList<Article> GOAL_ARTICLES, ArrayList<Comments> GOAL_COMMENTS) {	
		    if (GOAL_ARTICLES.isEmpty()) {
		        String htmlCode = "<!DOCTYPE html>\n"
		                + "<html>\n"
		                + "<head>\n"
		                + "    <title>Articles Not Found</title>\n"
		                + "</head>\n"
		                + "<body>\n"
		                + "    <h1>ARTICLES_NOT_FOUND</h1>\n"
		                + "</body>\n"
		                + "</html>";

		        return htmlCode;
		    }

		    StringBuilder htmlCode = new StringBuilder();

		    htmlCode.append("<!DOCTYPE html>\n");
		    htmlCode.append("<html>\n");
		    htmlCode.append("<head>\n");
		    htmlCode.append("    <title>Articles</title>\n");
		    htmlCode.append("    <style>\n");
		    htmlCode.append("        .article {\n");
		    htmlCode.append("            margin-bottom: 20px;\n");
		    htmlCode.append("            padding: 10px;\n");
		    htmlCode.append("            border: 1px solid #ccc;\n");
		    htmlCode.append("        }\n");
		    htmlCode.append("        .article h2 {\n");
		    htmlCode.append("            margin-top: 0;\n");
		    htmlCode.append("        }\n");
		    htmlCode.append("        .article p {\n");
		    htmlCode.append("            margin-bottom: 0;\n");
		    htmlCode.append("        }\n");
		    htmlCode.append("    </style>\n");
		    htmlCode.append("</head>\n");
		    htmlCode.append("<body>\n");
		    htmlCode.append("    <form method=\"POST\" action=\"/RESTstart/rest/auth/auth_user/displayAll_article/filAp\">\n");  
		    htmlCode.append("        <div id=\"Filters\" style=\"border: 1px solid #ccc;\">\n");
	        htmlCode.append("            <input type=\"hidden\" name=\"clickedByName\" value=\"").append(clickedByName).append("\">\n");
		    htmlCode.append("            <label for=\"state\">State of Article:</label>\n");
		    htmlCode.append("            <input type=\"text\" id=\"state\" name=\"state\" list=\"stateOptions\">\n");
		    htmlCode.append("            <datalist id=\"stateOptions\">\n");
		    htmlCode.append("                <option value=\"1\">\n");
		    htmlCode.append("                <option value=\"2\">\n");
		    htmlCode.append("                <option value=\"3\">\n");
		    htmlCode.append("                <option value=\"4\">\n");
		    htmlCode.append("            </datalist>\n");
		    htmlCode.append("            <label for=\"startDate\">Start date:</label>\n");
		    htmlCode.append("            <input type=\"date\" id=\"startDate\" name=\"startDate\">\n");
		    htmlCode.append("            <label for=\"endDate\">End date:</label>\n");
		    htmlCode.append("            <input type=\"date\" id=\"endDate\" name=\"endDate\">\n");
		    htmlCode.append("        </div>\n");
		    htmlCode.append("        <p></p>\n");
		    htmlCode.append("        <button type=\"submit\">Submit</button>\n");
		    htmlCode.append("    </form>\n");
		    htmlCode.append("    <p></p>");
		    htmlCode.append("<div style=\"text-align: right; padding: 10px;\">Log in as: ").append(clickedByName).append("</div>\n");

		    for (Article article : GOAL_ARTICLES) {
		        htmlCode.append("    <div class=\"article\">\n");
		        htmlCode.append("        <h2>ID: ").append(article.getId()).append("</h2>\n");
		        htmlCode.append("        <p>Creator username: ").append(article.getCreator_username()).append("</p>\n");
		        htmlCode.append("        <p>State ID: ").append(article.getState_id()).append("</p>\n");
		        htmlCode.append("        <p>Date creation: ").append(article.getDate_creation()).append("</p>\n");
		        htmlCode.append("        <p>Title: ").append(article.getTitle()).append("</p>\n");
		        htmlCode.append("        <p style=\"font-size: 25px;\">Content: ").append(article.getContents()).append("</p>\n");
		        
		        htmlCode.append("<p>--------------------------------------------------------------------------------------------------------------</p>");
		        htmlCode.append("<h4>Comments:</h4>");
		        /* Add each comment that this article has */
		        for (Comments comment : GOAL_COMMENTS) {
		            if (comment.getArticle_id() == article.getId()) {
		            	htmlCode.append("        <p style=\"text-indent: 20px;\"><em>").append(comment.getContent()).append("</em></p>\n");
		            }
		        }
		        htmlCode.append("<p>--------------------------------------------------------------------------------------------------------------</p>");
		        
		        /* SOS!!! COMMENT CAN NOT BE ADDED IN A ARTICLE THAT HAS NOT STATE PUBLISHED == 4 */
		        if(article.getState_id() == 4) {
			        htmlCode.append("        <div>\n");
			        htmlCode.append("            <form method=\"POST\" action=\"/RESTstart/rest/auth/auth_user/displayAll_article/add_comment\">\n");
			        htmlCode.append("                <input type=\"hidden\" name=\"clickedByName\" value=\"").append(clickedByName).append("\">\n");
			        htmlCode.append("                <input type=\"hidden\" name=\"articleId\" value=\"").append(article.getId()).append("\">\n");
			        htmlCode.append("                <input type=\"hidden\" name=\"creator_username\" value=\"").append(article.getCreator_username()).append("\">\n");
			        htmlCode.append("                    <input type=\"radio\" name=\"commentVisibility\" value=\"withName\" checked>\n");
			        htmlCode.append("                    Add with name\n");
			        htmlCode.append("                </label>\n");
			        htmlCode.append("                <label>\n");
			        htmlCode.append("                    <input type=\"radio\" name=\"commentVisibility\" value=\"anonymous\">\n");
			        htmlCode.append("                    Add anonymously\n");
			        htmlCode.append("                </label>\n");
			        htmlCode.append("                <textarea name=\"comment\" placeholder=\"Add a comment...\"></textarea>\n");
			        htmlCode.append("                <button type=\"submit\">Submit comment</button>\n");
			        htmlCode.append("            </form>\n");
			        htmlCode.append("        </div>\n");
		        }
		        
		        htmlCode.append("    </div>\n");
		    }

		    htmlCode.append("</body>\n");
		    htmlCode.append("</html>");

		    return htmlCode.toString();
		}
		public static String getArticlesFromSEARCH_ALL_ARTICLES_not_auth(ArrayList<Article> GOAL_ARTICLES, ArrayList<Comments> GOAL_COMMENTS) {
		    if (GOAL_ARTICLES.isEmpty()) {
		        String htmlCode = "<!DOCTYPE html>\n"
		                + "<html>\n"
		                + "<head>\n"
		                + "    <title>Articles Not Found</title>\n"
		                + "</head>\n"
		                + "<body>\n"
		                + "    <h1>ARTICLES_NOT_FOUND</h1>\n"
		                + "</body>\n"
		                + "</html>";

		        return htmlCode;
		    }

		    StringBuilder htmlCode = new StringBuilder();

		    htmlCode.append("<!DOCTYPE html>\n");
		    htmlCode.append("<html>\n");
		    htmlCode.append("<head>\n");
		    htmlCode.append("    <title>Articles</title>\n");
		    htmlCode.append("    <style>\n");
		    htmlCode.append("        .article {\n");
		    htmlCode.append("            margin-bottom: 20px;\n");
		    htmlCode.append("            padding: 10px;\n");
		    htmlCode.append("            border: 1px solid #ccc;\n");
		    htmlCode.append("        }\n");
		    htmlCode.append("        .article h2 {\n");
		    htmlCode.append("            margin-top: 0;\n");
		    htmlCode.append("        }\n");
		    htmlCode.append("        .article p {\n");
		    htmlCode.append("            margin-bottom: 0;\n");
		    htmlCode.append("        }\n");
		    htmlCode.append("    </style>\n");
		    htmlCode.append("</head>\n");
		    htmlCode.append("<body>\n");
		    htmlCode.append("    <form method=\"POST\" action=\"/RESTstart/rest/auth/not_auth_user/displayAll_article/filAp\">\n");  
		    htmlCode.append("        <div id=\"Filters\" style=\"border: 1px solid #ccc;\">\n");
		    htmlCode.append("            <input type=\"hidden\" name=\"clickedByName\" value=\"").append("Visitor").append("\">\n");
		    htmlCode.append("            <label for=\"startDate\">Start date:</label>\n");
		    htmlCode.append("            <input type=\"date\" id=\"startDate\" name=\"startDate\">\n");
		    htmlCode.append("            <label for=\"endDate\">End date:</label>\n");
		    htmlCode.append("            <input type=\"date\" id=\"endDate\" name=\"endDate\">\n");
		    htmlCode.append("        </div>\n");
		    htmlCode.append("        <button type=\"submit\">Submit</button>\n");
		    htmlCode.append("    </form>\n");
		    htmlCode.append("    <p></p>");
		    htmlCode.append("<div style=\"text-align: right; padding: 10px;\">Log in as: ").append("Visitor").append("</div>\n");


		    for (Article article : GOAL_ARTICLES) {
		        htmlCode.append("    <div class=\"article\">\n");
		        htmlCode.append("        <h2>ID: ").append(article.getId()).append("</h2>\n");
		        htmlCode.append("        <p>Creator username: ").append(article.getCreator_username()).append("</p>\n");
		        htmlCode.append("        <p>State ID: ").append(article.getState_id()).append("</p>\n");
		        htmlCode.append("        <p>Date creation: ").append(article.getDate_creation()).append("</p>\n");
		        htmlCode.append("        <p>Title: ").append(article.getTitle()).append("</p>\n");
		        htmlCode.append("        <p style=\"font-size: 25px;\">Content: ").append(article.getContents()).append("</p>\n");

		        htmlCode.append("<p>--------------------------------------------------------------------------------------------------------------</p>");
		        htmlCode.append("<h4>Comments:</h4>");
		        /* Add each comment that this article has */
		        for (Comments comment : GOAL_COMMENTS) {
		            if (comment.getArticle_id() == article.getId()) {
		                htmlCode.append("        <p style=\"text-indent: 20px;\"><em>").append(comment.getContent()).append("</em></p>\n");
		            }
		        }
		        htmlCode.append("<p>--------------------------------------------------------------------------------------------------------------</p>");


		        htmlCode.append("        <div>\n");
		        htmlCode.append("<form method=\"POST\" action=\"/RESTstart/rest/auth/not_auth_user/displayAll_article/add_comment\">\n");
		        htmlCode.append("    <input type=\"hidden\" name=\"clickedByName\" value=\"").append("Visitor").append("\">\n");
		        htmlCode.append("    <input type=\"hidden\" name=\"articleId\" value=\"").append(article.getId()).append("\">\n");
		        htmlCode.append("    <input type=\"hidden\" name=\"creator_username\" value=\"").append(article.getCreator_username()).append("\">\n");
		        htmlCode.append("    <input type=\"radio\" name=\"commentVisibility\" value=\"withName\" onclick=\"toggleNameField(").append(article.getId()).append(", true)\">\n");
		        htmlCode.append("    Add with name\n");
		        htmlCode.append("    <textarea id=\"name_visitor_").append(article.getId()).append("\" name=\"name_visitor\" placeholder=\"Add your name...\" style=\"display:none\"></textarea>\n");
		        htmlCode.append("    <label>\n");
		        htmlCode.append("        <input type=\"radio\" name=\"commentVisibility\" value=\"anonymous\" checked onclick=\"toggleNameField(").append(article.getId()).append(", false)\">\n");
		        htmlCode.append("        Add anonymously\n");
		        htmlCode.append("    </label>\n");
		        htmlCode.append("    <textarea name=\"comment\" placeholder=\"Add a comment...\"></textarea>\n");
		        htmlCode.append("    <button type=\"submit\">Submit comment</button>\n");
		        htmlCode.append("</form>\n");
		        htmlCode.append("<script>\n");
		        htmlCode.append("    function toggleNameField(articleId, show) {\n");
		        htmlCode.append("        var nameField = document.getElementById(\"name_visitor_\" + articleId);\n");
		        htmlCode.append("        if (show) {\n");
		        htmlCode.append("            nameField.style.display = \"block\";\n");
		        htmlCode.append("        } else {\n");
		        htmlCode.append("            nameField.style.display = \"none\";\n");
		        htmlCode.append("        }\n");
		        htmlCode.append("    }\n");
		        htmlCode.append("</script>\n");
		        htmlCode.append("        </div>\n");

		        htmlCode.append("    </div>\n");
		    }


		    htmlCode.append("</body>\n");
		    htmlCode.append("</html>");

		    return htmlCode.toString();
		}

		
		
		/// This is for Modify Comment
		public static String getCommentsFromMODIFY_COMMENTS_auth(ArrayList<Comments> COMMENTS_DATA, String username) {
		    if (COMMENTS_DATA.isEmpty()) {
		        String htmlCode = "<!DOCTYPE html>\n"
		                + "<html>\n"
		                + "<head>\n"
		                + "    <title>Comments Not Found</title>\n"
		                + "</head>\n"
		                + "<body>\n"
		                + "    <h1>COMMENTS_NOT_FOUND</h1>\n"
		                + "</body>\n"
		                + "</html>";

		        return htmlCode;
		    }

		    StringBuilder htmlCode = new StringBuilder();
		    htmlCode.append("<!DOCTYPE html>\n");
		    htmlCode.append("<html>\n");
		    htmlCode.append("<head>\n");
		    htmlCode.append("    <title>Modify comments</title>\n");
		    htmlCode.append("    <style>\n");
		    htmlCode.append("        .comment-container {\n");
		    htmlCode.append("            display: flex;\n");
		    htmlCode.append("            flex-direction: column;\n");
		    htmlCode.append("            align-items: center;\n");
		    htmlCode.append("        }\n");
		    htmlCode.append("        .comment {\n");
		    htmlCode.append("            margin-bottom: 20px;\n");
		    htmlCode.append("            padding: 10px;\n");
		    htmlCode.append("        }\n");
		    htmlCode.append("        .comment-content {\n");
		    htmlCode.append("            font-size: 18px;\n");
		    htmlCode.append("            width: 500px;\n");
		    htmlCode.append("            height: 150px;\n");
		    htmlCode.append("            resize: none;\n");
		    htmlCode.append("            background-color: transparent;\n");
		    htmlCode.append("            overflow: auto;\n");
		    htmlCode.append("        }\n");
		    htmlCode.append("    </style>\n");
		    htmlCode.append("</head>\n");
		    htmlCode.append("<body>\n");
		    htmlCode.append("<div style=\"text-align: right;\">Log in as: ").append(username).append("</div>\n");
		    htmlCode.append("<div class=\"comment-container\">\n");

		    for (Comments comment : COMMENTS_DATA) {
		        htmlCode.append("<div id=\"comment-").append(comment.getId()).append("\" class=\"comment\">\n");
		        htmlCode.append("    <h2>Comment ID: ").append(comment.getId()).append("</h2>\n");
		        htmlCode.append("    <p>Date Creation: ").append(comment.getDate_creation()).append("</p>\n");
		        htmlCode.append("    <p>Article ID: ").append(comment.getArticle_id()).append("</p>\n");
		        htmlCode.append("    <p>State ID: ").append(comment.getState_id()).append("</p>\n");
		        htmlCode.append("    <p>Creator: ").append(comment.getCreator_username()).append("</p>\n");
		        htmlCode.append("    <textarea class=\"comment-content\" name=\"new_contents\">").append(comment.getContent()).append("</textarea>\n");
		        htmlCode.append("    <button type=\"submit\" onclick=\"modifyComment(").append(comment.getId()).append(")\">Modify</button>\n");
		        htmlCode.append("</div>\n");
		    }

		    htmlCode.append("</div>\n");
		    htmlCode.append("<script>\n");
		    htmlCode.append("function modifyComment(commentId) {\n");
		    htmlCode.append("        console.log('ID that the button clicked is: ', commentId);\n");
		    htmlCode.append("    const textarea = document.querySelector(`#comment-${commentId} .comment-content`);\n");
		    htmlCode.append("    const newContents = textarea.value;\n");
		    htmlCode.append("    const data = {\n");
		    htmlCode.append("        comment_id: commentId,\n");
		    htmlCode.append("        new_contents: newContents\n");
		    htmlCode.append("    };\n");
		    htmlCode.append("    fetch('/RESTstart/rest/auth/auth_user/modify_comment/modify', {\n");
		    htmlCode.append("        method: 'POST',\n");
		    htmlCode.append("        headers: {\n");
		    htmlCode.append("            'Content-Type': 'application/json'\n");
		    htmlCode.append("        },\n");
		    htmlCode.append("        body: JSON.stringify(data)\n");
		    htmlCode.append("    })\n");
		    htmlCode.append("    .then(response => {\n");
		    htmlCode.append("        if (response.ok) {\n");
		    htmlCode.append("            console.log('Comment modified successfully');\n");
		    htmlCode.append("			 return response.text();");
		    htmlCode.append("        } else {\n");
		    htmlCode.append("            console.log('Failed to modify comment');\n");
		    htmlCode.append("        }\n");
		    htmlCode.append("    })\n");
		    htmlCode.append("    .then(responseText => {\n");
		    htmlCode.append("        console.log('Response:', responseText);\n");
		    htmlCode.append("		 alert(responseText);");
		    htmlCode.append("    })\n");
		    htmlCode.append("    .catch(error => {\n");
		    htmlCode.append("        console.log('An error occurred:', error);\n");
		    htmlCode.append("    });\n");
		    htmlCode.append("}\n");
		    htmlCode.append("</script>\n");
		    htmlCode.append("</body>\n");
		    htmlCode.append("</html>");

		    return htmlCode.toString();
		}
		
		/// This is for approve comments
		public static String getCommentsFromAPPROVE_COMMENTS_auth(ArrayList<Comments> COMMENTS_DATA, String username) {
		    if (COMMENTS_DATA.isEmpty()) {
		        String htmlCode = "<!DOCTYPE html>\n"
		                + "<html>\n"
		                + "<head>\n"
		                + "    <title>Comments Not Found</title>\n"
		                + "</head>\n"
		                + "<body>\n"
		                + "    <h1>COMMENTS_NOT_FOUND</h1>\n"
		                + "</body>\n"
		                + "</html>";

		        return htmlCode;
		    }

		    StringBuilder htmlCode = new StringBuilder();
		    htmlCode.append("<!DOCTYPE html>\n");
		    htmlCode.append("<html>\n");
		    htmlCode.append("<head>\n");
		    htmlCode.append("    <title>Approve comments</title>\n");
		    htmlCode.append("    <style>\n");
		    htmlCode.append("        .comment-container {\n");
		    htmlCode.append("            display: flex;\n");
		    htmlCode.append("            flex-direction: column;\n");
		    htmlCode.append("            align-items: center;\n");
		    htmlCode.append("        }\n");
		    htmlCode.append("        .comment {\n");
		    htmlCode.append("            margin-bottom: 20px;\n");
		    htmlCode.append("            padding: 10px;\n");
		    htmlCode.append("        }\n");
		    htmlCode.append("        .comment-content {\n");
		    htmlCode.append("            font-size: 18px;\n");
		    htmlCode.append("            width: 500px;\n");
		    htmlCode.append("            height: 150px;\n");
		    htmlCode.append("            resize: none;\n");
		    htmlCode.append("            background-color: transparent;\n");
		    htmlCode.append("            overflow: auto;\n");
		    htmlCode.append("        }\n");
		    htmlCode.append("    </style>\n");
		    htmlCode.append("</head>\n");
		    htmlCode.append("<div id=\"Filters\" style=\"border: 1px solid #ccc;\">\n");
	        htmlCode.append("<form method=\"GET\" action=\"/RESTstart/rest/auth/auth_user/approve_comment/filCom\">\n");
		    htmlCode.append("    <label for=\"articleId\">Article ID:</label>\n");
		    htmlCode.append("    <input type=\"text\" id=\"articleId\" name=\"articleId\">\n");
		    htmlCode.append("    <label for=\"commentId\">Comment ID:</label>\n");
		    htmlCode.append("    <input type=\"text\" id=\"commentId\" name=\"commentId\">\n");
		    htmlCode.append("    <input type=\"hidden\" id=\"clickedByName\" name=\"clickedByName\" value=\"").append(username).append("\">\n");
	        htmlCode.append("    <button type=\"submit\">Submit filters</button>\n");
	        htmlCode.append("</form>\n");
		    htmlCode.append("</div>\n");
		    htmlCode.append("<body>\n");
		    htmlCode.append("<div style=\"text-align: right;\">Log in as: ").append(username).append("</div>\n");
		    htmlCode.append("<div class=\"comment-container\">\n");

		    for (Comments comment : COMMENTS_DATA) {
		        htmlCode.append("<div id=\"comment-").append(comment.getId()).append("\" class=\"comment\">\n");
		        htmlCode.append("    <h2>Comment ID: ").append(comment.getId()).append("</h2>\n");
		        htmlCode.append("    <p>Date Creation: ").append(comment.getDate_creation()).append("</p>\n");
		        htmlCode.append("    <p>Article ID: ").append(comment.getArticle_id()).append("</p>\n");
		        htmlCode.append("    <p>State ID: ").append(comment.getState_id()).append("</p>\n");
		        htmlCode.append("    <p>Creator: ").append(comment.getCreator_username()).append("</p>\n");
		        htmlCode.append("    <textarea readonly class=\"comment-content\" name=\"new_contents\">").append(comment.getContent()).append("</textarea>\n");
		        htmlCode.append("    <button type=\"submit\" onclick=\"approveComment(").append(comment.getId()).append(")\">Approve</button>\n");
		        htmlCode.append("</div>\n");
		    }

		    htmlCode.append("</div>\n");
		    htmlCode.append("<script>\n");
		    
		    htmlCode.append("function approveComment(commentId) {\n");
		    htmlCode.append("        console.log('ID that the button clicked is: ', commentId);\n");
		    htmlCode.append("    const textarea = document.querySelector(`#comment-${commentId} .comment-content`);\n");
		    htmlCode.append("    const newContents = textarea.value;\n");
		    htmlCode.append("    const data = {\n");
		    htmlCode.append("        comment_id: commentId,\n");
		    htmlCode.append("        new_contents: newContents\n");
		    htmlCode.append("    };\n");
		    htmlCode.append("    fetch('/RESTstart/rest/auth/auth_user/approve_comment/approve', {\n");
		    htmlCode.append("        method: 'POST',\n");
		    htmlCode.append("        headers: {\n");
		    htmlCode.append("            'Content-Type': 'application/json'\n");
		    htmlCode.append("        },\n");
		    htmlCode.append("        body: JSON.stringify(data)\n");
		    htmlCode.append("    })\n");
		    htmlCode.append("    .then(response => {\n");
		    htmlCode.append("        if (response.ok) {\n");
		    htmlCode.append("            console.log('Comment modified successfully');\n");
		    htmlCode.append("			 return response.text();");
		    htmlCode.append("        } else {\n");
		    htmlCode.append("            console.log('Failed to modify comment');\n");
		    htmlCode.append("        }\n");
		    htmlCode.append("    })\n");
		    htmlCode.append("    .then(responseText => {\n");
		    htmlCode.append("        console.log('Response:', responseText);\n");
		    htmlCode.append("		 alert(responseText);");
		    htmlCode.append("    })\n");
		    htmlCode.append("    .catch(error => {\n");
		    htmlCode.append("        console.log('An error occurred:', error);\n");
		    htmlCode.append("    });\n");
		    htmlCode.append("}\n");
		    htmlCode.append("</script>\n");
		    htmlCode.append("</body>\n");
		    htmlCode.append("</html>");

		    return htmlCode.toString();
		}
		
		
		/// This is for decline comments
		public static String getCommentsFromDECLINE_COMMENTS_auth(ArrayList<Comments> COMMENTS_DATA, String username) {
		    if (COMMENTS_DATA.isEmpty()) {
		        String htmlCode = "<!DOCTYPE html>\n"
		                + "<html>\n"
		                + "<head>\n"
		                + "    <title>Comments Not Found</title>\n"
		                + "</head>\n"
		                + "<body>\n"
		                + "    <h1>COMMENTS_NOT_FOUND</h1>\n"
		                + "</body>\n"
		                + "</html>";

		        return htmlCode;
		    }

		    StringBuilder htmlCode = new StringBuilder();
		    htmlCode.append("<!DOCTYPE html>\n");
		    htmlCode.append("<html>\n");
		    htmlCode.append("<head>\n");
		    htmlCode.append("    <title>Decline comments</title>\n");
		    htmlCode.append("    <style>\n");
		    htmlCode.append("        .comment-container {\n");
		    htmlCode.append("            display: flex;\n");
		    htmlCode.append("            flex-direction: column;\n");
		    htmlCode.append("            align-items: center;\n");
		    htmlCode.append("        }\n");
		    htmlCode.append("        .comment {\n");
		    htmlCode.append("            margin-bottom: 20px;\n");
		    htmlCode.append("            padding: 10px;\n");
		    htmlCode.append("        }\n");
		    htmlCode.append("        .comment-content {\n");
		    htmlCode.append("            font-size: 18px;\n");
		    htmlCode.append("            width: 500px;\n");
		    htmlCode.append("            height: 150px;\n");
		    htmlCode.append("            resize: none;\n");
		    htmlCode.append("            background-color: transparent;\n");
		    htmlCode.append("            overflow: auto;\n");
		    htmlCode.append("        }\n");
		    htmlCode.append("    </style>\n");
		    htmlCode.append("</head>\n");
		    htmlCode.append("<div id=\"Filters\" style=\"border: 1px solid #ccc;\">\n");
	        htmlCode.append("<form method=\"GET\" action=\"/RESTstart/rest/auth/auth_user/approve_comment/filCom\">\n");
		    htmlCode.append("    <label for=\"articleId\">Article ID:</label>\n");
		    htmlCode.append("    <input type=\"text\" id=\"articleId\" name=\"articleId\">\n");
		    htmlCode.append("    <label for=\"commentId\">Comment ID:</label>\n");
		    htmlCode.append("    <input type=\"text\" id=\"commentId\" name=\"commentId\">\n");
		    htmlCode.append("    <input type=\"hidden\" id=\"clickedByName\" name=\"clickedByName\" value=\"").append(username).append("\">\n");
	        htmlCode.append("    <button type=\"submit\">Submit filters</button>\n");
	        htmlCode.append("</form>\n");
		    htmlCode.append("</div>\n");
		    htmlCode.append("<body>\n");
		    htmlCode.append("<div style=\"text-align: right;\">Log in as: ").append(username).append("</div>\n");
		    htmlCode.append("<div class=\"comment-container\">\n");

		    for (Comments comment : COMMENTS_DATA) {
		        htmlCode.append("<div id=\"comment-").append(comment.getId()).append("\" class=\"comment\">\n");
		        htmlCode.append("    <h2>Comment ID: ").append(comment.getId()).append("</h2>\n");
		        htmlCode.append("    <p>Date Creation: ").append(comment.getDate_creation()).append("</p>\n");
		        htmlCode.append("    <p>Article ID: ").append(comment.getArticle_id()).append("</p>\n");
		        htmlCode.append("    <p>State ID: ").append(comment.getState_id()).append("</p>\n");
		        htmlCode.append("    <p>Creator: ").append(comment.getCreator_username()).append("</p>\n");
		        htmlCode.append("    <textarea readonly class=\"comment-content\" name=\"new_contents\">").append(comment.getContent()).append("</textarea>\n");
		        htmlCode.append("    <button type=\"submit\" onclick=\"declineComment(").append(comment.getId()).append(")\">Decline</button>\n");
		        htmlCode.append("</div>\n");
		    }

		    htmlCode.append("</div>\n");
		    htmlCode.append("<script>\n");
		    
		    htmlCode.append("function declineComment(commentId) {\n");
		    htmlCode.append("        console.log('ID that the button clicked is: ', commentId);\n");
		    htmlCode.append("    const textarea = document.querySelector(`#comment-${commentId} .comment-content`);\n");
		    htmlCode.append("    const newContents = textarea.value;\n");
		    htmlCode.append("    const data = {\n");
		    htmlCode.append("        comment_id: commentId,\n");
		    htmlCode.append("        new_contents: newContents\n");
		    htmlCode.append("    };\n");
		    htmlCode.append("    fetch('/RESTstart/rest/auth/auth_user/decline_comment/decline', {\n");
		    htmlCode.append("        method: 'DELETE',\n");
		    htmlCode.append("        headers: {\n");
		    htmlCode.append("            'Content-Type': 'application/json'\n");
		    htmlCode.append("        },\n");
		    htmlCode.append("        body: JSON.stringify(data)\n");
		    htmlCode.append("    })\n");
		    htmlCode.append("    .then(response => {\n");
		    htmlCode.append("        if (response.ok) {\n");
		    htmlCode.append("            console.log('Comment modified successfully');\n");
		    htmlCode.append("			 return response.text();");
		    htmlCode.append("        } else {\n");
		    htmlCode.append("            console.log('Failed to modify comment');\n");
		    htmlCode.append("        }\n");
		    htmlCode.append("    })\n");
		    htmlCode.append("    .then(responseText => {\n");
		    htmlCode.append("        console.log('Response:', responseText);\n");
		    htmlCode.append("		 alert(responseText);");
		    htmlCode.append("    })\n");
		    htmlCode.append("    .catch(error => {\n");
		    htmlCode.append("        console.log('An error occurred:', error);\n");
		    htmlCode.append("    });\n");
		    htmlCode.append("}\n");
		    htmlCode.append("</script>\n");
		    htmlCode.append("</body>\n");
		    htmlCode.append("</html>");

		    return htmlCode.toString();
		}
		
		
		
		/// This is for Display Comments of an article
		public static String getIDS_DISPLAY_COMMENTS_OF_ARTICLE_ARTICLE_HTML(HashSet<String> COMMENTS_ARTICLES_IDs, String username, String role) {
		    String frameHTML = "<div class=\"ids-frame\">";

		    for (String element : COMMENTS_ARTICLES_IDs) {
		    	if(!role.equals("VISITOR"))
		    		frameHTML += "<a href=\"/RESTstart/rest/auth/auth_user/displayCommentsOfArticle_comment/" + element + "\">" + element + "</a> ";
		    	else // changed for authorisation
		        	frameHTML += "<a href=\"/RESTstart/rest/auth/not_auth_user/displayCommentsOfArticle_comment/" + element + "\">" + element + "</a> ";		   
		    }
		    frameHTML += "</div>";

		    String temp = "";
		    if(role.equals("JOURNALIST")) {
            	temp = "<h2>Here we display the articles ids that contains at least one comment and have state APPROVED and also<p>"
            			+ "the articles that they correspond have state PUBLISHED</h2>"
            			+ "<h3>If you want to see the articles that belong to you go (and also articles that have state PUBLISHED) to the function Display all the articles</h3>"; 
            } else if(role.equals("CURATOR")) {
            	temp = "<h2> You can see all the articles that have at least one comment in them</h2>";
            } else if(role.equals("VISITOR")) {
            	temp = "<h2>Here we display the articles ids that contains at least one comment and have state APPROVED and also<p>"
            			+ "the articles that they correspond have state PUBLISHED</h2>"
            			+ "<h3>If you want to see the articles that belong to you go (and also articles that have state PUBLISHED) to the function Display all the articles</h3>"; 
            	username = "Visitor";
            }
		    
		    String htmlCode = "<!DOCTYPE html>\n" +
		            "<html>\n" +
		            "<head>\n" +
		            "    <title>Display comments of an article</title>\n" +
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
		            "        .login-info {\n" +
		            "            position: absolute;\n" +
		            "            top: 10px;\n" +
		            "            right: 10px;\n" +
		            "        }\n" +
		            "    </style>\n" +
		            "</head>\n" +
		            "<body>\n" +
		            "    <div class=\"login-info\">Log in as " + username + "</div>" +
		            "    <div class=\"container\">\n" +
		            "        <h1>The articles that appear have at least one comment in them.</h1>\n" +
		             temp +
		            "        " + frameHTML + "\n" +
		            "    </div>\n" +
		            "</body>\n" +
		            "</html>";

		    return htmlCode;
		}

		public static String getArticleComments(Article ARTICLE_SELECTED, ArrayList<Comments> COMMENTS_DATA, String username) {
			
			StringBuilder htmlCode = new StringBuilder();
			htmlCode.append("<!DOCTYPE html>\n");
			htmlCode.append("<html>\n");
			htmlCode.append("<head>\n");
			htmlCode.append("    <title>Article Page</title>\n");
			htmlCode.append("    <style>\n");
			htmlCode.append("        .container {\n");
			htmlCode.append("            display: flex;\n");
			htmlCode.append("            justify-content: center;\n");
			htmlCode.append("            align-items: flex-start;\n");
			htmlCode.append("            padding: 50px;\n");
			htmlCode.append("        }\n");
			htmlCode.append("        .article {\n");
			htmlCode.append("            border: 1px solid #ccc;\n");
			htmlCode.append("            padding: 10px;\n");
			htmlCode.append("            margin-right: 20px;\n");
			htmlCode.append("        }\n");
			htmlCode.append("        .article-title {\n");
			htmlCode.append("            font-size: 30px;\n");
			htmlCode.append("            font-weight: bold;\n");
			htmlCode.append("            margin-bottom: 10px;\n");
			htmlCode.append("        }\n");
			htmlCode.append("        .article-creator {\n");
			htmlCode.append("            font-size: 14px;\n");
			htmlCode.append("            margin-bottom: 5px;\n");
			htmlCode.append("        }\n");
			htmlCode.append("        .article-date {\n");
			htmlCode.append("            font-size: 14px;\n");
			htmlCode.append("            margin-bottom: 5px;\n");
			htmlCode.append("        }\n");
			htmlCode.append("        .article-id {\n");
			htmlCode.append("            font-size: 14px;\n");
			htmlCode.append("            margin-bottom: 10px;\n");
			htmlCode.append("        }\n");
			htmlCode.append("        .comments {\n");
			htmlCode.append("            border: 1px solid #ccc;\n");
			htmlCode.append("            padding: 10px;\n");
			htmlCode.append("            flex-shrink: 0;\n");
			htmlCode.append("            width: 750px;\n");
			htmlCode.append("        }\n");
			htmlCode.append("        .comment {\n");
			htmlCode.append("            margin-bottom: 10px;\n");
			htmlCode.append("        }\n");
			htmlCode.append("        .comment-content {\n");
			htmlCode.append("            font-size: 25px;\n");
			htmlCode.append("            margin-bottom: 20px;\n");
			htmlCode.append("            margin-top: 10px;\n");
			htmlCode.append("        }\n");
			htmlCode.append("        .comment-date {\n");
			htmlCode.append("            font-size: 14px;\n");
			htmlCode.append("            margin-bottom: 2px;\n");
			htmlCode.append("            margin-top: 0;\n");
			htmlCode.append("        }\n");
			htmlCode.append("    </style>\n");
			htmlCode.append("</head>\n");
			htmlCode.append("<body>\n");
			htmlCode.append("<div style=\"text-align: right;\">Log in as: " + username + "</div>\n");
			htmlCode.append("<div class=\"container\">\n");
			htmlCode.append("    <div class=\"article\">\n");
			htmlCode.append("        <h1 class=\"article-title\">" + ARTICLE_SELECTED.getTitle()  + "</h1>\n");
			htmlCode.append("        <p class=\"article-creator\">Creator username: " + ARTICLE_SELECTED.getCreator_username()  + "</p>\n");
			htmlCode.append("        <p class=\"article-date\">Date creation: " + ARTICLE_SELECTED.getDate_creation() + "</p>\n");
			htmlCode.append("        <p class=\"article-id\">ID: " + ARTICLE_SELECTED.getId() + "</p>\n");
			htmlCode.append("        <h2 class=\"article-contents\">" + ARTICLE_SELECTED.getContents() + "</h2>\n");
			htmlCode.append("    </div>\n");
			htmlCode.append("    <div class=\"comments\">\n");
			htmlCode.append("        <h1>Comments</h1>\n");
			
			for(int i = 0;i < COMMENTS_DATA.size();i++) {
				htmlCode.append("        <div class=\"comment" + COMMENTS_DATA.get(i).getArticle_id() + ">\n");
				htmlCode.append("            <h4 class=\"comment-date\">" + COMMENTS_DATA.get(i).getDate_creation() + "</h4>\n");
				htmlCode.append("            <p class=\"comment-content\">" + COMMENTS_DATA.get(i).getContent() + "</p>\n");
				htmlCode.append("        </div>\n");
			}
			
			htmlCode.append("    </div>\n");
			htmlCode.append("</div>\n");
			htmlCode.append("</body>\n");
			htmlCode.append("</html>");
			
			return htmlCode.toString();
		}
		
		
		
		//// This is for the topics
		
		
		
		///This is for the create of a topic
		public static String getCREATE_TOPIC_HTML(ArrayList<String> TOPICS_LIST, String username) {
			StringBuilder htmlBuilder = new StringBuilder();

		    htmlBuilder.append("<!DOCTYPE html>\n");
		    htmlBuilder.append("<html>\n");
		    htmlBuilder.append("<head>\n");
		    htmlBuilder.append("    <title>Create a topic</title>\n");
		    htmlBuilder.append("    <style>\n");
		    htmlBuilder.append("        body {\n");
		    htmlBuilder.append("            display: flex;\n");
		    htmlBuilder.append("            justify-content: center;\n");
		    htmlBuilder.append("            align-items: center;\n");
		    htmlBuilder.append("            height: 100vh;\n");
		    htmlBuilder.append("            position: relative;\n");
		    htmlBuilder.append("        }\n");
		    htmlBuilder.append("        .container {\n");
		    htmlBuilder.append("            display: flex;\n");
		    htmlBuilder.append("            flex-direction: column;\n");
		    htmlBuilder.append("            align-items: flex-start;\n");
		    htmlBuilder.append("            padding: 20px;\n");
		    htmlBuilder.append("        }\n");
		    htmlBuilder.append("        .label {\n");
		    htmlBuilder.append("            font-weight: bold;\n");
		    htmlBuilder.append("            margin-bottom: 5px;\n");
		    htmlBuilder.append("        }\n");
		    htmlBuilder.append("        .text-input {\n");
		    htmlBuilder.append("            resize: none;\n");
		    htmlBuilder.append("            width: 300px;\n");
		    htmlBuilder.append("            height: 15px;\n");
		    htmlBuilder.append("            margin-bottom: 20px;\n");
		    htmlBuilder.append("        }\n");
		    htmlBuilder.append("        .div-button {\n");
		    htmlBuilder.append("            margin: 0;\n");
		    htmlBuilder.append("            position: absolute;\n");
		    htmlBuilder.append("            top: 65%;\n");
		    htmlBuilder.append("            left: 48%;\n");
		    htmlBuilder.append("        }\n");
		    htmlBuilder.append("        .create-button {\n");
		    htmlBuilder.append("            m	argin-top: 20px;\n");
		    htmlBuilder.append("        }\n");
		    htmlBuilder.append("        .login-info {\n");
		    htmlBuilder.append("            position: absolute;\n");
		    htmlBuilder.append("            top: 20px;\n");
		    htmlBuilder.append("            right: 20px;\n");
		    htmlBuilder.append("        }\n");
		    htmlBuilder.append("    </style>\n");
		    htmlBuilder.append("</head>\n");
		    htmlBuilder.append("<body>\n");
		    htmlBuilder.append("    <div class=\"login-info\">Log in as: " + username + " </div>\n");
		    htmlBuilder.append("    <div class=\"container\">\n");
		    htmlBuilder.append("        <h1>Here you can create a topic</h1>\n");
		    htmlBuilder.append("        <label class=\"label\">Title of the Topic:</label>\n");
		    htmlBuilder.append("        <textarea id=\"title\" class=\"text-input\" placeholder=\"Enter topic title...\"></textarea>\n");
		    htmlBuilder.append("        <label class=\"label\">Parent Topic:</label>\n");
		    htmlBuilder.append("        <select id=\"parentTopic\">\n");
		    
		    htmlBuilder.append("            <option value=" + "Empty" + ">" + "Empty" + "</option>\n");
		    for(int i = 0;i < TOPICS_LIST.size();i++) {
		        htmlBuilder.append("            <option value='" + TOPICS_LIST.get(i) + "'>" + TOPICS_LIST.get(i) + "</option>\n");
		    }
		    
		    htmlBuilder.append("        </select>\n");
		    htmlBuilder.append("        <div class=\"div-button\">\n");
		    htmlBuilder.append("            <button class=\"create-button\" onclick=\"createTopic()\">Create</button>\n");
		    htmlBuilder.append("        </div>\n");
		    htmlBuilder.append("    	<div class=\"response-div\">\n");
		    htmlBuilder.append("        	<p id=\"text\"></p>\n");
		    htmlBuilder.append("    	</div>\n");
		    htmlBuilder.append("    </div>\n");
		    
		    htmlBuilder.append("    <script>\n");
		    htmlBuilder.append("        function createTopic() {\n");
		    htmlBuilder.append("            var title = document.getElementById('title').value;\n");
		    htmlBuilder.append("            var parentTopic = document.getElementById('parentTopic').value;\n");
		    htmlBuilder.append("            var username = '" + username + "';\n");
		    htmlBuilder.append("            var jsonData = {\n");
		    htmlBuilder.append("                title: title,\n");
		    htmlBuilder.append("                parentTopic: parentTopic,\n");
		    htmlBuilder.append("                username: username\n");
		    htmlBuilder.append("            };\n");
		    htmlBuilder.append("            var xhr = new XMLHttpRequest();\n");
		    htmlBuilder.append("            xhr.open('POST', '/RESTstart/rest/auth/auth_user/create_topic/create', true);\n");
		    htmlBuilder.append("            xhr.setRequestHeader('Content-Type', 'application/json');\n");
		    htmlBuilder.append("            xhr.onreadystatechange = function() {\n");
		    htmlBuilder.append("                if (xhr.readyState === 4 && xhr.status === 200) {\n");
		    htmlBuilder.append("                    document.getElementById(\"text\").textContent = xhr.responseText;\n");
		    htmlBuilder.append("                }\n");
		    htmlBuilder.append("            };\n");
		    htmlBuilder.append("            xhr.send(JSON.stringify(jsonData));\n");
		    htmlBuilder.append("        }\n");
		    htmlBuilder.append("    </script>\n");
		    
		    htmlBuilder.append("</body>\n");
		    htmlBuilder.append("</html>\n");

		    return htmlBuilder.toString();
		}
		
		
		
		///This is for the modify Topic
		public static String getIDS_MODIFY_TOPIC_HTML(ArrayList<String> TOPICS_IDs, String username, String role) {
			String frameHTML = "<div class=\"ids-frame\">";
			
			for (int i = 0; i < TOPICS_IDs.size(); i++) {
		        frameHTML += "<a href=\"/RESTstart/rest/auth/auth_user/modify_topic/" + TOPICS_IDs.get(i) + "\">" + TOPICS_IDs.get(i) + "</a> ";
			}
			
			frameHTML += "</div>";
			
			StringBuilder temp = new StringBuilder();
			temp.append("        .login-info {\n");
			temp.append("            position: absolute;\n");
		    temp.append("            top: 20px;\n");
		    temp.append("            right: 20px;\n");
		    temp.append("        }\n");
			
			String htmlCode = "<!DOCTYPE html>\n" +
			        "<html>\n" +
			        "<head>\n" +
			        "    <title>Modify Topic</title>\n" +
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
			        temp.toString() +
			        "    </style>\n" +
			        "</head>\n" +
			        "<body>\n" +
			        "    <div class=\"login-info\">Log in as: " + username + " </div>\n" +
			        "    <div class=\"container\">\n" +
			        "        <h1>Modify a Topic. Choose ID of a topic.</h1>\n" +
			        "        <h2>The Topic that you see, have state CREATED (STATE_ID: 1)<p></h2>\n" +
	                "        " + frameHTML + "\n" +
			        "    </div>\n" +
			        "</body>\n" +
			        "</html>";
			return htmlCode;
		}
		
		public static String getMODIFY_TOPIC_HTML(ArrayList<String> TOPICS_LIST, 
												  String TITLE_FROM_DB, 
												  String PARENT_TOPIC_FROM_DB,
												  int TOPIC_CLICKED) {
			StringBuilder htmlBuilder = new StringBuilder();

		    htmlBuilder.append("<!DOCTYPE html>\n");
		    htmlBuilder.append("<html>\n");
		    htmlBuilder.append("<head>\n");
		    htmlBuilder.append("    <title>Modify a topic</title>\n");
		    htmlBuilder.append("    <style>\n");
		    htmlBuilder.append("        body {\n");
		    htmlBuilder.append("            display: flex;\n");
		    htmlBuilder.append("            justify-content: center;\n");
		    htmlBuilder.append("            align-items: center;\n");
		    htmlBuilder.append("            height: 100vh;\n");
		    htmlBuilder.append("            position: relative;\n");
		    htmlBuilder.append("        }\n");
		    htmlBuilder.append("        .container {\n");
		    htmlBuilder.append("            display: flex;\n");
		    htmlBuilder.append("            flex-direction: column;\n");
		    htmlBuilder.append("            align-items: flex-start;\n");
		    htmlBuilder.append("            padding: 20px;\n");
		    htmlBuilder.append("        }\n");
		    htmlBuilder.append("        .label {\n");
		    htmlBuilder.append("            font-weight: bold;\n");
		    htmlBuilder.append("            margin-bottom: 5px;\n");
		    htmlBuilder.append("        }\n");
		    htmlBuilder.append("        .text-input {\n");
		    htmlBuilder.append("            resize: none;\n");
		    htmlBuilder.append("            width: 300px;\n");
		    htmlBuilder.append("            height: 15px;\n");
		    htmlBuilder.append("            margin-bottom: 20px;\n");
		    htmlBuilder.append("        }\n");
		    htmlBuilder.append("        .div-button {\n");
		    htmlBuilder.append("            margin: 0;\n");
		    htmlBuilder.append("            position: absolute;\n");
		    htmlBuilder.append("            top: 65%;\n");
		    htmlBuilder.append("            left: 48%;\n");
		    htmlBuilder.append("        }\n");
		    htmlBuilder.append("        .create-button {\n");
		    htmlBuilder.append("            m	argin-top: 20px;\n");
		    htmlBuilder.append("        }\n");
		    htmlBuilder.append("        .login-info {\n");
		    htmlBuilder.append("            position: absolute;\n");
		    htmlBuilder.append("            top: 20px;\n");
		    htmlBuilder.append("            right: 20px;\n");
		    htmlBuilder.append("        }\n");
		    htmlBuilder.append("    </style>\n");
		    htmlBuilder.append("</head>\n");
		    htmlBuilder.append("<body>\n");
		    htmlBuilder.append("    <div class=\"container\">\n");
		    htmlBuilder.append("        <h1>Here you can modify the topic with id " + TOPIC_CLICKED + "</h1>\n");
		    htmlBuilder.append("        <label class=\"label\">Title of the Topic:</label>\n");
		    htmlBuilder.append("        <textarea id=\"title\" class=\"text-input\" placeholder=\"Enter topic title...\">" + TITLE_FROM_DB + "</textarea>\n");
		    htmlBuilder.append("        <label class=\"label\">Parent Topic:</label>\n");
		    htmlBuilder.append("        <select id=\"parentTopic\">\n");
		    
		    htmlBuilder.append("            <option value=" + PARENT_TOPIC_FROM_DB + ">" + PARENT_TOPIC_FROM_DB + "</option>\n");
		    for(int i = 0;i < TOPICS_LIST.size();i++) {
		    	if(!PARENT_TOPIC_FROM_DB.equals(TOPICS_LIST.get(i))) // we dont want to add two times the topic that already had picked
		        	htmlBuilder.append("            <option value='" + TOPICS_LIST.get(i) + "'>" + TOPICS_LIST.get(i) + "</option>\n");
		    }
		    
		    htmlBuilder.append("        </select>\n");
		    htmlBuilder.append("        <div class=\"div-button\">\n");
		    htmlBuilder.append("            <button class=\"create-button\" onclick=\"modifyTopic()\">Modify</button>\n");
		    htmlBuilder.append("        </div>\n");
		    htmlBuilder.append("    	<div class=\"response-div\">\n");
		    htmlBuilder.append("        	<p id=\"text\"></p>\n");
		    htmlBuilder.append("    	</div>\n");
		    htmlBuilder.append("    </div>\n");
		    
		    htmlBuilder.append("    <script>\n");
		    htmlBuilder.append("        function modifyTopic() {\n");
		    htmlBuilder.append("            var title = document.getElementById('title').value;\n");
		    htmlBuilder.append("            var parentTopic = document.getElementById('parentTopic').value;\n");
		    htmlBuilder.append("            var jsonData = {\n");
		    htmlBuilder.append("                title: title,\n");
		    htmlBuilder.append("                parentTopic: parentTopic,\n");
		    htmlBuilder.append("                topic_id_clicked: " + TOPIC_CLICKED +"\n");
		    htmlBuilder.append("            };\n");
		    htmlBuilder.append("            var xhr = new XMLHttpRequest();\n");
		    htmlBuilder.append("            xhr.open('POST', '/RESTstart/rest/auth/auth_user/modify_topic/modify', true);\n");
		    htmlBuilder.append("            xhr.setRequestHeader('Content-Type', 'application/json');\n");
		    htmlBuilder.append("            xhr.onreadystatechange = function() {\n");
		    htmlBuilder.append("                if (xhr.readyState === 4 && xhr.status === 200) {\n");
		    htmlBuilder.append("                    document.getElementById(\"text\").textContent = xhr.responseText;\n");
		    htmlBuilder.append("                }\n");
		    htmlBuilder.append("            };\n");
		    htmlBuilder.append("            xhr.send(JSON.stringify(jsonData));\n");
		    htmlBuilder.append("        }\n");
		    htmlBuilder.append("    </script>\n");
		    
		    htmlBuilder.append("</body>\n");
		    htmlBuilder.append("</html>\n");

		    return htmlBuilder.toString();
		}
		
		
		
		///This is for the Approve of the Topics
		public static String getIDS_APPROVE_TOPIC_HTML(ArrayList<String> TOPICS_IDs, String username, String role) {
			String frameHTML = "<div class=\"ids-frame\">";
			
			for (int i = 0; i < TOPICS_IDs.size(); i++) {
		        frameHTML += "<a href=\"/RESTstart/rest/auth/auth_user/approve_topic/" + TOPICS_IDs.get(i) + "\">" + TOPICS_IDs.get(i) + "</a> ";
			}
			
			frameHTML += "</div>";
			
			StringBuilder temp = new StringBuilder();
			temp.append("        .login-info {\n");
			temp.append("            position: absolute;\n");
		    temp.append("            top: 20px;\n");
		    temp.append("            right: 20px;\n");
		    temp.append("        }\n");
			
			String htmlCode = "<!DOCTYPE html>\n" +
			        "<html>\n" +
			        "<head>\n" +
			        "    <title>Approve Topic</title>\n" +
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
			        temp.toString() +
			        "    </style>\n" +
			        "</head>\n" +
			        "<body>\n" +
			        "    <div class=\"login-info\">Log in as: " + username + " </div>\n" +
			        "    <div class=\"container\">\n" +
			        "        <h1>Approve a Topic. Choose ID of a topic.</h1>\n" +
			        "        <h2>The Topic that you see, have state CREATED (STATE_ID: 1)<p></h2>\n" +
	                "        " + frameHTML + "\n" +
			        "    </div>\n" +
			        "</body>\n" +
			        "</html>";
			return htmlCode;
		}
		
		
		public static String getAPPROVE_TOPIC_HTML( 
													  String TITLE_FROM_DB, 
													  String PARENT_TOPIC_FROM_DB,
													  int TOPIC_CLICKED) {
				StringBuilder htmlBuilder = new StringBuilder();
				
				htmlBuilder.append("<!DOCTYPE html>\n");
				htmlBuilder.append("<html>\n");
				htmlBuilder.append("<head>\n");
				htmlBuilder.append("    <title>Approve a topic</title>\n");
				htmlBuilder.append("    <style>\n");
				htmlBuilder.append("        body {\n");
				htmlBuilder.append("            display: flex;\n");
				htmlBuilder.append("            justify-content: center;\n");
				htmlBuilder.append("            align-items: center;\n");
				htmlBuilder.append("            height: 100vh;\n");
				htmlBuilder.append("            position: relative;\n");
				htmlBuilder.append("        }\n");
				htmlBuilder.append("        .container {\n");
				htmlBuilder.append("            display: flex;\n");
				htmlBuilder.append("            flex-direction: column;\n");
				htmlBuilder.append("            align-items: flex-start;\n");
				htmlBuilder.append("            padding: 20px;\n");
				htmlBuilder.append("        }\n");
				htmlBuilder.append("        .label {\n");
				htmlBuilder.append("            font-weight: bold;\n");
				htmlBuilder.append("            margin-bottom: 5px;\n");
				htmlBuilder.append("        }\n");
				htmlBuilder.append("        .text-input {\n");
				htmlBuilder.append("            resize: none;\n");
				htmlBuilder.append("            width: 300px;\n");
				htmlBuilder.append("            height: 15px;\n");
				htmlBuilder.append("            margin-bottom: 20px;\n");
				htmlBuilder.append("        }\n");
				htmlBuilder.append("        .div-button {\n");
				htmlBuilder.append("            margin: 0;\n");
				htmlBuilder.append("            position: absolute;\n");
				htmlBuilder.append("            top: 65%;\n");
				htmlBuilder.append("            left: 48%;\n");
				htmlBuilder.append("        }\n");
				htmlBuilder.append("        .create-button {\n");
				htmlBuilder.append("            m	argin-top: 20px;\n");
				htmlBuilder.append("        }\n");
				htmlBuilder.append("        .login-info {\n");
				htmlBuilder.append("            position: absolute;\n");
				htmlBuilder.append("            top: 20px;\n");
				htmlBuilder.append("            right: 20px;\n");
				htmlBuilder.append("        }\n");
				htmlBuilder.append("    </style>\n");
				htmlBuilder.append("</head>\n");
				htmlBuilder.append("<body>\n");
				htmlBuilder.append("    <div class=\"container\">\n");
				htmlBuilder.append("        <h1>Here you can approve the topic with id " + TOPIC_CLICKED + "</h1>\n");
				htmlBuilder.append("        <label class=\"label\">Title of the Topic:</label>\n");
				htmlBuilder.append("        <textarea id=\"title\" class=\"text-input\" placeholder=\"Enter topic title...\" readonly>" + TITLE_FROM_DB + "</textarea>\n");
			    htmlBuilder.append("        <label class=\"label\">Parent Topic:</label>\n");
				htmlBuilder.append("        <select id=\"parentTopic\">\n");
				htmlBuilder.append("            <option value='" + PARENT_TOPIC_FROM_DB + "'>" + PARENT_TOPIC_FROM_DB + "</option>\n");
			    htmlBuilder.append("        </select>\n");
				htmlBuilder.append("        <div class=\"div-button\">\n");
				htmlBuilder.append("            <button class=\"create-button\" onclick=\"approveTopic()\">Approve</button>\n");
				htmlBuilder.append("        </div>\n");
				htmlBuilder.append("    	<div class=\"response-div\">\n");
				htmlBuilder.append("        	<p id=\"text\"></p>\n");
				htmlBuilder.append("    	</div>\n");
				htmlBuilder.append("    </div>\n");
				
				htmlBuilder.append("    <script>\n");
				htmlBuilder.append("        function approveTopic() {\n");
				htmlBuilder.append("            var title = document.getElementById('title').value;\n");
				htmlBuilder.append("            var parentTopic = document.getElementById('parentTopic').value;\n");
				htmlBuilder.append("            var jsonData = {\n");
				htmlBuilder.append("                title: title,\n");
				htmlBuilder.append("                parentTopic: parentTopic,\n");
				htmlBuilder.append("                topic_id_clicked: " + TOPIC_CLICKED +"\n");
				htmlBuilder.append("            };\n");
				htmlBuilder.append("            var xhr = new XMLHttpRequest();\n");
				htmlBuilder.append("            xhr.open('POST', '/RESTstart/rest/auth/auth_user/approve_topic/approve', true);\n");
				htmlBuilder.append("            xhr.setRequestHeader('Content-Type', 'application/json');\n");
				htmlBuilder.append("            xhr.onreadystatechange = function() {\n");
				htmlBuilder.append("                if (xhr.readyState === 4 && xhr.status === 200) {\n");
				htmlBuilder.append("                    document.getElementById(\"text\").textContent = xhr.responseText;\n");
				htmlBuilder.append("                }\n");
				htmlBuilder.append("            };\n");
				htmlBuilder.append("            xhr.send(JSON.stringify(jsonData));\n");
				htmlBuilder.append("        }\n");
				htmlBuilder.append("    </script>\n");
				
				htmlBuilder.append("</body>\n");
				htmlBuilder.append("</html>\n");
				
				return htmlBuilder.toString();
		}
		
		
		
		///This is for declined of the Topics
				public static String getIDS_DECLINE_TOPIC_HTML(ArrayList<String> TOPICS_IDs, String username, String role) {
					String frameHTML = "<div class=\"ids-frame\">";
					
					for (int i = 0; i < TOPICS_IDs.size(); i++) {
				        frameHTML += "<a href=\"/RESTstart/rest/auth/auth_user/decline_topic/" + TOPICS_IDs.get(i) + "\">" + TOPICS_IDs.get(i) + "</a> ";
					}
					
					frameHTML += "</div>";
					
					StringBuilder temp = new StringBuilder();
					temp.append("        .login-info {\n");
					temp.append("            position: absolute;\n");
				    temp.append("            top: 20px;\n");
				    temp.append("            right: 20px;\n");
				    temp.append("        }\n");
					
					String htmlCode = "<!DOCTYPE html>\n" +
					        "<html>\n" +
					        "<head>\n" +
					        "    <title>Decline/Delete Topic</title>\n" +
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
					        temp.toString() +
					        "    </style>\n" +
					        "</head>\n" +
					        "<body>\n" +
					        "    <div class=\"login-info\">Log in as: " + username + " </div>\n" +
					        "    <div class=\"container\">\n" +
					        "        <h1>Decline/Delete a Topic. Choose ID of a topic.</h1>\n" +
					        "        <h2>The Topic that you see, have state CREATED (STATE_ID: 1)<p></h2>\n" +
			                "        " + frameHTML + "\n" +
					        "    </div>\n" +
					        "</body>\n" +
					        "</html>";
					return htmlCode;
				}
				
				
				public static String getDECLINE_TOPIC_HTML( 
															  String TITLE_FROM_DB, 
															  String PARENT_TOPIC_FROM_DB,
															  int TOPIC_CLICKED) {
						StringBuilder htmlBuilder = new StringBuilder();
						
						htmlBuilder.append("<!DOCTYPE html>\n");
						htmlBuilder.append("<html>\n");
						htmlBuilder.append("<head>\n");
						htmlBuilder.append("    <title>Decline/Delete a topic</title>\n");
						htmlBuilder.append("    <style>\n");
						htmlBuilder.append("        body {\n");
						htmlBuilder.append("            display: flex;\n");
						htmlBuilder.append("            justify-content: center;\n");
						htmlBuilder.append("            align-items: center;\n");
						htmlBuilder.append("            height: 100vh;\n");
						htmlBuilder.append("            position: relative;\n");
						htmlBuilder.append("        }\n");
						htmlBuilder.append("        .container {\n");
						htmlBuilder.append("            display: flex;\n");
						htmlBuilder.append("            flex-direction: column;\n");
						htmlBuilder.append("            align-items: flex-start;\n");
						htmlBuilder.append("            padding: 20px;\n");
						htmlBuilder.append("        }\n");
						htmlBuilder.append("        .label {\n");
						htmlBuilder.append("            font-weight: bold;\n");
						htmlBuilder.append("            margin-bottom: 5px;\n");
						htmlBuilder.append("        }\n");
						htmlBuilder.append("        .text-input {\n");
						htmlBuilder.append("            resize: none;\n");
						htmlBuilder.append("            width: 300px;\n");
						htmlBuilder.append("            height: 15px;\n");
						htmlBuilder.append("            margin-bottom: 20px;\n");
						htmlBuilder.append("        }\n");
						htmlBuilder.append("        .div-button {\n");
						htmlBuilder.append("            margin: 0;\n");
						htmlBuilder.append("            position: absolute;\n");
						htmlBuilder.append("            top: 65%;\n");
						htmlBuilder.append("            left: 48%;\n");
						htmlBuilder.append("        }\n");
						htmlBuilder.append("        .create-button {\n");
						htmlBuilder.append("            m	argin-top: 20px;\n");
						htmlBuilder.append("        }\n");
						htmlBuilder.append("        .login-info {\n");
						htmlBuilder.append("            position: absolute;\n");
						htmlBuilder.append("            top: 20px;\n");
						htmlBuilder.append("            right: 20px;\n");
						htmlBuilder.append("        }\n");
						htmlBuilder.append("    </style>\n");
						htmlBuilder.append("</head>\n");
						htmlBuilder.append("<body>\n");
						htmlBuilder.append("    <div class=\"container\">\n");
						htmlBuilder.append("        <h1>Here you can decline/delete the topic with id " + TOPIC_CLICKED + "</h1>\n");
						htmlBuilder.append("        <label class=\"label\">Title of the Topic:</label>\n");
						htmlBuilder.append("        <textarea id=\"title\" class=\"text-input\" placeholder=\"Enter topic title...\" readonly>" + TITLE_FROM_DB + "</textarea>\n");
					    htmlBuilder.append("        <label class=\"label\">Parent Topic:</label>\n");
						htmlBuilder.append("        <select id=\"parentTopic\">\n");
						htmlBuilder.append("            <option value='" + PARENT_TOPIC_FROM_DB + "'>" + PARENT_TOPIC_FROM_DB + "</option>\n");
					    htmlBuilder.append("        </select>\n");
						htmlBuilder.append("        <div class=\"div-button\">\n");
						htmlBuilder.append("            <button class=\"create-button\" onclick=\"declineTopic()\">Decline/Delete</button>\n");
						htmlBuilder.append("        </div>\n");
						htmlBuilder.append("    	<div class=\"response-div\">\n");
						htmlBuilder.append("        	<p id=\"text\"></p>\n");
						htmlBuilder.append("    	</div>\n");
						htmlBuilder.append("    </div>\n");
						
						htmlBuilder.append("    <script>\n");
						htmlBuilder.append("        function declineTopic() {\n");
						htmlBuilder.append("            var title = document.getElementById('title').value;\n");
						htmlBuilder.append("            var parentTopic = document.getElementById('parentTopic').value;\n");
						htmlBuilder.append("            var jsonData = {\n");
						htmlBuilder.append("                title: title,\n");
						htmlBuilder.append("                parentTopic: parentTopic,\n");
						htmlBuilder.append("                topic_id_clicked: " + TOPIC_CLICKED +"\n");
						htmlBuilder.append("            };\n");
						htmlBuilder.append("            var xhr = new XMLHttpRequest();\n");
						htmlBuilder.append("            xhr.open('POST', '/RESTstart/rest/auth/auth_user/decline_topic/decline', true);\n");
						htmlBuilder.append("            xhr.setRequestHeader('Content-Type', 'application/json');\n");
						htmlBuilder.append("            xhr.onreadystatechange = function() {\n");
						htmlBuilder.append("                if (xhr.readyState === 4 && xhr.status === 200) {\n");
						htmlBuilder.append("                    document.getElementById(\"text\").textContent = xhr.responseText;\n");
						htmlBuilder.append("                }\n");
						htmlBuilder.append("            };\n");
						htmlBuilder.append("            xhr.send(JSON.stringify(jsonData));\n");
						htmlBuilder.append("        }\n");
						htmlBuilder.append("    </script>\n");
						
						htmlBuilder.append("</body>\n");
						htmlBuilder.append("</html>\n");
						
						return htmlBuilder.toString();
				}
				
				
				///This is for the Display of a Topic
				public static String getIDS_DISPLAY_TOPIC_HTML(ArrayList<String> TOPICS_IDs, String username, String role) {
					String frameHTML = "<div class=\"ids-frame\">";
					
					for (int i = 0; i < TOPICS_IDs.size(); i++) {
						if(role.equals("VISITOR"))
							frameHTML += "<a href=\"/RESTstart/rest/auth/not_auth_user/display_topic/" + TOPICS_IDs.get(i) + "?method=GET\">" + TOPICS_IDs.get(i) + "</a> ";
						else
							frameHTML += "<a href=\"/RESTstart/rest/auth/auth_user/display_topic/" + TOPICS_IDs.get(i) + "?method=GET\">" + TOPICS_IDs.get(i) + "</a> ";
					}
					
					frameHTML += "</div>";
					
					String temp1;
					if(role.equals("JOURNALIST"))
						temp1 = "<h2>The Topics that you see here have state APPROVED (STATE_ID: 3), you also see the articles that belongs to you<p></h2>\n";
					else if(role.equals("CURATOR")) // Curator
						temp1 = "<h2>You can see all the topics<p></h2>";
					else { // Visitor
						temp1 = "<h2>You can see topics that are in the state PUBLISHED<p></h2>";
					}
					
					String logIn;
			        if(role.equals("VISITOR")) {
			        	logIn = "    <div class=\"login-info\">Log in as: " + "Visitor" + " </div>\n";
			        } else {
			        	logIn = "    <div class=\"login-info\">Log in as: " + username + " </div>\n";
			        }
					
					StringBuilder temp = new StringBuilder();
					temp.append("        .login-info {\n");
					temp.append("            position: absolute;\n");
				    temp.append("            top: 20px;\n");
				    temp.append("            right: 20px;\n");
				    temp.append("        }\n");
					
					String htmlCode = "<!DOCTYPE html>\n" +
					        "<html>\n" +
					        "<head>\n" +
					        "    <title>Display Topic</title>\n" +
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
					        temp.toString() +
					        "    </style>\n" +
					        "</head>\n" +
					        "<body>\n" +
					        logIn +
					        "    <div class=\"container\">\n" +
					        "        <h1>Display a Topic. Choose ID of a topic.</h1>\n" + 
					        temp1 +			   
					        "        " + frameHTML + "\n" +
					        "    </div>\n" +
					        "</body>\n" +
					        "</html>";
					return htmlCode;
				}	
				
				
				public static String getDISPLAY_TOPIC_HTML(
				        int ID_FROM_DB,
				        String TITLE_FROM_DB,
				        String PARENT_TOPIC_FROM_DB,
				        ArrayList<String> KIDS_TOPICS_FROM_DB) {

				    StringBuilder htmlBuilder = new StringBuilder();

				    htmlBuilder.append("<!DOCTYPE html>\n");
				    htmlBuilder.append("<html>\n");
				    htmlBuilder.append("<head>\n");
				    htmlBuilder.append("    <title>Display a topic</title>\n");
				    htmlBuilder.append("    <style>\n");
				    htmlBuilder.append("        body {\n");
				    htmlBuilder.append("            display: flex;\n");
				    htmlBuilder.append("            justify-content: center;\n");
				    htmlBuilder.append("            align-items: center;\n");
				    htmlBuilder.append("            height: 100vh;\n");
				    htmlBuilder.append("        }\n");
				    htmlBuilder.append("        .container {\n");
				    htmlBuilder.append("            display: flex;\n");
				    htmlBuilder.append("            flex-direction: column;\n");
				    htmlBuilder.append("            align-items: center;\n");
				    htmlBuilder.append("            padding: 20px;\n");
				    htmlBuilder.append("            text-align: center;\n");
				    htmlBuilder.append("        }\n");
				    htmlBuilder.append("        .label {\n");
				    htmlBuilder.append("            font-weight: bold;\n");
				    htmlBuilder.append("            margin-bottom: 5px;\n");
				    htmlBuilder.append("        }\n");
				    htmlBuilder.append("        .text-input {\n");
				    htmlBuilder.append("            resize: none;\n");
				    htmlBuilder.append("            width: 300px;\n");
				    htmlBuilder.append("            height: 15px;\n");
				    htmlBuilder.append("            margin-bottom: 20px;\n");
				    htmlBuilder.append("        }\n");
				    htmlBuilder.append("        .div-button {\n");
				    htmlBuilder.append("            margin: 0;\n");
				    htmlBuilder.append("        }\n");
				    htmlBuilder.append("        .create-button {\n");
				    htmlBuilder.append("            margin-top: 20px;\n");
				    htmlBuilder.append("        }\n");
				    htmlBuilder.append("        .login-info {\n");
				    htmlBuilder.append("            position: absolute;\n");
				    htmlBuilder.append("            top: 20px;\n");
				    htmlBuilder.append("            right: 20px;\n");
				    htmlBuilder.append("        }\n");
				    htmlBuilder.append("    </style>\n");
				    htmlBuilder.append("</head>\n");
				    htmlBuilder.append("<body>\n");
				    htmlBuilder.append("    <div class=\"container\">\n");
				    htmlBuilder.append("        <h1>Displaying the topic with ID " + ID_FROM_DB + "</h1>\n");

				    htmlBuilder.append("        <label class=\"label\">ID of the Topic: " + ID_FROM_DB + "</label>\n");

				    htmlBuilder.append("        <label class=\"label\">Title of the Topic:</label>\n");
				    htmlBuilder.append("        <textarea id=\"title\" class=\"text-input\" placeholder=\"Enter topic title...\" readonly>" + TITLE_FROM_DB + "</textarea>\n");
				    htmlBuilder.append("        <label class=\"label\">Parent Topic: " + PARENT_TOPIC_FROM_DB + "</label>\n");

				    if (KIDS_TOPICS_FROM_DB != null) {
				    	
				    	if(KIDS_TOPICS_FROM_DB.isEmpty()){
					        htmlBuilder.append("        <label class=\"label\">No children topics found</label>\n");
					    } else {
					        htmlBuilder.append("        <label class=\"label\">Children Topics:</label>\n");
					        for (int i = 0; i < KIDS_TOPICS_FROM_DB.size(); i++) {
					            htmlBuilder.append("        <label class=\"label\">Child Topic: " + KIDS_TOPICS_FROM_DB.get(i) + "</label>\n");
					        }
					    }
				    }

				    htmlBuilder.append("    </div>\n");
				    htmlBuilder.append("</body>\n");
				    htmlBuilder.append("</html>\n");

				    return htmlBuilder.toString();
				}
		
				
				
				/// This is for the Display All the Topics 
				public static String getStartOptionsHTML_topics(String name, String role) {
		            
		            String temp_intro, temp_link_action, temp_check_boxes;
		            if(!role.equals("VISITOR")) {
		                temp_intro = "  <h3>USERNAME: " + name + " - ROLE: " + role + "</h3>\r\n";
		                temp_link_action = "  <form action=\"/RESTstart/rest/auth/auth_user/displayAll_topic/displayAll\" method=\"post\">\r\n";
		                temp_check_boxes = "    <label for=\"sortByState\">\r\n"
		                        + "      <input type=\"checkbox\" id=\"sortByState\" name=\"sortByState\" value=\"true\" onclick=\"handleCheckbox(this)\">\r\n"
		                        + "      Sort by State\r\n"
		                        + "    </label>\r\n"
		                        + "    <br>\r\n";
		            } else {
		                temp_intro = "  <h3>ROLE: " + role + "</h3>\r\n";
		                temp_link_action = "  <form action=\"/RESTstart/rest/auth/not_auth_user/displayAll_topic/displayAll\" method=\"post\">\r\n";
		                temp_check_boxes = "";
		            }
		            
		            String htmlCode = "<!DOCTYPE html>\r\n"
		                    + "<html>\r\n"
		                    + "<head>\r\n"
		                    + "  <title>Display all the Topics</title>\r\n"
		                    + "  <style>\r\n"
		                    + "    body {\r\n"
		                    + "      display: flex;\r\n"
		                    + "      justify-content: center;\r\n"
		                    + "      align-items: center;\r\n"
		                    + "      flex-direction: column;\r\n"
		                    + "      height: 100vh;\r\n"
		                    + "    }\r\n"
		                    + "    form {\r\n"
		                    + "      text-align: center;\r\n"
		                    + "    }\r\n"
		                    + "  </style>\r\n"
		                    + "</head>\r\n"
		                    + "<body>\r\n"
		                    + "  <h1>Display all the Topics</h1>\r\n"
		                    + "  <h2>Choose only one option</h2>"
		                    + temp_intro
		                    + temp_link_action
		                    + temp_check_boxes
		                    + "    <label for=\"sortByName\">\r\n"
		                    + "      <input type=\"checkbox\" id=\"sortByName\" name=\"sortByName\" value=\"true\" onclick=\"handleCheckbox(this)\">\r\n"
		                    + "      Sort by Title\r\n"
		                    + "    </label>\r\n"
		                    + "    <br>\r\n"
		                    + "    <input type=\"hidden\" id=\"name\" name=\"name\" value=\"" + name + "\">\r\n"
		                    + "    <input type=\"hidden\" id=\"role\" name=\"role\" value=\"" + role + "\">\r\n"
		                    + "    <input type=\"submit\" value=\"Submit\">\r\n"
		                    + "  </form>\r\n"
		                    + "</body>\r\n"
		                    + "</html>\r\n";

		            return htmlCode;
		        }
				
				public static String getArticlesFromSEARCH_ALL_TOPICS_auth(String clickedByName, String role, ArrayList<Topic> GOAL_TOPIC) {
				    if (GOAL_TOPIC.isEmpty()) {
				        String htmlCode = "<!DOCTYPE html>\n"
				                + "<html>\n"
				                + "<head>\n"
				                + "    <title>Topics Not Found</title>\n"
				                + "</head>\n"
				                + "<body>\n"
				                + "    <h1>TOPICS_NOT_FOUND</h1>\n"
				                + "</body>\n"
				                + "</html>";

				        return htmlCode;
				    }

				    // Convert GOAL_TOPIC ArrayList to JSON array
				    JSONArray topicArray = new JSONArray();
				    for (Topic topic : GOAL_TOPIC) {
				        JSONObject topicObject = new JSONObject();
				        topicObject.put("id", topic.getId());
				        topicArray.add(topicObject);
				    }

				    String script;
				    if(!role.equals("VISITOR")) {
				    	script = "<script>\n"
					            + "function filterTopics() {\n"
					            + "    console.log('filterTopics CALLED')\n"
					            + "    var clickedByName = document.getElementById('clickedByName').value;\n"
					            + "    var state = document.getElementById('state').value;\n"
					            + "    var startDate = document.getElementById('startDate').value;\n"
					            + "    var endDate = document.getElementById('endDate').value;\n"
					            + "    var jsonData = {\n"
					            + "        clickedByName: clickedByName,\n"
					            + "        role: '" + role + "',\n"
					            + "        state: state,\n"
					            + "        startDate: startDate,\n"
					            + "        endDate: endDate,\n"
					            + "        topics: " + topicArray.toString() + "\n" 
					            + "    };\n"
					            + "    var xhr = new XMLHttpRequest();\n"
					            + "    xhr.open('POST', '/RESTstart/rest/auth/auth_user/displayAll_topic/filAp', true);\n"
					            + "    xhr.setRequestHeader('Content-Type', 'application/json');\n"
					            + "    xhr.onreadystatechange = function() {\n"
					            + "        if (xhr.readyState === 4 && xhr.status === 200) {\n"
					            + "            document.body.innerHTML = xhr.responseText;\n" 	  
					            + "        }\n"
					            + "    };\n"
					            + "    xhr.send(JSON.stringify(jsonData));\n"
					            + "}\n"
					            + "</script>";
				    } else {
				    	script = "<script>\n"
					            + "function filterTopics() {\n"
					            + "    console.log('filterTopics CALLED')\n"
					            + "    var clickedByName = document.getElementById('clickedByName').value;\n"
					            + "    var startDate = document.getElementById('startDate').value;\n"
					            + "    var endDate = document.getElementById('endDate').value;\n"
					            + "    var jsonData = {\n"
					            + "        clickedByName: clickedByName,\n"
					            + "        role: '" + role + "',\n"
					            + "        startDate: startDate,\n"
					            + "        endDate: endDate,\n"
					            + "        topics: " + topicArray.toString() + "\n" 
					            + "    };\n"
					            + "    var xhr = new XMLHttpRequest();\n"
					            + "    xhr.open('POST', '/RESTstart/rest/auth/not_auth_user/displayAll_topic/filAp', true);\n"
					            + "    xhr.setRequestHeader('Content-Type', 'application/json');\n"
					            + "    xhr.onreadystatechange = function() {\n"
					            + "        if (xhr.readyState === 4 && xhr.status === 200) {\n"
					            + "            document.body.innerHTML = xhr.responseText;\n" 	  
					            + "        }\n"
					            + "    };\n"
					            + "    xhr.send(JSON.stringify(jsonData));\n"
					            + "}\n"
					            + "</script>";
				    }                 

				    StringBuilder htmlCode = new StringBuilder();

				    htmlCode.append("<!DOCTYPE html>\n");
				    htmlCode.append("<html>\n");
				    htmlCode.append("<head>\n");
				    htmlCode.append("    <title>Topics</title>\n");
				    htmlCode.append("    <style>\n");
				    htmlCode.append("        .topic {\n");
				    htmlCode.append("            margin-bottom: 20px;\n");
				    htmlCode.append("            padding: 10px;\n");
				    htmlCode.append("            border: 1px solid #ccc;\n");
				    htmlCode.append("        }\n");
				    htmlCode.append("        .article h2 {\n");
				    htmlCode.append("            margin-top: 0;\n");
				    htmlCode.append("        }\n");
				    htmlCode.append("        .article p {\n");
				    htmlCode.append("            margin-bottom: 0;\n");
				    htmlCode.append("        }\n");
				    htmlCode.append("    </style>\n");
				    htmlCode.append("</head>\n");
				    htmlCode.append("<body>\n");
				    htmlCode.append("        <div id=\"Filters\" style=\"border: 1px solid #ccc;\">\n");
				    htmlCode.append("            <input type=\"hidden\" id=\"clickedByName\" name=\"clickedByName\" value=\"").append(clickedByName).append("\">\n");
				    htmlCode.append("            <input type=\"hidden\" id=\"role\" name=\"role\" value=\"").append(role).append("\">\n");
				    if(!role.equals("VISITOR") ) {
					    htmlCode.append("            <label for=\"state\">State of Topic:</label>\n");
					    htmlCode.append("            <input type=\"text\" id=\"state\" name=\"state\" list=\"stateOptions\">\n");
					    htmlCode.append("            <datalist id=\"stateOptions\">\n");
					    htmlCode.append("                <option value=\"1\">\n");
					    htmlCode.append("                <option value=\"3\">\n");
					    htmlCode.append("            </datalist>\n");
				    }
				    htmlCode.append("            <label for=\"startDate\">Start date:</label>\n");
				    htmlCode.append("            <input type=\"date\" id=\"startDate\" name=\"startDate\">\n");
				    htmlCode.append("            <label for=\"endDate\">End date:</label>\n");
				    htmlCode.append("            <input type=\"date\" id=\"endDate\" name=\"endDate\">\n");
				    htmlCode.append("        </div>\n");
				    htmlCode.append("        <p></p>\n");
				    htmlCode.append("            <button class=\"submit-button\" onclick=\"filterTopics()\">Submit</button>\n");
				    htmlCode.append("    <p></p>");
				    if(!role.equals("VISITOR"))
				    	htmlCode.append("	<div style=\"text-align: right; padding: 10px;\">Log in as: ").append(clickedByName).append("</div>\n");
				    else 
				    	htmlCode.append("	<div style=\"text-align: right; padding: 10px;\">Log in as: ").append("Visitor").append("</div>\n");

				    /*htmlCode.append("    	<div class=\"response-div\">\n");
				    htmlCode.append("        	<p id=\"text\"></p>\n");
				    htmlCode.append("    	</div>\n");*/
				    
				    for (Topic topic : GOAL_TOPIC) {
				        htmlCode.append("    <div class=\"topic\">\n");
				        htmlCode.append("        <h2>ID: ").append(topic.getId()).append("</h2>\n");
				        htmlCode.append("        <p>Creator username: ").append(topic.getCreator_username()).append("</p>\n");
				        htmlCode.append("        <p>Date creation: ").append(topic.getDate_creation()).append("</p>\n");
				        htmlCode.append("        <p>State ID: ").append(topic.getState_id()).append("</p>\n");
				        htmlCode.append("        <p style=\"font-size: 25px;\">Title: ").append(topic.getTitle()).append("</p>\n");
				        htmlCode.append("    </div>\n");
				    }
				    htmlCode.append("</body>\n");
				    htmlCode.append(script);
				    htmlCode.append("</html>");
				    return htmlCode.toString();
				}

				
				///This is for the search Topics
				public static String getSEARCH_TOPIC_KEY_PHRASES_HTML(String username, String role, int USER_ROLE_ID)	{
					String script_str, temp_username, temp_input;
		            if (USER_ROLE_ID == 1) { // Visitor
		            	script_str = "<script>"
		                        + "function searchTopic() {\r\n"
		                        + "    console.log('filterTopics CALLED');\r\n"
		                        + "    var titleKeyPhrases_ = document.getElementById('titleKeyPhrases').value;\r\n"
		                        + "\r\n"
		                        + "    var url = '/RESTstart/rest/auth/not_auth_user/search_topic/search?search?username=" + username + "&role=" + role + "&titleKeyPhrases=' + encodeURIComponent(titleKeyPhrases_);\r\n"
		                        + "\r\n"
		                        + "    var newWindow = window.open();"
		                        + "    var xhr = new XMLHttpRequest();\r\n"
		                        + "    xhr.open('GET', url, true);\r\n"
		                        + "    xhr.setRequestHeader('Content-Type', 'application/json');\r\n"
		                        + "    xhr.onreadystatechange = function() {\r\n"
		                        + "        if (xhr.readyState === 4 && xhr.status === 200) {\r\n"
		                        + "            newWindow.document.open();\r\n"
		                        + "            newWindow.document.write(xhr.responseText);\r\n"
		                        + "            newWindow.document.close();"
		                        + "        }\r\n"
		                        + "    };\r\n"
		                        + "    xhr.send();\r\n"
		                        + "}\r\n"
		                        + "</script>";
		                 temp_username = "<h2>The topics you see have state APPROVED (STATE_ID: 3)";
		                 temp_input = "";
		            } else if(USER_ROLE_ID == 2){ // Journalist can not see all the articles 
		            	script_str = "<script>"
		                        + "function searchTopic() {\r\n"
		                        + "    console.log('filterTopics CALLED');\r\n"
		                        + "    var titleKeyPhrases_ = document.getElementById('titleKeyPhrases').value;\r\n"
		                        + "\r\n"
		                        + "    var url = '/RESTstart/rest/auth/auth_user/search_topic/search?username=" + username + "&role=" + role + "&titleKeyPhrases=' + encodeURIComponent(titleKeyPhrases_);\r\n"
		                        + "\r\n"
		                        + "    var newWindow = window.open();"
		                        + "    var xhr = new XMLHttpRequest();\r\n"
		                        + "    xhr.open('GET', url, true);\r\n"
		                        + "    xhr.setRequestHeader('Content-Type', 'application/json');\r\n"
		                        + "    xhr.onreadystatechange = function() {\r\n"
		                        + "        if (xhr.readyState === 4 && xhr.status === 200) {\r\n"
		                        + "            newWindow.document.open();\r\n"
		                        + "            newWindow.document.write(xhr.responseText);\r\n"
		                        + "            newWindow.document.close();"
		                        + "        }\r\n"
		                        + "    };\r\n"
		                        + "    xhr.send();\r\n"
		                        + "}\r\n"
		                        + "</script>";
		                 temp_username =  "        <h2>The topics you see belongs to " + username + " OR have state APPROVED (STATE_ID: 3)";
		                 temp_input = "    <input type=\"hidden\" name=\"username\" value=\"" + username + "\">\n";
		            } else { // Curator can see all the articles 
		            	script_str = "<script>"
		                        + "function searchTopic() {\r\n"
		                        + "    console.log('filterTopics CALLED');\r\n"
		                        + "    var titleKeyPhrases_ = document.getElementById('titleKeyPhrases').value;\r\n"
		                        + "\r\n"
		                        + "    var url = '/RESTstart/rest/auth/auth_user/search_topic/search?username=" + username + "&role=" + role + "&titleKeyPhrases=' + encodeURIComponent(titleKeyPhrases_);\r\n"
		                        + "\r\n"
		                        + "    var newWindow = window.open();"
		                        + "    var xhr = new XMLHttpRequest();\r\n"
		                        + "    xhr.open('GET', url, true);\r\n"
		                        + "    xhr.setRequestHeader('Content-Type', 'application/json');\r\n"
		                        + "    xhr.onreadystatechange = function() {\r\n"
		                        + "        if (xhr.readyState === 4 && xhr.status === 200) {\r\n"
		                        + "            newWindow.document.open();\r\n"
		                        + "            newWindow.document.write(xhr.responseText);\r\n"
		                        + "            newWindow.document.close();"
		                        + "        }\r\n"
		                        + "    };\r\n"
		                        + "    xhr.send();\r\n"
		                        + "}\r\n"
		                        + "</script>";
		                 temp_username =  "        <h2>The topics you see belongs to " + username + " and have have all the states possible";
		                 temp_input = "    <input type=\"hidden\" name=\"username\" value=\"" + username + "\">\n";
		            }
		            String html_code = "<!DOCTYPE html>\n"
		                + "<html>\n"
		                + "<head>\n"
		                + "    <title>Search Topic</title>\n"
		                + "    <style>\n"
		                + "        body {\n"
		                + "            display: flex;\n"
		                + "            justify-content: center;\n"
		                + "            align-items: center;\n"
		                + "            height: 100vh;\n"
		                + "        }\n"
		                + "        .container {\n"
		                + "            text-align: center;\n"
		                + "        }\n"
		                + "        form {\n"
		                + "            margin-top: 20px;\n"
		                + "        }\n"
		                + "        textarea {\n"
		                + "            resize: none;\n"
		                + "            overflow: auto;\n"
		                + "            height: 150px;\n"
		                + "            width: 300px;\n"
		                + "        }\n"
		                + "    </style>\n"
		                + "</head>\n"
		                + "<body>\n"
		                + "    <div class=\"container\">\n"
		                + "        <h1>Search Topic</h1>\n"
		                + "         <h2>Enter the key phrase for the title of the topic/topics you want to search</h2>"   
		                + temp_username
		                + "\n"
		                + "            <label for=\"titleKeyPhrases\"><p>Title Key Phrases:</label>\n"
		                + "            <br>\n"
		                + "            <textarea id=\"titleKeyPhrases\" name=\"titleKeyPhrases\" rows=\"5\" cols=\"30\"></textarea>\n"
		                + "            <br>\n"
		                + "\n"
		                + temp_input
		                + "            <button class=\"submit\" onclick=\"searchTopic()\">Search</button>\n"
		                + "    </div>\n"
		                + script_str
		                + "</body>\n"
		                + "</html>";
		            return html_code;
				}
				
		        public static String getTopicsFromSEARCH_ARTICLES(ArrayList<Topic> GOAL_TOPICS) {
		            
		            if(GOAL_TOPICS.isEmpty()) {
		                String htmlCode = "<!DOCTYPE html>\n"
		                        + "<html>\n"
		                        + "<head>\n"
		                        + "    <title>Topics Not Found</title>\n"
		                        + "</head>\n"
		                        + "<body>\n"
		                        + "    <h1>TOPICS_NOT_FOUND</h1>\n"
		                        + "</body>\n"
		                        + "</html>";

		                return htmlCode;
		            }
		            
		            StringBuilder htmlCode = new StringBuilder();

		            htmlCode.append("<!DOCTYPE html>\n");
		            htmlCode.append("<html>\n");
		            htmlCode.append("<head>\n");
		            htmlCode.append("    <title>Topics</title>\n");
		            htmlCode.append("    <style>\n");
		            htmlCode.append("        .topic {\n");
		            htmlCode.append("            margin-bottom: 20px;\n");
		            htmlCode.append("            padding: 10px;\n");
		            htmlCode.append("            border: 1px solid #ccc;\n");
		            htmlCode.append("        }\n");
		            htmlCode.append("        .article h2 {\n");
		            htmlCode.append("            margin-top: 0;\n");
		            htmlCode.append("        }\n");
		            htmlCode.append("        .article p {\n");
		            htmlCode.append("            margin-bottom: 0;\n");
		            htmlCode.append("        }\n");
		            htmlCode.append("    </style>\n");
		            htmlCode.append("</head>\n");
		            htmlCode.append("<body>\n");

		            for (Topic topic : GOAL_TOPICS) {
		                htmlCode.append("    <div class=\"topic\">\n");
		                htmlCode.append("        <h2>ID: ").append(topic.getId()).append("</h2>\n");
		                htmlCode.append("        <p>State ID: ").append(topic.getState_id()).append("</p>\n");
		                htmlCode.append("        <p>Cretor username: ").append(topic.getCreator_username()).append("</p>\n");
		                htmlCode.append("        <p>Title: ").append(topic.getTitle()).append("</p>\n");
		                htmlCode.append("    </div>\n");
		            }

		            htmlCode.append("</body>\n");
		            htmlCode.append("</html>");

		            return htmlCode.toString();
		        }
        
        
        
        ///This is for the Display Articles of a Topic
        public static String getDISPLAY_ARTICLES_OF_A_TOPIC_START_CODE(ArrayList<String> TOPICS_DB, String username, String role) {
        	
        	String script;
        	if(!role.equals("VISITOR")) {
	        	script = "<script>function getArticles(topic_clicked) {\r\n"
	        			+ "    console.log('getArticles CALLED');\r\n"
	        			+ "\r\n"
	        			+ "    var url = '/RESTstart/rest/auth/auth_user/displayArticlesOfTopic_topic/display?username=" + username + "&role=" + role + "&topic_clicked=' + topic_clicked;\r\n"	        			+ "    var xhr = new XMLHttpRequest();\r\n"
	        			+ "    xhr.open('GET', url, true);\r\n"
	        			+ "    // Remove or set Content-Type to application/json\r\n"
	        			+ "    xhr.setRequestHeader('Content-Type', 'application/json');\r\n"
	        			+ "    xhr.onreadystatechange = function() {\r\n"
	        			+ "        if (xhr.readyState === 4 && xhr.status === 200) {\r\n"
	        			+ "			   document.querySelector('.articles').innerHTML = xhr.responseText;"
	        			+ "        }\r\n"
	        			+ "    };\r\n"
	        			+ "    xhr.send();\r\n"
	        			+ "}</script>";
        	} else {
        		script = "<script>function getArticles(topic_clicked) {\r\n"
	        			+ "    console.log('getArticles CALLED');\r\n"
	        			+ "\r\n"
	        			+ "    var url = '/RESTstart/rest/auth/not_auth_user/displayArticlesOfTopic_topic/display?username=" + username + "&role=" + role + "&topic_clicked=' + topic_clicked;\r\n"	        			
	        			+ "    var xhr = new XMLHttpRequest();\r\n"
	        			+ "    var xhr = new XMLHttpRequest();\r\n"
	        			+ "    xhr.open('GET', url, true);\r\n"
	        			+ "    // Remove or set Content-Type to application/json\r\n"
	        			+ "    xhr.setRequestHeader('Content-Type', 'application/json');\r\n"
	        			+ "    xhr.onreadystatechange = function() {\r\n"
	        			+ "        if (xhr.readyState === 4 && xhr.status === 200) {\r\n"
	        			+ "			   document.querySelector('.articles').innerHTML = xhr.responseText;"
	        			+ "        }\r\n"
	        			+ "    };\r\n"
	        			+ "    xhr.send();\r\n"
	        			+ "}</script>";
        	}
        	
        	StringBuilder htmlBuilder = new StringBuilder();
        	htmlBuilder.append("<!DOCTYPE html>\r\n");
        	htmlBuilder.append("<html lang=\"en\">\r\n");
        	htmlBuilder.append("<head>\r\n");
        	htmlBuilder.append("    <meta charset=\"UTF-8\">\r\n");
        	htmlBuilder.append("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n");
        	htmlBuilder.append("    <title>News App</title>\r\n");
        	htmlBuilder.append("    <style>\r\n");
        	htmlBuilder.append("        body {\r\n");
        	htmlBuilder.append("            font-family: Arial, sans-serif;\r\n");
        	htmlBuilder.append("            margin: 0;\r\n");
        	htmlBuilder.append("            padding: 0;\r\n");
        	htmlBuilder.append("        }\r\n");
        	htmlBuilder.append("\r\n");
        	htmlBuilder.append("        /* Top bar for login */\r\n");
        	htmlBuilder.append("        .login-bar {\r\n");
        	htmlBuilder.append("            background-color: #333;\r\n");
        	htmlBuilder.append("            color: #fff;\r\n");
        	htmlBuilder.append("            padding: 10px;\r\n");
        	htmlBuilder.append("            display: flex;\r\n");
        	htmlBuilder.append("            justify-content: space-between;\r\n");
        	htmlBuilder.append("        }\r\n");
        	htmlBuilder.append("\r\n");
        	htmlBuilder.append("        /* Top bar for topics */\r\n");
        	htmlBuilder.append("        .top-bar {\r\n");
        	htmlBuilder.append("            background-color: #333;\r\n");
        	htmlBuilder.append("            color: #fff;\r\n");
        	htmlBuilder.append("            padding: 10px;\r\n");
        	htmlBuilder.append("            width: 100%;\r\n");
        	htmlBuilder.append("            z-index: 9999;\r\n");
        	htmlBuilder.append("            overflow-x: auto;\r\n");
        	htmlBuilder.append("            white-space: nowrap;\r\n");
        	htmlBuilder.append("        }\r\n");
        	htmlBuilder.append("\r\n");
        	htmlBuilder.append("        .topics {\r\n");
        	htmlBuilder.append("            list-style: none;\r\n");
        	htmlBuilder.append("            margin: 0;\r\n");
        	htmlBuilder.append("            padding: 0;\r\n");
        	htmlBuilder.append("            display: inline-block;\r\n");
        	htmlBuilder.append("        }\r\n");
        	htmlBuilder.append("\r\n");
        	htmlBuilder.append("        .topics li {\r\n");
        	htmlBuilder.append("            display: inline-block;\r\n");
        	htmlBuilder.append("            cursor: pointer;\r\n");
        	htmlBuilder.append("            padding: 10px;\r\n");
        	htmlBuilder.append("            border-right: 1px solid #fff;\r\n");
        	htmlBuilder.append("        }\r\n");
        	htmlBuilder.append("\r\n");
        	htmlBuilder.append("        .topics li:last-child {\r\n");
        	htmlBuilder.append("            border-right: none;\r\n");
        	htmlBuilder.append("        }\r\n");
        	htmlBuilder.append("\r\n");
        	htmlBuilder.append("        /* Main content */\r\n");
        	htmlBuilder.append("        .content {\r\n");
        	htmlBuilder.append("            padding-top: 50px; /* To give space for the fixed top bar */\r\n");
        	htmlBuilder.append("        }\r\n");
        	htmlBuilder.append("\r\n");
        	htmlBuilder.append("        .articles {\r\n");
        	htmlBuilder.append("            list-style: none;\r\n");
        	htmlBuilder.append("            padding: 0;\r\n");
        	htmlBuilder.append("        }\r\n");
        	htmlBuilder.append("\r\n");
        	htmlBuilder.append("        .articles li {\r\n");
        	htmlBuilder.append("            margin-bottom: 10px;\r\n");
        	htmlBuilder.append("        }\r\n");
        	htmlBuilder.append("    </style>\r\n");
        	htmlBuilder.append("</head>\r\n");
        	htmlBuilder.append("<body>\r\n");
        	htmlBuilder.append("    <div class=\"login-bar\">\r\n");
        	if (!role.equals("VISITOR"))
        	    htmlBuilder.append("        <div style=\"padding: 10px;\">Log in as: ").append(username).append("</div>\n");
        	else
        	    htmlBuilder.append("        <div style=\"padding: 10px;\">Log in as: ").append("Visitor").append("</div>\n");
        	htmlBuilder.append("    </div>\r\n");
        	htmlBuilder.append("    <div class=\"top-bar\">\r\n");
        	htmlBuilder.append("        <ul class=\"topics\">\r\n");

        	for (int i = 0; i < TOPICS_DB.size(); i++) {
        	    htmlBuilder.append("<li onclick=\"getArticles('").append(TOPICS_DB.get(i)).append("')\">")
                .append(TOPICS_DB.get(i)).append("</li>");
        	}

        	htmlBuilder.append("        </ul>\r\n");
        	htmlBuilder.append("    </div>\r\n");
        	htmlBuilder.append("\r\n");
        	htmlBuilder.append("    <div class=\"articles\">\r\n");
        	
        	htmlBuilder.append("    </div>\r\n");
        	htmlBuilder.append("</body>\r\n");
        	htmlBuilder.append(script);
        	htmlBuilder.append("</html>\r\n");

        	String html_str = htmlBuilder.toString();
        	return html_str;

        }
}
