package com.medkha.lol_notes.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class DeathId implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5426551576647683564L;
	
	@Column(name = "GAME_ID")
	protected Long gameId; 
	
	@Column(name = "REASON_ID")
	protected Long reasonId; 
	
	public DeathId() { 
		
	}

	public DeathId(Long gameId, Long reasonId) {
		this.gameId = gameId;
		this.reasonId = reasonId;
	}

	public Long getGameId() {
		return gameId;
	}

	public void setGameId(Long gameId) {
		this.gameId = gameId;
	}

	public Long getReasonId() {
		return reasonId;
	}

	public void setReasonId(Long reasonId) {
		this.reasonId = reasonId;
	}

	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((gameId == null) ? 0 : gameId.hashCode());
		result = prime * result + ((reasonId == null) ? 0 : reasonId.hashCode());
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
		DeathId other = (DeathId) obj;
		if (gameId == null) {
			if (other.gameId != null)
				return false;
		} else if (!gameId.equals(other.gameId))
			return false;
		if (reasonId == null) {
			if (other.reasonId != null)
				return false;
		} else if (!reasonId.equals(other.reasonId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DeathId (gameId=" + gameId + ", reasonId=" + reasonId + ")";
	}
	
	
}
