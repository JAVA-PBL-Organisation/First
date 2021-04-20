package com.studentguide.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.studentguide.dao.FavoriteRepository;
import com.studentguide.dao.UserRepository;
import com.studentguide.entities.Favorite;
import com.studentguide.entities.User;

@RestController
public class SearchController {

	@Autowired
	private UserRepository userRepository;
	
	
	@Autowired
	private FavoriteRepository favoriteRepository;
	
	
	@GetMapping("/search/{query}")
	public ResponseEntity<?> search(@PathVariable("query") String query, Principal principal)
	{
		System.out.println(query);
		
		User user= this.userRepository.getUserByUsername(principal.getName());
		List<Favorite> favorites=this.favoriteRepository.findByNameContainingAndUser(query, user);
		
		return ResponseEntity.ok(favorites);
	}
}
