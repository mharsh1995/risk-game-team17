package com.risk.team.controller;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.*;
import static org.junit.Assert.*;
import com.risk.team.controller.RiskMapGraph;
import com.risk.team.model.Continent;
import com.risk.team.model.Country;

/**
 * This class tests the RiskMapGraph class
 *
 * @author Dhaval Desai
 *
 * @version 1.0
 * 
 */
public class RiskMapGraphTest {

	/**Declaring object continent of type Continent */
	private Continent continent;

	/**Declaring Object riskMapGraph of type RiskMapGraph*/
	private RiskMapGraph riskMapGraph;

	/**Declaring object country of type Country*/
	private Country country;

	/**Declaring hashmap countryList*/
	private HashMap<Country, ArrayList<Country>> countryList;

	/**Declaring ArrayList list1,list2*/
	private ArrayList<Country> list1,list2;

	/**
	 * 
	 * Initializing the declared objects needed for the test functions.
	 * 
	 */
	@Before
	public void initialize() {
		continent = new Continent("Asia", 2);
		riskMapGraph = new RiskMapGraph();
		country = new Country("India");
		country.setContinent("Asia");
		list1 = new ArrayList<Country>();
		list2 = new ArrayList<Country>();
		list1.add(country);
		countryList = new HashMap<Country, ArrayList<Country>>();
		countryList.put(country, list1);
	}

	/**
	 * 
	 * This method tests addition of link between two countries.
	 * 
	 */
	@Test
	public void addLinkBetweenCountriesTest() {
		Country source=new Country("SriLanka");
		Country destination=new Country("Nepal");
		list2.add(source);
		countryList.put(source, list1);
		countryList.put(destination, list2);
		riskMapGraph.setAdjacentCountries(countryList);
		riskMapGraph.addLinkBetweenCountries(source, destination);

		assertTrue(riskMapGraph.checkAdjacencyOfCountries(source, destination));

	}

	/**
	 * 
	 * This method tests deletions of link between two countries.
	 * 
	 */
	@Test
	public void deleteLinkBetweenCountriesTest() {
		Country source=new Country("SriLanka");
		Country destination=new Country("Nepal");
		list2.add(source);
		countryList.put(source, list1);
		countryList.put(destination, list2);
		riskMapGraph.setAdjacentCountries(countryList);
		riskMapGraph.deleteLinkBetweenCountries(source, destination);

		assertFalse(riskMapGraph.checkAdjacencyOfCountries(source, destination));

	}

	/**
	 *
	 * This method tests whether the continent is sucessfully added or not.
	 * 
	 */
	@Test
	public void addContinentTest() {
		riskMapGraph.addContinent(continent);
		assertEquals(riskMapGraph.getContinents().get("Asia").getControlValue(), continent.getControlValue());
	}

	/**
	 * 
	 * This method tests whether the continent is sucessfully removed or not.
	 * 
	 */
	@Test
	public void removeContinentTest() {
		riskMapGraph.addContinent(continent);
		assertTrue(riskMapGraph.removeContinent(continent));
	}


}
