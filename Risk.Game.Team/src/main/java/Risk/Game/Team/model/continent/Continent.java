package Risk.Game.Team.model.continent;

import java.io.Serializable;
import java.util.List;

import Risk.Game.Team.model.country.Country;

/**
 * Continent Class get set all the properties of continent.
 * 
 * @author KartikaPatil YashGolwala
 */

 public class Continent implements Serializable{

    private int numberOfCountries;
    private int continentControlValue;
    private String continentName;
    private List<Country> countries1;

    /**
     * default constructor
     */
    public Continent(){

    }
    /**
     * This constructor creates the Continent by taking Continent Name and the countries in that continent as parameters.
     * 
     * @param continentName This is the name of the Continent 
     * @param countries This is the list of countries in that Continent
     */
    public Continent(String continentName, List<Country> countries){
        this.continentName = continentName;
        this.countries1 = countries;
    }
    /**
     * This constructor assigns control value to the continent name.
     * 
     * @param continentName This is the name of Continent
     * @param continentControlValue This is the Control Value of its Continent
     */
    public Continent(String continentName, int continentControlValue){
        this.continentName = continentName;
        this. continentControlValue = continentControlValue;
    }
    /**
     * This constructor creates the continent with the given name
     * 
     * @param continentName name of the continent
     */
    public Continent(String continentName){
        this.continentName = continentName;
    }

    /**
     * method to get the name of the continent 
     * 
     * @return String 
     */
    public String getContinentName(){
        return continentName;
    }
    /**
     * method to set the continent name
     * 
     * @param continentName name of the continent
     */
    public void setContinentName(String continentName){
        this.continentName = continentName;
    }
    /**
     * method to get the list of countries which belong to the continent
     * 
     * @return countries list of countries in that continent
     */
    public List<Country> getCountries(){
        return countries1;
    }
    /**
     * method to set the countries for that continent
     * 
     * @param countries 
     */
    public void setCountries<List<Country> countries){
        this.countries1 = countries1;
    }
    /**
     * method to get the number of countries in this continent
     * 
     * @return numberOfCountries
     */
    public int getNumberOfCountries(){
        return numberOfCountries;
    }
    /**
     * method to set number of Countries in that continent
     * 
     * @param numberOfCountries
     */
    public set numberOfCountries(int numberOfCountries){
        this.numberOfCountries = numberOfCountries;
    }
    /**
     * method to get the Continent's Control Value
     * 
     * @return continentControlValue
     */
    public get continentControlValue(){
        return continentControlValue;
    }
    /**
     * method to set the control value for the continent
     * 
     * @param continentControlvalue control value of that continent
     */
    public set continentControlValue(int continentControlValue){
        this.continentControlValue = continentControlValue;
    }

 }