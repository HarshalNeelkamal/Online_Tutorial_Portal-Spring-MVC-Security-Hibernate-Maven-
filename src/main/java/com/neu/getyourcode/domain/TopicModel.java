package com.neu.getyourcode.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class TopicModel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int topic_id;
	@NotEmpty
	private String topic_title;
	@NotEmpty
	@Column(columnDefinition="Text")
	private String topic_content;
	
	
	public TopicModel() {
		super();
	}
	public TopicModel(int topic_id, String topic_title, String topic_content) {
		super();
		this.topic_id = topic_id;
		this.topic_title = topic_title;
		this.topic_content = topic_content;
	}
	public int getTopic_id() {
		return topic_id;
	}
	public void setTopic_id(int topic_id) {
		this.topic_id = topic_id;
	}
	public String getTopic_title() {
		return topic_title;
	}
	public void setTopic_title(String topic_title) {
		this.topic_title = topic_title;
	}
	public String getTopic_content() {
		return topic_content;
	}
	public void setTopic_content(String topic_content) {
		this.topic_content = topic_content;
	}
	@Override
	public String toString() {
		return "TopicModel [topic_id=" + topic_id + ", topic_title=" + topic_title + ", topic_content=" + topic_content
				+ "]";
	}
	
	
	
}
