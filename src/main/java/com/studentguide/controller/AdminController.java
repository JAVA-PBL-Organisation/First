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
import com.studentguide.dao.HostelRepository;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HostelService hostelService;
    @Autowired
    private HostelRepository hostelRepository;
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


    @GetMapping("/index")
    public String adminIndex(Model m) {
        m.addAttribute("title", "Admin Panel");
        return "admin/index";
    }



    // Hostel controllers

    @GetMapping("/hostels")
    public String hostels(Model m) {
        List<Hostel> hostels = hostelService.getAllHostels();
        m.addAttribute("items", hostels);
        m.addAttribute("title", "Hostels");
        m.addAttribute("type", "hostel");
        return "admin/list";
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
        return "admin/edit";
    }

    @GetMapping("/hostel/new")
    public String newHostel(Hostel hostel, Model m) {
        m.addAttribute("type", "hostel");
        m.addAttribute("title", "Add new hostel");
        return "admin/new";
    }

    @PostMapping("/hostel/add")
    public String addHostel(Hostel hostel, Model m) {
        Hostel h = null;
        try {
            h = this.hostelService.addHostel(hostel);
            List<Hostel> hostels = hostelService.getAllHostels();
            m.addAttribute("items", hostels);
            m.addAttribute("title", "Hostels");
            m.addAttribute("type", "hostel");
            return "admin/list";
        } catch (Exception e) {
            e.printStackTrace();
            return "user/error";
        }
    }

    @DeleteMapping("/hostel/{id}")
    public String deletebHostel(@PathVariable("id") int id, Model m) {
        try {
            this.hostelService.deleteHostel(id);
            m.addAttribute("items", hostelService.getAllHostels());
            m.addAttribute("title", "Hostels");
            m.addAttribute("type", "hostel");
            return "admin/list";
        } catch (Exception e) {
            e.printStackTrace();
            m.addAttribute("message", "Hostel you trying to delete doesn't exist!");
            return "user/error";
        }
    }


    @PutMapping("/hostel/update/{id}")
    public String updateHostel(@PathVariable("id") int id, @ModelAttribute("item") Hostel hostel, Model m) {
        try {
            this.hostelService.updateHostel(hostel, id);
            m.addAttribute("items", hostelService.getAllHostels());
            m.addAttribute("title", "Hostels");
            m.addAttribute("type", "hostel");
            return "admin/list";
        } catch (Exception e) {
            return "user/error";
        }
    }




    //Mess Controllers

    @GetMapping("/messes")
    public String messes(Model m) {
        List<Mess> messes = messService.getAllMesses();
        m.addAttribute("title", "Messes");
        m.addAttribute("items", messes);
        m.addAttribute("type", "mess");
        return "admin/list";
    }

    @GetMapping("/mess/{id}")
    public String showMess(Model m, @PathVariable("id") int id) {
        Mess mess = messService.getMessById(id);
        if (mess == null) {
            m.addAttribute("message", "No mess found");
            return "user/error";
        }
        m.addAttribute("item", mess);
        m.addAttribute("title", mess.getName());
        m.addAttribute("type", "mess");
        return "admin/edit";
    }

    @GetMapping("/mess/new")
    public String newMess(Model m) {
        m.addAttribute("type", "mess");
        m.addAttribute("title", "Add new mess");
        return "admin/new";
    }

    @PostMapping("/mess/add")
    public String addMess(Mess mess, Model m) {
        Mess ms = null;
        try {
            ms = this.messService.addMess(mess);
            List<Mess> messes = messService.getAllMesses();
            m.addAttribute("items", messes);
            m.addAttribute("title", "Messes");
            m.addAttribute("type", "mess");
            return "admin/list";
        } catch (Exception e) {
            e.printStackTrace();
            return "user/error";
        }
    }

    @DeleteMapping("/mess/{id}")
    public String deletebMess(@PathVariable("id") int id, Model m) {
        try {
            this.messService.deleteMess(id);
            m.addAttribute("items", messService.getAllMesses());
            m.addAttribute("title", "Messes");
            m.addAttribute("type", "mess");
            return "admin/list";
        } catch (Exception e) {
            e.printStackTrace();
            m.addAttribute("message", "Mess you trying to delete doesn't exist!");
            return "user/error";
        }
    }


    @PutMapping("/mess/update/{id}")
    public String updateMess(@PathVariable("id") int id, @ModelAttribute("item") Mess mess, Model m) {
        try {
            this.messService.updateMess(mess, id);
            
            m.addAttribute("items", messService.getAllMesses());
            m.addAttribute("title", "Messes");
            m.addAttribute("type", "mess");
            return "admin/list";
        } catch (Exception e) {
            return "user/error";
        }
    }




    //Outlet Controller

    @GetMapping("/outlets")
    public String outlets(Model m) {
        List<Outlet> outlets = outletService.getAllOutlets();
        m.addAttribute("title", "Outlets");
        m.addAttribute("items", outlets);
        m.addAttribute("type", "outlet");
        return "admin/list";
    }

    @GetMapping("/outlet/{id}")
    public String showOutlet(Model m, @PathVariable("id") int id) {
        Outlet outlet = outletService.getOutletById(id);
        if (outlet == null) {
            m.addAttribute("message", "No outlet found");
            return "user/error";
        }
        m.addAttribute("item", outlet);
        m.addAttribute("title", outlet.getName());
        m.addAttribute("type", "outlet");
        return "admin/edit";
    }

    @GetMapping("/outlet/new")
    public String newOutlet(Model m) {
        m.addAttribute("type", "outlet");
        m.addAttribute("title", "Add new outlet");
        return "admin/new";
    }

    
    @PostMapping("/outlet/add")
    public String addOutlet(Outlet outlet, Model m) {
        Outlet o = null;
        try {
            o = this.outletService.addOutlet(outlet);
            List<Outlet> outlets = outletService.getAllOutlets();
            m.addAttribute("items", outlets);
            m.addAttribute("title", "outlets");
            m.addAttribute("type", "outlet");
            return "admin/list";
        } catch (Exception e) {
            e.printStackTrace();
            return "user/error";
        }
    }

    @DeleteMapping("/outlet/{id}")
    public String deletebOutlet(@PathVariable("id") int id, Model m) {
        try {
            this.outletService.deleteOutlet(id);
            m.addAttribute("items", outletService.getAllOutlets());
            m.addAttribute("title", "outlets");
            m.addAttribute("type", "outlet");
            return "admin/list";
        } catch (Exception e) {
            e.printStackTrace();
            m.addAttribute("message", "Outlet you trying to delete doesn't exist!");
            return "user/error";
        }
    }


    @PutMapping("/outlet/update/{id}")
    public String updateOutlet(@PathVariable("id") int id, @ModelAttribute("item") Outlet outlet, Model m) {
        try {
            this.outletService.updateOutlet(outlet, id);
            
            m.addAttribute("items", outletService.getAllOutlets());
            m.addAttribute("title", "outlets");
            m.addAttribute("type", "outlet");
            return "admin/list";
        } catch (Exception e) {
            return "user/error";
        }
    }



    //Club controller

    @GetMapping("/clubs")
    public String clubs(Model m) {
        List<Club> clubs = clubService.getAllClubs();
        m.addAttribute("title", "Clubs");
        m.addAttribute("items", clubs);
        m.addAttribute("type", "club");
        return "admin/list";
    }

    @GetMapping("/club/{id}")
    public String showClub(Model m, @PathVariable("id") int id) {
        Club club = clubService.getClubById(id);
        if (club == null) {
            m.addAttribute("message", "No club found");
            return "user/error";
        }
        m.addAttribute("item", club);
        m.addAttribute("title", club.getName());
        m.addAttribute("type", "club");
        return "admin/edit";
    }

    @GetMapping("/club/new")
    public String newClub(Model m) {
        m.addAttribute("type", "club");
        m.addAttribute("title", "Add new club");
        return "admin/new";
    }

    
    @PostMapping("/club/add")
    public String addClub(Club club, Model m) {
        Club c = null;
        try {
            c = this.clubService.addClub(club);
            List<Club> clubs = clubService.getAllClubs();
            m.addAttribute("items", clubs);
            m.addAttribute("title", "clubs");
            m.addAttribute("type", "club");
            return "admin/list";
        } catch (Exception e) {
            e.printStackTrace();
            return "user/error";
        }
    }

    @DeleteMapping("/club/{id}")
    public String deletebClub(@PathVariable("id") int id, Model m) {
        try {
            this.clubService.deleteClub(id);
            m.addAttribute("items", clubService.getAllClubs());
            m.addAttribute("title", "clubs");
            m.addAttribute("type", "club");
            return "admin/list";
        } catch (Exception e) {
            e.printStackTrace();
            m.addAttribute("message", "Club you trying to delete doesn't exist!");
            return "user/error";
        }
    }


    @PutMapping("/club/update/{id}")
    public String updateClub(@PathVariable("id") int id, @ModelAttribute("item") Club club, Model m) {
        try {
            this.clubService.updateClub(club, id);
            
            m.addAttribute("items", clubService.getAllClubs());
            m.addAttribute("title", "clubs");
            m.addAttribute("type", "club");
            return "admin/list";
        } catch (Exception e) {
            return "user/error";
        }
    }
}