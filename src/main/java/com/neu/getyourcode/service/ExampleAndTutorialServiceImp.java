package com.neu.getyourcode.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neu.getyourcode.dao.ExampleAndTutorialDAO;
import com.neu.getyourcode.domain.ExampleModel;
import com.neu.getyourcode.domain.Reports;
import com.neu.getyourcode.domain.TopicModel;
import com.neu.getyourcode.domain.TutorialModel;
@Service
public class ExampleAndTutorialServiceImp implements ExampleAndTutorialService{
	
	@Autowired
	ExampleAndTutorialDAO dao;
	
	@Override
	public void addExample(ExampleModel example) {
		
		String content = example.getContent().replaceAll("<", "&lt");
		content = content.replaceAll("<", "&gt");
		content = content.replaceAll("\'", "&apos");
		content = content.replaceAll("\"", "&quot");
		content = content.replaceAll("\n", "<br/>");
		content = content.replaceAll(" ", "&nbsp");
		example.setContent(content);
		
		dao.addExample(example);
	}

	@Override
	public void deleatExampleWithId(int id) {
		dao.deleatExampleWithId(id);
	}

	@Override
	public void updateExample(ExampleModel example) {
		dao.updateExample(example);
	}

	@Override
	public List<ExampleModel> listExamplesForUserId(int id) {
		return dao.listExamplesForUserId(id);
	}

	@Override
	public void addTutorial(TutorialModel tut) {
		String content = tut.getDescription().replaceAll("<", "&lt");
		content = content.replaceAll("<", "&gt");
		content = content.replaceAll("\'", "&apos");
		content = content.replaceAll("\"", "&quot");
		content = content.replaceAll("\n", "<br/>");
		content = content.replaceAll(" ", "&nbsp");
		tut.setDescription(content);
		
		dao.addTutorial(tut);
	}

	@Override
	public void deleatTutorialWithId(int id) {
		dao.deleatTutorialWithId(id);		
	}

	@Override
	public void updateTutorial(TutorialModel tut) {
		dao.updateTutorial(tut);		
	}

	@Override
	public List<TutorialModel> listTutorialsForUserId(int id) {
		return dao.listTutorialsForUserId(id);
	}

	@Override
	public ExampleModel getExampleWithId(int id) {
		return dao.getExampleWithId(id);
	}

	@Override
	public TutorialModel getTutorialWithId(int id) {
		return dao.getTutorialWithId(id);
	}

	@Override
	public void updateTopic(TopicModel topic) {
		dao.updateTopic(topic);
	}

//	public List<TutorialModel> getTutorialListForKey(String key) {
//		if(!key.equals(" ")){
//			String[] keyList = key.split(" ");
//			List<TutorialModel> modelList = dao.getTutorialListForKey("");
//			modelList.clear();
//			String appentString = "";
//			for(String k : keyList){
//				
//				appentString += appentString + "where tut_title like \'%"+k+"%\'";
//				modelList.addAll(dao.getTutorialListForKey(k));
//			}
//			return modelList;
//		}else
//			return dao.getTutorialListForKey(key);
//	}
	
	@Override
	public List<TutorialModel> getTutorialListForKey(String key) {
		if(!key.equals(" ")){
			key = key.trim();
			String[] keyList = key.split(" ");
			List<TutorialModel> modelList = dao.getTutorialListForKey("where tut_title like \'%%\'");
			modelList.clear();
			String appentString = "";
			System.out.println(">>>>>"+appentString);
			for(int i = 0; i < keyList.length; i++){
				System.out.println(">>>>>>>>"+appentString);
				if(i == 0){
					appentString += "where tut_title like \'%"+keyList[i].trim()+"%\'";
				}else{
					if(!keyList[i].trim().equals(""))
						appentString += "or tut_title like \'%"+keyList[i].trim()+"%\'";
				}
			}
			System.out.println(">>>"+appentString);
			modelList.addAll(dao.getTutorialListForKey(appentString));
			appentString = "";
			return modelList;
		}else
			return dao.getTutorialListForKey("where tut_title like \'%%\'");
	}

	@Override
	public List<ExampleModel> getExampleListForKey(String key) {

		if(!key.equals(" ")){
			key = key.trim();
			String[] keyList = key.split(" ");
			List<ExampleModel> modelList = dao.getExampleListForKey("where title like \'%%\'");
			modelList.clear();
			String appentString = "";
			System.out.println(">>>>>"+appentString);
			for(int i = 0; i < keyList.length; i++){
				System.out.println(">>>>>>>>"+appentString);
				if(i == 0){
					appentString += "where title like \'%"+keyList[i].trim()+"%\'";
				}else{
					if(!keyList[i].trim().equals(""))
						appentString += "or title like \'%"+keyList[i].trim()+"%\'";
				}
			}
			System.out.println(">>>"+appentString);
			modelList.addAll(dao.getExampleListForKey(appentString));
			appentString = "";
			return modelList;
		}else
			return dao.getExampleListForKey("where title like \'%%\'");
	}

	@Override
	public void fileReport(Reports r) {
		dao.fileReport(r);
	}

	@Override
	public List<Reports> getReports() {
		return dao.getReports();
	}

	@Override
	public void deleteReportWithId(int id) {
		dao.deleteReportWithId(id);
	}

}
