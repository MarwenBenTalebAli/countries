package org.demo.test;

import org.demo.bean.Countrylanguage;

public class CountrylanguageFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public Countrylanguage newCountrylanguage() {

		String countrycode = mockValues.nextString(3);
		String language = mockValues.nextString(30);

		Countrylanguage countrylanguage = new Countrylanguage();
		countrylanguage.setCountrycode(countrycode);
		countrylanguage.setLanguage(language);
		return countrylanguage;
	}
	
}
