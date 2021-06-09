package com.example.cnblog.instance;

public class Article {
	private int id;
	private String title;
	private String summary;
	private String published;
	private String updated;
	private String link;
	private String sourceName;
	private String authorurl;
	private String blogApp;
	public String getBlogApp() {
		return blogApp;
	}
	public String getAuthorurl() {
		return authorurl;
	}
	public void setAuthorurl(String authorurl) {
		this.authorurl = authorurl;
	}
	private String views;
	private String comments;
	private String url;
	private String uri;
	private String avatar;
	public String getUri() {
		return uri;
	}
	public String getAvatar() {
		return avatar;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getPublished() {
		return published;
	}
	public void setPublished(String published) {
		this.published = published;
	}
	public String getUpdated() {
		return updated;
	}
	public void setUpdated(String updated) {
		this.updated = updated;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getViews() {
		return views;
	}
	public void setViews(String views) {
		this.views = views;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getSourceName() {
		return sourceName;
	}
	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public void setBlogApp(String blogApp) {
		// TODO Auto-generated method stub
		this.blogApp=blogApp;
	}
	
}
