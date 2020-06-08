package org.demo.test;

import org.demo.bean.City;

public class CityFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public City newCity() {

		Integer id = mockValues.nextInteger();

		City city = new City();
		city.setId(id);
		return city;
	}
	
}
