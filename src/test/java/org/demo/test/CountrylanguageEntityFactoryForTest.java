package org.demo.test;

import org.demo.bean.jpa.CountrylanguageEntity;

public class CountrylanguageEntityFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public CountrylanguageEntity newCountrylanguageEntity() {

		String countrycode = mockValues.nextString(3);
		String language = mockValues.nextString(30);

		CountrylanguageEntity countrylanguageEntity = new CountrylanguageEntity();
		countrylanguageEntity.setCountrycode(countrycode);
		countrylanguageEntity.setLanguage(language);
		return countrylanguageEntity;
	}
	
}
