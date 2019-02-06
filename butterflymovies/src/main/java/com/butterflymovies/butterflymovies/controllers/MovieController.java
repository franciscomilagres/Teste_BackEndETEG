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
	public String addMovie(@Valid Movie movie, BindingResult res, RedirectAttributes attr) {
		if(res.hasErrors()) {
			attr.addFlashAttribute("msg", "Todos os campos devem ser preenchidos!");
			return "redirect:/filmes/cadastro";
		}
		mrepo.save(movie);
		//attr.addFlashAttribute("msg", "Filme cadastrado com sucesso!");
		return "redirect:/filmes";
	}
	
	@RequestMapping(value= "/filmes/{cod}", method = RequestMethod.DELETE)
	public String delMovie(@PathVariable("cod") long cod) {
		Movie movie = mrepo.findByCod(cod);
		if(movie != null) {
			mrepo.delete(movie);
		}
		return "redirect:/filmes";
	}
	
}
