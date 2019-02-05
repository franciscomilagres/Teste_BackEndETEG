package com.butterflymovies.butterflymovies.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.butterflymovies.butterflymovies.models.Movie;
import com.butterflymovies.butterflymovies.repositories.MovieRepository;

@Controller
public class MovieController {
//list, set and insert
	
	@Autowired
	private MovieRepository mrepo;
	
	@RequestMapping(value= "/filmes", method = RequestMethod.GET)
	public ModelAndView listMovies() {
		ModelAndView mav = new ModelAndView("movies/movies");
		Iterable<Movie> movies = mrepo.findAll();
		
		mav.addObject("movies", movies);
		
		return mav;
		//return "movies/movies";
	}
	
	@RequestMapping(value= "/filmes/cadastro", method = RequestMethod.GET)  //cadastrar um novo filme
	public String formMovies() {
		return "movies/formMovie";
	}
	
	@RequestMapping(value= "/filmes", method = RequestMethod.POST)		//adicionando o novo filme
	public String addMovie(Movie movie) {
		mrepo.save(movie);
		return "redirect:/filmes";
	}
}
