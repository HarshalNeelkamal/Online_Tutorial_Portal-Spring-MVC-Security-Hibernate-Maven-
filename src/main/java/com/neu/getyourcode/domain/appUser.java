package com.neu.getyourcode.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="appUser")
public class appUser {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@NotEmpty(message="username is required")
	@Column(unique=true)
	private String username;
	@NotEmpty(message="password is required")
	//@Length(max=10,min=8,message="Password should be 8 to 10 characters in length")
	private String password;
	@NotEmpty(message="email is required")
	@Email(message="please enter a valid email id")
	@Column(unique=true)
	private String emailId;
//	@LazyCollection(LazyCollectionOption.FALSE)
//	@OneToOne(mappedBy = "user")
	@OneToOne(orphanRemoval = true)
	private UserDetails details;
	@ManyToMany
	@JsonIgnore
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Role> roles;
	@OneToMany(mappedBy="app_user", orphanRemoval = true)
	@JsonIgnore
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<ExampleModel> examples = new ArrayList<ExampleModel>();
	private boolean enabled = true;
	@OneToMany(mappedBy="app_user", orphanRemoval = true)
	@JsonIgnore
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<TutorialModel> tutorials = new ArrayList<TutorialModel>();
	
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public List<ExampleModel> getExamples() {
		return examples;
	}
	public void setExamples(List<ExampleModel> examples) {
		this.examples = examples;
	}
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	public UserDetails getDetails() {
		return details;
	}
	public void setDetails(UserDetails details) {
		this.details = details;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public List<TutorialModel> getTutorials() {
		return tutorials;
	}
	public void setTutorials(List<TutorialModel> tutorials) {
		this.tutorials = tutorials;
	}
	@Override
	public String toString() {
		return "appUser [username=" + username + ", password=" + password + ", emailId=" + emailId + "]";
	}
}
