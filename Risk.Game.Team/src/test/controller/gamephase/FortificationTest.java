package com.risk.team.controller.gamephase;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;
import com.risk.team.model.*;

/**
 * Test class for Fortification.
 * 
 * @author Kartika Patil
 *
 */

public class FortificationTest {

	/**Object for Fortification Class */
	private Fortification fortification;

	/**Objects for Country Class */
	private Country country1, country2, country3;

	/**Objects for Player Class */
	private Player player;	

	/**Integer variable for armies count*/
	private int numberOfArmies;	

	/**ArrayList for adjacent countries */
	private ArrayList<Country> adjacentCountries1,adjacentCountries2;

	/**ArrayList for countries owned by player */
	private ArrayList<Country> playerOwnedCountries;

	/**Set of all countries in the Map Graph */
	HashSet<Country> allCountries;

	/**
	 * Set up the initial objects for Fortification Phase
	 * 
	 */
	@Before	
	public void initialize() {
		player = new Player();
		country1 = new Country("C1");
		country2 = new Country("C2");
		country3 = new Country("C3");
		fortification = new Fortification();
		adjacentCountries1 = new ArrayList<Country>();
		adjacentCountries2 = new ArrayList<Country>();
		playerOwnedCountries = new ArrayList<Country>();
		allCountries = new HashSet<Country>();

		country1.setNoOfArmies(8);
		country2.setNoOfArmies(3);	

		allCountries.add(country1);
		allCountries.add(country2);
		allCountries.add(country3);

		playerOwnedCountries.add(country1);	
		playerOwnedCountries.add(country2);

		adjacentCountries1.add(country3);
		adjacentCountries2.add(country2);

		country1.setAdjacentCountries(adjacentCountries1);
		country3.setAdjacentCountries(adjacentCountries2);

		player.setMyCountries(playerOwnedCountries);
		numberOfArmies = 1;
	}

	/**if path doesn't exist */
	@Test 
	public void moveArmiesIfPathDoesNotExist() {
		fortification.moveArmies(country1 ,country2, numberOfArmies,player,allCountries);	
		assertEquals(8,country1.getNoOfArmies());
		assertEquals(3,country2.getNoOfArmies());
	}

	/**if path exists */
	@Test
	public void moveArmiesIfPathExists() {
		playerOwnedCountries.add(country3);
		fortification.moveArmies(country1 ,country2, numberOfArmies,player,allCountries);	
		assertEquals(7,country1.getNoOfArmies());
		assertEquals(4,country2.getNoOfArmies());
	}
}
