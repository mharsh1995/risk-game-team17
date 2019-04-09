package com.risk.team.services;

import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import org.junit.Before;
import org.junit.Test;

import com.risk.team.model.Card;
import com.risk.team.model.Continent;
import com.risk.team.model.Country;
import com.risk.team.model.Player;
import com.risk.team.services.RiskLaunchPhase;
import com.risk.team.services.RiskMapRW;
import com.risk.team.services.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;

/**
 * Test class for launch Phase class.
 * 
 * @author Dhaval Desai
 *
 */
public class RiskLaunchPhaseTest {

	/** Object for StartUpPhase Class */
    private RiskLaunchPhase launchPhase;

    /** Object for Continent Class */
    private Continent continent;

    /** Object for Country Class */
    private Country country1;

    /** Object for Country CLass */
    private Country country2;

    /** Object for RiskMapRW Class */
    private RiskMapRW map;

    /** Object for Player Class */
    private Player player;

    /** The @fxPanel */
    @FXML
    private JFXPanel jfxPanel;

    /** The @textArea */
    @FXML
    private TextArea textArea;

    /** ArrayList to hold list of continents */
    private ArrayList<Continent> listOfContinents;

    /** ArrayList to hold list of Countries */
    private ArrayList<Country> listOfCountries;
    
    /** ArrayList to hold list of all Countries in the graph */
    private List<Country> allCountries;
    
    /** HashMap of all the Countries */
    HashMap<String, Country> countryList;

    /** ArrayList to hold list of Players */
    private ArrayList<Player> listOfPlayers;

    /**
	 * Set up the initial objects for StartUpPhaseTest
	 */
    @Before
    public void initialize(){
    	launchPhase = new RiskLaunchPhase();
        continent = new Continent("Asia",4);

        country1 = new Country("India");
        country1.setPartOfContinent(continent);

        country2 = new Country("Bangladesh");
        country2.setPartOfContinent(continent);

        country2.getAdjacentCountries().add(country2);
        country1.setAdjacentCountries(country2.getAdjacentCountries());
        country1.getAdjacentCountries().add(country1);
        country2.setAdjacentCountries(country1.getAdjacentCountries());              

        map = new RiskMapRW();
        player = new Player("Karan");

        listOfContinents= new ArrayList<>();
        listOfContinents.add(continent);

        listOfCountries= new ArrayList<>();
        listOfCountries.add(country1);

        listOfPlayers= new ArrayList<>();
        player.setPlayerCountries(listOfCountries);
        player.setArmyCount(45);
        listOfPlayers.add(player);
        jfxPanel=new JFXPanel();
        textArea=new TextArea();
        
        countryList = new HashMap<>();
        countryList.put(country1.getName(),country1);
        countryList.put(country2.getName(),country2);
        map.getMapGraph().setAllCountries(countryList);
        
    	allCountries = new ArrayList<>(map.getMapGraph().getAllCountries().values());
        
    }
    
    /**
     * Test for checking assignment of cards to a Country
     */
    @Test
    public void allocateCardToCountryTest(){
        assertNotNull(launchPhase.assignCardToCountry(map));
   }

    /**
     * Test for checking assignment of country to a player
     */
    @Test
    public void allocateCountryToPlayerTest(){
        assertNotNull(launchPhase.assignCountryToPlayer(map,listOfPlayers));
    }

}
