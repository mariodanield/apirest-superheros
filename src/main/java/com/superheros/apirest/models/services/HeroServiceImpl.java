package com.superheros.apirest.models.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.superheros.apirest.models.dao.IHeroDao;
import com.superheros.apirest.models.entity.Hero;

@Service
public class HeroServiceImpl implements IHeroService {

	@Autowired
	private IHeroDao heroDao;

	@Override
	@Cacheable("heros")
	@Transactional(readOnly = true)
	public List<Hero> findAll() {
		//Prueba de cacheo de peticiones
		try {
		      long time = 3000L;
		      Thread.sleep(time);
		    } catch (InterruptedException e) {
		      throw new IllegalStateException(e);
		    }
		return (List<Hero>)heroDao.findAll();
	}

	@Override
	@Transactional
	public Hero save(Hero hero) {
		return heroDao.save(hero);
	}
	
	@Override
	@Transactional
	public List<Hero> save(List<Hero> heroList) {
		return (List<Hero>) heroDao.saveAll(heroList);
	}

	@Override
	@Cacheable("heros")
	@Transactional(readOnly = true)
	public Optional<Hero> findById(Long id) {
		
		return heroDao.findById(id);
	}

	@Override
	@Transactional
	public boolean deleteById(Long id) {
		if(!findById(id).isPresent()) {
			return false;
		}
		heroDao.deleteById(id);
		return true;
	}
	
	@Override
	@Cacheable("heros")
	@Transactional(readOnly = true)
	public List<Hero> findByNameContains(String name){
		
		return heroDao.findByNameContainsIgnoreCase(name);
	}
	
}
