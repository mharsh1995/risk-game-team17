package com.risk.team.view;

import com.risk.team.controller.RiskMapRW;
//import com.risk.team.model.Dice;
//import com.risk.team.controller.DiceController;

/**
 * Launches the Game and provides the main window and the view for the user,
 * to load, Create and Edit a map.
 *
 * @author Harsh Mehta
 * @author Yash Golwala
 * 
 * @author Dhaval Desai - Seperated the file into controllers and LoadUI file.
 */

public class LoadRiskGame{

	public static boolean status;
	static RiskMapRW read;

	/**
	 * 
	 * The default constructor generates the initial UserInterace and provides the user with the Start Menu
	 * 
	 */
	public LoadRiskGame() {
		try {
			LoadUI userInterface=new LoadUI();
			userInterface.generateUI();
		} catch (Exception ex) {
			System.out.println("Error Loading User Interface!!!");
		}
	}
	/**
	 * Main Method
	 * 
	 * @param args String array.
	 */
	public static void main(String[] args) {
		LoadRiskGame exp = new LoadRiskGame();
	}
}