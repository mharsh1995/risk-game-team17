package com.risk.team.view;

import java.util.ArrayList;

import java.util.Scanner;

import com.risk.team.controller.RiskLaunchPhase;
import com.risk.team.model.Player;

/** PlayerDetailView contains method for player details
 * @author Kartika Patil
 *
 */
public class PlayerDetailView {
	/** riskLaunchPahse object for RiskLaunchPhase Class
	 */
	private RiskLaunchPhase riskLaunchPahse;

	/** List of Players
	 */
	public ArrayList<Player> playerlist;

	/** No of Players
	 */
	public int numOfPlayer;

	/** Constructor for PlayerDetailView with constructor
	 * @param riskLaunchPahse RiskLaunchPhase class
	 */
	public PlayerDetailView(RiskLaunchPhase riskLaunchPahse)
	{
		this.riskLaunchPahse = riskLaunchPahse;
		playerlist = new ArrayList<Player>();
		start();
	}

	/** method to fetch player details
	 */
	private void start()

	{
		getPlayerDetails();
		riskLaunchPahse.setNumOfPlayer(numOfPlayer);
		riskLaunchPahse.setPlayerList(playerlist);

	}

	/** getter method for player details
	 */
	private void getPlayerDetails() {
		Scanner scan = new Scanner(System.in);
		try {
			do {
				System.out.println("Enter total number of players:");
				this.numOfPlayer = Integer.parseInt(scan.nextLine());
				if (this.numOfPlayer < 2 || this.numOfPlayer > 6) {
					System.out.println("Number of players must be between 2 and 6.");
				}
			} while (this.numOfPlayer < 2 || this.numOfPlayer > 6);
			System.out.println("Please enter the name of player(s).");
			for (int i = 0; i < this.numOfPlayer; ++i) {
				Player player = new Player();
				String name = null;
				if ((name = scan.nextLine()) != null) {
					player.setName(name);
				}
				this.playerlist.add(player);
			}
		} catch (NumberFormatException e) {
			System.out.println("Invalid number format!! Please re-enter details");
			getPlayerDetails();
		}
		
	}
}
