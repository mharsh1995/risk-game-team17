package com.risk.team.strategy;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.risk.team.model.Country;
import com.risk.team.model.Player;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Test class for Cheater class.
 * 
 * @author Kartika Patil
 *
 */
public class CheaterTest {
	
	/** List of countries */
	private List<Country> list;	
	
	/** Object for Cheater Class */
	private Cheater cheater;
	
	/** Object of ObservableList.  */
    private List<Country> attackerCountryList;
	
	/** Object for Country Class */
	Country country1, country2,country3;
	
	/** Object for Player class */
	private Player player1,player2,currentPlayer;

	/** ArrayList of adjacent countries */
	private ArrayList<Country> adjacentCountries;
	
	/** ArrayList of countries owned by player */
	private ArrayList<Country> playerCountries1,playerCountries2;	
	
	/**
	 * Set up the initial objects for Cheater class
	 * 
	 */
	@Before
	public void initialize() {
		list = new ArrayList<Country>();
		player1 = new Player("Player1");
		
		currentPlayer = new Player();
		currentPlayer.currentPlayer = player1;
		cheater = new Cheater();
		
		player2 = new Player("Player2");
		
		country1 = new Country("India");
		country2 = new Country("Russia");
		country3 = new Country("Indonesia");
		
		adjacentCountries = new ArrayList<>();
		adjacentCountries.add(country2);
		adjacentCountries.add(country3);
		
	
		country1.setNoOfArmies(5);
		country2.setNoOfArmies(4);
		country3.setNoOfArmies(2);
		
		playerCountries1= new ArrayList<>();
		playerCountries1.add(country1);
		playerCountries1.add(country2);
		
		playerCountries2= new ArrayList<>();
		playerCountries2.add(country3);
		
		country1.setPlayer(currentPlayer);
		country2.setPlayer(currentPlayer);
		country3.setPlayer(player2);
				
		country1.setAdjacentCountries(playerCountries2);
	
		
		attackerCountryList = new ArrayList<>();
		attackerCountryList.add(country1);
		attackerCountryList.add(country2);
		
		cheater.attackerCountryList =FXCollections.observableArrayList(attackerCountryList);

	}

	@Test
	public void cheaterFortification1Test() {
		
	int beforeArmies = country1.getNoOfArmies();
	cheater.fortificationPhase(null,null,currentPlayer);
	assertEquals(beforeArmies*2,country1.getNoOfArmies());
		
	}
	
	
	@Test
	public void cheaterFortification2Test() {
		
	int beforeArmies = country2.getNoOfArmies();
	cheater.fortificationPhase(null,null,currentPlayer);
	assertEquals(beforeArmies,country2.getNoOfArmies());
		
	}

}
