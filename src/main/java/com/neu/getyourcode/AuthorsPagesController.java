package com.neu.getyourcode;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.neu.getyourcode.domain.ExampleModel;
import com.neu.getyourcode.domain.Reports;
import com.neu.getyourcode.domain.TutorialModel;
import com.neu.getyourcode.domain.appUser;
import com.neu.getyourcode.service.ExampleAndTutorialService;
import com.neu.getyourcode.service.userSignupService;

@Controller
public class AuthorsPagesController {
	
	@Autowired
	private userSignupService service;
	@Autowired
	private ExampleAndTutorialService tutService;
	int tut_id = 0;
	int ex_id = 0;
	int authors_user_id = 0;
	
	@RequestMapping("/authorsTut")
	public String TakeToTutorial(ModelMap model, HttpServletRequest request){
		
		if(request.getParameter("tut_id") != null){
		  tut_id = Integer.parseInt(request.getParameter("tut_id"));
		}
		
		TutorialModel tempTut = new TutorialModel();
		
		if(tut_id != 0){
			tempTut = tutService.getTutorialWithId(tut_id);
		}
		model.addAttribute("tutorial", tempTut);
		model.addAttribute("Reports", new Reports());

		return "AuthsTutorial";
	}
	
	@RequestMapping("/authorsEx")
	public String TakeToExample(ModelMap model, HttpServletRequest request){
		
		if(request.getParameter("ex_id") != null){
			ex_id = Integer.parseInt(request.getParameter("ex_id"));
		}
		ExampleModel tempEx = new ExampleModel();
		
		if(ex_id != 0){
			tempEx = tutService.getExampleWithId(ex_id);
		}
		model.addAttribute("example", tempEx);
		model.addAttribute("Reports", new Reports());
		
		return "AuthsExample";
	}
	
	@RequestMapping("/authorsProfile")
	public String TakeToAuthorsProfile(ModelMap model, HttpServletRequest request){
		
		if(request.getParameter("authors_user_id") != null){
			authors_user_id = Integer.parseInt(request.getParameter("authors_user_id"));
		}
		
		appUser tempUser = new appUser();
		List <TutorialModel> temptut = new ArrayList<TutorialModel>();
		List <ExampleModel> tempEx = new ArrayList<ExampleModel>();
		
		if(authors_user_id != 0){
			 String userName = service.getUserName(authors_user_id);
			 if(userName != null){
				 tempUser = service.getUserWithName(userName);
				 temptut = tutService.listTutorialsForUserId(authors_user_id);
				 tempEx = tutService.listExamplesForUserId(authors_user_id);
			 }else{
					model.addAttribute("error", "Author is no more an existing user");
			 }
		}
		
		model.addAttribute("author", tempUser);
		model.addAttribute("tutorial", temptut);
		model.addAttribute("example", tempEx);

		return "AuthsProfile";
	}
	
	@RequestMapping("/ReportAuthor")
	public String reportAuthor(@ModelAttribute("Reports") Reports r, ModelMap model){
//		String type = (String)request.getParameter("type");
//		String title = (String)request.getParameter("title");
//		String message = (String)request.getParameter("message");
//		int id = Integer.parseInt(request.getParameter("id"));
//		int user_id = Integer.parseInt(request.getParameter("user_id"));
//		int author_id = Integer.parseInt(request.getParameter("author_id"));
//		
		tutService.fileReport(r);
		if(r.getType().equalsIgnoreCase("example")){
			return "redirect:authorsEx";
		}else{
			return "redirect:authorsTut";
		}
	}
}
