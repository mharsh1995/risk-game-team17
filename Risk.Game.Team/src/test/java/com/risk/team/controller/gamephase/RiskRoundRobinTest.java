package com.risk.team.controller.gamephase;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import com.risk.team.model.Player;

/**
 * Test class for Risk Round Robin.
 * 
 * @author Jenny
 *
 */

public class RiskRoundRobinTest {

	/** Object for RoundRobin class */
	private RiskRoundRobin riskRoundRobin;

	/** Object for Player class */
	Player player;

	/** ArrayList to hold list of players in the game */
	private ArrayList<Player> playerList;

	/**
	 * Set up the initial objects for Round Robin Phase
	 * 
	 */
	@Before
	public void initialize() {

		playerList = new ArrayList<Player>();

		player = new Player(); 
		player.setName("ONE"); 
		playerList.add(player);		 

		player = new Player();
		player.setName("TWO");
		playerList.add(player);

		player = new Player();
		player.setName("THREE");
		playerList.add(player);

		player = new Player();
		player.setName("FOUR");
		playerList.add(player);

		riskRoundRobin = new RiskRoundRobin(playerList);
	}

	/**
	 * Test to return the next player in round robin fashion.
	 */
	@Test
	public void nextTest() {

		String player1= riskRoundRobin.nextPlayer().getName(); 
		assertEquals("ONE",player1);

		String player2= riskRoundRobin.nextPlayer().getName();
		assertEquals("TWO", player2);

		String player3= riskRoundRobin.nextPlayer().getName();
		assertEquals("THREE", player3);

		String player4= riskRoundRobin.nextPlayer().getName();
		assertEquals("FOUR", player4);
	}

}
