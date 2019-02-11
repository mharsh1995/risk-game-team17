package Risk.Game.Team.model.country;

import java.io.Serializable;
import java.util.List;

import Risk.Game.Team.model.player.Player;
/**
 * Class to get and set country data for game play.
 *
 * @author KartikaPatil YashgGolwala
 */

public class Country implements Serializable{

	private static final long serialVersionUID = 1L;
	private static final String Name = null;
	private String countryName;
	private int pixelStartPoint;
	private int pixelEndPoint;
	private int numberOfArmy;
	private List<Country> adjacentCountries;
	private Player occupant;
	private String belongsToContinent;

	/**
	 * Country default constructor.
	 */
	public Country(){
	} 
	
	/**
	 * Constructor given country Name
	 * @param name name of the country
	 */
	 public Country(String countryName) {
		this.countryName = countryName;
	}

	/**
	 * Contructor with given parameters
	 * 
	 * @param countryName name of the country
	 * @param belongsToContinent name of the continent to which it belongs to
	 */
	 public Country(String countryName, String belongsToContinent){
		this.countryName = countryName;
		this.belongsToContinent = belongsToContinent;
	}
	
	/**
	 * Constructor with given parameters
	 * 
	 * @param countryName Name of the Country
	 * @param pixelStartPoint For displaying the map 
	 * @param pixelEndPoint For displaying the map
	 * @param belongsToContinent Name of the Continent
	 */
	public Country(String countryName, int pixelStartPoint, int pixelEndPoint, String belongsToContinent) {
		this.countryName = countryName;
		this.pixelStartPoint = pixelStartPoint;
		this.pixelEndPoint = pixelEndPoint;
		this.belongsToContinent = belongsToContinent;
	}
	/**
	 * Get country name
	 *
	 * @return The country name
	 */
	public String getCountryName() {
		return countryName;
	}

	/**
	 * method to set country name
	 *
	 * @param countryName The name to set
	 */
	public void setName(String countryName) {
		this.countryName = Name;
	}
	
	/**
	 * method to get the List of adjacent countries
	 * 
	 * @return adjacentCountries List of neighboring countries
	 */
	public List<Country> getAdjacentCountries() {
		return adjacentCountries;
	}

	/**
	 * method to set the adjacent countries of this particular country
	 * 
	 * @param adjacentCountries  List of neighboring countries
	 */
	public void setAdjacentCountries(List<Country> adjacentCountries) {
		this.adjacentCountries = adjacentCountries;
	}
	
	/**
	 * method to get the continent of that country
	 * 
	 * @return belongsToContinent Name of the continent to which the country belongs
	 */
	public String getBelongsToContinent() {
		return belongsToContinent;
	}

	/**
	 * method to set the continent to which the country belongs
	 * 
	 * @param belongsToContinent  Name of the continent to which the country belongs
	 */
	public void setBelongsToContinent(String belongsToContinent) {
		this.belongsToContinent = belongsToContinent;
	}
	
	/**
	 * method to get the Pixel start point
	 * 
	 * @return pixelStartPoint start Coordinate of the country
	 */
	public int getPixelStartPoint() {
		return pixelStartPoint;
	}
	
	/**
	 * method to set the Pixel start point
	 * 
	 * @param pixelStartPoint start Coordinate of the country
	 */
	public int setPixelStartPoint(int pixelStartPoint) {
		return this.pixelStartPoint = pixelStartPoint;
	}

	/**
	 * method to get the Pixel end point
	 * 
	 * @return pixelEndPoint ending Coordinate of the country
	 */
	public int getPixelEndPoint() {
		return pixelEndPoint;
	}

	/**
	 * method to set the Pixel end point
	 * 
	 * @param pixelEndPoint End Coordinate of the country
	 */
	public int setPixelEndPoint(int pixelEndPoint) {
		return this.pixelEndPoint = pixelEndPoint;
	}

	/**
	 * get number of armies
	 * @return numberOfArmy number of armies
	 */
	public int getNumberOfArmy(){
		return numberOfArmy;
	}

	/**
	 * set number of armies
	 * @param numberOfArmy number of armies
	 */
	public void setNumberOfArmy(int numberOfArmy){
		this.numberOfArmy = numberOfArmy;
	}
	/**
	 * get the occupant's name to whome does the country belongs.
	 * 
	 * @return Player occupant to which this country belongs.
	 */
	public Player getOccupant() {
		return occupant;
	}

	/**
	 * sets the occupant's name to which the country belongs.
	 * 
	 * @param occupant The player to which this country belongs.
	 */
	public void setOccupant(Player occupant) {
		this.occupant = occupant;
	}
	@Override
	public boolean equals(Object object){
		if(!(object instanceof Country)){
			return false;
		}
		Country country = (Country) object;
		return country.getCountryName().equals(getCountryName());
	}
}