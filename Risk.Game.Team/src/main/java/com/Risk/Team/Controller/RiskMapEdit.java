package com.Risk.Team.Controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.Risk.Team.model.Continent;
import com.Risk.Team.model.Country;

/**
 * Class provides the user with the option to edit a Risk Game Map
 * as per the rules prescribed in the game.
 *
 * @author Dhaval Desai
 */
public class RiskMapEdit {

    /**
     * riskMapRW object of type RiskMapRW
     */
    private RiskMapRW riskMapRW;

    /**
     * Constructor of the class initializing riskMapRW object.
     */
    public RiskMapEdit() {
        this.riskMapRW = new RiskMapRW();
    }

    /**
     * Paramaterized Constructor of the class initializing riskMapRW object.
     *
     * @param riskMapRW object of type RiskMapRW.
     */
    public RiskMapEdit(RiskMapRW riskMapRW) {
        this.riskMapRW = riskMapRW;
    }

    /**
     * Getter for fetching the RiskMapRW Object
     *
     * @return riskMapRW Object of type RiskMapRW
     */
    public RiskMapRW getRiskMapRW() {
        return riskMapRW;
    }

    /**
     * 
     * This method allows creation of new map or editing an existing map. 
     * 
     * The user can choose from 9 options:
     * <ul>
     * <li>1) Enter Map Tag
     * <li>2) Add a Continent
     * <li>3) Remove a Continent
     * <li>4) Add a Country
     * <li>5) Remove a Country
     * <li>6) Add an Edge between Countries
     * <li>7) Remove an Edge between Countries
     * <li>8) Show Map Details
     * <li>9) Save Map and Exit
     * </ul>
     * 
     * @param flag represents 
     * 
     * @return true if the map is created else returns false
     */
    public boolean createEditMap(boolean flag) {
        System.out.println("Please Select one of the following Options : "
                + "\n==================\n1. Enter Map Info \n2. Add a Continent\n3. Remove a Continent\n4. Add a Country\n5. "
                + "Remove a Country\n6. Add an Edge between Countries\n7. Delete an Edge between Countries\n8. Show Map Details \n9. Exit");

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
                System.out.println("Add continent in the format: \n Continent name=Control value (Eg: Asia=6)");
                Pattern p = Pattern.compile("[a-z, A-Z]+=[0-9]+");
                for (int i = 0; i < count; ++i) {
                    String continentInput = sc.nextLine();
                    Matcher match = p.matcher(continentInput.trim());
                    if (!match.matches()) {
                        System.out.print("Invalid values for Continent!!! Please try again!!!");
                        --i;
                        continue;
                    }
                    if (riskMapRW.getMapGraph().getContinents().containsKey(continentInput.split("=")[0])) {
                        System.out.println(continentInput.split("=")[0] + "  Continent already exists!! Enter a different Configuration!!!");
                        --i;
                        continue;
                    }
                    Continent continent = new Continent(continentInput.split("=")[0], Integer.parseInt(continentInput.split("=")[1]));
                    riskMapRW.getMapGraph().addContinent(continent);
                }
                createEditMap(flag);
                break;
            case 3:
                System.out.println("Which continent would you like to delete?");
                String continentName = sc.nextLine();
                if (!riskMapRW.getMapGraph().getContinents().containsKey(continentName.trim())) {
                    System.out.println("Invalid Input! No such Continent Exists!!!");
                    createEditMap(flag);
                }
                riskMapRW.getMapGraph().removeContinent(riskMapRW.getMapGraph().getContinents().get(continentName.trim()));
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
                        if (riskMapRW.getMapGraph().getContinents().get(input[3].trim()) == null) {
                        	System.out.println("Invalid Input! No such Continent found!!!");
                        	--i;
                            continue;
                        }
                        Country country = null;
                        if (!riskMapRW.getMapGraph().getAllCountries().containsKey(input[0].trim())) {
                            country = new Country(input[0].trim());
                            country.setContinent(riskMapRW.getMapGraph().getContinents().get(input[3]).getName());
                            country.setPartOfContinent(riskMapRW.getMapGraph().getContinents().get(input[3].trim()));
                            country.setxValue(input[1]);
                            country.setyValue(input[2]);
                        } else {
                            country = riskMapRW.getMapGraph().getAllCountries().get(input[0].trim());
                            country.setContinent(riskMapRW.getMapGraph().getContinents().get(input[3].trim()).getName());
                            country.setPartOfContinent(riskMapRW.getMapGraph().getContinents().get(input[3].trim()));
                            country.setxValue(input[1]);
                            country.setyValue(input[2]);
                        }
                        ArrayList<Country> countryList = new ArrayList<>();
                        for (int j = 4; j < input.length; ++j) {
                            Country adjacentCountry;
                            if (!riskMapRW.getMapGraph().getAllCountries().containsKey(input[j].trim())) {
                                adjacentCountry = new Country(input[j].trim());
                                riskMapRW.getMapGraph().getAllCountries().put(input[j].trim(), adjacentCountry);
                            } else {
                                adjacentCountry = riskMapRW.getMapGraph().getAllCountries().get(input[j].trim());
                            }
                            if (!adjacentCountry.getAdjacentCountries().contains(country)) {
                                adjacentCountry.getAdjacentCountries().add(country);
                            }
                            countryList.add(adjacentCountry);
                        }
                        country.setAdjacentCountries(countryList);
                        riskMapRW.getMapGraph().addCountry(country);
                    }
                }
                createEditMap(flag);
                break;
            case 5:
                System.out.println("Which country would you like to be deleted?");
                String countryName = sc.nextLine();
                if (!riskMapRW.getMapGraph().getAllCountries().containsKey(countryName.trim())) {
                    System.out.println("Invalid Input! No such Country Found!!!");
                    createEditMap(flag);
                }
                riskMapRW.getMapGraph().removeCountry(riskMapRW.getMapGraph().getAllCountries().get(countryName.trim()));
                createEditMap(flag);
                break;
            case 6:
                System.out.println("Please specify the name of Country1 and Country2 each on a New Line");
                String firstCountry = sc.nextLine();
                String secondCountry = sc.nextLine();
                if (!riskMapRW.getMapGraph().getAllCountries().containsKey(firstCountry)) {
                    System.out.println("Invalid Country Name:" + firstCountry);
                    createEditMap(flag);
                } else if (!riskMapRW.getMapGraph().getAllCountries().containsKey(secondCountry)) {
                    System.out.println("Invalid Country Name:" + secondCountry);
                    createEditMap(flag);
                } else if (riskMapRW.getMapGraph().checkAdjacencyOfCountries(riskMapRW.getMapGraph().getAllCountries().get(firstCountry),
                		riskMapRW.getMapGraph().getAllCountries().get(secondCountry))) {
                    System.out.println("Given Countries are already Adjacent to each other!!!");
                    createEditMap(flag);
                }
                riskMapRW.getMapGraph().addLinkBetweenCountries(riskMapRW.getMapGraph().getAllCountries().get(firstCountry),
                		riskMapRW.getMapGraph().getAllCountries().get(secondCountry));
                createEditMap(flag);
                break;
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
        for (Map.Entry<Country, ArrayList<Country>> countries : riskMapRW.getMapGraph().getAdjacentCountries().entrySet()) {
            Country country = countries.getKey();
            ArrayList<Country> connected = countries.getValue();
            for (Country adjacent : connected) {
                if (!riskMapRW.getMapGraph().getAdjacentCountries().get(adjacent).contains(country)) {
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
        for (Continent continent : riskMapRW.getMapGraph().getContinents().values()) {
            if (continent.getListOfCountries().size() < 2) {
                System.out.println("The continent " + continent.getName() + " has less then 2 countries!!!");
                return false;
            }
        }
        return true;
    }

    /**
     * This method is used to print the current map details
     */
    private void printMyMap() {
        System.out.println("Map Information");
        System.out.println("================");
        System.out.println("Map Tag Info:");
        
        
        riskMapRW.getMapTagInfo().forEach(System.out::println);

        for (Map.Entry<String,Continent> list : riskMapRW.getMapGraph().getContinents().entrySet()) {
            System.out.println("\nContinent Name=" + " " + list.getValue().getName() + ", Associated Value = "+ list.getValue().getControlValue());
                list.getValue().getListOfCountries().forEach(c -> {
                System.out.print(c.getName() + " has the following countries adjacent to it: ");
                c.getAdjacentCountries().forEach(c1 -> System.out.print(" " + c1.getName() + ", "));
                System.out.println();
            });
        }
    }
