package com.Risk.Team.Controller;

import java.util.ArrayList;

import com.Risk.Team.Controller.RiskMapGraph;

/**
 * Class RiskMapRW contains Read/Write methods to read an existing map or creating a new map for the game.
 * @author Kartika Patil
 * @author 
 */

public class RiskMapRW {

	/** FileName of the existing map */
	private String fileName;

	/** List to hold the information under the Map tags from the map file */
	private ArrayList<String> mapTagInfo;

	/** FileName for the new map */
	private String newFileName;

	/** COMMA Delimiter */
	private static final String COMMA_DELIMITER = ",";

	/** Object of RiskMapGraph */
	private RiskMapGraph mapGraph;

	/**
	 * Constructor to load the contents of a New Map in case of creating a map.
	 */
	public RiskMapRW() {
		this.mapGraph = new RiskMapGraph();		
		this.mapTagInfo = new ArrayList<String>();
	}

	/**
	 * Constructor to load the contents of an Existing Map in case of browsing existing map.
	 * 
	 * @param map
	 *            Object of RiskMapVerify
	 */
	public RiskMapRW(RiskMapVerify map) {
	    this.mapGraph = new RiskMapGraph();
		this.mapGraph.setContinents(map.getContinentsFromTerritories());
		this.mapGraph.setAdjacentCountries(map.getAdjacentCountries());
		this.fileName = map.getFileName();
		this.mapTagInfo = map.getMapTagInfo();
		this.mapGraph.setCountrySet(map.getCountrySet());

	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public ArrayList<String> getMapTagInfo() {
		return mapTagInfo;
	}

	public void setMapTagInfo(ArrayList<String> mapTagInfo) {
		this.mapTagInfo = mapTagInfo;
	}

	public String getNewFileName() {
		return newFileName;
	}

	public void setNewFileName(String newFileName) {
		this.newFileName = newFileName;
	}

	
	
	
	
}
