package com.butterflymovies.butterflymovies.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.butterflymovies.butterflymovies.models.Renting;
import com.butterflymovies.butterflymovies.repositories.RentRepository;

@Controller
public class RentController {
	
	@Autowired
	RentRepository rrepo;
	
	@RequestMapping(value= "/locacoes", method = RequestMethod.GET)
	public ModelAndView listRents() {
		ModelAndView mav = new ModelAndView("rents/rents");
		Iterable<Renting> rents = rrepo.findAll();
		
		mav.addObject("rents", rents);
		return mav;
	}
	
	@RequestMapping(value="/locacoes/nova", method = RequestMethod.GET)
	public String formRent() {
		return "rents/formRent";
	}
	
	@RequestMapping(value="/locacoes", method = RequestMethod.POST)
	public String addRent(Renting renting) {
		rrepo.save(renting);
		return "redirect:/filmes";
	}
}
