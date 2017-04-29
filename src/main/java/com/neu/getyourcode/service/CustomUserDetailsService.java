package com.neu.getyourcode.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neu.getyourcode.dao.SignupDao;
import com.neu.getyourcode.dao.UserDao;
import com.neu.getyourcode.domain.Role;
import com.neu.getyourcode.domain.User;
import com.neu.getyourcode.domain.UserStatus;
import com.neu.getyourcode.domain.appUser;

@Service("UserDetailsService")
public class CustomUserDetailsService implements UserDetailsService{

	@Autowired
	private  SignupDao service;
	
	@Override
	@Transactional(readOnly=true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		appUser user = service.getUserWithName(username);
		
		if(user != null){
			String password = user.getPassword();
			System.out.println(">>>>"+user);
			GrantedAuthority authority = new GrantedAuthority() {
				
				@Override
				public String getAuthority() {
					// TODO Auto-generated method stub
					return "ROLE_USER";
				}
			};
			Set<GrantedAuthority> authoritySet = new HashSet<GrantedAuthority>();
			for(Role r : user.getRoles()){
				SimpleGrantedAuthority auth = new SimpleGrantedAuthority(r.getRoleName());
				authoritySet.add(auth);
			}
			boolean enabled = true;
			if(!user.isEnabled()){
				enabled = false;
			}
			UserDetails details = new org.springframework.security.core.userdetails.User(username, password, enabled, true, true, true, authoritySet);
			return details;
		}else{
			throw new UsernameNotFoundException("user with username "+username+" not found");
		}
	}

}
