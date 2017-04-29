package com.neu.getyourcode.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="examples")
public class ExampleModel {
	
	@NotEmpty
	private String title;
	@NotEmpty
	@Column(columnDefinition="Text", name="Content")
	private String content;
	@Id
	@Column(name="example_id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int example_id;
	@NotNull
	private int user_id;
	@ManyToOne()
	private appUser app_user;
	
	
	public appUser getUser() {
		return app_user;
	}
	public void setUser(appUser app_user) {
		this.app_user = app_user;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getExample_id() {
		return example_id;
	}
	public void setExample_id(int example_id) {
		this.example_id = example_id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	
	@Override
	public String toString() {
		return "ExampleModel [title=" + title + ", Content=" + content + ", example_id=" + example_id + ", user_id="
				+ user_id + "]";
	}
	
	
	
}
