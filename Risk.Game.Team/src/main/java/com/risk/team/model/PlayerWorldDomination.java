package com.risk.team.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map.Entry;

import com.risk.team.services.RiskMapRW;

import java.util.Observable;

/**
 * 
 * Players world domination view class.
 * Display:
 * (1) the percentage of the map controlled by every player
 * (2) the continents controlled by every player 
 * (3) the total number of armies owned by every player.
 * 
 * @author Jenny Pujara
 * 			  
 */
public class PlayerWorldDomination extends Observable implements Serializable {

	/**
	 * 
	 * Method for PlayerWorldDomination class to generate world domination data.
	 * 
	 * @param map RiskMapRW object		  
	 * 
	 * @return playerTerPercent Percentage of the map controlled by every player
	 * 			  	  
	 */
	public HashMap<Player, Double> generateWorldDominationData(RiskMapRW map) {

		HashMap<Player, Double> playerCountryCount = new HashMap<>();
		Double countryCount = 0.0;
		for (Continent cont : map.getMapGraph().getContinents().values()) {
			for (Country ter : cont.getListOfCountries()) {
				countryCount++;
				Player player = ter.getPlayer();
				if(playerCountryCount.containsKey(player)) {
					playerCountryCount.put(player, playerCountryCount.get(player)+1);
				} else {
					playerCountryCount.put(player, Double.valueOf("1"));
				}
			}
		}

		HashMap<Player, Double> playerTerPercent = new HashMap<>();
		for(Entry<Player, Double> entry : playerCountryCount.entrySet()) {
			playerTerPercent.put(entry.getKey(), (entry.getValue()/countryCount * 100));
		}
		return playerTerPercent;
	}

}
