package com.risk.team.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.risk.team.model.Country;

/**
 * Test class for Country class.
 * 
 * @author Jenny Pujara
 *
 */

public class CountryTest {
	
	/** Name of country. */
	private String countryName;
	
	/** Objects for Country Class */
	private Country country;
	
	/** X dimension */
	private String xValue;
	
	 @Before
	    public void initialize(){
		 
		 country = new Country("India");
		 countryName = "China";
		 
		 country.setxValue("X-Dimesnion");
		 country.setyValue("Y-Dimesnion");
	 }
	 
	/**test method to set country name 
	 */
	@Test
	public void setCountryNameTest() {
		country.setName(countryName);
		assertNotSame("India",country.getName());
	}
	
	/**test method to get the x dimension
	 */
	@Test
	public void checkxValueTest() {
		assertNotNull(country.getxValue());
	}
	
	/**test method to get the y dimension
	 */
	@Test
	public void checkyValueTest() {
		assertNotNull(country.getyValue());
	}
	
	/** test method to check if object is not of type Continent.
     */
	@Test
	public void checkIfNotCountriesTypeTest() {		
		assertFalse(country.equals("Asia"));
	}
}
