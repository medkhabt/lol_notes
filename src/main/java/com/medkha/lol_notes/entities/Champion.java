package com.medkha.lol_notes.entities;

public enum Champion {
	KAISA("Kai'Sa"), 
	MISSFORTUNE("Miss Fortune"),
	TRISTANA("Tristana"),
	JINX("Jinx"); 
	
	private String championName; 
	
	Champion(String championName) { 
		this.championName = championName; 
	}
	
	public String getChampionName() {
		return this.championName; 
	}
	
	@Override
	public String toString() {
		return this.championName; 
	}
}
