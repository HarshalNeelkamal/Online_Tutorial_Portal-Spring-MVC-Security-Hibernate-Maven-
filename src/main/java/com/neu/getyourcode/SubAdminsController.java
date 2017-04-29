package com.neu.getyourcode;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.neu.getyourcode.domain.ExampleModel;
import com.neu.getyourcode.domain.Reports;
import com.neu.getyourcode.domain.Role;
import com.neu.getyourcode.domain.appUser;
import com.neu.getyourcode.service.AdminServices;
import com.neu.getyourcode.service.ExampleAndTutorialService;
import com.neu.getyourcode.service.userSignupService;


@Controller
@RequestMapping("/sub_Admin")
public class SubAdminsController {

	@Autowired
	AdminServices adminService;
	@Autowired
	private userSignupService service;
	@Autowired
	ExampleAndTutorialService tutService;
	
	@RequestMapping("/deleteExample")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String deleteExample(HttpServletRequest request){
		
		int id = Integer.parseInt(request.getParameter("example_id"));
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    String username = user.getUsername();
	    appUser tempUser = service.getUserWithName(username);
	    List<Role> tempList = tempUser.getRoles();
	    for(int i = 0; i<tempList.size();i++){
	    	Role r = tempList.get(i);
	    	if(r.getRoleName().equals("ROLE_SUB_ADMIN")){
	    		tutService.deleatExampleWithId(id);
	    	}
	    }		
		return"redirect:../userHome";
	}
	
	@RequestMapping("/deleteTutorial")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String deleteTutorial(HttpServletRequest request){
		System.out.println(">>>>>>deleting Tut");
		int id = Integer.parseInt(request.getParameter("tut_id"));
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    String username = user.getUsername();
	    appUser tempUser = service.getUserWithName(username);
	    List<Role> tempList = tempUser.getRoles();
	    for(int i = 0; i<tempList.size();i++){
	    	Role r = tempList.get(i);
	    	if(r.getRoleName().equals("ROLE_SUB_ADMIN")){
	    		tutService.deleatTutorialWithId(id);
	    	}
	    }		
		return"redirect:../userHome";
	}
	
	@RequestMapping("/viewInbox")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String viewInbox(ModelMap model){
		model.addAttribute(new Reports());
		List<Reports> replist = tutService.getReports();
		ArrayList<Reports> newList = new ArrayList<Reports>();
		for(int i = 0; i<replist.size(); i++){
			newList.add(replist.get(i));
		}
		model.addAttribute("reports", newList);
		return "ReportsPage";
	}
	
	@RequestMapping("/resolveReport")
	public String resolveReport(@RequestParam("report_id") int id) {
		tutService.deleteReportWithId(id);
		return "redirect:viewInbox";
	}
	
	@RequestMapping("/viewUsers")
	public String viewUsers() {
		return "UsersList";
	}
	
	@RequestMapping("/getUsers")
	public @ResponseBody List<appUser> viewUsers(@RequestParam("key") String key) {
		List<appUser> exList = new ArrayList<appUser>();
		List<appUser> tempList = new ArrayList<appUser>();

		exList = service.getUserListForKey(key);		
		for(int i=0;i<exList.size();i++){
			if(exList.get(i).getRoles().size() == 1){
				tempList.add(exList.get(i));
			}
		}
		return tempList;
	}
	
	@RequestMapping("/toggleStatus")
	public String toggleUserStatus(@RequestParam("id") int id) {
		String username = "";
		username= service.getUserName(id);
		System.out.println("***********************here");
		if(username != ""){
			System.out.println("***********************found user name");
			appUser userObj = service.getUserWithName(username);
			if(userObj != null){
				System.out.println("***********************found user");
				if(userObj.isEnabled()){
					System.out.println(">>>>>>>>>>blocked");
					userObj.setEnabled(false);
				}
				else{
					System.out.println(">>>>>>>>>>>>>>un-blocked");
					userObj.setEnabled(true);
				}
				service.updateUser(userObj);
			}
		}
		return "redirect:viewUsers";
	}
	
	@RequestMapping("/deleteUser")
	@PreAuthorize("hasRole('ROLE_SUB_ADMIN')")
	public String deleteSubAdm(@RequestParam("user_id") int id, ModelMap model) {
		
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    String username = user.getUsername();
	    if(username != null){
	    	service.deleteUserWithId(id);
		 }
		
		return "redirect:viewUsers";
	}
	
}
