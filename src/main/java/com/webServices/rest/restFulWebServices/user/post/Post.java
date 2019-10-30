package com.webServices.rest.restFulWebServices.user.post;

import java.util.Date;

import com.webServices.rest.restFulWebServices.user.User;

public class Post {
	
	private Integer id;
	private String message;
	private User user;
	private Date postDate;
	
	public Post() {}
	

	public Post(Integer id, String message, User user, Date postDate) {
		super();
		this.id = id;
		this.message = message;
		this.user = user;
		this.postDate = postDate;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getPostDate() {
		return postDate;
	}

	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}
	
	

	
	
	
	
}
