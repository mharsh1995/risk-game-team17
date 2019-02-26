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
 * This class provides the user with the option to edit a Risk Game Map
 * as per the rules prescribed in the game.
 *
 * @author Dhaval Desai
 */
public class EditGameMap {

    /**
     * Creating a riskMapRW object of type RiskMapRW
     */
    private RiskMapRW riskMapRW;

    /**
     * Constructor of the class which initializes riskMapRW object.
     */
    public EditGameMap() {
        this.riskMapRW = new RiskMapRW();
    }

    /**
     * Constructor of the class which initializes riskMapRW object.
     *
     * @param mapIO MapIO object.
     */
    public EditGameMap(RiskMapRW riskMapRW) {
        this.riskMapRW = riskMapRW;
    }

    /**
     * Getter to fetch the MapIO Object
     *
     * @return MapIO returns mapIO Object
     */
    public RiskMapRW getMapIO() {
        return riskMapRW;
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
        System.out.println("Create a New Map : "
                + "\n==================\n1. Enter Map tag data\n2. Add Continent\n3. Remove Continent\n4. Add Country\n5. "
                + "Remove Country\n6. Add an Edge\n7. Delete an Edge\n8. Print current map \n9. Save and Exit");

        Scanner scan = new Scanner(System.in);
        int select = 0;
        try {
            select = Integer.parseInt(scan.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("\nPlease enter a valid number.");
            createNewMap();
        }

        switch (select) {
            case 1:
                editMapTagData(scan);
                skipNewLines(scan);
                createNewMap();
                break;
            case 2:
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
                    if (riskMapRW.getMapGraph().getContinents().containsKey(line.split("=")[0])) {
                        System.out.println("Continent " + line.split("=")[0] + " is already defined.");
                        --i;
                        continue;
                    }
                    Continent continent = new Continent(line.split("=")[0], Integer.parseInt(line.split("=")[1]));
                    riskMapRW.getMapGraph().addContinent(continent);
                }
                skipNewLines(scan);
                createNewMap();
                break;
            case 3:
                System.out.println("Enter the name of the continent to be deleted.");
                String continentName = scan.nextLine();
                if (!riskMapRW.getMapGraph().getContinents().containsKey(continentName.trim())) {
                    System.out.println("Continent " + continentName + " does not exist");
                    createNewMap();
                }
                riskMapRW.getMapGraph().removeContinent(riskMapRW.getMapGraph().getContinents().get(continentName.trim()));
                skipNewLines(scan);
                createNewMap();
                break;
            case 4:
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
                        if (riskMapRW.getMapGraph().getContinents().get(input[3].trim()) == null) {
                            System.out.println("Continent " + input[3].trim() + " does not exist.");
                            --i;
                            continue;
                        }
                        Country country = null;
                        if (!riskMapRW.getMapGraph().getCountrySet().containsKey(input[0].trim())) {
                            country = new Country(input[0].trim());
                            country.setContinent(riskMapRW.getMapGraph().getContinents().get(input[3]).getName());
                            country.setPartOfContinent(riskMapRW.getMapGraph().getContinents().get(input[3]));
                            country.setxValue(input[1]);
                            country.setyValue(input[2]);
                        } else {
                            country = riskMapRW.getMapGraph().getCountrySet().get(input[0].trim());
                            country.setContinent(riskMapRW.getMapGraph().getContinents().get(input[3]).getName());
                            country.setPartOfContinent(riskMapRW.getMapGraph().getContinents().get(input[3]));
                            country.setxValue(input[1]);
                            country.setyValue(input[2]);
                        }
                        ArrayList<Country> countries = new ArrayList<>();
                        for (int j = 4; j < input.length; ++j) {
                            Country adjacentCountry;
                            if (!riskMapRW.getMapGraph().getCountrySet().containsKey(input[j].trim())) {
                                adjacentCountry = new Country(input[j].trim());
                                riskMapRW.getMapGraph().getCountrySet().put(input[j].trim(), adjacentCountry);
                            } else {
                                adjacentCountry = riskMapRW.getMapGraph().getCountrySet().get(input[j].trim());
                            }
                            if (!adjacentCountry.getAdjacentCountries().contains(country)) {
                                adjacentCountry.getAdjacentCountries().add(country);
                            }
                            countries.add(adjacentCountry);
                        }
                        country.setAdjacentCountries(countries);
                        riskMapRW.getMapGraph().addCountry(country);
                    }
                }
                skipNewLines(scan);
                createNewMap();
                break;
            case 5:
                System.out.println("Enter the name of the country to be deleted.");
                String countryName = scan.nextLine();
                if (!mapIO.getMapGraph().getCountrySet().containsKey(countryName.trim())) {
                    System.out.println("Country " + countryName + " does not exist");
                    createNewMap();
                }
                riskMapRW.getMapGraph().removeCountry(mapIO.getMapGraph().getCountrySet().get(countryName.trim()));
                skipNewLines(scan);
                createNewMap();
                break;
            case 6:
                System.out.println("Enter the name of two countries to be connected, separated by newline.");
                String country1 = scan.nextLine();
                String country2 = scan.nextLine();
                if (!riskMapRW.getMapGraph().getCountrySet().containsKey(country1)) {
                    System.out.println("Country " + country1 + " does not exist.");
                    createNewMap();
                } else if (!riskMapRW.getMapGraph().getCountrySet().containsKey(country2)) {
                    System.out.println("Country " + country2 + " does not exist.");
                    createNewMap();
                } else if (riskMapRW.getMapGraph().checkAdjacency(riskMapRW.getMapGraph().getCountrySet().get(country1),
                		riskMapRW.getMapGraph().getCountrySet().get(country2))) {
                    System.out.println("Both countries are already adjacent.");
                    createNewMap();
                }
                riskMapRW.getMapGraph().addEdgeBetweenCountries(riskMapRW.getMapGraph().getCountrySet().get(country1),
                		riskMapRW.getMapGraph().getCountrySet().get(country2));
                skipNewLines(scan);
                createNewMap();
                break;
            case 7:
                System.out.println("Enter the name of two countries to be disconnected, separated by newline.");
                String country1 = scan.nextLine();
                String country2 = scan.nextLine();
                if (!riskMapRW.getMapGraph().getCountrySet().containsKey(country1)) {
                    System.out.println("Country " + country1 + " does not exist.");
                    createNewMap();
                } else if (!riskMapRW.getMapGraph().getCountrySet().containsKey(country2)) {
                    System.out.println("Country " + country2 + " does not exist.");
                    createNewMap();
                } else if (!riskMapRW.getMapGraph().checkAdjacency(riskMapRW.getMapGraph().getCountrySet().get(country1),
                		riskMapRW.getMapGraph().getCountrySet().get(country2))) {
                    System.out.println("Both countries are already not adjacent.");
                    createNewMap();
                }
                riskMapRW.getMapGraph().deleteEdgeBetweenCountries(riskMapRW.getMapGraph().getCountrySet().get(country1),
                		riskMapRW.getMapGraph().getCountrySet().get(country2));
                skipNewLines(scan);
                createNewMap();
                break;
            case 8:
                printCurrentMapContents();
                createNewMap();
                break;
            case 9:
                if (!checkCountriesAreAdjacent()) {
                    return false;
                }
                if (!checkMinimumCountriesInContinent()) {
                    return false;
                }
                RiskGraphConnected connected = new RiskGraphConnected(new HashSet<Country>(mapIO.getMapGraph().getCountrySet().values()));

                if (!connected.isGraphConnected()) {
                    return false;
                }
                System.out.println("\nPlease enter the file name to save map file.");
                String fileName = scan.nextLine();
                riskMapRW.setFileName(fileName);
                riskMapRW.writeMapFile(true);
                LaunchGameDriver.status = true;
                return true;
            break;
            default:
                System.out.println("Please enter valid input.");
                createNewMap();
                break;
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

        switch (select) {
            case 1:
                editMapTagData(scan);
                skipNewLines(scan);
                editExistingMap();
                break;
            case 2:
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
                    if (riskMapRW.getMapGraph().getContinents().containsKey(line.split("=")[0])) {
                        System.out.println("Continent " + line.split("=")[0] + " is already defined.");
                        --i;
                        continue;
                    }
                    Continent continent = new Continent(line.split("=")[0], Integer.parseInt(line.split("=")[1]));
                    riskMapRW.getMapGraph().addContinent(continent);
                }
                skipNewLines(scan);
                editExistingMap();
                break;
            case 3:
                System.out.println("Enter the name of the continent to be deleted.");
                String continentName = scan.nextLine();
                if (!riskMapRW.getMapGraph().getContinents().containsKey(continentName.trim())) {
                    System.out.println("Continent " + continentName + " does not exist");
                    editExistingMap();
                }
                riskMapRW.getMapGraph().removeContinent(riskMapRW.getMapGraph().getContinents().get(continentName.trim()));
                skipNewLines(scan);
                editExistingMap();
                break;
            case 4:
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
                        if (riskMapRW.getMapGraph().getContinents().get(input[3].trim()) == null) {
                            System.out.println("Continent " + input[3].trim() + " does not exist.");
                            --i;
                            continue;
                        }
                        Country country = null;
                        if (!riskMapRW.getMapGraph().getCountrySet().containsKey(input[0].trim())) {
                            country = new Country(input[0].trim());
                            country.setContinent(riskMapRW.getMapGraph().getContinents().get(input[3].trim()).getName());
                            country.setPartOfContinent(riskMapRW.getMapGraph().getContinents().get(input[3].trim()));
                            country.setxValue(input[1]);
                            country.setyValue(input[2]);
                        } else {
                            country = riskMapRW.getMapGraph().getCountrySet().get(input[0].trim());
                            country.setContinent(riskMapRW.getMapGraph().getContinents().get(input[3].trim()).getName());
                            country.setPartOfContinent(riskMapRW.getMapGraph().getContinents().get(input[3].trim()));
                            country.setxValue(input[1]);
                            country.setyValue(input[2]);
                        }
                        ArrayList<Country> countries = new ArrayList<>();
                        for (int j = 4; j < input.length; ++j) {
                            Country adjacentCountry;
                            if (!riskMapRW.getMapGraph().getCountrySet().containsKey(input[j].trim())) {
                                adjacentCountry = new Country(input[j].trim());
                                riskMapRW.getMapGraph().getCountrySet().put(input[j].trim(), adjacentCountry);
                            } else {
                                adjacentCountry = riskMapRW.getMapGraph().getCountrySet().get(input[j].trim());
                            }
                            adjacentCountry
                                    .setContinent(riskMapRW.getMapGraph().getContinents().get(input[3].trim()).getName());
                            adjacentCountry.setPartOfContinent(riskMapRW.getMapGraph().getContinents().get(input[3].trim()));
                            if (!adjacentCountry.getAdjacentCountries().contains(country)) {
                                adjacentCountry.getAdjacentCountries().add(country);
                            }
                            countries.add(adjacentCountry);
                        }
                        country.setAdjacentCountries(countries);
                        riskMapRW.getMapGraph().addCountry(country);
                    }
                }
                skipNewLines(scan);
                editExistingMap();
                break;
            case 5:
                System.out.println("Enter the name of the country to be deleted.");
                String countryName = scan.nextLine();
                if (!riskMapRW.getMapGraph().getCountrySet().containsKey(countryName.trim())) {
                    System.out.println("Country " + countryName + " does not exist");
                    editExistingMap();
                }
                riskMapRW.getMapGraph().removeCountry(riskMapRW.getMapGraph().getCountrySet().get(countryName.trim()));
                skipNewLines(scan);
                editExistingMap();
                break;
            case 6:
                System.out.println("Enter the name of two countries to be connected, separated by newline.");
                String country1 = scan.nextLine();
                String country2 = scan.nextLine();
                if (!riskMapRW.getMapGraph().getCountrySet().containsKey(country1)) {
                    System.out.println("Country " + country1 + " does not exist.");
                    editExistingMap();
                } else if (!riskMapRW.getMapGraph().getCountrySet().containsKey(country2)) {
                    System.out.println("Country " + country2 + " does not exist.");
                    editExistingMap();
                } else if (riskMapRW.getMapGraph().checkAdjacency(riskMapRW.getMapGraph().getCountrySet().get(country1),
                		riskMapRW.getMapGraph().getCountrySet().get(country2))) {
                    System.out.println("Both countries are already adjacent.");
                    editExistingMap();
                }
                riskMapRW.getMapGraph().addEdgeBetweenCountries(riskMapRW.getMapGraph().getCountrySet().get(country1),
                		riskMapRW.getMapGraph().getCountrySet().get(country2));
                skipNewLines(scan);
                editExistingMap();
                break;
            case 7:
                System.out.println("Enter the name of two countries to be disconnected, separated by newline.");
                String country1 = scan.nextLine();
                String country2 = scan.nextLine();
                if (!riskMapRW.getMapGraph().getCountrySet().containsKey(country1)) {
                    System.out.println("Country " + country1 + " does not exist.");
                    editExistingMap();
                } else if (!riskMapRW.getMapGraph().getCountrySet().containsKey(country2)) {
                    System.out.println("Country " + country2 + " does not exist.");
                    editExistingMap();
                } else if (!riskMapRW.getMapGraph().checkAdjacency(mapIO.getMapGraph().getCountrySet().get(country1),
                		riskMapRW.getMapGraph().getCountrySet().get(country2))) {
                    System.out.println("Both countries are already not adjacent.");
                    editExistingMap();
                }
                riskMapRW.getMapGraph().deleteEdgeBetweenCountries(riskMapRW.getMapGraph().getCountrySet().get(country1),
                		riskMapRW.getMapGraph().getCountrySet().get(country2));
                skipNewLines(scan);
                editExistingMap();
                break;
            case 8:
                if (!checkCountriesAreAdjacent()) {
                    return false;
                }
                if (!checkMinimumCountriesInContinent()) {
                    return false;
                }
                RiskGraphConnected connected = new RiskGraphConnected(new HashSet<Country>(mapIO.getMapGraph().getCountrySet().values()));

                if (!connected.isGraphConnected()) {
                    return false;
                }
                System.out.println("\nPlease enter a new file name to save map file.");
                String fileName = scan.nextLine().trim();
                riskMapRW.setNewFileName(fileName);
                riskMapRW.writeMapFile(false);
                LaunchGameDriver.status = true;
                return true;
            break;
            default:
                System.out.println("Please enter valid input.");
                editExistingMap();
                break;
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
        for (Map.Entry<com.Risk.Team.model.Country, ArrayList<com.Risk.Team.model.Country>> countries : riskMapRW.getMapGraph().getAdjacentCountries().entrySet()) {
            com.Risk.Team.model.Country checkCountry = countries.getKey();
            ArrayList<com.Risk.Team.model.Country> neighbours = countries.getValue();
            for (com.Risk.Team.model.Country adjacent : neighbours) {
                if (!riskMapRW.getMapGraph().getAdjacentCountries().get(adjacent).contains(checkCountry)) {
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
        for (com.Risk.Team.model.Continent continent : riskMapRW.getMapGraph().getContinents().values()) {
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
     * @param scan Scanner object
     */
    public void editMapTagData(Scanner scan) {

    	riskMapRW.getMapTagInfo().clear();

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

        riskMapRW.getMapTagInfo().add(author);
        riskMapRW.getMapTagInfo().add(warn);
        riskMapRW.getMapTagInfo().add(image);
        riskMapRW.getMapTagInfo().add(wrap);
        riskMapRW.getMapTagInfo().add(scroll);

        System.out.println("Added map tag data.");
    }

    /**
     * Method for skipping blank lines in the .map file.
     *
     * @param scan Scanner object
     */
    private void skipNewLines(Scanner scan) {
        do {
        } while (scan.nextLine().equals("\n"));
    }

    /**
     * Method for printing current map details which is being created.
     */
    private void printCurrentMapContents() {
        System.out.println("\nCurrent map contents:\n");
        System.out.println("Map tag data [MAP]:");

        ////
        riskMapRW.getMapTagInfo().forEach(System.out::println);

        for (Map.Entry<String, com.Risk.Team.model.Continent> entry : riskMapRW.getMapGraph().getContinents().entrySet()) {
            System.out.println("\nName of continent =" + " " + entry.getValue().getName() + ", control value = "
                    + entry.getValue().getControlValue());

            ////
            entry.getValue().getListOfCountries().forEach(c -> {
                System.out.print("Adjacent countries to " + c.getName() + " are : ");
                c.getAdjacentCountries().forEach(c1 -> System.out.print(" " + c1.getName() + ", "));
                System.out.println();
            });
        }
    }
}

