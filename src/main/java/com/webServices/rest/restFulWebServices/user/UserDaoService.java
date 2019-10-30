package com.webServices.rest.restFulWebServices.user;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

@Component
public class UserDaoService {	

	private static List<User> users = new ArrayList<>();
	private static int usersCount = 4;
	
	static {
		try {
			users.add(new User(1, "Josias", new SimpleDateFormat("dd/MM/yyyy").parse("25/10/2000") ) );
			users.add(new User(2, "Elias", new SimpleDateFormat("dd/MM/yyyy").parse("10/10/1980") ) );
			users.add(new User(3, "Malaquias", new SimpleDateFormat("dd/MM/yyyy").parse("01/12/1988") ) );
			users.add(new User(4, "Sofonias", new SimpleDateFormat("dd/MM/yyyy").parse("26/07/1985") ) );
		} catch (ParseException e) {			
			e.printStackTrace();
		}		
	}
	
	public static List<User> getUsers() {
		return users;
	}
	
	// findAll()
	public List<User> findAll(){
		return users;
	}
	
	// findOne()
	public User findOne(int id) {
		for(User us : users) {
			if(us.getId().intValue() == id) {
				return us;
			}
		}
		return null;
	}
	
	// save(User user)
	public User save(User user) {
		if(user.getId() == null) {
			user.setId(++usersCount);
		}
		users.add(user);
		return user;
	}

	public User deleteById(Integer id) {
		Iterator<User> iterator = users.iterator();
		while(iterator.hasNext()) {
			User user = iterator.next();
			if(user.getId() == id) {
				iterator.remove();
				return user;
			}
		}
		return null;
		
	}
	
	
}
