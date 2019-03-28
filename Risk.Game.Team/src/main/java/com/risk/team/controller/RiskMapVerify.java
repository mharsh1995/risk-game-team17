package com.risk.team.controller;

import com.risk.team.model.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/***
 * The RiskMapVerify class file contains method to verify input .map file.
 * It also has methods to get/set continents , countries and mapTagData to Object.
 * 
 * @author Kartika Patil
 *
 */
public class RiskMapVerify {

	/**HashMap of continents under continents Tag from the file*/
	private HashMap<String, Continent> orignalContinents;

	/**HashMap of continents under territories Tag from the file*/
	private HashMap<String, Continent> continentsFromTerritories;

	/**HashMap of countries or territories*/
	private HashMap<String, Country> countryMap;

	/**HashMap of country as key and adjacent countries as a value*/
	private HashMap<Country, ArrayList<Country>> adjacentCountries;

	/**
	 * HashMap containing continent as key and countries belonging to continent
	 * as value
	 */
	private HashMap<Continent, HashSet<Country>> countriesOfContinent;

	/** Name of the file to be validated */
	private String fileName;

	/** List containing all the mapTag Information , like name of author, scroll , warn etc. */
	private ArrayList<String> mapTagInfo;

	/**
	 * A no argument constructor for initializing all the data fields of the
	 * RiskMapVerify class.
	 * 
	 */
	public RiskMapVerify() {
		this.orignalContinents = new HashMap<>();
		this.continentsFromTerritories = new HashMap<>();
		this.adjacentCountries = new HashMap<>();
		this.countriesOfContinent = new HashMap<>();
		this.mapTagInfo = new ArrayList<>();
		this.countryMap = new HashMap<>();
	}

	/**
	 * Method to get HashMap of continent under territories Tag Data.
	 * 
	 * @return HashMap which is continentsFromTerritories
	 */

	public HashMap<String, Continent> getContinentsFromTerritories() {
		return continentsFromTerritories;
	}


	/**
	 * Method to set HashMap of continent under territories Tag Data.
	 * 
	 * @param continentsFromTerritories which is continent HashMap generated from territories Tag data.
	 */

	public void setContinentsFromTerritories(HashMap<String, Continent> continentsFromTerritories) {
		this.continentsFromTerritories = continentsFromTerritories;
	}

	/**
	 * Method to get HashMap of continents under continents Tag from the file.
	 * 
	 * @return HashMap which is orignalContinents
	 */

	public HashMap<String, Continent> getOrignalContinents() {
		return orignalContinents;
	}

	/**
	 * Method to set HashMap of continents under continents Tag from the file.
	 * @param orignalContinents which is orignalContinents
	 */

	public void setOrignalContinents(HashMap<String, Continent> orignalContinents) {
		this.orignalContinents = orignalContinents;
	}

	/**
	 * HashMap containing country as key and adjacent countries as a value.
	 * as value
	 *
	 * @return adjacentCountries
	 */

	public HashMap<Country, ArrayList<Country>> getAdjacentCountries() {
		return adjacentCountries;
	}

	/**
	 * Method to get HashMap containing continent as key and countries belonging to continent as value.
	 * @return countriesOfContinent
	 */
	public HashMap<Continent, HashSet<Country>> getCountriesOfContinent() {
		return countriesOfContinent;
	}

	/**
	 * Method to get file name.
	 * 
	 * @return String fileName
	 */

	public String getFileName() {
		return fileName;
	}

	/**
	 * Method to get mapTagInfo.
	 * 
	 * @return ArrayList Contents of map Tag Data like scroll, warn etc.
	 */

	public ArrayList<String> getMapTagInfo() {
		return mapTagInfo;
	}

	/**
	 * Method to get countryMap.
	 * 
	 * @return HashMap of countries/territories inside file.
	 */


	public HashMap<String, Country> getCountryMap() {
		return countryMap;
	}


	/**
	 * Method for verification of a map file. At first it check for validity of map tag data. Then it checks for continent and control value. 
	 * After receiving the the continent and control value it moves to check countries.In territories it reads all the countries one by one, 
	 * along with its x, y coordinates and continent.Then it reads the list of adjacent countries and checks for the validation, 
	 * that whether the two countries are present in each others list of adjacent countries or not.
	 * 
	 * @param inputMapFile
	 *            String that contains name of the file to be validated.
	 * 
	 * @return true if map is verified  otherwise false
	 */


	public boolean verifyMapFile(String inputMapFile) {

		String mapFile = inputMapFile;
		this.fileName = mapFile;

		try {
			if (mapFile != null) {
				try (BufferedReader read = new BufferedReader(new FileReader(mapFile))) {
					String fileText = new String(Files.readAllBytes(Paths.get(mapFile)), StandardCharsets.UTF_8);
					if (!checkAllTags(fileText)) {
						System.out.println("File is missing necessary tags or having incorrect tags!!");
						return false;
					}

					for (String line; (line = read.readLine()) != null;) {
						if (line.trim().equals("[Map]")) {
							while (!((line = read.readLine()).equals("[Continents]"))) {
								if (!line.trim().isEmpty() && !(line.contains("="))) {
									System.out.print("Invalid Structure details of Map");
									return false;
								}
								mapTagInfo.add(line);
							}
						}
						if (line.equals("[Continents]")) {
							while (!((line = read.readLine()).equals("[Territories]"))) {
								Pattern pattern = Pattern.compile("[a-z, A-Z]+=[0-9]+");
								if (!line.trim().isEmpty()) {
									Matcher match = pattern.matcher(line.trim());
									if (!match.matches()) {
										if (line.trim().equals("[Territories]")) {
											break;
										}
										System.out.print("Invalid Structure details of Continent");
										return false;
									}

									if (orignalContinents.containsKey(line.split("=")[0])) {
										System.out.println("Continent is already defined in a System.");
										return false;
									}
									Continent continent = new Continent(line.split("=")[0],
											Integer.parseInt(line.split("=")[1]));
									orignalContinents.put(continent.getName(), continent);
								}
							}
						}
						if (line.equals("[Territories]")) {
							while ((line = read.readLine()) != null) {
								if (!line.trim().isEmpty()) {
									String input[] = line.split(",");
									if (continentsFromTerritories.get(input[3].trim()) == null) {
										Continent continent = new Continent(input[3].trim(), 0);
										continent.setControlValue(orignalContinents.get(input[3].trim()).getControlValue());
										continentsFromTerritories.put(continent.getName(), continent);
									}
									Country country = null;
									if (!countriesOfContinent.keySet().contains(continentsFromTerritories.get(input[3]))) {
										HashSet<Country> countries = new HashSet<>();
										if (!countryMap.containsKey(input[0].trim())) {
											country = new Country(input[0].trim());
											country.setContinent(continentsFromTerritories.get(input[3]).getName());
											country.setxValue(input[1]);
											country.setyValue(input[2]);
											countryMap.put(input[0].trim(), country);
										} else {
											country = countryMap.get(input[0].trim());
											country.setContinent(continentsFromTerritories.get(input[3]).getName());
											country.setxValue(input[1]);
											country.setyValue(input[2]);
										}
										country.setPartOfContinent(continentsFromTerritories.get(input[3]));
										countries.add(country);
										if (!continentsFromTerritories.get(input[3]).getListOfCountries()
												.contains(country)) {
											continentsFromTerritories.get(input[3]).getListOfCountries().add(country);
										}
										countriesOfContinent.put(continentsFromTerritories.get(input[3]), countries);
									} else {
										HashSet<Country> countries = getCountriesFromContinent(input[3].trim(),
												countriesOfContinent);
										if (!countryMap.containsKey(input[0].trim())) {
											country = new Country(input[0].trim());
											country.setxValue(input[1]);
											country.setyValue(input[2]);
											country.setContinent(continentsFromTerritories.get(input[3]).getName());
											countryMap.put(input[0].trim(), country);
										} else {
											country = countryMap.get(input[0].trim());
											country.setContinent(continentsFromTerritories.get(input[3]).getName());
											country.setxValue(input[1]);
											country.setyValue(input[2]);
										}
										country.setPartOfContinent(continentsFromTerritories.get(input[3]));
										countries.add(country);
										if (!continentsFromTerritories.get(input[3]).getListOfCountries()
												.contains(country)) {
											continentsFromTerritories.get(input[3]).getListOfCountries().add(country);
										}
										countriesOfContinent.put(continentsFromTerritories.get(input[3]), countries);
									}
									ArrayList<Country> countries = new ArrayList<>();
									for (int i = 4; i < input.length; ++i) {
										Country adjacentCountry;
										if (!countryMap.containsKey(input[i].trim())) {
											adjacentCountry = new Country(input[i].trim());
											countryMap.put(input[i].trim(), adjacentCountry);
										} else {
											adjacentCountry = countryMap.get(input[i].trim());
										}
										countries.add(adjacentCountry);
									}
									country.setAdjacentCountries(countries);
									adjacentCountries.put(country, countries);
								}
							}
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}


				for (Map.Entry<Continent, HashSet<Country>> countries : countriesOfContinent.entrySet()) {
					if (countries.getValue().size() < 2) {
						System.out.println("Countinent should have more than 2 countries");
						return false;
					}
				}



				for (Map.Entry<Country, ArrayList<Country>> countries : adjacentCountries.entrySet()) {
					Country currentCountry = countries.getKey();
					ArrayList<Country> neighbours = countries.getValue();
					for (Country adjacent : neighbours) {
						if (!adjacentCountries.get(adjacent).contains(currentCountry)) {
							System.out.println("Countries are not adjacent");
							return false;
						}
					}
				}
			}

			if (orignalContinents.size() != continentsFromTerritories.size()) {
				System.out.println("Number of continents defined in continents tag does not match "
						+ " with the continents defined in the territories tag");
				return false;
			}

			RiskGraphConnected connected = new RiskGraphConnected(new HashSet<Country>(countryMap.values()));

			if (!connected.isGraphConnected()) {
				return false;
			}

		}catch (NullPointerException e){
			System.out.println("NullPointerException - Something went wrong in validation or File Stucture is Invalid!!");
			return false;
		}

		return true;
	}
	/**
	 * Method for getting countries corresponding to a continent.
	 * 
	 * @param continent
	 *            String that is the name of the continent.
	 * @param countriesOfContinent
	 *            HashMap consisting of continents and their countries.
	 * 
	 * @return HashSet countries inside a continent or null;
	 */

	public HashSet<Country> getCountriesFromContinent(String continent,
			HashMap<Continent, HashSet<Country>> countriesOfContinent) {
		for (Map.Entry<Continent, HashSet<Country>> pair : countriesOfContinent.entrySet()) {
			if (pair.getKey().getName().equals(continent)) {
				return pair.getValue();
			}
		}
		return null;
	}

	/**
	 * Method for checking whether all the attributes are present in a map file
	 * or not, these attributes are mapTag data, continents and territories.
	 * 
	 * @param fileContent
	 *            String that has the particular tags.
	 * 
	 * @return true if mapTags are present; otherwise false.
	 */

	public boolean checkAllTags(String fileContent) {
		if (!fileContent.contains("[Map]") || countOccurrences(fileContent, "[Map]") != 1) {
			return false;
		} else if (!fileContent.contains("[Continents]") || countOccurrences(fileContent, "[Continents]") != 1) {
			return false;
		} else if (!fileContent.contains("[Territories]") || countOccurrences(fileContent, "[Territories]") != 1) {
			return false;
		}

		return true;
	}
	/**
	 * Method for checking that all tags occur only once or not.
	 * 
	 * 
	 * @param input
	 *            String type
	 * @param search
	 *            String type
	 * 
	 * @return count it is number of occurrences of type int.
	 */

	public int countOccurrences(String input, String search) {
		int count = 0, startIndex = 0;
		Pattern pattern = Pattern.compile(search, Pattern.LITERAL);
		Matcher match = pattern.matcher(input);

		while (match.find(startIndex)) {
			count++;
			startIndex = match.start() + 1;
		}
		return count;
	}

}
