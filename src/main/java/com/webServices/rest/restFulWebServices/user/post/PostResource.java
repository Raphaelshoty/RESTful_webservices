package com.webServices.rest.restFulWebServices.user.post;

import java.net.URI;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.webServices.rest.restFulWebServices.exception.PostNotFoundException;

@RestController
public class PostResource {
	
	
	private PostDaoService service = new PostDaoService();
	
	@GetMapping(path = "/users/{id}/posts")
	public List<Post> findAll(@PathVariable Integer id) {
		return service.findAllByUser(id);
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
