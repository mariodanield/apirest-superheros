package com.superheros.apirest.controllers;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.superheros.apirest.annotation.LogExecutionTime;
import com.superheros.apirest.exception.ResourceNotFoundException;
import com.superheros.apirest.models.entity.Hero;
import com.superheros.apirest.models.services.IHeroService;


@RestController
@EnableCaching //habilitando el cacheo de peticiones
@RequestMapping("/api/superHeroes")
public class SuperHeroesController {

	private static final Logger logger = LogManager.getLogger(SuperHeroesController.class);
	
	@Autowired
	private IHeroService heroService;
	
	@LogExecutionTime(name="timeFindAll")
	@GetMapping("/findAll")
	public ResponseEntity<List<Hero>> findAll(){
		logger.info("Start service rest /findAll.");
		List<Hero> resultList = heroService.findAll();
		logger.info("End service rest /findAll.");
		return new ResponseEntity<List<Hero>>(resultList, HttpStatus.OK);
	}
	
	@LogExecutionTime
	@GetMapping("/findById/{heroId}")
	public ResponseEntity<Hero> findById(@PathVariable("heroId") Long id ) throws ResourceNotFoundException{
		logger.info("Start service rest /findById/{heroId}.");
		Optional<Hero> optionalHero = heroService.findById(id);
		logger.info("End service rest /findById/{heroId}.");
		if(!optionalHero.isPresent()) {
			throw new ResourceNotFoundException("Hero not found with id " + id);
		}
		return new ResponseEntity<Hero>(optionalHero.get(), HttpStatus.OK);
	}
	
	@LogExecutionTime
	@GetMapping("/findByNameContains/{name}")
	public ResponseEntity<List<Hero>> findByNameContains(@PathVariable("name") String name ) throws ResourceNotFoundException{
		logger.info("Start service rest /findByNameContains/{name}.");
		List<Hero> resultList = heroService.findByNameContains(name);
		logger.info("End service rest /findByNameContains/{name}.");
		if ( resultList ==null || resultList.isEmpty()) {
			throw new ResourceNotFoundException("Heros not found with name " + name);
		}
		return new ResponseEntity<List<Hero>>(resultList, HttpStatus.OK);
	}
	
	@LogExecutionTime
	@PostMapping("/saveHero")
	public ResponseEntity<Hero> saveHero(@Validated @RequestBody Hero hero) {
		logger.info("Start service rest /saveHero.");
		Hero result = heroService.save(hero);
		logger.info("End service rest /saveHero.");
		return new ResponseEntity<Hero>(result, HttpStatus.OK);
	}
	
	@LogExecutionTime
	@DeleteMapping("/deleteById/{id}")
	public ResponseEntity<?> deleteById(@PathVariable("id") Long id) throws ResourceNotFoundException {
		logger.info("Start service rest /deleteById/{id}.");
		Boolean result = heroService.deleteById(id);
		logger.info("End service rest /deleteById/{id}.");
		if (!result) {
			throw new ResourceNotFoundException("Hero not found with id " + id);
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
}
