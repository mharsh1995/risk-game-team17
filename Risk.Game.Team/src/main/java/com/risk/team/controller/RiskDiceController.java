package com.risk.team.controller;

import com.risk.team.model.*;

import java.util.ArrayList;
import java.util.Scanner;

/** DiceController class contains methods for dice roll
 * @author Kartika Patil
 */

public class RiskDiceController {

	/** attackingPlayer object for Attacker */
	private Player attackingPlayer;

	/** attackingCountry object for Attacker Country*/
	private Country attackingCountry;

	/** attackingArmies armies owned by attacking country*/
	private int attackingArmies;

	/** defendingPlayer object for Defender */
	private Player defendingPlayer;

	/** defendingCountry object for Attacker Country*/
	private Country defendingCountry;

	/** defendingArmies armies owned by Defending country*/
	private int  defendingArmies;

	/** winnerResult Result displayed on winning the dice roll*/
	private String winner_result;

	/** dice1Attacker Flag value for Dice1 of Attacker*/
	public boolean dice1Attacker = false;

	/** dice2Attacker Flag value for Dice2 of Attacker*/
	public boolean dice2Attacker = false;

	/** dice3Attacker Flag value for Dice3 of Attacker*/
	public boolean dice3Attacker = false;

	/** dice1Defender Flag value for Dice1 of Defender*/
	public boolean dice1Defender = false;

	/** dice2Defender Flag value for Dice2 of Defender*/
	public boolean dice2Defender = false;	

	/** attackerWon Flag to check if attacker has won or not */
	public boolean attacker_won = false;	

	/** attackerDiceNumberList ArrayList of Attacker's dice values */
	public ArrayList<Boolean> attackerdicenumberlist ;

	/** defenderDiceNumberList ArrayList of Defender's dice values */
	public ArrayList<Boolean> defenderdicenumberlist ;

	/** Flag variable */
	boolean flag = true;

	/** dice Object for Dice Class*/
	public Dice dice;
	public boolean diceValidateCount;


	private boolean flag5 = true;

	/** DiceController with parameter
	 * @param dice Dice
	 * */
	public RiskDiceController(Dice dice) {
		this.dice = dice;
		this.attackerdicenumberlist = new ArrayList<Boolean>();
		this.defenderdicenumberlist = new ArrayList<Boolean>();
		this.attackingPlayer = dice.getAttackingCountry().getPlayer();
		this.defendingPlayer =  dice.getDefendingCountry().getPlayer();

	}

	/** method to start dice roll
	 * @return attackerWon
	 * */
	public boolean start()
	{
		Scanner scan = new Scanner(System.in);

		loadAttackScreen();
		if(attacker_won)
		{
			System.out.println("Do you want to move more than one army to won country??(Yes/No)");
			if(scan.nextLine().trim().equalsIgnoreCase("Yes"))
			{
				System.out.println("Do you want to move all to won country??(Yes/No)");
				if(scan.nextLine().trim().equalsIgnoreCase("Yes"))
				{
					dice.moveAllArmies();
				}
				else
				{

					System.out.println("Please Enter the number of armies to move");
					int armiesToMove = Integer.parseInt(scan.nextLine());
					dice.moveArmies(armiesToMove);

				}

			}
			else
			{

				dice.skipMoveArmy();
			}
		}

		return attacker_won;
	}

	/** method to load attack screen
	 * */
	public void loadAttackScreen() {
		Scanner scan = new Scanner(System.in);
		Country countryAttacking = dice.getAttackingCountry();
		System.out.println(countryAttacking.getPlayer().getName());
		System.out.println(countryAttacking.getName());
		System.out.println("Attacking Armies: " + countryAttacking.getNoOfArmies());

		Country countryDefending = dice.getDefendingCountry();
		System.out.println(countryDefending.getPlayer().getName());
		System.out.println(countryDefending.getName());
		System.out.println("Defending Armies: " + countryDefending.getNoOfArmies());

		if (dice.checkDiceThrowPossible())
		{		
			this.dice1Attacker = false;
			this.dice2Attacker = false;
			this.dice3Attacker = false;

			this.dice1Defender = false;
			this.dice2Defender = false;

			if(!attackingPlayer.allOutMode)
			{
				diceSelection();
				boolean flagVar = true;

				while(flagVar)
				{
					System.out.println("Do You wanna Start Roll");
					if(scan.nextLine().trim().equalsIgnoreCase("Yes")) {		
						startRoll();
						flagVar = false;

					}
					else
					{
						dice.cancelDiceThrow();
						flagVar = false;
						return;

					}
				}

				while(flag)
				{

					System.out.println("Attacker Armies: " + String.valueOf(countryAttacking.getNoOfArmies()));
					System.out.println("Defender Armies: " + String.valueOf(countryDefending.getNoOfArmies()));


					System.out.println(winner_result);
					flag = false;
				}

			}

			else
			{
				diceSelectionAllOutMode();
				startRoll();

				while(flag5 )
				{

					System.out.println("Attacker Armies: " + String.valueOf(countryAttacking.getNoOfArmies()));
					System.out.println("Defender Armies: " + String.valueOf(countryDefending.getNoOfArmies()));


					System.out.println(winner_result);
					flag5 = false;
				}
			}
		}
		else
		{

			System.out.println("Dice throw is not possible");

		}
	}

	/** method for dice selection in all out mode
	 * */
	public void diceSelectionAllOutMode() {
		int attckingArmies = dice.getAttackingCountry().getNoOfArmies();
		int defendingArmies = dice.getDefendingCountry().getNoOfArmies();

		if (attckingArmies == 2)
		{
			this.dice1Attacker = true;
		}
		else if (attckingArmies == 3)
		{
			this.dice1Attacker = true;
			this.dice2Attacker = true;
		}
		else 

		{
			this.dice1Attacker = true;
			this.dice2Attacker = true;
			this.dice3Attacker = true;

		}

		if (defendingArmies == 1)
		{
			dice1Defender =  true;
		}
		else{


			this.dice1Defender = true;
			this.dice2Defender = true;

		}

		attackerdicenumberlist.add(dice1Attacker);
		attackerdicenumberlist.add(dice2Attacker);
		attackerdicenumberlist.add(dice3Attacker);

		defenderdicenumberlist.add(dice1Defender);
		defenderdicenumberlist.add(dice2Defender);
	}

	/** method to select dice
	 * */
	private void diceSelection() {

		Scanner scan = new Scanner(System.in);

		boolean flag1 = true;
		boolean flag2 = true;
		while (flag1)
		{
			flag1 = false;
			System.out.println("Please mention the no of dice to be rolled for attacker");

			int number = Integer.parseInt(scan.nextLine());

			if(number < dice.getAttackingCountry().getNoOfArmies() && number > 0)
			{

				if (number == 1)
				{
					this.dice1Attacker = true;
				}
				else if (number == 2)
				{
					this.dice1Attacker = true;
					this.dice2Attacker = true;
				}
				else if ( number == 3)

				{
					this.dice1Attacker = true;
					this.dice2Attacker = true;
					this.dice3Attacker = true;

				}
				else
				{
					System.out.println("Please re-select the no of dice between 1-3");
					flag1 = true;
				}
			}
			else
			{
				System.out.println("Your army count is less than number of dice selected , Please re-select dice count");
				flag1 = true;
			}


		}

		attackerdicenumberlist.add(dice1Attacker);
		attackerdicenumberlist.add(dice2Attacker);
		attackerdicenumberlist.add(dice3Attacker);

		while (flag2)
		{
			flag2 = false;
			System.out.println("Please mention the no of dice to be rolled for defender either 1 or 2");
			int number = Integer.parseInt(scan.nextLine());

			if(dice.getDefendingCountry().getNoOfArmies() >= 2)
			{
				if (number == 1)
				{
					this.dice1Defender = true;
				}
				else if (number == 2)
				{

					this.dice1Defender = true;
					this.dice2Defender = true;												

				}

				else
				{
					System.out.println("Please re-select the no of dice between 1-2");
					flag2 = true;
				}
			}
			else
			{
				System.out.println("Available armies are less then no of dice selected,So only one dice is selected");
				this.dice1Defender = true;
			}

		}

		defenderdicenumberlist.add(dice1Defender);
		defenderdicenumberlist.add(dice2Defender);

	}

	/** method to calculate dice value for attacker
	 * */
	public void attackDiceValue() {
		for (Boolean dicenum : attackerdicenumberlist) {
			if (dicenum) {
				int diceValue = dice.generateRandomNumber();
				dice.getAttackerDiceList().add(diceValue);
			}
		}
	}

	/** method to calculate dice value for defender
	 * */
	public void defenceDiceValue() {
		for (Boolean dicenum : defenderdicenumberlist) {
			if (dicenum) {
				int diceValue = dice.generateRandomNumber();
				dice.getDefenderDiceList().add(diceValue);
			}
		}
	}

	/** method if the dice roll is continued
	 * */
	public void continueDiceRoll() {
		attackerdicenumberlist.clear();
		defenderdicenumberlist.clear();
		dice.setAttackerDiceList(new ArrayList<>());
		dice.setDefenderDiceList(new ArrayList<>());
		loadAttackScreen();

	}

	/**
	 * Method to start roll
	 */
	public void startRoll() {
		diceValidateCount = false;

		if (!dice1Attacker && !dice2Attacker && !dice3Attacker ) {
			System.out.println ("Atleast one attacking dice should be selected");
			return ;
		} else if (!dice1Defender && !dice2Defender ) {
			System.out.println ("Atleast one defender dice should be selected");
			return ;
		}

		diceValidateCount = true;

		attackDiceValue();
		defenceDiceValue();

		ArrayList<String> diceResult = dice.getDicePlayResult();

		Country countryAttacking = dice.getAttackingCountry();
		Country defendingCountry = dice.getDefendingCountry();

		if (defendingCountry.getNoOfArmies() <= 0) {
			diceResult.add(countryAttacking.getPlayer().getName() + " won " + defendingCountry.getName() + " Country");
			dice.setCountriesWonCount(dice.getCountriesWonCount() + 1);
			winner_result = countryAttacking.getPlayer().getName() + " won " + defendingCountry.getName() + " Country";
			attacker_won = true;

		} else if (countryAttacking.getNoOfArmies() < 2) {
			diceResult.add(countryAttacking.getPlayer().getName() + " lost the match");
			winner_result = countryAttacking.getPlayer().getName() + " lost the match";

		} else {

			continueDiceRoll();

		}


	}

}