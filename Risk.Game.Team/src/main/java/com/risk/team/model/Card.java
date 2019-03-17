package com.risk.team.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.risk.team.controller.RiskCardController;
import com.risk.team.model.BonusCardType;


/**
 * /**
 * Card class represents the card model.
 * It provides methods for Trade in like operations on the cards during game.
 * 
 * @author yashgolwala
 *
 */
 
public class Card {
	
	/**
	 * Kind of card 
	 */
	String kindOfCard;
	
	/**
	 * Object of Country class
	 */
	private Country country;
	
	/**
	 * Card possessed by Player 
	 * Object of Player Class
	 */
	private Player currentPlayer;
	
	/**
	 *List of card that can be traded
	 */
	private List<Card> cardsToTrade;
	
	/**
     * Card default constructor
     */

    public Card() { 
    	
    }

    /**
     * Card constructor with parameter
     *
     * @param cardType Type of card
     */

    public Card(String kindOfCard) {
        this.kindOfCard = kindOfCard;
    }

    /**
     * Get the card kind
     *
     * @return kind of card
     */
    
    public String getKindOfCard() {
        return kindOfCard;
    }

    /**
     * get Country of the card
     *
     * @return country of the card
     */
    public Country getCountry() {
        return country;
    }
    
    /**
     * Set Country
     *
     * @param country of the card
     */

    public void setCountry(Country country) {
        this.country = country;
    }
    
    /**
     * Getter for list of cards to trade.
     *
     * @return list of cards
     */

    public List<Card> getCardsToTrade() {
        return cardsToTrade;
    }

    /**
     * Set list of cards to be traded
     *
     * @param cardsToTrade cards to trade
     */

    public void setCardsToTrade(List<Card> cardsToTrade) {
        this.cardsToTrade = cardsToTrade;
    }
      
    /**
     * Method is used to display
     * the cards owned by the player.
     *
     * @param player Player having the turn
     * @param card   card to display for the player
     */

    public void showCardsOfPlayer(Player player, Card card) {
        this.currentPlayer = player;
        RiskCardController cardController = new RiskCardController(this.currentPlayer, card);
       
    }

    
    /**
     * Method returns a list of cards which
     * are seleted by the current player
     *
     * @param list list of cards
     * @return List of cards selected by the player
     */
    
    public List<Card> chooseCards(List<Card> list) {
        List<Card> selectedCards = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        String answer = new String();
        System.out.println("enter yes to select");
        for (int i = 0; i < list.size(); ++i) {
        System.out.println(" "+list.get(i));
        answer= sc.nextLine();
        if(answer=="yes" || answer=="Yes" || answer=="Y" || answer=="y") {
        	selectedCards.add(list.get(i));
        }
        }
        return selectedCards;
    }
    
    
    /**
     * Method for verifying if cards can be traded for army or not
     *
     * @param selectedCards selected cards
     * @return true if the exchange is possible; otherwise false
     */
    public boolean isTradePossible(List<Card> selectedCards) {
        boolean isPossible = false;
        if (selectedCards.size() == 3) {
            int infantry = 0, cavalry = 0, artillery = 0;
            for (Card card : selectedCards) {
                if (card.getKindOfCard().equals(BonusCardType.infantry)) {
                    infantry++;
                } else if (card.getKindOfCard().equals(BonusCardType.cavalry)) {
                    cavalry++;
                } else if (card.getKindOfCard().equals(BonusCardType.artillery)) {
                    artillery++;
                }
            }
            if ((infantry == 1 && cavalry == 1 && artillery == 1) || infantry == 3 || cavalry == 3 || artillery == 3) {
                isPossible = true;
            }
        }
        return isPossible;
    }
    
    /**
     * Method notifies the observers of the card,
     * Also sets the cards which are selected for exchange.
     *
     * @param selectedCards cards which are selected by the user to trade
     */

    public void cardsToTrade(List<Card> selectedCards) {
        setCardsToTrade(selectedCards);
    }
}
