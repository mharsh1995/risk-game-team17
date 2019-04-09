package com.risk.team.model;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.risk.team.model.Continent;
import com.risk.team.model.Country;

/**
 * This class contains test methods for continent class
 * @author Jenny Pujara
 *
 */

public class ContinentTest {
	
	/** Object for Continent Class */
    private Continent continent,continent2;
    
    /** Name of the continent. */
	private String continentName;
	
	/** Objects for Country Class */
	private Country country1, country2, country3;
	
	/** ArrayList to store list of countries */
	private ArrayList<Country> listOfCountries;
    
    @Before
    public void initialize(){
    	continent = new Continent("Asia",4); 
    	continent2 = new Continent("Europe",2);
    	
    	continentName = "Asia";
    	  
    	country1 = new Country("India");
  		country2 = new Country("China");
  		country3 = new Country("Indonesia");
  		
  		listOfCountries = new ArrayList<>();
  		listOfCountries.add(country1);
		listOfCountries.add(country2);
		listOfCountries.add(country3);
		
		continent.setListOfCountries(listOfCountries);
    }

    /** test method to set continent name 
     */
	@Test
	public void setCotinentNameTest() {
		continent.setName(continentName);
		assertEquals("Asia", continentName);
	}

	/** test method to delete country from list of countries 
     */
	@Test
	public void deleteCountryTest() {
		continent.deleteCountry(country2);
		assertNotNull(listOfCountries);
	}
	
	 /** test method to check if names of two continents are same.
     */
	@Test
	public void checkIfContinentsSameTest() {		
		assertTrue(continent.equals(continent));
	}
	
	/** test method to check if names of two continents are not same.
     */
	@Test
	public void checkIfContinentsNotSameTest() {		
		assertFalse(continent.equals(continent2));
	}
	
	/** test method to check if object is not of type Continent.
     */
	@Test
	public void checkIfNotContinentsTypeTest() {		
		assertFalse(continent.equals("Asia"));
	}
	
	/** test method to change the continent object to continent name.
     */
	@Test
	public void convertToContinentNameTest() {		
		assertEquals("Asia", continent.toString());;
	}
}
