package com.risk.team.model;

//import com.risk.services.controller.CardController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import com.risk.team.model.BonusCardType;


/**
 * /**
 * Card class represents the card model.
 * It provides methods for Trade in like operations on the cards during game.
 * 
 * @author yashgolwala
 *
 */
 
public class Card extends Observable {
	
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
	private Player presentPlayer;
	
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
        setChanged();
        notifyObservers("cardsTraded");
    }
}
