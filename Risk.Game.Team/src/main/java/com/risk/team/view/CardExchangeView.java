package com.risk.team.view;

import java.util.ArrayList;
import java.util.Scanner;
import com.risk.team.controller.RiskCardController;
import com.risk.team.model.Card;
import com.risk.team.model.Player;

public class CardExchangeView {
	
	private RiskCardController card_controller;
	
	private static final String entryText = "*********WELCOME TO THE CARD EXCHANGE VIEW*********";
	
	private static final String exitText = "*********EXIT CARD EXCHANGE VIEW*********";
	
	
	
	public CardExchangeView(RiskCardController controller)
	{
		card_controller = controller;
		process();
		System.out.println(exitText);
	}

	private void process() {
		if(card_controller.initializeTrade())
		{
		    	System.out.println(entryText);
		    	
				displayCards();
				
				if(card_controller.player.getListOfCards().size()>=5)
				{
					
					System.out.println("You must exchange cards");
					
					while(true)
					{
							boolean flag = card_controller.checkTrade();
							
								
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
									
								boolean flag = card_controller.checkTrade();
								
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

	private void cardTrade() {
					
		System.out.println(card_controller.player.getArmyCount()+" : Army Count Before Card Trade");
		
		
		card_controller.player.exchangeCards(card_controller.card.getCardsToTrade(), card_controller.player.getCardSetCount());
		
		System.out.println(card_controller.player.getArmyCount()+": Army Count After Card Trade");	
		
		
		
	}

	public void displayCards()
	{
		card_controller.loadCards();
	}
	
	
	
	
}
