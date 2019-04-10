package com.risk.team.strategy;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.risk.team.model.Country;
import com.risk.team.model.Player;

/**
 * Test class for Aggressive class.
 * 
 * @author Dhaval Desai
 * @author Jenny Pujara
 * 
 * @version 1.0.0
 *
 */
public class AggressiveTest {

	/** List of countries */
	List<Country> list;	

	/** Object for Aggressive Class */
	Aggressive aggressive;	

	/** Object for Country Class */
	Country country1, country2,country3;

	/** Object for Player class */
	private Player player1,player2;

	/** ArrayList of adjacent countries */
	private ArrayList<Country> adjacentCountries;

	/** ArrayList of countries owned by player */
	private ArrayList<Country> playerCountries1,playerCountries2;

	/** Set up the initial objects for Aggressive class
	 * 
	 */
	@Before
	public void initialize() {
		list = new ArrayList<Country>();
		aggressive = new Aggressive();		

		country1 = new Country("India");
		country2 = new Country("Russia");
		country3 = new Country("Indonesia");

		adjacentCountries = new ArrayList<>();
		adjacentCountries.add(country2);
		adjacentCountries.add(country3);

		player1 = new Player("Player1");
		player2 = new Player("Player2");

		country1.setAdjacentCountries(adjacentCountries);

		country1.setNoOfArmies(5);
		country2.setNoOfArmies(4);
		country3.setNoOfArmies(2);

		playerCountries1= new ArrayList<>();
		playerCountries1.add(country1);
		playerCountries1.add(country2);

		playerCountries2= new ArrayList<>();
		playerCountries2.add(country3);

		country1.setPlayer(player1);
		country2.setPlayer(player1);
		country3.setPlayer(player2);

		list.add(country1);
		list.add(country2);

	}

	/** test method to check and find the strongest country if adjacent country to attack.
	 */ 
	@Test
	public void checkAndFindStrongestIfAdjacentCountryToAttackTest() {
		assertEquals(country1,aggressive.checkAndFindStrongestIfNoAdjacentCountryToAttack(list));
	}

	/** test method to check and find the strongest country if no adjacent country to attack.
	 */ 
	@Test
	public void checkAndFindStrongestIfNoAdjacentCountryToAttackTest() {
		adjacentCountries.remove(country3);
		country1.setAdjacentCountries(adjacentCountries);
		assertNull(aggressive.checkAndFindStrongestIfNoAdjacentCountryToAttack(list));
	}

	/** test method to check and find the strongest country for reinforcement.
	 */ 
	@Test
	public void findStrongestCountryForReinforcementTest() {
		adjacentCountries.add(country3);
		country1.setAdjacentCountries(adjacentCountries);
		assertEquals(country1,aggressive.findStrongestCountryForReinforcement(list));
	}

	/** test method to check and find the strongest country if no adjacent country to attack.
	 */ 
	@Test
	public void findNoStrongestCountryForReinforcementTest() {
		adjacentCountries.remove(country3);
		country1.setAdjacentCountries(adjacentCountries);
		assertNull(aggressive.checkAndFindStrongestIfNoAdjacentCountryToAttack(list));
	}

}
