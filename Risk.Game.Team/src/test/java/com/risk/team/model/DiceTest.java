package com.risk.team.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.risk.team.model.Country;
import com.risk.team.model.Dice;
import com.risk.team.model.Player;

/**
 * Test class for Dice.
 * 
 * @author Dhaval Desai
 * 
 * @version 2.0.0
 */

public class DiceTest {

	/** Object for Country class for attacking country */
	private Country attackingCountry;

	/** Object for Country class for defending country */
	private Country defendingCountry;

	/** Object for Dice class */
	private Dice dice;

	/** Object for Player class for player 1 */
	private Player player1;

	/** Object for Player class for player 2 */
	private Player player2;

	/** ArrayList to hold list of players countries */
	private ArrayList<Country> playerCountries;

	private ArrayList<Country> playerCountries1;

	/** ArrayList to hold play result */
	private ArrayList<String> playResult;

	/**
	 * Set up the initial objects for Round Robin Phase
	 * 
	 */
	@Before
	public void initialize() {

		player1 = new Player("Player1");
		attackingCountry = new Country("India");
		attackingCountry.setPlayer(player1);
		attackingCountry.setNoOfArmies(3);

		player2 = new Player("Player2");
		defendingCountry = new Country("China");

		playerCountries = new ArrayList<>();
		playerCountries.add(defendingCountry);
		player2.setPlayerCountries(playerCountries);
		defendingCountry.setPlayer(player2);

		playerCountries1 = new ArrayList<>();
		playerCountries1.add(attackingCountry);
		player1.setPlayerCountries(playerCountries1);

		defendingCountry.setNoOfArmies(1);

		dice = new Dice(attackingCountry, defendingCountry);

		dice.setDefendingCountry(defendingCountry);

		playResult = new ArrayList<>();
	}

	/**
	 * Test to check if dice throw is possible.
	 * 
	 */
	@Test
	public void checkDiceThrowPossibleTest() {
		dice.updateCountryList();
		assertEquals(player1, defendingCountry.getPlayer());
	}

	/**
	 * Test to check if dice throw not possible.
	 * 
	 */
	@Test
	public void checkDiceThrowNotPossibleTest() {
		defendingCountry.setNoOfArmies(0);
		attackingCountry.setNoOfArmies(1);
		assertFalse(dice.checkDiceThrowPossible());
	}

	/**
	 * Test to check country list update after dice throw.
	 * 
	 */
	@Test
	public void updateCountryListTest() {
		assertTrue(dice.checkDiceThrowPossible());
	}

	/**
	 * Test to set attacking country for attack phase
	 * 
	 */
	@Test
	public void setAttackingCountryTest() {
		dice.setAttackingCountry(attackingCountry);
		assertNotNull(attackingCountry);
	}
	/**
	 * Test to set attacking country for attack phase
	 * 
	 */
	@Test
	public void checkSkipMoveArmyTest() {
		dice.skipMoveArmy();
		assertNotNull(defendingCountry.getPlayer().getPlayerCountries());
	}

	/**
	 * Test to check update armies after attack defender lost.
	 * 
	 */
	@Test
	public void updateArmiesAfterAttackDefenderLostTest() {
		dice.updateArmiesAfterAttack(2, 3, playResult);
		assertEquals("Defender has lost one army.",playResult.get(0));
	}

	/**
	 * Test to check update armies after attack attacker lost.
	 * 
	 */
	@Test
	public void updateArmiesAfterAttackAttackerLostTest() {
		dice.updateArmiesAfterAttack(3, 2, playResult);
		assertEquals("Attacker has lost one army.",playResult.get(0));
	}


}
