package com.medkha.lol_notes.entities;

import java.util.Date;
import java.util.function.Predicate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.medkha.lol_notes.entities.interfaces.DeathFilterEntity;


@Entity
public class Game implements DeathFilterEntity {
	private static Logger log = LoggerFactory.getLogger(Game.class);

	
	@Id
	@GeneratedValue(generator = Constants.ID_GENERATOR)
	private Long id; 
	
	@Temporal(TemporalType.TIMESTAMP)
	@org.hibernate.annotations.CreationTimestamp
	@Column(updatable = false)
	private Date createdOn; 
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private Role role; 
	
	@NotNull
	private Integer championId;
	
	
	public Game() { 
		
	}
	public Game(Role role, Integer championId) {
		this.role = role; 
		this.championId = championId;
	}
	
	public static Game copy(Game game) { 
		
		Game copy = new Game(); 
		copy.setId(game.getId());
		copy.setChampionId(game.getChampionId());
		copy.setRole(game.getRole());
		
		return copy; 
	}

	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public Integer getChampionId() {
		return championId;
	}
	public void setChampionId(Integer championId) {
		this.championId = championId;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((championId == null) ? 0 : championId.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((role == null) ? 0 : role.hashCode());
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
		Game other = (Game) obj;
		if (championId != other.championId)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (role != other.role)
			return false;
		return true;
	}


	@Override
	public Predicate<Death> getPredicate() {
		return (Death death) -> {
			log.info("getDeathFilterByGamePredicate: Filter by Game with id: {}", this.getId());
			Boolean result = death.getGame().getId().equals(this.getId());
			log.info("Death with id: {} has Game with id: {} equals Filter Game with id:{} ? {} ",
					death.getId(), death.getGame().getId(), this.getId(), result);
			return result;
		};
	}
}
