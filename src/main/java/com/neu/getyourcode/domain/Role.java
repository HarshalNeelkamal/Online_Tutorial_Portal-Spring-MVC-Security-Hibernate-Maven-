package com.neu.getyourcode.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name="role")
public class Role {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private String roleName;
	@ManyToMany(mappedBy="roles")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<appUser> users;
	
	public Role(){
		
	}

	public Role(int id, String roleName, List<appUser> users) {
		super();
		this.id = id;
		this.roleName = roleName;
		this.users = users;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public List<appUser> getUsers() {
		return users;
	}

	public void setUsers(List<appUser> users) {
		this.users = users;
	}
	
	
}
