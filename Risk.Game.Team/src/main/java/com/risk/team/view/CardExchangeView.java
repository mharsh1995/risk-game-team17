package com.risk.team.view;

import java.util.ArrayList;
import java.util.Scanner;
import com.risk.team.controller.RiskCardController;
import com.risk.team.model.Card;
import com.risk.team.model.Player;

/** CardExchangeView class methods for exchanging cards
 * @author Kartika Patil
 * @author Jenny
 */
public class CardExchangeView {
	
	/** cardController object for RiskCardController class
	 */
	private RiskCardController cardcontroller;
	
	/** entryText Message to be displayed on entry of card exchange phase
	 */
	private static final String entryText = "*********WELCOME TO THE CARD EXCHANGE VIEW***********";
	
	/** exitText Message to be displayed on exit of card exchange phase
	 */
	private static final String exitText =  "********EXIT CARD EXCHANGE VIEW**********************";
	
	
	/** Constructor for CardExchangeView with parameter
	 * @param controller RiskCardController
	 */
	public CardExchangeView(RiskCardController controller)
	{
		System.out.println(entryText);
		cardcontroller = controller;
		process();
		System.out.println(exitText);
	}
	
	/** Method to start process for card exchange
	 */
	private void process() {
		if(cardcontroller.initializeTrade())
		{
		    	    	
				displayCards();
				
				if(cardcontroller.player.getListOfCards().size()>=5)
				{
					
					System.out.println("You must exchange cards");
					
					while(true)
					{
							boolean flag = cardcontroller.checkTrade();
							
								
							if(flag)
							{
								cardTrade();
								break;
								
							}
					}
					
				}
				else
				{					
					boolean condition = true;

					while (condition)
					{
					 
					
					Scanner scan = new Scanner(System.in);
					System.out.println("Do you want to Trade Cards? (Select : Yes or No)");
					
					boolean flag1 = scan.nextLine().trim().equalsIgnoreCase("Yes");
					
							 if(flag1) {
									
								boolean flag = cardcontroller.checkTrade();
								
									if(flag)
									{
										cardTrade();
										condition=false;
									}else
									{   
										
										condition = true;
									}
								}
							 else
							 {
								 
								
								 condition=false;
							 }
							 
					
					}}
		}	
		
		else
		{
			System.out.println("Player does not own any card yet");
		}
		
		return;
	}
	
	/** Method for trading cards for armies
	 */
	private void cardTrade() {
					
		System.out.println(cardcontroller.player.getArmyCount()+" : Army Count Before Card Trade");
		
		
		cardcontroller.player.exchangeCards(cardcontroller.card.getCardsToTrade(), cardcontroller.player.getCardSetCount());
		
		System.out.println(cardcontroller.player.getArmyCount()+": Army Count After Card Trade");	
		
		
		
	}
	
	/** Method to display the cards available
	 */
	public void displayCards()
	{
		cardcontroller.loadCards();
	}
	
	
	
	
}
