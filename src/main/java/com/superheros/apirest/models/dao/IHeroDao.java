package com.superheros.apirest.models.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.superheros.apirest.models.entity.Hero;

public interface IHeroDao extends CrudRepository<Hero, Long> {

	List<Hero> findByNameContainsIgnoreCase(String name);
	
}
