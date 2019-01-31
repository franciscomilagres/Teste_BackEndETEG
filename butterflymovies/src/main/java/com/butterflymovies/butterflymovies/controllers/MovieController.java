package com.butterflymovies.butterflymovies.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MovieController {
//list, set and insert
	
	@RequestMapping(value= "/filmes", method = RequestMethod.GET)
	public String listMovies() {
		return "movies/movies";
	}
}
