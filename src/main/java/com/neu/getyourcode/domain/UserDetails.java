package com.neu.getyourcode.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;

@Entity
public class UserDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int detail_id;
	@Column(columnDefinition="Text")
	private String about;
	@Email
	private String email;
	@URL
	private String linkedIn;
	private String other;
//	@OneToOne
//	@JoinColumn(name = "id")
	private int user;
	
	public UserDetails() {
		super();
	}

	public UserDetails(int detail_id, String about, String email, String linkedIn, String other, int user) {
		super();
		this.detail_id = detail_id;
		this.about = about;
		this.email = email;
		this.linkedIn = linkedIn;
		this.other = other;
		this.user = user;
	}

	public int getDetail_id() {
		return detail_id;
	}

	public void setDetail_id(int detail_id) {
		this.detail_id = detail_id;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLinkedIn() {
		return linkedIn;
	}

	public void setLinkedIn(String linkedIn) {
		this.linkedIn = linkedIn;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	public int getUser() {
		return user;
	}

	public void setUser(int user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "UserDetails [detail_id=" + detail_id + ", about=" + about + ", email=" + email + ", linkedIn="
				+ linkedIn + ", other=" + other + ", user=" + user + "]";
	}
	
}
