package com.webServices.rest.restFulWebServices.entities.user;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.webServices.rest.restFulWebServices.exception.UsersNotFoundException; // static import for linkto methods

@RestController
public class UserJpaResource {
	
	@Autowired
	private UserRepository service;

	@GetMapping(path = "/jpa/users")
	public List<User> findAll(){
		List<User> users = service.findAll();
		if(Objects.isNull(users)) {
			throw new UsersNotFoundException("No users found at this resource");
		}			
		return users;
	}
	
	// here below is an example of use of hateoas
//	@GetMapping(path = "/jpa/users/{id}")
//	public EntityModel<User> findOne(@PathVariable int id) {
//		User user = service.findOne(id);
//		if(Objects.isNull(user)) {
//			throw new UserNotFoundException("id-"+id); // in this not found exception i configured an specific response code  
//		}
//		
//		//HATEOAS
//		//all-users, SERVER_PATH+"/users"
//		//findAll
//		EntityModel<User> resource = new EntityModel<>(user);
//		WebMvcLinkBuilder linkToFindAll = linkTo(methodOn(this.getClass()).findAll());		
//		resource.add(linkToFindAll.withRel("all-users"));
//		//HATEOAS
//		
//		return resource;
//	}
	
	@GetMapping(path = "/jpa/users/{id}")
	public User findOne(@PathVariable int id) {
		Optional<User> user = service.findById(id);
		if(!user.isPresent()) {
			throw new UserNotFoundException("id-"+id); // in this not found exception i configured an specific response code  
		}
		return user.get();
	}
	
	//input - details of user
	//output - CREATED - return the created URI
	@PostMapping(path = "/jpa/users")
	public ResponseEntity<Object> save(@Valid @RequestBody User user) { // the @Valid is a annotation to validate the value passed as parameter on this resource 
		User us = service.save(user);
			
		//CREATED 	
		URI location = ServletUriComponentsBuilder
		.fromCurrentRequest()
		.path("/{id}")
		.buildAndExpand(us.getId())
		.toUri();
		
		return ResponseEntity.created(location).build();// telling that the resource has been created with response code will be 201
	}
	
	@DeleteMapping(path = "/jpa/users/{id}") // remember that in order to remove an user that has posts associated, we hate to reflect it on its posts, in other words, delete its posts first an them remove the user itself
	public void deleteUser(@PathVariable Integer id) {
		Optional<User> userFound = service.findById(id);					
		if(!userFound.isPresent()) {
			throw new UserNotFoundException("User id = "+id+" Not found");
		}	
		service.deleteById(id);
	}
	
	@PutMapping(path = "/jpa/users/{id}")
	public void updateUser(@PathVariable Integer id) {
		Optional<User> user = service.findById(id);
		if(!user.isPresent()) {
			throw new UserNotFoundException("User id = "+id+" Not found");
		}
		service.save(user.get());
	}
	
}
