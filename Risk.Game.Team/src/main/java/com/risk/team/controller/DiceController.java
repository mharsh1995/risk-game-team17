package com.risk.team.controller;


import com.risk.team.model.*;

import java.util.ArrayList;
import java.util.Scanner;



public class DiceController {

	
	private Player attackingPlayer;

	
	private Country attackingCountry;
	
	
	private int attackingArmies;
	
	
	private Player defendingPlayer;
	

	private Country defendingCountry;
	
	
	private int  defendingArmies;
	
	
	private String winner_result;
	
	
	private boolean dice1_Attacker = false;
	

	private boolean dice2_Attacker = false;
	
	
	private boolean dice3_Attacker = false;
	
	
	private boolean dice1_Defender = false;
	
	
	private boolean dice2_Defender = false;	
	
	private boolean attacker_won = false;	
	
	private ArrayList<Boolean> attackerdicenumberlist ;
	
	private ArrayList<Boolean> defenderdicenumberlist ;
	static boolean flag = true;
	
	public Dice dice;
	
	public DiceController(Dice dice) {
		this.dice = dice;
		this.attackerdicenumberlist = new ArrayList<Boolean>();
		this.defenderdicenumberlist = new ArrayList<Boolean>();
		start();
		
	}
	
	
	public void start()
	{
		Scanner scan = new Scanner(System.in);
		
		loadAttackScreen();
		if(attacker_won)
		{
				System.out.println("Do you want to move armies to won country??(Yes/No)");
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
	}
	public void  loadAttackScreen() {
	
	
		Scanner scan = new Scanner(System.in);
		Country countryAttacking = dice.getAttackingCountry();
		System.out.println(countryAttacking.getPlayer().getName());
		System.out.println(countryAttacking.getName());
		System.out.println("Attacking Armies: " + countryAttacking.getNoOfArmies());
		
		Country countryDefending = dice.getDefendingCountry();
		System.out.println(countryDefending.getPlayer().getName());
		System.out.println(countryDefending.getName());
		System.out.println("Defending Armies: " + countryDefending.getNoOfArmies());
		
		diceSelection();
		
		
		System.out.println("Do You wanna Start Roll");
		if(scan.nextLine().trim().equalsIgnoreCase("Yes")) {		
			startRoll();
			
		}
		
		while(flag)
		{
		
		System.out.println("Attacker Armies: " + String.valueOf(countryAttacking.getNoOfArmies()));
		System.out.println("Defender Armies: " + String.valueOf(countryDefending.getNoOfArmies()));
		
		
		System.out.println(winner_result);
		flag = false;
	    }
		
	}
	
	private void diceSelection() {
		
				Scanner scan1 = new Scanner(System.in);
				
				boolean flag1 = true;
				boolean flag2 = true;
				while (flag1)
				{
					flag1 = false;
					System.out.println("Please mention the no of dice to be rolled for attacker");
					int number = Integer.parseInt(scan1.nextLine());
					
					if (number == 1)
					{
						this.dice1_Attacker = true;
					}
					else if (number == 2)
					{
						this.dice1_Attacker = true;
						this.dice2_Attacker = true;
					}
					else if ( number == 3)
						
					{
						this.dice1_Attacker = true;
						this.dice2_Attacker = true;
						this.dice3_Attacker = true;
						
					}
					else
					{
						System.out.println("Please re-select the no of dice between 1-3");
						flag1 = true;
					}
					
					
				}
				
				attackerdicenumberlist.add(dice1_Attacker);
				attackerdicenumberlist.add(dice2_Attacker);
				attackerdicenumberlist.add(dice3_Attacker);
				
				while (flag2)
				{
					flag2 = false;
					System.out.println("Please mention the no of dice to be rolled for defender");
					int number = Integer.parseInt(scan1.nextLine());
					
					if (number == 1)
					{
						this.dice1_Defender = true;
					}
					else if (number == 2)
					{
						this.dice1_Defender = true;
						this.dice2_Defender = true;
					}
					
					else
					{
						System.out.println("Please re-select the no of dice between 1-3");
						flag2 = true;
					}
					
					
				}
		
				defenderdicenumberlist.add(dice1_Defender);
				defenderdicenumberlist.add(dice2_Defender);
				
	}


	
	
	
	public void attackDiceValue() {
		for (Boolean dicenum : attackerdicenumberlist) {
			if (dicenum) {
				int diceValue = dice.generateRandomNumber();
				dice.getAttackerDiceList().add(diceValue);
			}
		}
	}
	
	public void defenceDiceValue() {
		for (Boolean dicenum : defenderdicenumberlist) {
			if (dicenum) {
				int diceValue = dice.generateRandomNumber();
				dice.getDefenderDiceList().add(diceValue);
			}
		}
	}
	
	
    public void continueDiceRoll() {
    	dice.setAttackerDiceList(new ArrayList<>());
		dice.setDefenderDiceList(new ArrayList<>());
		loadAttackScreen();
		
	}
	
	
	
	
	/**
	 * Method to start roll
	 * 
	 * @param event
	 *  	ActionEvent
	 *  
	 */
	
	public void startRoll() {
		if (!dice1_Attacker && !dice2_Attacker && !dice3_Attacker ) {
			System.out.println ("Atleast one attacking dice should be selected");
			return;
		} else if (!dice1_Defender && !dice2_Defender ) {
			System.out.println ("Atleast one defender dice should be selected");
			return;
		}
		
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
