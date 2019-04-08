package com.risk.team.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import com.risk.team.controller.GamePhaseController;

/**
 * 
 * This class contains methods for setting up stage to view card.
 * 
 * @author yashgolwala
 * @author Harsh Mehta
 */
public class LoadGame {

	/**
     * This method is is used to load the game.
     */
	public static void openLoadGame() {

		GamePhaseController controller = new GamePhaseController().loadGame();

		final Stage gamePlayStage = new Stage();
		gamePlayStage.setTitle("Game Screen");

		FXMLLoader loader = new FXMLLoader(LoadGame.class.getClassLoader().getResource("MapSelectorLayout.fxml"));
		loader.setController(controller);
		Parent root = null;
		try {
			root = (Parent) loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Scene scene = new Scene(root);
		gamePlayStage.setScene(scene);
		gamePlayStage.show();

		final Stage terminalStage = new Stage();
		terminalStage.setTitle("Terminal Window");

		FXMLLoader fxmlLoader = new FXMLLoader(LoadGame.class.getClassLoader().getResource("TerminalWindow.fxml"));
		fxmlLoader.setController(controller);

		Parent myRoot = null;
		try {
			myRoot = (Parent) fxmlLoader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Scene myScene = new Scene(myRoot);
		terminalStage.setScene(myScene);
		terminalStage.show();

	}

}
