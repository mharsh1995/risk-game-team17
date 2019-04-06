package com.risk.team.model;

import com.risk.team.controller.GamePhaseController;
import com.risk.team.services.RiskMapRW;
import com.risk.team.services.Util.GameWindowUtil;
import com.risk.team.strategy.*;
import com.risk.team.strategy.Random;

import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

import java.io.Serializable;
import java.util.*;

/**
 * Player class for information regarding the player
 *
 * @author Kartika Patil
 * @author yashgolwala
 *
 */
public class Player extends Observable implements Observer, Serializable {

	/**
	 * Player currently playing.
	 */

	public static Player currentPlayer;

	/**
	 * Name of the player
	 */
	private String name;

	/**
	 * Initialization of army count
	 */
	private int armyCount=0;

	/**
	 * List of countries possessed by the Player
	 */
	private ArrayList<Country> myCountries;

	/**
	 * List of cards that Player holds
	 */
	private ArrayList<Card> listOfCards;

	/**
	 * Type of Player
	 */
	private String playerType;

	/**
	 * PlayerBehaviour
	 */
	private PlayerBehaviour playerBehaviour;

	/**
	 * Number of countries won by the player
	 */
	private int CountryWon;
	
	public Collection<Country> mapCountries;
	
	private int numberOfCardSetExchanged = 0;

	/**
	 * Player constructor, initialization of army count
	 */
	public Player() {
		armyCount = 0;
	}

	/**
	 * Constructor for player class
	 *
	 * @param name player name
	 */
	public Player(String name) {
		this.name = name;
		this.listOfCards = new ArrayList<>();
	}

	/**
	 * Player constructor
	 *
	 * @param name name
	 * @param playerType player type as string
	 * @param gamePhaseController GamePhaseController object
	 */
	public Player(String name, String playerType, GamePhaseController gamePhaseController) {
		armyCount = 0;
		this.name = name;
		this.playerType = playerType;
		this.myCountries = new ArrayList<>();
		this.mapCountries = gamePhaseController.map.getMapGraph().getAllCountries().values();
		this.listOfCards = new ArrayList<>();
		if (playerType.equals(PlayerMode.AGGRESSIVE))
			this.playerBehaviour = new Aggressive(gamePhaseController);
		else if (playerType.equals(PlayerMode.BENEVOLENT))
			this.playerBehaviour = new Benevolent(gamePhaseController);
		else if (playerType.equals(PlayerMode.CHEATER))
			this.playerBehaviour = new Cheater(gamePhaseController);
		else if (playerType.equals(PlayerMode.HUMAN))
			this.playerBehaviour = new Human(gamePhaseController);
		else if (playerType.equals(PlayerMode.RANDOM))
			this.playerBehaviour = new Random(gamePhaseController);
		this.addObserver(gamePhaseController);
	}

	/**
	 * Constructor for player
	 *
	 * @param name name
	 * @param playerType playerType
	 */
	public Player(String name, String playerType) {
		armyCount = 0;
		this.name = name;
		this.playerType = playerType;
		this.myCountries = new ArrayList<>();
		this.listOfCards = new ArrayList<>();
		if (playerType.equals(PlayerMode.AGGRESSIVE))
			this.playerBehaviour = new Aggressive();
		else if (playerType.equals(PlayerMode.BENEVOLENT))
			this.playerBehaviour = new Benevolent();
		else if (playerType.equals(PlayerMode.CHEATER))
			this.playerBehaviour = new Cheater();
		else if (playerType.equals(PlayerMode.RANDOM))
			this.playerBehaviour = new Random();
	}

	/**
	 * Method to get name of the player
	 *
	 * @return name player's name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setter for player's name
	 *
	 * @param name name of the player
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Getter for player armies
	 *
	 * @return armyCount armyCount of the player
	 */
	public int getArmyCount() {
		return armyCount;
	}

	/**
	 * Setter for players army count
	 *
	 * @param armyCount armyCount of the player
	 */
	public void setArmyCount(int armyCount) {
		this.armyCount = armyCount;
	}

	/**
	 * Getter for list of player's armies.
	 *
	 * @return myCountries List of myCountries
	 */
	public ArrayList<Country> getMyCountries() {
		return myCountries;
	}

	/**
	 * Setter for list of player's armies.
	 *
	 * @param playerCountries myCountries set player armies
	 */
	public void setMyCountries(ArrayList<Country> playerCountries) {
		this.myCountries = playerCountries;
	}

	/**
	 * Method to add a country to the player's country list
	 *
	 * @param country country object
	 */
	public void addCountry(Country country) {
		this.myCountries.add(country);
	}

	/**
	 * Method for getting the player's list of card
	 *
	 * @return listOfCards listOfCards of player
	 */
	public ArrayList<Card> getListOfCards() {
		return listOfCards;
	}

	/**
	 * Method for returning the player's list of card
	 *
	 * @param cardList listOfCards of player
	 */
	public void setListOfcards(ArrayList<Card> cardList) {
		this.listOfCards = cardList;
	}

	/**
	 * Method for getting playerType
	 *
	 * @return playerType type of player
	 */
	public String getPlayerType() {
		return playerType;
	}

	/**
	 * Method for setting player behaviour
	 *
	 * @param playerBehaviour behaviour of player
	 */
	public void setPlayerBehaviour(PlayerBehaviour playerBehaviour) {
		this.playerBehaviour = playerBehaviour;
	}

	/**
	 * Method to get player behaviour
	 *
	 * @return playerBehaviour behaviour of player
	 */
	public PlayerBehaviour getPlayerBehaviour() {
		return playerBehaviour;
	}

	
	public int getNumberOfCardSetExchanged() {
		this.numberOfCardSetExchanged = numberOfCardSetExchanged + 1;
		return numberOfCardSetExchanged;
	}

	public void setNumberOfCardSetExchanged(int numberOfCardSetExchanged) {
		this.numberOfCardSetExchanged = numberOfCardSetExchanged;
	}
	
	/**
	 * Method for adding armies to a country
	 *
	 * @param country Country to which armies are to be assigned
	 * @param numberOfArmies number for armies to be assigned
	 */
	public void addArmiesToCountry(Country country, int numberOfArmies) {
		if (this.getArmyCount() > 0 && this.getArmyCount() >= numberOfArmies) {
			if (!this.getMyCountries().contains(country)) {
				System.out.println("This country is not under your Ownership.");
			} else {
				country.setNoOfArmies(country.getNoOfArmies() + numberOfArmies);
				this.setArmyCount(this.getArmyCount() - numberOfArmies);
			}
		} else {
			System.out.println("Sufficient number of armies not available.");
		}
	}

	/**
	 * Getter for current PLayer
	 *
	 * @return currentPlayer current Player
	 */

	public static Player getPlayerPlaying() {
		return Player.currentPlayer;
	}

	/**
	 * Method for allocating initial armies to the player,
	 * depending upon the total number of players
	 *
	 * @param players List of all the players
	 * @return isSuccessfulAssignment true, is armies are successfully assigned,; otherwise false
	 */
	public boolean assignArmiesToPlayers(List<Player> players) {
		boolean isSuccessfulAssignment = false;
		int armiesPerPlayer = 0;

		if (players.size()== 2) {
			armiesPerPlayer = 40;
		}
		else if (players.size() == 3) {
			armiesPerPlayer = 35;
		} else if (players.size() == 4) {
			armiesPerPlayer = 30;
		} else if (players.size() == 5) {
			armiesPerPlayer = 25;
		} else if (players.size() == 6) {
			armiesPerPlayer = 20;
		}

		for (int playerNumber = 0; playerNumber < players.size(); playerNumber++) {
			players.get(playerNumber).setArmyCount(armiesPerPlayer);
			System.out.println(armiesPerPlayer + " armies assigned to " + players.get(playerNumber).getName() + ".\n");
			setChanged();
			notifyObservers(armiesPerPlayer + " armies assigned to " + players.get(playerNumber).getName() + ".\n");
			isSuccessfulAssignment = true;
		}

		return isSuccessfulAssignment;
	}

	/**
	 * Method for generating players according to the data entered by the user
	 *
	 * @param hm Map of all the player details
	 * @param gamePhaseController GamePlayController object as observer
	 * @return listPlayer List of player objects
	 */
	public ArrayList<Player> generatePlayer(HashMap<String, String> hm, GamePhaseController gamePhaseController) {

		ArrayList<Player> listPlayer = new ArrayList<>();
		for (Map.Entry<String, String> playerEntry : hm.entrySet()) {
			listPlayer.add(new Player(playerEntry.getKey().trim(), playerEntry.getValue(), gamePhaseController));
			System.out.println("Created player " + playerEntry.getKey().trim() + ".\n");
			setChanged();
			notifyObservers("Created player " + playerEntry.getKey().trim() + ".\n");
		}
		return listPlayer;
	}

	/**
	 * Method for calculating number of reinforcement armies to be allocated to the player
	 *
	 * @param currentPlayer Player to which armies are to be allocated
	 * @return currentPlayer Player, object of the current player
	 */
	public Player noOfReinforcementArmies(Player currentPlayer) {
		currentPlayer.setArmyCount(currentPlayer.getArmyCount() + currentPlayer.findNoOfArmies(currentPlayer));
		System.out.println("Total number of armies available to player " + currentPlayer.getName() + ": " + currentPlayer.getArmyCount() + "\n");
		setChanged();
		notifyObservers("Total number of armies available to player " + currentPlayer.getName() + ": " + currentPlayer.getArmyCount() + "\n");
		return currentPlayer;
	}

	/**
	 * Method to calculate no of armies
	 *
	 * @param player Player object
	 * @return numberOfArmies
	 */
	public int findNoOfArmies(Player player) {
		int noOfCountries = player.getMyCountries().size();
		int numberOfArmies = (int) Math.floor(noOfCountries / 3);
		HashSet<Continent> countryInContinent = new HashSet<>();
		ArrayList<Country> playerOwnedCountries = player.getMyCountries();

		boolean isPlayerOwnedContinent;

		for (Country country : playerOwnedCountries) {
			countryInContinent.add(country.getPartOfContinent());
		}

		for (Continent continent : countryInContinent) {
			isPlayerOwnedContinent = true;
			for (Country country : continent.getListOfCountries()) {
				if (!playerOwnedCountries.contains(country)) {
					isPlayerOwnedContinent = false;
					break;
				}
			}
			if (isPlayerOwnedContinent) {
				numberOfArmies += continent.getControlValue();
			}
		}

		if (numberOfArmies < 3) {
			numberOfArmies = 3;
		}
		System.out.println("Player " + player.getName() + " has been assigned " + numberOfArmies + " armies.\n");
		setChanged();
		notifyObservers("Player " + player.getName() + " has been assigned " + numberOfArmies + " armies.\n");

		return numberOfArmies;
	}

	/**
	 * Method governing the reinforcement phase.
	 *
	 * @param countries countries Observable List
	 * @param country country to which reinforcement armies are to be assigned
	 * @param playerList list of players
	 */
	public void reinforcementPhase(ObservableList<Country> countries, Country country, List<Player> playerList) {
		currentPlayer.getPlayerBehaviour().reinforcementPhase(countries, country, currentPlayer);
		if (currentPlayer.getArmyCount() <= 0 && playerList.size() > 1) {
			System.out.println("Reinforcement Phase Ended\n");
			setChanged();
			notifyObservers("Reinforcement Phase Ended\n");
			setChanged();
			notifyObservers("Attack");
		}
	}

	/**
	 * Method governing the attack phase
	 *
	 * @param attackingCountries attacking country list
	 * @param defendingCountries defending country list
	 * @param playerList list of players
	 */
	public void attackPhase(ListView<Country> attackingCountries, ListView<Country> defendingCountries,
			List<Player> playerList) {
		currentPlayer.getPlayerBehaviour().attackPhase(attackingCountries, defendingCountries, currentPlayer);
		if (!(currentPlayer.getPlayerBehaviour() instanceof Human) && playerList.size() > 1) {
			System.out.println(currentPlayer.getName() + " player with " + currentPlayer.getPlayerType() +
					" strategy is going to call skipAttack after doing attack.\n");
			setChanged();
			notifyObservers(currentPlayer.getName() + " player with " + currentPlayer.getPlayerType() +
					" strategy is going to call skipAttack after doing attack.\n");
			setChanged();
			notifyObservers("skipAttack");
		}
	}

	/**
	 * Method governing the fortification phase
	 *
	 * @param selectedCountries selected countries list
	 * @param adjacentCountries adjacent countries list
	 * @param listOfPlayer list of players
	 */
	public void fortificationPhase(ListView<Country> selectedCountries, ListView<Country> adjacentCountries,
			List<Player> listOfPlayer) {
		boolean success = currentPlayer.getPlayerBehaviour().fortificationPhase(selectedCountries, adjacentCountries, currentPlayer );
		if (success && listOfPlayer.size() > 1) {
			System.out.println("Fortification phase ended. \n");
			setChanged();
			notifyObservers("Fortification phase ended. \n");
			setChanged();
			notifyObservers("Reinforcement");
		}
	}

	/**
	 * Method to check if the fortification move taking place in fortification is valid or not
	 *
	 * @param riskMapRW RiskMapRW object
	 * @param currentPlayer current player
	 * @return true if the move is valid; otherwise false
	 */
	public boolean isFortificationPhaseValid(RiskMapRW riskMapRW, Player currentPlayer) {
		boolean flag = currentPlayer.getPlayerBehaviour().isFortificationPhaseValid(riskMapRW, currentPlayer);
		if (flag) {
			setChanged();
			notifyObservers("Fortification");
		} else {
			setChanged();
			notifyObservers("noFortificationMove");
		}
		return flag;
	}

	/**
	 * Method for placing armies on the countries during the startup phase.
	 *
	 * @param selectedCountryList List view for the countries of the current player.
	 * @param gamePlayerList List of all the players of playing the game.
	 */
	public void placeArmyOnCountry(ListView<Country> selectedCountryList, List<Player> gamePlayerList) {
		if (this.getPlayerBehaviour() instanceof Human) {
			int playerArmies = currentPlayer.getArmyCount();
			if (playerArmies > 0) {
				Country Country = selectedCountryList.getSelectionModel().getSelectedItem();
				if (Country == null) {
					Country = selectedCountryList.getItems().get(0);
				}
				Country.setNoOfArmies(Country.getNoOfArmies() + 1);
				this.setArmyCount(playerArmies - 1);
			}
		} else {
			automaticAssignPlayerArmiesToCountry(this);
		}

		boolean armiesExhausted = isPlayerArmyLeft(gamePlayerList);
		if (armiesExhausted) {
			System.out.println("StartUp Phase Completed.\n");
			setChanged();
			notifyObservers("StartUp Phase Completed.\n");
			setChanged();
			notifyObservers("FirstAttack");
		} else {
			setChanged();
			notifyObservers("placeArmyOnCountry");
		}
	}

	/**
	 * Method for assigning player armies to country
	 *
	 * @param currentPlayer name of current Player
	 */
	public void automaticAssignPlayerArmiesToCountry(Player currentPlayer) {
		if (currentPlayer.getArmyCount() > 0) {
			Country country = currentPlayer.getMyCountries()
					.get(new java.util.Random().nextInt(currentPlayer.getMyCountries().size()));
			country.setNoOfArmies(country.getNoOfArmies() + 1);
			currentPlayer.setArmyCount(currentPlayer.getArmyCount() - 1);
			System.out.println("Player " + currentPlayer.getName() + " , Country " + country.getName() + " has been assigned one army.");
			setChanged();
			notifyObservers("Country " + country.getName() + " has been assigned one army.\n");
		}
	}

	/**
	 * Method to check if the player has armies or not.
	 *
	 * @param allPlayers List of all the players of playing the game.
	 * @return true if player has armies left; otherwise false.
	 */
	public boolean isPlayerArmyLeft(List<Player> allPlayers) {
		int count = 0;

		for (Player player : allPlayers) {
			if (player.getArmyCount() == 0) {
				count++;
			}
		}
		if (count == allPlayers.size()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Method to check if the player can attack or not.
	 *
	 * @param attackingCountries List view of all the countries of the player
	 * @return canAttack true if the player can attack; other wise false
	 */
	public boolean playerCanAttack(ListView<Country> attackingCountries) {
		boolean canAttack = currentPlayer.getPlayerBehaviour().playerCanAttack(attackingCountries);
		if (!canAttack) {
			setChanged();
			notifyObservers("checkIfFortificationPhaseValid");
		}
		return canAttack;
	}

	/**
	 * Method to check if the attack move is valid or not
	 *
	 * @param attacking Country attacking
	 * @param defending Country under attack
	 * @return isValidAttackMove true if the attack move is valid; other wise false
	 */

	public boolean isAttackMoveValid(Country attacking, Country defending) {
		boolean isValidAttackMove = false;
		if (defending.getPlayer() != attacking.getPlayer()) {
			if (attacking.getNoOfArmies() > 1) {
				isValidAttackMove = true;
			} else {
				GameWindowUtil.popUpWindow("Select a country with more armies.", "Invalid game move", "There should be more than one army on the country which is attacking.");
			}
		} else {
			GameWindowUtil.popUpWindow("You have selected your own country", "Invalid game move", "Select another player's country to attack");
		}
		return isValidAttackMove;
	}

	/**
	 * Method to check if any player lost the game after every attack move
	 *
	 * @param playersPlaying List containing all the players playing the game
	 * @return playersLost Player object of the lost player
	 */
	public List<Player> checkPlayerLost(List<Player> playersPlaying) {
		List<Player> playersLost = new ArrayList<>();
		for (Player player : playersPlaying) {
			if (player.getMyCountries().isEmpty()) {
				currentPlayer.getListOfCards().addAll(player.getListOfCards());
				playersLost.add(player);
			}
		}
		return playersLost;
	}

	/**
	 * Method to check if any player Won the game after every attack move
	 *
	 * @param playersPlaying List containing all the players playing the game
	 * @return playerWon Player object of the winning player
	 */
	public Player checkPlayerWon(List<Player> playersPlaying) {
		Player playerWon = null;
		if (playersPlaying.size() == 1) {
			playerWon = playersPlaying.get(0);
		}
		return playerWon;
	}

	/**
	 * Methods for exchanging cards of the player for armies
	 *
	 * @param selectedCards List of selected cards by the player
	 * @param numberOfCardSetExchanged Number of card sets to be exchanged
	 * @return currentPlayer Player object exchanging the cards
	 */
	public Player exchangeCards(List<Card> selectedCards, int numberOfCardSetExchanged) {

		currentPlayer.setArmyCount(currentPlayer.getArmyCount() + (5 * numberOfCardSetExchanged));
		System.out.println(currentPlayer.getName() + " successfully exchanged 3 cards for " + (5 * numberOfCardSetExchanged) + " armies.\n");
		setChanged();
		notifyObservers(currentPlayer.getName() + " successfully exchanged 3 cards for " + (5 * numberOfCardSetExchanged) + " armies.\n");

		for (Card card : selectedCards) {
			if (currentPlayer.getMyCountries().contains(card.getCountry())) {
				card.getCountry().setNoOfArmies(card.getCountry().getNoOfArmies() + 2);
				System.out.println(currentPlayer.getName() + " \" got extra 2 armies on \" " + card.getCountry().getName() + "\n");
				setChanged();
				notifyObservers(currentPlayer.getName() + " \" got extra 2 armies on \" " + card.getCountry().getName() + "\n");
				break;
			}
		}
		return currentPlayer;
	}

	/**
	 * Setter for setting the current player
	 *
	 * @param currentPlayer current player
	 */
	public static void setPlayerPlaying(Player currentPlayer) {
		Player.currentPlayer = currentPlayer;
	}

	/**
	 * Getter for number of countries won by the player
	 *
	 * @return CountryWon countryWonCount
	 */

	public int getCountryWon() {
		return CountryWon;
	}

	/**
	 * Setter for countries won by the player
	 *
	 * @param CountryWon number of countries won by the player
	 */
	public void setCountryWon(int CountryWon) {
		this.CountryWon = CountryWon;
	}

	/**
	 * update method for PLayer object
	 *
	 * @param o Observable
	 * @param arg String which is passed t the player object
	 */
	public void update(Observable o, Object arg) {
		String view = (String) arg;
		if (view.equals("rollDiceComplete")) {
			Dice dice = (Dice) o;
			setCountryWon(dice.getCountriesWonCount());
		}
		setChanged();
		notifyObservers(view);
	}

	
}
