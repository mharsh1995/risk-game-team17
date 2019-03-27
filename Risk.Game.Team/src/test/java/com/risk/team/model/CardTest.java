package com.risk.team.model;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import com.risk.team.model.Card;

public class CardTest {

	public List<Card> selectedCards;
	Card card1,card2,card3;
	Card test;
	
	@Before
	public void init() {
		selectedCards = new ArrayList<Card>();
		card1=new Card();
		card2=new Card();
		card3=new Card();
		selectedCards.add(card1);
		selectedCards.add(card2);
		selectedCards.add(card3);		
		System.out.println(selectedCards);
		
	}
	
}