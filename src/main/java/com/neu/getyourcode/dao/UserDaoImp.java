package com.neu.getyourcode.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.neu.getyourcode.domain.User;
import com.neu.getyourcode.domain.appUser;

@Repository
@Transactional
public class UserDaoImp implements UserDao{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void addUser(User user) {
		Session session = sessionFactory.getCurrentSession();
		session.save(user);
	}

	@Override
	public void updateUser(User user) {
		Session session = sessionFactory.getCurrentSession();
		session.update(user);
	}

	@Override
	public User getUser(int id) {
		Session session = sessionFactory.getCurrentSession();
		return (User)session.get(User.class, id);
	}

	@Override
	public User getUserByName(String name) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(User.class);
		criteria.add(Restrictions.eq("username", name));
		User user = (User)criteria.uniqueResult();
		return user;
	}

	@Override
	public void deleteUser(int id) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(getUser(id));
	}

	@Override
	public List<User> listUser() {
		return sessionFactory.getCurrentSession().createQuery("from user").list();
	}

}
