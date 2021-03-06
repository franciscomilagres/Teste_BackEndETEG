package com.butterflymovies.butterflymovies.repositories;

import org.springframework.data.repository.CrudRepository;

import com.butterflymovies.butterflymovies.models.User;

public interface UserRepository extends CrudRepository<User, String> {
	User findByCod(long cod);
	User findByCpf(String cpf);
}
