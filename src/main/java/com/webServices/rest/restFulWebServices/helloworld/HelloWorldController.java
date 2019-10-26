package com.webServices.rest.restFulWebServices.helloworld;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

//controller
@RestController // annotation to tell spring that this class is going to recieve and respond rest calls
public class HelloWorldController {
	
	//GET
	//URI - /hello-world
	//Method returns "Hello World"
	
	//@GetMapping(path = "/hello-world") // this sintax produce the same result as the used below 
	
	@RequestMapping(method = RequestMethod.GET, path = "/hello-world")	
	public String helloWorld() {
		return "Hello World !";
	}
	
	@GetMapping(path = "/hello-world-bean")	
	public HelloWorldBean helloWorldBean() {
		return  new HelloWorldBean("Hello World !");
	}
	
	@GetMapping(path = "/hello-world/path-parameter/{parameter}")	
	public HelloWorldBean helloWorldWithPathVariable(@PathVariable String parameter) {
		return  new HelloWorldBean(String.format("Hello World do %s", parameter));
	}
	
	
}
