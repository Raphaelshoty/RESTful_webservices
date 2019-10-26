package com.webServices.rest.restFulWebServices.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserResource {
	
	@Autowired
	UserDaoService service;

	@GetMapping(path = "/users")
	public List<User> findAll(){
		return service.findAll();
	}
	
	@GetMapping(path = "/users/{id}")
	public User findOne(@PathVariable int id) {
		return service.findOne(id);
	}
	
	//input - details of user
	//output - CREATED - return the created URI
	@PostMapping(path = "/users")
	public void save(@RequestBody User user) {
		User us = service.save(user);
	}
	
}
