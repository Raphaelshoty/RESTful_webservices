package com.webServices.rest.restFulWebServices;

public class HelloWorldBean {

	private String message;
	
	public HelloWorldBean(String msg) {
		this.message = msg;
	}
	
	public HelloWorldBean() {
		
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return String.format("HelloWorldBean [message=%s]", message);
	}
	
	
	
}
