package com.neu.getyourcode;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.hibernate.exception.spi.TemplatedViolatedConstraintNameExtracter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.neu.getyourcode.domain.ExampleModel;
import com.neu.getyourcode.domain.TopicModel;
import com.neu.getyourcode.domain.TutorialModel;
import com.neu.getyourcode.domain.appUser;
import com.neu.getyourcode.service.ExampleAndTutorialService;
import com.neu.getyourcode.service.userSignupService;

@Controller
public class TutorialAndExampleController {
	
private int activeId = 0;
	
	@Autowired
	private userSignupService service;
	@Autowired
	private ExampleAndTutorialService tutService;
	private int presentTutId = 0;

	
	@RequestMapping("/tutorialForm")
	public String tutorialForm(ModelMap model){
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		appUser userObj = service.getUserWithName((user.getUsername()));
		model.addAttribute("userId",userObj.getId());
		model.addAttribute("TutorialModel",new TutorialModel());
		return "TutorialFom";
	}
	
	@RequestMapping("/addTutorial")
	public String addTutorial(@Valid @ModelAttribute("TutorialModel") TutorialModel tutorial, BindingResult result, ModelMap model){
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		appUser userObj = service.getUserWithName((user.getUsername()));
		if(result.hasErrors())
			return "TutorialFom";
		else{
			List<TopicModel> topicList = new ArrayList<TopicModel>();
			tutorial.setTopics(topicList);
			userObj.getTutorials().add(tutorial);
			service.updateUser(userObj);
			tutorial.setApp_user(userObj);
			tutService.addTutorial(tutorial);
		}
		return "redirect:myTutorials";
	}
	
	@RequestMapping("/exampleForm")
	public String addExample(ModelMap model){
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		appUser userObj = service.getUserWithName((user.getUsername()));
		model.addAttribute("userId",userObj.getId());
		model.addAttribute("ExampleModel",new ExampleModel());
		return "ExampleForm";
	}
	
	@RequestMapping("/myTutorials")
	public String myTutorial(ModelMap model){
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		appUser userObj = service.getUserWithName((user.getUsername()));
		List<TutorialModel> listOfTutorials = tutService.listTutorialsForUserId(userObj.getId());
		model.addAttribute("tutorial_list",listOfTutorials);
		if(presentTutId != 0){
			model.addAttribute("present_tutorial_Id",presentTutId);
			presentTutId = 0;
		}else if(listOfTutorials.size() > 0)
			model.addAttribute("present_tutorial_Id",listOfTutorials.get(0).getTut_id());
		else{
			model.addAttribute("present_tutorial_Id",-1);
		}
		return "MyTutorials";
	}
	
	@RequestMapping("/myExamples")
	public String myExamples(ModelMap model){
		
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		appUser userObj = service.getUserWithName(user.getUsername());
		int id = userObj.getId();
		List<ExampleModel> listOfExamples = tutService.listExamplesForUserId(id);
		model.addAttribute("example_list",listOfExamples);
		return "MyExamples";
	}
	
	@RequestMapping("/addExample")
	public String addExample(@Valid @ModelAttribute("ExampleModel") ExampleModel example, BindingResult result, ModelMap model){
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		appUser userObj = service.getUserWithName(user.getUsername());
		if(result.hasErrors())
			return "ExampleForm";
		else
			example.setUser(userObj);
			userObj.getExamples().add(example);
			tutService.addExample(example);
			service.updateUser(userObj);
		return "redirect:myExamples";
	}
	
	@RequestMapping("/topicLoad")
	private @ResponseBody List<TopicModel> topicLoad(@RequestParam("tut_id") int id) {
		TutorialModel tutmod = tutService.getTutorialWithId(id);
		return tutmod.getTopics();
	}
	
	@RequestMapping("/addTopic")
	private String addTopic(@Valid @ModelAttribute("TopicModel") TopicModel topic, BindingResult results, ModelMap model,@RequestParam("tut_id") int tut_id) {
		if(results.hasErrors()){
			model.addAttribute("tut_id",tut_id);
			return "topicForm";
		}
		TutorialModel tutorial = tutService.getTutorialWithId(tut_id);
		topic.setTopic_content(convertStringTOsuitableForm(topic.getTopic_content()));
		tutorial.addNewTopic(topic);
		tutService.updateTutorial(tutorial);
		presentTutId = tut_id;
		return "redirect:myTutorials";
	}
	@RequestMapping("/topicForm")
	private String topicForm(@RequestParam("tut_id") int tut_id,ModelMap model) {
		model.addAttribute("tut_id",tut_id);
		model.addAttribute("TopicModel", new TopicModel());
		return "topicForm";
	}

	@RequestMapping("/deleteExample")
	private String deleteExample(@RequestParam("id") int id, ModelMap model){
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		appUser userObj = service.getUserWithName((user.getUsername()));
		int presentUserId = userObj.getId();
		int examplesUserId = 0;
		if(tutService.getExampleWithId(id) != null){
			examplesUserId = tutService.getExampleWithId(id).getUser_id();
		}
		if(presentUserId == examplesUserId){
			tutService.deleatExampleWithId(id);
		}else{
			System.out.println("Un-authorised Attempt to delete account by user with id:"+examplesUserId+"\nThe account belongs to user with Id"+presentUserId);
		}
		return "redirect:myExamples";
	}
	
	@RequestMapping("/deleteTopic")
	private @ResponseBody List<TopicModel> deleteTopic(@RequestParam("top_id") int top_id, @RequestParam("tutId") int tut_id, ModelMap model){
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		appUser userObj = service.getUserWithName((user.getUsername()));
		int presentUserId = userObj.getId();
		int examplesUserId = 0;
		if(tutService.getTutorialWithId(tut_id) != null){
			examplesUserId = tutService.getTutorialWithId(tut_id).getUser_id();
		}
		if(presentUserId == examplesUserId){
			TutorialModel tempTut = tutService.getTutorialWithId(tut_id);
			List<TopicModel> tempList = tempTut.getTopics();
			int removable_index = -1;
			int i = 0;
			for(TopicModel t : tempList){
				if(t.getTopic_id() == top_id){
					removable_index = i;
				}
				i++;
			}
			if(removable_index != -1){
				tempTut.getTopics().remove(removable_index);
				tutService.updateTutorial(tempTut);
			}
		}else{
			System.out.println("Un-authorised Attempt to delete Tutorial Topic of user with id:"+examplesUserId+"\nThe account belongs to user with Id"+presentUserId);
		}
		return tutService.getTutorialWithId(tut_id).getTopics();
	}
	
	@RequestMapping("/editExampleAttempt")
	public String editExample(@RequestParam("id") int id,@RequestParam("title") String title,@RequestParam("content") String content , ModelMap model){
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		appUser userObj = service.getUserWithName((user.getUsername()));
		int presentUserId = userObj.getId();
		int examplesUserId = 0;
		ExampleModel tempEx =  tutService.getExampleWithId(id);
		
		if(tempEx != null){
			examplesUserId = tempEx.getUser_id();
			if(examplesUserId == presentUserId){
				tempEx.setTitle(title);
				tempEx.setContent(convertStringTOsuitableForm(content));
				tutService.updateExample(tempEx);
			}
			else 
				System.out.println("Un-authorised Attempt to delete Example of user with id:"+examplesUserId+"\nThe account belongs to user with Id"+presentUserId);
		}
		return"redirect:myExamples";
	}
	
	@RequestMapping("/editTopicAttempt")
	public String editTopic(@RequestParam("topicId") int top_id, @RequestParam("tutorialId") int id,@RequestParam("title") String title,@RequestParam("content") String content , ModelMap model){
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		appUser userObj = service.getUserWithName((user.getUsername()));
		int presentUserId = userObj.getId();
		int examplesUserId = 0;
		if(tutService.getTutorialWithId(id) != null){
			List<TopicModel> tempTopList =  tutService.getTutorialWithId(id).getTopics();
			int edit_index = -1;
			int i = 0;
			for(TopicModel t : tempTopList){
				if(t.getTopic_id() == top_id){
					edit_index = i;
					break;
				}
				i++;
			}
			if(edit_index != -1){
				TopicModel temptop = tempTopList.get(edit_index);
				examplesUserId = tutService.getTutorialWithId(id).getUser_id();
				if(examplesUserId == presentUserId){
					temptop.setTopic_title(title);
					temptop.setTopic_content(convertStringTOsuitableForm(content));
					tutService.updateTopic(temptop);
				}
				else 
					System.out.println("Un-authorised Attempt to delete Topic of user with id:"+examplesUserId+"\nThe account belongs to user with Id"+presentUserId);
			}
		}else{
			System.out.println("Un-authorised Attempt to delete Topic");
		}
		
		return"redirect:myTutorials";
	}
	
	@RequestMapping("/editTopic")
	public String editTopicForm(@RequestParam("topicId") int top_id, @RequestParam("tutorialId") int id,ModelMap model){
		model.addAttribute("TopicModel",new TopicModel());
		model.addAttribute("topicId",top_id);
		model.addAttribute("tutorialId",id);
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		appUser userObj = service.getUserWithName((user.getUsername()));
		int presentUserId = userObj.getId();
		if(tutService.getTutorialWithId(id) != null){
			int examplesUserId = tutService.getTutorialWithId(id).getUser_id();
			if(presentUserId == examplesUserId)
			{
				List<TopicModel> tempTopList =  tutService.getTutorialWithId(id).getTopics();
				int edit_index = -1;
				int i = 0;
				for(TopicModel t : tempTopList){
					if(t.getTopic_id() == top_id){
						edit_index = i;
						break;
					}
					i++;
				}
				if(edit_index != -1){
					TopicModel tempTop = tempTopList.get(edit_index); 
					model.addAttribute("title",tempTop.getTopic_title());
					model.addAttribute("content",tempTop.getTopic_content());
				}else{
					model.addAttribute("Error","not authorised to edit this topic");
				}
			}
			else{
				model.addAttribute("Error","not authorised to edit this topic");
			}
		}
		else{
			model.addAttribute("Error","not authorised to edit this topic");
		}
		return "EditTopicForm";

	}
		
	@RequestMapping("/editExample")
	public String editExampleForm(@RequestParam("id") int id, ModelMap model){
		model.addAttribute("ExampleModel",new ExampleModel());
		model.addAttribute("id",id);
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		appUser userObj = service.getUserWithName((user.getUsername()));
		int presentUserId = userObj.getId();
		int examplesUserId = 0;
		ExampleModel tempEx =  tutService.getExampleWithId(id);
		
		if(tempEx != null){
			examplesUserId = tempEx.getUser_id();
			if(examplesUserId == presentUserId){
				model.addAttribute("title",tempEx.getTitle());
				model.addAttribute("content",tempEx.getContent());
			}
			else 
				model.addAttribute("Error","not authorised to edit this Example");
		}else
			model.addAttribute("Error","not authorised to edit this Example");
		return"EditExampleForm";
	}
	@RequestMapping("/deleteTutorial")
	public @ResponseBody List<TopicModel> deleteTutorial(@RequestParam("tutId") int id){
		tutService.deleatTutorialWithId(id);
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		appUser userObj = service.getUserWithName((user.getUsername()));
		List<TopicModel> tempTutList = new ArrayList<TopicModel>();
		
			//tempTutList = tutService.getTutorialWithId(next_id).getTopics();
			List<TutorialModel> listOfTutorials = tutService.listTutorialsForUserId(userObj.getId());
			if(listOfTutorials != null){
				if(listOfTutorials.size() > 0){
					tempTutList = listOfTutorials.get(0).getTopics();
				}
			}
		
		return tempTutList;
	}
	@RequestMapping("/searchResults")
	public @ResponseBody List<TutorialModel> searchResultFor(@RequestParam("key") String key){
		List<TutorialModel> tutList = new ArrayList<TutorialModel>();
		tutList = tutService.getTutorialListForKey(key);
		return tutList;
	}
	@RequestMapping("/searchExampleResults")
	public @ResponseBody List<ExampleModel> searchExampleResultFor(@RequestParam("key") String key){
		List<ExampleModel> exList = new ArrayList<ExampleModel>();
		exList = tutService.getExampleListForKey(key);
		for(ExampleModel e : exList){
			System.out.println(">>>>>>>>desc:"+e.getContent());
		}
		return exList;
	}
	
	private String convertStringTOsuitableForm(String input){
		String content = input.replaceAll("<", "&lt");
		content = content.replaceAll("<", "&gt");
		content = content.replaceAll("\'", "&apos");
		content = content.replaceAll("\"", "&quot");
		content = content.replaceAll("\n", "<br/>");
		content = content.replaceAll(" ", "&nbsp");
		return content;
	}
}
