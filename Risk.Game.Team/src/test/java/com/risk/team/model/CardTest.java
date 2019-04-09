package com.risk.team.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.risk.team.model.BonusCardType;
import com.risk.team.model.Card;
import com.risk.team.model.Player;

/**
 * Test class for Card.
 * 
 * @author Dhaval Desai
 *
 */
public class CardTest {

	/** Object for Card class */
    private Card card;
    
    /** Objects for Player class */
    private Player player, currentPlayer;

    /** ArrayList to hold list of cards in the game */
    private ArrayList<Card> listOfCards;
    
    /** List to hold cards to be traded */
    private ArrayList<Card> cardsToTrade;
    
    /** hashMap to hold cards count */
    private HashMap<String, Integer> cardCountMap;

	/**
	 * Set up the initial objects for Round Robin Phase.
	 */
    @Before
    public void initialize() {

        card = new Card();
        listOfCards = new ArrayList<>();
        
        player = new Player("p1");
        
        cardCountMap = new HashMap<>();
        
        cardsToTrade = new ArrayList<>();
        cardsToTrade.add(new Card(BonusCardType.INFANTRY));
    	cardsToTrade.add(new Card(BonusCardType.ARTILLERY));
    	cardsToTrade.add(new Card(BonusCardType.CAVALRY));
    }

	/**
	* Test to check trade for different cards for
	* a valid card trade possibility.
	*/
    @Test
    public void checkExchangeForDiffCards(){
        listOfCards.add(new Card(BonusCardType.INFANTRY));
        listOfCards.add(new Card(BonusCardType.ARTILLERY));
        listOfCards.add(new Card(BonusCardType.CAVALRY));

        assertEquals(true,card.isTradePossible(listOfCards));
        }
    
    /**
	* Test to check the current player playing the game
	*/
    @Test
    public void checkCurrentPlayerTest(){   	
    	card.setCurrentPlayer(player);
    	currentPlayer = card.getCurrentPlayer();
        assertEquals("p1",currentPlayer.getName());
        }
    
    /**
   	* Test to check which cards to be traded
   	*/
    @Test
    public void cardsToBeTradedTest(){       	
    	card.setCardsToTrade(cardsToTrade);
    	assertNotNull(card.getCardsToTrade());
   }
       
	/**
	* Test to check trade for same cards for
	* a valid card trade possibility.
	* 
	*/
    @Test
    public void checkTradeForSameCards(){
        listOfCards.add(new Card(BonusCardType.CAVALRY));
        listOfCards.add(new Card(BonusCardType.CAVALRY));
        listOfCards.add(new Card(BonusCardType.CAVALRY));

        assertEquals(true,card.isTradePossible(listOfCards));
        listOfCards.clear();

        listOfCards.add(new Card(BonusCardType.INFANTRY));
        listOfCards.add(new Card(BonusCardType.INFANTRY));
        listOfCards.add(new Card(BonusCardType.INFANTRY));
        assertEquals(true,card.isTradePossible(listOfCards));
        listOfCards.clear();

        listOfCards.add(new Card(BonusCardType.ARTILLERY));
        listOfCards.add(new Card(BonusCardType.ARTILLERY));
        listOfCards.add(new Card(BonusCardType.ARTILLERY));
        assertEquals(true,card.isTradePossible(listOfCards));
        listOfCards.clear();
        }
    
    
    /**
	* Test to notify observers for all the cards that are traded
	*/
    @Test
    public void cardsTradedNotifyTest(){
    	player.setArmyCount(0);
    	player.setCardList(cardsToTrade);
    	card.cardsToBeTraded(cardsToTrade);
    	assertNotNull(cardsToTrade);
        }
    
    /**
	* Test to notify observers for all the cards that are traded
	*/
    @Test
    public void validCardCombinationTest(){
    	card.generateValidCardCombination(cardsToTrade);
    	assertNotNull(cardCountMap);
        }
    }
