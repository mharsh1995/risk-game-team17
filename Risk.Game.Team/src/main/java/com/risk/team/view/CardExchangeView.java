package com.risk.team.view;

import com.risk.team.controller.RiskCardController;
import com.risk.team.model.Card;
import com.risk.team.model.Player;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * 
 * This class contains methods for setting up stage to view card.
 * 
 * @author Kartika Patil
 * @author yashgolwala
 */
public class CardExchangeView {

	/**
	 * This method is used to create a scene at UI end and opens a window for dice.
	 * 
	 * @param currentPlayer object of Player having current playing player
	 * @param card object of Card
	 */
	public static void openCardWindow(Player currentPlayer, Card card) {
		final Stage newCardStage = new Stage();
		newCardStage.setTitle("Player Card Window");
		RiskCardController cardController = new RiskCardController(currentPlayer, card);
		FXMLLoader loader = new FXMLLoader(CardExchangeView.class.getClassLoader().getResource("Cards.fxml"));
		loader.setController(cardController);
		Parent root = null;
		try {
			root = (Parent) loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Scene scene = new Scene(root);
		newCardStage.setScene(scene);
		newCardStage.show();
	}
}
