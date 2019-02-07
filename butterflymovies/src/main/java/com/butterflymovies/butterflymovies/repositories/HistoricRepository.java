package com.butterflymovies.butterflymovies.repositories;

import org.springframework.data.repository.CrudRepository;

import com.butterflymovies.butterflymovies.models.Historic;

public interface HistoricRepository extends CrudRepository<Historic, String> {

}
