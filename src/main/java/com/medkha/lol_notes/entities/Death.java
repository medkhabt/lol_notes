package com.medkha.lol_notes.entities;


import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;



@Entity
public class Death {
	
	@Id
	@GeneratedValue(generator = Constants.ID_GENERATOR)
	private Long id; 
	
	@NotNull
	private int minute; 
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private @Valid Reason reasonOfDeath ;
	
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@ManyToOne(fetch = FetchType.LAZY)
	private @Valid Game game; 
	
	protected Death() {}
	
	public Death(int minute, Game game) { 
		this.minute = minute; 
		this.game = game; 
		this.reasonOfDeath = new Reason(); 
	}
	
	public Death(int minute, Reason reasonOfDeath, Game game) {
		this.minute = minute; 
		this.reasonOfDeath = reasonOfDeath; 
		this.game = game; 
	}

	public int getMinute() {
		return minute;
	}

	public void setMinute(int minute) {
		this.minute = minute;
	}

	public Reason getReasonOfDeath() {
		return reasonOfDeath;
	}

	public void setReasonOfDeath(Reason reasonOfDeath) {
		this.reasonOfDeath = reasonOfDeath;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public Long getId() {
		return id;
	}
	
	

	public void setId(Long id) {
		this.id = id;
	}

	public Death copyDeath() {
		return new Death(this.minute, this.reasonOfDeath, this.game); 
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		Death other = (Death) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
	
}
