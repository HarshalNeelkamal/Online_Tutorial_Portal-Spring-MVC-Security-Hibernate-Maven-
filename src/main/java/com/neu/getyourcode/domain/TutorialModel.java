package com.neu.getyourcode.domain;

import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class TutorialModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int tut_id;
	@NotEmpty
	@Length(max=25,message="Title should not be more then 25 characters in length")
	private String tut_title;
	@NotEmpty
	@Column(columnDefinition="Text", name="Description")
	private String description;
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name="tutopics", joinColumns = @JoinColumn(name="tut_id"), inverseJoinColumns=@JoinColumn(name="topic_id"))
	private List<TopicModel> topics;
	@NotNull
	private int user_id;
	@ManyToOne
	private appUser app_user;
	
	
	public TutorialModel() {
		super();
	}
	public TutorialModel(int tut_id, String tut_title, List<TopicModel> topics) {
		super();
		this.tut_id = tut_id;
		this.tut_title = tut_title;
		this.topics = topics;
	}
	
	
	public appUser getApp_user() {
		return app_user;
	}
	public void setApp_user(appUser app_user) {
		this.app_user = app_user;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public int getTut_id() {
		return tut_id;
	}
	public void setTut_id(int tut_id) {
		this.tut_id = tut_id;
	}
	public String getTut_title() {
		return tut_title;
	}
	public void setTut_title(String tut_title) {
		this.tut_title = tut_title;
	}
	public List<TopicModel> getTopics() {
		return topics;
	}
	public void setTopics(List<TopicModel> topics) {
		this.topics = topics;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void addNewTopic(TopicModel topic){
		this.getTopics().add(topic);
	}
	
	
}
