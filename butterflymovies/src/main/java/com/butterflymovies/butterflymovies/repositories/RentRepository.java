package com.butterflymovies.butterflymovies.repositories;

import org.springframework.data.repository.CrudRepository;

import com.butterflymovies.butterflymovies.models.Renting;

public interface RentRepository extends CrudRepository<Renting, String> {
	Renting findByCod(long cod);
}
