package com.risk.team.services;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import com.risk.team.model.Country;

/**
 * Class containing the method to check whether a map is a connected graph or
 * not
 *
 * @author Kartika Patil
 * @author jenny
 * 
 * @version 1.0.0
 */

public class RiskGraphConnected implements Serializable {

	/**
	 * HashMap for marking a country as visited or not
	 */
	private HashMap<Country, Boolean> visitedcountries;

	/**
	 * Set containing countries
	 */
	private Set<Country> countrySet;

	boolean flag = false;

	/**
	 * Constructor for ConnectedGraph class, which sets initial data for the
	 * class. Moreover, it sets a the countries visited value as false.
	 *
	 * @param countrySet Set containing all the countries.
	 */
	public RiskGraphConnected(Set<Country> countrySet) {
		this.countrySet = countrySet;
		this.visitedcountries = new HashMap<>();
		Iterator<Country> iterator = this.countrySet.iterator();
		while (iterator.hasNext()) {
			visitedcountries.put(iterator.next(), false);
		}
	}

	/**
	 * Method for traversing the graph for depth first traversal.
	 *
	 * @param startCountry Starting country
	 */
	private void depthFirstTraversal(Country startCountry) {
		visitedcountries.put(startCountry, true);
		Iterator<Country> it = startCountry.getAdjacentCountries().iterator();
		while (it.hasNext()) {
			Country adjacentCountry = it.next();
			if (!visitedcountries.get(adjacentCountry)) {
				depthFirstTraversal(adjacentCountry);
			}
		}
	}

	/**
	 * Method for checking if the graph is connected or not.
	 *
	 * @return true if map is connect; else false.
	 */
	public boolean isConnected() {
		depthFirstTraversal(countrySet.iterator().next());
		Iterator<Country> it = countrySet.iterator();
		while (it.hasNext()) {
			Country country = it.next();
			if (visitedcountries.get(country) == false) {
				System.out.println("Map is not a connected graph.");
				return false;
			}
		}
		return true;
	}

	/**
	 * Method to check if graph is connected.
	 * 
	 * @return true or false
	 */
	public boolean isConnectedSubGraph() {
		depthFirstTraversalSubGraph(countrySet.iterator().next());
		Iterator<Country> it = countrySet.iterator();
		while (it.hasNext()) {
			Country country = it.next();
			if (visitedcountries.get(country) == false) {
				System.out.println("Continent " + country.getContinent() + " is not connected.");
				return false;
			}
		}
		return true;
	}

	/**
	 * Method to traverse subGraph in depth first pattern.
	 * 
	 * @param startCountry	starting country
	 */
	private void depthFirstTraversalSubGraph(Country startCountry) {
		visitedcountries.put(startCountry, true);
		Iterator<Country> it = startCountry.getAdjacentCountries().iterator();
		while (it.hasNext()) {
			Country adjacentCountry = it.next();
			if (countrySet.contains(adjacentCountry)) {
				if (!visitedcountries.get(adjacentCountry)) {
					depthFirstTraversalSubGraph(adjacentCountry);
				}
			}
		}
	}


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
