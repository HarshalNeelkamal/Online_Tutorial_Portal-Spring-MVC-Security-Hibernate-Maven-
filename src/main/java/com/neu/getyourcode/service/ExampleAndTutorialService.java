package com.neu.getyourcode.service;

import java.util.List;

import com.neu.getyourcode.domain.ExampleModel;
import com.neu.getyourcode.domain.Reports;
import com.neu.getyourcode.domain.TopicModel;
import com.neu.getyourcode.domain.TutorialModel;

public interface ExampleAndTutorialService {
	public void addExample(ExampleModel example);
	public void deleatExampleWithId(int id);
	public void updateExample(ExampleModel example);
	public ExampleModel getExampleWithId(int id);
	public List<ExampleModel> listExamplesForUserId(int id);
	public void addTutorial(TutorialModel tut);
	public void deleatTutorialWithId(int id);
	public void updateTutorial(TutorialModel tut);
	public List<TutorialModel> listTutorialsForUserId(int id);
	public TutorialModel getTutorialWithId(int id); 
	public void updateTopic(TopicModel topic);
	public List<TutorialModel> getTutorialListForKey(String key);
	public List<ExampleModel> getExampleListForKey(String key);
	public void fileReport(Reports r);
	public void deleteReportWithId(int id);
	public List<Reports> getReports();
}
