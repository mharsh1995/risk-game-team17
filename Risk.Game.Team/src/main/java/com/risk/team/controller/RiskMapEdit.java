package com.risk.team.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.risk.team.model.Continent;
import com.risk.team.model.Country;
import com.risk.team.view.LoadUI;
import com.risk.team.view.GamePhaseView;

/**
 * Class provides the user with the option to edit a Risk Game Map
 * as per the rules prescribed in the game.
 *
 * @author Dhaval Desai
 */
/**
 * This class provides the user with the option to edit a Risk Game Map
 * as per the rules prescribed in the game.
 *
 * @author Dhaval Desai
 */
public class RiskMapEdit {

	/**
	 * Creating a mapObj object of type RiskMapRW
	 */
	private RiskMapRW mapObj;

	/**
	 * Constructor of the class which initializes mapObj object.
	 */
	public RiskMapEdit() {
		this.mapObj = new RiskMapRW();
	}

	/**
	 * Paramaterized Constructor of the class which initializes mapObj object.
	 *
	 * @param mapObj object of type RiskMapRW.
	 */
	public RiskMapEdit(RiskMapRW mapObj) {
		this.mapObj = mapObj;
	}

	/**
	 * Getter for fetching the RiskMapRW Object
	 *
	 * @return mapObj Object of type RiskMapRW
	 */
	public RiskMapRW getMapObj() {
		return mapObj;
	}

	/**
	 * <ul>
	 * This method allows creation of new map. The user can choose from 9 options:
	 *<li>1) Enter Map Tag
	 *<li>2) Add a Continent
	 *<li>3) Remove a Continent
	 *<li>4) Add a Country
	 *<li>5) Remove a Country
	 *<li>6) Add an Edge between Countries
	 *<li>7) Remove an Edge between Countries
	 *<li>8) Show Map Details
	 *<li>9) Save Map and Exit
	 * </ul>
	 * @param flag when set true creates a new map and when set false allows editing existing map.
	 * 
	 * @return true if the map is created or editing successfully else returns false
	 */
	public boolean createEditMap(boolean flag) {
		System.out.println("*****************************WELCOME TO************************************** ");
		if(flag) {
			System.out.println("************************CREATE NEW MAP MENU********************************** \n");
		}else {
			System.out.println("**********************EDIT EXISTING MAP MENU********************************* \n");
		}
		System.out.println("Please Select one of the following Options : "
				+ "\n====================================\n1. Enter Map Info \n2. Add a Continent\n3. Remove a Continent\n4. Add a Country\n5. "
				+ "Remove a Country\n6. Add an Edge between Countries\n7. Delete an Edge between Countries\n8. Show Map Details \n9. Save and Exit"+"\n====================================\n");

		Scanner sc = new Scanner(System.in);
		int select = 0;
		try {
			select = Integer.parseInt(sc.nextLine());
		} catch (NumberFormatException e) {
			System.out.println("\nInvalid Input! Please enter a Valid Input:");
			createEditMap(flag);
		}

		switch (select) {
		case 1:
			addEditMapInfo(sc);
			removeBlankLines(sc);
			createEditMap(flag);
			break;
		case 2:
			System.out.println("How Many Continents would you like to add?\n");
			int count = 0;
			try {
				count = Integer.parseInt(sc.nextLine());
			} catch (NumberFormatException e) {
				System.out.println("\\nInvalid Input! Expecting a Number value for Continents\n");
				createEditMap(flag);
			}
			System.out.println("Add continent in the format: \n Continent name=Control value (Example: Africa=6)");
			Pattern p = Pattern.compile("[a-z, A-Z]+=[0-9]+");
			for (int i = 0; i < count; ++i) {
				String continentInput = sc.nextLine();
				Matcher match = p.matcher(continentInput.trim());
				if (!match.matches()) {
					System.out.print("Invalid values for Continent!!! Please try again!!!");
					--i;
					continue;
				}
				if (mapObj.getMapGraph().getContinents().containsKey(continentInput.split("=")[0])) {
					System.out.println(continentInput.split("=")[0] + "  Continent already exists!! Enter a different Configuration!!!");
					--i;
					continue;
				}
				Continent continent = new Continent(continentInput.split("=")[0], Integer.parseInt(continentInput.split("=")[1]));
				mapObj.getMapGraph().addContinent(continent);
			}
			removeBlankLines(sc);
			createEditMap(flag);
			break;
		case 3:
			System.out.println("Which continent would you like to delete?");
			String continentName = sc.nextLine();
			if (!mapObj.getMapGraph().getContinents().containsKey(continentName.trim())) {
				System.out.println("Invalid Input! No such Continent Exists!!!");
				createEditMap(flag);
			}
			mapObj.getMapGraph().removeContinent(mapObj.getMapGraph().getContinents().get(continentName.trim()));
			System.out.println("Continent deleted Successfully");
			removeBlankLines(sc);
			createEditMap(flag);
			break;
		case 4:
			System.out.println("How many countries would you like to add?");
			int count1 = 0;
			try {
				count1 = Integer.parseInt(sc.nextLine());
			} catch (NumberFormatException e) {
				System.out.println("\\nInvalid Input! Expecting a Number value\n");
				createEditMap(flag);
			}
			System.out.println("\nFormat for entering a New Country:\n");
			System.out.println("Country Name,X-coordinate,Y-coordinate,Continent Name,Adjacent countries (Countries should be seperated by , )");
			for (int i = 0; i < count1; ++i) {
				String line = sc.nextLine();
				if (!line.trim().isEmpty()) {
					String input[] = line.split(",");
					if (mapObj.getMapGraph().getContinents().get(input[3].trim()) == null) {
						System.out.println("Invalid Input! No such Continent found!!!");
						--i;
						continue;
					}
					Country country = null;
					if (!mapObj.getMapGraph().getAllCountries().containsKey(input[0].trim())) {
						country = new Country(input[0].trim());
						country.setContinent(mapObj.getMapGraph().getContinents().get(input[3]).getName());
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
					ArrayList<Country> countryList = new ArrayList<>();
					for (int j = 4; j < input.length; ++j) {
						Country adjacentCountry;
						if (!mapObj.getMapGraph().getAllCountries().containsKey(input[j].trim())) {
							adjacentCountry = new Country(input[j].trim());
							mapObj.getMapGraph().getAllCountries().put(input[j].trim(), adjacentCountry);
						} else {
							adjacentCountry = mapObj.getMapGraph().getAllCountries().get(input[j].trim());
						}
						if (!adjacentCountry.getAdjacentCountries().contains(country)) {
							adjacentCountry.getAdjacentCountries().add(country);
						}
						countryList.add(adjacentCountry);
					}
					country.setAdjacentCountries(countryList);
					mapObj.getMapGraph().addCountry(country);
				}
			}
			removeBlankLines(sc);
			createEditMap(flag);
			break;
		case 5:
			System.out.println("Which country would you like to be deleted?");
			String countryName = sc.nextLine();
			if (!mapObj.getMapGraph().getAllCountries().containsKey(countryName.trim())) {
				System.out.println("Invalid Input! No such Country Found!!!");
				createEditMap(flag);
			}
			mapObj.getMapGraph().removeCountry(mapObj.getMapGraph().getAllCountries().get(countryName.trim()));
			System.out.println("Country deleted Successfully");
			removeBlankLines(sc);
			createEditMap(flag);
			break;
		case 6:
			System.out.println("Please specify the name of Country1 and Country2 each on a New Line");
			String firstCountry = sc.nextLine();
			String secondCountry = sc.nextLine();
			if (!mapObj.getMapGraph().getAllCountries().containsKey(firstCountry)) {
				System.out.println("Invalid Country Name:" + firstCountry);
				createEditMap(flag);
			} else if (!mapObj.getMapGraph().getAllCountries().containsKey(secondCountry)) {
				System.out.println("Invalid Country Name:" + secondCountry);
				createEditMap(flag);
			} else if (mapObj.getMapGraph().checkAdjacencyOfCountries(mapObj.getMapGraph().getAllCountries().get(firstCountry),
					mapObj.getMapGraph().getAllCountries().get(secondCountry))) {
				System.out.println("Given Countries are already Adjacent to each other!!!");
				createEditMap(flag);
			}
			mapObj.getMapGraph().addLinkBetweenCountries(mapObj.getMapGraph().getAllCountries().get(firstCountry),
					mapObj.getMapGraph().getAllCountries().get(secondCountry));
			System.out.println("Link/Edge Added Successfully");
			removeBlankLines(sc);
			createEditMap(flag);
			break;
		case 7:
			System.out.println("Please specify the name of Country1 and Country2 each on a New Line");
			String thirdCountry = sc.nextLine();
			String fourthCountry = sc.nextLine();
			if (!mapObj.getMapGraph().getAllCountries().containsKey(thirdCountry)) {
				System.out.println("Invalid Country Name:" + thirdCountry);
				createEditMap(flag);
			} else if (!mapObj.getMapGraph().getAllCountries().containsKey(fourthCountry)) {
				System.out.println("Invalid Country Name:" + fourthCountry);
				createEditMap(flag);
			} else if (!mapObj.getMapGraph().checkAdjacencyOfCountries(mapObj.getMapGraph().getAllCountries().get(thirdCountry),
					mapObj.getMapGraph().getAllCountries().get(fourthCountry))) {
				System.out.println("Countries already disjoint!!!");
				createEditMap(flag);
			}
			mapObj.getMapGraph().deleteLinkBetweenCountries(mapObj.getMapGraph().getAllCountries().get(thirdCountry),
					mapObj.getMapGraph().getAllCountries().get(fourthCountry));
			System.out.println("Link/Edge deleted Successfully");
			removeBlankLines(sc);
			createEditMap(flag);
			break;
		case 8:
			printMyMap();
			createEditMap(flag);
			break;
		case 9:
			if (!checkAdjacency()) {
				return false;
			}
			if (!countCountriesInContinent()) {
				return false;
			}
			RiskGraphConnected connected = new RiskGraphConnected(new HashSet<Country>(mapObj.getMapGraph().getAllCountries().values()));

			if (!connected.isGraphConnected()) {
				return false;
			}
			System.out.println("\nPlease enter Map name you wish to save?");
			String fileName = sc.nextLine().trim();
			mapObj.setFileName(fileName);
			mapObj.setNewFileName(fileName);
			mapObj.writeMapFile(flag);
			LoadUI.status = true;
			return true;

		default:
			System.out.println("Please enter valid input.");
			createEditMap(flag);
			break;
		}
		return true;
	}


	/**
	 * 
	 * This method verifies whether the countries are adjacent
	 * to each other or not as per the Map file.
	 * 
	 * @return true if the countries are adjacent to each other else returns false if they are not adjacent.
	 */
	public boolean checkAdjacency() {
		for (Map.Entry<Country, ArrayList<Country>> countries : mapObj.getMapGraph().getAdjacentCountries().entrySet()) {
			Country country = countries.getKey();
			ArrayList<Country> connected = countries.getValue();
			for (Country adjacent : connected) {
				if (!mapObj.getMapGraph().getAdjacentCountries().get(adjacent).contains(country)) {
					System.out.println("Given countries are not adjacent to each other!!!");
					return false;
				}
			}
		}
		return true;
	}

	/**
	 *
	 * This method verifies whether a particular continent has 
	 * minimum 2 countries inside it.
	 * 
	 * @return true if the continent has more than 2 countries inside it else returns false
	 */
	public boolean countCountriesInContinent() {
		for (Continent continent : mapObj.getMapGraph().getContinents().values()) {
			if (continent.getListOfCountries().size() < 2) {
				System.out.println("The continent " + continent.getName() + " has less then 2 countries!!!");
				return false;
			}
		}
		return true;
	}

	/**
	 * 
	 * This method allows to add or edit a Map Tag Information.
	 *
	 *@param sc Scanner Object which allows scanning input from the user.
	 *
	 *
	 */
	public void addEditMapInfo(Scanner sc) {
		mapObj.getMapTagInfo().clear();
		System.out.println("Please enter the following information as requested:");
		System.out.println("Enter Author Name:");
		String auth = "Author = " + sc.nextLine().trim();

		System.out.println("Warning = Yes or No?");
		String warn = "Warning = " + sc.nextLine().trim();

		System.out.println("Please enter name of the Map Image file(.bmp):");
		String img = "Image = " + sc.nextLine().trim();

		System.out.println("Wrap = Yes or No?");
		String wrap = "Wrap = " + sc.nextLine().trim();

		System.out.println("Scrolling = Horizontal or Vertical?");
		String scroll = "Scroll = " + sc.nextLine().trim();

		mapObj.getMapTagInfo().add(auth);
		mapObj.getMapTagInfo().add(warn);
		mapObj.getMapTagInfo().add(img);
		mapObj.getMapTagInfo().add(wrap);
		mapObj.getMapTagInfo().add(scroll);

		System.out.println("Map Tag Info added Sucessfully!!");
	}

	/**
	 *
	 * This method is used for pre-processing the 
	 * blank lines in the Map file.
	 *
	 *@param sc Scanner Object which allows scanning input from the user.
	 *
	 */
	private void removeBlankLines(Scanner sc) {

		System.out.println("*****************************************************************************\n");
	}

	/**
	 * This method is used to print the current map details
	 */
	private void printMyMap() {
		System.out.println("Map Information");
		System.out.println("================");
		System.out.println("Map Tag Info:");

		mapObj.getMapTagInfo().forEach(System.out::println);

		for (Map.Entry<String,Continent> entry : mapObj.getMapGraph().getContinents().entrySet()) {
			System.out.println("\nContinent Name=" + " " + entry.getValue().getName() + ", Associated Value = "+ entry.getValue().getControlValue());

			entry.getValue().getListOfCountries().forEach(c -> {
				System.out.print(c.getName() + " has the following countries adjacent to it: ");
				c.getAdjacentCountries().forEach(c1 -> System.out.print(" " + c1.getName() + ", "));
				System.out.println();
			});
		}
	}
}

