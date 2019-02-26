package com.risk.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.risk.model.Continent;
import com.risk.model.Country;
import com.risk.view.LaunchGameDriver;

/**
 * Class proving the m=functionality for creating a new map or edit existing map
 * according the risk game rules.
 *
 * @author Dhaval Desai
 */
public class editGameMap {

    /**
     * Variable for MapIO object
     */
    private MapIO mapIO;

    /**
     * Constructor for the MapEditor class Initializes mapIO object.
     */
    public MapEditor() {
        this.mapIO = new MapIO();
    }

    /**
     * Constructor for the MapEditor class Initializes mapIO object.
     *
     * @param mapIO MapIO object.
     */
    public MapEditor(MapIO mapIO) {
        this.mapIO = mapIO;
    }

    /**
     * Getter to fetch the MapIO Object
     *
     * @return MapIO returns mapIO Object
     */
    public MapIO getMapIO() {
        return mapIO;
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
                    if (mapIO.getMapGraph().getContinents().containsKey(line.split("=")[0])) {
                        System.out.println("Continent " + line.split("=")[0] + " is already defined.");
                        --i;
                        continue;
                    }
                    Continent continent = new Continent(line.split("=")[0], Integer.parseInt(line.split("=")[1]));
                    mapIO.getMapGraph().addContinent(continent);
                }
                skipNewLines(scan);
                createNewMap();
                break;
            case 3:
                System.out.println("Enter the name of the continent to be deleted.");
                String continentName = scan.nextLine();
                if (!mapIO.getMapGraph().getContinents().containsKey(continentName.trim())) {
                    System.out.println("Continent " + continentName + " does not exist");
                    createNewMap();
                }
                mapIO.getMapGraph().removeContinent(mapIO.getMapGraph().getContinents().get(continentName.trim()));
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
                        if (mapIO.getMapGraph().getContinents().get(input[3].trim()) == null) {
                            System.out.println("Continent " + input[3].trim() + " does not exist.");
                            --i;
                            continue;
                        }
                        Country country = null;
                        if (!mapIO.getMapGraph().getCountrySet().containsKey(input[0].trim())) {
                            country = new Country(input[0].trim());
                            country.setContinent(mapIO.getMapGraph().getContinents().get(input[3]).getName());
                            country.setPartOfContinent(mapIO.getMapGraph().getContinents().get(input[3]));
                            country.setxValue(input[1]);
                            country.setyValue(input[2]);
                        } else {
                            country = mapIO.getMapGraph().getCountrySet().get(input[0].trim());
                            country.setContinent(mapIO.getMapGraph().getContinents().get(input[3]).getName());
                            country.setPartOfContinent(mapIO.getMapGraph().getContinents().get(input[3]));
                            country.setxValue(input[1]);
                            country.setyValue(input[2]);
                        }
                        ArrayList<Country> countries = new ArrayList<>();
                        for (int j = 4; j < input.length; ++j) {
                            Country adjacentCountry;
                            if (!mapIO.getMapGraph().getCountrySet().containsKey(input[j].trim())) {
                                adjacentCountry = new Country(input[j].trim());
                                mapIO.getMapGraph().getCountrySet().put(input[j].trim(), adjacentCountry);
                            } else {
                                adjacentCountry = mapIO.getMapGraph().getCountrySet().get(input[j].trim());
                            }
                            if (!adjacentCountry.getAdjacentCountries().contains(country)) {
                                adjacentCountry.getAdjacentCountries().add(country);
                            }
                            countries.add(adjacentCountry);
                        }
                        country.setAdjacentCountries(countries);
                        mapIO.getMapGraph().addCountry(country);
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
                mapIO.getMapGraph().removeCountry(mapIO.getMapGraph().getCountrySet().get(countryName.trim()));
                skipNewLines(scan);
                createNewMap();
                break;
            case 6:
                System.out.println("Enter the name of two countries to be connected, separated by newline.");
                String country1 = scan.nextLine();
                String country2 = scan.nextLine();
                if (!mapIO.getMapGraph().getCountrySet().containsKey(country1)) {
                    System.out.println("Country " + country1 + " does not exist.");
                    createNewMap();
                } else if (!mapIO.getMapGraph().getCountrySet().containsKey(country2)) {
                    System.out.println("Country " + country2 + " does not exist.");
                    createNewMap();
                } else if (mapIO.getMapGraph().checkAdjacency(mapIO.getMapGraph().getCountrySet().get(country1),
                        mapIO.getMapGraph().getCountrySet().get(country2))) {
                    System.out.println("Both countries are already adjacent.");
                    createNewMap();
                }
                mapIO.getMapGraph().addEdgeBetweenCountries(mapIO.getMapGraph().getCountrySet().get(country1),
                        mapIO.getMapGraph().getCountrySet().get(country2));
                skipNewLines(scan);
                createNewMap();
                break;
            case 7:
                System.out.println("Enter the name of two countries to be disconnected, separated by newline.");
                String country1 = scan.nextLine();
                String country2 = scan.nextLine();
                if (!mapIO.getMapGraph().getCountrySet().containsKey(country1)) {
                    System.out.println("Country " + country1 + " does not exist.");
                    createNewMap();
                } else if (!mapIO.getMapGraph().getCountrySet().containsKey(country2)) {
                    System.out.println("Country " + country2 + " does not exist.");
                    createNewMap();
                } else if (!mapIO.getMapGraph().checkAdjacency(mapIO.getMapGraph().getCountrySet().get(country1),
                        mapIO.getMapGraph().getCountrySet().get(country2))) {
                    System.out.println("Both countries are already not adjacent.");
                    createNewMap();
                }
                mapIO.getMapGraph().deleteEdgeBetweenCountries(mapIO.getMapGraph().getCountrySet().get(country1),
                        mapIO.getMapGraph().getCountrySet().get(country2));
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
                ConnectedGraph connected = new ConnectedGraph(
                        new HashSet<Country>(mapIO.getMapGraph().getCountrySet().values()));

                if (!connected.isConnected()) {
                    return false;
                }
                System.out.println("\nPlease enter the file name to save map file.");
                String fileName = scan.nextLine();
                mapIO.setFileName(fileName);
                mapIO.writeToFile(true);
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
                    if (mapIO.getMapGraph().getContinents().containsKey(line.split("=")[0])) {
                        System.out.println("Continent " + line.split("=")[0] + " is already defined.");
                        --i;
                        continue;
                    }
                    Continent continent = new Continent(line.split("=")[0], Integer.parseInt(line.split("=")[1]));
                    mapIO.getMapGraph().addContinent(continent);
                }
                skipNewLines(scan);
                editExistingMap();
                break;
            case 3:
                System.out.println("Enter the name of the continent to be deleted.");
                String continentName = scan.nextLine();
                if (!mapIO.getMapGraph().getContinents().containsKey(continentName.trim())) {
                    System.out.println("Continent " + continentName + " does not exist");
                    editExistingMap();
                }
                mapIO.getMapGraph().removeContinent(mapIO.getMapGraph().getContinents().get(continentName.trim()));
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
                        if (mapIO.getMapGraph().getContinents().get(input[3].trim()) == null) {
                            System.out.println("Continent " + input[3].trim() + " does not exist.");
                            --i;
                            continue;
                        }
                        Country country = null;
                        if (!mapIO.getMapGraph().getCountrySet().containsKey(input[0].trim())) {
                            country = new Country(input[0].trim());
                            country.setContinent(mapIO.getMapGraph().getContinents().get(input[3].trim()).getName());
                            country.setPartOfContinent(mapIO.getMapGraph().getContinents().get(input[3].trim()));
                            country.setxValue(input[1]);
                            country.setyValue(input[2]);
                        } else {
                            country = mapIO.getMapGraph().getCountrySet().get(input[0].trim());
                            country.setContinent(mapIO.getMapGraph().getContinents().get(input[3].trim()).getName());
                            country.setPartOfContinent(mapIO.getMapGraph().getContinents().get(input[3].trim()));
                            country.setxValue(input[1]);
                            country.setyValue(input[2]);
                        }
                        ArrayList<Country> countries = new ArrayList<>();
                        for (int j = 4; j < input.length; ++j) {
                            Country adjacentCountry;
                            if (!mapIO.getMapGraph().getCountrySet().containsKey(input[j].trim())) {
                                adjacentCountry = new Country(input[j].trim());
                                mapIO.getMapGraph().getCountrySet().put(input[j].trim(), adjacentCountry);
                            } else {
                                adjacentCountry = mapIO.getMapGraph().getCountrySet().get(input[j].trim());
                            }
                            adjacentCountry
                                    .setContinent(mapIO.getMapGraph().getContinents().get(input[3].trim()).getName());
                            adjacentCountry.setPartOfContinent(mapIO.getMapGraph().getContinents().get(input[3].trim()));
                            if (!adjacentCountry.getAdjacentCountries().contains(country)) {
                                adjacentCountry.getAdjacentCountries().add(country);
                            }
                            countries.add(adjacentCountry);
                        }
                        country.setAdjacentCountries(countries);
                        mapIO.getMapGraph().addCountry(country);
                    }
                }
                skipNewLines(scan);
                editExistingMap();
                break;
            case 5:
                System.out.println("Enter the name of the country to be deleted.");
                String countryName = scan.nextLine();
                if (!mapIO.getMapGraph().getCountrySet().containsKey(countryName.trim())) {
                    System.out.println("Country " + countryName + " does not exist");
                    editExistingMap();
                }
                mapIO.getMapGraph().removeCountry(mapIO.getMapGraph().getCountrySet().get(countryName.trim()));
                skipNewLines(scan);
                editExistingMap();
                break;
            case 6:
                System.out.println("Enter the name of two countries to be connected, separated by newline.");
                String country1 = scan.nextLine();
                String country2 = scan.nextLine();
                if (!mapIO.getMapGraph().getCountrySet().containsKey(country1)) {
                    System.out.println("Country " + country1 + " does not exist.");
                    editExistingMap();
                } else if (!mapIO.getMapGraph().getCountrySet().containsKey(country2)) {
                    System.out.println("Country " + country2 + " does not exist.");
                    editExistingMap();
                } else if (mapIO.getMapGraph().checkAdjacency(mapIO.getMapGraph().getCountrySet().get(country1),
                        mapIO.getMapGraph().getCountrySet().get(country2))) {
                    System.out.println("Both countries are already adjacent.");
                    editExistingMap();
                }
                mapIO.getMapGraph().addEdgeBetweenCountries(mapIO.getMapGraph().getCountrySet().get(country1),
                        mapIO.getMapGraph().getCountrySet().get(country2));
                skipNewLines(scan);
                editExistingMap();
                break;
            case 7:
                System.out.println("Enter the name of two countries to be disconnected, separated by newline.");
                String country1 = scan.nextLine();
                String country2 = scan.nextLine();
                if (!mapIO.getMapGraph().getCountrySet().containsKey(country1)) {
                    System.out.println("Country " + country1 + " does not exist.");
                    editExistingMap();
                } else if (!mapIO.getMapGraph().getCountrySet().containsKey(country2)) {
                    System.out.println("Country " + country2 + " does not exist.");
                    editExistingMap();
                } else if (!mapIO.getMapGraph().checkAdjacency(mapIO.getMapGraph().getCountrySet().get(country1),
                        mapIO.getMapGraph().getCountrySet().get(country2))) {
                    System.out.println("Both countries are already not adjacent.");
                    editExistingMap();
                }
                mapIO.getMapGraph().deleteEdgeBetweenCountries(mapIO.getMapGraph().getCountrySet().get(country1),
                        mapIO.getMapGraph().getCountrySet().get(country2));
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
                ConnectedGraph connected = new ConnectedGraph(
                        new HashSet<Country>(mapIO.getMapGraph().getCountrySet().values()));

                if (!connected.isConnected()) {
                    return false;
                }
                System.out.println("\nPlease enter a new file name to save map file.");
                String fileName = scan.nextLine().trim();
                mapIO.setNewFileName(fileName);
                mapIO.writeToFile(false);
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
        for (Map.Entry<Country, ArrayList<Country>> countries : mapIO.getMapGraph().getAdjacentCountries().entrySet()) {
            Country checkCountry = countries.getKey();
            ArrayList<Country> neighbours = countries.getValue();
            for (Country adjacent : neighbours) {
                if (!mapIO.getMapGraph().getAdjacentCountries().get(adjacent).contains(checkCountry)) {
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
        for (Continent continent : mapIO.getMapGraph().getContinents().values()) {
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

        mapIO.getMapTagData().clear();

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

        mapIO.getMapTagData().add(author);
        mapIO.getMapTagData().add(warn);
        mapIO.getMapTagData().add(image);
        mapIO.getMapTagData().add(wrap);
        mapIO.getMapTagData().add(scroll);

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
        mapIO.getMapTagData().forEach(System.out::println);

        for (Map.Entry<String, Continent> entry : mapIO.getMapGraph().getContinents().entrySet()) {
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

