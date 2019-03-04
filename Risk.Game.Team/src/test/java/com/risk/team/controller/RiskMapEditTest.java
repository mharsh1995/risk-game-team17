package com.risk.team.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.risk.team.controller.*;
import com.risk.team.model.*;

/**
 * 
 * Class for testing RiskMapEdit file and its containing functions.
 * 
 * @author Dhaval Desai
 * @author Kartika Patil
 * 
 */
public class RiskMapEditTest {

	/** Declaring Object riskMapVerify of type RiskMapVerify */
	private RiskMapVerify riskMapVerify;

	/** Declaring Object riskMapRW of type RiskMapRW */
	private RiskMapRW riskMapRW;

	/** Declaring Object riskMapEdit of type RiskMapEdit */
	private RiskMapEdit riskMapEdit;

	/** Declaring country1,country2,country3 objects of type Country */
	private Country country1, country2, country3;

	/** Declaring HashSet for storing Countries */
	private HashSet<Country> countries;

	/** Declaring Object continent of type Continent */
	private Continent continent;

	/** Declaring HashMap for storing countries of a continent */
	private HashMap<Continent, HashSet<Country>> countriesInContinent;

	/** Declaring HashMap for storing list of adjacent countries */
	private HashMap<Country, ArrayList<Country>> adjacentCountries;

	/** Declaring ArrayList to store list of countries */
	private ArrayList<Country> listOfCountries;

	/** Declaring HashMap to store continents */
	private HashMap<String, Continent> continents;

	/**
	 * 
	 * Initializing all declared objects
	 * 
	 */
	@Before
	public void initialize() {

		continents = new HashMap<String, Continent>();
		continent = new Continent("Asia", 2);
		listOfCountries = new ArrayList<>();
		countries = new HashSet<Country>();
		countriesInContinent = new HashMap<Continent, HashSet<Country>>();
		adjacentCountries = new HashMap<Country, ArrayList<Country>>();
		riskMapVerify = new RiskMapVerify();
		riskMapRW = new RiskMapRW(riskMapVerify);

		country1 = new Country("India");
		country2 = new Country("Nepal");
		country3 = new Country("Sri Lanka");

		listOfCountries.add(country1);
		countries.add(country1);

		continent.setListOfCountries(listOfCountries);
		countriesInContinent.put(continent, countries);

		adjacentCountries.put(country1, listOfCountries);
		adjacentCountries.put(country2, listOfCountries);
		adjacentCountries.put(country3, listOfCountries);

		continents.put("Asia", continent);
		riskMapRW.getMapGraph().setContinents(continents);
		riskMapRW.getMapGraph().setAdjacentCountries(adjacentCountries);

		riskMapEdit = new RiskMapEdit(riskMapRW);

	}

	/**
	 * 
	 * This method tests the createNewMap function.
	 * The arguement is given as true so it creates a new map 
	 * 
	 */

	@Ignore
	public void createEditMapTrueTest() {
		boolean expected= true;
		boolean actual= riskMapEdit.createEditMap(true);
		assertEquals(expected,actual);

	}


	/**
	 * 
	 * This method tests the createNewMap function.
	 * The argument is given as false so it edits an existing map. 
	 * 
	 */

	@Ignore
	public void createEditMapFalseTest() {
		boolean expected= false;
		boolean actual= riskMapEdit.createEditMap(false);
		assertEquals(expected,actual);

	}

	/**
	 * 
	 * This method tests minimum two countries in a particular continent.
	 * 
	 */
	@Test
	public void countCountriesInContinentNoTTest() {

		assertFalse(riskMapEdit.countCountriesInContinent());

	}

	/**
	 * 
	 * This method tests minimum two countries in a particular continent.
	 * 
	 */
	@Test
	public void countCountriesInContinentTest() {

		listOfCountries.add(country2);
		countries.add(country2);

		listOfCountries.add(country3);
		countries.add(country3);

		assertTrue(riskMapEdit.countCountriesInContinent());

	}

	/**
	 * 
	 * This method tests whether the country are adjacent to each other or not.
	 *  
	 */
	@Test
	public void checkAdjacencyNotTest() {


		assertFalse(riskMapEdit.checkAdjacency());
	}

	/**
	 * 
	 * This method tests whether the country are adjacent to each other or not.
	 *  
	 */
	@Test
	public void checkAdjacencyTest() {
		listOfCountries.add(country2);
		listOfCountries.add(country3);

		assertTrue(riskMapEdit.checkAdjacency());
	}
}
