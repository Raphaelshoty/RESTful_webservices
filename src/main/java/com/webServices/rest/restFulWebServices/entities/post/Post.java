package com.webServices.rest.restFulWebServices.entities.post;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;
import com.webServices.rest.restFulWebServices.entities.user.User;

//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;

//@ApiModel(description = "Informations about the Post entity/class") // info to be displayed at swagger documentation about this model

//@JsonIgnoreProperties(value = {"id","postDate"}) // this is a way to ignore the property on json response of the object

@JsonFilter("PostsFilter") // this property matches with the filter rule specified on PostResource controller on getAllFiltered and this rule will be applied as specified
@Entity
@Table(name = "Post")
public class Post {
	
	//@JsonIgnore // this tells the spring context to not include this property on json response of this object
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Size(min = 5, message = "The message should have at least 5 characters")
	//@ApiModelProperty(notes = "this property needs to have at least 5 characters")// information to be displayed at the api configuration - swagger, to say a rule about this property
	@Column(name = "message", nullable = false)
	private String message;
	
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_user_post"))
	private User user;
	
	@Past(message = "The post date canot be a future date")
	//@ApiModelProperty(notes = "this property needs to have a past date") // information to be displayed at the api configuration - swagger, to say a rule about this property
	@Column(name ="post_date", nullable = false)	
	private Date postDate;
	
	public Post() {}
	

	public Post(Integer id, String message, User user, Date postDate) {
		super();
		this.id = id;
		this.message = message;
		this.user = user;
		this.postDate = postDate;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getPostDate() {
		return postDate;
	}

	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}
	
	

	
	
	
	
}
