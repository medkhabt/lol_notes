package com.medkha.lol_notes.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Reason {
	
	@Id
	@GeneratedValue(generator = Constants.ID_GENERATOR)
	private Long id; 
	
	@NotNull
	@Column(nullable = false)
	private String description; 
	
	
	protected Reason() {}
	
	public Reason(String description) {
		this.description = description; 
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getId() {
		return id;
	}
	
	
}
