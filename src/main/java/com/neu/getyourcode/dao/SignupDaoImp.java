package com.neu.getyourcode.dao;

import java.util.List;

import javax.jws.soap.SOAPBinding.Use;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.loader.custom.Return;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.neu.getyourcode.domain.Role;
import com.neu.getyourcode.domain.TutorialModel;
import com.neu.getyourcode.domain.UserDetails;
import com.neu.getyourcode.domain.appUser;

@Repository
@Transactional
public class SignupDaoImp implements SignupDao{
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void addUser(appUser user) {
		String hashed = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
		user.setPassword(hashed);
		Session session = sessionFactory.getCurrentSession();
		session.save(user);
	}

	@Override
	public String getUserName(int id) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from appUser where id = :id");
		query.setParameter("id", id);
		appUser user = (appUser)query.uniqueResult(); //(appUser)session.load(appUser.class,new Integer(id));
		System.out.println("id:>>>>"+user);
		if(user == null){
			return null;
		}else{
			return user.getUsername();
		}
	}

	@Override
	public String getUserEmail(int id) {
		Session session = sessionFactory.getCurrentSession();
		appUser user = (appUser)session.load(appUser.class,new Integer(id));
		return user.getEmailId();
	}

	@Override
	public boolean updateUser(appUser user) {
		boolean success = true;
		Session session = sessionFactory.getCurrentSession();
		session.update(user);
		return success;
	}

	@Override
	public List<appUser> list() {	
		return sessionFactory.getCurrentSession().createQuery("from appUser").list();
	}

	@Override
	@Transactional
	public appUser getUserWithName(String name) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(appUser.class);
		criteria.add(Restrictions.eq("username", name));
		appUser user = (appUser)criteria.uniqueResult();
		return user;
	}

	@Override
	public appUser getUserWithEmail(String email) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(appUser.class);
		criteria.add(Restrictions.eq("emailId", email));
		appUser user = (appUser)criteria.uniqueResult();
		return user;
	}

	@Override
	public void addUserDetails(UserDetails details) {
		Session session = sessionFactory.getCurrentSession();
		session.save(details);
	}

	@Override
	public void updateUserDetails(UserDetails details) {
		Session session = sessionFactory.getCurrentSession();
		session.update(details);		
	}

	@Override
	public UserDetails retriveUserDetailsWithId(int detailId) {
		Session session = sessionFactory.getCurrentSession();
		UserDetails detail = (UserDetails)session.load(UserDetails.class,new Integer(detailId));
		return detail;
	}

	@Override
	public void updateRole(Role r) {
		Session session = sessionFactory.getCurrentSession();
		session.update(r);
	}

	@Override
	public Role getRoleForId(int id) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Role where id = :roleID");
		query.setParameter("roleID", id);
		return (Role)query.uniqueResult();
	}

	@Override
	public void deleteUserWithId(int id) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(getUserWithName(getUserName(id)).getDetails());
		session.delete(getUserWithName(getUserName(id)));
	}
	
	@Override
	public List<appUser> getUserListForKey(String key) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from appUser "+key);
		return (List<appUser>)query.list();
	}

}
