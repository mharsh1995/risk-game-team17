package com.risk.team.view;

import java.util.ArrayList;
import java.util.Scanner;

import com.risk.team.controller.RiskLaunchPhase;
import com.risk.team.model.Player;

public class PlayerDetailView {
	
	private RiskLaunchPhase riskLaunchPahse;
	
	public ArrayList<Player> playerlist;

	private int numOfPlayer;
	
	public PlayerDetailView(RiskLaunchPhase riskLaunchPahse)
	{
		this.riskLaunchPahse = riskLaunchPahse;
		start();
		
	}

	private void start()
	
	{
			getPlayerDetails();
			riskLaunchPahse.setNumOfPlayer(numOfPlayer);
			riskLaunchPahse.setPlayerList(playerlist);
			
	}

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
		
		scan.close();
		
	}
	
}
