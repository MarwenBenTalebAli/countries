package org.demo.test;

import org.demo.bean.jpa.CityEntity;

public class CityEntityFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public CityEntity newCityEntity() {

		Integer id = mockValues.nextInteger();

		CityEntity cityEntity = new CityEntity();
		cityEntity.setId(id);
		return cityEntity;
	}
	
}
