package com.butterflymovies.butterflymovies.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;


@Entity
public class Renting implements Serializable{
	
	/**
	 * Generated by eclipse. This keeps everybody happy! 
	 */
	private static final long serialVersionUID = 5530837988124322848L;
	
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private long cod;
	
	@ManyToOne()
	private Movie movie;
	
	@ManyToOne
	private User user;
	
	@NotEmpty
	private String start, devolution;
			
	
	public long getCod() {
		return cod;
	}
	public void setCod(long cod) {
		this.cod = cod;
	}
	public Movie getMovie() {
		return movie;
	}
	public void setMovie(Movie movie) {
		this.movie = movie;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getDevolution() {
		return devolution;
	}
	public void setDevolution(String devolution) {
		this.devolution = devolution;
	}		
	
}
