package com.studentguide.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import com.studentguide.dao.FavoriteRepository;
import com.studentguide.dao.UserRepository;
import com.studentguide.entities.Favorite;
import com.studentguide.entities.User;
import com.studentguide.helper.Message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private FavoriteRepository favoriteRepository;
    
    
    @ModelAttribute
    public void addCommonData(Model m,Principal  principal) {
    	String username = principal.getName();
        User user = userRepository.getUserByUsername(username);
        m.addAttribute("user", user);
    }

    @GetMapping("/dashboard")
    public String dashboard(Model m, Principal principal) {
        
        m.addAttribute("title", "Dashboard");
        return "user/dashboard";
    }
    
    // open add fav from
    @GetMapping("/add-fav")
    public String openAddFavForm(Model m) {
    	
    	m.addAttribute("favorite", new Favorite());
    	m.addAttribute("title","Add Favorite");
    	return "user/add_fav_form";
    }
    // processing add fav form
    
    @PostMapping("/process-favorite")
    public String processFavorite(@ModelAttribute Favorite favorite, @RequestParam("profileImage") MultipartFile file ,Principal principal, HttpSession session) {
    	
    	try {
    	String name = principal.getName();
    	User user=this.userRepository.getUserByUsername(name);
    	
    	// processing and uploading file
    	if(file.isEmpty()) {
    		//try our msg
    		System.out.println("file is empty");
    		favorite.setImage("contactt.png");
    		
    	}
    	else {
    		
    		//upload file in folder
    		
    		favorite.setImage(file.getOriginalFilename());
    		
    		File saveFile= new ClassPathResource("static/image").getFile();
    		
    	Path path=	Paths.get(saveFile.getAbsolutePath()+ File.separator +file.getOriginalFilename());
  
    		Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
    		System.out.println("image uploaded");
    	}
    	
    	user.getFavorites().add(favorite);
    	favorite.setUser(user);
    	this.userRepository.save(user);
    	System.out.println("Databse updated");
    	
    	
    	// message success
    	
    	session.setAttribute("message", new Message("Favorite is added !! Add more..","success"));
    	System.out.println("Data"+favorite);
    	} catch(Exception e) {
    	 System.out.println("ERROR" + e.getMessage());	
    	 e.printStackTrace();
    	 
    	 // message error
    	 session.setAttribute("message", new Message("Something went wrong !! Try agin","danger"));
  
    	}
    	return "user/add_fav_form";
    	
    }
    
    @GetMapping("/show-favorites/{page}")
    public String showFavorites(@PathVariable("page") Integer page ,Model m,Principal principal) {
    	
    	// send fav list
    	//per page fav 5[n]
    	// current page=0[page]
    	m.addAttribute("title","Show User Favorite");
    	
    	String userName= principal.getName();
    	User user=this.userRepository.getUserByUsername(userName);
    	
    	Pageable pageable = PageRequest.of(page, 5);
    	Page<Favorite> favorites=this.favoriteRepository.findFavoriteByUser(user.getId(),pageable);
    	
    	m.addAttribute("favorites",favorites);
    	m.addAttribute("currentPage",page);
    	m.addAttribute("totalPages", favorites.getTotalPages());
    	
    	return "user/show_favorites";
    }
    // show particular favorite thing details
    @RequestMapping("/{cId}/favorite")
    public String showFavoriteDetail(@PathVariable("cId") Integer cId, Model m, Principal principal) {
    	
    	 System.out.println("CID" + cId);	
    	 String userName= principal.getName();
    	 
    	User user= this.userRepository.getUserByUsername(userName);
    Optional<Favorite>	favoriteOptional = this.favoriteRepository.findById(cId);
    Favorite favorite= favoriteOptional.get();
  
    if(user.getId()==favorite.getUser().getId())
    	  m.addAttribute("favorite",favorite);
          m.addAttribute("title",favorite.getName());
    
    	return "user/favorite_detail";
    }
    // delete contact handler
    @GetMapping("/delete/{cid}")
   public String deleteFavorite(@PathVariable("cid")  Integer cId, Model m,HttpSession session) {
    
    	Favorite favorite= this.favoriteRepository.findById(cId).get();
    	  
    	favorite.setUser(null);
    	
    	this.favoriteRepository.delete(favorite);
    	  session.setAttribute("message", new Message("Favorite deleted successfully.....","success"));
    	return "redirect:/user/show-favorites/0";
    }
    
    @PostMapping("/update-favorite/{cid}")
    public String updateForm(@PathVariable("cid")  Integer cid,Model m) {
    	
    	Favorite favorite= this.favoriteRepository.findById(cid).get();
    	m.addAttribute("title","Update Favorite");
    	m.addAttribute(favorite);
    	return "user/update_form";
    }
    
    // update favorite handler
    
    @RequestMapping(value="/process-update" , method=RequestMethod.POST)
    public String updateHandler(@ModelAttribute Favorite favorite, @RequestParam("profileImage") MultipartFile  file, Model m, HttpSession session, Principal principal) {
    	
    	try {
    		
    		// old fav details
    		
    		Favorite oldfavoriteDetail = this.favoriteRepository.findById(favorite.getcId()).get();
    		
    		if(!file.isEmpty()) {
    			
    			
    			// delete old image
    			
    			File deleteFile= new ClassPathResource("static/image").getFile();
    			File file1= new File(deleteFile, oldfavoriteDetail.getImage());
    			file1.delete();
    			// update new
    			
    			File saveFile= new ClassPathResource("static/image").getFile();
        		
    	    	Path path=	Paths.get(saveFile.getAbsolutePath()+ File.separator +file.getOriginalFilename());
    	  
    	    		Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
    			
    	    		
    	    		favorite.setImage(file.getOriginalFilename());
    		}
    		else {
    			favorite.setImage( oldfavoriteDetail.getImage());
    		}
    		User user= this.userRepository.getUserByUsername(principal.getName());
    		
    		favorite.setUser(user);
    		this.favoriteRepository.save(favorite);
    		 session.setAttribute("message", new Message(" Your Favorite updated successfully.....","success"));
    		 
    		 
    	}
    	catch(Exception e) {
    		
    		e.printStackTrace();
    	}
    	 System.out.println("Favorite Name:" + favorite.getName());	
    	return "redirect:/user/"+ favorite.getcId() + "/favorite";
    }
   
    

}