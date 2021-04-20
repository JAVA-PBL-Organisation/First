package com.studentguide.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;

import com.studentguide.Services.ClubService;
import com.studentguide.Services.HostelService;
import com.studentguide.Services.MessService;
import com.studentguide.Services.OutletService;
import com.studentguide.dao.UserRepository;
import com.studentguide.entities.Club;
import com.studentguide.entities.Hostel;
import com.studentguide.entities.Mess;
import com.studentguide.entities.Outlet;
import com.studentguide.entities.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HostelService hostelService;
    @Autowired
    private MessService messService;
    @Autowired
    private OutletService outletService;
    @Autowired
    private ClubService clubService;

    @ModelAttribute
    public void commonData(Model model, Principal principal) {
        String username = principal.getName();
        User user = userRepository.getUserByUsername(username);
        model.addAttribute("user", user);
    }

    // DashBoard Handler

    @GetMapping("/dashboard")
    public String dashboard(Model m) {
        m.addAttribute("title", "Dashboard");
        return "user/dashboard";
    }

    // Info Section Handlers

    @GetMapping("/hostels")
    public String hostels(Model m) {
        List<Hostel> hostels = hostelService.getAllHostels();
        m.addAttribute("items", hostels);
        m.addAttribute("title", "Hostels");
        m.addAttribute("type", "hostel");
        return "user/index";
    }

    @GetMapping("/hostel/{id}")
    public String showHostel(Model m, @PathVariable("id") int id) {
        Hostel hostel = hostelService.getHostelById(id);
        if (hostel == null) {
            m.addAttribute("message", "No hostel found");
            return "user/error";
        }
        m.addAttribute("item", hostel);
        m.addAttribute("title", hostel.getName());
        m.addAttribute("type", "hostel");
        return "user/show";
    }

    @GetMapping("/messes")
    public String messes(Model m) {
        List<Mess> messes = messService.getAllMesses();
        m.addAttribute("title", "Messes");
        m.addAttribute("items", messes);
        m.addAttribute("type", "mess");
        return "user/index";
    }

    @GetMapping("/mess/{id}")
    public String showMess(Model m, @PathVariable("id") int id) {
        Mess mess = messService.getMessById(id);
        if (mess == null) {
            m.addAttribute("message", "No hostel found");
            return "user/error";
        }
        m.addAttribute("item", mess);
        m.addAttribute("title", mess.getName());
        m.addAttribute("type", "mess");
        return "user/show";
    }

    @GetMapping("/outlets")
    public String outlets(Model m) {
        List<Outlet> outlets = outletService.getAllOutlets();
        m.addAttribute("title", "Outlets");
        m.addAttribute("items", outlets);
        m.addAttribute("type", "outlet");
        return "user/index";
    }

    @GetMapping("/outlet/{id}")
    public String showOutlet(Model m, @PathVariable("id") int id) {
        Outlet outlet = outletService.getOutletById(id);
        if (outlet == null) {
            m.addAttribute("message", "No hostel found");
            return "user/error";
        }
        m.addAttribute("item", outlet);
        m.addAttribute("title", outlet.getName());
        m.addAttribute("type", "outlet");
        return "user/show";
    }

    @GetMapping("/clubs")
    public String clubs(Model m) {
        List<Club> clubs = clubService.getAllClubs();
        m.addAttribute("title", "Clubs");
        m.addAttribute("items", clubs);
        m.addAttribute("type", "club");
        return "user/index";
    }

    @GetMapping("/club/{id}")
    public String showClub(Model m, @PathVariable("id") int id) {
        Club club = clubService.getClubById(id);
        if (club == null) {
            m.addAttribute("message", "No hostel found");
            return "user/error";
        }
        m.addAttribute("item", club);
        m.addAttribute("title", club.getName());
        m.addAttribute("type", "club");

        return "user/show";
    }

    // User Profile Handlers

    @GetMapping("/profile")
    public String profile(Model m) {
        m.addAttribute("title", "Your Profile");
        return "user/profile";
    }

    @GetMapping("/profile/edit")
    public String editProfile(Model m) {
        m.addAttribute("title", "Edit Profile");
        m.addAttribute("message", "NULL");
        return "user/editProfile";
    }

    @PostMapping("/profile")
    public String saveProfile(@ModelAttribute("user") User user, @RequestParam("profilePicture") MultipartFile file,
            Model m) {
        String msg = "NULL";
        try {

            if (!file.isEmpty()) {
                user.setImageUrl(file.getOriginalFilename());
                File file1 = new ClassPathResource("static/image").getFile();
                Path path = Paths.get(file1.getAbsolutePath() + File.separator + file.getOriginalFilename());
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            }

            User res = userRepository.save(user);
            m.addAttribute("title", "Your Profile");
            m.addAttribute("user", user);
            return "user/profile";
        } catch (Exception e) {
            e.printStackTrace();
            msg = "Something went wrong! Try again.";
            m.addAttribute("message", msg);
            return "user/editprofile";
        }

    }
    
}