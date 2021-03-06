package com.medkha.lol_notes.entities;

import java.util.function.Predicate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.medkha.lol_notes.entities.interfaces.DeathFilterEntity;


@Entity
public class Reason implements DeathFilterEntity {
	private static Logger log = LoggerFactory.getLogger(Reason.class);

	@Id
	@GeneratedValue(generator = Constants.ID_GENERATOR)
	private Long id; 
	
//	@NotNull
	@NotBlank
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

	
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Reason other = (Reason) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}


	@Override
	public Predicate<Death> getPredicate() {
		return (Death death) -> {
			log.info("getDeathFilterByReasonPredicate: Filter by Reason with id: {}", this.getId());
			Boolean result = death.getReason().getId().equals(this.getId());
			log.info("Death with id: {} has Reason with id: {} equals Filter Reason with id:{} ? {} ",
					death.getId(), death.getReason().getId(), this.getId(), result);
			return result;
		};
	}
}
