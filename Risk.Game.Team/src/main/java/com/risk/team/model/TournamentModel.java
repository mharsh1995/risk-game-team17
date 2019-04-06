package com.risk.team.model;

import com.risk.team.controller.GamePhaseController;
import com.risk.team.services.RiskMapRW;
import com.risk.team.services.RiskMapVerify;
import com.risk.team.services.StartUpPhase;
import com.risk.team.services.Util.GameWindowUtil;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * 
 * Tournament Model class having methods related to tournament mode.
 * 
 * @author Kartika Patil
 * 			  
 */

public class TournamentModel {

	/** Static flag to check if tournament is being played*/
	public static boolean isTournamentMode = false;

	/**
	 * Constructor for TournamentModel
	 *
	 */
	public TournamentModel(){
		isTournamentMode = true;
	}

	/**
	 * Method for checking if the map is valid or not,
	 * then uploading it to play the tournament
	 *
	 * @param mapList List of maps to be updated when a file is uploaded
	 * @return Map File
	 */
	public File checkAndLoadMap(List<RiskMapRW> mapList) {

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Select a Map File");
		fileChooser.getExtensionFilters()
		.add(new FileChooser.ExtensionFilter("Map File Extensions (*.map or *.MAP)", "*.map", "*.MAP"));
		File selectedFile = fileChooser.showOpenDialog(null);

		if (selectedFile != null) {

			String fileName = selectedFile.getAbsolutePath();
			System.out.println("File location: " + fileName);
			RiskMapVerify riskMapVerify = new RiskMapVerify();

			if (riskMapVerify.validateMapFile(fileName)) {
				RiskMapRW readMap = new RiskMapRW(riskMapVerify);
				mapList.add(readMap);
				return selectedFile;
			}
			GameWindowUtil.popUpWindow("Invalid Map", "Problem with map file", "Please selecte another file");

		}

		return null;
	}

	/**
	 *
	 * Method for controlling the flow of each game in the tournament
	 *
	 * @param playerList List of players playing
	 * @param numberOfTurnsToPlay NUmber of turns to be played in each game
	 * @param gameCount Number of the game being played
	 * @param riskMapRW Map on which the game would be played
	 * @param textArea Text Area where the game details would be printed
	 * @return winnerMap HashMAp with the winner name and game number.
	 */

	public HashMap<Player, Integer> playGame(List<Player> playerList, int numberOfTurnsToPlay, int gameCount, RiskMapRW riskMapRW, TextArea textArea) {

		Player winner;
		List<Player> currentGamePlayerList = new ArrayList<>();
		HashMap<String, String> hashMapForGameController = new HashMap<>();

		for(Player p: playerList){
			currentGamePlayerList.add(p);
		}

		System.out.println("Game started");

		// Dummy player to call player methods
		Player player = new Player();
		for(Player p: currentGamePlayerList){
			hashMapForGameController.put(p.getName(), p.getPlayerType());
		}
		GamePhaseController gamePhaseController = new GamePhaseController(riskMapRW, hashMapForGameController);
		gamePhaseController.setGamePlayerList(new ArrayList<Player>());
		gamePhaseController.getGamePlayerList().clear();

		Card card = new Card();

		// Startup phase started
		System.out.println(" Startup phase started");
		StartUpPhase startUpPhase =  new StartUpPhase();
		gamePhaseController.setCardStack(startUpPhase.assignCardToCountry(riskMapRW));

		// Assign armies to player
		player.assignArmiesToPlayers(currentGamePlayerList);
		// Assign country to players
		startUpPhase.assignCountryToPlayer(riskMapRW, currentGamePlayerList);
		System.out.println("Armies assigned");
		for(Player p: currentGamePlayerList){
			while(p.getArmyCount() > 0) {
				p.automaticAssignPlayerArmiesToCountry(p);
			}
			gamePhaseController.getGamePlayerList().add(p);
		}
		System.out.println("Assign armies to countries of the players, startup phase complete");

		while (numberOfTurnsToPlay > 0){
			Iterator<Player> playerListIterator = currentGamePlayerList.iterator();
			while(playerListIterator.hasNext()){

				// Setting current player
				Player.setPlayerPlaying(playerListIterator.next());
				card.automateCardWindow(Player.currentPlayer);
				List<Card> playerOwnedCards = Player.currentPlayer.getListOfCards();

				// Check, if cards can be exchanged or not
				if(playerOwnedCards != null){
					List<Card> cards = card.generateValidCardCombination(playerOwnedCards);
					if (cards != null && cards.size() >= 3) {
						card.cardsToTrade(cards);
						card.setCardsToTrade(playerOwnedCards);
						gamePhaseController.exchangeCards(card);
					}
				}
				ObservableList<Country> observableListReinforcementPhase = FXCollections.observableArrayList(Player.currentPlayer.getMyCountries());
				player.noOfReinforcementArmies(Player.currentPlayer);

				if(Player.currentPlayer.getArmyCount() > 0){
					System.out.println(" Reinforcement phase started for player " + Player.currentPlayer.getName());
					Player.currentPlayer.reinforcementPhase(observableListReinforcementPhase, null, gamePhaseController.getGamePlayerList());
					System.out.println(" Reinforcement phase completed for player " + Player.currentPlayer.getName());
				}
				// Reinforcement phase ended

				// Attack phase
				System.out.println("Attack phase started");

				ListView<Country> listViewOfCountries = new ListView<Country>(FXCollections.observableArrayList(Player.currentPlayer.getMyCountries()));
				while(Player.currentPlayer.playerCanAttack(listViewOfCountries)){
					Player.currentPlayer.getPlayerBehaviour().attackPhase(listViewOfCountries, null,  Player.currentPlayer);

					// Allocate cards to player if player won any country
					if(Player.currentPlayer.getCountryWon() > 0){
						gamePhaseController.setPlayerPlaying(Player.currentPlayer);
						gamePhaseController.allocateCardToPlayer();
					}

					List<Player> lostPlayerList = player.checkPlayerLost(currentGamePlayerList);

					if(!lostPlayerList.isEmpty()){

						for(Player p: lostPlayerList){
							System.out.println(p.getName() + " lost the game");
							currentGamePlayerList.remove(p);
							playerListIterator = currentGamePlayerList.iterator();
						}
					}

					winner = player.checkPlayerWon(currentGamePlayerList);
					if(winner != null){
						HashMap<Player, Integer> winnerMap = new HashMap<Player, Integer>();
						winnerMap.put(winner, gameCount);
						System.out.println(winner.getName() + "won the game");
						return winnerMap;
					}

				}
				System.out.println("Attack phase ended");

				System.out.println("Fortification started");
				ListView<Country> listViewOfCountriesForFortification = new ListView<Country>(FXCollections.observableArrayList(Player.currentPlayer.getMyCountries()));
				if(player.isFortificationPhaseValid(riskMapRW, Player.currentPlayer)){
					Player.currentPlayer.getPlayerBehaviour().fortificationPhase(listViewOfCountriesForFortification, null, Player.currentPlayer );
				}
				else {
					System.out.println("No fortification move possible");
				}
				System.out.println("Fortification move finished");


			}
			numberOfTurnsToPlay--;
		}
		HashMap<Player, Integer> winnerMap = new HashMap<Player, Integer>();
		winnerMap.put(null, gameCount);
		return winnerMap;
	}
}
