package com.Risk.Team.Controller;

import com.Risk.Team.model.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RiskMapVerify {
	
	
	private HashMap<String, Continent> orignalContinents;

	
	private HashMap<String, Continent> continentsFromTerritories;


	private HashMap<String, Country> countryMap;

	
	private HashMap<Country, ArrayList<Country>> adjacentCountries;

	
	private HashMap<Continent, HashSet<Country>> countriesOfContinent;

	
	private String fileName;

	
	private ArrayList<String> mapTagInfo;

	public RiskMapVerify() {
		this.orignalContinents = new HashMap<>();
		this.continentsFromTerritories = new HashMap<>();
		this.adjacentCountries = new HashMap<>();
		this.countriesOfContinent = new HashMap<>();
		this.mapTagInfo = new ArrayList<>();
		this.countryMap = new HashMap<>();
	}
	
	public HashMap<String, Continent> getContinentsFromTerritories() {
		return continentsFromTerritories;
	}

	
	public void setContinentsFromTerritories(HashMap<String, Continent> continentsFromTerritories) {
		this.continentsFromTerritories = continentsFromTerritories;
	}
	
	
	public HashMap<String, Continent> getOrignalContinents() {
		return orignalContinents;
	}
	
	
	public void setOrignalContinents(HashMap<String, Continent> orignalContinents) {
			this.orignalContinents = orignalContinents;
		}

	
	public HashMap<Country, ArrayList<Country>> getAdjacentCountries() {
		return adjacentCountries;
	}
	
	public HashMap<Continent, HashSet<Country>> getCountriesOfContinent() {
		return countriesOfContinent;
	}
	
	
	public String getFileName() {
		return fileName;
	}

	
	public ArrayList<String> getMapTagInfo() {
		return mapTagInfo;
	}

	
	
    public HashMap<String, Country> getCountryMap() {
			return countryMap;
		}

	

	public boolean verifyMapFile(String inputMapFile) {
		
//file processing logic

		
	   RiskGraphConnected connected = new RiskGraphConnected(new HashSet<Country>(countryMap.values()));

		if (!connected.isGraphConnected()) {
			return false;
		}

		return true;
	}
	
	public HashSet<Country> getCountriesFromContinent(String continent,
			HashMap<Continent, HashSet<Country>> countriesOfContinent) {
		for (Map.Entry<Continent, HashSet<Country>> pair : countriesOfContinent.entrySet()) {
			if (pair.getKey().getName().equals(continent)) {
				return pair.getValue();
			}
		}
		return null;
	}

	
	public boolean checkAllTags(String fileContent) {
		if (!fileContent.contains("[Map]") || countOccurrences(fileContent, "[Map]") != 1) {
			return false;
		} else if (!fileContent.contains("[Continents]") || countOccurrences(fileContent, "[Continents]") != 1) {
			return false;
		} else if (!fileContent.contains("[Territories]") || countOccurrences(fileContent, "[Territories]") != 1) {
			return false;
		}

		return true;
	}
	
	public int countOccurrences(String input, String search) {
		int count = 0, startIndex = 0;
		Pattern pattern = Pattern.compile(search, Pattern.LITERAL);
		Matcher match = pattern.matcher(input);

		while (match.find(startIndex)) {
			count++;
			startIndex = match.start() + 1;
		}
		return count;
	}

}
