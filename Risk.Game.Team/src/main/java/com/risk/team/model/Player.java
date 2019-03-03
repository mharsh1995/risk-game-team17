package com.risk.team.model;

import java.util.ArrayList;
import com.risk.team.model.BonusCardType;

/**
 * Player class for information regarding the player
 * 
 * @author Kartika Patil
 *
 */
public class Player {
	
	/** Player name */
    private String name;
    
    /**Initialization of army count */
    private int armyCount=0;
    
    /** List of countries possessed by the Player */
    private ArrayList<Country> myCountries;
    
    /** List of cards that Player holds*/
    private ArrayList<BonusCardType> listOfCards;
    
    /** Player constructor */
    public Player() {
        this.myCountries = new ArrayList<Country>();
        this.listOfCards = new ArrayList<BonusCardType>();
    }
    
    /**
     * Get method to get the name of the Player
     *
     * @return name Player name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Set method for the name of the Player
     * 
     * @param name Player name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Method to get the Count of the Army that the Player owns
     * 
     * @return armyCount count of army
     */
    public int getArmyCount() {
        return armyCount;
    }
    
    /**
     * Method to set the count of the Army
     * 
     * @param armyCount Count of the Army
     * 
     */
    public void setArmyCount(int armyCount) {
        this.armyCount = armyCount;
    }
    
    /**
     * Get the list of countries the player owns
     * 
     * @return myCountries list of countries
     */
    public ArrayList<Country> getMyCountries() {
        return myCountries;
    }
    
    /**
     * Method to set the country list for player
     * 
     * @param myCountries list of countries
     * 				
     */
    public void setMyCountries(ArrayList<Country> myCountries) {
        this.myCountries = myCountries;
    }
    
    /**
     *  Method for adding a single country to the player's country list
     *  
     * @param country Object of the country
     */
    public void addCountry(Country country){
        this.myCountries.add(country);
    }
    
    /**
     * Method for getting the list of cards the player owns
     * 
     * @return listOfCards list of cards
     */
    public ArrayList<BonusCardType> getListOfCards() {
        return listOfCards;
    }
    
    /**
     * Method to set the list of card of one particular type
     * 
     * @param listOfCards Types of cards
     */
    public void setListOfCards(ArrayList<BonusCardType> listOfCards) {
        this.listOfCards = listOfCards;
    }
    
    /**
     * Method to add the armies to a country
     * 
     * @param country name of the country
     * @param numberOfArmies number of armies to be added
     */
    public void addArmiesToCountry(Country country, int numberOfArmies) {
    	if(this.getArmyCount()>0 && this.getArmyCount()>=numberOfArmies) {
    		if(!this.getMyCountries().contains(country)) {
    			System.out.println("This country does not falls under your territory.");
    		}
    		else {
    			country.setNoOfArmies(country.getNoOfArmies() + numberOfArmies);
    			this.setArmyCount(this.getArmyCount() - numberOfArmies);
    		}
    	}
    	else {
    		System.out.println("Insufficient number of armies.");
    	}
    }
}
