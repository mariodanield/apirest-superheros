package com.superheros.apirest.models.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.MethodMode;

import com.superheros.apirest.models.entity.Hero;

@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest
class HeroServiceImplTest {

	@Autowired
	private IHeroService heroService;
	
	@Test
	@Order(1)
	void testFindAll() {
		assertThat(heroService).isNotNull();
		assertDoesNotThrow(() -> {
			heroService.findAll();
	    });
		assertEquals(5, heroService.findAll().size());
	}

	@DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
	@Test
	@Order(4)
	void testSaveHero() {
		Hero hero = new Hero("Mario", 43, "Strategist");
		assertThat(heroService).isNotNull();
		assertDoesNotThrow(() -> {
			heroService.save(new Hero(hero));
	    });
		
		Hero result= heroService.save(new Hero(hero));
		assertThat(result).isNotNull();
		assertEquals(hero.getName(), result.getName());
		assertEquals(hero.getAge(), result.getAge());
		assertEquals(hero.getPower(), result.getPower());
	}

	@DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
	@Test
	@Order(5)
	void testSaveListOfHero() {
		List<Hero> list = new ArrayList<Hero>();
		list.add(new Hero("Mario1", 43, "Strategist1"));
		list.add(new Hero("Mario2", 33, "Strategist2"));
		list.add(new Hero("Mario3", 22, "Strategist3"));
		assertThat(heroService).isNotNull();
		assertDoesNotThrow(() -> {
			heroService.save(list);
	    });
		
		List<Hero> resultList= heroService.save(list);
		assertThat(resultList).isNotNull();
		assertEquals(3, resultList.size());
	}

	@Test
	@Order(2)
	void testFindById() {
		assertThat(heroService).isNotNull();
		assertDoesNotThrow(() -> {
			heroService.findById(1L);
			heroService.findById(100L);
	    });
		
		Optional<Hero> result = heroService.findById(1L);
		assertTrue(result.isPresent());
		assertFalse(result.isEmpty());
	}

	@DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
	@Test
	@Order(6)
	void testDeleteById() {
		assertThat(heroService).isNotNull();
		assertDoesNotThrow(() -> {
			heroService.deleteById(1L);
			heroService.deleteById(100L);
	    });
		
		Boolean result = heroService.deleteById(2L);
		assertTrue(result);
		result = heroService.deleteById(200L);
		assertFalse(result);
	}

	@Test
	@Order(3)
	void testFindByNameContains() {
		assertThat(heroService).isNotNull();
		assertDoesNotThrow(() -> {
			heroService.findByNameContains("man");
			heroService.findByNameContains("1.2-*09@//¿?=%#@!|'<>{}[]_:;,`^+¬ºª");
	    });
		
		List<Hero> resultList = heroService.findByNameContains("man");
		assertEquals(4,resultList.size());
	}

}
