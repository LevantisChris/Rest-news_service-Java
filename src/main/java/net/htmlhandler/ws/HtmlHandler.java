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
		        "	 <a class=\"link\" href=\"/RESTstart/rest/auth/auth_user/submit_article?username=" + username + "&role=" + "JOURNALIST" + "\">Submit Article</a>\n" +
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
		        "	 <a class=\"link\" href=\"/RESTstart/rest/auth/auth_user/submit_article?username=" + username + "&role=" + "CURATOR" + "\">Submit Article</a>\n" +
		        "	 <a class=\"link\" href=\"/RESTstart/rest/auth/auth_user/approve_article?username=" + username + "&role=" + "CURATOR" + "\">Approve Article</a>\n" +
		        "	 <a class=\"link\" href=\"/RESTstart/rest/auth/auth_user/decline_article?username=" + username + "&role=" + "CURATOR" + "\">Decline Article</a>\n" +
		        "	 <a class=\"link\" href=\"/RESTstart/rest/auth/auth_user/publish_article?username=" + username + "&role=" + "CURATOR" + "\">Article Puplication</a>\n" +
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
                "        <form action=\"/RESTstart/rest/auth/auth_user/create_article/create\" method=\"post\">\n" +
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
	
	/// NOTE: For modify article ...
	public static String getIDS_MODIFY_ARTICLE_HTML(ArrayList<String> ARTICLES_IDs) {
		String frameHTML = "<div class=\"ids-frame\">";
		
		for (int i = 0; i < ARTICLES_IDs.size(); i++) {
	        frameHTML += "<a href=\"/RESTstart/rest/auth/auth_user/modify_article/" + ARTICLES_IDs.get(i) + "?method=GET\">" + ARTICLES_IDs.get(i) + "</a> ";
		}
		
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
	        frameHTML += "<a href=\"/RESTstart/rest/auth/auth_user/submit_article/" + ARTICLES_IDs.get(i) + "?method=GET\">" + ARTICLES_IDs.get(i) + "</a> ";
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
	        frameHTML += "<a href=\"/RESTstart/rest/auth/auth_user/approve_article/" + ARTICLES_IDs.get(i) + "?method=GET\">" + ARTICLES_IDs.get(i) + "</a> ";
		}
		
		frameHTML += "</div>";
		
		String htmlCode = "<!DOCTYPE html>\n" +
		        "<html>\n" +
		        "<head>\n" +
		        "    <title>Accept Article</title>\n" +
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
	        frameHTML += "<a href=\"/RESTstart/rest/auth/auth_user/decline_article/" + ARTICLES_IDs.get(i) + "?method=GET\">" + ARTICLES_IDs.get(i) + "</a> ";
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
}
