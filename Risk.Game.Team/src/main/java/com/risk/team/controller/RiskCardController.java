package com.risk.team.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.Exchanger;

import com.risk.team.model.Card;
import com.risk.team.model.Player;

/**
 * Card Controller Class
 *
 * @author yashgolwala
 */

public class RiskCardController {

	/**
	 * Object for Player class
	 */
	public Player player;

	/**
	 * Object for Card class
	 */
	public Card card;

	public int isLoadCard=0; //LoadCard flag variable

	/**
	 * List of cards owned by the player
	 */
	private ArrayList<Card> playerOwnedCards;

	/**
	 * Risk Card Controller constructor class
	 *
	 * @param player Current Player
	 * @param card   player card
	 */
	public RiskCardController(Player player, Card card) {
		this.player = player;
		this.card = card;

	}

	/**
	 * Method to initialize Trade for player
	 * @return true if possible or false if not possible
	 */
	public boolean initializeTrade() {
		System.out.println("Player : " + player.getName());
		playerOwnedCards = player.getListOfCards();
		if (playerOwnedCards.size() < 3) {
			isLoadCard=0;
			return false;

		} else {
			isLoadCard=1;
			return true;
		}


	}

	/**
	 * Method to load cards for player
	 */
	public void loadCards() {

		System.out.println("Cards Owned by the player currently :");

		for(Card card : player.getListOfCards())
		{
			System.out.println(card.getKindOfCard());
		}

	}

	/**
	 * Method for check trade. validates the card
	 * combination and number of cards selected by the player
	 * @return true if trade possible or false if trade is not possible
	 */

	public boolean checkTrade() {
		isLoadCard=1;

		List<Card> selectedCards = card.chooseCards(playerOwnedCards);

		if (selectedCards.size() == 3) {
			boolean flag = card.isTradePossible(selectedCards);

			if (flag) {
				card.cardsToTrade(selectedCards);
				return true;

			} else {
				System.out.println("This Combination is not Valid");
				isLoadCard=0;            
				return false;
			}
		} else {
			System.out.println("Select only 3 Cards or Select at least 3 cards");
			isLoadCard=0;
			return false;
		}  
	}
}