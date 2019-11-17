package com.webServices.rest.restFulWebServices.entities.post;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.webServices.rest.restFulWebServices.entities.user.User;

@Repository
@Transactional
public interface PostRepository extends JpaRepository<Post, Integer> {

	@Query("Select post From Post post Inner join fetch post.user user Where user = :user ")
	Set<Post> findByUser(User user);
	
	@Query("Select post From Post post Where post.postDate = parseDateTime(:date,'dd/MM/yyyy') ")
	Set<Post> findByPostDate(String date);
	
	@Query("Select post from Post post Where lower(post.message) like lower(:message) ")
	Set<Post> findByPieceOfMessage(String message);

	
	@Query("Select post From Post post Inner Join Fetch post.user user Where post.id = :postId  And user.id = :userId")
	Post findPostByUser(int userId, int postId);

	
	@Modifying
	@Query(value = "Delete From Post Where Post.id = :postId and Post.user_id = :userId", nativeQuery = true)
	Post deletePostByUserAndPostId(Integer userId, Integer postId);


	@Modifying
	@Query(value = "Update Post set Post.message = :message where Post.user_id = :userId and Post.id = :postId", nativeQuery = true)
	Post updatePostByUserAndPostId(Integer userId, Integer postId, String message);
	
}
