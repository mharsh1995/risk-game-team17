package com.risk.team.model;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Observable;

import com.risk.team.controller.RiskMapRW;

/**Class to calculate percentage of territories owned by player across the world
 * @author Jenny */

public class PlayerWorldDomination extends Observable {
	
	/** method to retrieve the data for world domination
	 * @param RiskMapRW map
	 * @return playerOwnedTerPercent */

	public HashMap<Player, Double> fetchWorldDominationDetails(RiskMapRW map) {

		HashMap<Player, Double> playerOwnedCountryCount = new HashMap<>();
		Double totalCountries = 0.0;
		for (Continent cont : map.getMapGraph().getContinents().values()) {
			for (Country ter : cont.getListOfCountries()) {
				totalCountries++;
				Player player = ter.getPlayer();
				if(!playerOwnedCountryCount.containsKey(player)) {
					playerOwnedCountryCount.put(player, Double.valueOf("1"));					
				} else {
					playerOwnedCountryCount.put(player, playerOwnedCountryCount.get(player)+1);					
				}
			}
		}

		HashMap<Player, Double> playerOwnedTerPercent = new HashMap<>();
		for(Entry<Player, Double> entry : playerOwnedCountryCount.entrySet()) {
			playerOwnedTerPercent.put(entry.getKey(), (entry.getValue()/totalCountries * 100));
		}
		return playerOwnedTerPercent;
	}

}
