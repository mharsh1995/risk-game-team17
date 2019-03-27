package com.risk.team.model;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Observable;
import java.util.Random;
import com.risk.team.model.*;


/**
 * Class for the dice. Attacking player can have a maximum of 3 dices
 * and the defender can have a maximum of 2 dices. It has methods for
 * simulating the dice rolls for the attack phase
 *
 * @author Kartika Patil 
 */
public class Dice  {

    /**
     * Country which is attacking the defending country,
     * adjacent to it.
     */
    private Country attackingCountry;

    /**
     * List of dice values of the attacking player
     */
    private ArrayList<Integer> attackerDiceList;

    /**
     * Country is is under attacked
     */
    private Country defendingCountry;

    /**
     * List of dice values of the defending player
     */
    private ArrayList<Integer> defenderDiceList;

    /**
     * Number of countries won by the attacking player
     */
    private int countriesWonCount;

    /**
     * Constructor for the dice class. It initializes the
     * value of the attacking and defending countries.
     *
     * @param attackingCountry Country which is attacking currently
     * @param defendingCountry Country under attack
     */
    public Dice(Country attackingCountry, Country defendingCountry) {
        this.attackingCountry = attackingCountry;
        this.defendingCountry = defendingCountry;
        attackerDiceList = new ArrayList<>();
        defenderDiceList = new ArrayList<>();
    }

    /**
     * Getter for the attacking Country.
     *
     * @return Attacking country.
     */
    public Country getAttackingCountry() {
        return attackingCountry;
    }

    /**
     * Setter for attacking country
     *
     * @param attackingCountry Attacking country.
     */
    public void setAttackingCountry(Country attackingCountry) {
        this.attackingCountry = attackingCountry;
    }

    /**
     * Getter for attacking dice values.
     *
     * @return list of values of the attacking dice.
     */
    public ArrayList<Integer> getAttackerDiceList() {
        return attackerDiceList;
    }

    /**
     * Setter for attacking dice values.
     *
     * @param attackerDiceList list of values of the attacking dice.
     */
    public void setAttackerDiceList(ArrayList<Integer> attackerDiceList) {
        this.attackerDiceList = attackerDiceList;
    }

    /**
     *  Getter for the defending Country.
     *
     * @return Country underattack
     */
    public Country getDefendingCountry() {
        return defendingCountry;
    }

    /**
     * Setter for defending dice values.
     *
     * @param defendingCountry list of values of the defending dice.
     */
    public void setDefendingCountry(Country defendingCountry) {
        this.defendingCountry = defendingCountry;
    }

    /**
     * Getter  for defending dice values.
     *
     * @return list of values of the defending dice.
     */

    public ArrayList<Integer> getDefenderDiceList() {
        return defenderDiceList;
    }

    /**
     * Setter  for defending dice values.
     *
     * @param defenderDiceList list of values of the defending dice.
     */

    public void setDefenderDiceList(ArrayList<Integer> defenderDiceList) {
        this.defenderDiceList = defenderDiceList;
    }

    /**
     * Getter for count of countries won
     *
     * @return Number of countries won by the player
     */
    public int getCountriesWonCount() {
        return countriesWonCount;
    }

    /**
     * Setter for count of countries won
     *
     * @param countriesWonCount Number of countries won by the player
     */
    public void setCountriesWonCount(int countriesWonCount) {
        this.countriesWonCount = countriesWonCount;
    }

    /**
     * Method for comparing the dice values of attacker and defender.
     * It compares the value in descending order.
     *
     * @return List of the results of the dice throw
     */
    public ArrayList<String> getDicePlayResult(){
        ArrayList<String> diceThrowResult = new ArrayList<>();
    
        System.out.println("Attacker Dice Roll results : " + attackerDiceList.size() + " dice has been rolled");
        for (Integer result : attackerDiceList) {
			System.out.println(result + " ");
		}
        System.out.println("Defender Dice Roll results" + defenderDiceList.size() + " dice has been rolled");
		for (Integer result : defenderDiceList) {
			System.out.println(result + " ");
		}
			
		
		Collections.sort(attackerDiceList);
		Collections.reverse(attackerDiceList);
		Collections.sort(defenderDiceList);
		Collections.reverse(defenderDiceList);
		
		int maximumAttackerDice = attackerDiceList.size();
		int maximumDefenderDice = defenderDiceList.size();
		
		int minimumDiceNumber = maximumAttackerDice < maximumDefenderDice ? maximumAttackerDice
				: maximumDefenderDice;
		
		for (int i = 0; i < minimumDiceNumber; i++) {
			if (attackerDiceList.get(i) != null && defenderDiceList.get(i) != null) {
				System.out.println("Result number " + (i + 1));
				int attackerValue = attackerDiceList.get(i);
				int defenderValue = defenderDiceList.get(i);
				System.out.println("Attacker Dice value " + attackerDiceList.get(i));
				System.out.println("Defender Dice value " + defenderDiceList.get(i));
				
				updateArmiesAfterAttack(defenderValue, attackerValue, diceThrowResult);
				
			} else {
				break;
			}
		}
        
        
        
        return diceThrowResult;
    }

    /**
     * Method is used to update the armies of the players,
     * after the attack has been completed.
     *
     *
     * @param defenderValue Values of defender dice
     * @param attackerValue Values of attacker dice
     * @param playResult List of attack results
     */
    public void updateArmiesAfterAttack(Integer defenderValue, Integer attackerValue, ArrayList<String> playResult) {
    	System.out.println(attackerValue.compareTo(defenderValue));
    	
        if (attackerValue.compareTo(defenderValue) > 0) {    	
            playResult.add("Defender has lost one army.");
            if (defendingCountry.getNoOfArmies() > 0) {
                defendingCountry.setNoOfArmies(defendingCountry.getNoOfArmies() - 1);
            }
            System.out.println("Defender has lost one army" +"Now it has : "+defendingCountry.getNoOfArmies());
        } 
        else {
        	
            playResult.add("Attacker has lost one army.");
            if (attackingCountry.getNoOfArmies() > 1) {
                attackingCountry.setNoOfArmies(attackingCountry.getNoOfArmies() - 1);
            }         
            System.out.println("Attacker has lost one army."+ "Now it has : "+ attackingCountry.getNoOfArmies());
        }
    }

    /**
     * Method of indicate that the dice roll is complete
     * or cancelled
     */
    public void cancelDiceThrow(){
      
    }

    /**
     * Method for moving all the armies,
     * after attacker has won a country.
     */

    public void moveAllArmies() {
        int attackingArmyCount = getAttackingCountry().getNoOfArmies();
        getAttackingCountry().setNoOfArmies(1);
        getDefendingCountry().setNoOfArmies(attackingArmyCount - 1);
        updateCountryList();
       
    }

    /**
     * Method for skipping the army move after attacke has won the attack.
     * But at least one army will move from attacking to defending country.
     */
    public void skipMoveArmy() {
        int attackingArmyCount = getAttackingCountry().getNoOfArmies();
        getAttackingCountry().setNoOfArmies(attackingArmyCount - 1);
        getDefendingCountry().setNoOfArmies(1);
        updateCountryList();
       
    }

    /**
     * Mehtod for updating the country list after attacker
     * has won at least one country.
     */
    public void updateCountryList() {
        ArrayList<Country> defenderCountries = defendingCountry.getPlayer().getMyCountries();
        defenderCountries.remove(defendingCountry);

        defendingCountry.setPlayer(attackingCountry.getPlayer());
        defendingCountry.getPlayer().getMyCountries().add(defendingCountry);

    }

    /**
     * Method for moving armies from attacker's country to the defending country,
     * if attckes won the country.
     *
     *
     * @param armiesToMove Number aries to move
     * @param message text fir the label which displays how many armies were moved
     * @param moveArmies Button to execute the method.
     */
    public boolean moveArmies(int armiesToMove) {
        int currentArmies = getAttackingCountry().getNoOfArmies();
        if (currentArmies <= armiesToMove) {
            
            System.out.println("You can move a maximum of " + (currentArmies - 1) + " armies");
            return false;
        } else {
            getAttackingCountry().setNoOfArmies(currentArmies - armiesToMove);
            getDefendingCountry().setNoOfArmies(armiesToMove);
            updateCountryList();        
            return true;
        }
    }

    /**
     * Method for generating the dice value between 1 to 6.
     * @return A random integer between 1 to 6.
     */
    public int generateRandomNumber() {
        return new Random().nextInt(6) + 1;
    }

    /**
     *  Check if attacker has sufficient number of armies
     *
     * @return true if attacker can execute the attack; else false.
     */

    public boolean checkDiceThrowPossible() {
        boolean diceThrowPossible = true;
        if (attackingCountry.getNoOfArmies() < 2 || defendingCountry.getNoOfArmies() <= 0) {
            diceThrowPossible = false;
        }
        return diceThrowPossible;
    }
}
