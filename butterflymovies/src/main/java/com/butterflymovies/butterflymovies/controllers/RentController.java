package com.butterflymovies.butterflymovies.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.butterflymovies.butterflymovies.models.Movie;
import com.butterflymovies.butterflymovies.models.Renting;
import com.butterflymovies.butterflymovies.models.User;
import com.butterflymovies.butterflymovies.repositories.MovieRepository;
import com.butterflymovies.butterflymovies.repositories.RentRepository;
import com.butterflymovies.butterflymovies.repositories.UserRepository;

@Controller
public class RentController {
	
	@Autowired
	RentRepository rrepo;
	
	@Autowired
	MovieRepository mrepo;
	
	@Autowired
	UserRepository urepo;
	
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
	public String addRent(long movieCod, long userCod, String start, String devolution) {

		int quantity;
		Renting renting = new Renting();
		Movie movie = mrepo.findByCod(movieCod);
		User user = urepo.findByCod(userCod);				//its so wonderful		
		
		quantity = movie.getQuantity();
		if(quantity > 0) {
			renting.setMovie(movie);
			renting.setUser(user);
			renting.setStart(start);
			renting.setDevolution(devolution);
			//movie.setQuantity(quantity-1);			//TODO
			
			mrepo.save(movie);			//updating movie quantity!
			rrepo.save(renting);
		}
		else {
			//keep calm
		}
		return "redirect:/locacoes";
	}
}
