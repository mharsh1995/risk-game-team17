package com.risk.team.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import com.risk.team.controller.RiskDiceController;
import com.risk.team.controller.RiskGraphConnected;
import com.risk.team.controller.RiskMapRW;
import com.risk.team.controller.gamephase.Fortification;
import com.risk.team.controller.gamephase.Reinforcement;
import com.risk.team.observer.Subject;

/**
 * Player class for information regarding the player
 * 
 * @author Kartika Patil
 * @author yashgolwala
 * @version 2.0.0
 */
public class Player {

	/**
	 * Player currently playing.
	 */
	public Player currentPlayer;

	/** Player name */
	private String name;

	/**Initialization of army count */
	private int armyCount=0;

	/** List of countries possessed by the Player */
	private ArrayList<Country> myCountries;

	/** List of cards that Player holds*/
	private ArrayList<Card> listOfCards;

	/** No of cards */
	private int cardSetCount = 0;

	/** Flag to set all out mode */
	public boolean allOutMode = false;



	/** Player constructor */
	public Player() {
		this.myCountries = new ArrayList<Country>();
		this.listOfCards = new ArrayList<Card>();
		currentPlayer = this;
	}

	/**
	 * Get method to get the name of the Player
	 *
	 * @return name Player name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set method for the name of the Player
	 * 
	 * @param name Player name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get method to get card count
	 *
	 * @return cardSetCount
	 */
	public int getCardSetCount() {
		cardSetCount = cardSetCount+1;
		return cardSetCount;
	}

	/**
	 * Get method to set card count
	 *  @param cardSetCount card count
	 */
	public void setCardSetCount(int cardSetCount) {
		this.cardSetCount = cardSetCount;
	}

	/**
	 * Method to get the Count of the Army that the Player owns
	 * 
	 * @return armyCount count of army
	 */
	public int getArmyCount() {
		return armyCount;
	}

	/**
	 * Method to set the count of the Army
	 * 
	 * @param armyCount Count of the Army
	 * 
	 */
	public void setArmyCount(int armyCount) {
		this.armyCount = armyCount;
	}

	/**
	 * Get the list of countries the player owns
	 * 
	 * @return myCountries list of countries
	 */
	public ArrayList<Country> getMyCountries() {
		return myCountries;
	}

	/**
	 * Method to set the country list for player
	 * 
	 * @param myCountries list of countries
	 * 				
	 */
	public void setMyCountries(ArrayList<Country> myCountries) {
		this.myCountries = myCountries;
	}

	/**
	 *  Method for adding a single country to the player's country list
	 *  
	 * @param country Object of the country
	 */
	public void addCountry(Country country){
		this.myCountries.add(country);
	}

	/**
	 * Method for getting the list of cards the player owns
	 * 
	 * @return listOfCards list of cards
	 */
	public ArrayList<Card> getListOfCards() {
		return listOfCards;
	}

	/**
	 * Method to set the list of card of one particular type
	 * 
	 * @param listOfCards Types of cards
	 */
	public void setListOfCards(ArrayList<Card> listOfCards) {
		this.listOfCards = listOfCards;
	}

	/**
	 * Method to add the armies to a country
	 * 
	 * @param country name of the country
	 * @param numberOfArmies number of armies to be added
	 */
	public void addArmiesToCountry(Country country, int numberOfArmies) {
		if(this.getArmyCount()>0 && this.getArmyCount()>=numberOfArmies) {
			if(!this.getMyCountries().contains(country)) {
				System.out.println("This country does not falls under your territory.");
			}
			else {
				country.setNoOfArmies(country.getNoOfArmies() + numberOfArmies);
				this.setArmyCount(this.getArmyCount() - numberOfArmies);
			}
		}
		else {
			System.out.println("Insufficient number of armies.");
		}
	}

	/**
	 * Methods for trading cards of the player for armies
	 *
	 * @param selectedCards List of selected cards by the player
	 * @param numberOfCardSetExchanged Number of card sets to be exchanged
	 */
	public void exchangeCards(List<Card> selectedCards, int numberOfCardSetExchanged) {
		this.setArmyCount(this.getArmyCount() + (5 * numberOfCardSetExchanged));
		System.out.println(this.getName() + " successfully exchanged 3 cards for "+ (5 * numberOfCardSetExchanged) +" armies! \n");

		for (Card card : selectedCards) {
			this.listOfCards.remove((Card) card);
		}
	}



	/**
	 * Method for performing attack
	 * @param attackingCountry Country which is attacking
	 * @param defendingCountry Country under attack
	 */
	public void attackPhase(Country attackingCountry, Country defendingCountry) {
		boolean result;
		if (attackingCountry != null && defendingCountry != null) {
			boolean playerCanAttack = isAttackMoveValid(attackingCountry, defendingCountry);
			if (playerCanAttack) {
				Player defendingPlayer = defendingCountry.getPlayer();
				int countriesCount = defendingPlayer.getMyCountries().size();
				Dice dice = new Dice(attackingCountry, defendingCountry);
				RiskDiceController diceController = new RiskDiceController(dice);
				result = diceController.start();
				if(result)
				{
					Card randomCard = new Card();
					Card obj = randomCard.getRandomCard();
					currentPlayer.listOfCards.add(randomCard.getRandomCard());
					System.out.println("Player got " + obj);
					System.out.println(currentPlayer.listOfCards.toString());
					checkExtremeCondition(defendingPlayer,countriesCount);
				}
			}
		}
	}

	/**
	 * Method to check if the attack move is valid or not
	 * @param defendingPlayer Country attacking
	 * @param countriesCount Country under attack
	 * @return true if the attack move is valid; other wise false
	 */
	public boolean checkExtremeCondition(Player defendingPlayer, int countriesCount) {
		int countriescount_afterattack = defendingPlayer.myCountries.size();
		if((countriesCount == 1) && countriescount_afterattack == 0)
		{
			currentPlayer.listOfCards.addAll(defendingPlayer.getListOfCards());
			defendingPlayer.getListOfCards().clear();
			return true;
		}
		return false;
	}

	/**
	 * Method to check if the attack move is valid or not
	 * @param attacking Country attacking
	 * @param defending Country under attack
	 * @return true if the attack move is valid; other wise false
	 */
	public boolean isAttackMoveValid(Country attacking, Country defending) {
		
		boolean isValidAttackMove = false;
		if (defending.getPlayer() != attacking.getPlayer()) {
			if (attacking.getNoOfArmies() > 1 && attacking.getAdjacentCountries().contains(defending)) {
				isValidAttackMove = true;
			} else {
				System.out.println("Select only adjacent contries as a defending contry or Invalid game move or There should be more than one army on the country which is attacking.");
			}
		} else {
			System.out.println("You have selected your own country or Invalid game move or Select another player's country to attack");
		}
		return isValidAttackMove;
	}

	/**
	 * Method to check if the player can attack or not.
	 * @param countries List view of all the countries of the player
	 * @return true if the player can attack other wise false
	 */
	public boolean playerCanAttack(List<Country> countries) {
		boolean canAttack = false;
		for (Country Country : countries) {
			if (Country.getNoOfArmies() > 1) {
				canAttack = true;
			}
		}
		if (!canAttack) {
			System.out.println("Player cannot continue with attack phase, move to fortification phase.\n");
			System.out.println("Attack phase ended\n");
			return canAttack;
		}
		return canAttack;
	}

	/**
	 * Method to check if any player lost the game after every attack move
	 * @param playersPlaying List containing all the players playing the game
	 * @return Player object of the lost player
	 */
	public Player checkPlayerLost(List<Player> playersPlaying) {
		Player playerLost = null;
		for (Player player : playersPlaying) {
			if (player.getMyCountries().isEmpty()) {
				playerLost = player;
				currentPlayer.getListOfCards().addAll(playerLost.getListOfCards());
			}
		}
		return playerLost;
	}

	/** Method to perform reinforcement*/
	public void reinforcement() {
		currentPlayer = this;

		Continent continent = currentPlayer.getMyCountries().get(currentPlayer.getMyCountries().size()-1).getPartOfContinent();
		int balanceArmyCount = assignArmies(currentPlayer, continent);	
		System.out.println("Armies received for reinforcement for player: " + balanceArmyCount);
		currentPlayer.setArmyCount(currentPlayer.getArmyCount() + balanceArmyCount);

	}

	/** Method for assigning armies during reinforcement phase
	 * @param player Player object
	 * @param continent Continent object
	 * @return noOfArmies Number of armies assigned
	 */
	public int assignArmies(Player player, Continent continent) {
		boolean hasPlayerAllContinents = true;

		int playerOwnedArmy = (int) Math.floor(player.getMyCountries().size()/ 3);
		int noOfArmies = (int) playerOwnedArmy;

		ArrayList<Country> playerOwnedCountries = player.getMyCountries();
		ArrayList<Country> continentCountryList = continent.getListOfCountries();

		// Minimum number of armies for a player in case armies count is less than 3.
		
		if (noOfArmies < 3) {
			noOfArmies = 3;
		}

		for (Country country : continentCountryList) {
			if (!playerOwnedCountries.contains(country)) {
				hasPlayerAllContinents = false;
				break;
			}
		}

		// If a player owns all the countries in a continent, then armies count will be equal to the control value of the continent.
		if (hasPlayerAllContinents) {
			noOfArmies = continent.getControlValue();
		}

		return noOfArmies;
	}

	/** Method to perform fortification
	 * @param mapObj object for RiskMapRW class
	 * @return countryList list containing source country and destination country
	 */
	public ArrayList<Country> fortification(RiskMapRW mapObj) {
		ArrayList<Country> countryList = new ArrayList<Country>() ;
		currentPlayer = this;
		Scanner scan = new Scanner(System.in);
		if(currentPlayer.getMyCountries().size()>=2) {
			boolean flag = true;
			String giverCountry = "";
			String receiverCountry = "";
			System.out.println("List of countries owned by you and current army assigned are: ");
			for(Country ownedCountry: currentPlayer.getMyCountries()) {
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
				if(currentPlayer.getMyCountries().contains(givingCountry) && currentPlayer.getMyCountries().contains(receviningCountry)){
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
			}
			while(flag==false);
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
			moveArmies(mapObj.getMapGraph().getAllCountries().get(giverCountry), (mapObj.getMapGraph().getAllCountries().get(receiverCountry)), countOfArmies,currentPlayer,mapObj.getMapGraph().getAllCountries().values());
			countryList.add(mapObj.getMapGraph().getAllCountries().get(giverCountry));
			countryList.add(mapObj.getMapGraph().getAllCountries().get(receiverCountry));
		}
		else {
			System.out.println("You don't have sufficient number of countries to choose from.");
		}
		return countryList;
	}

	/** Method to move armies during fortification phase
	 *@param fromCountry source country from which armies are to be moved
	 *@param toCountry destination country to which armies are moved
	 *@param noOfArmies No of armies to be moved
	 *@param player Player who does fortification.
	 *@param allCountriescollection collection of all countries in the Map Graph
	 *@return pathFlag Flag to indicate if path exists between both source and destination country
	 */
	public boolean moveArmies(Country fromCountry, Country toCountry, int noOfArmies,Player player,Collection<Country> allCountriescollection) {

		boolean pathFlag = false;

		Set<Country> allCountries = new HashSet<Country>(allCountriescollection);

		Set<Country> playerOwnedCountries = new HashSet<Country>(player.getMyCountries());

		RiskGraphConnected rc = new RiskGraphConnected(allCountries);

		pathFlag = rc.ifPathExists(fromCountry,toCountry,playerOwnedCountries);		

		//if the flag is true then move armies from fromCountry to toCountry
		if(pathFlag) {
			fromCountry.setNoOfArmies(fromCountry.getNoOfArmies() - noOfArmies);
			toCountry.setNoOfArmies(toCountry.getNoOfArmies() + noOfArmies);
			for(Country country : playerOwnedCountries)
			{
				System.out.println("Number of armies assigned to country after fortification " + country.getName() + " :" + country.getNoOfArmies());
			}

		}
		else
		{				
			System.out.println("Source and Destination Country does not have adjacent path through countries owned by Player");

		}

		return pathFlag;

	}


}