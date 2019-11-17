package com.webServices.rest.restFulWebServices.entities.post;

import java.net.URI;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.webServices.rest.restFulWebServices.entities.user.User;
import com.webServices.rest.restFulWebServices.entities.user.UserNotFoundException;
import com.webServices.rest.restFulWebServices.entities.user.UserRepository;
import com.webServices.rest.restFulWebServices.exception.PostNotFoundException;

@RestController
public class PostJpaResource {
	
	
	@Autowired
	private PostRepository service;
	
	@Autowired
	private UserRepository userService;
	
		
	@GetMapping(path = "/jpa/users/{id}/posts")
	public MappingJacksonValue findAll(@PathVariable Integer id) {
		Optional<User> user = userService.findById(id);
		Set<Post> posts = new HashSet<>();
		if(user.isPresent()) {
			posts = service.findByUser(user.get());			
		}		
		// since i know the user that owns the post i dont need to return it on json, otherwise i would have a n+1 problem 
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("postDate","message","id"); // saying what i want to show on my response
		
		FilterProvider filterProvider = new SimpleFilterProvider().addFilter("PostsFilter", filter); // here i specify the filter provider name and add the filters to be applied
		
		MappingJacksonValue mappedValues = new MappingJacksonValue(posts);	// here i input the entity to be filtered out on its properties 
		mappedValues.setFilters(filterProvider); // here i set the filter provider to apply the filter rules
		return mappedValues;
			
	}	
	
	//using filtering on the mapping dynamically 
	@GetMapping(path = "/jpa/users/{id}/posts", headers = "filtered-version=true")
	public MappingJacksonValue findAllFiltered(@PathVariable Integer id) {
		
		Optional<User> user = userService.findById(id);
		Set<Post> posts = new HashSet<>();
		if(user.isPresent()) {
			posts = service.findByUser(user.get());			
		}	
		// since i know the user that owns the post i dont need to return it on json, otherwise i would have a n+1 problem 
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("postDate","message"); // saying what i want to show on my response
		
		FilterProvider filterProvider = new SimpleFilterProvider().addFilter("PostsFilter", filter); // here i specify the filter provider name and add the filters to be applied
		
		MappingJacksonValue mappedValues = new MappingJacksonValue(posts);	// here i input the entity to be filtered out on its properties 
		mappedValues.setFilters(filterProvider); // here i set the filter provider to apply the filter rules
		return mappedValues;
		
	}
	
	// here below is the implementation using the hateoas feature 
//	@GetMapping(path = "/users/{id}/posts/{postId}")
//	public EntityModel<Post> findOne(@PathVariable Integer id, @PathVariable Integer postId) {
//		
//		Post post = service.findPostByUser(id, postId);
//		
//		EntityModel<Post> resource = new EntityModel<>(post);
//		WebMvcLinkBuilder linkToFindAll = linkTo(methodOn(this.getClass()).findAll(id));		
//		resource.add(linkToFindAll.withRel("all-user-posts"));
//		
//		return resource;
//	}
	
	@GetMapping(path = "/jpa/users/{userId}/posts/{postId}")
	public MappingJacksonValue findOne(@PathVariable int userId, @PathVariable int postId) {
		Post post = service.findPostByUser(userId, postId);
		if(Objects.isNull(post)) {
			throw new PostNotFoundException("post id - "+postId+"Not Found"); // in this not found exception i configured an specific response code  
		}
		
		return filterPost(post);	
		
	} 
	
	@PostMapping(path = "/jpa/users/{id}/posts")
	public ResponseEntity<Object> save(@Valid @RequestBody Post post, @PathVariable Integer id) {
		
		Optional<User> userFound = userService.findById(id);
		if(!userFound.isPresent()) {
			throw new UserNotFoundException("Usuário id = "+id+" Não encontrado");
		}
		
		post.setUser(userFound.get());
		
		service.save(post);
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{postId}")
				.buildAndExpand(post.getId())
				.toUri();
		return ResponseEntity.created(location).build();
	}
	
	@DeleteMapping(path = "/jpa/users/{userId}/posts/{postId}")
	public ResponseEntity<Object> deletePostByUserAndPostId(@PathVariable Integer userId, @PathVariable Integer postId) {
		Post post = service.deletePostByUserAndPostId(userId, postId);
		if(Objects.isNull(post)){
			throw new PostNotFoundException("Post id "+postId+" Not found for User "+userId);
		}
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping(path = "/jpa/users/{userId}/posts/{postId}")
	public ResponseEntity<Object> updateUserPost(@PathVariable Integer userId, @PathVariable Integer postId, @Valid @RequestBody Post post){
		
		Optional<User> user = userService.findById(userId);
		if(user.isPresent()) {
			List<Post> posts = user.get().getPosts().stream().collect(Collectors.toList());
			Post updatePost =  posts.stream().filter(pst -> pst.getId().equals(postId)).findFirst().orElse(null);
			if(Objects.nonNull(post.getMessage())) {
				updatePost.setMessage(post.getMessage());
			}
			
			updatePost.setPostDate(post.getPostDate());
			updatePost.setUser(user.get());
			service.save(updatePost);
			
			URI location = ServletUriComponentsBuilder
					.fromCurrentRequest()
					.path("/{postId}")
					.buildAndExpand(post.getId())
					.toUri();
			
			return ResponseEntity.created(location).build();
		}
		throw new PostNotFoundException("Post id "+postId+" Not found for User "+userId);		
		
	}
	
	
	private MappingJacksonValue filterPost(Post post) {
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("postDate","message","id"); // saying what i want to show on my response
		
		FilterProvider filterProvider = new SimpleFilterProvider().addFilter("PostsFilter", filter); // here i specify the filter provider name and add the filters to be applied
		
		MappingJacksonValue mappedValues = new MappingJacksonValue(post);	// here i input the entity to be filtered out on its properties 
		mappedValues.setFilters(filterProvider); // here i set the filter provider to apply the filter rules
		return mappedValues;
	} 
	
	
}
