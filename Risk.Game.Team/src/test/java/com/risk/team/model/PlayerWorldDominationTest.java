package com.risk.team.model;

/**
 * Test class for player World Domination class.
 * 
 * @author Dhaval Desai
 * 
 * @version 2.0.0
 */
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import com.risk.team.model.Continent;
import com.risk.team.model.Country;
import com.risk.team.model.Player;
import com.risk.team.model.PlayerWorldDomination;
import com.risk.team.services.RiskMapRW;

public class PlayerWorldDominationTest {

	/** Object for RiskMapRW Class */
	private RiskMapRW map;

	/** Object for RiskMapRW Class */
	private PlayerWorldDomination playerDomination;

	/** Object for Continent class */
	private Continent continent1,continent2,continent3;

	/** HashMap for all the continents in the map graph */
	private HashMap<String, Continent> continents;

	/** Object for Country class */
	private Country country1,country2,country3,country4;

	/** HashMap for percentage of map owned by each player in the game */
	HashMap<Player, Double> playerTerPercent;

	/** ArrayList for list of countries */
	private ArrayList<Country> listOfCountries1,listOfCountries2,listOfCountries3;

	/**
	 * Set up the initial objects forPlayerWorldDominationTest
	 */
	@Before
	public void initialize(){
		map = new RiskMapRW();
		playerTerPercent = new HashMap<>();
		playerDomination = new PlayerWorldDomination();
		continents = new HashMap<>();
		listOfCountries1 = new ArrayList<>();
		listOfCountries2 = new ArrayList<>();
		listOfCountries3 = new ArrayList<>();

		continent1 = new Continent("Asia", 2);
		continent2 = new Continent("Europe", 4);
		continent3 = new Continent("North-America", 1);

		country1 = new Country("India");
		country2 = new Country("China");
		country3 = new Country("Japan");
		country4 = new Country("Srilanka");

		listOfCountries1.add(country1);
		listOfCountries2.add(country2);
		listOfCountries2.add(country3);
		listOfCountries3.add(country4);

		continents.put("Asia", continent1);
		continents.put("Europe", continent2);
		continents.put("North-America", continent3);

		map.getMapGraph().setContinents(continents);
		continents.get("Asia").setListOfCountries(listOfCountries1);
		continents.get("Europe").setListOfCountries(listOfCountries2);
		continents.get("North-America").setListOfCountries(listOfCountries3);

	}

	/** test method to check the generated world domination data
	 */
	@Test
	public void generateWorldDominationDataTest() {
		assertNotNull(playerDomination.generateWorldDominationData(map));
	}

}
