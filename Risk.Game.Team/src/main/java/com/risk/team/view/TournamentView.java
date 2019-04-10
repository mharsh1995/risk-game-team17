package com.risk.team.view;

import javafx.event.ActionEvent;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

import com.risk.team.controller.RiskTournamentController;
/**
 * This class provides the view for the user showing game screen..
 * 
 * @author Jenny Pujara
 * @author Dhaval Desai
 * 
 * @version 1.0.0
 */
public class TournamentView  implements EventHandler<ActionEvent> {

	/** This method is overridden to create a scene at UI end.
	 * @see javafx.event.EventHandler#handle(javafx.event.Event)
	 */
	@Override
	public void handle(ActionEvent event) {

		final Stage tournamentStage = new Stage();
		RiskTournamentController tournamentController =  new RiskTournamentController();
		tournamentStage.initModality(Modality.APPLICATION_MODAL);

		tournamentStage.setTitle("Tournament");

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/TournamentView.fxml"));
		loader.setController(tournamentController);

		Parent root = null;
		try {
			root = loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

		Scene scene = new Scene(root);
		tournamentStage.setScene(scene);
		tournamentStage.show();
	}
}
