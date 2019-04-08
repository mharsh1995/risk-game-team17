package com.risk.team.view;

import java.io.IOException;

import com.risk.team.controller.GamePhaseController;
import com.risk.team.controller.RiskDiceController;
import com.risk.team.model.Dice;
import com.risk.team.model.Player;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This class will setup attack window
 *
 * @author Kartika Patil
 */
public class DiceView {

    /**
     * This method create a scene at UI end and opens a window for dice.
     * 
     * @param dice object of Dice
     * @param player object of Player
     * @param gamePhaseController object of GamePlayController
     */
    public static void openDiceWindow(Dice dice, Player player, GamePhaseController gamePhaseController) {
        final Stage diceStage = new Stage();
        diceStage.setTitle("Attack Window");

        RiskDiceController diceController = new RiskDiceController(dice, player.getPlayerBehaviour(), gamePhaseController);

        FXMLLoader loader = new FXMLLoader(DiceView.class.getClassLoader().getResource("DiceView.fxml"));
        loader.setController(diceController);

        Parent root = null;
        try {
            root = (Parent) loader.load();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Scene scene = new Scene(root);
        diceStage.setScene(scene);
        diceStage.show();
    }
}
