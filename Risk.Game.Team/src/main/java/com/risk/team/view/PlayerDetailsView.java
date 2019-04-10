package com.risk.team.view;

import com.risk.team.controller.PlayerDetailsController;
import com.risk.team.services.RiskMapRW;
import com.risk.team.services.RiskMapVerify;
import com.risk.team.services.util.GameUpdateWindow;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

/**
 * Provides the view for the user showing details of the players
 *
 * @author Dhaval Desai
 * @author Jenny Pujara
 * 
 * @version 2.0.0
 */
public class PlayerDetailsView implements EventHandler<ActionEvent> {

	/*
	 * (non-Javadoc)
	 * This method is overridden to create a scene at UI end.
	 * @see javafx.event.EventHandler#handle(javafx.event.Event)
	 */
	@Override
	public void handle(ActionEvent event) {

		File selectedFile = GameUpdateWindow.showFileChooser();
		String fileName = selectedFile.getAbsolutePath();
		System.out.println("File location: " + fileName);
		RiskMapVerify mapValidate = new RiskMapVerify();
		RiskMapRW mapObj=null;
		if (mapValidate.validateMapFile(fileName)) {
			mapObj = new RiskMapRW(mapValidate);
		}
		else{
			GameUpdateWindow.popUpWindow("","Error","Invalid Map file.");
			return;
		}

		final Stage playerDetailsStage = new Stage();

		PlayerDetailsController playerDetailsController = new PlayerDetailsController(mapObj);
		playerDetailsStage.initModality(Modality.APPLICATION_MODAL);

		playerDetailsStage.setTitle("Player Details");

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/PlayerDetails.fxml"));
		loader.setController(playerDetailsController);
		Parent root = null;
		try {
			root = loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

		Scene scene = new Scene(root);
		playerDetailsStage.setScene(scene);
		playerDetailsStage.show();
	}

}
