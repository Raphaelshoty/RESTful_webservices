package com.webServices.rest.restFulWebServices.user.post;

import java.net.URI;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.webServices.rest.restFulWebServices.exception.PostNotFoundException;

@RestController
public class PostResource {
	
	
	private PostDaoService service = new PostDaoService();
	
	@GetMapping(path = "/users/{id}/posts")
	public List<Post> findAll(@PathVariable Integer id) {
		return service.findAllByUser(id);
	}
	
	
	//using filtering on the mapping dynamically 
	@GetMapping(path = "/users/{id}/posts", headers = "filtered-version")
	public MappingJacksonValue findAllFiltered(@PathVariable Integer id) {
		
		List<Post> posts = service.findAllByUser(id);
		
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("postDate","message","user","id"); // saying what i want to show on my response
		
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
	
	@GetMapping(path = "/users/{userId}/posts/{postId}", headers = "filtered-version")
	public MappingJacksonValue findOneFiltered(@PathVariable int userId, @PathVariable int postId) {
		
		Post post = service.findPostByUser(userId, postId);
		if(Objects.isNull(post)) {
			throw new PostNotFoundException("post id - "+postId+"Not Found"); // in this not found exception i configured an specific response code  
		}
			
			SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("postDate","message","id"); // saying what i want to show on my response
			
			FilterProvider filterProvider = new SimpleFilterProvider().addFilter("PostsFilter", filter); // here i specify the filter provider name and add the filters to be applied
			
			MappingJacksonValue mappedValues = new MappingJacksonValue(post);	// here i input the entity to be filtered out on its properties 
			mappedValues.setFilters(filterProvider); // here i set the filter provider to apply the filter rules
			
			return mappedValues;			
		
	} 
	
	@GetMapping(path = "/users/{userId}/posts/{postId}")
	public Post findOne(@PathVariable int userId, @PathVariable int postId) {
		Post post = service.findPostByUser(userId, postId);
		if(Objects.isNull(post)) {
			throw new PostNotFoundException("post id - "+postId+"Not Found"); // in this not found exception i configured an specific response code  
		}
		return post;
	} 
	
	@PostMapping(path = "/users/{id}/posts")
	public ResponseEntity<Object> save(@Valid @RequestBody Post post) {
		service.save(post);
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{postId}")
				.buildAndExpand(post.getId())
				.toUri();
		return ResponseEntity.created(location).build();
	}
	
	@DeleteMapping(path = "/users/{userId}/posts/{postId}")
	public void deletePostById(@PathVariable Integer userId, @PathVariable Integer postId) {
		Post post = service.deleteByPostId(userId, postId);
		if(Objects.isNull(post)){
			throw new PostNotFoundException("Post id "+postId+" Not found for User "+userId);
		}
	}
	
	
}
