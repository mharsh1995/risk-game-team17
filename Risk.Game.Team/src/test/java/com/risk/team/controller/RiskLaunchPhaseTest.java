package com.risk.team.controller;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;

import com.risk.team.controller.RiskLaunchPhase;
import com.risk.team.model.Player;

/**
 * 
 * TestClass for army allocation on launch of the game.
 * 
 * @author Harsh Mehta
 * 
 */

public class RiskLaunchPhaseTest {

	/** Object for RiskLaunchPhase Class*/
	private RiskLaunchPhase launchObj;

	/** Objects for Player Class */
	private Player player1, player2, player3, player4, player5;

	/** ArrayList to store Players*/
	private ArrayList<Player> playerList1;
	private ArrayList<Player> playerList2;
	private ArrayList<Player> playerList3;
	private ArrayList<Player> playerList4;
	/**
	 * Set up the initial objects for checking the allocated army counts
	 * 
	 */

	@Before public void intialize() {

		launchObj = new RiskLaunchPhase();

		player1 = new Player();
		player2 = new Player();
		player3 = new Player();
		player4 = new Player();
		player5 = new Player();

		playerList1 = new ArrayList<>();
		playerList1.add(player1);
		playerList1.add(player2);

		playerList2 = new ArrayList<>();
		playerList2.add(player1);
		playerList2.add(player2);
		playerList2.add(player3);

		playerList3 = new ArrayList<>();
		playerList3.add(player1);
		playerList3.add(player2);
		playerList3.add(player3);
		playerList3.add(player4);

		playerList4 = new ArrayList<>();
		playerList4.add(player1);
		playerList4.add(player2);
		playerList4.add(player3);
		playerList4.add(player4);
		playerList4.add(player5);

	}

	/**
	 * Test method 1 to check if a army is allocated Properly
	 */

	@Test public void armyAllocateToTwoPlayerTest() {

		launchObj.setNumOfPlayer(2); //If number of players are Two
		launchObj.setPlayerList(playerList1);
		launchObj.armyAllocateToPlayer();
		assertEquals(40,player2.getArmyCount());

	}

	/**
	 * Test method 2 to check if a army is allocated Properly
	 */

	@Test public void armyAllocateToThreePlayerTest() {

		launchObj.setNumOfPlayer(3); //If number of players are Three
		launchObj.setPlayerList(playerList2);
		launchObj.armyAllocateToPlayer();
		assertEquals(35,player2.getArmyCount());
	}

	/**
	 * Test method 3 to check if a army is allocated Properly
	 */

	@Test public void armyAllocateToFourPlayerTest() {

		launchObj.setNumOfPlayer(4); //If number of players are Four
		launchObj.setPlayerList(playerList3);
		launchObj.armyAllocateToPlayer();
		assertEquals(30,player2.getArmyCount());
	}

	/**
	 * Test method 4 to check if a army is allocated Properly
	 */

	@Test public void armyAllocateToFivePlayerTest() {

		launchObj.setNumOfPlayer(5); //If number of players are Five
		launchObj.setPlayerList(playerList4);
		launchObj.armyAllocateToPlayer();
		assertEquals(25,player2.getArmyCount());
	}

}
