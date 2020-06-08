package org.demo.web.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

//--- Entities
import org.demo.bean.City;
import org.demo.bean.Country;
import org.demo.test.CityFactoryForTest;
import org.demo.test.CountryFactoryForTest;

//--- Services 
import org.demo.business.service.CityService;
import org.demo.business.service.CountryService;

//--- List Items 
import org.demo.web.listitem.CountryListItem;

import org.demo.web.common.Message;
import org.demo.web.common.MessageHelper;
import org.demo.web.common.MessageType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.MessageSource;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RunWith(MockitoJUnitRunner.class)
public class CityControllerTest {
	
	@InjectMocks
	private CityController cityController;
	@Mock
	private CityService cityService;
	@Mock
	private MessageHelper messageHelper;
	@Mock
	private MessageSource messageSource;
	@Mock
	private CountryService countryService; // Injected by Spring

	private CityFactoryForTest cityFactoryForTest = new CityFactoryForTest();
	private CountryFactoryForTest countryFactoryForTest = new CountryFactoryForTest();

	List<Country> countrys = new ArrayList<Country>();

	private void givenPopulateModel() {
		Country country1 = countryFactoryForTest.newCountry();
		Country country2 = countryFactoryForTest.newCountry();
		List<Country> countrys = new ArrayList<Country>();
		countrys.add(country1);
		countrys.add(country2);
		when(countryService.findAll()).thenReturn(countrys);

	}

	@Test
	public void list() {
		// Given
		Model model = new ExtendedModelMap();
		
		List<City> list = new ArrayList<City>();
		when(cityService.findAll()).thenReturn(list);
		
		// When
		String viewName = cityController.list(model);
		
		// Then
		assertEquals("city/list", viewName);
		Map<String,?> modelMap = model.asMap();
		assertEquals(list, modelMap.get("list"));
	}
	
	@Test
	public void formForCreate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		// When
		String viewName = cityController.formForCreate(model);
		
		// Then
		assertEquals("city/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertNull(((City)modelMap.get("city")).getId());
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/city/create", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<CountryListItem> countryListItems = (List<CountryListItem>) modelMap.get("listOfCountryItems");
		assertEquals(2, countryListItems.size());
		
	}
	
	@Test
	public void formForUpdate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		City city = cityFactoryForTest.newCity();
		Integer id = city.getId();
		when(cityService.findById(id)).thenReturn(city);
		
		// When
		String viewName = cityController.formForUpdate(model, id);
		
		// Then
		assertEquals("city/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertEquals(city, (City) modelMap.get("city"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/city/update", modelMap.get("saveAction"));
		
		List<CountryListItem> countryListItems = (List<CountryListItem>) modelMap.get("listOfCountryItems");
		assertEquals(2, countryListItems.size());
		
	}
	
	@Test
	public void createOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		City city = cityFactoryForTest.newCity();
		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		City cityCreated = new City();
		when(cityService.create(city)).thenReturn(cityCreated); 
		
		// When
		String viewName = cityController.create(city, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/city/form/"+city.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(cityCreated, (City) modelMap.get("city"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void createBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		City city = cityFactoryForTest.newCity();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = cityController.create(city, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("city/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(city, (City) modelMap.get("city"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/city/create", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<CountryListItem> countryListItems = (List<CountryListItem>) modelMap.get("listOfCountryItems");
		assertEquals(2, countryListItems.size());
		
	}

	@Test
	public void createException() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(false);

		City city = cityFactoryForTest.newCity();
		
		Exception exception = new RuntimeException("test exception");
		when(cityService.create(city)).thenThrow(exception);
		
		// When
		String viewName = cityController.create(city, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("city/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(city, (City) modelMap.get("city"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/city/create", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "city.error.create", exception);
		
		@SuppressWarnings("unchecked")
		List<CountryListItem> countryListItems = (List<CountryListItem>) modelMap.get("listOfCountryItems");
		assertEquals(2, countryListItems.size());
		
	}

	@Test
	public void updateOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		City city = cityFactoryForTest.newCity();
		Integer id = city.getId();

		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		City citySaved = new City();
		citySaved.setId(id);
		when(cityService.update(city)).thenReturn(citySaved); 
		
		// When
		String viewName = cityController.update(city, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/city/form/"+city.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(citySaved, (City) modelMap.get("city"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void updateBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		City city = cityFactoryForTest.newCity();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = cityController.update(city, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("city/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(city, (City) modelMap.get("city"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/city/update", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<CountryListItem> countryListItems = (List<CountryListItem>) modelMap.get("listOfCountryItems");
		assertEquals(2, countryListItems.size());
		
	}

	@Test
	public void updateException() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(false);

		City city = cityFactoryForTest.newCity();
		
		Exception exception = new RuntimeException("test exception");
		when(cityService.update(city)).thenThrow(exception);
		
		// When
		String viewName = cityController.update(city, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("city/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(city, (City) modelMap.get("city"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/city/update", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "city.error.update", exception);
		
		@SuppressWarnings("unchecked")
		List<CountryListItem> countryListItems = (List<CountryListItem>) modelMap.get("listOfCountryItems");
		assertEquals(2, countryListItems.size());
		
	}
	

	@Test
	public void deleteOK() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		City city = cityFactoryForTest.newCity();
		Integer id = city.getId();
		
		// When
		String viewName = cityController.delete(redirectAttributes, id);
		
		// Then
		verify(cityService).delete(id);
		assertEquals("redirect:/city", viewName);
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"delete.ok"));
	}

	@Test
	public void deleteException() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		City city = cityFactoryForTest.newCity();
		Integer id = city.getId();
		
		Exception exception = new RuntimeException("test exception");
		doThrow(exception).when(cityService).delete(id);
		
		// When
		String viewName = cityController.delete(redirectAttributes, id);
		
		// Then
		verify(cityService).delete(id);
		assertEquals("redirect:/city", viewName);
		Mockito.verify(messageHelper).addException(redirectAttributes, "city.error.delete", exception);
	}
	
	
}
