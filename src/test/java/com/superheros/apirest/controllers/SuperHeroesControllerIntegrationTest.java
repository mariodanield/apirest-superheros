package com.superheros.apirest.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.MethodMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.superheros.apirest.models.entity.Hero;
import com.superheros.apirest.models.services.IHeroService;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class SuperHeroesControllerIntegrationTest {

	@Autowired
	private IHeroService heroService;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private MockMvc mockMvc;

	@Test
	@WithMockUser(username = "user", roles = "USER")
	public void testFindAll() throws Exception {
		this.mockMvc.perform(get("/api/superHeroes/findAll")).andExpect(status().isOk());
	}

	@Test
	@WithMockUser(username = "user", roles = "USER")
	public void testFindById() throws Exception {
		this.mockMvc.perform(get("/api/superHeroes/findById/{id}","100")).andExpect(status().isNotFound());
		this.mockMvc.perform(get("/api/superHeroes/findById/{id}","1")).andExpect(status().isOk());
	}
	
	@Test
	@WithMockUser(username = "user", roles = "USER")
	public void testFindByNameContains() throws Exception {
		this.mockMvc.perform(get("/api/superHeroes/findByNameContains/{name}","1")).andExpect(status().isNotFound());
		this.mockMvc.perform(get("/api/superHeroes/findByNameContains/{name}","man")).andExpect(status().isOk());
	}
	
	@DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
	@Test
	@WithMockUser(username = "admin", roles = "ADMIN")
	void testSaveHero() throws JsonProcessingException, Exception {
		Hero hero = new Hero("Daniel", 28, "Force");

	    mockMvc.perform(post("/api/superHeroes/saveHero")
		    	.content(objectMapper.writeValueAsString(hero))
		    	.contentType(MediaType.APPLICATION_JSON)
		    	.accept(MediaType.APPLICATION_JSON))
		    	.andExpect(status().isOk());

	    List<Hero> heroResult = heroService.findByNameContains("Daniel");
	    assertNotNull(heroResult);
	    assertEquals(1, heroResult.size());
	    assertThat(heroResult.get(0).getName()).isEqualTo("Daniel");
	    assertThat(heroResult.get(0).getAge()).isEqualTo(28);
	    assertThat(heroResult.get(0).getPower()).isEqualTo("Force");
	}
	
	@DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
	@Test
	void testSaveHeroUnauthorized() throws JsonProcessingException, Exception {
		Hero hero = new Hero("Daniel", 28, "Force");

	    mockMvc.perform(post("/api/superHeroes/saveHero")
		    	.content(objectMapper.writeValueAsString(hero))
		    	.contentType(MediaType.APPLICATION_JSON)
		    	.accept(MediaType.APPLICATION_JSON))
		    	.andExpect(status().isUnauthorized());

	    List<Hero> heroResult = heroService.findByNameContains("Daniel");
	    assertNotNull(heroResult);
	    assertEquals(0, heroResult.size());
	}
	
	@DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
	@Test
	@WithMockUser(username = "user", roles = "USER")
	void testSaveHeroForbidden() throws JsonProcessingException, Exception {
		Hero hero = new Hero("Daniel", 28, "Force");

	    mockMvc.perform(post("/api/superHeroes/saveHero")
		    	.content(objectMapper.writeValueAsString(hero))
		    	.contentType(MediaType.APPLICATION_JSON)
		    	.accept(MediaType.APPLICATION_JSON))
		    	.andExpect(status().isForbidden());

	    List<Hero> heroResult = heroService.findByNameContains("Daniel");
	    assertNotNull(heroResult);
	    assertEquals(0, heroResult.size());
	}

	@DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
	@Test
	@WithMockUser(username = "admin", roles = "ADMIN")
	void testDeleteById() throws JsonProcessingException, Exception {
	    mockMvc.perform(delete("/api/superHeroes/deleteById/{id}", 5))
		    	.andExpect(status().isNoContent());

	    Optional<Hero> heroResult = heroService.findById(5L);
	    assertFalse(heroResult.isPresent());
	}

}
