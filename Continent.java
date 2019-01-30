
import java.util.ArrayList;

/**
 * Continent class which provides information regarding the Continents.
 * 
 * @author Kartika Patil
 */
public class Continent {

	/** Name of the continent. */
	private String name;

	/** List of countries in the continent. */
	private ArrayList<Country> listOfCountries;

	/**
	 * Continent constructor
	 * 
	 * @param name
	 *            name of the continent
	 * @param controlValue
	 *            control value of the continent
	 */
	public Continent(String name, int controlValue) {
		this.name = name;
		listOfCountries = new ArrayList<Country>();
	}

	/**
	 * Get the continent name.
	 * 
	 * @return name name of the continent
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the continent name.
	 * 
	 * @param name
	 *            To set the continent name
	 */
	public void setName(String name) {
		this.name = name;
	}

	
	/**
	 * Method to get the list of countries held by the continent.
	 * 
	 * @return list of countries
	 * 
	 */
	public ArrayList<Country> getListOfCountries() {
		return listOfCountries;
	}

	/**
	 * Method to add a country to the list of countries in the continent
	 * 
	 * @param country
	 *            country to be added
	 */
	public void addCountry(Country country) {
		listOfCountries.add(country);
	}

	/**
	 * Set the list of countries.
	 * 
	 * @param listOfCountries
	 *            list of countries to set
	 */
	public void setListOfCountries(ArrayList<Country> listOfCountries) {
		this.listOfCountries = listOfCountries;
	}

	/**
	 * Method to remove country from the list of countires in the continent.
	 * 
	 * @param country
	 *            country to be deleted
	 */
	public void deleteCountry(Country country) {
		listOfCountries.remove(country);
	}

	/**
	 * {@inheritDoc} Used to check if names of two continents are same.
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
	 * {@inheritDoc} returns the hashcode for continent's name.
	 */
	@Override
	public int hashCode() {
		return getName().toLowerCase().hashCode();
	}
}
