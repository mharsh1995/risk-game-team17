package com.Risk.Team.Controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import com.Risk.Team.model.*;


/**
 * The RiskGraphConnection class contains method to check weather risk map graph is connected or not.
 * 
 * 
 * @author Kartika Patil
 * @author Jenny
 * 
 */


public class RiskGraphConnected {
	
	/** HashMap of countries to check country is visited or not */
	
	private HashMap<Country, Boolean> visitedcountries;
	
	/** Set of allCountries of a MapGraph*/

	private Set<Country> countrySet;
	
	/**
	 * Constructor initializing visitedcountries member values to false
	 * 
	 * @param countrySet
	 * 					Set containing all countries for Game Map
	 */
	
	public RiskGraphConnected(Set<Country> countrySet)
	{
		this.countrySet = countrySet;
		this.visitedcountries = new HashMap<>();
		Iterator<Country> citerator = this.countrySet.iterator();
		while (citerator.hasNext()) {
			
		}
			visitedcountries.put(citerator.next(), false);
	}

	
	// Depth Search First traversal for Map graph 
	
	public boolean isGraphConnected() {
		
				
		return true;
		
	}
	
	
	// fortification path connection method to be added
	
}

	