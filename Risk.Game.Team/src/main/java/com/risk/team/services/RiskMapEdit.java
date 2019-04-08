package com.risk.team.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.risk.team.model.Continent;
import com.risk.team.model.Country;


/**
 * Class proving the m=functionality for creating a new map or edit existing map
 * according the risk game rules.
 * 
 * @author yashgolwala
 * @author Harsh Mehta
 */
public class RiskMapEdit implements Serializable {

	/** Variable for RiskMapRW object */
	private RiskMapRW mapObj;

	/**
	 * Constructor for the MapEditor class Initializes RiskMapRW object.
	 */
	public RiskMapEdit() {
		this.mapObj = new RiskMapRW();
	}

	/**
	 * Constructor for the MapEditor class Initializes mapIO object.
	 * 
	 * @param mapIO RiskMapRW object.
	 */
	public RiskMapEdit(RiskMapRW mapIO) {
		this.mapObj = mapIO;
	}

	/**
	 * Getter to fetch the MapIO Object
	 * 
	 * @return RiskMapRW returns mapObj Object
	 */
	public RiskMapRW getMapIO() {
		return mapObj;
	}

	/**
	 * Method for creating a new map from scratch. It provides 9 options to the
	 * user from creating continent and country. Deleting Continent and Country.
	 * Adding and deleting edge between countries, getting mapTagDat, printing
	 * current data and save and exit.
	 * 
	 * @return true if the map is successfully created; otherwise false.
	 */
	public boolean createNewMap() {
		System.out.println("\nCreate a New Map : "
				+ "\n\n1. Enter Map tag data\n2. Add Continents\n3. Remove a Continent\n4. Add Countries\n5. "
				+ "Remove a Country\n6. Add an Edge\n7. Delete an Edge\n8. Print current map contents\n9. Save and Exit");
		Scanner scan = new Scanner(System.in);
		int select = 0;
		try {
			select = Integer.parseInt(scan.nextLine());
		} catch (NumberFormatException e) {
			System.out.println("\nPlease enter a valid number.");
			createNewMap();
		}

		if (select == 1) {
			editMapTagData(scan);
			skipNewLines(scan);
			createNewMap();
		}

		else if (select == 2) {
			System.out.println("Please enter number of continents to be added.");
			int count = 0;
			try {
				count = Integer.parseInt(scan.nextLine());
			} catch (NumberFormatException e) {
				System.out.println("\nPlease enter a valid number.");
				createNewMap();
			}
			System.out.println("Please enter continent details in below format.");
			System.out.println("Continent name=Control value");
			Pattern pattern = Pattern.compile("[a-z, A-Z]+=[0-9]+");
			for (int i = 0; i < count; ++i) {
				String line = scan.nextLine();
				Matcher match = pattern.matcher(line.trim());
				if (!match.matches()) {
					System.out.print("Invalid continent configuration");
					--i;
					continue;
				}
				if (mapObj.getMapGraph().getContinents().containsKey(line.split("=")[0])) {
					System.out.println("Continent " + line.split("=")[0] + " is already defined.");
					--i;
					continue;
				}
				Continent continent = new Continent(line.split("=")[0], Integer.parseInt(line.split("=")[1]));
				mapObj.getMapGraph().addContinent(continent);
			}
			skipNewLines(scan);
			createNewMap();
		}

		else if (select == 3) {
			System.out.println("Enter the name of the continent to be deleted.");
			String continentName = scan.nextLine();
			if (!mapObj.getMapGraph().getContinents().containsKey(continentName.trim())) {
				System.out.println("Continent " + continentName + " does not exist");
				createNewMap();
			}
			mapObj.getMapGraph().removeContinent(mapObj.getMapGraph().getContinents().get(continentName.trim()));
			skipNewLines(scan);
			createNewMap();
		}

		else if (select == 4) {
			System.out.println("Please enter number of countries to be added.");
			int count = 0;
			try {
				count = Integer.parseInt(scan.nextLine());
			} catch (NumberFormatException e) {
				System.out.println("\nPlease enter a valid number.");
				createNewMap();
			}
			System.out.println("\nEnter Countries details in below format.\n");
			System.out.println(
					"Country Name, X-value, Y-value, Continent Name, List of adjacent countries separated by ,");
			for (int i = 0; i < count; ++i) {
				String line = scan.nextLine();
				if (!line.trim().isEmpty()) {
					String input[] = line.split(",");
					if (mapObj.getMapGraph().getContinents().get(input[3].trim()) == null) {
						System.out.println("Continent " + input[3].trim() + " does not exist.");
						--i;
						continue;
					}
					Country country = null;
					if (!mapObj.getMapGraph().getAllCountries().containsKey(input[0].trim())) {
						country = new Country(input[0].trim());
						country.setContinent(mapObj.getMapGraph().getContinents().get(input[3]).getName());
						country.setPartOfContinent(mapObj.getMapGraph().getContinents().get(input[3]));
						country.setxValue(input[1]);
						country.setyValue(input[2]);
					} else {
						country = mapObj.getMapGraph().getAllCountries().get(input[0].trim());
						country.setContinent(mapObj.getMapGraph().getContinents().get(input[3]).getName());
						country.setPartOfContinent(mapObj.getMapGraph().getContinents().get(input[3]));
						country.setxValue(input[1]);
						country.setyValue(input[2]);
					}
					ArrayList<Country> countries = new ArrayList<>();
					for (int j = 4; j < input.length; ++j) {
						Country adjacentCountry;
						if (!mapObj.getMapGraph().getAllCountries().containsKey(input[j].trim())) {
							adjacentCountry = new Country(input[j].trim());
							mapObj.getMapGraph().getAllCountries().put(input[j].trim(), adjacentCountry);
						} else {
							adjacentCountry = mapObj.getMapGraph().getAllCountries().get(input[j].trim());
						}
						if(!adjacentCountry.getAdjacentCountries().contains(country)){
							adjacentCountry.getAdjacentCountries().add(country);
						}
						countries.add(adjacentCountry);
					}
					country.setAdjacentCountries(countries);
					mapObj.getMapGraph().addCountry(country);
				}
			}
			skipNewLines(scan);
			createNewMap();
		} else if (select == 5) {
			System.out.println("Enter the name of the country to be deleted.");
			String countryName = scan.nextLine();
			if (!mapObj.getMapGraph().getAllCountries().containsKey(countryName.trim())) {
				System.out.println("Country " + countryName + " does not exist");
				createNewMap();
			}
			mapObj.getMapGraph().removeCountry(mapObj.getMapGraph().getAllCountries().get(countryName.trim()));
			skipNewLines(scan);
			createNewMap();
		}

		else if (select == 6) {
			System.out.println("Enter the name of two countries to be connected, separated by newline.");
			String country1 = scan.nextLine();
			String country2 = scan.nextLine();
			if (!mapObj.getMapGraph().getAllCountries().containsKey(country1)) {
				System.out.println("Country " + country1 + " does not exist.");
				createNewMap();
			} else if (!mapObj.getMapGraph().getAllCountries().containsKey(country2)) {
				System.out.println("Country " + country2 + " does not exist.");
				createNewMap();
			} else if (mapObj.getMapGraph().checkAdjacencyOfCountries(mapObj.getMapGraph().getAllCountries().get(country1),
					mapObj.getMapGraph().getAllCountries().get(country2))) {
				System.out.println("Both countries are already adjacent.");
				createNewMap();
			}
			mapObj.getMapGraph().addLinkBetweenCountries(mapObj.getMapGraph().getAllCountries().get(country1),
					mapObj.getMapGraph().getAllCountries().get(country2));
			skipNewLines(scan);
			createNewMap();
		}

		else if (select == 7) {
			System.out.println("Enter the name of two countries to be disconnected, separated by newline.");
			String country1 = scan.nextLine();
			String country2 = scan.nextLine();
			if (!mapObj.getMapGraph().getAllCountries().containsKey(country1)) {
				System.out.println("Country " + country1 + " does not exist.");
				createNewMap();
			} else if (!mapObj.getMapGraph().getAllCountries().containsKey(country2)) {
				System.out.println("Country " + country2 + " does not exist.");
				createNewMap();
			} else if (!mapObj.getMapGraph().checkAdjacencyOfCountries(mapObj.getMapGraph().getAllCountries().get(country1),
					mapObj.getMapGraph().getAllCountries().get(country2))) {
				System.out.println("Both countries are already not adjacent.");
				createNewMap();
			}
			mapObj.getMapGraph().deleteLinkBetweenCountries(mapObj.getMapGraph().getAllCountries().get(country1),
					mapObj.getMapGraph().getAllCountries().get(country2));
			skipNewLines(scan);
			createNewMap();
		}

		else if (select == 8) {
			printCurrentMapContents();
			createNewMap();
		}

		else if (select == 9) {
			if (!checkCountriesAreAdjacent()) {
				return false;
			}
			if (!checkMinimumCountriesInContinent()) {
				return false;
			}
			RiskGraphConnected connected = new RiskGraphConnected(
					new HashSet<Country>(mapObj.getMapGraph().getAllCountries().values()));

			if (!connected.isConnected()) {
				return false;
			}
			System.out.println("\nPlease enter the file name to save map file.");
			String fileName = scan.nextLine();
			mapObj.setFileName(fileName);
			mapObj.writeMapFile(true);
			return true;
		} else {
			System.out.println("Please enter valid input.");
			createNewMap();
		}
		return true;
	}

	/**
	 * Method for editing an already existing Map file. It provides 9 options to
	 * the user from creating continent and country. Deleting Continent and
	 * Country. Adding and deleting edge between countries, getting mapTagDat
	 * and save and exit.
	 * 
	 * @return true if the map is successfully created; otherwise false.
	 */
	public boolean editExistingMap() {

		printCurrentMapContents();

		System.out.println("\nEdit Map : "
				+ "\n\n1. Enter Map tag data\n2. Add Continents\n3. Remove a Continent\n4. Add Countries\n5. "
				+ "Remove a Country\n6. Add an Edge\n7. Delete an Edge\n8. Save and Exit");
		Scanner scan = new Scanner(System.in);
		int select = 0;
		try {
			select = Integer.parseInt(scan.nextLine());
		} catch (NumberFormatException e) {
			System.out.println("\nPlease enter a valid number.");
			editExistingMap();
		}

		if (select == 1) {
			editMapTagData(scan);
			skipNewLines(scan);
			editExistingMap();
		}

		else if (select == 2) {
			System.out.println("Please enter number of continents to be added.");
			int count = 0;
			try {
				count = Integer.parseInt(scan.nextLine());
			} catch (NumberFormatException e) {
				System.out.println("\nPlease enter a valid number.");
				editExistingMap();
			}
			System.out.println("Please enter continent details in below format.");
			System.out.println("Continent name=Control value");
			Pattern pattern = Pattern.compile("[a-z, A-Z]+=[0-9]+");
			for (int i = 0; i < count; ++i) {
				String line = scan.nextLine();
				Matcher match = pattern.matcher(line.trim());
				if (!match.matches()) {
					System.out.print("Invalid continent configuration");
					--i;
					continue;
				}
				if (mapObj.getMapGraph().getContinents().containsKey(line.split("=")[0])) {
					System.out.println("Continent " + line.split("=")[0] + " is already defined.");
					--i;
					continue;
				}
				Continent continent = new Continent(line.split("=")[0], Integer.parseInt(line.split("=")[1]));
				mapObj.getMapGraph().addContinent(continent);
			}
			skipNewLines(scan);
			editExistingMap();
		}

		else if (select == 3) {
			System.out.println("Enter the name of the continent to be deleted.");
			String continentName = scan.nextLine();
			if (!mapObj.getMapGraph().getContinents().containsKey(continentName.trim())) {
				System.out.println("Continent " + continentName + " does not exist");
				editExistingMap();
			}
			mapObj.getMapGraph().removeContinent(mapObj.getMapGraph().getContinents().get(continentName.trim()));
			skipNewLines(scan);
			editExistingMap();
		}

		else if (select == 4) {
			System.out.println("Please enter number of countries to be added.");
			int count = 0;
			try {
				count = Integer.parseInt(scan.nextLine());
			} catch (NumberFormatException e) {
				System.out.println("\nPlease enter a valid number.");
				editExistingMap();
			}
			System.out.println("\nEnter Countries details in below format.\n");
			System.out.println(
					"Country Name, X-value, Y-value, Continent Name, List of adjacent countries separated by ,");
			for (int i = 0; i < count; ++i) {
				String line = scan.nextLine();
				if (!line.trim().isEmpty()) {
					String input[] = line.split(",");
					if (mapObj.getMapGraph().getContinents().get(input[3].trim()) == null) {
						System.out.println("Continent " + input[3].trim() + " does not exist.");
						--i;
						continue;
					}
					Country country = null;
					if (!mapObj.getMapGraph().getAllCountries().containsKey(input[0].trim())) {
						country = new Country(input[0].trim());
						country.setContinent(mapObj.getMapGraph().getContinents().get(input[3].trim()).getName());
						country.setPartOfContinent(mapObj.getMapGraph().getContinents().get(input[3].trim()));
						country.setxValue(input[1]);
						country.setyValue(input[2]);
					} else {
						country = mapObj.getMapGraph().getAllCountries().get(input[0].trim());
						country.setContinent(mapObj.getMapGraph().getContinents().get(input[3].trim()).getName());
						country.setPartOfContinent(mapObj.getMapGraph().getContinents().get(input[3].trim()));
						country.setxValue(input[1]);
						country.setyValue(input[2]);
					}
					ArrayList<Country> countries = new ArrayList<>();
					for (int j = 4; j < input.length; ++j) {
						Country adjacentCountry;
						if (!mapObj.getMapGraph().getAllCountries().containsKey(input[j].trim())) {
							adjacentCountry = new Country(input[j].trim());
							mapObj.getMapGraph().getAllCountries().put(input[j].trim(), adjacentCountry);
						} else {
							adjacentCountry = mapObj.getMapGraph().getAllCountries().get(input[j].trim());
						}
						adjacentCountry
						.setContinent(mapObj.getMapGraph().getContinents().get(input[3].trim()).getName());
						adjacentCountry.setPartOfContinent(mapObj.getMapGraph().getContinents().get(input[3].trim()));
						if(!adjacentCountry.getAdjacentCountries().contains(country)){
							adjacentCountry.getAdjacentCountries().add(country);
						}
						countries.add(adjacentCountry);
					}
					country.setAdjacentCountries(countries);
					mapObj.getMapGraph().addCountry(country);
				}
			}
			skipNewLines(scan);
			editExistingMap();
		} else if (select == 5) {
			System.out.println("Enter the name of the country to be deleted.");
			String countryName = scan.nextLine();
			if (!mapObj.getMapGraph().getAllCountries().containsKey(countryName.trim())) {
				System.out.println("Country " + countryName + " does not exist");
				editExistingMap();
			}
			mapObj.getMapGraph().removeCountry(mapObj.getMapGraph().getAllCountries().get(countryName.trim()));
			skipNewLines(scan);
			editExistingMap();
		}

		else if (select == 6) {
			System.out.println("Enter the name of two countries to be connected, separated by newline.");
			String country1 = scan.nextLine();
			String country2 = scan.nextLine();
			if (!mapObj.getMapGraph().getAllCountries().containsKey(country1)) {
				System.out.println("Country " + country1 + " does not exist.");
				editExistingMap();
			} else if (!mapObj.getMapGraph().getAllCountries().containsKey(country2)) {
				System.out.println("Country " + country2 + " does not exist.");
				editExistingMap();
			} else if (mapObj.getMapGraph().checkAdjacencyOfCountries(mapObj.getMapGraph().getAllCountries().get(country1),
					mapObj.getMapGraph().getAllCountries().get(country2))) {
				System.out.println("Both countries are already adjacent.");
				editExistingMap();
			}
			mapObj.getMapGraph().addLinkBetweenCountries(mapObj.getMapGraph().getAllCountries().get(country1),
					mapObj.getMapGraph().getAllCountries().get(country2));
			skipNewLines(scan);
			editExistingMap();
		}

		else if (select == 7) {
			System.out.println("Enter the name of two countries to be disconnected, separated by newline.");
			String country1 = scan.nextLine();
			String country2 = scan.nextLine();
			if (!mapObj.getMapGraph().getAllCountries().containsKey(country1)) {
				System.out.println("Country " + country1 + " does not exist.");
				editExistingMap();
			} else if (!mapObj.getMapGraph().getAllCountries().containsKey(country2)) {
				System.out.println("Country " + country2 + " does not exist.");
				editExistingMap();
			} else if (!mapObj.getMapGraph().checkAdjacencyOfCountries(mapObj.getMapGraph().getAllCountries().get(country1),
					mapObj.getMapGraph().getAllCountries().get(country2))) {
				System.out.println("Both countries are already not adjacent.");
				editExistingMap();
			}
			mapObj.getMapGraph().deleteLinkBetweenCountries(mapObj.getMapGraph().getAllCountries().get(country1),
					mapObj.getMapGraph().getAllCountries().get(country2));
			skipNewLines(scan);
			editExistingMap();
		}

		else if (select == 8) {
			if (!checkCountriesAreAdjacent()) {
				return false;
			}
			if (!checkMinimumCountriesInContinent()) {
				return false;
			}
			RiskGraphConnected connected = new RiskGraphConnected(
					new HashSet<Country>(mapObj.getMapGraph().getAllCountries().values()));

			if (!connected.isConnected()) {
				return false;
			}
			System.out.println("\nPlease enter a new file name to save map file.");
			String fileName = scan.nextLine().trim();
			mapObj.setNewFileName(fileName);
			mapObj.writeMapFile(false);
			return true;
		} else {
			System.out.println("Please enter valid input.");
			editExistingMap();
		}
		return true;
	}

	/**
	 * Method for verifying if the adjacent countries in the game are also
	 * adjacent according to the .map file.
	 * 
	 * @return true if adjacent country data is true; otherwise false.
	 */
	public boolean checkCountriesAreAdjacent() {
		for (Map.Entry<Country, ArrayList<Country>> countries : mapObj.getMapGraph().getAdjacentCountries().entrySet()) {
			Country checkCountry = countries.getKey();
			ArrayList<Country> neighbours = countries.getValue();
			for (Country adjacent : neighbours) {
				if (!mapObj.getMapGraph().getAdjacentCountries().get(adjacent).contains(checkCountry)) {
					System.out.println("Countries are not adjacent");
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Method for verifying if every continent has at least two countries.
	 * 
	 * @return true if each continent has at least 2 countries; otherwise false.
	 */
	public boolean checkMinimumCountriesInContinent() {
		for (Continent continent : mapObj.getMapGraph().getContinents().values()){
			if (continent.getListOfCountries().size() < 2) {
				System.out.println("Number of countries in a continent " + continent.getName() + " is less");
				return false;
			}
		}
		return true;
	}

	/**
	 * Method for editing or adding map tag data.
	 * 
	 * @param scan  Scanner object 
	 */
	public void editMapTagData(Scanner scan) {

		mapObj.getMapTagData().clear();

		System.out.println("Please enter name of the author");
		String author = "Author = " + scan.nextLine().trim();

		System.out.println("Please specify warn is yes or no");
		String warn = "Warn = " + scan.nextLine().trim();

		System.out.println("Please specify the image for the map");
		String image = "Image = " + scan.nextLine().trim();

		System.out.println("Please specify wrap is yes or no");
		String wrap = "Wrap = " + scan.nextLine().trim();

		System.out.println("Please specify scroll is horizontal or vertical");
		String scroll = "Scroll = " + scan.nextLine().trim();

		mapObj.getMapTagData().add(author);
		mapObj.getMapTagData().add(warn);
		mapObj.getMapTagData().add(image);
		mapObj.getMapTagData().add(wrap);
		mapObj.getMapTagData().add(scroll);

		System.out.println("Added map tag data.");
	}

	/**
	 * Method for skipping blank lines in the .map file.
	 * 
	 * @param scan Scanner object     
	 */
	private void skipNewLines(Scanner scan) {
		while (scan.nextLine().equals("\n")) {
		}
	}

	/**
	 * Method for printing current map details which is being created.
	 */
	private void printCurrentMapContents() {
		System.out.println("\nCurrent map contents:\n");
		System.out.println("Map tag data [MAP]:");
		for (String mapData : mapObj.getMapTagData()) {
			System.out.println(mapData);
		}
		for (Map.Entry<String, Continent> entry : mapObj.getMapGraph().getContinents().entrySet()) {
			System.out.println();
			System.out.println("Name of continent =" + " " + entry.getValue().getName() + ", control value = "
					+ entry.getValue().getControlValue());
			for (Country country : entry.getValue().getListOfCountries()) {
				System.out.print("Adjacent countries to " + country.getName() + " are : ");
				for (Country countryInList : country.getAdjacentCountries()) {
					System.out.print(" " + countryInList.getName() + ", ");
				}
				System.out.println();
			}
		}
	}
}
