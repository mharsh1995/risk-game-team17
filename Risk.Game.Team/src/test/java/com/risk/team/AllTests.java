package com.risk.team;

import com.risk.team.controller.*;
import com.risk.team.controller.gamephase.FortificationTest;
import com.risk.team.controller.gamephase.ReinforcementTest;
import com.risk.team.controller.gamephase.RiskRoundRobinTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({RiskGraphConnectedTest.class, RiskLaunchPhaseTest.class, RiskMapEditTest.class, RiskMapGraphTest.class,
	RiskMapVerifyTest.class,FortificationTest.class, ReinforcementTest.class, RiskRoundRobinTest.class})
public class AllTests {

}
