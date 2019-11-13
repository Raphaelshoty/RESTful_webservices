package com.webServices.rest.restFulWebServices.versioning;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonVersioningController {
	
	//this is one approach of versioning restFul web services  
	// simply having different URI  eg: http://localhost:8080/v2/person
	
	@GetMapping(path = "v1/person")
	public Person1 person1() {
		return new Person1("Josyas Lima");
	}	
	
	@GetMapping(path = "v2/person")
	public Person2 person2() {
		return new Person2(new Name("Josyas", "Lima"));
	}

	/////// this is a second approach to perform a versioning 
	// use of path params on path parameters eg: http://localhost:8080/person/param?version=1
	
	@GetMapping(path = "/person/param", params = "version=1")
	public Person1 param1() {
		return new Person1("Josyas Lima");
	}
	
	@GetMapping(path = "/person/param" ,params = "version=2")
	public Person2 param2() {
		return new Person2(new Name("Josyas", "Lima"));
	}
	
	// this is the third way of versioning restful web services
	// the use of headers on header of request eg: api-version=1
	
	@GetMapping(path = "/person/header", headers = "api-version=1")
	public Person1 header1() {
		return new Person1("Josyas Lima");
	}
	
	@GetMapping(path = "/person/header" ,headers = "api-version=2")
	public Person2 header2() {
		return new Person2(new Name("Josyas", "Lima"));
	}
	
	// this is the fourth way of versioning restful webservices 
	// the use of different produces on header 
	// on header use the Accept keyword and use the type of produces you want eg: http://localhost:8080/person/produces   with the header keyword Accept with 'application/person-v1+json' 
	
	@GetMapping(path = "/person/produces", produces = "application/person-v1+json")
	public Person1 produces1() {
		return new Person1("Josyas Lima");
	}
	
	@GetMapping(path = "/person/produces" , produces = "application/person-v2+json")
	public Person2 produces2() {
		return new Person2(new Name("Josyas", "Lima"));
	}
	
	
	
}
