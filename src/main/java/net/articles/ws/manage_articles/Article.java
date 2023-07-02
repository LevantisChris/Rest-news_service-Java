package net.articles.ws.manage_articles;

/* This class is used int the functions:
 * Search Article */

public class Article {

	private int id;
	private String title;
	private String contents;
	
	public Article(int id, String title, String contents) {
		this.id = id;
		this.title = title;
		this.contents = contents;
	}

	public int getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getContents() {
		return contents;
	}
	
}
