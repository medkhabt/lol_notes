package com.medkha.lol_notes.entities;


import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;




@Entity
public class Death {
	
	@Id
	@GeneratedValue(generator = Constants.ID_GENERATOR)
	private Long id; 
	
	
	private int minute; 
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REASON_ID", nullable = false, updatable = true)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private Reason reason ;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "GAME_ID", nullable = false, updatable = false)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private Game game; 
	
	protected Death() {}
	
	
	public Death(int minute, Reason reason, Game game) {
		this.minute = minute; 
		this.reason = reason; 
		this.game = game;
	}

	
	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public int getMinute() {
		return minute;
	}

	public void setMinute(int minute) {
		this.minute = minute;
	}

	public Reason getReason() {
		return reason;
	}

	public void setReason(Reason reason) {
		this.reason = reason;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}



	public Death copyDeath() {
		return new Death(this.minute, this.reason, this.game); 
	}



	@Override
	public String toString() {
		return "Death [minute=" + minute + ", reasonOfDeath=" + reason.getId() + ", game=" + game.getId() + "]";
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
