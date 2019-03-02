package com.Risk.Team.Controller.GamePhase;

import java.util.ArrayList;
import java.util.Iterator;

import com.Risk.Team.model.Player;

/**
 * The Player takes get turn in Round Robin fashion.
 * 
 * @author Jenny
 *
 */
public class RiskRoundRobin {

	/** Player Iterator */
	private Iterator<Player> iterator;

	/** List of Players */
	private ArrayList<Player> playerList;

	/**
	 * RiskRoundRobin Constructor
	 * 
	 * @param PlayerList list of Players
	 */
	public RiskRoundRobin(ArrayList<Player> PlayerList) {
		this.playerList = PlayerList;
		this.iterator = this.playerList.iterator();
	}

	/**
	 * Method for the next player to take the turn.
	 * 
	 * @return turn of Player
	 */
	public Player nextPlayer() {
		if (!this.iterator.hasNext()) {
			this.iterator = this.playerList.iterator();
		}
		
		return this.iterator.next();
		
	}
}

