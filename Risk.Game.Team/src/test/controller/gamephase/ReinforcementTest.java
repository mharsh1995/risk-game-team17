package com.risk.team.controller.gamephase;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import com.risk.team.model.*;

/**
 * Test class for Reinforcement.
 * 
 * @author Jenny
 *
 */

public class ReinforcementTest {

	/** Object for Reinforcement class */
	private Reinforcement reinforcement;

	/** Object for Player class */
	private Player player;	

	/** Object for Country class */
	private Country country1,country2,country3,country4;

	/** Object for Continent class */
	private Continent continent;

	/**ArrayList of countries owned by player*/
	private ArrayList<Country> playerOwnedCountries;

	/** ArrayList of countries in that continent*/
	private ArrayList<Country> continentCountryList;

	/**
	 * Set up the initial objects for Reinforcement Phase
	 * 
	 */
	@Before
	public void initialize() {
		reinforcement = new Reinforcement();
		playerOwnedCountries = new ArrayList<Country>();
		continentCountryList = new ArrayList<Country>();

		country1 = new Country("Country1");
		country2 = new Country("Country2");
		country3 = new Country("Country3");

		playerOwnedCountries.add(country1);
		continentCountryList.add(country1);

		playerOwnedCountries.add(country2);
		continentCountryList.add(country2);

		playerOwnedCountries.add(country3);
		continentCountryList.add(country3);

		player = new Player();
		player.setMyCountries(playerOwnedCountries);
		continent = new Continent("North America", 2);

		continent.setListOfCountries(continentCountryList);
	}

	/**
	 * Test to validate number of armies when player does not own the continent
	 */
	@Test
	public void findNoOfArmiesWhenPlayerDoesNotOwnContinentTest() {
		country4 = new Country("Country4");
		continentCountryList.add(country4);
		assertEquals(3, reinforcement.assignArmies(player, continent));
	}

	/**
	 * Test to validate number of armies when the whole continent is owned by the player
	 */
	@Test
	public void findNoOfArmiesWhenPlayerOwnsContinentTest() {		
		assertEquals(continent.getControlValue(), reinforcement.assignArmies(player, continent));

	}

}
