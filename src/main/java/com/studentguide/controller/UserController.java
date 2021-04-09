package com.studentguide.controller;

import java.security.Principal;

import com.studentguide.dao.UserRepository;
import com.studentguide.entities.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/dashboard")
    public String dashboard(Model m, Principal principal) {
        String username = principal.getName();
        User user = userRepository.getUserByUsername(username);
        m.addAttribute("user", user);
        m.addAttribute("title", "Dashboard");
        return "user/dashboard";
    }

}