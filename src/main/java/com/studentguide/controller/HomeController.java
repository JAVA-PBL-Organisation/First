package com.studentguide.controller;

import java.security.Principal;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.studentguide.dao.UserRepository;
import com.studentguide.entities.User;
import com.studentguide.helper.Message;

@Controller

// Home page handler
public class HomeController {

	@Autowired
	private BCryptPasswordEncoder PasswordEncoder;

	@Autowired
	private UserRepository userRepository;

	@GetMapping("/")
	public String home(Model m, Principal principal) {
		boolean isLoggedIn;
		if (principal == null) {
			isLoggedIn = false;
		} else {
			isLoggedIn = true;
		}
		m.addAttribute("isLoggedIn", isLoggedIn);
		m.addAttribute("title", "Home-Student guide");
		return "home";
	}

	// About page handler
	@GetMapping("/about")
	public String about(Model m) {

		m.addAttribute("title", "about-Student guide");
		return "about";
	}

	// Signup page handler
	@GetMapping("/signup")
	public String signup(Model m) {

		m.addAttribute("title", "Register-Student guide");
		m.addAttribute("user", new User());
		return "signup";
	}

	// this is for registering user
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult result, Model model,
			Principal principal,
			// @RequestParam(value = "agreement", defaultValue = "false") boolean agreement,
			HttpSession session) {

		try {
			// if(!agreement) {
			// System.out.println("You have not agreed terms and conditions");
			// throw new Exception("You have not agreed terms and conditions");
			// }
			if (result.hasErrors()) {
				System.out.println("ERROR" + result.toString());
				model.addAttribute("user", user);
				return "signup";
			}
			user.setEnabled(true);
			user.setRole("ROLE_USER");
			user.setImageUrl("default.png");
			user.setPassword(PasswordEncoder.encode(user.getPassword()));
			user.setInterests("-");
			user.setSkills("-");
			user.setProfession("-");
			user.setContact("-");

			// System.out.println("Agreement:"+agreement);

			System.out.println("user" + user);
			User res = this.userRepository.save(user);
			model.addAttribute("user", user);
			session.setAttribute("message", new Message("Successfully registerd!", "alert-success"));
			return "user/dashboard";
		} catch (Exception e) {

			e.printStackTrace();
			model.addAttribute("user", user);
			session.setAttribute("message", new Message("Something went wrong!! " + e.getMessage(), "alert-danger"));
			return "signup";
		}
	}

	// Login Page
	@GetMapping("/login")
	public String login(Model m) {
		m.addAttribute("title", "Login-Student guide");
		return "login";
	}
}
