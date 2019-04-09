package com.risk.team.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import com.risk.team.model.Continent;
import com.risk.team.model.Country;
import com.risk.team.services.RiskMapGraph;
import com.risk.team.services.RiskMapVerify;

/**
 * Test Class for Map graph class.
 *
 * @author Dhaval Desai
 *
 */
public class RiskMapGraphTest {
	
	/**variable for total no of countries */
	private int totalCountries;

	/** Object for Continent class */
	private Continent continent;

	private RiskMapVerify riskMapVerify;

	/** Object for RiskMapGraph class */
	private RiskMapGraph riskMapGraph;

	/** Object for Country class */
	private Country country;

	private Country country2, country3, country4;

	/** Adjacent countries */
	private HashMap<Country, ArrayList<Country>> adjacentCountries,adCountryList;

	private HashMap<String,Continent> continents;

	private HashMap<String,Continent> countrySet;

	/** List of countries */
	private ArrayList<Country> countryList;
	
	/** List of all countries in the graph */
	private HashMap<String, Country> allCountries;
	
	/**
	 * Set up the initial objects for RiskMapGraph
	 * 
	 */
	@Before
	public void initialize() {
		System.out.println("Inside Initialize");
		totalCountries = 0;
		continent = new Continent("Asia", 2);
		continents = new HashMap<>();
		countrySet = new HashMap<>();
		
		riskMapGraph = new RiskMapGraph();
		riskMapVerify = new RiskMapVerify();
		
		country = new Country("India");
		country2 = new Country("China");
		country3 = new Country("Japan");
		country4 = new Country("Srilanka");
		
		country.setContinent("Asia");
		country2.setContinent("Asia");
		country3.setContinent("Asia");
		
		countryList = new ArrayList<Country>();
		countryList.add(country);
		countryList.add(country2);
		
		adjacentCountries = new HashMap<Country, ArrayList<Country>>();
		adjacentCountries.put(country, countryList);
		adjacentCountries.put(country2,countryList);
		
		adCountryList = new HashMap<Country, ArrayList<Country>>();
		adCountryList.put(country, countryList);		
		
		allCountries = new HashMap<String, Country>();
		
	}
	
	/**
	 * Test method for testing if the two countries are adjacent
	 * 
	 */
	@Test
	public void isCountriesAdjacentTest() {
		riskMapGraph.setAdjacentCountries(adjacentCountries);
		assertTrue(riskMapGraph.checkAdjacencyOfCountries(country, country2));
	}
	
	/**
	 * Test method for testing if the two countries are not adjacent
	 * 
	 */
	@Test
	public void isCountriesNotAdjacentTest() {
		riskMapGraph.setAdjacentCountries(adCountryList);
		assertFalse(riskMapGraph.checkAdjacencyOfCountries(country, country3));
	}
	
	/**
	 * Test method for setting total no of countries
	 * 
	 */
	@Test
	public void totalCountriesCountTest() {
		totalCountries = 5;
		riskMapGraph.setCountOfCountries(totalCountries);
		assertNotSame(10,riskMapGraph.getCountOfCountries());
	}

	/**
	 * Test method for testing adding a continent from the map
	 * 
	 */
	@Test
	public void addContinentTest() {
		riskMapGraph.addContinent(continent);
		assertEquals(riskMapGraph.getContinents().get("Asia").getControlValue(), continent.getControlValue());
	}

	/**
	 * Test method for removing a continent from the map
	 *
	 */
	@Test
	public void removeContinentTest() {
		continents.put("Asia",continent);
		continent.setListOfCountries(countryList);
		riskMapGraph.setAdjacentCountries(adjacentCountries);
		riskMapGraph.setContinents(continents);
		assertTrue(riskMapGraph.removeContinent(continent));
	}
	
	/**
	 * Test method for removing a continent from the map
	 *
	 */
	@Test
	public void removeContinentTest2() {		
		assertFalse(riskMapGraph.removeContinent(continent));
	}
	
	/**
	 * Test method for adding link between source and destination country
	 *
	 */
	@Test
	public void addLinkBetweenCountriesTest() {
		riskMapGraph.setAdjacentCountries(adjacentCountries);
		riskMapGraph.addLinkBetweenCountries(country, country2);
		assertNotNull(adjacentCountries);
	}
	
	/**
	 * Test method for deleting link between source and destination country
	 *
	 */
	@Test
	public void deleteLinkBetweenCountriesTest() {
		riskMapGraph.setAdjacentCountries(adjacentCountries);
		riskMapGraph.deleteLinkBetweenCountries(country, country2);
		assertNotNull(adjacentCountries);
	}
	

	/**
	 * Test method for testing removal of country from map 
	 */
	@Test
	public void removeCountryTest(){
		countrySet.put("India",continent);
		countrySet.put("China",continent);
		continents.put("Asia",continent);
		continent.setListOfCountries(countryList);
		riskMapGraph.setAdjacentCountries(adjacentCountries);
		riskMapGraph.setContinents(continents);
		assertEquals(true,riskMapGraph.removeCountry(country));
	}
	
	/**
	 * Test method for testing removal of country from map 
	 */
	@Test
	public void removeCountryTest2(){
		riskMapGraph.addContinent(continent);		
		riskMapGraph.setAdjacentCountries(adjacentCountries);	
		assertFalse(riskMapGraph.removeCountry(country3));
	}
	
	/**
	 * Test method to add new country in the country list  
	 */
	@Test
	public void addCountryTest(){
		riskMapGraph.addContinent(continent);
		riskMapGraph.addCountry(country3);
		assertNotNull(allCountries);		
	}	
}
