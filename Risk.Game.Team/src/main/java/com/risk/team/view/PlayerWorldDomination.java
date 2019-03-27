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
 * @author Jenny 
 */

public class PlayerWorldDomination extends Observer {
	
	private Subject observerSubject;
	
	/**
	 * Constructor
	 * @param observerSubject ObserverSubject
	 */	
	public PlayerWorldDomination(Subject observerSubject) {
		this.observerSubject = observerSubject;
		this.observerSubject.attach(this);
	}
	
	/** method to calculate the data for world domination
	 */
	public void getDominationDetails(RiskMapRW map) {
		fetchWorldDominationDetails(map);
	}
	
	/** method to retrieve the data for world domination
	 * @param RiskMapRW map
	 * @return playerOwnedTerPercent */

	public void fetchWorldDominationDetails(RiskMapRW map) {

		HashMap<String, Double> playerOwnedCountryCount = new HashMap<>();
		HashMap<String, Integer> playerOwnedArmyCount = new HashMap<>();
		Double totalCountries = 0.0;
		
		for (Continent cont : map.getMapGraph().getContinents().values()) {
			for (Country ter : cont.getListOfCountries()) {
				totalCountries++;
				Player player = ter.getPlayer();
				int armyCount = player.getArmyCount();
				
				//Calculates countries owned by player
				if(!playerOwnedCountryCount.containsKey(player)) {
					playerOwnedCountryCount.put(player.getName(), Double.valueOf("1"));					
				} else {
					playerOwnedCountryCount.put(player.getName(), playerOwnedCountryCount.get(player)+1);					
				}
				
				//Calculates armies owned by player
				if(!playerOwnedArmyCount.containsKey(player)) {
					playerOwnedArmyCount.put(player.getName(), armyCount);					
				} else {
					playerOwnedArmyCount.put(player.getName(), playerOwnedArmyCount.get(player)+armyCount);					
				}								
			
			}
		}
		
		for (Map.Entry<String, Integer> entry : playerOwnedArmyCount.entrySet()) {  
            System.out.println("Player = " + entry.getKey() + ", Armies = " + entry.getValue()); 
    } 
		//observerSubject.setPlayerOwnedArmyCount(playerOwnedArmyCount);
		
		//Calculates continents controlled by player
		HashMap <String, ArrayList<String>> playerOwnedContinent = new HashMap<>();
		RiskLaunchPhase RiskLaunchPhase = new RiskLaunchPhase(map);
				
		for(Player player: RiskLaunchPhase.getPlayerList())
		{
			ArrayList<Country> playerOwnedCountries= player.getMyCountries();
			ArrayList<String> contList= new ArrayList<>();
			
			for (Continent cont : map.getMapGraph().getContinents().values()) {
				for (Country country : cont.getListOfCountries()) {
					if (!playerOwnedCountries.contains(country)) {
						contList.add("0");
						break;
					}
					else {
						contList.add(cont.getName());						
					}
										
						
					}
				}
			playerOwnedContinent.put(player.getName(),contList);	
			}
		
		
		for (Map.Entry<String, ArrayList<String>> entry : playerOwnedContinent.entrySet()) {  
            System.out.println("Player = " + entry.getKey() + ", Continents = " + entry.getValue().toString()); 
		} 
		//observerSubject.setPlayerOwnedContinents(playerOwnedContinent);
		
		HashMap<String, Double> playerOwnedTerPercent = new HashMap<>();
		for(Entry<String, Double> entry : playerOwnedCountryCount.entrySet()) {
			playerOwnedTerPercent.put(entry.getKey(), (entry.getValue()/totalCountries * 100));
		}	
		
		for (Map.Entry<String, Double> entry : playerOwnedTerPercent.entrySet()) {  
            System.out.println("Player = " + entry.getKey() + ", % occupied = " + entry.getValue()); 
		} 
		//observerSubject.setplayerOwnedTerPercent(playerOwnedTerPercent);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fortificationUpdate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void attackUpdate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void tradeInCardUpdate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void botTradeInCardUpdate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reinforcementUpdate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void playerLogUpdate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void currentPlayerUpdate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionsUpdate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void armyCountUpdate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void attackActionUpdate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fortificationActionUpdate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void tradeArmyUpdate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void playerOwnedArmyCountUpdate() {
		System.out.println("Armies owned each player:" + observerSubject.getPlayerOwnedArmyCount());
	}

	@Override
	public void playerOwnedArmyContinentUpdate() {
		System.out.println("Continents owned each player:" + observerSubject.getPlayerOwnedContinents());
	}

	@Override
	public void playerTerPercentUpdate() {
		System.out.println("Percentage of map owned each player:" + observerSubject.getPlayerTerPercent());
	}

	
}
