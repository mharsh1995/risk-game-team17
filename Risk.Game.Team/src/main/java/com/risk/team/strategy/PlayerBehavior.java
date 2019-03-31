package com.risk.team.strategy;

import java.util.List;
import java.util.stream.Collectors;

import com.risk.team.model.Continent;
import com.risk.team.controller.RiskMapRW;
import com.risk.team.model.Country;
import com.risk.team.model.Player;


/**
 * 
 * PlayerBehavior class contains methods for the Player Behavior.
 * There are five player behaviors 
 * i.e. Human, aggressive, benevolent, random and cheater
 * 
 * @author Harsh Mehta
 * @author yashgolwala
 */

public abstract class PlayerBehavior {

	/**
	 * Method for reinforcement phase. 
	 * Start and end of the reinforcement phase. 
	 * @param myCountries List of countries owned by the player.
	 * @param country Country to which reinforcement armies are to be assigned.
	 * @param currentPlayer Current player.
	 */

	abstract public void reinforcementPhase(List<Country> myCountries, Country country, Player currentPlayer);

	/**
	 * 
	 * Method for attack phase.
	 * 
	 * @param attackingCountryList List of countries attacking.
	 * @param defendingCountryList List of countries defending.
	 * @param currentPlayer Current player.
	 *            
	 */
	abstract public void attackPhase(List<Country> attackingCountryList, List<Country> defendingCountryList, Player currentPlayer);

	/**
	 * 
	 * Method for fortification phase. 
	 * Start and end of the fortification phase. 
	 * 
	 * @param selectedCountry List of countries selected by the player.
	 * @param adjacentCountry List of adjacent countries.
	 * @param playerPlaying Current player.
	 * @return true  If the fortification successful; other wise false.
	 * 
	 */ 

	abstract public boolean fortificationPhase(List<Country> selectedCountry, List<Country> adjacentCountry,
			Player playerPlaying);

	/**
	 * 
	 * Method to get list of defending countries. 
	 * 
	 * @param attackingCountry Attacking country.
	 * @return defendingCountries  List of defending countries.
	 * 
	 */
	public List<Country> getDefendingCountryList(Country attackingCountry) {
		List<Country> defendingCountries = attackingCountry.getAdjacentCountries().stream().filter(t -> (attackingCountry.getPlayer() != t.getPlayer())).collect(Collectors.toList());

		return defendingCountries;
	}
	
	/**
     * 
	 * Method for if player can attack.
	 * 
	 * @param myCountries List of countries owned by the player.
	 *   
	 * @return true  If player can attack; other wise false.
     *            
	 */    
    abstract public boolean playerCanAttack(List<Country> myCountries);
    
    /**
     * 
	 * Method for to check if fortification phase is valid.
	 * 
	 * @param map RiskMapRW Object.
	 * @param playerPlaying Player playing.
	 *   
	 * @return true If fortification phase is valid; other wise false.
     *            
	 */ 
    public boolean isFortificationPhaseValid(RiskMapRW map, Player playerPlaying) {
        boolean isFortificationAvailable = false;
        outer:
        for (Continent continent : map.getMapGraph().getContinents().values()) {
            for (Country Country : continent.getListOfCountries()) {
                if (Country.getPlayer().equals(playerPlaying)) {
                    if (Country.getNoOfArmies() > 1) {
                        for (Country adjCountry : Country.getAdjacentCountries()) {
                            if (adjCountry.getPlayer().equals(playerPlaying)) {
                                isFortificationAvailable = true;
                                break outer;
                            }
                        }
                    }
                }
            }
        }

        return isFortificationAvailable;
    }


}