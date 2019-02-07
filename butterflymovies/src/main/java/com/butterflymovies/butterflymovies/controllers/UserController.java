package com.butterflymovies.butterflymovies.controllers;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

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
		else if(!this.validateNasc(user.getNascimento())){
			attr.addFlashAttribute("msg", "O Usuário deve ser maior de idade!");
			return "redirect:/usuarios/cadastro";						//alternatives are similar but on future would be changed
		}
		else if(!this.validateCPF(user.getCpf())) {
			attr.addFlashAttribute("msg", "CPF inválido!");
			return "redirect:/usuarios/cadastro";
		}
		else if(urepo.findByCpf(user.getCpf()) != null) {
			attr.addFlashAttribute("msg", "CPF já cadastrado!");
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
	
	private boolean validateCPF(String cpf) {			//verifies CPF digits;
		boolean resp = false;		
		
		if(cpf.length() == 11) {
			int count=11;
			int sum1=0, sum2=0;
			for(int i=0; i < 9; i++) {
				sum2+= count * Character.getNumericValue(cpf.charAt(i));
				sum1+= (count-1) * Character.getNumericValue(cpf.charAt(i));
				count--;
			}
			sum2+= Character.getNumericValue(cpf.charAt(9)) * count;
			
			if(((sum1*10)%11)%10 == Character.getNumericValue(cpf.charAt(9)) &&
									((sum2*10)%11)%10 == Character.getNumericValue(cpf.charAt(10))) { 	//validating digits.
				resp = true;
			}
			return resp;			
		}
		
		return resp;		
	}

	private boolean validateNasc(String nasc) {		//if user is 18+ returns true. else, false;

		LocalDate datenasc;
		try{
			datenasc = LocalDate.parse(nasc, DateTimeFormatter.ISO_DATE);
		} catch(DateTimeParseException e) {
			System.out.println("UserController msg: Date is invalid");
			return false;
		}
		LocalDate today = LocalDate.now();							//Java deprecated Date gets and this became boring!
		
		if (Period.between(datenasc,today).getYears() < 18) {
			return false;
		}
		else {
			return true;
		}
	}
	
}
