/*
 * Created on 2020-05-29 ( Time 16:10:09 )
 * Generated by Telosys Tools Generator ( version 3.1.2 )
 */
package org.demo.web.listitem;

import org.demo.bean.Countrylanguage;
import org.demo.web.common.ListItem;

public class CountrylanguageListItem implements ListItem {

	private final String value ;
	private final String label ;
	
	public CountrylanguageListItem(Countrylanguage countrylanguage) {
		super();

		this.value = ""
			 + countrylanguage.getCountrycode()
			 + "|"  + countrylanguage.getLanguage()
		;

		//TODO : Define here the attributes to be displayed as the label
		this.label = countrylanguage.toString();
	}

	@Override
	public String getValue() {
		return value;
	}

	@Override
	public String getLabel() {
		return label;
	}

}
