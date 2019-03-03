package com.risk.team.controller;

import java.util.ArrayList;
import java.util.HashMap;

import com.risk.team.model.*;

/**
 * RiskMapGraph class contains information about Map Graph in Risk Game.
 * Class has information about countries , continents , adjacent countries list and count of each.
 * Class also contains methods to set , get and manipulation (add,delete) of corresponding objects.
 * Class also contains methods to check adjacent countries.
 * @author Kartika Patil
 *
 */

public class RiskMapGraph {
	
	/** HashMap to store names of all the continent */
	private HashMap<String, Continent> continents;

	/** HashMap to store the list of adjacent countries */
	private HashMap<Country, ArrayList<Country>> adjacentCountries;

	/** HashMap for set of all countries with key as a name and Country object as a value*/
	private HashMap<String, Country> allCountries;

	/** Count of the total countries in the game */
	private int totalCountries = 0;
	
	/**
	 * RiskMapGraph constructor
	 */
	public RiskMapGraph() {
		this.continents = new HashMap<>();
		this.adjacentCountries = new HashMap<>();
		this.allCountries = new HashMap<>();
	}
	
	/**
	 * Method to get the continents
	 * 
	 * @return continents
	 */
	public HashMap<String, Continent> getContinents() {
		return continents;
	}

	/**
	 * Method to set a continent
	 * 
	 * @param continents
	 *            Name of the continent
	 */
	public void setContinents(HashMap<String, Continent> continents) {
		this.continents = continents;
	}

	/**
	 * Method to get the list of adjacent countries.
	 * 
	 * @return adjacent countries
	 */
	public HashMap<Country, ArrayList<Country>> getAdjacentCountries() {
		return adjacentCountries;
	}

	/**
	 * Method to set the adjacent countries
	 * 
	 * @param adjacentCountries
	 *            countries which are adjacent to each other
	 */
	public void setAdjacentCountries(HashMap<Country, ArrayList<Country>> adjacentCountries) {
		this.adjacentCountries = adjacentCountries;
	}
	
	/**
	 * Method to get the count of total number of countries.
	 * 
	 * @param totalCountries
	 *            total number of countries
	 */
	public int getTotalCountries() {
		return totalCountries;
	}
	/**
	 * Method to set the count of total number of countries.
	 * 
	 * @param totalCountries
	 *            total number of countries
	 */
	public void setTotalCountries(int totalCountries) {
		this.totalCountries = totalCountries;
	}
	
	/**
	 * Method to get all the countries from a set
	 * 
	 * @return allCountries
	 */
	public HashMap<String, Country> getAllCountries() {
		return allCountries;
	}

	/**
	 * Method to the set the country in a set.
	 * 
	 * @param allCountries
	 *            Hashset to store country names and country objects.
	 */
	public void setCountrySet(HashMap<String, Country> allCountries) {
		this.allCountries = allCountries;
	}

	/**
	 * Method to add an edge/Link between countries considering map as undirected graph.
	 * 
	 * @param source
	 *            Country
	 * 
	 * @param destination
	 *            Country
	 * 
	 * 
	 */
	public void addLinkBetweenCountries(Country source, Country destination) {
		if (adjacentCountries.containsKey(source)) {
			adjacentCountries.get(source).add(destination);
		}

		if (adjacentCountries.containsKey(destination)) {
			adjacentCountries.get(destination).add(source);
		}
	}
	
	/**
	 * Method to delete an edge/Link between countries considering map as undirected graph.
	 * 
	 * @param source
	 *            Country
	 * 
	 * @param destination
	 *            Country
	 * 
	 */
	public void deleteLinkBetweenCountries(Country source, Country destination) {
		if (adjacentCountries.containsKey(source)) {
			adjacentCountries.get(source).remove(destination);
		}

		if (adjacentCountries.containsKey(destination)) {
			adjacentCountries.get(destination).remove(source);
		}
	}

	/**
	 * Method to add a country to continent in map.
	 * 
	 * @param country
	 *            country Object
	 * 
	 */
	public void addCountry(Country country) {
		continents.get(country.getContinent()).addCountry(country);
		adjacentCountries.put(country, country.getAdjacentCountries());
		allCountries.put(country.getName(), country);
	}
	
	/**
	 * Method to remove a country
	 * 
	 * @param country
	 *            country Object
	 * 
	 * @return True if country is removed successfully
	 */
	public boolean removeCountry(Country country) {
		if (adjacentCountries.containsKey(country)) {
			ArrayList<Country> surround = adjacentCountries.get(country);
			for (Country adjacentCountry : surround) {
				adjacentCountries.get(adjacentCountry).remove(country);
			}
			adjacentCountries.remove(country);
			continents.get(country.getContinent()).getListOfCountries().remove(country);
			allCountries.remove(country.getName());
			return true;
		}
		return false;
	}
	
	/**
	 * Method to add a continent
	 * 
	 * @param continent
	 *            continent Object
	 * 
	 */
	public void addContinent(Continent continent) {
		continents.put(continent.getName(), continent);
	}
	
	/**
	 * Method to remove a continent
	 * 
	 * @param continent
	 *            Object
	 * 
	 * @return True if continent is removed successfully
	 * 
	 */
	public boolean removeContinent(Continent continent) {
		if (continents.containsKey(continent.getName())) {
			for (int i = 0; i < continent.getListOfCountries().size(); i++) {
				Country current_country = continent.getListOfCountries().get(i);
				ArrayList<Country> surround_countries = adjacentCountries.get(current_country);
				for (Country adjCountry : surround_countries) {
					adjacentCountries.get(adjCountry).remove(current_country);
				}
				adjacentCountries.remove(current_country);
				allCountries.remove(current_country.getName());
			}
			continents.remove(continent.getName());
			return true;
		}
		return false;
	}
	
	/**
	 * Method to check if two countries are adjacent to each other.
	 * 
	 * @param country1
	 *            country Object
	 * 
	 * @param country2
	 *            country Object
	 * 
	 * @return True if exists an adjacency
	 */
	public boolean checkAdjacencyOfCountries(Country country1, Country country2) {
		if (adjacentCountries.get(country1).contains(country2) && adjacentCountries.get(country2).contains(country1)) {
			return true;
		}
		return false;
	}


}
