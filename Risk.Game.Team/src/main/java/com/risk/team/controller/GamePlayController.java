package com.risk.team.controller;

import java.util.Scanner;

import com.risk.team.controller.gamephase.Fortification;
import com.risk.team.controller.gamephase.Reinforcement;
import com.risk.team.controller.gamephase.RiskRoundRobin;
import com.risk.team.model.Continent;
import com.risk.team.model.Country;
import com.risk.team.model.Player;
/**
 * This class implements the actual game play functions
 * such as Reinforcement, Fortification, Attack, etc.
 * 
 * @author Jenny Pujara
 * @author Yash Golwala
 * @author Kartika Patil
 * @author Dhaval Desai
 * @author Harsh Mehta
 */
public class GamePlayController {

/*	public void init() {
		
	}
	public void reinforcementPhase() {
			Continent continent = player.getMyCountries().get(player.getMyCountries().size()-1).getPartOfContinent();
			int balanceArmyCount = (new Reinforcement()).assignArmies(player, continent);
			System.out.println("Armies received for reinforcement for player: " + balanceArmyCount);
			player.setArmyCount(player.getArmyCount() + balanceArmyCount);
			for(Country country: player.getMyCountries()) {
				if(player.getArmyCount()>0) {
					System.out.println("Number of armies currently assigned to country " + country.getName() + " :" + country.getNoOfArmies());
					System.out.println("Total number of armies available:" + player.getArmyCount());
					System.out.println("Enter number of armies to assign to country " + country.getName() + " :");
					try {
						int assignArmies = Integer.parseInt(scan.nextLine());
						player.addArmiesToCountry(country, assignArmies);
					}catch(NumberFormatException e) {
						System.out.println("Invalid number of armies.");
					}
				}
				else {
					System.out.println("Now !! You do not have sufficient number of armies available.");
					break;
				}
			}
		}


	public void attackPhase() {
			System.out.println("List of countries owned by you and current army assigned are: ");
			for(Country ownedCountry: player.getMyCountries()) {
				System.out.println(ownedCountry.getName() + ":" + ownedCountry.getNoOfArmies());
			}
			
			System.out.println("List of all countries owned by Game in Map Graph: ");
			for(String country: mapObj.getMapGraph().getAllCountries().keySet()) {
				System.out.println(country);
			}
			
			System.out.println("Enter the name of country from you wish to attck");
			String AttackCountry = scan.nextLine();
			System.out.println("Enter the name of country to which you want to attck from " + AttackCountry);
			String defendCountry = scan.nextLine();
			
			Country AttackCountryObj = (mapObj.getMapGraph().getAllCountries().get(AttackCountry.trim()));
			Country defendCountryObj = (mapObj.getMapGraph().getAllCountries().get(defendCountry.trim()));
			
			//Dice dice = new Dice(AttackCountryObj, defendCountryObj);
			//DiceController diceController = new DiceController(dice);
		
	}


	public void fortificationPhase() {
			if(player.getMyCountries().size()>=2) {
				boolean flag = true;
				String giverCountry = "";
				String receiverCountry = "";

				System.out.println("List of countries owned by you and current army assigned are: ");
				for(Country ownedCountry: player.getMyCountries()) {
					System.out.println(ownedCountry.getName() + ":" + ownedCountry.getNoOfArmies());
				}
				do {
					flag=true;
					System.out.println("Enter the name of country from which you want to move some armies :");
					giverCountry = scan.nextLine();
					System.out.println("Enter the name of country to which you want to move some armies, from country " + giverCountry);
					receiverCountry = scan.nextLine();
					if(!(mapObj.getMapGraph().getAllCountries().containsKey(giverCountry.trim())) || !(mapObj.getMapGraph().getAllCountries().containsKey(receiverCountry.trim()))) {
						flag=false;
						System.out.println("Please enter correct country name.");
					}
					Country givingCountry = (mapObj.getMapGraph().getAllCountries().get(giverCountry.trim()));
					Country receviningCountry = (mapObj.getMapGraph().getAllCountries().get(receiverCountry.trim()));


					if(player.getMyCountries().contains(givingCountry) && player.getMyCountries().contains(receviningCountry)){


						if(givingCountry.equals(receviningCountry)){

							System.out.println("Source and Destination Country can not be same");
							flag = false;

						}else {

							if((mapObj.getMapGraph().getAllCountries().get(giverCountry).getNoOfArmies()) <= 1)

							{
								System.out.println("Please select a giver country having armies more than one");
								flag=false;
							}
							else
							{
								flag = true;
							}
						}

					}
					else {
						System.out.println("Player does not own these country, please enter country names again");
						flag = false;
					}

				}while(flag==false);


				int countOfArmies = 0;
				do {
					flag=true;
					System.out.println("Enter the number of armies to move from " + giverCountry + " to " + receiverCountry);
					try {
						countOfArmies = Integer.parseInt(scan.nextLine());
						if(countOfArmies >= mapObj.getMapGraph().getAllCountries().get(giverCountry).getNoOfArmies()) {
							System.out.println("Sufficient number of armies is not available.");
							flag=false;
						}


					}catch(NumberFormatException e) {
						System.out.println("Invalid number of armies.");
						flag=false;
					}
				}while(flag==false);	

				(new Fortification()).moveArmies(mapObj.getMapGraph().getAllCountries().get(giverCountry), (mapObj.getMapGraph().getAllCountries().get(receiverCountry)), countOfArmies,player,mapObj.getMapGraph().getAllCountries().values());
			
			}
		
		turn++;
	}

	*/
	
	
	/**
	 * This gamePlay method begins the startup phase and depending on the no of the turns,
	 * enters into the reinforcement phase and assigns the armies based on the input value.
	 * 
	 * @param RiskMapRW Object
	 * 
	 */
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
			
			
			System.out.println("\nBeginning Reinforcement phase for player : " + player.getName() + "\n\n");
			System.out.println("Do you want to continue with Reinforcement phase? (Select : Yes or No)");
			if(scan.nextLine().trim().equalsIgnoreCase("Yes")) {
				Continent continent = player.getMyCountries().get(player.getMyCountries().size()-1).getPartOfContinent();
				int balanceArmyCount = (new Reinforcement()).assignArmies(player, continent);
				System.out.println("Armies received for reinforcement for player: " + balanceArmyCount);
				player.setArmyCount(player.getArmyCount() + balanceArmyCount);
				for(Country country: player.getMyCountries()) {
					if(player.getArmyCount()>0) {
						System.out.println("Number of armies currently assigned to country " + country.getName() + " :" + country.getNoOfArmies());
						System.out.println("Total number of armies available:" + player.getArmyCount());
						System.out.println("Enter number of armies to assign to country " + country.getName() + " :");
						try {
							int assignArmies = Integer.parseInt(scan.nextLine());
							player.addArmiesToCountry(country, assignArmies);
						}catch(NumberFormatException e) {
							System.out.println("Invalid number of armies.");
						}
					}
					else {
						System.out.println("Now !! You do not have sufficient number of armies available.");
						break;
					}
				}
			}
	//*************************************************************************		
			System.out.println("Beginning Attack phase for player : " + player.getName() + "\n\n");
			System.out.println("Do you want to continue with Attack phase? (Select : Yes or No)");
			if(scan.nextLine().trim().equalsIgnoreCase("Yes")) {
				
				System.out.println("List of countries owned by you and current army assigned are: ");
				for(Country ownedCountry: player.getMyCountries()) {
					System.out.println(ownedCountry.getName() + ":" + ownedCountry.getNoOfArmies());
				}
				
				System.out.println("List of all countries owned by Game in Map Graph: ");
				for(String country: mapObj.getMapGraph().getAllCountries().keySet()) {
					System.out.println(country);
				}
				
				System.out.println("Enter the name of country from you wish to attck");
				String AttackCountry = scan.nextLine();
				System.out.println("Enter the name of country to which you want to attck from " + AttackCountry);
				String defendCountry = scan.nextLine();
				
				Country AttackCountryObj = (mapObj.getMapGraph().getAllCountries().get(AttackCountry.trim()));
				Country defendCountryObj = (mapObj.getMapGraph().getAllCountries().get(defendCountry.trim()));
				
				//Dice dice = new Dice(AttackCountryObj, defendCountryObj);
				//DiceController diceController = new DiceController(dice);
				
			}
   //*************************************************************************		
			System.out.println("Beginning Fortification phase for player : " + player.getName() + "\n\n");
			System.out.println("Do you want to continue with Fortification phase? (Select : Yes or No)");
			if(scan.nextLine().trim().equalsIgnoreCase("Yes")) {
				if(player.getMyCountries().size()>=2) {
					boolean flag = true;
					String giverCountry = "";
					String receiverCountry = "";

					System.out.println("List of countries owned by you and current army assigned are: ");
					for(Country ownedCountry: player.getMyCountries()) {
						System.out.println(ownedCountry.getName() + ":" + ownedCountry.getNoOfArmies());
					}
					do {
						flag=true;
						System.out.println("Enter the name of country from which you want to move some armies :");
						giverCountry = scan.nextLine();
						System.out.println("Enter the name of country to which you want to move some armies, from country " + giverCountry);
						receiverCountry = scan.nextLine();
						if(!(mapObj.getMapGraph().getAllCountries().containsKey(giverCountry.trim())) || !(mapObj.getMapGraph().getAllCountries().containsKey(receiverCountry.trim()))) {
							flag=false;
							System.out.println("Please enter correct country name.");
						}
						Country givingCountry = (mapObj.getMapGraph().getAllCountries().get(giverCountry.trim()));
						Country receviningCountry = (mapObj.getMapGraph().getAllCountries().get(receiverCountry.trim()));


						if(player.getMyCountries().contains(givingCountry) && player.getMyCountries().contains(receviningCountry)){


							if(givingCountry.equals(receviningCountry)){

								System.out.println("Source and Destination Country can not be same");
								flag = false;

							}else {

								if((mapObj.getMapGraph().getAllCountries().get(giverCountry).getNoOfArmies()) <= 1)

								{
									System.out.println("Please select a giver country having armies more than one");
									flag=false;
								}
								else
								{
									flag = true;
								}
							}

						}
						else {
							System.out.println("Player does not own these country, please enter country names again");
							flag = false;
						}

					}while(flag==false);


					int countOfArmies = 0;
					do {
						flag=true;
						System.out.println("Enter the number of armies to move from " + giverCountry + " to " + receiverCountry);
						try {
							countOfArmies = Integer.parseInt(scan.nextLine());
							if(countOfArmies >= mapObj.getMapGraph().getAllCountries().get(giverCountry).getNoOfArmies()) {
								System.out.println("Sufficient number of armies is not available.");
								flag=false;
							}


						}catch(NumberFormatException e) {
							System.out.println("Invalid number of armies.");
							flag=false;
						}
					}while(flag==false);	

					(new Fortification()).moveArmies(mapObj.getMapGraph().getAllCountries().get(giverCountry), (mapObj.getMapGraph().getAllCountries().get(receiverCountry)), countOfArmies,player,mapObj.getMapGraph().getAllCountries().values());
				}
				else {
					System.out.println("You don't have sufficient number of countries to choose from.");
				}
			}
			turn++;
		}

		System.out.println("\n****************GAME-OVER*****************\n");
	}

}	
