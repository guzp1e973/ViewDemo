package com.example.cnblog.instance;



public class Bloger {
	private String id;
	private String title;
	private String updated;
	private String blogapp;
	private int postcount;
	private String avatar;
	
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getId() {
		return id;
	}
	public void setId(String string) {
		this.id = string;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUpdated() {
		return updated;
	}
	public void setUpdated(String updated) {
		this.updated = updated;
	}
	public String getBlogapp() {
		return blogapp;
	}
	public void setBlogapp(String blogapp) {
		this.blogapp = blogapp;
	}
	public int getPostcount() {
		return postcount;
	}
	public void setPostcount(int postcount) {
		this.postcount = postcount;
	}
	
}
