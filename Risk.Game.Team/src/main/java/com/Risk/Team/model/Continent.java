package com.Risk.Team.model;
import java.util.ArrayList;

import com.Risk.Team.model.Country;

/**
 * Continent class provides data regarding the Continents
 * 
 * @author Kartika Patil
 */
public class Continent {

	/** Name of the continent */
	private String name;

	/** List of countries in the continent */
	private ArrayList<Country> listOfCountries;
	
	/** Control Value of continent. */
	private int controlValue;

	/**
	 * Continent constructor
	 * 
	 * @param name continent name
	 * @param controlValue control value of that continent
	 */
	public Continent(String name, int controlValue) {
		this.name = name;
		this.controlValue = controlValue;
		listOfCountries = new ArrayList<Country>();
	}

	/**
	 * Get method for getting the continent name
	 * 
	 * @return name name of the continent
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set method for setting the continent name
	 * 
	 * @param name To set the continent name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get the control value of the continent.
	 * 
	 * @return control value of continent
	 * 
	 */
	public int getControlValue() {
		return controlValue;
	}

	/**
	 * Set the control value of the continent.
	 * 
	 * @param controlFlag
	 *            the control value to set
	 */
	public void setControlValue(int controlValue) {
		this.controlValue = controlValue;
	}
	
	/**
	 * Method to get the list of countries held by the continent
	 * 
	 * @return listOfCountries list of countries in that continent 
	 * 
	 */
	public ArrayList<Country> getListOfCountries() {
		return listOfCountries;
	}

	/**
	 * Method to add a country to the list of countries in the continent
	 * 
	 * @param country name of country to be added
	 */
	public void addCountry(Country country) {
		listOfCountries.add(country);
	}

	/**
	 * Set the list of countries.
	 * 
	 * @param listOfCountries list of countries to set
	 */
	public void setListOfCountries(ArrayList<Country> listOfCountries) {
		this.listOfCountries = listOfCountries;
	}

	/**
	 * Method to remove any country from list of countries in the continent
	 * 
	 * @param country country name that is to be deleted
	 */
	public void deleteCountry(Country country) {
		listOfCountries.remove(country);
	}

	/**
	 * {@inheritDoc} check if names of two continents are same
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof Continent))
			return false;
		return this.getName().toLowerCase().equals(((Continent) obj).getName().toLowerCase());
	}

	/**
	 * {@inheritDoc} it returns the hashCode for continent's name.
	 */
	@Override
	public int hashCode() {
		return getName().toLowerCase().hashCode();
	}
}
