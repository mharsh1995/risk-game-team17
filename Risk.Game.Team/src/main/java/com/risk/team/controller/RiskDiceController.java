package com.risk.team.controller;



import com.risk.team.model.*;

import java.util.ArrayList;
import java.util.Scanner;



public class RiskDiceController {

	
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
	
	boolean flag = true;
	
	public Dice dice;
	public boolean diceValidateCount;


	private boolean flag5 = true;
	
	public RiskDiceController(Dice dice) {
		this.dice = dice;
		this.attackerdicenumberlist = new ArrayList<Boolean>();
		this.defenderdicenumberlist = new ArrayList<Boolean>();
		this.attackingPlayer = dice.getAttackingCountry().getPlayer();
		this.defendingPlayer =  dice.getDefendingCountry().getPlayer();
		
	}
	
	
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
			this.dice1_Attacker = false;
			this.dice2_Attacker = false;
			this.dice3_Attacker = false;
			
			this.dice1_Defender = false;
			this.dice2_Defender = false;
			
			
			
			
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
	
	private void diceSelectionAllOutMode() {
		int attckingArmies = dice.getAttackingCountry().getNoOfArmies();
		int defendingArmies = dice.getDefendingCountry().getNoOfArmies();
		
		if (attckingArmies == 2)
		{
			this.dice1_Attacker = true;
		}
		else if (attckingArmies == 3)
		{
			this.dice1_Attacker = true;
			this.dice2_Attacker = true;
		}
		else 
			
		{
			this.dice1_Attacker = true;
			this.dice2_Attacker = true;
			this.dice3_Attacker = true;
			
		}
		
		if (defendingArmies == 1)
		{
			dice1_Defender =  true;
		}
		else{
		

		this.dice1_Defender = true;
		this.dice2_Defender = true;
		
		}
		
		attackerdicenumberlist.add(dice1_Attacker);
		attackerdicenumberlist.add(dice2_Attacker);
		attackerdicenumberlist.add(dice3_Attacker);
		
		defenderdicenumberlist.add(dice1_Defender);
		defenderdicenumberlist.add(dice2_Defender);
	}


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
					else
					{
						System.out.println("Your army count is less than number of dice selected , Please re-select dice count");
						flag1 = true;
					}
					
					
				}
				
				attackerdicenumberlist.add(dice1_Attacker);
				attackerdicenumberlist.add(dice2_Attacker);
				attackerdicenumberlist.add(dice3_Attacker);
				
				while (flag2)
				{
					flag2 = false;
					System.out.println("Please mention the no of dice to be rolled for defender either 1 or 2");
					int number = Integer.parseInt(scan.nextLine());
					
					if(dice.getDefendingCountry().getNoOfArmies() >= 2)
					{
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
								System.out.println("Please re-select the no of dice between 1-2");
								flag2 = true;
							}
					}
					else
					{
						System.out.println("Available armies are less then no of dice selected,So only one dice is selected");
						this.dice1_Defender = true;
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
    	attackerdicenumberlist.clear();
    	defenderdicenumberlist.clear();
    	dice.setAttackerDiceList(new ArrayList<>());
		dice.setDefenderDiceList(new ArrayList<>());
		loadAttackScreen();
		
	}
	
	
	
	
	/**
	 * Method to start roll
	 * 
	 * @param event
	 *  	ActionEvent
	 * @return 
	 *  
	 */
	
	public void startRoll() {
		diceValidateCount = false;
		
		if (!dice1_Attacker && !dice2_Attacker && !dice3_Attacker ) {
			System.out.println ("Atleast one attacking dice should be selected");
			return ;
		} else if (!dice1_Defender && !dice2_Defender ) {
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