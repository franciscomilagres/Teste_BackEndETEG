package com.butterflymovies.butterflymovies.controllers;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	public String addUser(@Valid User user, BindingResult res, RedirectAttributes attr) {
		if(res.hasErrors()) {
			attr.addFlashAttribute("msg", "Todos os campos devem ser preenchidos!");
			return "redirect:/usuarios/cadastro";
		}
		urepo.save(user);
		return "redirect:/usuarios";
	}
	
	@RequestMapping(value= "/usuarios/{cod}", method = RequestMethod.DELETE)
	public String delUser(@PathVariable("cod") long cod) {
		User user = urepo.findByCod(cod);
		if(user != null) {
			urepo.delete(user);
		}
		return "redirect:/usuarios";
	}
	
	private boolean validateCPF(String cpf) {
		boolean resp = false;
		
		if(cpf.length() == 11) {
			int count=11;
			int sum1=0, sum2=0;
			for(int i=0; i < 11; i++) {
				sum2+= cpf.charAt(i)*count--;
				sum1+= cpf.charAt(i)*count;
			}
		}
		
		return resp;
		
	}
	
}
