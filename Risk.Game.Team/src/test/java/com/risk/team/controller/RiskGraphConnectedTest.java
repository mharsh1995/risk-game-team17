package com.risk.team.controller;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.Before;
import org.junit.Ignore;

import com.risk.team.model.Country;
import com.risk.team.model.Player;
import com.risk.team.controller.RiskGraphConnected;

/**
 * Test Class for RiskMapVerify
 *
 * @author Jenny
 *
 */

public class RiskGraphConnectedTest {

	/**Objects for Player Class */
	private Player player;

	/**Object for ConnectedGraph Class*/
	private RiskGraphConnected connectedGraph;

	/** Objects for Country Class */
	private Country country1, country2, country3, country4;

	/**Set to store countries*/
	private Set<Country> countrySet;

	/**ArrayList to store adjacent countries*/
	private ArrayList<Country> adjacentCountries;

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

		country1= new Country("India");
		country2 = new Country("Russia");
		country3 = new Country("SriLanka");
		country4 = new Country("Japan");	

		player= new Player();
		playerOwnedCountries = new ArrayList<Country>();
		countrySet = new HashSet<Country>();
		adjacentCountries = new ArrayList<Country>();

		countrySet.add(country1);
		countrySet.add(country2);
		countrySet.add(country3);		

		connectedGraph = new RiskGraphConnected(countrySet);

		adjacentCountries.add(country1);
		adjacentCountries.add(country2);
		adjacentCountries.add(country3);

		playerOwnedCountries.add(country1);
		playerOwnedCountries.add(country2);
		playerOwnedCountries.add(country3);

		country1.setAdjacentCountries(adjacentCountries);
		country2.setAdjacentCountries(adjacentCountries);
		country3.setAdjacentCountries(adjacentCountries);	
		player.setMyCountries(playerOwnedCountries);
	}

	/**
	 * Test method to check if a Graph is connected
	 */
	@Test
	public void isGraphConnectedTest() {
		assertTrue(connectedGraph.isGraphConnected());
	}

	/**
	 * Test method to check if a Graph is not connected
	 */
	@Test
	public void isGraphNotConnectedTest() {
		countrySet.add(country4);
		connectedGraph = new RiskGraphConnected(countrySet);	
		assertFalse(connectedGraph.isGraphConnected());
	}


	/**
	 * Test method to check if a path exists between countries in fortification phase
	 */
	@Test
	public void ifPathExistsBetweenCountriesTest() {
		playerOwnedCountriesSet = new HashSet<Country>(playerOwnedCountries);
		connectedGraph = new RiskGraphConnected(countrySet);	
		assertTrue(connectedGraph.ifPathExists(country1,country2,playerOwnedCountriesSet));
	}

	/**
	 * Test method to check if a path does not exist between countries in fortification phase
	 */
	@Test
	public void ifPathDoesNotExistBetweenCountriesTest() {
		countrySet.add(country4);
		connectedGraph = new RiskGraphConnected(countrySet);
		playerOwnedCountries.add(country4);
		playerOwnedCountriesSet = new HashSet<Country>(playerOwnedCountries);	
		assertFalse(connectedGraph.ifPathExists(country3,country4,playerOwnedCountriesSet));
	}



}
