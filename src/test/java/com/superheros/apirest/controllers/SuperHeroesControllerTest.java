package com.superheros.apirest.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.MethodMode;

import com.superheros.apirest.exception.ResourceNotFoundException;
import com.superheros.apirest.models.entity.Hero;

@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest
class SuperHeroesControllerTest {

	@Autowired
	private SuperHeroesController SuperHerosController;
	
	@Test
	@Order(1)
	 void superHerosControllerIsNotNull() {
	  assertThat(SuperHerosController).isNotNull();
	 }
	
	@Test
	@Order(1)
	void testFindAll() {
		ResponseEntity<List<Hero>> response = SuperHerosController.findAll();
		assertNotNull(response);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}

	@Test
	@Order(2)
	void testFindById() throws ResourceNotFoundException {
		ResponseEntity<Hero> response = SuperHerosController.findById(5L);
		assertNotNull(response);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}

	@Test
	@Order(3)
	void testFindByNameContains() throws ResourceNotFoundException {
		ResponseEntity<List<Hero>> response = SuperHerosController.findByNameContains("man");
		assertNotNull(response);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}

	@DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
	@Test
	@Order(4)
	void testSaveHero() {
		ResponseEntity<Hero> response = SuperHerosController.saveHero(new Hero("Mario", 28, "Force"));
		assertNotNull(response);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}

	@DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
	@Test
	@Order(5)
	void testDeleteById() throws ResourceNotFoundException {
		ResponseEntity response = SuperHerosController.deleteById(5L);
		assertNotNull(response);
		assertEquals(response.getStatusCode(), HttpStatus.NO_CONTENT);
	}

}
