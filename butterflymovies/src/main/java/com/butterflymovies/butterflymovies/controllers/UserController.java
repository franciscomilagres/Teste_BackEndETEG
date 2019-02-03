package com.butterflymovies.butterflymovies.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.butterflymovies.butterflymovies.models.User;
import com.butterflymovies.butterflymovies.repositories.UserRepository;


@Controller
public class UserController {
	
	@Autowired
	private UserRepository urepo;
	
	@RequestMapping(value= "/usuarios", method = RequestMethod.GET)
	public ModelAndView listUsers() {
		ModelAndView mav = new ModelAndView("users/users");
		Iterable<User> users = urepo.findAll();
		
		mav.addObject("users", users);
		return mav;
	}
	
	@RequestMapping(value= "/usuarios/cadastro", method = RequestMethod.GET)  //cadastrar um novo filme
	public String formMovies() {
		return "users/formUser";
	}
	
	@RequestMapping(value= "/usuarios", method = RequestMethod.POST)
	public String addUser(User user) {
		urepo.save(user);
		return "redirect:/usuarios";
	}
	
}
