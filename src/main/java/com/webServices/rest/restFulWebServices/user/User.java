package com.webServices.rest.restFulWebServices.user;

import java.util.Date;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;

//@ApiModel(description = "Users information") // info to be displayed at swagger documentation about this model
public class User {
	
	private Integer id;
	
	@Size(min = 2,message = "Name should have at least 2 characters") // validation to be performed over this property and its error message
	//@ApiModelProperty(notes = "The name should have ate least 2 characters") // information to be displayed at the api configuration - swagger, to say a rule about this property
	private String name;
	
	@Past(message = "future dates are not allowed") // validation to be performed over this property and its error message
	//@ApiModelProperty(notes = "the date need to be in past") // information to be displayed at the api configuration - swagger, to say a rule about this property
	private Date birthdate;
	
	public User() {}
			
	public User(Integer id, String name, Date birthdate) {
		super();
		this.id = id;
		this.name = name;
		this.birthdate = birthdate;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	@Override
	public String toString() {
		return String.format("User [id=%s, name=%s, birthdate=%s]", id, name, birthdate);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((birthdate == null) ? 0 : birthdate.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (birthdate == null) {
			if (other.birthdate != null)
				return false;
		} else if (!birthdate.equals(other.birthdate))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
		
}
