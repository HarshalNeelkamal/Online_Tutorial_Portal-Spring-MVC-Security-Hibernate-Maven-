package com.neu.getyourcode;

import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.neu.getyourcode.domain.Role;
import com.neu.getyourcode.domain.TutorialModel;
import com.neu.getyourcode.domain.UserDetails;
import com.neu.getyourcode.domain.appUser;
import com.neu.getyourcode.service.userSignupService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private int activeId = 0;
	
	@Autowired
	private userSignupService service;
//	@Autowired
//	AuthenticationSuccessHandler successHandler;
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
				
		return "home";
	}
	
	@RequestMapping(value="/UserSignup")
	public String signup(ModelMap model){
		
		model.addAttribute("appUser",new appUser());
		
		return "signup";
	}
	
	@RequestMapping(value="/login")
	public String login(ModelMap model){
		return "home";
	}
	@RequestMapping(value="/addUser", method = RequestMethod.POST)
	public String addUser(@Valid @ModelAttribute("appUser") appUser u,BindingResult result , ModelMap model){
		
		if(result.hasErrors()){
			return "signup";
		}
			model.addAttribute("j_username", u.getUsername());
			model.addAttribute("j_password", u.getPassword());
			List<Role> roleList = new ArrayList<Role>();
			Role role = service.getRoleForId(3);//3 stands for user
			roleList.add(role);
			List<appUser> userList = role.getUsers();
			userList.add(u);
			u.setRoles(roleList);
			role.setUsers(userList);
			service.updateRole(role);
			List<TutorialModel> tutorialList = new ArrayList<TutorialModel>();
 			u.setTutorials(tutorialList);
			String message = service.addUser(u);
			if(message == null){
				activeId = u.getId();
				appUser tempObj = service.getUserWithName(service.getUserName(activeId));
				GrantedAuthority authority = new GrantedAuthority() {
					@Override
					public String getAuthority() {
						return "ROLE_USER";
					}
				};
				model.addAttribute("user_name",tempObj.getUsername());
				Collection<GrantedAuthority> autList = new ArrayList<GrantedAuthority>();
				autList.add(authority);
				Authentication auth = new UsernamePasswordAuthenticationToken(tempObj.getUsername(), tempObj.getPassword(), autList);
				SecurityContextHolder.getContext().setAuthentication(auth);
				
			}else{
				model.addAttribute("message",message);
				return "signup";
			}
		return "home";
	}
	
	@RequestMapping(value="/userHome")
	public String loginAttempt(ModelMap model){
		System.out.println("=================================see its working===================================");
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    String username = user.getUsername();
	    if(username != null && username != ""){
	    	appUser tempUser = service.getUserWithName(username);
	    	model.addAttribute("activeUser",username);
	    	if(tempUser.getDetails() != null){
	    		model.addAttribute("aboutUser",tempUser.getDetails().getAbout());
	    		model.addAttribute("userEmail",tempUser.getDetails().getEmail());
	    		model.addAttribute("UserLinkedIn",tempUser.getDetails().getLinkedIn());
	    		model.addAttribute("UserOther",tempUser.getDetails().getOther());
	    		model.addAttribute("UserDetails", tempUser.getDetails());
	    	}else{
	    		model.addAttribute("UserDetails",new UserDetails());
	    	}
		 }
		return "userHome";
	}
	
	@RequestMapping(value="/customLoginForm")
	public String loginViewRedirect(ModelMap model, @RequestParam(value="error", required=false) String error, @RequestParam(value="logout", required=false) String logout){
		 
		if(error != null){
			 model.addAttribute("error","Inavlid Credentials");
		 }else if(logout != null){
			 model.addAttribute("logout","User successfully logged out");
		 }
		
		return "login";
	}
	
	@RequestMapping(value="/saveUserDetails")
	public String saveUserDetails(@Valid @ModelAttribute("UserDetails") UserDetails details,BindingResult results){
		if(results.hasErrors()){
			return "userHome";
		}
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    String username = user.getUsername();
	    appUser tempUser = service.getUserWithName(username);
	    System.out.println(">>>>"+tempUser.getDetails().getDetail_id());
	    UserDetails tempDetails = tempUser.getDetails();
	    tempDetails.setAbout(details.getAbout());
	    tempDetails.setEmail(details.getEmail());
	    tempDetails.setLinkedIn(details.getLinkedIn());
	    tempDetails.setOther(details.getOther());
	    service.updateUserDetails(tempDetails);
		return "redirect:userHome";
	}
}
