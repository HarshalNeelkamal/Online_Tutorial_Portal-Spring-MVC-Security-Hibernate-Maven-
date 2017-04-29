package com.neu.getyourcode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.neu.getyourcode.domain.Role;
import com.neu.getyourcode.domain.UserDetails;
import com.neu.getyourcode.domain.appUser;
import com.neu.getyourcode.service.AdminServices;
import com.neu.getyourcode.service.ExampleAndTutorialService;
import com.neu.getyourcode.service.userSignupService;

@Controller
@RequestMapping("/admin")
public class AdminsController {

	@Autowired
	AdminServices adminService;
	@Autowired
	private userSignupService service;
	@Autowired
	ExampleAndTutorialService tutService;
	
	@RequestMapping("/subAdmins")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String showSubAdmins(ModelMap model) {
		List<appUser> userList = adminService.getRoleForId(2).getUsers();
		model.addAttribute("subAdminsList",userList);
		return "AdminsView";
	}
	
	@RequestMapping("/subAdminForm")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String subAdminForm(ModelMap model) {
		model.addAttribute("appUser",new appUser());
		return "subAdminForm";
	}
	
	@RequestMapping("/deleteSubAdm")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String deleteSubAdm(@RequestParam("sub_adm_id") int id, ModelMap model) {
		
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    String username = user.getUsername();
	    if(username != null){
	    	service.deleteUserWithId(id);
		 }
		
		return "redirect:subAdmins";
	}
	
	@RequestMapping(value="/addSubAdmin", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String addUser(@Valid @ModelAttribute("appUser") appUser u,BindingResult result , ModelMap model){
		
		if(result.hasErrors()){
			return "subAdminForm";
		}
			List<Role> roleList = new ArrayList<Role>();
			Role role = service.getRoleForId(3);//3 stands for user
			roleList.add(role);
			roleList.add(service.getRoleForId(2));//2 stands for sub-admin
			List<appUser> userList = role.getUsers();
			userList.add(u);
			u.setRoles(roleList);
			role.setUsers(userList);
			service.updateRole(role);
 			String message = service.addUser(u);
			if(message == null){
				
			}else{
				model.addAttribute("message",message);
				return "subAdminForm";
			}
		return "redirect:subAdmins";
	}
	
}
