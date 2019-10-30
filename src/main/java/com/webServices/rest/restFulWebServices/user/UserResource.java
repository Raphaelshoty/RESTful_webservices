package com.webServices.rest.restFulWebServices.user;

import java.net.URI;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.webServices.rest.restFulWebServices.exception.UsersNotFoundException;

@RestController
public class UserResource {
	
	@Autowired
	UserDaoService service;

	@GetMapping(path = "/users")
	public List<User> findAll(){
		List<User> users = service.findAll();
		if(Objects.isNull(users)) {
			throw new UsersNotFoundException("No users found at this resource");
		}
		return users;
	}
	
	@GetMapping(path = "/users/{id}")
	public User findOne(@PathVariable int id) {
		User user = service.findOne(id);
		if(Objects.isNull(user)) {
			throw new UserNotFoundException("id-"+id); // in this not found exception i configured an specific response code  
		}
		return user;
	}
	
	//input - details of user
	//output - CREATED - return the created URI
	@PostMapping(path = "/users")
	public ResponseEntity<Object> save(@RequestBody User user) {
		User us = service.save(user);
		//CREATED 
		URI location = ServletUriComponentsBuilder
		.fromCurrentRequest()
		.path("/{id}")
		.buildAndExpand(us.getId())
		.toUri();
		
		return ResponseEntity.created(location).build();// telling that the resource has been created with response code will be 201
	}
	
	@DeleteMapping(path = "/users/{id}")
	public void deleteUser(@PathVariable Integer id) {
		User user = service.deleteById(id);			
		if(Objects.isNull(user)) {
			throw new UserNotFoundException("User id = "+id+" Not found");
		}		
	}
	
}
