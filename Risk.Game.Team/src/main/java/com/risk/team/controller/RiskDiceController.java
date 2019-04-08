package com.risk.team.controller;

import com.risk.team.model.Country;
import com.risk.team.model.Dice;
import com.risk.team.services.Util.GameUpdateWindow;
import com.risk.team.strategy.Human;
import com.risk.team.strategy.PlayerBehavior;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Observable;
import java.util.ResourceBundle;

/**
 * RiskDiceController class contains the methods used in dice view,
 * load attack screen, move armies, cancel move, cancel throw,
 * continue Dice Roll, attack dice value, defence dice value
 * and start dice roll.
 *
 * @author Kartika Patil
 */
public class RiskDiceController extends Observable implements Initializable,Serializable {

	/**
	 * Label variable for attacking player
	 */
	@FXML
	private Label attackingPlayer;

	/**
	 * Label variable for attacking Country
	 */
	@FXML
	private Label attackingCountry;

	/**
	 * Label variable for attacking armies
	 */
	@FXML
	private Label attackingArmies;

	/**
	 * Label variable for defending player
	 */
	@FXML
	private Label defendingPlayer;

	/**
	 * Label variable for defending country
	 */
	@FXML
	private Label defendingCountry;

	/**
	 * Label variable for defending armies
	 */
	@FXML
	private Label defendingArmies;

	/**
	 * Label variable for winner name
	 */
	@FXML
	private Label winnerName;

	/**
	 * CheckBox variable for dice 1 attacker
	 */
	@FXML
	private CheckBox dice1Attacker;

	/**
	 * CheckBox variable for dice 2 attacker
	 */
	@FXML
	private CheckBox dice2Attacker;

	/**
	 * CheckBox variable for dice 3 attacker
	 */
	@FXML
	private CheckBox dice3Attacker;

	/**
	 * CheckBox variable for dice 1 defender
	 */
	@FXML
	private CheckBox dice1Defender;

	/**
	 * CheckBox variable for dice 2 defender
	 */
	@FXML
	private CheckBox dice2Defender;

	/**
	 * Button variable for start roll
	 */
	@FXML
	private Button startRoll;

	/**
	 * Button variable for cancel throw
	 */
	@FXML
	private Button cancelThrow;

	/**
	 * Button variable for continue roll
	 */
	@FXML
	private Button continueRoll;

	/**
	 * Pane variable for after attack view
	 */
	@FXML
	private Pane afterAttackView;

	/**
	 * Button variable for move armies
	 */
	@FXML
	private Button moveArmies;

	/**
	 * Button variable for cancel move
	 */
	@FXML
	private Button cancelMove;

	/**
	 * The @moveAllArmies button.
	 */
	@FXML
	private Button moveAllArmies;

	/**
	 * TextField variable for number of armies to move
	 */
	@FXML
	private TextField numberOfArmiesToMove;

	/**
	 * Object for Dice class
	 */
	private Dice dice;

	/**
	 * Object for PlayerBehavior class
	 */
	private PlayerBehavior playerBehavior;

	/**
	 * RiskDiceController Constructor
	 *
	 * @param dice Dice object
	 * @param playerBehavior PlayerBehavior Object
	 * @param gamePhaseController GamePhaseController object
	 */
	public RiskDiceController(Dice dice, PlayerBehavior playerBehavior, GamePhaseController gamePhaseController) {
		this.dice = dice;
		this.playerBehavior = playerBehavior;
		this.addObserver(gamePhaseController);
	}

	/**
	 * RiskDiceController Constructor
	 *
	 * @param dice Dice object
	 * @param playerBehavior PlayerBehavior object
	 */
	public RiskDiceController(Dice dice, PlayerBehavior playerBehavior) {
		this.dice = dice;
		this.playerBehavior = playerBehavior;
	}

	/**
	 * Method to call load attack screen and dice view
	 *
	 * @param location  URL
	 * @param resources ResourceBundle
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		loadAttackScreen();
		diceView();

	}

	/**
	 * Method to automate dice roll
	 */
	public void automateDiceRoll() {
		automaticInitialization();
		loadAttackScreen();
		diceView();
		if (!(playerBehavior instanceof Human)) {
			autoRollDice();
		}

	}

	/**
	 * Method to auto role dice
	 */
	private void autoRollDice() {
		GameUpdateWindow.selectVisibleDice(dice1Attacker, dice2Attacker, dice3Attacker, dice1Defender, dice2Defender);

		startRoll(null);
		if (!continueRoll.isDisabled() && !cancelThrow.isDisabled()) {
			continueDiceRoll(null);
		} else if (continueRoll.isDisabled() && !cancelThrow.isDisabled()) {
			dice.cancelDiceThrow();
		} else if (afterAttackView.isVisible()) {
			dice.moveAllArmies();
		}
	}

	/**
	 * Method to load Attack screen
	 */
	public void loadAttackScreen() {
		// TODO Auto-generated method stub

		Country countryAttacking = dice.getAttackingCountry();
		attackingPlayer.setText(countryAttacking.getPlayer().getName());
		attackingCountry.setText(countryAttacking.getName());
		attackingArmies.setText("Armies: " + countryAttacking.getNoOfArmies());

		Country countryDefending = dice.getDefendingCountry();
		defendingPlayer.setText(countryDefending.getPlayer().getName());
		defendingCountry.setText(countryDefending.getName());
		defendingArmies.setText("Armies: " + countryDefending.getNoOfArmies());

		winnerName.setVisible(false);
		winnerName.setText("");

		GameUpdateWindow.unCheckBoxes(dice1Attacker, dice2Attacker, dice3Attacker, dice1Defender, dice2Defender);
		GameUpdateWindow.enableButtonControl(startRoll, continueRoll);
		GameUpdateWindow.disableButtonControl(winnerName);
		GameUpdateWindow.disablePane(afterAttackView);
	}

	/**
	 * Method for dice view
	 */
	public void diceView() {
		if (dice.getAttackingCountry().getNoOfArmies() >= 4) {
			GameUpdateWindow.showCheckBox(dice1Attacker, dice2Attacker, dice3Attacker);
		} else if (dice.getAttackingCountry().getNoOfArmies() == 3) {
			GameUpdateWindow.showCheckBox(dice1Attacker, dice2Attacker);
			GameUpdateWindow.hideButtonControl(dice3Attacker);
		} else if (dice.getAttackingCountry().getNoOfArmies() == 2) {
			GameUpdateWindow.showCheckBox(dice1Attacker);
			GameUpdateWindow.hideButtonControl(dice2Attacker, dice3Attacker);
		}
		if (dice.getDefendingCountry().getNoOfArmies() >= 2) {
			GameUpdateWindow.showCheckBox(dice1Defender, dice2Defender);
		} else if (dice.getDefendingCountry().getNoOfArmies() == 1) {
			GameUpdateWindow.showCheckBox(dice1Defender);
			GameUpdateWindow.hideButtonControl(dice2Defender);
		}
	}

	/**
	 * Method to move armies
	 *
	 * @param event ActionEvent
	 */
	@FXML
	private void moveArmies(ActionEvent event) {

		String getText = numberOfArmiesToMove.getText();

		if (getText.length() == 0) {
			GameUpdateWindow.popUpWindow("Armies Alert", " Title", "Please enter a valid number to move armies.");
			return;
		} else {
			int numberOfArmies = Integer.valueOf(getText.trim());
			dice.moveArmies(numberOfArmies, winnerName, moveArmies);
		}
	}

	/**
	 * Move all armies
	 *
	 * @param event action event
	 */
	@FXML
	private void moveAllArmies(ActionEvent event) {
		dice.moveAllArmies();
		GameUpdateWindow.exitWindow(moveAllArmies);
	}

	/**
	 * Method to cancel move
	 *
	 * @param event ActionEvent
	 */
	@FXML
	private void cancelMove(ActionEvent event) {
		dice.skipMoveArmy();
		GameUpdateWindow.exitWindow(cancelMove);
	}

	/**
	 * Method to cancel throw
	 *
	 * @param event ActionEvent
	 */
	@FXML
	private void cancelThrow(ActionEvent event) {
		dice.cancelDiceThrow();
		GameUpdateWindow.exitWindow(cancelThrow);
	}

	/**
	 * @param event Action event
	 */
	@FXML
	private void allOut(ActionEvent event) {
		dice.setAttackerDiceList(new ArrayList<>());
		dice.setDefenderDiceList(new ArrayList<>());
		loadAttackScreen();


		Country countryAttacking = dice.getAttackingCountry();
		Country defendingCountry = dice.getDefendingCountry();
		ArrayList<String> diceResult = new ArrayList<>();

		int bufferAttackingArmies = countryAttacking.getNoOfArmies();
		int bufferDefendingArmies = defendingCountry.getNoOfArmies();

		boolean flagAttack = false;
		boolean flagDefender = false;

		while (dice.getAttackingCountry().getNoOfArmies() > 1 && dice.getDefendingCountry().getNoOfArmies() > 0) {
			// Refreshing a the dices
			dice.getAttackerDiceList().clear();
			dice.getDefenderDiceList().clear();
			if (countryAttacking.getNoOfArmies() >= 4) {

				GameUpdateWindow.showCheckBox(dice1Attacker, dice2Attacker, dice3Attacker);
				GameUpdateWindow.checkCheckBoxes(dice1Attacker, dice2Attacker, dice3Attacker);
				attackerDiceValue(dice1Attacker, dice2Attacker, dice3Attacker);

			} else if (countryAttacking.getNoOfArmies() == 3) {

				GameUpdateWindow.showCheckBox(dice1Attacker, dice2Attacker);
				GameUpdateWindow.hideButtonControl(dice3Attacker);
				GameUpdateWindow.checkCheckBoxes(dice1Attacker, dice2Attacker);
				attackerDiceValue(dice1Attacker, dice2Attacker);

			} else if (countryAttacking.getNoOfArmies() == 2) {

				GameUpdateWindow.showCheckBox(dice1Attacker);
				GameUpdateWindow.checkCheckBoxes(dice1Attacker);
				GameUpdateWindow.hideButtonControl(dice2Attacker, dice3Attacker);
				attackerDiceValue(dice1Attacker);
			}

			if (defendingCountry.getNoOfArmies() >= 2) {

				GameUpdateWindow.showCheckBox(dice1Defender, dice2Defender);
				GameUpdateWindow.checkCheckBoxes(dice1Defender, dice2Defender);
				defenderDiceValue(dice1Defender, dice2Defender);

			} else if (defendingCountry.getNoOfArmies() == 1) {

				GameUpdateWindow.showCheckBox(dice1Defender);
				GameUpdateWindow.checkCheckBoxes(dice1Defender);
				GameUpdateWindow.hideButtonControl(dice2Defender);
				defenderDiceValue(dice1Defender);
			}
			diceResult = dice.getDicePlayResult();


			if (countryAttacking.getNoOfArmies() != bufferAttackingArmies) {
				flagAttack = true;
				System.out.println(countryAttacking.getPlayer().getName() + " lost: " + (bufferAttackingArmies - countryAttacking.getNoOfArmies()) + " armies\n");
				setChanged();
				notifyObservers(countryAttacking.getPlayer().getName() + " lost: " + (bufferAttackingArmies - countryAttacking.getNoOfArmies()) + " armies\n");
			}
			if (defendingCountry.getNoOfArmies() != bufferDefendingArmies) {
				flagDefender = true;
				System.out.println(defendingCountry.getPlayer().getName() + " lost: " + (bufferDefendingArmies - defendingCountry.getNoOfArmies()) + " armies\n");
				setChanged();
				notifyObservers(defendingCountry.getPlayer().getName() + " lost: " + (bufferDefendingArmies - defendingCountry.getNoOfArmies()) + " armies\n");
			}

			defendingArmies.setText("Armies: " + String.valueOf(defendingCountry.getNoOfArmies()));
			attackingArmies.setText("Armies: " + String.valueOf(countryAttacking.getNoOfArmies()));
		}

		if (defendingCountry.getNoOfArmies() <= 0) {
			System.out.println(countryAttacking.getPlayer().getName() + " won " + defendingCountry.getName() + " country.\n");
			diceResult.add(countryAttacking.getPlayer().getName() + " won " + defendingCountry.getName() + " country.\n");
			setChanged();
			notifyObservers(countryAttacking.getPlayer().getName() + " won " + defendingCountry.getName() + " country.\n");
			dice.setCountriesWonCount(dice.getCountriesWonCount() + 1);
			GameUpdateWindow.enablePane(afterAttackView);
			GameUpdateWindow.hideButtonControl(startRoll, continueRoll);
		} else if (countryAttacking.getNoOfArmies() < 2) {
			System.out.println(countryAttacking.getPlayer().getName() + " lost the match.\n");
			setChanged();
			notifyObservers(countryAttacking.getPlayer().getName() + " lost the match.\n");
			diceResult.add(countryAttacking.getPlayer().getName() + " lost the match.\n");
			GameUpdateWindow.disableButtonControl(startRoll, continueRoll);
		} else {
			GameUpdateWindow.disableButtonControl(startRoll);
		}

		if (flagAttack && flagDefender) {
			winnerName.setText(countryAttacking.getPlayer().getName() + " lost: " + (bufferAttackingArmies - countryAttacking.getNoOfArmies()) + " armies\n" + " & " +
					defendingCountry.getPlayer().getName() + " lost: " + (bufferDefendingArmies - defendingCountry.getNoOfArmies()) + " armies\n"
					);
		} else if (flagAttack) {
			winnerName.setText(countryAttacking.getPlayer().getName() + " lost: " + (bufferAttackingArmies - countryAttacking.getNoOfArmies()) + " armies\n");
		} else if (flagDefender) {
			winnerName.setText(defendingCountry.getPlayer().getName() + " lost: " + (bufferDefendingArmies - defendingCountry.getNoOfArmies()) + " armies\n");
		}
		winnerName.setVisible(true);

		diceView();
		if (!(playerBehavior instanceof Human)) {
			autoRollDice();
		}
	}

	/**
	 * Method to continue dice roll
	 *
	 * @param event ActionEvent
	 */
	@FXML
	private void continueDiceRoll(ActionEvent event) {

		dice.setAttackerDiceList(new ArrayList<>());
		dice.setDefenderDiceList(new ArrayList<>());
		loadAttackScreen();

		diceView();
		if (!(playerBehavior instanceof Human)) {
			autoRollDice();
		}
	}

	/**
	 * Method to attacker dice value
	 *
	 * @param allCheckBoxes CheckBox
	 */
	public void attackerDiceValue(CheckBox... allCheckBoxes) {
		for (CheckBox checkBox : allCheckBoxes) {
			if (checkBox.isSelected()) {
				int diceValue = dice.generateRandomNumber();
				checkBox.setText(String.valueOf(diceValue));
				dice.getAttackerDiceList().add(diceValue);
			}
		}
	}

	/**
	 * Method to defender dice value
	 *
	 * @param allCheckBoxes CheckBox
	 */
	public void defenderDiceValue(CheckBox... allCheckBoxes) {
		for (CheckBox checkBox : allCheckBoxes) {
			if (checkBox.isSelected()) {
				int diceValue = dice.generateRandomNumber();
				checkBox.setText(String.valueOf(diceValue));
				dice.getDefenderDiceList().add(diceValue);
			}
		}
	}

	/**
	 * Method to start roll
	 *
	 * @param event ActionEvent
	 */
	@FXML
	public void startRoll(ActionEvent event) {
		if (!dice1Attacker.isSelected() && !dice2Attacker.isSelected() && !dice3Attacker.isSelected()) {
			GameUpdateWindow.popUpWindow("Head", "Message", "At least one attacking dice should be selected");
			return;
		} else if (!dice1Defender.isSelected() && !dice2Defender.isSelected()) {
			GameUpdateWindow.popUpWindow("Head", "Message", "At least one defender dice should be selected");
			return;
		}
		attackerDiceValue(dice1Attacker, dice2Attacker, dice3Attacker);
		defenderDiceValue(dice1Defender, dice2Defender);

		Country countryAttacking = dice.getAttackingCountry();
		Country defendingCountry = dice.getDefendingCountry();

		int bufferAttackingArmies = countryAttacking.getNoOfArmies();
		int bufferDefendingArmies = defendingCountry.getNoOfArmies();

		ArrayList<String> diceResult = dice.getDicePlayResult();

		boolean flagAttack = false;
		boolean flagDefender = false;

		if (countryAttacking.getNoOfArmies() != bufferAttackingArmies) {
			flagAttack = true;
			System.out.println(countryAttacking.getPlayer().getName() + " lost: " + (bufferAttackingArmies - countryAttacking.getNoOfArmies()) + " armies\n");
			setChanged();
			notifyObservers(countryAttacking.getPlayer().getName() + " lost: " + (bufferAttackingArmies - countryAttacking.getNoOfArmies()) + " armies\n");
		}
		if (defendingCountry.getNoOfArmies() != bufferDefendingArmies) {
			flagDefender = true;
			System.out.println(defendingCountry.getPlayer().getName() + " lost: " + (bufferDefendingArmies - defendingCountry.getNoOfArmies()) + " armies\n");
			setChanged();
			notifyObservers(defendingCountry.getPlayer().getName() + " lost: " + (bufferDefendingArmies - defendingCountry.getNoOfArmies()) + " armies\n");
		}

		if (defendingCountry.getNoOfArmies() <= 0) {
			System.out.println(countryAttacking.getPlayer().getName() + " won " + defendingCountry.getName() + " Country\n");
			setChanged();
			notifyObservers(countryAttacking.getPlayer().getName() + " won " + defendingCountry.getName() + " Country\n");
			diceResult.add(countryAttacking.getPlayer().getName() + " won " + defendingCountry.getName() + " Country");
			dice.setCountriesWonCount(dice.getCountriesWonCount() + 1);
			GameUpdateWindow.enablePane(afterAttackView);
			GameUpdateWindow.disableButtonControl(startRoll, continueRoll, cancelThrow);
			GameUpdateWindow.hideButtonControl(startRoll, continueRoll, cancelThrow);
		} else if (countryAttacking.getNoOfArmies() < 2) {
			System.out.println(defendingCountry.getPlayer().getName() + " lost: " + (bufferDefendingArmies - defendingCountry.getNoOfArmies()) + " armies\n");
			System.out.println(countryAttacking.getPlayer().getName() + " lost the match");
			setChanged();
			notifyObservers(defendingCountry.getPlayer().getName() + " lost: " + (bufferDefendingArmies - defendingCountry.getNoOfArmies()) + " armies\n");
			diceResult.add(countryAttacking.getPlayer().getName() + " lost the match");
			GameUpdateWindow.disableButtonControl(startRoll, continueRoll);
			GameUpdateWindow.enableButtonControl(cancelThrow);
			GameUpdateWindow.disablePane(afterAttackView);
		} else {
			GameUpdateWindow.disablePane(afterAttackView);
			GameUpdateWindow.disableButtonControl(startRoll);
			GameUpdateWindow.enableButtonControl(continueRoll, cancelThrow);

		}
		defendingArmies.setText("Armies: " + String.valueOf(defendingCountry.getNoOfArmies()));
		attackingArmies.setText("Armies: " + String.valueOf(countryAttacking.getNoOfArmies()));
		diceResult.clear();
		if (flagAttack && flagDefender) {
			winnerName.setText(countryAttacking.getPlayer().getName() + " lost: " + (bufferAttackingArmies - countryAttacking.getNoOfArmies()) + " armies\n" + " & " +
					defendingCountry.getPlayer().getName() + " lost: " + (bufferDefendingArmies - defendingCountry.getNoOfArmies()) + " armies\n"
					);
		} else if (flagAttack) {
			winnerName.setText(countryAttacking.getPlayer().getName() + " lost: " + (bufferAttackingArmies - countryAttacking.getNoOfArmies()) + " armies\n");
		} else if (flagDefender) {
			winnerName.setText(defendingCountry.getPlayer().getName() + " lost: " + (bufferDefendingArmies - defendingCountry.getNoOfArmies()) + " armies\n");
		}
		if (!diceResult.isEmpty())
			winnerName.setText(diceResult.get(diceResult.size() - 1));
		winnerName.setVisible(true);
	}

	/**
	 * Method to automate setup of RiskDiceController
	 */
	public void automaticInitialization() {

		attackingPlayer = new Label();

		attackingCountry = new Label();

		attackingArmies = new Label();

		defendingPlayer = new Label();

		defendingCountry = new Label();

		defendingArmies = new Label();

		winnerName = new Label();

		dice1Attacker = new CheckBox();

		dice2Attacker = new CheckBox();

		dice3Attacker = new CheckBox();

		dice1Defender = new CheckBox();

		dice2Defender = new CheckBox();

		startRoll = new Button();

		cancelThrow = new Button();

		continueRoll = new Button();

		afterAttackView = new Pane();

		moveArmies = new Button();

		cancelMove = new Button();

		numberOfArmiesToMove = new TextField();
	}

}