package com.neu.getyourcode.service;

import java.util.List;

import com.neu.getyourcode.domain.Role;
import com.neu.getyourcode.domain.UserDetails;
import com.neu.getyourcode.domain.appUser;

public interface userSignupService {

	public String addUser(appUser user);
	public void deleteUserWithId(int id);
	public boolean updateUser(appUser user);
	public String getUserName(int id);
	public String getUserEmail(int id);
	public List<appUser> List();
	public appUser getUserWithName(String name);
	public void addUserDetails(UserDetails details);
	public void updateUserDetails(UserDetails details);
	public UserDetails retriveUserDetailsWithId(int detailId);
	public void updateRole(Role r);
	public Role getRoleForId(int id);
	public List<appUser> getUserListForKey(String key);
}