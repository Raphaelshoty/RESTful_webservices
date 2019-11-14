package com.webServices.rest.restFulWebServices.entities.post;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.webServices.rest.restFulWebServices.entities.user.UserDaoService;


public class PostDaoService extends UserDaoService{

	private static List<Post> posts = new ArrayList<>();
	private int userPosts = 5;
	
	static {
		try {
			posts.add(new Post(1, "Fantastic", getUsers().get(0) ,new SimpleDateFormat("dd/MM/yyyy").parse("25/10/2000")));			
			posts.add(new Post(1, "Fantastic", getUsers().get(1) ,new SimpleDateFormat("dd/MM/yyyy").parse("25/10/2000")));			
			posts.add(new Post(1, "Fantastic", getUsers().get(2) ,new SimpleDateFormat("dd/MM/yyyy").parse("25/10/2000")));			
			posts.add(new Post(1, "Fantastic", getUsers().get(3) ,new SimpleDateFormat("dd/MM/yyyy").parse("25/10/2000")));			
		} catch (ParseException e) {			
			e.printStackTrace();
		}
	}
	
	public List<Post> findAllByUser(Integer userId) {
		return posts.stream().filter(ps -> ps.getUser().getId().equals(userId)).collect(Collectors.toList());
	}
	
	public Post save(Post post) {
		if(Objects.isNull(post.getId())) {
			post.setId(++userPosts);
		}
		posts.stream().filter(ps -> ps.getUser().equals(post.getUser())).collect(Collectors.toList()).add(post);
		return post;
	}
	
	public Post findPostByUser( Integer userId, Integer postid) {
		return posts.stream().
		filter(ps -> ps.getUser().getId().equals(userId))
		.filter(psu -> psu.getId().equals(postid))
		.findFirst()
		.orElse(null);		
	}
	
	public Post deleteByPostId(Integer userId , Integer postId) {
		List<Post> postsUser = posts.stream().filter(ps -> ps.getUser().getId().equals(userId)).collect(Collectors.toList());		
		Iterator<Post> it = postsUser.iterator();
		while(it.hasNext()) {
			Post post = it.next();
			if(post.getId().equals(postId)) {
				it.remove();
				return post;
			}
		}
		return null;
	}
	
	
	
}
