package com.risk.team.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.risk.team.model.Dice;
import com.risk.team.model.Player;
import com.risk.team.controller.RiskDiceController;
import com.risk.team.controller.RiskCardController;
import com.risk.team.controller.RiskLaunchPhase;
import com.risk.team.controller.RiskMapEdit;
import com.risk.team.controller.RiskMapRW;
import com.risk.team.controller.RiskMapVerify;
import com.risk.team.controller.gamephase.Fortification;
import com.risk.team.controller.gamephase.Reinforcement;
import com.risk.team.controller.gamephase.RiskRoundRobin;
import com.risk.team.model.Card;
import com.risk.team.model.Continent;
import com.risk.team.model.Country;

import com.risk.team.observer.Subject;
import com.risk.team.observer.Observer;
import java.util.ArrayList;


/**
 * Launches the Game and provides the main window and the view for the user,
 * to load, Create and Edit a map.
 *
 * @author Harsh Mehta
 * @author Yash Golwala
 * @author Jenny
 */

public class GamePhaseView extends Observer {
	
		protected Subject observerSubject;
		
		public GamePhaseView(Subject observerSubject) {
			this.observerSubject = observerSubject;
			this.observerSubject.attach(this);
	}

	public void gamePlay(RiskMapRW mapObj) {
		Scanner scan = new Scanner(System.in);	
		System.out.println("\n**************GAME IS ON*****************\n");
		System.out.println("\nBeginning Startup Phase.\n");
		RiskLaunchPhase RiskLaunchPhase = new RiskLaunchPhase(mapObj);
		RiskLaunchPhase.getPlayerDetails();
		RiskLaunchPhase.allocateCountries();
		RiskLaunchPhase.armyAllocateToPlayer();
		RiskLaunchPhase.initialArmyToCountries();
		RiskLaunchPhase.balanceArmyToCountries();
		
		int turn = 1;
		RiskRoundRobin roundRobin = new RiskRoundRobin(RiskLaunchPhase.getPlayerList());

		
		while(turn <= (RiskLaunchPhase).getPlayerList().size()) {
			Player player = roundRobin.nextPlayer();
		
			
			Card card = new Card();
			RiskCardController riskcardconroller = new RiskCardController(player,card);
			
	/*		Card c1 = new Card("infantry");
			Card c2 = new Card("infantry");
			Card c3 = new Card("artillery");
			Card c4 = new Card("cavalry");
			Card c5 = new Card("infantry");
			
			player.getListOfCards().add(c1);
			player.getListOfCards().add(c2);
			player.getListOfCards().add(c3);
			player.getListOfCards().add(c4);
			player.getListOfCards().add(c5);*/
			
			observerSubject.setReinforcementMsg("************Beginning Reinforcement Phase************");
			observerSubject.setCurrentPlayerDetails(player.getName());
							
			System.out.println("Do you want to continue with Reinforcement phase? (Select : Yes or No)");
			if(scan.nextLine().trim().equalsIgnoreCase("Yes")) {
				
				ArrayList<Integer> tradeArmyCount = new ArrayList<Integer>();
				tradeArmyCount.add(player.getArmyCount());
				CardExchangeView cardView = new CardExchangeView(riskcardconroller);
				tradeArmyCount.add(player.getArmyCount());
				observerSubject.setTradeArmyCount(tradeArmyCount);
        		player.reinforcement();
			
			for(Country country: player.getMyCountries()) {
			if(player.getArmyCount()>0) {
				observerSubject.setActionDetails(player,country);
				System.out.println("Enter number of armies to assign to country " + country.getName() + " :");
				try {
					Scanner sc = new Scanner(System.in);
					int assignArmies = Integer.parseInt(sc.nextLine());
					player.addArmiesToCountry(country, assignArmies);
					}
				catch(NumberFormatException e) {
					System.out.println("Invalid number of armies.");
				}
			}
			else {
				System.out.println("Now !! You do not have sufficient number of armies available.");
				break;
				}
			  }
			}
			
			observerSubject.setAttackMsg("************Beginning Attack Phase************");
			observerSubject.setCurrentPlayerDetails(player.getName());
			
			System.out.println("Do you want to continue with Attack phase? (Select : Yes or No)");
			if(scan.nextLine().trim().equalsIgnoreCase("Yes")) {
							
				System.out.println("List of countries owned by you and current army assigned are: ");
				for(Country ownedCountry: player.getMyCountries()) {
					System.out.println(ownedCountry.getName() + ":" + ownedCountry.getNoOfArmies());
				}
				
				System.out.println("List of all countries where you can attack and army assigned are: ");
				for(Country country : player.getMyCountries()) {
					
					System.out.println(country + " : Adjecent Countries");
					for(Country country2 :country.getAdjacentCountries())
					{
						if(!player.getMyCountries().contains(country2))
						{
								System.out.print(country2.getName() + ":" + country2.getNoOfArmies() );
						}
						System.out.println();
					}
				}
				
			boolean wishToAttack = true;
			
			while(player.playerCanAttack(player.getMyCountries()) && wishToAttack )
				{
					System.out.println("Do you still wish to continue attack (yes/no)");
					if(scan.nextLine().trim().equalsIgnoreCase("yes")) {
					
					System.out.println("Enter the name of country from you wish to attck");
					String AttackCountry = scan.nextLine();
					System.out.println("Enter the name of country to which you want to attck from " + AttackCountry);
					String defendCountry = scan.nextLine();
					
					
					System.out.println("Do you want to Play ALL-OUT mode :");
					
					boolean allOutMode = scan.nextLine().trim().equalsIgnoreCase("yes");
					
					if(allOutMode)
					{
						player.allOutMode = true;
					}
					
					Country AttackCountryObj = (mapObj.getMapGraph().getAllCountries().get(AttackCountry.trim()));
					Country defendCountryObj = (mapObj.getMapGraph().getAllCountries().get(defendCountry.trim()));
					
					player.attackPhase(AttackCountryObj, defendCountryObj);						
					observerSubject.setAttackDetails(AttackCountryObj, defendCountryObj);
					player.allOutMode = false;
					
					}
					else
					{
						wishToAttack = false;
					}			
				
			}
				
			}
  
			observerSubject.setFortificationMsg("************Beginning Fortification Phase************");
			observerSubject.setCurrentPlayerDetails(player.getName());
			
			System.out.println("Do you want to continue with Fortification phase? (Select : Yes or No)");
			if(scan.nextLine().trim().equalsIgnoreCase("Yes")) {
				ArrayList<Country> countryList = new ArrayList<Country>();
				countryList = player.fortification(mapObj);
				
				if(!(countryList == null))
				{
				Country sourceCountry = countryList.get(0);
				Country destiationCountry = countryList.get(1);
				observerSubject.setFortificationDetails(sourceCountry,destiationCountry);
				}
				
			}
			
	
			PlayerWorldDomination playerdomination = new PlayerWorldDomination(observerSubject);
			playerdomination.getDominationDetails(mapObj);
			
			turn++;
		
			if(turn > (RiskLaunchPhase).getPlayerList().size())
			{
				turn=1;
			}
		}
		System.out.println("\n****************GAME-OVER*****************\n");
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fortificationUpdate() {
		System.out.println(observerSubject.getFortificationMsg());	
	}

	@Override
	public void attackUpdate() {
		System.out.println(observerSubject.getAttackMsg());
	}

	@Override
	public void tradeInCardUpdate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void botTradeInCardUpdate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reinforcementUpdate() {
		System.out.println(observerSubject.getReinforcementMsg());
	}

	@Override
	public void playerLogUpdate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void currentPlayerUpdate() {
		System.out.println("Current Player: " + observerSubject.getCurrentPlayerDetails());		
	}

	@Override
	public void actionsUpdate() {
		System.out.println("Number of armies currently assigned to country " + observerSubject.getCountryObj().getName() + " :" + observerSubject.getCountryObj().getNoOfArmies());
		System.out.println("Total number of armies available :" + observerSubject.getPlayerObj().getArmyCount());		
	}

	@Override
	public void armyCountUpdate() {
		System.out.println("Armies assigned to " + observerSubject.getCountryObj().getName() + ":- "+ observerSubject.getCountryObj().getNoOfArmies());
	}

	@Override
	public void attackActionUpdate() {
		System.out.println("******After Attackr Details********");	
		System.out.println("Country: " + observerSubject.getAttackCountryObj().getName());
		System.out.println("No of Armies: "+ observerSubject.getAttackCountryObj().getNoOfArmies());
		System.out.println("******After Attack Details********");	
		System.out.println("Country: " + observerSubject.getDefendCountryObj().getName());
		System.out.println("No of Armies: "+ observerSubject.getDefendCountryObj().getNoOfArmies());
	}

	@Override
	public void fortificationActionUpdate() {
		System.out.println("*****************************************************");
		System.out.println("Armies owned by Source Country " + observerSubject.getSourceCountry().getName() + ":- " + observerSubject.getSourceCountry().getNoOfArmies());
		System.out.println("Armies owned by Destination Country " + observerSubject.getDestinationCountry().getName() + ":- " + observerSubject.getDestinationCountry().getNoOfArmies());
	}
	@Override
	public void tradeArmyUpdate() {
		System.out.println("Army Count Before Card Trade: " + observerSubject.getTradeArmyCount().get(0));
		System.out.println("Army Count After Card Trade: " + observerSubject.getTradeArmyCount().get(1));
	}

	@Override
	public void playerOwnedArmyCountUpdate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void playerOwnedArmyContinentUpdate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void playerTerPercentUpdate() {
		// TODO Auto-generated method stub
		
	}

}