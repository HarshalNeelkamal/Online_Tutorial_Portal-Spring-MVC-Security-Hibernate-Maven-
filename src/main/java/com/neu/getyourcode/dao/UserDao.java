package com.neu.getyourcode.dao;

import java.util.List;

import com.neu.getyourcode.domain.User;

public interface UserDao {

	public void addUser(User user);
	public void updateUser(User user);
	public User getUser(int id);
	public User getUserByName(String name);
	public void deleteUser(int id);
	public List<User> listUser();
	
}
