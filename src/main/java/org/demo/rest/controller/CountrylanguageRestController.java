/*
 * Created on 2020-05-29 ( Time 16:10:10 )
 * Generated by Telosys Tools Generator ( version 3.1.2 )
 */
package org.demo.rest.controller;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.demo.bean.Countrylanguage;
import org.demo.business.service.CountrylanguageService;
import org.demo.web.listitem.CountrylanguageListItem;

/**
 * Spring MVC controller for 'Countrylanguage' management.
 */
@Controller
public class CountrylanguageRestController {

	@Resource
	private CountrylanguageService countrylanguageService;
	
	@RequestMapping( value="/items/countrylanguage",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<CountrylanguageListItem> findAllAsListItems() {
		List<Countrylanguage> list = countrylanguageService.findAll();
		List<CountrylanguageListItem> items = new LinkedList<CountrylanguageListItem>();
		for ( Countrylanguage countrylanguage : list ) {
			items.add(new CountrylanguageListItem( countrylanguage ) );
		}
		return items;
	}
	
	@RequestMapping( value="/countrylanguage",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<Countrylanguage> findAll() {
		return countrylanguageService.findAll();
	}

	@RequestMapping( value="/countrylanguage/{countrycode}/{language}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Countrylanguage findOne(@PathVariable("countrycode") String countrycode, @PathVariable("language") String language) {
		return countrylanguageService.findById(countrycode, language);
	}
	
	@RequestMapping( value="/countrylanguage",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Countrylanguage create(@RequestBody Countrylanguage countrylanguage) {
		return countrylanguageService.create(countrylanguage);
	}

	@RequestMapping( value="/countrylanguage/{countrycode}/{language}",
			method = RequestMethod.PUT,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Countrylanguage update(@PathVariable("countrycode") String countrycode, @PathVariable("language") String language, @RequestBody Countrylanguage countrylanguage) {
		return countrylanguageService.update(countrylanguage);
	}

	@RequestMapping( value="/countrylanguage/{countrycode}/{language}",
			method = RequestMethod.DELETE,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public void delete(@PathVariable("countrycode") String countrycode, @PathVariable("language") String language) {
		countrylanguageService.delete(countrycode, language);
	}
	
}
