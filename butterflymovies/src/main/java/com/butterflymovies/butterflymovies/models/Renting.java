package com.butterflymovies.butterflymovies.models;

import java.util.Date;

public class Renting {
	private Movie movie;
	private User user;
	private Date start, devolution;
	
	
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
	public Date getStart() {
		return start;
	}
	public void setStart(Date start) {
		this.start = start;
	}
	public Date getDevolution() {
		return devolution;
	}
	public void setDevolution(Date devolution) {
		this.devolution = devolution;
	}
	
	
	
	
}
