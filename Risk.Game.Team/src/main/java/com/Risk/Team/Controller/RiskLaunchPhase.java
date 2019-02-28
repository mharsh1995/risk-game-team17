package com.Risk.Team.Controller;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import com.Risk.Team.model.*;
import com.Risk.Team.Controller.*;

/**
 * 
 * Class for the launch phase of the game. It makes the game by
 * getting player info, and allocating countries and armies
 * 
 * @author yashgolwala
 * 
 */
public class RiskLaunchPhase {

	/** List containing name of players */
	private ArrayList<Player> playerList;

	/** Variable for MapRW object */
	private RiskMapRW mapObj;

	/** Number of players */
	private int numOfPlayer = 0;

	//private int playerCount;

	/** Minimum number of players */
	private static final int MIN_PLAYER = 2;

	/** Maximum number of players */
	private static final int MAX_PLAYER = 6;

	/**
	 * Constructor for the LaunchPhase class. This constructors 
     * gets the number of players and initializes them
	 * 
	 * @param mapObj Object of RiskMapRW with map data
	 */
	public RiskLaunchPhase(RiskMapRW mapObj) {
		this.mapObj = mapObj;
		this.playerList = new ArrayList<Player>();
		Scanner scan = new Scanner(System.in);
		try {
			do {
				System.out.println("Enter total number of players:");
				this.numOfPlayer = Integer.parseInt(scan.nextLine());
				if (this.numOfPlayer < 2 || this.numOfPlayer > 6) {
					System.out.println("Number of players must be between 2 and 6.");
				}
			} while (this.numOfPlayer < 2 || this.numOfPlayer > 6);
		} catch (NumberFormatException e) {
			System.out.println("Invalid number format");
		}

		System.out.println("Please enter the name of player(s).");
		for (int i = 0; i < this.numOfPlayer; ++i) {
			Player player = new Player();
			String name = null;
			if ((name = scan.nextLine()) != null) {
				player.setName(name);
			}
			this.playerList.add(player);
		}
	}

	/**
	 * Method to get players List
	 * 
	 * @return ArrayList which has the list of players.
	 */
	public ArrayList<Player> getPlayerList() {
		return playerList;
	}

	/**
	 * Method to set the player List
	 * 
	 * @param playerList which is the list of players needs to be set.
	 */
	public void setPlayerList(ArrayList<Player> playerList) {
		this.playerList = playerList;
	}

	/**
	 * Method to get mapObj object.
	 * 
	 * @return mapObj
	 */
	public RiskMapRW getMapObj() {
		return mapObj;
	}

	/**
	 * Method to set mapObj reference.
	 * 
	 * @param mapObj RiskMapRW object
	 */
	public void setMapObj(RiskMapRW mapObj) {
		this.mapObj = mapObj;
	}

	/**
	 * Method to get total number of players
	 * 
	 * @return int numOfPlayer
	 */
	public int getNumOfPlayer() {
		return numOfPlayer;
	}

	/**
	 * Method to set total number of players
	 * 
	 * @param numOfPlayer number of Players
	 */
	public void setNumOfPlayer(int numOfPlayer) {
		this.numOfPlayer = numOfPlayer;
	}

	/**
	 * Method to get minimum number of players.
	 * 
	 * @return int MIN_PLAYER
	 */
	public static int getMinPlayer() {
		return MIN_PLAYER;
	}

	/**
	 * Method to get maximum number of players
	 * 
	 * @return int MAX_PlAYER
	 */
	public static int getMaxPlayer() {
		return MAX_PLAYER;
	}

	/**
	 * Method to allocate countries to the players.
	 * Randomly assigned countries to the players.
	 */
	public void allocateCountries() {
		ArrayList<Country> countries = new ArrayList<>(this.mapObj.getMapGraph().getAllCountries().values());
		while (countries.size() > 0) {
			for (int i = 0; i < this.playerList.size(); ++i) {
				if (countries.size() > 1) {
					int countryIndex = new Random().nextInt(countries.size());
					this.playerList.get(i).addCountry(countries.get(countryIndex));
					countries.remove(countryIndex);
				} else if (countries.size() == 1) {
					this.playerList.get(i).addCountry(countries.get(0));
					countries.remove(0);
					break;
				} else {
					break;
				}
			}
		}
	}

	/**
	 * Method to allocate number of armies to the players which will vary
	 * based on the number of players.
	 */
	public void armyAllocateToPlayer() {
		for (Player player : this.playerList) {
			if (this.numOfPlayer == 2) {
				player.setArmyCount(40);
			} else if (this.numOfPlayer == 3) {
				player.setArmyCount(35);
			} else if (this.numOfPlayer == 4) {
				player.setArmyCount(30);
			} else if (this.numOfPlayer == 5) {
				player.setArmyCount(25);
			} else if (this.numOfPlayer == 6) {
				player.setArmyCount(20);
			}
		}
	}

	/**
	 * Method to allocate armies to the countries such that each country gets
	 * at least one country
	 */
	public void initialArmyToCountries() {
		for (Country country : mapObj.getMapGraph().getAllCountries().values()) {
			country.setNoOfArmies(1);
		}
		for (Player player : this.playerList) {
			player.setArmyCount(player.getArmyCount() - player.getMyCountries().size());
		}
	}

	/**
	 * Method to allocate armies to the countries in balanced state
	 */
	public void balanceArmyToCountries() {
		Scanner scan = new Scanner(System.in);
		for (Player player : this.playerList) {
			System.out.println("***** Player: " + player.getName() + " *****");
			for (Country country : player.getMyCountries()) {
				if (player.getArmyCount() > 0) {
					System.out.println("Number of armies currently assigned to country " + country.getName() + " is: "
							+ country.getNoOfArmies());
					System.out.println("Your available number of armies: " + player.getArmyCount());
					System.out.println("Enter number of armies you want to assign to country " + country.getName() + " :");
					int count = 0;
					try {
						count = Integer.parseInt(scan.nextLine());
						player.addArmiesToCountry(country, count);
					} catch (NumberFormatException e) {
						System.out.println("Please enter a valid number.");
					}
				} else {
					break;
				}
			}
		}
	}
}
