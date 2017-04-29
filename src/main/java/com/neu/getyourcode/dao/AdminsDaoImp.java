package com.neu.getyourcode.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.neu.getyourcode.domain.Role;

@Transactional
@Repository
public class AdminsDaoImp implements AdminsDao{

	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	public Role getRoleForId(int id) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Role where id = :id");
		query.setParameter("id", id);
		return (Role)query.uniqueResult();
	}

	
	
	
}
