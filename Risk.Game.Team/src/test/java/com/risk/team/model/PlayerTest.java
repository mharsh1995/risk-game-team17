package com.risk.team.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.text.html.ListView;

import org.junit.Before;
import org.junit.Test;

import com.risk.team.controller.GamePhaseController;
import com.risk.team.model.BonusCardType;
import com.risk.team.model.Card;
import com.risk.team.model.Continent;
import com.risk.team.model.Country;
import com.risk.team.model.Player;
import com.risk.team.services.RiskMapRW;
import com.risk.team.services.*;
import com.risk.team.strategy.Aggressive;
import com.risk.team.strategy.Human;

import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import static org.junit.Assert.*;

/**
 * Test class for Player.
 * 
 * @author Jenny Pujara
 *
 */

public class PlayerTest {

	private Player player;

	public static Player playerArmy;
	
	/** Object for Player Class */
	private Player player1, player2, player3, player4, player5;
	
	/** Object for Player Class */
	private static Player testPlayer;
	
	/** Object for Player Class */
	private static Player playerPlaying;
	
	/** List to hold list of players */
	private List<Player> players;
	
	/** List to hold list of total players in the game*/
	static List<Player> playersList;
	
	/** Object for Country Class */
	private Country attackingCountry;
	
	/** Object for Country Class */
	private Country defendingCountry;

	private Country country;

	/** Object for Country Class */
	private Country country1;

	/** Object for Country Class */
	private Country country2;
	
	/** HashMap of player names and types */
	 HashMap<String, String> hm;
	    	
	/** ArrayList to hold list of countries */
	private ArrayList<Country> myCountries;

	/** ArrayList to hold list of countries */
	private ArrayList<Country> playercountries;

	private ArrayList<Country> playerOwnedCountries;

	private ArrayList<Country> continentListOfCountries;

	/** Object for Continent Class */
	private Continent continent,continent1,continent2;
	
	/** Object for GamePhaseController Class */
	private GamePhaseController gamePhaseController;
	
	/** Object for RiskMapRW CLass */
	private RiskMapRW riskMapRW;
	
	 /** Object for Player class */
    private Player currentPlayer;
	
	/** Player's cards*/
	private ArrayList<Card> cardList;
	
	/** HashMap of all countries in the graph */
	private HashMap<String, Country> allCountries;

	/** The @fxPanel */
	@FXML
	private JFXPanel jfxPanel;

	/** The @textArea */
	@FXML
	private TextArea textArea;
	
	/**
	 * Set up the initial objects for PlayerTest
	 */
	@Before
	public void initialize() {
		
		riskMapRW = new RiskMapRW();

		player = new Player();
		playerArmy = new Player();
		player1 = new Player("player1");
		player2 = new Player("player2");
		playerPlaying = new Player("playerPlaying");
		player3 = new Player();
		
		players = new ArrayList<Player>();
		players.add(player1);
		players.add(player2);
		
		attackingCountry = new Country("India");
		attackingCountry.setPlayer(player1);
	
		defendingCountry = new Country("China");
		defendingCountry.setPlayer(player2);
		
		continent1 = new Continent("Asia", 2);
		defendingCountry.setPartOfContinent(continent1);
		
		myCountries = new ArrayList<Country>();
		myCountries.add(defendingCountry);

		continent2 = new Continent("NorthAmerica",4);

		country1 = new Country("Canada");
		country1.setPartOfContinent(continent2);
		continent1.getListOfCountries().add(country1);

		country2 = new Country("America");
		country2.setPartOfContinent(continent2);
		continent1.getListOfCountries().add(country2);

		country1.getAdjacentCountries().add(country2);
		country2.getAdjacentCountries().add(country1);

		riskMapRW.getMapGraph().addContinent(continent2);
		
		continent2.setListOfCountries(myCountries);
		playerPlaying.setPlayerCountries(myCountries);

		playercountries = new ArrayList<>();
		playercountries.add(country1);
		playercountries.add(country2);
		continent2.setListOfCountries(playercountries);
		player1.setPlayerCountries(playercountries);

		jfxPanel = new JFXPanel();
		textArea= new TextArea();

		playerOwnedCountries = new ArrayList<Country>();
		continentListOfCountries = new ArrayList<Country>();

		continent = new Continent("Europe", 2);

		country = new Country("C1");
		playerOwnedCountries.add(country);
		country.setPartOfContinent(continent);
		continentListOfCountries.add(country);

		country = new Country("C2");
		playerOwnedCountries.add(country);
		country.setPartOfContinent(continent);
		continentListOfCountries.add(country);

		country = new Country("C3");
		playerOwnedCountries.add(country);
		country.setPartOfContinent(continent);
		continentListOfCountries.add(country);

		player = new Player();
		player.setPlayerCountries(playerOwnedCountries);
		
		hm= new HashMap<>();
		hm.put("player1", "Aggressive");
		hm.put("player2", "Human");
		
		continent.setListOfCountries(continentListOfCountries);
		
		gamePhaseController = new GamePhaseController(riskMapRW,hm);
		allCountries = new HashMap<String, Country>();
		allCountries.put(country1.getName(),country1);
		allCountries.put(country2.getName(),country2);
		
		player4 = new Player("player4", "Aggressive",gamePhaseController);
		player5 = new Player("player5", "Cheater");
		
		cardList= new ArrayList<>();
		cardList.add(new Card("C1"));
		cardList.add(new Card("C2"));
		
		playersList = new ArrayList<>();
		playersList.add(new Player("Jenny"));
		playersList.add(new Player("Kartika"));
		playersList.add(new Player("Yash"));
		
	}

	/**
	 * Test to check assignment of armies to player
	 */
	@Test
	public void assignArmiesToPlayerTest(){
		assertTrue(playerArmy.assignArmiesToPlayers(playersList));
	}
	
	/**
	 * Test to check if Attack move is valid
	 */
	@Test
	public void isAttackMoveValidTest() {
		attackingCountry.setNoOfArmies(3);
		assertTrue(player1.isAttackMoveValid(attackingCountry,defendingCountry));
	}
	
	/**
	 * Test to check if player lost the game after attack move
	 */
	@Test
	public void checkPlayerLostTest()  {
		player1.setPlayerCountries(myCountries);
		player2.setPlayerCountries(new ArrayList<Country>());
		Player.currentPlayer = player1;
		assertEquals(player2.getName(),player1.checkPlayerLost(players).get(0).getName());
	}
	
	/**
	 * Test to check if player did not lost the game after attack move
	 */
	@Test
	public void checkPlayerNotLostTest() {
		player1.setPlayerCountries(myCountries);
		player2.setPlayerCountries(myCountries);
		Player.currentPlayer = player1;
		assertTrue(player1.checkPlayerLost(players).isEmpty());	
	}
	
	/**
	 * Test to check if player notifies the correct player type
	 */
	@Test
	public void playerNotifyTest() {	
		assertNotNull(player4.getPlayerBehaviour());	
	}
	
	/**
	 * Test to check if player notifies the correct player type
	 */
	@Test
	public void playerNotifyTest2() {		
		assertNotNull(player5.getPlayerBehaviour());	
	}
	
	/**
	 * Test to set the list of cards for the player
	 */
	@Test
	public void setCardListTest() {		
		player4.setCardList(cardList);
		assertNotNull(cardList);	
	}
	
	/**
	 * Test to check if player army is left
	 */
	@Test
	public void isPlayerArmyLeftTest() {		
		playersList.get(0).setArmyCount(0);
		playersList.get(1).setArmyCount(0);
		playersList.get(2).setArmyCount(0);
		assertTrue(player.isPlayerArmyLeft(playersList));
	}
	
	/**
	 * Test to check if player army is left
	 */
	@Test
	public void isPlayerArmyNotLeftTest() {		
		playersList.get(0).setArmyCount(0);
		playersList.get(1).setArmyCount(2);
		playersList.get(2).setArmyCount(5);
		assertFalse(player.isPlayerArmyLeft(playersList));
	}
	
	/**
	 * Test to check calculation of number of reinforcement armies to be allocated to the player
	 */
	@Test
	public void noOfReinforcementArmiesTest() {
		assertEquals(playerPlaying,player1.noOfReinforcementArmies(playerPlaying));
	}
	
	/**
	 * Test to get the number of cards exchanged for the player
	 */
	@Test
	public void setNumberOfCardSetExchangedTest() {
		player.setNumberOfCardSetTraded(2);
		assertNotNull(player.getNumberOfCardSetTraded());
	}
	
	/**
	* Test to check the current player playing the game
	* 
	*/
    @Test
    public void checkCurrentPlayerTest(){ 
    	Player player6 = new Player();
    	player6.setPlayerPlaying(player2);
    	currentPlayer = player6.getPlayerPlaying();
    	assertEquals(player2,currentPlayer);
        }

	/**
	 * Test to validate number of armies when the whole continent is owned by the player
	 */
	@Test
	public void findNoOfArmiesWhenPlayerOwnContinentTest() {

		assertEquals(continent.getControlValue()+1, player.findNoOfArmies(player));
	}

	/**
	 * Test to validate number of armies when player does not owns the continent
	 */
	@Test
	public void findNoOfArmiesWhenPlayerDoesNotOwnContinentTest() {

		country = new Country("C4");
		continentListOfCountries.add(country);
		assertEquals(3, player.findNoOfArmies(player));
	}

	/**
	 * Test to check invalid of fortification move
	 */
	@Test
	public void isFortificationValidTest(){
		player1.setPlayerBehaviour(new Human());
		country1.setPlayer(player1);
		country1.setNoOfArmies(3);
		country2.setPlayer(player1);
		assertEquals(true,player1.isFortificationPhaseValid(riskMapRW,player1));
	}

	/**
	 * Test to check valid fortification move
	 */
	@Test
	public void isFortificationValidFalse(){
		player1.setPlayerBehaviour(new Human());
		country1.setPlayer(player1);
		country1.setNoOfArmies(0);
		country2.setPlayer(player1);
		assertEquals(false,player1.isFortificationPhaseValid(riskMapRW,player1));
	}
	
	/**
	 * Test to generate player
	 */
	@Test
	public void generatePlayerTest(){		
		assertNotNull(player4.generatePlayer(hm, gamePhaseController));
	}
	


	/**
	 * Test to check exchange of cards of the player for armies
	 */
	@Test
	public void exchangeCardsTest(){
		List<Card> listOfCards = new ArrayList<>();
		listOfCards.add(new Card(BonusCardType.ARTILLERY));
		listOfCards.add(new Card(BonusCardType.INFANTRY));
		listOfCards.add(new Card(BonusCardType.CAVALRY));
		testPlayer = player3.tradeCards(listOfCards,player3.getNumberOfCardSetTraded());
		assertEquals(5,testPlayer.getArmyCount());
		testPlayer = player3.tradeCards(listOfCards,player3.getNumberOfCardSetTraded());
		assertEquals(15,testPlayer.getArmyCount());
	}
	
	/**
	 * Test to check exchange of cards of the player for armies
	 */
	@Test
	public void exchangeCardsTest2(){
		List<Card> listOfCards = new ArrayList<>();
		listOfCards.add(new Card(BonusCardType.ARTILLERY));
		listOfCards.add(new Card(BonusCardType.INFANTRY));
		
		listOfCards.get(0).setCountry(defendingCountry);
		listOfCards.get(1).setCountry(defendingCountry);
		
		Player.currentPlayer = player3;
		player3.setPlayerCountries(myCountries);
		testPlayer = player3.tradeCards(listOfCards,2);
		assertNotNull(testPlayer.getArmyCount());
	}
	/**
	 * Test to automatically assign armies to country
	 */
	@Test
	public void automaticAssignPlayerArmiesToCountryTest() {
		Player player1 = new Player("Jenny");
		player1.setArmyCount(10);
		ArrayList<Country> listOfCountries = new ArrayList<>();
		listOfCountries.add(country1);
		listOfCountries.add(country2);
		player1.setPlayerCountries(listOfCountries);
		player.automaticAssignPlayerArmiesToCountry(player1);
		assertEquals(9,player1.getArmyCount());
	}
	
	/**
	 * Test to add armies to country
	 */
	@Test
	public void addArmiesToCountryTest() {
		player4.setArmyCount(10);
		player4.setPlayerCountries(myCountries);
		player4.addArmiesToCountry(defendingCountry, 3);
		assertNotNull(defendingCountry.getNoOfArmies());
	}
	
	/**
	 * Test to check if sufficient number of armies are available to assign to country
	 */
	@Test
	public void insufficientArmiesToCountryTest() {
		player4.setArmyCount(5);
		player4.setPlayerCountries(myCountries);
		player4.addArmiesToCountry(defendingCountry, 5);
		assertNotNull(defendingCountry.getNoOfArmies());
	}
	
	
	
}