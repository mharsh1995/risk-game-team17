
import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * This class sets up the countries and continents on the Risk game RiskBoard.
 * 
 * @author Kartika Patil

 **/
public class RiskBoard {

	private int i;
	private int j;
	
	private boolean isLoaded;
	private boolean isAdjacent;
	
	private String name;
	
	private String[] continentsArray;
	private String[] adjacenciesArray;
	
	private ArrayList<Country> countriesList;
	private ArrayList<Country> adjacenciesList;
	private ArrayList<Country> memberCountries;
	private ArrayList<Country> unoccupied;
	private ArrayList<Continent> continentsList;

    private HashMap<String, Country> countriesMap;
    private HashMap<String, Continent> continentsMap;
	
    public RiskBoard() {

    }

    /**
     * Loads the information needed to construct the RiskBoard and constructs the country and continent objects
     * needed for the RiskBoard from three files.  The first file lists all the countries.  The second file lists 
     * all of the continents and which countries are on a given continent.  The third file lists the adjacencies 
     * for each country.
     **/
    public boolean loadRiskBoard(String[] countriesArray, String[] adjacenciesFileArray, String[] continentsFileArray) {
		
		isLoaded = false;
	
		countriesMap = new HashMap<String, Country>();
		continentsMap = new HashMap<String, Continent>();
		
		//to be coded 
		
		
		isLoaded = true;
		
		return isLoaded;
	}

    /**
     * Returns a list containing the continent objects the RiskBoard has
     * */
    public ArrayList<Continent> getContinents() {
		return new ArrayList<Continent>(continentsMap.values());
    }


    /**
     * Returns the continent object whose name is the string continentName
     **/
    public Continent getContinentByName(String continentName) {
		return continentsMap.get(continentName);
    }


    /**
     * Returns the number of bonus armies awarded to a player for controlling all the countries in
     * the continent whose name is the string continentName
     **/
    

    /**
     * Returns a list of the country objects that are in the continent specified 
     * by the string continentName
     **/
    public ArrayList<Country> getMemberCountries(String continentName) {
		return continentsMap.get(continentName).getListOfCountries();
    }


    /**
     * Returns a list of all of the country objects in the RiskBoard
     **/
    public ArrayList<Country> getCountries() {
		return countriesList;
    }

    /**
     * Returns the country object for the country specified by the string
     * countryName
     **/
    public Country getCountryByName(String countryName) {
		return countriesMap.get(countryName);
    }


    /**
     * Sets the occupant of the country object specified by the string countryName
     * to be the player object supplied as an argument.
     **/
    public void setOccupant(String countryName, Player occupant) {
		countriesMap.get(countryName).setOccupant(occupant);
    }


    /**
     * Returns the player object that currently occupies the country specufied by
     * string countryName
     **/
    public Player getOccupant(String countryName) {
		return countriesMap.get(countryName).getOccupant();
    }


    /**
     * Sets the number of armies currently in the country specified by the string
     * countryName to the integer supplied as an argument
     **/
    public void setNumberArmies(String countryName, int numberArmies) {
		countriesMap.get(countryName).setNoOfArmies(numberArmies);
    }


    /**
     * Returns the number of armies currently in the country specified by the string
     * countryName
     **/
    public int getNumberArmies(String countryName) {
		return countriesMap.get(countryName).getNoOfArmies();
    }


    /**
     * Returns a list of the country objects that are the countries adjacent to the country
     * specified by the string countryName on the RiskBoard
     **/
    public ArrayList<Country> getAdjacencies(String countryName) {
		return countriesMap.get(countryName).getAdjacentCountries();
    }
	
	public ArrayList<Country> getUnoccupied() {

		unoccupied = new ArrayList<Country>();
		
		for (i = 0; i < countriesList.size(); i++) {
		
			if (countriesList.get(i).gethasPlayer() == false) {	
				unoccupied.add(countriesList.get(i));
			}
		}
		return unoccupied;
	}
	
	public boolean checkAdjacency(String countryA, String countryB) {
		if (countriesMap.get(countryA).getAdjacentCountries().contains(countriesMap.get(countryB))) {
			isAdjacent = true;
		} else {
			isAdjacent = false;
		}
		
		return isAdjacent;
	}
}