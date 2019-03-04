package com.risk.team.controller;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import com.risk.team.controller.*;

/**
 *Test Class for Valid Map Verification 
 *
 * @author yashgolwala
 *
 */



public class RiskMapVerifyTest {

	/** Object for RiskMapVerify class*/

	private RiskMapVerify mapValidation;

	/** String having Invalid Tags content */
	private String invalidTag;

	/** String having valid Tags content*/
	private String validTag;

	/** String to hold valid Map File Path */
	private String validMapFile;

	/** String to hold invalid Map File Path */
	private String invalidMapFile;

	/**
	 * Set up the initial objects for RiskMapVerify
	 * 
	 */
	@Before public void intialize()
	{

		mapValidation = new RiskMapVerify();
		invalidTag = " [Continents] [Territories]";
		validTag = "[Map] [Continents] [Territories]";
		validMapFile = "ABC_Map.map";
		invalidMapFile = "ABC_Map_Invalid.map";


	}

	/**
	 * Test method for testing validation of a file
	 * 
	 */

	@Test
	public void validateFileValidFileTest() {

		boolean test = mapValidation.verifyMapFile(validMapFile);
		assertTrue(test);
	}

	/**
	 * Test method for testing validation of an Invalid file
	 * 
	 */

	@Test
	public void validateFileInValidFileTest() {

		assertFalse(mapValidation.verifyMapFile(invalidMapFile));
	}

	/**
	 * Test method for checking all necessary tags like [Map], [Continents], [Territories]
	 * 
	 */

	@Test
	public void checkValidTagsTest() {

		assertTrue(mapValidation.checkAllTags(validTag));
	}

	/**
	 * Test method for checking invalid tags
	 * 
	 */

	@Test
	public void checkInValidTagsTest() {

		assertFalse(mapValidation.checkAllTags(invalidTag));
	}

	/**
	 * Test method for counting occurrences of tags
	 * 
	 */

	@Test
	public void countOccurrencesTest() {

		assertEquals(0,mapValidation.countOccurrences(invalidTag,"[Map]"));
		assertEquals(1,mapValidation.countOccurrences(validTag,"[Map]"));
		assertEquals(1,mapValidation.countOccurrences(invalidTag,"[Continents]"));

	}


}
