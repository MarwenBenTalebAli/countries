/*
 * Created on 2020-05-29 ( Time 14:26:45 )
 * Generated by Telosys Tools Generator ( version 3.1.2 )
 */
package org.demo.business.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.demo.bean.City;
import org.demo.bean.jpa.CityEntity;
import org.demo.business.service.mapping.CityServiceMapper;
import org.demo.data.repository.jpa.CityJpaRepository;
import org.demo.test.CityFactoryForTest;
import org.demo.test.CityEntityFactoryForTest;
import org.demo.test.MockValues;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Test : Implementation of CityService
 */
@RunWith(MockitoJUnitRunner.class)
public class CityServiceImplTest {

	@InjectMocks
	private CityServiceImpl cityService;
	@Mock
	private CityJpaRepository cityJpaRepository;
	@Mock
	private CityServiceMapper cityServiceMapper;
	
	private CityFactoryForTest cityFactoryForTest = new CityFactoryForTest();

	private CityEntityFactoryForTest cityEntityFactoryForTest = new CityEntityFactoryForTest();

	private MockValues mockValues = new MockValues();
	
	@Test
	public void findById() {
		// Given
		Integer id = mockValues.nextInteger();
		
		CityEntity cityEntity = cityJpaRepository.findOne(id);
		
		City city = cityFactoryForTest.newCity();
		when(cityServiceMapper.mapCityEntityToCity(cityEntity)).thenReturn(city);

		// When
		City cityFound = cityService.findById(id);

		// Then
		assertEquals(city.getId(),cityFound.getId());
	}

	@Test
	public void findAll() {
		// Given
		List<CityEntity> cityEntitys = new ArrayList<CityEntity>();
		CityEntity cityEntity1 = cityEntityFactoryForTest.newCityEntity();
		cityEntitys.add(cityEntity1);
		CityEntity cityEntity2 = cityEntityFactoryForTest.newCityEntity();
		cityEntitys.add(cityEntity2);
		when(cityJpaRepository.findAll()).thenReturn(cityEntitys);
		
		City city1 = cityFactoryForTest.newCity();
		when(cityServiceMapper.mapCityEntityToCity(cityEntity1)).thenReturn(city1);
		City city2 = cityFactoryForTest.newCity();
		when(cityServiceMapper.mapCityEntityToCity(cityEntity2)).thenReturn(city2);

		// When
		List<City> citysFounds = cityService.findAll();

		// Then
		assertTrue(city1 == citysFounds.get(0));
		assertTrue(city2 == citysFounds.get(1));
	}

	@Test
	public void create() {
		// Given
		City city = cityFactoryForTest.newCity();

		CityEntity cityEntity = cityEntityFactoryForTest.newCityEntity();
		when(cityJpaRepository.findOne(city.getId())).thenReturn(null);
		
		cityEntity = new CityEntity();
		cityServiceMapper.mapCityToCityEntity(city, cityEntity);
		CityEntity cityEntitySaved = cityJpaRepository.save(cityEntity);
		
		City citySaved = cityFactoryForTest.newCity();
		when(cityServiceMapper.mapCityEntityToCity(cityEntitySaved)).thenReturn(citySaved);

		// When
		City cityResult = cityService.create(city);

		// Then
		assertTrue(cityResult == citySaved);
	}

	@Test
	public void createKOExists() {
		// Given
		City city = cityFactoryForTest.newCity();

		CityEntity cityEntity = cityEntityFactoryForTest.newCityEntity();
		when(cityJpaRepository.findOne(city.getId())).thenReturn(cityEntity);

		// When
		Exception exception = null;
		try {
			cityService.create(city);
		} catch(Exception e) {
			exception = e;
		}

		// Then
		assertTrue(exception instanceof IllegalStateException);
		assertEquals("already.exists", exception.getMessage());
	}

	@Test
	public void update() {
		// Given
		City city = cityFactoryForTest.newCity();

		CityEntity cityEntity = cityEntityFactoryForTest.newCityEntity();
		when(cityJpaRepository.findOne(city.getId())).thenReturn(cityEntity);
		
		CityEntity cityEntitySaved = cityEntityFactoryForTest.newCityEntity();
		when(cityJpaRepository.save(cityEntity)).thenReturn(cityEntitySaved);
		
		City citySaved = cityFactoryForTest.newCity();
		when(cityServiceMapper.mapCityEntityToCity(cityEntitySaved)).thenReturn(citySaved);

		// When
		City cityResult = cityService.update(city);

		// Then
		verify(cityServiceMapper).mapCityToCityEntity(city, cityEntity);
		assertTrue(cityResult == citySaved);
	}

	@Test
	public void delete() {
		// Given
		Integer id = mockValues.nextInteger();

		// When
		cityService.delete(id);

		// Then
		verify(cityJpaRepository).delete(id);
		
	}

}
