package com.risk.team.controller;

import org.junit.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.Before;
import org.junit.Ignore;


import com.risk.team.model.Card;
import com.risk.team.model.Player;
import com.risk.team.view.LoadUI;
public class RiskCardControllerTest {
	
	private Player player;
	private Card card;
	private RiskCardController r;
	
	@Before
	public void init() {
		player=new Player();
		card=new Card();
		r=new RiskCardController(player,card);
	}
	
	@Test
	public void testInitializeFalse() {
		
		boolean expected=false;
		boolean actual=r.initializeTrade();
		assertEquals(actual,expected);
	}
	
	
	@Test
	public void testInitializeTrue() {
		//Understand from Kartika
	}
	
	@Test
	public void getCardsToTradeTest() {
		
		RiskCardController rcc=mock(RiskCardController.class);
		r.loadCards();
		
	}
	
}
