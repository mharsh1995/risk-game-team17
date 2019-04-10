package com.risk.team.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.risk.team.model.Continent;
import com.risk.team.model.Country;

/**
 * This MapGraph class contains all the methods to add , remove countries ,
 * continents and also to add , delete edges between countries. This also
 * contains methods to set and get the adjacent countries list.
 * 
 * @author Kartika Patil
 * @author Dhaval Desai
 * 
 * @version 1.0.0
 */
public class RiskMapGraph implements Serializable {

	/** HashMap to store the continent names */
	private HashMap<String, Continent> continents;

	/** HashMap to store the list of adjacent countries */
	private HashMap<Country, ArrayList<Country>> adjacentCountries;

	/** HashMap for set of countries */
	private HashMap<String, Country> allCountries;

	/** Count of the number of countries */
	private int countOfCountries = 0;

	/**
	 * MapGraph constructor
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
	 *            countries which are adjacent to eachother
	 */
	public void setAdjacentCountries(HashMap<Country, ArrayList<Country>> adjacentCountries) {
		this.adjacentCountries = adjacentCountries;
	}

	/**
	 * Method to get the count of the countries.
	 * 
	 * @return No of countries
	 */
	public int getCountOfCountries() {
		return countOfCountries;
	}

	/**
	 * Method to set the count of the countries.
	 * 
	 * @param countOfCountries
	 *            Number of countries
	 */
	public void setCountOfCountries(int countOfCountries) {
		this.countOfCountries = countOfCountries;
	}

	/**
	 * Method to get the countries from a set
	 * 
	 * @return countrySet
	 */
	public HashMap<String, Country> getAllCountries() {
		return allCountries;
	}

	/**
	 * Method to the set the country in a set.
	 * 
	 * @param allCountries
	 *            Hashset to store country names
	 */
	public void setAllCountries(HashMap<String, Country> allCountries) {
		this.allCountries = allCountries;
	}

	/**
	 * Method to add an edge between countries.
	 * 
	 * @param source
	 *            Country
	 * 
	 * @param destination
	 *            Country
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
	 * Method to delete an edge between countries
	 * 
	 * @param source
	 *            Country
	 * 
	 * @param destination
	 *            Country
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
	 * Method to add a country to the Graph
	 * 
	 * @param country
	 *            Object
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
	 *            Object
	 * 
	 * @return True if country is removed successfully
	 */
	public boolean removeCountry(Country country) {
		if (adjacentCountries.containsKey(country)) {
			ArrayList<Country> neighbours = adjacentCountries.get(country);
			for (Country adjacentCountry : neighbours) {
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
	 *            Object
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
				ArrayList<Country> neighbours = adjacentCountries.get(continent.getListOfCountries().get(i));
				for (Country adjCountry : neighbours) {
					adjacentCountries.get(adjCountry).remove(continent.getListOfCountries().get(i));
				}
				adjacentCountries.remove(continent.getListOfCountries().get(i));
				allCountries.remove(continent.getListOfCountries().get(i).getName());
			}
			continents.remove(continent.getName());
			return true;
		}
		return false;
	}

	/**
	 * Method to check if there exists an adjace ncy between two countries
	 * 
	 * @param country1
	 *            Object
	 * 
	 * @param country2
	 *            Object
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
