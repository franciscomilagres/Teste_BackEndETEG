package com.butterflymovies.butterflymovies.repositories;

import org.springframework.data.repository.CrudRepository;

import com.butterflymovies.butterflymovies.models.Movie;

public interface MovieRepository extends CrudRepository<Movie, String> {
	Movie findByCod(long cod);			//I have no idea about how does it implement this with just writing this line;
	
}
