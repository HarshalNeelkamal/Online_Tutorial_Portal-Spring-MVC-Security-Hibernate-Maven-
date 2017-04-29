package com.neu.getyourcode.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.neu.getyourcode.domain.ExampleModel;
import com.neu.getyourcode.domain.Reports;
import com.neu.getyourcode.domain.TopicModel;
import com.neu.getyourcode.domain.TutorialModel;

@Repository
@Transactional
public class ExampleAndTutorialDAOImp implements ExampleAndTutorialDAO {

	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	public void addExample(ExampleModel example) {
		Session session = sessionFactory.getCurrentSession();
		session.save(example);
	}

	@Override
	public void deleatExampleWithId(int id) {
		Session session = sessionFactory.getCurrentSession();
		ExampleModel temp = getExampleWithId(id);
		session.delete(temp);
	}

	@Override
	public void updateExample(ExampleModel example) {
		Session session = sessionFactory.getCurrentSession();
		session.update(example);
	}

	@Override
	public List<ExampleModel> listExamplesForUserId(int id) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from ExampleModel where user_id = :id");//session.createSQLQuery("Select * from examples example where user_id = :id");
		query.setParameter("id", id);
		List<ExampleModel> respo = (List<ExampleModel>)query.list();
		return respo;
	}
	
	@Override
	public ExampleModel getExampleWithId(int id) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from ExampleModel where example_id = :id");//session.createSQLQuery("Select * from examples example where user_id = :id");
		query.setParameter("id", id);
		return (ExampleModel)query.uniqueResult();
	}

	@Override
	public void addTutorial(TutorialModel tut) {
		Session session = sessionFactory.getCurrentSession();
		session.save(tut);		
	}

	@Override
	public void deleatTutorialWithId(int id) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(getTutorialWithId(id));
	}

	@Override
	public void updateTutorial(TutorialModel tut) {
		Session session = sessionFactory.getCurrentSession();
		session.update(tut);		
	}

	@Override
	public List<TutorialModel> listTutorialsForUserId(int id) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from TutorialModel where user_id = :id");//session.createSQLQuery("Select * from examples example where user_id = :id");
		query.setParameter("id", id);
		List<TutorialModel> respo = (List<TutorialModel>)query.list();
		return respo;
	}

	@Override
	public TutorialModel getTutorialWithId(int id) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from TutorialModel where tut_id = :id");//session.createSQLQuery("Select * from examples example where user_id = :id");
		query.setParameter("id", id);
		return (TutorialModel)query.uniqueResult();
	}

	@Override
	public void updateTopic(TopicModel topic) {
		Session session = sessionFactory.getCurrentSession();
		session.update(topic);
	}

	@Override
	public List<TutorialModel> getTutorialListForKey(String key) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from TutorialModel "+key);
		return (List<TutorialModel>)query.list();
	}

	@Override
	public List<ExampleModel> getExampleListForKey(String key) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from ExampleModel "+key);
		return (List<ExampleModel>)query.list();
	}

	@Override
	public void fileReport(Reports r) {
		Session session = sessionFactory.getCurrentSession();
		session.save(r);
	}

	@Override
	public List<Reports> getReports() {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Reports").setFirstResult(0).setMaxResults(50);
		return query.list();
	}
	
	@Override
	public void deleteReportWithId(int id){
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Reports where report_id = \'"+id+"\'");
		session.delete(query.uniqueResult());
	}

}
