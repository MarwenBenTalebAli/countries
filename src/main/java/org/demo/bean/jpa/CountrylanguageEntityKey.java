/*
 * Created on 2020-05-29 ( Time 14:26:38 )
 * Generated by Telosys Tools Generator ( version 3.1.2 )
 */
package org.demo.bean.jpa;
import java.io.Serializable;

import javax.persistence.*;

/**
 * Composite primary key for entity "CountrylanguageEntity" ( stored in table "countrylanguage" )
 *
 * @author Telosys Tools Generator
 *
 */
 @Embeddable
public class CountrylanguageEntityKey implements Serializable {

    private static final long serialVersionUID = 1L;

    //----------------------------------------------------------------------
    // ENTITY KEY ATTRIBUTES 
    //----------------------------------------------------------------------
    @Column(name="CountryCode", nullable=false, length=3)
    private String     countrycode  ;
    
    @Column(name="Language", nullable=false, length=30)
    private String     language     ;
    

    //----------------------------------------------------------------------
    // CONSTRUCTORS
    //----------------------------------------------------------------------
    public CountrylanguageEntityKey() {
        super();
    }

    public CountrylanguageEntityKey( String countrycode, String language ) {
        super();
        this.countrycode = countrycode ;
        this.language = language ;
    }
    
    //----------------------------------------------------------------------
    // GETTERS & SETTERS FOR KEY FIELDS
    //----------------------------------------------------------------------
    public void setCountrycode( String value ) {
        this.countrycode = value;
    }
    public String getCountrycode() {
        return this.countrycode;
    }

    public void setLanguage( String value ) {
        this.language = value;
    }
    public String getLanguage() {
        return this.language;
    }


    //----------------------------------------------------------------------
    // equals METHOD
    //----------------------------------------------------------------------
	public boolean equals(Object obj) { 
		if ( this == obj ) return true ; 
		if ( obj == null ) return false ;
		if ( this.getClass() != obj.getClass() ) return false ; 
		CountrylanguageEntityKey other = (CountrylanguageEntityKey) obj; 
		//--- Attribute countrycode
		if ( countrycode == null ) { 
			if ( other.countrycode != null ) 
				return false ; 
		} else if ( ! countrycode.equals(other.countrycode) ) 
			return false ; 
		//--- Attribute language
		if ( language == null ) { 
			if ( other.language != null ) 
				return false ; 
		} else if ( ! language.equals(other.language) ) 
			return false ; 
		return true; 
	} 


    //----------------------------------------------------------------------
    // hashCode METHOD
    //----------------------------------------------------------------------
	public int hashCode() { 
		final int prime = 31; 
		int result = 1; 
		
		//--- Attribute countrycode
		result = prime * result + ((countrycode == null) ? 0 : countrycode.hashCode() ) ; 
		//--- Attribute language
		result = prime * result + ((language == null) ? 0 : language.hashCode() ) ; 
		
		return result; 
	} 


    //----------------------------------------------------------------------
    // toString METHOD
    //----------------------------------------------------------------------
    public String toString() {
		StringBuffer sb = new StringBuffer(); 
		sb.append(countrycode); 
		sb.append("|"); 
		sb.append(language); 
        return sb.toString();
    }
}
