package com.superheros.apirest.models.services;

import java.util.List;
import java.util.Optional;

import com.superheros.apirest.models.entity.Hero;

public interface IHeroService {

	List<Hero> findAll();

	Optional<Hero> findById(Long id);

	Hero save(Hero hero);

	List<Hero> save(List<Hero> heroList);

	boolean deleteById(Long id);

	List<Hero> findByNameContains(String name);

}
