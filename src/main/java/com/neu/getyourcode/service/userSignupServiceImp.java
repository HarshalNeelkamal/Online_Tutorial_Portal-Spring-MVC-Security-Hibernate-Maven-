package com.neu.getyourcode.service;

import java.util.List;

import javax.persistence.Transient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.neu.getyourcode.dao.SignupDao;
import com.neu.getyourcode.domain.ExampleModel;
import com.neu.getyourcode.domain.Role;
import com.neu.getyourcode.domain.UserDetails;
import com.neu.getyourcode.domain.appUser;


@Service
public class userSignupServiceImp implements userSignupService{

	@Autowired
	private SignupDao signupDao;
	
	@Override
	public String addUser(appUser user) {
		appUser newUser = this.signupDao.getUserWithEmail(user.getEmailId()); 
		if(newUser == null)
			newUser = this.signupDao.getUserWithName(user.getUsername());
		else
			return "Account for user with emailId "+user.getEmailId()+" already exists";
		if(newUser == null){
			UserDetails details = new UserDetails();
			details.setUser(user.getId());
			user.setDetails(details);
			signupDao.addUserDetails(details);
			this.signupDao.addUser(user);
		}
		else
			return "User name "+user.getUsername()+" already taken";

		return null;
	}

	@Override
	public boolean updateUser(appUser user) {
		boolean success = true; 
		success = this.signupDao.updateUser(user);
		return success;
	}

	@Override
	public String getUserName(int id) {
		String returnable = this.signupDao.getUserName(id);
		return returnable;
	}

	@Override
	public String getUserEmail(int id) {
		String returnable = this.signupDao.getUserEmail(id);
		return returnable;
	}

	@Override
	public java.util.List<appUser> List() {
		return signupDao.list();
	}

	@Override
	public appUser getUserWithName(String name) {
		return this.signupDao.getUserWithName(name);
	}

	@Override
	public void addUserDetails(UserDetails details) {
		signupDao.addUserDetails(details);
	}

	@Override
	public void updateUserDetails(UserDetails details) {
		signupDao.updateUserDetails(details);
	}

	@Override
	public UserDetails retriveUserDetailsWithId(int detailId) {
		return signupDao.retriveUserDetailsWithId(detailId);
	}

	@Override
	public void updateRole(Role r) {
		signupDao.updateRole(r);
	}

	@Override
	public Role getRoleForId(int id) {
		return signupDao.getRoleForId(id);
	}

	@Override
	public void deleteUserWithId(int id) {
		signupDao.deleteUserWithId(id);
	}

	@Override
	public java.util.List<appUser> getUserListForKey(String key) {
		if(!key.equals(" ")){
			key = key.trim();
			String[] keyList = key.split(" ");
			List<appUser> modelList = signupDao.getUserListForKey("where username like \'%%\'");
			modelList.clear();
			String appentString = "";
			System.out.println(">>>>>"+appentString);
			for(int i = 0; i < keyList.length; i++){
				System.out.println(">>>>>>>>"+appentString);
				if(i == 0){
					appentString += "where username like \'%"+keyList[i].trim()+"%\'";
				}else{
					if(!keyList[i].trim().equals(""))
						appentString += "or username like \'%"+keyList[i].trim()+"%\'";
				}
			}
			System.out.println(">>>"+appentString);
			modelList.addAll(signupDao.getUserListForKey(appentString));
			appentString = "";
			return modelList;
		}else
			return signupDao.getUserListForKey("where username like \'% %\'");
	}
	
	
	
}
