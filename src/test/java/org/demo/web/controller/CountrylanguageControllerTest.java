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
import org.demo.bean.Countrylanguage;
import org.demo.bean.Country;
import org.demo.test.CountrylanguageFactoryForTest;
import org.demo.test.CountryFactoryForTest;

//--- Services 
import org.demo.business.service.CountrylanguageService;
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
public class CountrylanguageControllerTest {
	
	@InjectMocks
	private CountrylanguageController countrylanguageController;
	@Mock
	private CountrylanguageService countrylanguageService;
	@Mock
	private MessageHelper messageHelper;
	@Mock
	private MessageSource messageSource;
	@Mock
	private CountryService countryService; // Injected by Spring

	private CountrylanguageFactoryForTest countrylanguageFactoryForTest = new CountrylanguageFactoryForTest();
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
		
		List<Countrylanguage> list = new ArrayList<Countrylanguage>();
		when(countrylanguageService.findAll()).thenReturn(list);
		
		// When
		String viewName = countrylanguageController.list(model);
		
		// Then
		assertEquals("countrylanguage/list", viewName);
		Map<String,?> modelMap = model.asMap();
		assertEquals(list, modelMap.get("list"));
	}
	
	@Test
	public void formForCreate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		// When
		String viewName = countrylanguageController.formForCreate(model);
		
		// Then
		assertEquals("countrylanguage/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertNull(((Countrylanguage)modelMap.get("countrylanguage")).getCountrycode());
		assertNull(((Countrylanguage)modelMap.get("countrylanguage")).getLanguage());
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/countrylanguage/create", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<CountryListItem> countryListItems = (List<CountryListItem>) modelMap.get("listOfCountryItems");
		assertEquals(2, countryListItems.size());
		
	}
	
	@Test
	public void formForUpdate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Countrylanguage countrylanguage = countrylanguageFactoryForTest.newCountrylanguage();
		String countrycode = countrylanguage.getCountrycode();
		String language = countrylanguage.getLanguage();
		when(countrylanguageService.findById(countrycode, language)).thenReturn(countrylanguage);
		
		// When
		String viewName = countrylanguageController.formForUpdate(model, countrycode, language);
		
		// Then
		assertEquals("countrylanguage/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertEquals(countrylanguage, (Countrylanguage) modelMap.get("countrylanguage"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/countrylanguage/update", modelMap.get("saveAction"));
		
	}
	
	@Test
	public void createOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		Countrylanguage countrylanguage = countrylanguageFactoryForTest.newCountrylanguage();
		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		Countrylanguage countrylanguageCreated = new Countrylanguage();
		when(countrylanguageService.create(countrylanguage)).thenReturn(countrylanguageCreated); 
		
		// When
		String viewName = countrylanguageController.create(countrylanguage, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/countrylanguage/form/"+countrylanguage.getCountrycode()+"/"+countrylanguage.getLanguage(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(countrylanguageCreated, (Countrylanguage) modelMap.get("countrylanguage"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void createBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Countrylanguage countrylanguage = countrylanguageFactoryForTest.newCountrylanguage();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = countrylanguageController.create(countrylanguage, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("countrylanguage/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(countrylanguage, (Countrylanguage) modelMap.get("countrylanguage"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/countrylanguage/create", modelMap.get("saveAction"));
		
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

		Countrylanguage countrylanguage = countrylanguageFactoryForTest.newCountrylanguage();
		
		Exception exception = new RuntimeException("test exception");
		when(countrylanguageService.create(countrylanguage)).thenThrow(exception);
		
		// When
		String viewName = countrylanguageController.create(countrylanguage, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("countrylanguage/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(countrylanguage, (Countrylanguage) modelMap.get("countrylanguage"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/countrylanguage/create", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "countrylanguage.error.create", exception);
		
		@SuppressWarnings("unchecked")
		List<CountryListItem> countryListItems = (List<CountryListItem>) modelMap.get("listOfCountryItems");
		assertEquals(2, countryListItems.size());
		
	}

	@Test
	public void updateOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		Countrylanguage countrylanguage = countrylanguageFactoryForTest.newCountrylanguage();
		String countrycode = countrylanguage.getCountrycode();
		String language = countrylanguage.getLanguage();

		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		Countrylanguage countrylanguageSaved = new Countrylanguage();
		countrylanguageSaved.setCountrycode(countrycode);
		countrylanguageSaved.setLanguage(language);
		when(countrylanguageService.update(countrylanguage)).thenReturn(countrylanguageSaved); 
		
		// When
		String viewName = countrylanguageController.update(countrylanguage, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/countrylanguage/form/"+countrylanguage.getCountrycode()+"/"+countrylanguage.getLanguage(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(countrylanguageSaved, (Countrylanguage) modelMap.get("countrylanguage"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void updateBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Countrylanguage countrylanguage = countrylanguageFactoryForTest.newCountrylanguage();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = countrylanguageController.update(countrylanguage, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("countrylanguage/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(countrylanguage, (Countrylanguage) modelMap.get("countrylanguage"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/countrylanguage/update", modelMap.get("saveAction"));
		
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

		Countrylanguage countrylanguage = countrylanguageFactoryForTest.newCountrylanguage();
		
		Exception exception = new RuntimeException("test exception");
		when(countrylanguageService.update(countrylanguage)).thenThrow(exception);
		
		// When
		String viewName = countrylanguageController.update(countrylanguage, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("countrylanguage/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(countrylanguage, (Countrylanguage) modelMap.get("countrylanguage"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/countrylanguage/update", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "countrylanguage.error.update", exception);
		
	}
	

	@Test
	public void deleteOK() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		Countrylanguage countrylanguage = countrylanguageFactoryForTest.newCountrylanguage();
		String countrycode = countrylanguage.getCountrycode();
		String language = countrylanguage.getLanguage();
		
		// When
		String viewName = countrylanguageController.delete(redirectAttributes, countrycode, language);
		
		// Then
		verify(countrylanguageService).delete(countrycode, language);
		assertEquals("redirect:/countrylanguage", viewName);
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"delete.ok"));
	}

	@Test
	public void deleteException() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		Countrylanguage countrylanguage = countrylanguageFactoryForTest.newCountrylanguage();
		String countrycode = countrylanguage.getCountrycode();
		String language = countrylanguage.getLanguage();
		
		Exception exception = new RuntimeException("test exception");
		doThrow(exception).when(countrylanguageService).delete(countrycode, language);
		
		// When
		String viewName = countrylanguageController.delete(redirectAttributes, countrycode, language);
		
		// Then
		verify(countrylanguageService).delete(countrycode, language);
		assertEquals("redirect:/countrylanguage", viewName);
		Mockito.verify(messageHelper).addException(redirectAttributes, "countrylanguage.error.delete", exception);
	}
	
	
}
