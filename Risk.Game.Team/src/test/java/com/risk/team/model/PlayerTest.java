package com.risk.team.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.risk.team.model.Card;
import com.risk.team.model.Country;
import com.risk.team.model.Player;

public class PlayerTest {
	Player player;
	Country country;
	
	/** Declaring ArrayList to store list of countries */
	private ArrayList<Country> listOfCountries;
	
	/** Declaring country1,country2,country3 objects of type Country */
	private Country country1, country2, country3, c;
	
	Card card1,card2,card3;
	ArrayList<Card> selectedCards;
	
	@Before
	public void init() {
		player=new Player();
		country=new Country("India");
		listOfCountries = new ArrayList<>();
		country1 = new Country("India");
		country2 = new Country("Nepal");
		country3 = new Country("Sri Lanka");
		card1=new Card("infantry");
		card2=new Card("infantry");
		card3=new Card("infantry");

		selectedCards=new ArrayList<>();
		selectedCards.add(card1);
		selectedCards.add(card2);
		selectedCards.add(card3);
		
		listOfCountries.add(country1);

	}
	
	@Test
	public void addArmiesToCountryTest() {
		int numberOfArmies=15;
		player.setArmyCount(numberOfArmies);
		player.addArmiesToCountry(country, numberOfArmies);
		
	}
	
	@Test
	public void addArmiesToCountryTestTwo() {
		int numberOfArmies=15;
		player.addArmiesToCountry(country, numberOfArmies);
		
	}
	
	@Test
	public void addArmiesToCountryTestThree() {
		int numberOfArmies=15;
		player.setArmyCount(16);
		player.addCountry(country2);
		country2.setNoOfArmies(0);
		player.addArmiesToCountry(country2, numberOfArmies);
		assertEquals(15,country2.getNoOfArmies());
	}
	
	@Test
	public void attackPhaseTest() {
		Country attackingCountry=new Country("India");
		Country defendingCountry=new Country("China");
		player.attackPhase(attackingCountry, defendingCountry);
	}
	
	@Test
	public void attackPhaseTestTwo() {
		player.playerCanAttack(listOfCountries);
		Country attackingCountry=new Country("India");
		Country defendingCountry=new Country("China");
		player.attackPhase(attackingCountry, defendingCountry);
	}
	
	@Test
	public void setCardSetCountTest() {
		player.setCardSetCount(5);
		assertEquals(player.getCardSetCount(),6);
	}
	
	@Test
	public void getCardSetCountTest() {
		player.setCardSetCount(5);
		assertEquals(player.getCardSetCount(),6);
	}
	
	@Test
	public void addCountryTest() {
		player.addCountry(c);
		assertTrue(player.getMyCountries().contains(c));
	}
	
	@Test
	public void exchangeCardsTest() {
		player.setArmyCount(0);
		player.exchangeCards(selectedCards, 2);
		assertEquals(10,player.getArmyCount());
	}
	
	@Test
	public void exchangeCardsTestTwo() {
		player.setArmyCount(0);
		country1.setNoOfArmies(0);
		player.addCountry(country1);
		card1.setCountry(country1);
		player.exchangeCards(selectedCards, 2);
		assertEquals(2,country1.getNoOfArmies());
	}
	
	@Test
	public void playerCanAttackTest() {
		country1.setNoOfArmies(10);
		assertTrue(player.playerCanAttack(listOfCountries));
	}
	
	
	@Test
	public void setListOfCardsTest() {
		player.setListOfCards(selectedCards);
		assertNotNull(player.getListOfCards());
	}
	
	
	@Test
	public void isAttackMoveValidTest() {
		country1.setNoOfArmies(10);
		country1.setPlayer(player);
		Player player1=new Player();
		country2.setPlayer(player1);
		player.addCountry(country1);
		assertTrue(player.isAttackMoveValid(country1, country2));
		}
	
	@Test
	public void isAttackMoveValidTestTwo() {
		country1.setNoOfArmies(0);
		country1.setPlayer(player);
		Player player1=new Player();
		country2.setPlayer(player1);
		player.addCountry(country1);
		assertFalse(player.isAttackMoveValid(country1, country2));
		}
	
	@Test
	public void checkPlayerLostTest() {
		Player player1=new Player();
		List<Player> playersPlaying=new ArrayList<>();;
		playersPlaying.add(player);
		playersPlaying.add(player1);
		player.addCountry(country1);
		assertEquals(player1,player.checkPlayerLost(playersPlaying));
	}
	
	@Test
	public void reinforcementTest() {
		
	}
}
