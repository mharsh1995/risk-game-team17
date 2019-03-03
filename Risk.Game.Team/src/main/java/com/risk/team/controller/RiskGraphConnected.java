package com.risk.team.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.risk.team.model.*;


/**
 * The RiskGraphConnection class contains method to check weather risk map graph is connected or not.
 * The class is used in Map Verification and Fortification Phase. 
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
	
	/**Flag to validate if path exists or not in fortification phase*/	
	
	boolean flag = false;
	
	/**
	 * Constructor initializing visited countries member values to false
	 * 
	 * @param countrySet
	 * Set containing all countries for Game Map
	 */
	
	ArrayList<Country> countryPathList;
	 
	public RiskGraphConnected(Set<Country> countrySet)
	{
		this.countrySet = countrySet;
		this.visitedcountries = new HashMap<>();
		Iterator<Country> citerator = this.countrySet.iterator();
		while (citerator.hasNext()) {
			
			
			visitedcountries.put(citerator.next(), false);}
	}

	/**
	 * Depth Search First traversal for Map graph 
	 * 
	 * @param start_country
	 * Intial country for start DFS traversal
	 */
		
	private void dfsTraversal(Country start_country){

		visitedcountries.put(start_country,true);
		
	    Iterator<Country> adjiterator = start_country.getAdjacentCountries().iterator();

	    while (adjiterator.hasNext())
	    {
			Country adjacentCountry = adjiterator.next();
			if (!visitedcountries.get(adjacentCountry))
			{
				dfsTraversal(adjacentCountry);
			}
	    }

	}
	
	/**
	 * Depth Search First traversal for Map graph for fortification.
	 * 
	 * @param s_country 
	 * 					country from where armies to be moved in fortification;
	 * @param d_country 
	 * 					country to where armies are moved in fortification;
	 * @param playerowncountries 
	 * 					set of countries owned by the current player;
	 * @return nothing
	 * 						
	 */
	
	private void dfsTraversal2(Country s_country,Country d_country,Set playerowncountries){

		visitedcountries.put(s_country ,true);
		
		
		if(s_country.toString().equals(d_country.toString()))
		{
			flag = true;
		}
			
	    Iterator<Country> adjiterator = s_country.getAdjacentCountries().iterator();

	    while (adjiterator.hasNext())
	    {
	    	Country adjacentCountry = adjiterator.next();
	    	
			if (!visitedcountries.get(adjacentCountry) && playerowncountries.contains(adjacentCountry) )
			{
				dfsTraversal2(adjacentCountry,d_country,playerowncountries);
			}	   
		}
	     
	}
	
	/**
	 * Main method checking weather Risk Game Map graph is connected or not
	 * 
	 * @return
	 * 			true if map is connected else it returns false;
	 */
	
	public boolean isGraphConnected() {
		
		dfsTraversal(countrySet.iterator().next());
		Iterator<Country> iterator = countrySet.iterator();
		while (iterator.hasNext()) {
			Country country = iterator.next();
			if (visitedcountries.get(country) == false) {
				System.out.println("Risk Game MapGraph is not a connected graph.");
				return false;

			}

		}
		
		return true;
		
	}
	
	/**
	 * Method checking weather Risk Game Map graph is connected or not through adjacent countries in fortification Phase.
	 * 
	 * @param fromCountry 
	 * 					country from where armies to be moved in fortification;
	 * @param toCountry 
	 * 					country to where armies are moved in fortification;
	 * @param playerowncountries 
	 * 					set of countries owned by the current player;
	 * @return
	 * 			true if map is connected else it returns false;
	 */
	
	
	 public boolean ifPathExists(Country fromCountry, Country toCountry,Set playerowncountries) { 
		
		 if (countrySet.contains(fromCountry)) {
			 dfsTraversal2(fromCountry,toCountry,playerowncountries);		 
		  }
		 
		 if(flag){
			 return true;
		 }
		 else {	 
			 return false;
		 }
	} 
	 
}

	