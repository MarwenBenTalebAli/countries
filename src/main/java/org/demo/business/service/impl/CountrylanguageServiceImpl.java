/*
 * Created on 2020-05-29 ( Time 14:26:45 )
 * Generated by Telosys Tools Generator ( version 3.1.2 )
 */
package org.demo.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.demo.bean.Countrylanguage;
import org.demo.bean.jpa.CountrylanguageEntity;
import org.demo.bean.jpa.CountrylanguageEntityKey;
import org.demo.business.service.CountrylanguageService;
import org.demo.business.service.mapping.CountrylanguageServiceMapper;
import org.demo.data.repository.jpa.CountrylanguageJpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of CountrylanguageService
 */
@Component
@Transactional
public class CountrylanguageServiceImpl implements CountrylanguageService {

	@Resource
	private CountrylanguageJpaRepository countrylanguageJpaRepository;

	@Resource
	private CountrylanguageServiceMapper countrylanguageServiceMapper;
	
	@Override
	public Countrylanguage findById(String countrycode, String language) {
		CountrylanguageEntityKey id = new CountrylanguageEntityKey(countrycode, language);
		CountrylanguageEntity countrylanguageEntity = countrylanguageJpaRepository.findOne(id);
		return countrylanguageServiceMapper.mapCountrylanguageEntityToCountrylanguage(countrylanguageEntity);
	}

	@Override
	public List<Countrylanguage> findAll() {
		Iterable<CountrylanguageEntity> entities = countrylanguageJpaRepository.findAll();
		List<Countrylanguage> beans = new ArrayList<Countrylanguage>();
		for(CountrylanguageEntity countrylanguageEntity : entities) {
			beans.add(countrylanguageServiceMapper.mapCountrylanguageEntityToCountrylanguage(countrylanguageEntity));
		}
		return beans;
	}

	@Override
	public Countrylanguage save(Countrylanguage countrylanguage) {
		return update(countrylanguage) ;
	}

	@Override
	public Countrylanguage create(Countrylanguage countrylanguage) {
		CountrylanguageEntityKey id = new CountrylanguageEntityKey(countrylanguage.getCountrycode(), countrylanguage.getLanguage());
		CountrylanguageEntity countrylanguageEntity = countrylanguageJpaRepository.findOne(id);
		if( countrylanguageEntity != null ) {
			throw new IllegalStateException("already.exists");
		}
		countrylanguageEntity = new CountrylanguageEntity();
		countrylanguageServiceMapper.mapCountrylanguageToCountrylanguageEntity(countrylanguage, countrylanguageEntity);
		CountrylanguageEntity countrylanguageEntitySaved = countrylanguageJpaRepository.save(countrylanguageEntity);
		return countrylanguageServiceMapper.mapCountrylanguageEntityToCountrylanguage(countrylanguageEntitySaved);
	}

	@Override
	public Countrylanguage update(Countrylanguage countrylanguage) {
		CountrylanguageEntityKey id = new CountrylanguageEntityKey(countrylanguage.getCountrycode(), countrylanguage.getLanguage());
		CountrylanguageEntity countrylanguageEntity = countrylanguageJpaRepository.findOne(id);
		countrylanguageServiceMapper.mapCountrylanguageToCountrylanguageEntity(countrylanguage, countrylanguageEntity);
		CountrylanguageEntity countrylanguageEntitySaved = countrylanguageJpaRepository.save(countrylanguageEntity);
		return countrylanguageServiceMapper.mapCountrylanguageEntityToCountrylanguage(countrylanguageEntitySaved);
	}

	@Override
	public void delete(String countrycode, String language) {
		CountrylanguageEntityKey id = new CountrylanguageEntityKey(countrycode, language);
		countrylanguageJpaRepository.delete(id);
	}

	public CountrylanguageJpaRepository getCountrylanguageJpaRepository() {
		return countrylanguageJpaRepository;
	}

	public void setCountrylanguageJpaRepository(CountrylanguageJpaRepository countrylanguageJpaRepository) {
		this.countrylanguageJpaRepository = countrylanguageJpaRepository;
	}

	public CountrylanguageServiceMapper getCountrylanguageServiceMapper() {
		return countrylanguageServiceMapper;
	}

	public void setCountrylanguageServiceMapper(CountrylanguageServiceMapper countrylanguageServiceMapper) {
		this.countrylanguageServiceMapper = countrylanguageServiceMapper;
	}

}
