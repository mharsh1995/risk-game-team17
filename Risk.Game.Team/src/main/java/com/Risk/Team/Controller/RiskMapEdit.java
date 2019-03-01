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
    public RiskMapRW getRiskMapRW() {
        return mapObj;
    }

    /**
     * This method allows creation of new map. The user can choose from 9 options:
     * 1) Enter Map Tag
     * 2) Add a Continent
     * 3) Remove a Continent
     * 4) Add a Country
     * 5) Remove a Country
     * 6) Add an Edge between Countries
     * 7) Remove an Edge between Countries
     * 8) Show Map Details
     * 9) Save Map and Exit
     * 
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
                    if (mapObj.getMapGraph().getContinents().containsKey(continentInput.split("=")[0])) {
                        System.out.println(continentInput.split("=")[0] + "  Continent already exists!! Enter a different Configuration!!!");
                        --i;
                        continue;
                    }
                    Continent continent = new Continent(continentInput.split("=")[0], Integer.parseInt(continentInput.split("=")[1]));
                    mapObj.getMapGraph().addContinent(continent);
                }
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
                createEditMap(flag);
                break;
                
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            	
            default:
                System.out.println("Please enter valid input.");
                createEditMap(flag);
                break;
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
        
        
        mapObj.getMapTagInfo().forEach(System.out::println);

        for (Map.Entry<String,Continent> entrySet : mapObj.getMapGraph().getContinents().entrySet()) {
            System.out.println("\nContinent Name=" + " " + entrySet.getValue().getName() + ", Associated Value = "+ entrySet.getValue().getControlValue());
                entrySet.getValue().getListOfCountries().forEach(c -> {
                System.out.print(c.getName() + " has the following countries adjacent to it: ");
                c.getAdjacentCountries().forEach(c1 -> System.out.print(" " + c1.getName() + ", "));
                System.out.println();
            });
        }
    }
}


