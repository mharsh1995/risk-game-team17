package com.risk.team.services;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

import com.risk.team.model.Continent;
import com.risk.team.model.Country;
import com.risk.team.services.RiskMapEdit;
import com.risk.team.services.RiskMapRW;
import com.risk.team.services.RiskMapVerify;

/**
 * Test Class for RiskMapEdit
 *
 * @author Jenny Pujara
 *
 */
public class RiskMapEditTest {

	/** Object for RiskMapVerify Class */
	private RiskMapVerify riskMapVerify;

	/** Object for RiskMapRW Class */
	private RiskMapRW riskMapRW, mapObj;

	/** Object for RiskMapEdit Class */
	private RiskMapEdit riskMapEdit;

	/** Object for Continent Class */
	private Continent continent, continent2;

	/** Objects for Country Class */
	private Country country1, country2, country3,country4;

	/** ArrayList to store list of countries */
	private ArrayList<Country> listOfCountries,listOfCountries2;

	/** HashSet to store unique Countries */
	private HashSet<Country> countries;
	
	/** HashMap to store all continents */
	HashMap<String, Continent> continents;

	/** HashMap to store counties in a continent */
	private HashMap<Continent, HashSet<Country>> countriesInContinent;

	/** HashMap to store adjacent counties */
	private HashMap<Country, ArrayList<Country>> adjacentCountries;

	/**
	 * Setup initial objects for RiskMapEditTest
	 * 
	 */
	@Before
	public void initialize() {

		continent = new Continent("Asia", 2);
		continent2 = new Continent("Europe", 1);
		continents = new HashMap<>();
		listOfCountries = new ArrayList<>();
		listOfCountries2 = new ArrayList<>();

		country1 = new Country("India");
		country2 = new Country("China");
		country3 = new Country("Indonesia");
		country4 = new Country("Japan");

		listOfCountries.add(country1);
		listOfCountries.add(country2);
		listOfCountries.add(country3);
		
		listOfCountries2.add(country1);		

		continent.setListOfCountries(listOfCountries);
		continent2.setListOfCountries(listOfCountries2);

		countries = new HashSet<Country>();
		countries.add(country1);
		countries.add(country2);
		countries.add(country3);

		countriesInContinent = new HashMap<Continent, HashSet<Country>>();
		countriesInContinent.put(continent, countries);

		riskMapVerify = new RiskMapVerify();
		riskMapRW = new RiskMapRW(riskMapVerify);

		adjacentCountries = new HashMap<Country, ArrayList<Country>>();
		adjacentCountries.put(country1, listOfCountries);
		adjacentCountries.put(country2, listOfCountries);
		adjacentCountries.put(country3, listOfCountries);

		riskMapRW.getMapGraph().setAdjacentCountries(adjacentCountries);		

		riskMapEdit = new RiskMapEdit(riskMapRW);
		
		new RiskMapEdit();
		
		mapObj = riskMapEdit.getMapObj();	

	}

	/**
	 * Test method for checking minimum number of countries in a continent.
	 * 
	 */
	@Test
	public void checkMinimumCountriesInContinentTest() {

		assertTrue(riskMapEdit.countCountriesInContinent());

	}
	
	/**
	 * Test method for checking minimum number of countries in a continent.
	 * 
	 */
	@Test
	public void checkMinimumCountriesInContinentTest2() {
		continents.put("Europe", continent2);
		riskMapRW.getMapGraph().setContinents(continents);
		assertFalse(riskMapEdit.countCountriesInContinent());

	}

	/**
	 * Test method for checking if countries are adjacent.
	 * 
	 */
	@Test
	public void checkCountriesAdjacentTest() {

		assertTrue(riskMapEdit.checkAdjacency());

	}
	
}
