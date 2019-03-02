package com.Risk.Team.Controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import com.Risk.Team.model.*;
import com.Risk.Team.Controller.RiskMapGraph;

/**
 * Class RiskMapRW contains Read/Write methods to read an existing map or writing a map to new file for the game.
 * @author Kartika Patil
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
		this.mapGraph.setCountrySet(map.getCountryMap());

	}

	/**
	 * Method to read calling RiskMapRW Object
	 * 
	 * @return RiskMapRW map Object of reading file;
	 */
	public RiskMapRW readMapFile() {
		return this;
	}

	/**
	 * Method to get filename of map.
	 * 
	 * @return filename name of map;
	 */
	
	public String getFileName() {
		return fileName;
	}
	
	/**
	 * Method to set filename of new map.
	 * 
	 * @param  filename name of file of type string;
	 * @return nothing
	 */
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * Method to get map Tag details.
	 * 
	 * @return mapTagInfo list of map Tag Data;
	 */
	public ArrayList<String> getMapTagInfo() {
		return mapTagInfo;
	}
	
	/**
	 * Method to set map Tag Data into magTagInfo;
	 * 
	 * @param  mapTagInfo list of map Tag Data;
	 * @return nothing;
	 */

	public void setMapTagInfo(ArrayList<String> mapTagInfo) {
		this.mapTagInfo = mapTagInfo;
	}

	/**
	 * Method to get newFileName.
	 * @return newFileName name of new file to be created;
	 */
	
	public String getNewFileName() {
		return newFileName;
	}

	/**
	 * Method to set newFileName.
	 * @param newFileName name of new file to be created;
	 * @return nothing;
	 */
	
	public void setNewFileName(String newFileName) {
		this.newFileName = newFileName;
	}

	/**
	 * Method to get RiskMapGraph Object.
	 * @return RiskMapGraph Object of type RiskMapGraph;
	 */
	
	public RiskMapGraph getMapGraph() {
		return mapGraph;
	}
	
	/**
	 * Method to set RiskMapGraph Object.
	 * @param RiskMapGraph Object of type RiskMapGraph;
	 * @return nothing;
	 */

	public void setMapGraph(RiskMapGraph mapGraph) {
		this.mapGraph = mapGraph;
	}
	
	/**
	 * Method to write content to new or existing .map file
	 * It initially creates file object with new filename.It then retrieves mapGraph object details and appends into
	 * StrinBuilder Object.At last StringBuilder Object is written into file successfully.
	 * 
	 * @param flag boolean value for file new is existing;
	 * @return true if file is written successfully or false;
	 */
	
	public boolean writeMapFile(boolean flag)
	
	{   File file;
		StringBuilder sb = new StringBuilder();
		
		if (flag)
			
		{
			file = new File(new File("").getAbsolutePath() + "\\src\\main\\gameMap\\" + this.fileName + ".map");
		}
		else
			
		{
			
			file = new File(new File("").getAbsolutePath() + "\\src\\main\\gameMap\\" + this.newFileName + ".map");

		}
		
		try(BufferedWriter mapwriter = new BufferedWriter(new FileWriter(file.getAbsolutePath())))
		{
			
			sb.append("[Map]\n");
			for (String line : mapTagInfo) {
				sb.append(line);
				sb.append("\n");
			}
			sb.append("\n");
			sb.append("[Continents]\n");
			for (Map.Entry<String, Continent> continentEntry : mapGraph.getContinents().entrySet()) {
				sb.append(continentEntry.getValue().getName() + "=" + continentEntry.getValue().getControlValue());
				sb.append("\n");
			}
			sb.append("\n");
			
			sb.append("[Territories]\n");
			
			for (Map.Entry<Country, ArrayList<Country>> adjacentCountriesEntry : mapGraph.getAdjacentCountries().entrySet())
				{
				
					Country country = adjacentCountriesEntry.getKey();
					ArrayList<Country> neighbours = adjacentCountriesEntry.getValue();
					
					String line = country.getName() + COMMA_DELIMITER + country.getxValue() + COMMA_DELIMITER
							+ country.getyValue() + COMMA_DELIMITER + country.getContinent();
					
					for (Country adjacentCountry : neighbours) {
						line += COMMA_DELIMITER + adjacentCountry.getName();
					}
					
					sb.append(line);

					sb.append("\n");
				}

			
					mapwriter.write(sb.toString());
					
				
			}


		 catch (IOException e) {
			 
			 System.out.println("IO Exception - Error occucred while writing to a file");
			
			 return false;

		}
		
		System.out.println("File Written Successfully into "+this.fileName+".map");
						
		return true;
	
	}
	
	
}
