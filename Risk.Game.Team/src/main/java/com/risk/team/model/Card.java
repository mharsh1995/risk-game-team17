package com.risk.team.model;

import javafx.scene.control.CheckBox;


import java.util.*;
import java.util.stream.Collectors;
import com.risk.team.controller.GamePhaseController;
import com.risk.team.controller.RiskCardController;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * Card class which represents the card model of the Risk game.
 * It provides methods for performing operations on the cards
 * like trade cards for armies etc.
 *
 * @author yashgolwala
 * 
 */

public class Card extends Observable implements Serializable {

	/**
	 * Kind of the card
	 */
	String kindOfCard;

	/**
	 * Object of country, which is the on the card
	 */
	private Country country;

	/**
	 * Current Player who is the card Holder
	 */
	private Player currentPlayer;

	/**
	 * List of cards which can be trade
	 */
	private List<Card> cardsToTrade;

	/**
	 * Cards constructor
	 */

	public Card() {
	}

	/**
	 * Cards constructor
	 *
	 * @param kindOfCard Type of card
	 */

	public Card(String cardType) {
		this.kindOfCard = cardType;
	}

	/**
	 * Method to get current player
	 * 
	 * @return currentPlayer current player
	 */
	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	/**
	 * Method to set current player
	 * 
	 * @param currentPlayer current player
	 */
	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	/**
	 * Get kind of card
	 *
	 * @return KindOfCard kind of card
	 */

	public String getKindOfCard() {
		return kindOfCard;
	}

	/**
	 * get Country of the card
	 *
	 * @return country country of the card
	 */
	public Country getCountry() {
		return country;
	}

	/**
	 * Set Country
	 *
	 * @param country country of the card
	 */

	public void setCountry(Country country) {
		this.country = country;
	}

	/**
	 * Getter for list of cards for trade.
	 *
	 * @return cardsToTrade list of cards to trade
	 */

	public List<Card> getCardsToTrade() {
		return cardsToTrade;
	}

	/**
	 * Set cardsToTrade
	 *
	 * @param cardsToTrade cards to trade
	 */
	public void setCardsToTrade(List<Card> cardsToTrade) {
		this.cardsToTrade = cardsToTrade;
	}

	/**
	 * Method to automate card window
	 * 
	 * @param player player object
	 */
	public void automateCardWindow(Player player){
		RiskCardController riskCardController = new RiskCardController(player, this);
		riskCardController.automaticCardInitialization();
	}


	/**
	 * Method returns a list of cards which
	 * are selected by the current player
	 *
	 * @param list list
	 * @param checkboxes checkboxes
	 * @return selectedCards List of cards selected by the player
	 */
	public List<Card> chooseCards(List<Card> list, CheckBox[] checkboxes) {
		List<Card> selectedCards = new ArrayList<>();
		for (int i = 0; i < checkboxes.length; ++i) {
			if (checkboxes[i].isSelected()) {
				selectedCards.add(list.get(i));
			}
		}
		return selectedCards;
	}

	/**
	 * Method is used to verify ,
	 * if cards can be traded for army or not
	 *
	 * @param selectedCards selected cards
	 * @return isPossible true if the trade is possible; otherwise false
	 */
	public boolean isTradePossible(List<Card> selectedCards) {
		boolean isPossible = false;
		if (selectedCards.size() == 3) {
			int infantry = 0, cavalry = 0, artillery = 0;
			for (Card card : selectedCards) {
				if (card.getKindOfCard().equals(BonusCardType.INFANTRY)) {
					infantry++;
				} else if (card.getKindOfCard().equals(BonusCardType.CAVALRY)) {
					cavalry++;
				} else if (card.getKindOfCard().equals(BonusCardType.ARTILLERY)) {
					artillery++;
				}
			}
			if ((infantry == 1 && cavalry == 1 && artillery == 1) || infantry == 3 || cavalry == 3 || artillery == 3) {
				isPossible = true;
			}
		}
		return isPossible;
	}

	/**
	 * Method notifies the observers of the card,
	 * Also sets the cards which are selected for trade.
	 *
	 * @param selectedCards cards which are selected by the user to exchange
	 */
	public void cardsToTrade(List<Card> selectedCards) {
		setCardsToTrade(selectedCards);
		setChanged();
		notifyObservers("cardsExchange");

	}

	/**
	 * Method to generate valid combination of cards
	 * 
	 * @param selectedCards List of selected cards
	 * @return List of valid combination of cards
	 */
	public List<Card> generateValidCardCombination(List<Card> selectedCards) {
		HashMap<String, Integer> cardCountMap = new HashMap<>();
		for (Card card : selectedCards) {
			if (cardCountMap.containsKey(card.getKindOfCard())) {
				cardCountMap.put(card.getKindOfCard(), cardCountMap.get(card.getKindOfCard()) + 1);
			} else {
				cardCountMap.put(card.getKindOfCard(), 1);
			}

		}
		for (Map.Entry<String, Integer> entry : cardCountMap.entrySet()) {
			if (entry.getValue() >= 3) {
				return selectedCards.stream().filter(t -> t.getKindOfCard().equals(entry.getKey()))
						.collect(Collectors.toList());
			}
		}
		return null;
	}
}
