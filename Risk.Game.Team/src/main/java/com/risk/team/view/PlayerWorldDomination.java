package com.risk.team.view;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.Observable;

import com.risk.team.model.Country;
import com.risk.team.model.Player;
import com.risk.team.model.Continent;
import com.risk.team.controller.RiskLaunchPhase;
import com.risk.team.controller.RiskMapRW;
import com.risk.team.observer.Observer;
import com.risk.team.observer.Subject;

/**
 * Class to calculate percentage of territories owned by player across the world
 * 
 * @author Jenny
 */

public class PlayerWorldDomination extends Observer {

	private Subject observerSubject;

	/**
	 * Constructor
	 * 
	 * @param observerSubject ObserverSubject
	 */
	public PlayerWorldDomination(Subject observerSubject) {
		this.observerSubject = observerSubject;
		this.observerSubject.attach(this);
	}

	/**
	 * method to calculate the data for world domination
	 * 
	 * @param map RiskMapRW class
	 * @param RiskLaunchPhase1 Risk launch phase object
	 */
	public void getDominationDetails(RiskMapRW map,RiskLaunchPhase RiskLaunchPhase1) {
		fetchWorldDominationDetails(map,RiskLaunchPhase1);
	}

	/**
	 * method to retrieve the data for world domination
	 * @param map map
	 * @param RiskLaunchPhase1 risk launch phase object
	 */

	public void fetchWorldDominationDetails(RiskMapRW map, RiskLaunchPhase RiskLaunchPhase1) {

		HashMap<String, Double> playerOwnedCountryCount = new HashMap<>();
		HashMap<String, Integer> playerOwnedArmyCount = new HashMap<>();
		Double totalCountries = 0.0;

		for (Continent cont : map.getMapGraph().getContinents().values()) {
			for (Country ter : cont.getListOfCountries()) {
				totalCountries++;
				Player player = ter.getPlayer();
				ArrayList<Country> armyCountList = player.getMyCountries();
				int totalCount = 0;
				for (Country country: armyCountList) {
					totalCount += country.getNoOfArmies();
				}
				
				// Calculates countries owned by player
				if (!playerOwnedCountryCount.containsKey(player)) {
					playerOwnedCountryCount.put(player.getName(), Double.valueOf("1"));
				} else {
					playerOwnedCountryCount.put(player.getName(), playerOwnedCountryCount.get(player) + 1);
				}

				// Calculates armies owned by player
				if (!playerOwnedArmyCount.containsKey(player)) {
					playerOwnedArmyCount.put(player.getName(), totalCount);
		} 
			

			}
		}

		observerSubject.setPlayerOwnedArmyCount(playerOwnedArmyCount);

		// Calculates continents controlled by player
		HashMap<String, ArrayList<String>> playerOwnedContinent = new HashMap<>();
		

		for (Player player : RiskLaunchPhase1.getPlayerList()) {
			ArrayList<Country> playerOwnedCountries = player.getMyCountries();
			ArrayList<String> contList = new ArrayList<>();

			for (Continent cont : map.getMapGraph().getContinents().values()) {
				for (Country country : cont.getListOfCountries()) {
					if (!playerOwnedCountries.contains(country)) {
						contList.add("0");
						break;
					} else {
						contList.add(cont.getName());
					}

				}
			}
			playerOwnedContinent.put(player.getName(), contList);
		}

		observerSubject.setPlayerOwnedContinents(playerOwnedContinent);
		
		HashMap<String, Double> playerOwnedTerPercent = new HashMap<>();
		for (Entry<String, Double> entry : playerOwnedCountryCount.entrySet()) {
			playerOwnedTerPercent.put(entry.getKey(), (entry.getValue() / totalCountries * 100));
		}

		observerSubject.setPlayerOwnedTerPercent(playerOwnedTerPercent);
	}

	/**
	 * {@inheritDoc} update method to update changes
	 */
	@Override
	public void update() {
		if (observerSubject.getPlayerOwnedArmyCount() != null) {
			System.out.println("*******Armies owned by each player*********");
			for (Map.Entry<String, Integer> entry : observerSubject.getPlayerOwnedArmyCount().entrySet()) {
				System.out.println("Player = " + entry.getKey() + ", Armies = " + entry.getValue().toString());
			}
			System.out.println();
		}
		
		if (observerSubject.getPlayerOwnedContinents() != null) {
			System.out.println("*******Continents owned by each player*********");
			for (Map.Entry<String, ArrayList<String>> entry : observerSubject.getPlayerOwnedContinents().entrySet()) {
				System.out.println("Player = " + entry.getKey() + ", Continents = " + entry.getValue().toString());
			}
			System.out.println();
		}
		if (observerSubject.getPlayerOwnedTerPercent() != null) {
			System.out.println("*******Percentage of Map owned by each player*********");
			for (Map.Entry<String, Double> entry : observerSubject.getPlayerOwnedTerPercent().entrySet()) {
				System.out.println("Player = " + entry.getKey() + ", % occupied = " + entry.getValue());
			}
		}
	}

	/**	
	 * {@inheritDoc} update method to update fortification message
	 */
	@Override
	public void fortificationUpdate() {

	}

	/**
	 * {@inheritDoc} method to update attack message
	 */
	@Override
	public void attackUpdate() {

	}

	/**
	 * {@inheritDoc} method to update reinforcement message
	 */
	@Override
	public void reinforcementUpdate() {

	}

	/**
	 * {@inheritDoc} method to update current player details
	 */
	@Override
	public void currentPlayerUpdate() {

	}

	/**
	 * {@inheritDoc} method to update reinforcement actions
	 */
	@Override
	public void actionsUpdate() {

	}

	/**
	 * {@inheritDoc} method to update army count
	 */
	@Override
	public void armyCountUpdate() {
		// TODO Auto-generated method stub

	}

	/**
	 * {@inheritDoc} method to update attack action details
	 */
	@Override
	public void attackActionUpdate() {
		// TODO Auto-generated method stub

	}

	/**
	 * {@inheritDoc} method to update fortification action details
	 */
	@Override
	public void fortificationActionUpdate() {
		// TODO Auto-generated method stub

	}

	/**
	 * {@inheritDoc} method to update trade army count during card exchange
	 */
	@Override
	public void tradeArmyUpdate() {
		// TODO Auto-generated method stub

	}

}
