package com.risk.team.strategy;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import com.risk.team.controller.GamePhaseController;
import com.risk.team.model.Country;
import com.risk.team.model.Player;
import com.risk.team.strategy.Human;

/**
 * Test class for Human class.
 * 
 * @author yashgolwala
 *
 */
public class HumanTest {

	/** Object for Country class */
	private Country attacking;
	
	/** Object for Country class */
	private Country defending;
	
	/** Object for Player class */
	private Player player1;
	
	/** Object for Player class */
	private Player player2;
	
	/** Object for Human class */
	private Human human;
	
	/** Object for GamePlayController class */
	private GamePhaseController gamePhaseController;
	
	/**
	 * Set up the initial objects for Human class
	 * 
	 */
	@Before
	public void initialize() {
		
		attacking = new Country("India");
		player1 = new Player();
		attacking.setPlayer(player1);
		
		
		defending = new Country("China");
		player2 = new Player();
		defending.setPlayer(player2);
		
		gamePhaseController = new GamePhaseController();
		human = new Human(gamePhaseController);
		
	}
	/**
	* Test to check valid attack move.
	*/
	@Test
	public void isAttackMoveValidTest() {
		attacking.setNoOfArmies(3);
		assertTrue(human.isAttackMoveValid(attacking, defending));
	}

}
