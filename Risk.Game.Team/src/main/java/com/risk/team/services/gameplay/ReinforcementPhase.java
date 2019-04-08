package com.risk.team.services.gameplay;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.risk.team.model.Continent;
import com.risk.team.model.Country;
import com.risk.team.model.Player;

/**
 * 
 * ReinforcementPhase class contains the methods used in the reinforcement phase
 * gameplay.
 * 
 * @author Jenny Pujara
 */
public class ReinforcementPhase {

	boolean isPlayerOwnedContinent = true;
	ArrayList<Country> playerOwnedContries;
	ArrayList<Country> countriesInContient;
	Set<Continent> countryInContinent;

	/**
	 * Method to find the number of countries owned by the player and to assign
	 * the armies based on the countries list.
	 * 
	 * @param player Current Player
	 * @return reinforcement armies
	 */
	public int findNoOfArmies(Player player) {
		int playerCountriesCount = player.getPlayerCountries().size();
		int armiesCount = (int) Math.floor(playerCountriesCount / 3);
		countryInContinent = new HashSet<>();
		playerOwnedContries = player.getPlayerCountries();

		for(Country country : playerOwnedContries){
			countryInContinent.add(country.getPartOfContinent());
		}

		System.out.println(countryInContinent);


		for(Continent continent : countryInContinent){
			isPlayerOwnedContinent = true;
			for(Country country: continent.getListOfCountries()){
				if(!playerOwnedContries.contains(country)){
					isPlayerOwnedContinent = false;
					break;
				}
			}
			if (isPlayerOwnedContinent) {
				armiesCount += continent.getControlValue();
			}
		}


		if (armiesCount < 3) {
			armiesCount = 3;
		}

		return armiesCount;
	}
}
