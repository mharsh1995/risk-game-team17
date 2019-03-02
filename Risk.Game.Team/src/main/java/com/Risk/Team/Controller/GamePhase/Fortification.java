package com.Risk.Team.Controller.GamePhase;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.Risk.Team.Controller.RiskGraphConnected;
import com.Risk.Team.model.Country;
import com.Risk.Team.model.Player;

/**
 * 
 * Fortification class contains methods for the Fortification phase of
 * the game.
 * 
 * @author Jenny
 */
public class Fortification {
	
	/**Flag to validate if path exists or not between two countries*/
	public boolean pathFlag;	
	
	/**
	 * Fortification Constructor 
	 */
	public Fortification(){
		this.pathFlag = false;		
	}
	
	/**
	 * Method to move armies between countries owned by the player.
	 * 
	 * @param fromCountry country from which armies are to  be moved.
	 * @param toCountry country to which the armies are to be moved.
	 * @param noOfArmies number of armies to be moved.
	 * @param player Player who does fortification.
	 * @param allCountriescollection collection of all countries in the Map Graph
	 */
	public void moveArmies(Country fromCountry, Country toCountry, int noOfArmies,Player player,Collection<Country> allCountriescollection) {
		
		Set<Country> allCountries = new HashSet<Country>(allCountriescollection);
			
		Set<Country> playerOwnedCountries = new HashSet<Country>(player.getMyCountries());
		
		RiskGraphConnected rc = new RiskGraphConnected(allCountries);
		
		pathFlag = rc.ifPathExists(fromCountry,toCountry,playerOwnedCountries);		
			
			//if the flag is true then move armies from fromCountry to toCountry
			if(pathFlag) {
				fromCountry.setNoOfArmies(fromCountry.getNoOfArmies() - noOfArmies);
				toCountry.setNoOfArmies(toCountry.getNoOfArmies() + noOfArmies);
				for(Country country : playerOwnedCountries)
				{
				System.out.println("Number of armies assigned to country after fortification " + country.getName() + " :" + country.getNoOfArmies());
				}
			}
			else
			{				
				System.out.println("Source and Destination Country does not have adjacent path through countries owned by Player");
			}
			
		}
}