package com.medkha.lol_notes.entities;

public enum Role {
	ADC,
	SUPPORT,
	MID,
	JUNGLER,
	TOP; 
	
	public Role getPartner() { 
		
		switch(this) {
		case ADC:
			return SUPPORT; 
		case SUPPORT:
			return ADC;
		case MID:
			return JUNGLER;
		case TOP:
			return JUNGLER;
		case JUNGLER:
			return MID;
		default:
			return null;
		}
		
		
	}
}
