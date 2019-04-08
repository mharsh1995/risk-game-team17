package com.risk.team.controller;

import com.risk.team.model.IPlayerType;
import com.risk.team.model.Player;
import com.risk.team.model.TournamentModel;
import com.risk.team.services.RiskMapRW;
import com.risk.team.services.Util.GameUpdateWindow;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.File;
import java.net.URL;
import java.util.*;
import java.util.regex.Pattern;

/**
 * This class provides all the methods to control various activities during the game play,
 * implements Observer.
 *
 * @author Kartika Patil
 */
public class RiskTournamentController extends Observable implements Initializable {

	/** Object for TournamentModel class */
	private TournamentModel tournamentModel;

	/** Terminal window text area */
	private TextArea terminalWindow;

	/**
	 * Method to get Terminal Window
	 *
	 * @return terminalWindow
	 */
	public TextArea getTerminalWindow() {

		return terminalWindow;
	}

	/**
	 * TextField for number of games
	 */
	@FXML
	private TextField numberOfGames;

	/**
	 * TextField for number of turns
	 */
	@FXML
	private TextField numberOfTurns;

	/**
	 * TextField for number of maps
	 */
	@FXML
	private TextField numberOfMaps;

	/**
	 * TextField for number of players
	 */
	@FXML
	private TextField numberOfPlayers;

	/**
	 * ChoiceBox to display player1
	 */
	@FXML
	private ChoiceBox<String> player1;

	/**
	 * ChoiceBox to display player2
	 */
	@FXML
	private ChoiceBox<String> player2;

	/**
	 * ChoiceBox to display player3
	 */
	@FXML
	private ChoiceBox<String> player3;

	/** ChoiceBox to display player4 */
	@FXML
	private ChoiceBox<String> player4;

	/** Button for map1 */
	@FXML
	private Button map1;

	/** Button for map2 */
	@FXML
	private Button map2;

	/** Button for map3 */
	@FXML
	private Button map3;

	/** Button for map4 */
	@FXML
	private Button map4;

	/** Button for map5 */
	@FXML
	private Button map5;

	/** Button to start game */
	@FXML
	private Button start;

	/** Button to exit game */
	@FXML
	private Button exit;

	/** Button to register game */
	@FXML
	private Button register;

	/** Button to show Players */
	@FXML
	private Button showPlayers;

	/** Text to show error */
	@FXML
	private Label errorMessage;

	/** Variable to show number of turns to play */
	private int numberOfTurnsToPlay;

	/** Variable to show number of maps to play */
	private int numberOfMapsToPlay;

	/** List to hold list of maps */
	private List<RiskMapRW> mapList;

	/** List to hold list of players */
	private List<Player> playerList;

	/** List of choices to choose player */
	private List<ChoiceBox> playerDropDownList = new ArrayList<>();

	/** Variable to show number of games to play */
	private int numberOfGamesToPlay;

	@FXML
	private TextArea textArea;

	/**
	 * Getter method
	 * 
	 * @return numberOfTurnsToPlay number of turns to play
	 */
	public int getNumberOfTurnsToPlay() {
		return numberOfTurnsToPlay;
	}
	/**
	 * Setter method
	 * 
	 * @param numberOfTurnsToPlay number of turns to play
	 */
	public void setNumberOfTurnsToPlay(int numberOfTurnsToPlay) {
		this.numberOfTurnsToPlay = numberOfTurnsToPlay;
	}

	/**
	 * getter method
	 * 
	 * @return numberOfGamesToPlay number of games to play
	 */
	public int getNumberOfGamesToPlay() {
		return numberOfGamesToPlay;
	}

	/**
	 * Setter method
	 * 
	 * @param numberOfGamesToPlay number of games to play
	 */
	public void setNumberOfGamesToPlay(int numberOfGamesToPlay) {
		this.numberOfGamesToPlay = numberOfGamesToPlay;
	}

	/**
	 * Getter method
	 * 
	 * @return numberOfPlayersPlaying number of players playing
	 */
	public int getNumberOfPlayersPlaying() {
		return numberOfPlayersPlaying;
	}

	/**
	 * Setter method
	 * 
	 * @param numberOfPlayersPlaying number of players playing
	 */
	public void setNumberOfPlayersPlaying(int numberOfPlayersPlaying) {
		this.numberOfPlayersPlaying = numberOfPlayersPlaying;
	}

	/**
	 * variable for number of players playing
	 */
	private int numberOfPlayersPlaying;

	/**
	 *  Setter method
	 *  
	 * @param numberOfMapsToPlay number of maps to display
	 */
	public void setNumberOfMapsToPlay(int numberOfMapsToPlay) {
		this.numberOfMapsToPlay = numberOfMapsToPlay;
	}

	/**
	 * Constructor of TournamentController class
	 */
	public RiskTournamentController() {
		mapList = new ArrayList<>();
		playerList = new ArrayList<>();
	}

	/**
	 * (non-Javadoc
	 *
	 * @see javafx.fxml.Initializable#initialize(java.net.URL,
	 * java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		GameUpdateWindow.disableButtonControl(player1, player2, player3, player4, map1, map2, map3, map4, map5, start);
		populatePlayerCheckBox();
		tournamentModel = new TournamentModel();
	}

	/**
	 * Method to register number of maps, number of turns and games
	 *
	 * @param event Action event
	 * @throws NumberFormatException NumberFormatException
	 */
	@FXML
	private void register(ActionEvent event) throws NumberFormatException {
		System.out.println("In register");
		try {
			int bufferNumberOfGames = Integer.parseInt(numberOfGames.getText().trim());
			int bufferNumberOfTurns = Integer.parseInt(numberOfTurns.getText().trim());
			int bufferNumberOfMaps = Integer.parseInt(numberOfMaps.getText().trim());

			setNumberOfGamesToPlay(bufferNumberOfGames);
			setNumberOfTurnsToPlay(bufferNumberOfTurns);
			setNumberOfMapsToPlay(bufferNumberOfMaps);

			if (numberOfGamesToPlay > 5 || numberOfGamesToPlay < 1) {
				GameUpdateWindow.popUpWindow("Games to be played should be between 1 to 5", "Problem in games to play", "You have entered a value less than 1 or greater than 5");
				GameUpdateWindow.disableButtonControl(start);
				return;
			}

			if (numberOfTurnsToPlay > 50 || numberOfTurnsToPlay < 10) {
				GameUpdateWindow.popUpWindow("Turns to be played should be between 10 to 50", "Problem in turns to play", "You have entered a value less than 10 or greater than 50");
				GameUpdateWindow.disableButtonControl(start);
				return;
			}

			if (numberOfMapsToPlay > 5 || numberOfMapsToPlay < 1) {
				GameUpdateWindow.popUpWindow("Maps to be played should be between 1 to 5", "Problem in maps to play", "You have entered a value less than 1 or greater than 5");
				GameUpdateWindow.disableButtonControl(start);
				return;
			}

			List<Button> mapButtonList = new ArrayList<Button>();
			mapButtonList.add(map1);
			mapButtonList.add(map2);
			mapButtonList.add(map3);
			mapButtonList.add(map4);
			mapButtonList.add(map5);

			int mapToEnable;

			for (mapToEnable = 0; mapToEnable < numberOfMapsToPlay; mapToEnable++) {
				GameUpdateWindow.enableButtonControl(mapButtonList.get(mapToEnable));
			}
			for (int i = mapToEnable; i < 5; i++) {
				GameUpdateWindow.disableButtonControl(mapButtonList.get(i));
			}

			if (numberOfGamesToPlay != 0 && numberOfTurnsToPlay != 0 && numberOfPlayersPlaying != 0 && numberOfTurnsToPlay != 0) {
				GameUpdateWindow.enableButtonControl(start);
				errorMessage.setText("Select players properly and then click start");
			}


		} catch (NumberFormatException exception) {
			GameUpdateWindow.popUpWindow("Please enter a number", "NumberFormatException", "You did not enter a number");
		}
	}

	/**
	 * Method to show player
	 *
	 * @param event	Action event
	 * @throws NumberFormatException NumberFormatException
	 */
	@FXML
	public void showPlayer(ActionEvent event) throws NumberFormatException {
		System.out.println("In Player");

		try {
			int bufferNumberOfPlayers = Integer.parseInt(numberOfPlayers.getText().trim());

			setNumberOfPlayersPlaying(bufferNumberOfPlayers);

			if (numberOfPlayersPlaying > 4 || numberOfPlayersPlaying < 1) {
				GameUpdateWindow.popUpWindow("Players playing should be between 1 to 4", "Problem in number of players", "You have entered a value less than 1 or greater than 4");
				GameUpdateWindow.disableButtonControl(start);
				return;
			}

			playerDropDownList.add(player1);
			playerDropDownList.add(player2);
			playerDropDownList.add(player3);
			playerDropDownList.add(player4);

			int playerToEnable;

			for (playerToEnable = 0; playerToEnable < numberOfPlayersPlaying; playerToEnable++) {
				GameUpdateWindow.enableButtonControl(playerDropDownList.get(playerToEnable));
			}
			for (int i = playerToEnable; i < 4; i++) {
				GameUpdateWindow.disableButtonControl(playerDropDownList.get(i));
			}

			if (numberOfGamesToPlay != 0 && numberOfMapsToPlay != 0 && numberOfPlayersPlaying != 0 && (numberOfTurnsToPlay >= 10 && numberOfTurnsToPlay <= 50)) {
				GameUpdateWindow.enableButtonControl(start);
				errorMessage.setText("Select players properly and then click start");
			}

		} catch (NumberFormatException e) {
			GameUpdateWindow.popUpWindow("Please enter a number", "NumberFormatException", "You did not enter a number");
		}


	}

	/**
	 * Method to upload map1
	 *
	 * @param event	Action event
	 */
	@FXML
	private void map1(ActionEvent event) {
		File selectedFile = tournamentModel.checkAndLoadMap(mapList);
		if (selectedFile == null) {
			map1.setText("Invalidmap selected");
			errorMessage.setText("Invalidmap selected");
			GameUpdateWindow.disableButtonControl(start);
			return;
		}
		map1.setText(selectedFile.getName());
	}

	/**
	 * Method to upload map2
	 *
	 * @param event	Action event
	 */
	@FXML
	private void map2(ActionEvent event) {
		File selectedFile = tournamentModel.checkAndLoadMap(mapList);
		if (selectedFile == null) {
			map2.setText("Invalidmap selected");
			errorMessage.setText("Invalidmap selected");
			GameUpdateWindow.disableButtonControl(start);
			return;
		}
		map2.setText(selectedFile.getName());
	}

	/**
	 * Method to upload map3
	 *
	 * @param event	Action event
	 */
	@FXML
	private void map3(ActionEvent event) {
		File selectedFile = tournamentModel.checkAndLoadMap(mapList);
		if (selectedFile == null) {
			map3.setText("Invalidmap selected");
			errorMessage.setText("Invalidmap selected");
			GameUpdateWindow.disableButtonControl(start);
			return;
		}
		map3.setText(selectedFile.getName());
	}

	/**
	 * Method to upload map4
	 *
	 * @param event	Action event
	 */
	@FXML
	private void map4(ActionEvent event) {
		File selectedFile = tournamentModel.checkAndLoadMap(mapList);
		if (selectedFile == null) {
			map4.setText("Invalidmap selected");
			errorMessage.setText("Invalidmap selected");
			GameUpdateWindow.disableButtonControl(start);
			return;
		}
		map4.setText(selectedFile.getName());
	}

	/**
	 * Method to upload map5
	 *
	 * @param event	Action event
	 */
	@FXML
	private void map5(ActionEvent event) {
		File selectedFile = tournamentModel.checkAndLoadMap(mapList);
		if (selectedFile == null) {
			map5.setText("Invalidmap selected");
			errorMessage.setText("Invalidmap selected");
			GameUpdateWindow.disableButtonControl(start);
			return;
		}
		map5.setText(selectedFile.getName());
	}

	/**
	 * Method to display checkbox for players
	 */
	public void populatePlayerCheckBox() {
		String playerTypes[] = {IPlayerType.AGGRESSIVE, IPlayerType.BENEVOLENT, IPlayerType.RANDOM, IPlayerType.CHEATER};
		player1.getItems().addAll(playerTypes);
		player2.getItems().addAll(playerTypes);
		player3.getItems().addAll(playerTypes);
		player4.getItems().addAll(playerTypes);

	}

	/**
	 * Method to start tournament
	 *
	 * @param event	Action event
	 */
	@FXML
	public void start(ActionEvent event) {

		HashMap<String, ArrayList<HashMap<Player, Integer>>> result = new HashMap<>();
		System.out.println("In start");

		playerList.clear();
		for (int i = 0; i < numberOfPlayersPlaying; i++) {
			playerList.add(new Player("Player " + (i + 1), (String) playerDropDownList.get(i).getValue()));
		}

		for (RiskMapRW mapObj : mapList) {
			playerList.clear();
			for (int i = 0; i < numberOfPlayersPlaying; i++) {
				playerList.add(new Player("Player " + (i + 1), (String) playerDropDownList.get(i).getValue()));
			}
			result.put(mapObj.getFileName(), new ArrayList<>());
			for (int gameCount = 1; gameCount <= numberOfGamesToPlay; gameCount++) {
				// Adding result
				playerList.clear();
				for (int i = 0; i < numberOfPlayersPlaying; i++) {
					playerList.add(new Player("Player " + (i + 1), (String) playerDropDownList.get(i).getValue()));
				}
				result.get(mapObj.getFileName()).add(tournamentModel.playGame(playerList, numberOfTurnsToPlay, gameCount, mapObj, textArea));
				System.out.println(gameCount);
			}
			System.out.println(result.toString());

			String text="";
			for (Map.Entry<String, ArrayList<HashMap<Player, Integer>>> mapEntry: result.entrySet() ) {
				String str = mapEntry.getKey();
				String separator = "\\";
				String[] mapSplit = str.replaceAll(Pattern.quote(separator), "\\\\").split("\\\\");

				text = text + "Map: " + mapSplit[mapSplit.length - 1]  + " -> \n";
				for(HashMap<Player, Integer> winnerHashMap: mapEntry.getValue()){
					for(Map.Entry<Player, Integer> winnerHashMapMapntry: winnerHashMap.entrySet()){
						if(winnerHashMapMapntry.getKey() != null){
							text = text + " Winner: " + winnerHashMapMapntry.getKey().getName() + " | Game Number " + winnerHashMapMapntry.getValue();
						}
						else {
							text = text + " Winner: Draw |" + " Game Number" + winnerHashMapMapntry.getValue();
						}
					}
					text = text + "\n\n";
				}
			}
			textArea.setText(text);
		}
	}

}
