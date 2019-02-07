package com.butterflymovies.butterflymovies.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.butterflymovies.butterflymovies.models.Historic;
import com.butterflymovies.butterflymovies.models.Movie;
import com.butterflymovies.butterflymovies.models.Renting;
import com.butterflymovies.butterflymovies.models.User;
import com.butterflymovies.butterflymovies.repositories.HistoricRepository;
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
	
	@Autowired
	HistoricRepository hrepo;
	
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
	public String addRent(long movieCod, long userCod, @Valid String start, @Valid String devolution, RedirectAttributes attr) {
		if(movieCod <= 0 || userCod <=0 || start.isEmpty() || devolution.isEmpty()) {
			attr.addFlashAttribute("msg", "Todos os campos devem ser preenchidos corretamente!");
			return "redirect:/locacoes/nova";
		}
		
		Historic historic = new Historic();
		int quantity;
		Renting renting = new Renting();
		Movie movie = mrepo.findByCod(movieCod);
		User user = urepo.findByCod(userCod);				//its so wonderful	
		
		if(movie == null) {
			attr.addFlashAttribute("msg", "Filme não existe!");
			return "redirect:/locacoes/nova";
		}
		else {
			quantity = movie.getQuantity();		
			if(quantity == 0) {
				attr.addFlashAttribute("msg", "Filme indisponível em estoque!");
				return "redirect:/locacoes/nova";
			}
			else if(user == null){
				attr.addFlashAttribute("msg", "Usuário não existe!");
				return "redirect:/locacoes/nova";
			}
			else {
				renting.setMovie(movie);
				renting.setUser(user);
				renting.setStart(start);
				renting.setDevolution(devolution);
				movie.setQuantity(quantity-1);
				
				historic.setData("O filme " + movie.getName() + " foi alugado por " + user.getName() + " no dia " + start +
									" para o dia " + devolution);
				hrepo.save(historic);
				
				mrepo.save(movie);			//updating movie quantity!
				rrepo.save(renting);
				return "redirect:/locacoes";
			}
		}
	}
	
	@RequestMapping(value="/locacoes/{cod}", method= RequestMethod.DELETE)
	public String delRent(@PathVariable("cod") long cod) {
		Renting rent = rrepo.findByCod(cod);
		if(rent != null) {
			Movie movie = rent.getMovie();
			int q = movie.getQuantity();
			movie.setQuantity(q + 1);				//one movie more, rent deleted!
			
			mrepo.save(movie);
			rrepo.delete(rent);
		}
		return "redirect:/locacoes";
	}	
	
	@RequestMapping(value="/locacoes/{cod}", method= RequestMethod.PUT)
	public String updateRent(@PathVariable("cod") long cod, String devolution, RedirectAttributes attr) {
		Renting rent = rrepo.findByCod(cod);
		if(devolution == "") {
			attr.addFlashAttribute("msg", "Preencha a data!");
			return "redirect:/locacoes";
		}
		if(rent == null) {
			attr.addFlashAttribute("msg", "Locação inválida!");
			return "/validationMsg";
		}
		if(rent.getRenews() == 2) {
			attr.addFlashAttribute("msg", "Locação já foi renovada duas vezes!");
			return "redirect:/locacoes";
		}
		Historic historic = new Historic();
		int r = rent.getRenews();
		rent.setDevolution(devolution);
		rent.setRenews(r+1);
		rrepo.save(rent);
		
		historic.setData("O filme " + rent.getMovie().getName() + " foi renovado por " + rent.getUser().getName() + " para o dia " + devolution);
		hrepo.save(historic);
		
		return "redirect:/locacoes";
	}
}
