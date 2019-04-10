package com.risk.team.testsuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.risk.team.model.CardTest;
import com.risk.team.model.ContinentTest;
import com.risk.team.model.CountryTest;
import com.risk.team.model.DiceTest;
import com.risk.team.model.PlayerTest;
import com.risk.team.model.PlayerWorldDominationTest;
import com.risk.team.model.TournamentModelTest;
import com.risk.team.services.*;
import com.risk.team.services.saveload.ResourceManagerTest;
import com.risk.team.strategy.AggressiveTest;
import com.risk.team.strategy.BenevolentTest;
import com.risk.team.strategy.CheaterTest;
import com.risk.team.strategy.HumanTest;

/**
 * TestSuite Class to test all test cases
 * 
 * @author Dhaval Desai
 * 
 * @version 3.0.0
 *
 */

@RunWith(Suite.class)
@SuiteClasses({RiskMapEditTest.class,RiskMapGraphTest.class,RiskMapVerifyTest.class,ResourceManagerTest.class,PlayerWorldDominationTest.class,
	AggressiveTest.class,BenevolentTest.class,HumanTest.class,TournamentModelTest.class,RiskConnectedGraphTest.class,CardTest.class,DiceTest.class,ContinentTest.class,CountryTest.class,TournamentModelTest.class,PlayerTest.class,RiskLaunchPhaseTest.class,CheaterTest.class})

/**
 * Method to execute all the test cases mentioned in suite classes.
 */
public class AllTests {   
}
