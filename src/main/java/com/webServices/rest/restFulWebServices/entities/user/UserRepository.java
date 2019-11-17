package com.webServices.rest.restFulWebServices.entities.user;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Integer>{

	@Query("Select user from User user where UPPER(user.name) Like Upper(:name) ")
	Set<User> findByPartialName(String name);
	
	
	@Query("Select user From User user Where user.birthdate = parseDateTime(:dtNascimento,'dd/MM/yyyy') ")
	Set<User> findByBirthdate(String dtNascimento);
	
}
