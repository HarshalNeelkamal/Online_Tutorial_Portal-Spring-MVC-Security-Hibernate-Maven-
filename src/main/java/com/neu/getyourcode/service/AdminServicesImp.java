package com.neu.getyourcode.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neu.getyourcode.dao.AdminsDao;
import com.neu.getyourcode.dao.ExampleAndTutorialDAO;
import com.neu.getyourcode.domain.Role;

@Service
public class AdminServicesImp implements AdminServices{

	@Autowired
	AdminsDao dao;
	
	@Override
	public Role getRoleForId(int id) {
		return dao.getRoleForId(id);
	}

}
