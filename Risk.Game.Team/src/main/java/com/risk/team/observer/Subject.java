package com.risk.team.observer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.risk.team.observer.Observer;
//import com.risk.team.view.GamePhaseView;
import com.risk.team.model.Player;
import com.risk.team.model.Country;

/**
 * This class used to implement observer pattern
 *  @author Jenny
 */
public class Subject {
	private List<Observer> observers = new ArrayList<>();
	private String fortificationMsg;
	private String reinforcementMsg;
	private String attackMsg;
	private String tradeInMsg;
	private String playerName;
	private Player player;
	private Country country;
	private Country AttackCountryObj;
	private Country defendCountryObj;
	private Country sourceCountry;
	private Country destinationCountry;
	private ArrayList<Integer> calculateTradeArmyCount;
	private HashMap<String, Integer> playerOwnedArmyCount;
	private HashMap<String, ArrayList<String>> playerOwnedContinent;
	private HashMap<String, Double> playerTerPercent;
	/**
	 * getReinforcement Message
	 * @return reinforcementMsg
	 */
	public String getReinforcementMsg() {
		return reinforcementMsg;
	}

	/**
	 * set Reinforcement message
	 * @param reinforcementMsg reinforcementMsg
	 */
	public void setReinforcementMsg(String reinforcementMsg) {
		this.reinforcementMsg = reinforcementMsg;
		notifyForReinforcement();
	}

	/**
	 * get current player details
	 * @return playerName player name
	 */
	public String getCurrentPlayerDetails() {
		return playerName;
	}
	/**
	 * set current player details
	 * @param reinforcementMsg reinforcementMsg
	 */
	public void setCurrentPlayerDetails(String playerName) {
		this.playerName = playerName;
		notifyForCurrentPlayer();
	}
	
	/**
	 * get Country object
	 * @param country Country
	 */
	public Country getCountryObj() {
		return country;
	}
	
	/**
	 * get Player object
	 * @param player Player
	 */
	public Player getPlayerObj() {
		return player;
	}
	
	/**
	 * set current player details
	 * @param reinforcementMsg reinforcementMsg
	 */
	public void setActionDetails(Player player, Country country) {
		this.player = player;
		this.country = country;
		notifyActionDetails();
	}
	
	/**
	 * get method for messageFlag
	 * @return messageFlag message flag boolean
	 */

	/**
	 * get method for attack message
	 * @return attackmsg attack message
	 */
	public String getAttackMsg() {
		return attackMsg;
	}

	/**
	 * set method for attack message 
	 * @param attackMsg attack message
	 */
	public void setAttackMsg(String attackMsg) {
		this.attackMsg = attackMsg;
		notifyForAttack();
	}
	
	/**
	 * set method for attack actions 
	 * @param AttackCountryObj Country
	 * @param defendCountryObj Country
	 */
	public void setAttackDetails(Country AttackCountryObj, Country defendCountryObj) {
		this.AttackCountryObj = AttackCountryObj;
		this.defendCountryObj = defendCountryObj;
		notifyAttackAction();
	}	

	/**
	 *  get method for Attacker Country Object
	 *  @return AttackCountryObj 
	 */
	public Country getAttackCountryObj(){
		return AttackCountryObj;
	}
	
	/**
	 *  get method for Defender Country Object
	 *  @return fortificationMsg
	 *  */
	public Country getDefendCountryObj(){
		return defendCountryObj;
	}

	/**
	 * set PlayerLog
	 */
	public void setPlayerLog() {
		notifyForLogUpdate();
	}


	/**
	 * observer registration
	 * @param observer observer name
	 */
	public void attach(Observer observer) {
		observers.add(observer);
	}

	/**
	 * notify all registered observers
	 */
	public void notifyAllObservers() {
		for (Observer observer : observers) {
			observer.update();
		}
	}
	
	/**
	 *  method to retrieve army count after placing army in reinforcement phase
	 */
	public void placedArmyCount(Country country) {
		this.country = country;
		notifyArmyCountAfterReinforecement();		
	}
	
	/**
	 *  get method for fortification message
	 * @return fortification msg message 
	 */
	public String getFortificationMsg() {
		return fortificationMsg;
	}

	/**
	 * set method for fortification message
	 * @param fortificationMsg message
	 */
	public void setFortificationMsg(String fortificationMsg) {
		this.fortificationMsg = fortificationMsg;
		notifyForFortification();
	}
	
	/**
	 * get method for fortification source country
	 * @return sourceCountry Country
	 */
	public Country getSourceCountry() {
		return sourceCountry;		
	}
	
	/**
	 * get method for fortification destination country
	 * @return destinationCountry Country
	 */
	public Country getDestinationCountry() {
		return destinationCountry;		
	}
	
	/**
	 * set method for fortification actions
	 * @param sourceCountry Country
	 * @param destinationCountry Country
	 */
	public void setFortificationDetails(Country sourceCountry, Country destinationCountry) {
		this.sourceCountry = sourceCountry;
		this.destinationCountry = destinationCountry; 
		notifyFortificationAction();
		
	}
	/**
	 * set method for card trade
	 * @param calculateTradeArmyCount list of army count
	 */	
	public ArrayList<Integer> getTradeArmyCount() {
		return calculateTradeArmyCount;
	}
	
	/**
	 * set method for card trade
	 * @param calculateTradeArmyCount list of army count
	 */	
	public void setTradeArmyCount(ArrayList<Integer> calculateTradeArmyCount) {
		this.calculateTradeArmyCount = calculateTradeArmyCount;
		notifyTradeArmyCount();
	}
	/**
	 * get method for army count owned by player
	 */
	public HashMap<String, Integer> getPlayerOwnedArmyCount() {
		return playerOwnedArmyCount;
	}
	
	/**
	 * set method for card trade
	 * @param playerOwnedArmyCount hashmap of army count owned by every player
	 */
	public void setPlayerOwnedArmyCount(HashMap<String, Integer> playerOwnedArmyCount) {
		this.playerOwnedArmyCount = playerOwnedArmyCount;
		notifyPlayerOwnedArmyCount();
	}
	
	/**
	 * get method for continents owned by player
	 */
	public HashMap<String, ArrayList<String>> getPlayerOwnedContinents() {
		return playerOwnedContinent;
	}
	
	/**
	 * set method for card trade
	 * @param playerOwnedArmyCount hashmap of army count owned by every player
	 */
	public void setPlayerOwnedContinents(HashMap<String, ArrayList<String>> playerOwnedContinent) {
		this.playerOwnedContinent = playerOwnedContinent;
		notifyPlayerOwnedContinent();
	}	
	
	/**
	 * get method for continents owned by player
	 */
	public HashMap<String, Double> getPlayerTerPercent() {
		return playerTerPercent;
	}
	
	/**
	 * set method for card trade
	 * @param playerOwnedArmyCount hashmap of army count owned by every player
	 */
	public void setPlayerTerPercent(HashMap<String, Double> playerTerPercent) {
		this.playerTerPercent = playerTerPercent;
		notifyPlayerTerPercent();
	}	


	/**
	 * notify observers about fortification changes
	 */
	public void notifyForFortification() {
		for (Observer observer : observers) {
			observer.fortificationUpdate();
		}
	}

	/**
	 * notify observers about attack changes
	 */
	public void notifyForAttack() {
		for (Observer observer : observers) {
			observer.attackUpdate();
		}
	}

	/**
	 * notify observers about card trade changes
	 */
	public void notifyForTradeIn() {
		for (Observer observer : observers) {
			observer.tradeInCardUpdate();
		}
	}

	/**
	 * notifyForBotTradeIn
	 */
	public void notifyForBotTradeIn() {
		for (Observer observer : observers) {
			observer.botTradeInCardUpdate();
		}
	}

	/**
	 * notifyForReinforcement
	 */
	public void notifyForReinforcement() {
		for (Observer observer : observers) {
			observer.reinforcementUpdate();
		}
	}
	
	/**
	 * notifyForCurrentPlayer
	 */
	public void notifyForCurrentPlayer() {
		for (Observer observer : observers) {
			observer.currentPlayerUpdate();
		}
	}	

	/**
	 * notifyForLogUpdate
	 */
	public void notifyForLogUpdate() {
		for (Observer observer : observers) {
			observer.playerLogUpdate();
		}
	}
	
	/**
	 * notifyActionDetails
	 */
	
	public void notifyActionDetails() {
		for (Observer observer : observers) {
			observer.actionsUpdate();
		}
	}
	
	/**
	 * notifyArmyCountAfterReinforecement
	 */
	public void notifyArmyCountAfterReinforecement() {
		for (Observer observer : observers) {
			observer.armyCountUpdate();
		}
	}
	
	/**
	 * notifyAttackActionUpdate
	 */
	public void notifyAttackAction() {
		for (Observer observer : observers) {
			observer.attackActionUpdate();
		}		
	}
	
	/**
	 * notifyFortificationAction
	 */	
	public void notifyFortificationAction() {
		for (Observer observer : observers) {
			observer.fortificationActionUpdate();
		}	
	}
	
	/**
	 * notifyTradeArmyCount
	 */	
	public void notifyTradeArmyCount() {
		for (Observer observer : observers) {
			observer.tradeArmyUpdate();
		}	
	}	
	
	/**
	 * notifyPlayerOwnedArmyCount
	 */	
	public void notifyPlayerOwnedArmyCount() {
		for (Observer observer : observers) {
			observer.playerOwnedArmyCountUpdate();
		}	
	}
	/**
	 * notifyPlayerOwnedContinent
	 */
	public void notifyPlayerOwnedContinent() {
		for (Observer observer : observers) {
			observer.playerOwnedArmyContinentUpdate();
		}
	}
	/**
	 * notifyPlayerTerPercent
	 */
	public void notifyPlayerTerPercent() {
		for (Observer observer : observers) {
			observer.playerTerPercentUpdate();
		}
	}
}
