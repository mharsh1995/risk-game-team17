package com.risk.team.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.risk.team.model.Country;
import com.risk.team.model.Player;
import com.risk.team.services.RiskGraphConnected;
/**
 * Test Class for MapValidation
 *
 * @author Jenny Pujara
 * 
 * @version 3.0.0
 *
 */
public class RiskConnectedGraphTest {

	/**Objects for Player Class */
	private Player player;

	/** Object for RiskGraphConnected Class */
	private RiskGraphConnected riskGraphConnected;

	/** Object for RiskGraphConnected Class */
	private RiskGraphConnected connectedGraph1, connectedGraph2;

	/** Objects for Country Class */
	private Country country0,country1, country2, country3, country4, country5, country6;

	/** Set to store countries */
	private Set<Country> countrySet, countrySet1, countrySet2;

	/** ArrayList to store adjacent countries */
	private ArrayList<Country> adjacentCountries0;
	private ArrayList<Country> adjacentCountries1;
	private ArrayList<Country> adjacentCountries2;
	private ArrayList<Country> adjacentCountries3;

	/**ArrayList for countries owned by player */
	private ArrayList<Country> playerOwnedCountries;

	/**HashSet for countries owned by player */
	private HashSet<Country> playerOwnedCountriesSet;

	/**
	 * Set up the initial objects for RiskGraphConnected
	 * 
	 */
	@Before
	public void initialize() {

		country0= new Country("Australia");
		country1= new Country("India");
		country2 = new Country("China");
		country3 =new Country("Indonesia");
		country4 =new Country("Japan");
		country5 =new Country("Russia");
		country6 =new Country("Srilanka");

		countrySet = new HashSet<Country>();
		countrySet.add(country1);
		countrySet.add(country2);
		countrySet.add(country3);

		countrySet1 = new HashSet<Country>();
		countrySet1.add(country0);
		countrySet1.add(country3);

		countrySet2 = new HashSet<Country>();
		countrySet2.add(country0);
		countrySet2.add(country4);
		countrySet2.add(country5);		

		adjacentCountries0 = new ArrayList<>();
		adjacentCountries1 = new ArrayList<>();
		adjacentCountries2 = new ArrayList<>();
		adjacentCountries3 = new ArrayList<>();

		player= new Player();
		playerOwnedCountries = new ArrayList<Country>();

		adjacentCountries0.add(country0);
		adjacentCountries0.add(country4);
		adjacentCountries0.add(country5);

		adjacentCountries1.add(country2);
		adjacentCountries1.add(country3);

		adjacentCountries2.add(country1);
		adjacentCountries2.add(country3);

		adjacentCountries3.add(country1);
		adjacentCountries3.add(country2);

		riskGraphConnected = new RiskGraphConnected(countrySet);
		country1.setAdjacentCountries(adjacentCountries1);
		country2.setAdjacentCountries(adjacentCountries2);
		country3.setAdjacentCountries(adjacentCountries3);

		country0.setAdjacentCountries(adjacentCountries0);
		country4.setAdjacentCountries(adjacentCountries0);
		country5.setAdjacentCountries(adjacentCountries0);

		connectedGraph1 = new RiskGraphConnected(countrySet1);

		playerOwnedCountries.add(country0);
		playerOwnedCountries.add(country4);
		playerOwnedCountries.add(country5);
		player.setPlayerCountries(playerOwnedCountries);

	}

	/**
	 * Test method to check if a Graph is connected
	 */
	@Test
	public void isConnectedTest() {

		assertTrue(riskGraphConnected.isConnected());

	}

	/**
	 * Test method to check if a path exists between countries in fortification phase
	 */
	@Test
	public void ifPathExistsBetweenCountriesTest() {
		playerOwnedCountriesSet = new HashSet<Country>(playerOwnedCountries);
		connectedGraph2 = new RiskGraphConnected(countrySet2);	
		assertTrue(connectedGraph2.ifPathExists(country0,country5,playerOwnedCountriesSet));
	}

	/**
	 * Test method to check if a path does not exist between countries in fortification phase
	 */
	@Test
	public void ifPathDoesNotExistBetweenCountriesTest() {
		countrySet2.add(country6);
		connectedGraph2 = new RiskGraphConnected(countrySet2);
		playerOwnedCountries.add(country6);
		playerOwnedCountriesSet = new HashSet<Country>(playerOwnedCountries);	
		assertFalse(connectedGraph2.ifPathExists(country0,country6,playerOwnedCountriesSet));
	}



	/**
	 * Test method to check connectivity of subGraph
	 */
	@Test
	public void isConnectedSubGraphTest(){

		assertTrue(riskGraphConnected.isConnectedSubGraph());
	}

	/**
	 * Test method to check non-connectivity of subGraph
	 */
	@Test
	public void isConnectedSubGraphFalseTest(){

		assertEquals(false,connectedGraph1.isConnectedSubGraph());
	}

}
