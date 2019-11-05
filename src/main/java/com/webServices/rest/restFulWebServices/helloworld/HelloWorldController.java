package com.webServices.rest.restFulWebServices.helloworld;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

//controller
@RestController // annotation to tell spring that this class is going to recieve and respond rest calls
public class HelloWorldController {
	
	@Autowired
	private MessageSource ms;
	
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
	
	// internationalized request
	//this one needs the ResourceBundleMessageSource to be on the base class of the application
	@RequestMapping(method = RequestMethod.GET, path = "/hello-world-internationalized")	
	public String helloWorldInternationalized(@RequestHeader(name = "Accept-Language", required = false ) Locale locale) { // Locale as a request header tells the service which message to use otherwise use default
		return ms.getMessage("good.morning.message", null, locale);
	}
	
	// internationalized request
	//this one just need to be configured on spring properties and make use of the AcceptHeaderLocaleResolver object on the localeResolver bean
	
	@RequestMapping(method = RequestMethod.GET, path = "/hello-world-internationalized-easier")	
	public String helloWorldInternationalizedEasier() { // Locale as a request header tells the service which message to use otherwise use default
		return ms.getMessage("good.morning.message", null, LocaleContextHolder.getLocale());
	}
	
	
}
