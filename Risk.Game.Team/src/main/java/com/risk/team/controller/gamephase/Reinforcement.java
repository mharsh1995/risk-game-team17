package com.risk.team.controller.gamephase;

import java.util.ArrayList;

import com.risk.team.model.Continent;
import com.risk.team.model.Country;
import com.risk.team.model.Player;

/**
 * 
 * Reinforcement class contains the methods used in the reinforcement phase
 * 
 * @author Jenny
 *
 */
public class Reinforcement {
	
	
	/** hasPlayerAllContinents flag to check if player has all continents */
	boolean hasPlayerAllContinents = true;
	
	 /** playerOwnedContries list of countries owned by player */
	ArrayList<Country> playerOwnedCountries;
	
	 /** continentCountryList list of countries in that continent */
	ArrayList<Country> continentCountryList;

	/**
	 * Method to find the number of countries owned by the player and to assign
	 * the armies based on the countries list.
	 * 
	 * @param player Current Player
	 * @param continent  Continent
	 * @return noOfArmies reinforcement armies
	 */
	 
	public int assignArmies(Player player, Continent continent) {
		int playerOwnedArmy = player.getMyCountries().size()/ 3;
		int noOfArmies = (int) playerOwnedArmy;
		playerOwnedCountries = player.getMyCountries();
		continentCountryList = continent.getListOfCountries();

		// Minimum number of armies for a player in case armies count is less
		// than 3.
		if (noOfArmies < 3) {
			noOfArmies = 3;
		}

		for (Country country : continentCountryList) {
			if (!playerOwnedCountries.contains(country)) {
				hasPlayerAllContinents = false;
				break;
			}
		}
		
		// If a player owns all the countries in a continent, then armies count will be equal to the control value of the continent.
		if (hasPlayerAllContinents) {
		  noOfArmies = continent.getControlValue();
	  }

		return noOfArmies;
	}
}
