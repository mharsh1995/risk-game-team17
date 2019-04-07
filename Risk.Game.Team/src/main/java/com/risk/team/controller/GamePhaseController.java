package com.risk.team.controller;

import com.risk.team.model.*;
import com.risk.team.services.RiskLaunchPhase;
import com.risk.team.services.RiskMapRW;
import com.risk.team.services.Util.GameUpdateWindow;
import com.risk.team.services.gameplay.RoundRobin;
import com.risk.team.services.saveload.ResourceManager;
import com.risk.team.strategy.Human;
import com.risk.team.view.CardExchangeView;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

/**
 * This class provides all the methods to control various activities during the game play,
 * implements Observer.
 *
 * @author Kartika Patil
 * @author yashgolwala
 * @author Harsh Mehta
 * @author Jenny Pujara
 * @author Dhaval Desai
 */
public class GamePhaseController implements Initializable, Observer, Externalizable {

	/** HashSet to storing adjacent countries */
    private static HashSet<Country> allAdjacentCountries = new HashSet<>();

    /** HashMap to store visited country */
    private static HashMap<Country, Boolean> visited = new HashMap<>();

    /** HashMap to store player names and types */
    private HashMap<String, String> playerNamesAndTypes;

    /**
     * roundRobin Object of RoundRobin Class
     */
    private RoundRobin roundRobin;

    /**
     * Object to load contents of the Map file
     */
    private RiskMapRW map;

    /**
     * Object to provide Setup Phase
     */
    private RiskLaunchPhase startUpPhase;

    /**
     * Card Object
     */
    private Card card;

    /**
     * Object to create barChart
     */
    @FXML
    private BarChart dominationBarChart;

    /**
     * WorldDomination Object
     */
    private PlayerWorldDomination worldDomination;

    /**
     * attack Button
     */
    @FXML
    private Button attack;

    /**
     * fortify Button
     */
    @FXML
    private Button fortify;

    /**
     * endTurn Button
     */
    @FXML
    private Button endTurn;

    /**
     * reinforcement Button
     */
    @FXML
    private Button reinforcement;

    /**
     * cards Button
     */
    @FXML
    private Button cards;

    /**
     * VerticalBox for Display
     */
    @FXML
    private VBox displayBox;

    /**
     * Selected Countries ListView provides scrollable list of items
     */
    @FXML
    private ListView<Country> selectedCountryList;

    /**
     * Adjacent Countries ListView provides scrollable list of items
     */
    @FXML
    private ListView<Country> adjacentCountryList;

    /**
     * Label of selected player
     */
    @FXML
    private Label playerChosen;

    /**
     * PhaseView Label
     */
    @FXML
    private Label phaseView;

    /**
     * TextArea to which current game information will be displayed
     */
    @FXML
    private TextArea terminalWindow;

    /**
     * placeArmy Button
     */
    @FXML
    private Button placeArmy;

    /**
     * saveGame Button
     */
    @FXML
    private Button saveGame;

    /**
     * variable to store current phase
     */
    private String phaseViewString;

    /**
     * variable to store terminal window data
     */
    private String gameDataString;

    /**
     * stores name of current player
     */
    private String playerData;

    /**
     * selected number of players who are supposed to play the Game
     */
    private int numberOfPlayersSelected;

    /**
     * variable specifying if game is started as saved game or as a normal game
     */
    private boolean isGameSaved = false;

    /**
     * Method to store player list
     * @return gamePlayerList
     */
    public ArrayList<Player> getGamePlayerList() {
        return gamePlayerList;
    }

    /**
     * Methoid for set player list of the game
     * @param gamePlayerList Sets the current player list
     */
    public void setGamePlayerList(ArrayList<Player> gamePlayerList) {
        this.gamePlayerList = gamePlayerList;
    }

    /**
     * List of Players
     */
    private ArrayList<Player> gamePlayerList;

    /**
     * The playerIterator.
     */
    private Iterator<Player> playerIterator;

    /**
     *
     * @return playerPlaying gets current player playing
     */
    public Player getPlayerPlaying() {
        return playerPlaying;
    }


    /**
     *
     * @param playerPlaying Sets current player playing
     */
    public void setPlayerPlaying(Player playerPlaying) {
        this.playerPlaying = playerPlaying;
    }

    /**
     * The Current Player whose playing
     */
    private Player playerPlaying;

    /**
     * Getter for Stack Card
     * @return cardStack Stack of cards
     */
    public Stack<Card> getCardStack() {
        return cardStack;
    }

    /**
     * Setter for Card Stack
     * @param cardStack Card Stack to be set
     */
    public void setCardStack(Stack<Card> cardStack) {
        this.cardStack = cardStack;
    }

    /**
     * Stack to store and retrieve the cards
     */
    private Stack<Card> cardStack;

    /**
     * Number of card sets exchanged
     */
    private int numberOfCardSetTraded;

    /**
     * Buffered Writer for Log file
     */
    private transient BufferedWriter bufferedWriter;

    /**
     * GamePlayController Default Constructor
     */
    public GamePhaseController() {

    }

    /**
     * GamePhaseController Constructor
     * @param mapObj RiskMapRW Object
     * @param hm  HashMap
     */

    public GamePhaseController(RiskMapRW mapObj, HashMap<String, String> hm) {
        this.map = mapObj;
        this.startUpPhase = new RiskLaunchPhase(this);
        this.card = new Card();
        this.playerNamesAndTypes = hm;
        card.addObserver(this);
        worldDomination = new PlayerWorldDomination();
        worldDomination.addObserver(this);
        this.setNumberOfCardSetTraded(0);
        this.bufferedWriter = clearContentsOfFile();
    }

    /**
     * This method helps in player creation.
     */
    public void playerCreation() {

        setNumberOfPlayersSelected(this.playerNamesAndTypes.keySet().size());
        gamePlayerList.clear();
        updateTerminalWindow("Set up phase started\n");
        gamePlayerList = new Player().generatePlayer(this.playerNamesAndTypes, this);
        for (Player player : gamePlayerList) {
            player.addObserver(this);
        }
        System.out.println("All players generated");
        updateTerminalWindow("All players generated\n");

        playerIterator = gamePlayerList.iterator();

        new Player().assignArmiesToPlayers(gamePlayerList);
        System.out.println("Armies assigned to players");
        allocateCountryToPlayerInGamePlay();
        System.out.println("Countries allocated to player");
        updateTerminalWindow("All countries assigned\n");

        loadMapData();
        System.out.println("Map data loaded");
        loadCurrentPlayer();
        System.out.println("Current player loaded");
        generateBarGraph();
        System.out.println("bar graph generated");
        GameUpdateWindow.enableButtonControl(cards);
        loadGameCard();
        System.out.println("Game cards loaded");
        if (!(playerPlaying.getPlayerBehaviour() instanceof Human)) {
            placeArmy(null);
        }
    }

    /**
     * This is the initial method which loads the Game Cards,Map Contents
     * and helps in assigning the country and adjacent countries to the
     * player.
     *
     * @param location Location
     * @param resources Resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (!isGameSaved) {
            gamePlayerList = new ArrayList<>();
            playerCreation();
            loadMapData();
        } else {
            loadGameCard();
            loadMapData();
        }
        phaseView.setText("Phase: Start Up");
        GameUpdateWindow.disableButtonControl(reinforcement, fortify, attack, cards);

        selectedCountryList.setCellFactory(param -> new ListCell<Country>() {
            @Override
            protected void updateItem(Country currentCountry, boolean empty) {
                super.updateItem(currentCountry, empty);

                if (empty || currentCountry == null || currentCountry.getName() == null) {
                    setText(null);
                } else {
                    setText(currentCountry.getName() + ":-" + currentCountry.getNoOfArmies() + "-" + currentCountry.getPlayer().getName());
                }
            }
        });
        selectedCountryList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Country country = selectedCountryList.getSelectionModel().getSelectedItem();
                if (phaseView.getText().equals("Phase: Fortification"))
                    moveToAdjacentCountryFortification(country);
                else
                    moveToAdjacentCountry(country);
            }
        });

        adjacentCountryList.setCellFactory(param -> new ListCell<Country>() {
            @Override
            protected void updateItem(Country currentCountry, boolean empty) {
                super.updateItem(currentCountry, empty);

                if (empty || currentCountry == null || currentCountry.getName() == null) {
                    setText(null);
                } else {
                    setText(currentCountry.getName() + "-" + currentCountry.getNoOfArmies() + "-" + currentCountry.getPlayer().getName());
                }
            }
        });

        if (isGameSaved) {
            dataToLoad();
            
        }
    }

    /**
     * Method to move to the adjacent country for fortification
     *
     * @param country Country Object
     */
    private void moveToAdjacentCountryFortification(Country country) {
        this.adjacentCountryList.getItems().clear();
        GamePhaseController.allAdjacentCountries.clear();
        for (Country country1 : this.map.getMapGraph().getAllCountries().values()) {
            GamePhaseController.visited.put(country1, false);
        }
        this.findAdjacentCountriesFortification(country);
        this.findAdjacentCountriesOtherPlayers(country);
        this.adjacentCountryList.getItems().addAll(GamePhaseController.allAdjacentCountries);
        
    }

    private void findAdjacentCountriesOtherPlayers(Country country) {
        for (Country country1 : country.getAdjacentCountries()) {
            if (!playerPlaying.getPlayerCountries().contains(country1)) {
                GamePhaseController.allAdjacentCountries.add(country1);
            }
        }
    }

    /**
     * Method to move to the adjacent country for fortification
     *
     * @param country Country Object
     */
    private void moveToAdjacentCountry(Country country) {
        this.adjacentCountryList.getItems().clear();
        if (country != null) {
            for (Country adjTerr : country.getAdjacentCountries()) {
                this.adjacentCountryList.getItems().add(adjTerr);
            }
        }
    }

    private void findAdjacentCountriesFortification(Country country) {
        if (country != null) {
            GamePhaseController.visited.put(country, true);
            for (Country adjCountry : country.getAdjacentCountries()) {
                if (!GamePhaseController.visited.get(adjCountry) && !GamePhaseController.allAdjacentCountries.contains(adjCountry)
                        && playerPlaying.getPlayerCountries().contains(adjCountry)) {
                    GamePhaseController.allAdjacentCountries.add(adjCountry);
                    findAdjacentCountriesFortification(adjCountry);
                }
            }
        }
    }


    /**
     * Method which helps in allocating cards to Countries.
     */
    private void loadGameCard() {
        cardStack = startUpPhase.assignCardToCountry(map);
        System.out.println("cardstack=" + cardStack.toString());
        updateTerminalWindow("Cards loaded\n");
    }

    /**
     * This is the Complete Attack button event. This button helps the player
     * to end attack phase and move to next phase.
     *
     * @param event End Attack Event
     */
    @FXML
    private void completeAttack(ActionEvent event) {
        adjacentCountryList.setOnMouseClicked(e -> System.out.print(""));
        if (playerPlaying.getCountryWon() > 0) {
            allocateCardToPlayer();
        }
        System.out.println("Attack phase ended\n");
        updateTerminalWindow("Attack phase ended\n");
        isValidFortificationPhase();
    }

    /**
     * Method to allocate cards to player
     */
    public void allocateCardToPlayer() {
        if (!cardStack.isEmpty()) {
            Card cardToBeAdded = cardStack.pop();
            cardToBeAdded.setCurrentPlayer(playerPlaying);
            playerPlaying.getCardList().add(cardToBeAdded);
            playerPlaying.setCountryWon(0);
            System.out.println(cardToBeAdded.getCardType() + " card is assigned to " +
                    playerPlaying.getName() + " and won country " + cardToBeAdded.getCountry().getName() + "\n");
            updateTerminalWindow(cardToBeAdded.getCardType() + " card is assigned to " +
                    playerPlaying.getName() + " and won country " + cardToBeAdded.getCountry().getName() + "\n");
        }
    }

    /**
     * Method to assign the attacking and defending country
     */
    private void attack() {
        System.out.println("Player " + playerPlaying.getName() + " list of countries are: " + selectedCountryList.getItems().toString());
        playerPlaying.attackPhase(selectedCountryList, adjacentCountryList, gamePlayerList);
    }

    /**
     * This is the Fortify Button Event
     *
     * @param event fortify button
     */
    @FXML
    private void fortify(ActionEvent event) {
        playerPlaying.fortificationPhase(selectedCountryList, adjacentCountryList, gamePlayerList);
        selectedCountryList.refresh();
        adjacentCountryList.refresh();
        loadMapData();
    }

    /**
     * This is the endTurn Action event.
     *
     * @param event endTurn button
     */
    @FXML
    private void endTurn(ActionEvent event) {
        adjacentCountryList.setOnMouseClicked(e -> System.out.print(""));
        System.out.println("Player " + playerPlaying.getName() + " ended his turn.\n");
        updateTerminalWindow("Player " + playerPlaying.getName() + " ended his turn.\n");
        if (playerPlaying.getCountryWon() > 0) {
            allocateCardToPlayer();
        }
        if (gamePlayerList.size() > 1) {
            initializeReinforcement();
        }
    }

    /**
     * This is the placeArmy Action event.
     *
     * @param event placeArmy button
     */
    @FXML
    private void placeArmy(ActionEvent event) {
        playerPlaying.placeArmyOnCountry(selectedCountryList, gamePlayerList);
    }

    /**
     * This is the reinforcement Action event.
     *
     * @param event reinforcement button
     */
    @FXML
    private void reinforcement(ActionEvent event) {
        Country country = selectedCountryList.getSelectionModel().getSelectedItem();
        if (playerPlaying.getCardList().size() >= 5) {
            GameUpdateWindow.popUpWindow("Exchange Card", "Card exchange popup", "You have at least 5 cards please exchange for armies");
            return;
        }
        playerPlaying.reinforcementPhase(selectedCountryList.getItems(), country, gamePlayerList);
        selectedCountryList.refresh();
        loadMapData();
        playerChosen.setText(playerPlaying.getName() + ":- " + playerPlaying.getArmyCount() + " armies left.");
    }

    /**
     * Method to load the Map Data in the new Pane.
     */
    private void loadMapData() {
        displayBox.getChildren().clear();
        for (Continent continent : map.getMapGraph().getContinents().values()) {
            displayBox.autosize();
            displayBox.getChildren().add(GameUpdateWindow.createNewPane(continent));
        }
    }

    /**
     * Method which helps to allocate country to the player.
     */
    private void allocateCountryToPlayerInGamePlay() {
        System.out.println("Assigning countries to all players\n");
        updateTerminalWindow("Assigning countries to all players\n");
        startUpPhase.assignCountryToPlayer(map, gamePlayerList);
    }

    /**
     * The Game goes in the round robin fashion and loads a player
     * for the next turn.
     */
    private void loadCurrentPlayer() {
        if (!playerIterator.hasNext()) {
            playerIterator = gamePlayerList.iterator();
        }
        Player newPlayer = playerIterator.next();
        if (newPlayer.equals(playerPlaying)) {
            if (playerIterator.hasNext()) {
                newPlayer = playerIterator.next();
            }
        }
        if (playerPlaying != null) {
            playerPlaying.setCountryWon(0);
        }
        playerPlaying = newPlayer;
        Player.setPlayerPlaying(playerPlaying);
        playerPlaying.setCountryWon(0);
        playerPlaying.addObserver(this);
        System.out.println(playerPlaying.getName() + "'s turn started.\n");
        updateTerminalWindow(playerPlaying.getName() + "'s turn started.\n");

        selectedCountryList.getItems().clear();
        adjacentCountryList.getItems().clear();
        loadMapData();
        for (Country territory : playerPlaying.getPlayerCountries()) {
            selectedCountryList.getItems().add(territory);
        }
        playerChosen.setText(playerPlaying.getName() + ":- " + playerPlaying.getArmyCount() + " armies left.\n");
    }

    /**
     * The Game goes in the round robin fashion and loads a player
     * for the next turn.
     */
    private void loadCurrentPlayerSave(){
        System.out.println(playerPlaying.getName() + "'s turn started.\n");
        updateTerminalWindow(playerPlaying.getName() + "'s turn started.\n");

        selectedCountryList.getItems().clear();
        adjacentCountryList.getItems().clear();
        loadMapData();
        for (Country territory : playerPlaying.getPlayerCountries()) {
            selectedCountryList.getItems().add(territory);
        }
        playerChosen.setText(playerPlaying.getName() + ":- " + playerPlaying.getArmyCount() + " armies left.\n");
    }

    /**
     * Method which helps to calculate the reinforcement armies.
     */
    public void calculateReinforcementArmies() {
        if (this.playerPlaying != null) {
            playerPlaying = playerPlaying.noOfReinforcementArmies(playerPlaying);
            System.out.println(playerPlaying.getName() + ":- " + playerPlaying.getArmyCount() + " armies left.");
            playerChosen.setText(playerPlaying.getName() + ":- " + playerPlaying.getArmyCount() + " armies left.");
        } else {
            System.out.println("Wait, no player is assigned the position of current player\n");
            updateTerminalWindow("Wait, no player is assigned the position of current player\n");
        }
    }

    /**
     * Init card Button to open the card window
     *
     * @param event Open card Window Button
     */
    @FXML
    public void initCardWindow(ActionEvent event) {
        CardExchangeView.openCardWindow(playerPlaying, card);
    }

    /**
     * Method to initialize reinforcement phase after each player's turn.
     */
    private void initializeReinforcement() {
        loadCurrentPlayer();

        phaseView.setText("Phase: Reinforcement");
        GameUpdateWindow.disableButtonControl(placeArmy, fortify, attack);
        GameUpdateWindow.enableButtonControl(reinforcement, cards);
        updateTerminalWindow("\nReinforcement phase started\n");
        calculateReinforcementArmies();
        if (!(playerPlaying.getPlayerBehaviour() instanceof Human)) {
            card.automateCardWindow(playerPlaying);
            playerPlaying.reinforcementPhase(selectedCountryList.getItems(), null, gamePlayerList);
            selectedCountryList.refresh();
            System.out.println(playerPlaying.getName() + ":- " + playerPlaying.getArmyCount() + " armies left.");
            loadMapData();
            playerChosen.setText(playerPlaying.getName() + ":- " + playerPlaying.getArmyCount() + " armies left.");

        } else {
            GameUpdateWindow.enableButtonControl(reinforcement);
            reinforcement.requestFocus();
            CardExchangeView.openCardWindow(playerPlaying, card);
        }
    }

    /**
     * Method to initialize reinforcement phase after each player's turn.
     */
    private void initializeReinforcementSave() {
        loadCurrentPlayerSave();

        phaseView.setText("Phase: Reinforcement");
        GameUpdateWindow.disableButtonControl(placeArmy, fortify, attack);
        GameUpdateWindow.enableButtonControl(reinforcement, cards);
        updateTerminalWindow("\nReinforcement phase started\n");

        if (!(playerPlaying.getPlayerBehaviour() instanceof Human)) {
            card.automateCardWindow(playerPlaying);
            playerPlaying.reinforcementPhase(selectedCountryList.getItems(), null, gamePlayerList);
            selectedCountryList.refresh();
            System.out.println(playerPlaying.getName() + ":- " + playerPlaying.getArmyCount() + " armies left.");
            loadMapData();
            playerChosen.setText(playerPlaying.getName() + ":- " + playerPlaying.getArmyCount() + " armies left.");

        } else {
            GameUpdateWindow.enableButtonControl(reinforcement);
            reinforcement.requestFocus();
            CardExchangeView.openCardWindow(playerPlaying, card);
        }
    }

    /**
     * Method to perform the attack action event after
     * selection of defending country.
     */
    private void performAttack() {
        adjacentCountryList.setOnMouseClicked(e -> attack());
    }

    /**
     * Method to initialize attack phase in the game.
     */
    private void initializeAttack() {
        System.out.println("\n Attack phase started.\n");
        updateTerminalWindow("\n Attack phase started.\n");

        if (playerPlaying.playerCanAttack(selectedCountryList)) {
            phaseView.setText("Phase: Attack");

            GameUpdateWindow.disableButtonControl(reinforcement, placeArmy);
            GameUpdateWindow.enableButtonControl(attack);

            attack.requestFocus();
            System.out.println("Player " + playerPlaying.getName() + " is " + playerPlaying.getPlayerType());
            if (playerPlaying.getPlayerBehaviour() instanceof Human) {
                System.out.println("Human is attacking\n");
                performAttack();
            } else {
                System.out.println("Non-Human is attacking\n");
                attack();
            }
        }
    }

    /**
     * Method to initialize fortification in the game.
     */
    private void initializeFortification() {

        GameUpdateWindow.disableButtonControl(reinforcement, attack, placeArmy);
        GameUpdateWindow.enableButtonControl(fortify);
        phaseView.setText("Phase: Fortification");
        fortify.requestFocus();
        System.out.println("\nFortification phase started.\n");
        updateTerminalWindow("\nFortification phase started.\n");
        if (!(playerPlaying.getPlayerBehaviour() instanceof Human)) {
            playerPlaying.fortificationPhase(selectedCountryList, adjacentCountryList, gamePlayerList);
            selectedCountryList.refresh();
            adjacentCountryList.refresh();
            loadMapData();
        }

    }

    /**
     * Method to generate the graph to populate the World Domination Window.
     */
    private void generateBarGraph() {
        HashMap<Player, Double> playerTerPercent = worldDomination.generateWorldDominationData(map);
        ObservableList<XYChart.Series<String, Double>> answer = FXCollections.observableArrayList();
        ArrayList<XYChart.Series<String, Double>> chartData = new ArrayList<>();
        for (Entry<Player, Double> entry : playerTerPercent.entrySet()) {
            XYChart.Series<String, Double> aSeries = new XYChart.Series<>();
            aSeries.setName(entry.getKey().getName());
            aSeries.getData().add(new XYChart.Data(entry.getKey().getName(), entry.getValue()));
            chartData.add(aSeries);
        }
        answer.addAll(chartData);
        dominationBarChart.setData(answer);
    }

    /**
     * Method to check if any player has lost the game.
     */
    private void isAnyPlayerLost() {
        List<Player> playersLost = playerPlaying.checkPlayerLost(gamePlayerList);
        if (!playersLost.isEmpty()) {
            for (Player player : playersLost) {
                System.out.println(player.getName() + " lost the game and hence all the countries.\n\n");
                gamePlayerList.remove(player);
                playerIterator = gamePlayerList.iterator();
                GameUpdateWindow.popUpWindow(player.getName() + " Lost", "Player Lost popup", "Player: " + player.getName() + " lost the game");
                updateTerminalWindow(player.getName() + " lost the game and hence all the countries.\n\n");
            }
        }
    }

    /**
     * Method to End the Game/
     */
    private void endGame() {
        GameUpdateWindow.disableButtonControl(selectedCountryList, adjacentCountryList, reinforcement, attack, fortify, cards, endTurn);
        phaseView.setText("GAME OVER");
        playerChosen.setText(playerPlaying.getName().toUpperCase() + " WON.");

        System.out.println("\n " + playerPlaying.getName().toUpperCase() + " WON.\n\n");
        updateTerminalWindow("\n " + playerPlaying.getName().toUpperCase() + " WON.\n\n");
    }

    /**
     * Method to check if any player won the game.
     *
     * @return playerWon boolean player status
     */
    private boolean isAnyPlayerWon() {
        boolean playerWon = false;
        if (gamePlayerList.size() == 1) {
            GameUpdateWindow.popUpWindow("Player: " + gamePlayerList.get(0).getName(), "Winning popup", "Game complete.");
            playerWon = true;
            endGame();
            System.out.flush();
            Platform.exit();
            System.exit(0);
        }

        return playerWon;
    }

    /**
     * Method to reset the window after each phase in the game
     */
    private void resetWindow() {
        isAnyPlayerLost();
        selectedCountryList.getItems().clear();
        adjacentCountryList.getItems().clear();
        for (Country country : playerPlaying.getPlayerCountries()) {
            selectedCountryList.getItems().add(country);
        }
        loadMapData();
        generateBarGraph();
        playerChosen.setText(playerPlaying.getName() + ": " + playerPlaying.getArmyCount() + " armies left.\n");
        if (!isAnyPlayerWon()) {
            playerPlaying.playerCanAttack(selectedCountryList);
        }
    }

    /**
     * Skip Attack
     */
    private void skipAttack() {
        isAnyPlayerLost();
        selectedCountryList.getItems().clear();
        adjacentCountryList.getItems().clear();
        for (Country country : playerPlaying.getPlayerCountries()) {
            selectedCountryList.getItems().add(country);
        }
        loadMapData();
        generateBarGraph();
        playerChosen.setText(playerPlaying.getName() + ": " + playerPlaying.getArmyCount() + " armies left.\n");
        if (!isAnyPlayerWon()) {
            if (!(playerPlaying.getPlayerBehaviour() instanceof Human)) {
                if (playerPlaying.getCountryWon() > 0) {
                    allocateCardToPlayer();
                }
                System.out.println("Attack phase ended\n");
                updateTerminalWindow("Attack phase ended\n");
                isValidFortificationPhase();
            }

        }
    }


    /**
     * Method to initialize placeArmy view.
     */
    private void initializePlaceArmy() {
        loadMapData();
        selectedCountryList.refresh();
        loadCurrentPlayer();
        if (!(playerPlaying.getPlayerBehaviour() instanceof Human)) {
            placeArmy(null);
        }
    }

    /**
     * Method to check if fortification phase is valid.
     */
    private void isValidFortificationPhase() {
        playerPlaying.isFortificationPhaseValid(map, playerPlaying);
    }

    /**
     * Method to update the player having few or no armies to fortify through the terminal window
     */
    private void noFortificationPhase() {
        System.out.println("Fortification phase started\n");
        System.out.println(playerPlaying.getName() + " does not have armies to fortify.\n");
        System.out.println("Fortification phase ended\n");
        updateTerminalWindow("Fortification phase started\n");
        updateTerminalWindow(playerPlaying.getName() + " does not have armies to fortify.\n");
        updateTerminalWindow("Fortification phase ended\n");
        initializeReinforcement();
    }

    /**
     * Getter Method to get the number of players selected in the initial window.
     *
     * @return numberOfPlayersSelected number of players playing
     */
    public int getNumberOfPlayersSelected() {
        return numberOfPlayersSelected;
    }

    /**
     * Setter Method to set the number of Players.
     *
     * @param numberOfPlayersSelected number of players selected
     */
    public void setNumberOfPlayersSelected(int numberOfPlayersSelected) {
        this.numberOfPlayersSelected = numberOfPlayersSelected;
    }

    /**
     * Method to exchange the cards to get that number of armies.
     *
     * @param cards Card Object
     */
    public void tradeCards(Card cards) {
        List<Card> tradedCards = cards.getCardsToTrade();
    //    setNumberOfCardSetExchanged(getNumberOfCardSetExchanged() + 1);
        playerPlaying.tradeCards(tradedCards, playerPlaying.getNumberOfCardSetTraded());
        playerPlaying.getCardList().removeAll(tradedCards);
        cardStack.addAll(tradedCards);
        Collections.shuffle(cardStack);

        if(!TournamentModel.isTournamentMode){
            selectedCountryList.refresh();
            adjacentCountryList.refresh();
            loadMapData();
            generateBarGraph();
            playerChosen.setText(playerPlaying.getName() + ":- " + playerPlaying.getArmyCount() + " armies left.\n");
            System.out.println();
        }

    }

    /**
     * This method is called whenever the observed object is changed and
     * it also notifies all the observers of the change.
     *
     * @param o Object
     * @param arg Object
     */
    public void update(Observable o, Object arg) {

        String view = (String) arg;

        if (view.equals("Attack")) {
            initializeAttack();
        } else if (view.equals("FirstAttack")) {
           // loadCurrentPlayer();
            initializeReinforcement();
        } else if (view.equals("Reinforcement")) {
            initializeReinforcement();
        } else if (view.equals("Fortification")) {
            initializeFortification();
        } else if (view.equals("placeArmyOnCountry")) {
            initializePlaceArmy();
        } else if (view.equals("WorldDomination")) {
            generateBarGraph();
        } else if (view.equals("checkIfFortificationPhaseValid")) {
            isValidFortificationPhase();
        } else if (view.equals("noFortificationMove")) {
            noFortificationPhase();
        } else if (view.equals("rollDiceComplete")) {
            resetWindow();
        } else if (view.equals("cardsExchange")) {
            Card cardObject = (Card) o;
            tradeCards(cardObject);
        } else if (view.equals("skipAttack")) {
            updateTerminalWindow(gamePlayerList.size() + " number of players are left.\n");
            skipAttack();
        } else {
            updateTerminalWindow(view);
        }
    }

    /**
     * Getter to get the number of card sets exchanged.
     *
     * @return numberOfCardSetTraded number of card sets
     */
    public int getNumberOfCardSetTraded() {
        return numberOfCardSetTraded;
    }

    /**
     * Setter method to set the number of card set exchanged.
     *
     * @param numberOfCardSetTraded number of card sets
     */
    public void setNumberOfCardSetTraded(int numberOfCardSetTraded) {
        this.numberOfCardSetTraded = numberOfCardSetTraded;
    }


    /**
     * This method helps in updating the terminal window by running single thread
     *
     * @param information Information
     */
    public void updateTerminalWindow(String information) {
        Platform.runLater(() -> {
            if (terminalWindow != null)
                terminalWindow.appendText(information);
        });
        writeToFile(information);
    }

    /**
     * FXML Button to save Game
     * @param event Event
     */

    @FXML
    private void saveGame(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Game");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Save Game Extension(*.save or *.SAVE)", "*.save", "*.SAVE"));

        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            save(this, file);
        }


    }

    /**
     * Method to save Game
     *
     * @param gamePhaseController GamePhaseController Object
     * @param file File
     */

    public void save(GamePhaseController gamePhaseController, File file) {

        try {
            ResourceManager.save(gamePhaseController, file);
            System.out.println("Data is saved");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Method to Load Game
     * @return controller Controller File
     */

    public GamePhaseController loadGame() {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load Game");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Save Game Extension(*.save or *.SAVE)", "*.SAVE", "*.save"));

        File file = fileChooser.showOpenDialog(null);
        GamePhaseController controller = load(file);

        return controller;

    }

    /**
     * Method to Load the contents to the Game
     * @param file File
     * @return controller GamePlayController
     */

    public GamePhaseController load(File file) {

        GamePhaseController controller = null;
        try {
            controller = (GamePhaseController) ResourceManager.load(file);
            System.out.println("Game is Loaded");
        } catch (Exception e) {
            System.out.println("Couldn't load save data: " + e.getMessage());
        }
        return controller;
    }

    /**
     * The object implements the writeExternal method to save its contents
     * by calling the methods of DataOutput for its primitive values or
     * calling the writeObject method of ObjectOutput for objects, strings,
     * and arrays.
     *
     * @serialData Overriding methods should use this tag to describe
     *             the data layout of this Externalizable object.
     *             List the sequence of element types and, if possible,
     *             relate the element to a public/protected field and/or
     *             method of this Externalizable class.
     *
     * @param data the stream to write the object to
     * @exception IOException Includes any I/O exceptions that may occur
     */

    @Override
    public void writeExternal(ObjectOutput data) throws IOException {

        data.writeObject(map);
        data.writeObject(startUpPhase);
        data.writeObject(Player.currentPlayer);
        data.writeObject(playerPlaying);
        data.writeObject(card);
        data.writeObject(cardStack);
        data.writeObject(gamePlayerList);
        data.writeObject(terminalWindow.getText());
        data.writeObject(phaseView.getText());
        data.writeObject(worldDomination);
        data.writeObject(numberOfCardSetTraded);
        data.writeObject(playerChosen.getText());

    }
    /**
     * The object implements the readExternal method to restore its
     * contents by calling the methods of DataInput for primitive
     * types and readObject for objects, strings and arrays.  The
     * readExternal method must read the values in the same sequence
     * and with the same types as were written by writeExternal.
     *
     * @param data the stream to read data from in order to restore the object
     * @exception IOException if I/O errors occur
     * @exception ClassNotFoundException If the class for an object being
     *              restored cannot be found.
     */
    @Override
    public void readExternal(ObjectInput data) throws IOException, ClassNotFoundException {

        isGameSaved = true;
        map = (RiskMapRW) data.readObject();
        startUpPhase = (RiskLaunchPhase) data.readObject();
        Player.currentPlayer=(Player)data.readObject();
        playerPlaying = (Player) data.readObject();
        card = (Card) data.readObject();
        cardStack = (Stack<Card>) data.readObject();
        gamePlayerList = (ArrayList<Player>) data.readObject();
        gameDataString = (String) data.readObject();
        phaseViewString = (String) data.readObject();
        worldDomination = (PlayerWorldDomination) data.readObject();
        numberOfCardSetTraded = (int) data.readObject();
        playerData = (String) data.readObject();
        playerPlaying.addObserver(this);
        card.addObserver(this);
        worldDomination.addObserver(this);
        this.bufferedWriter = clearContentsOfFile();

    }

    /**
     * Method to Load the contents to the Game
     */
    private void dataToLoad() {
        phaseView.setText(phaseViewString);
        updateTerminalWindow(gameDataString);
        playerChosen.setText(playerData);
        generateBarGraph();
        ObservableList<Country> list = FXCollections.observableArrayList(playerPlaying.getPlayerCountries());
        selectedCountryList.getItems().addAll(list);
        playerIterator = gamePlayerList.iterator();
        while (playerIterator.hasNext()) {
            if (playerIterator.next().equals(playerPlaying)) {
                break;
            }
        }
        if(phaseViewString.contains("Reinforcement")){
            initializeReinforcementSave();
        }else if(phaseViewString.contains("Fortification")){
            initializeFortification();
        }else if(phaseViewString.contains("Attack")){
            initializeAttack();
        }
    }

    /**
     * Method to generate BufferedWriter Object
     * @return bufferedWriter buffered Writer
     */

    public BufferedWriter clearContentsOfFile() {
        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(new File("").getAbsolutePath() + "\\src\\main\\resources\\GamePlay" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".txt", true));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bufferedWriter;
    }

    /**
     * Method to Write Contents to File
     * @param output String
     */

    public void writeToFile(String output) {
        try {
            this.bufferedWriter.write(output);
            this.bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}