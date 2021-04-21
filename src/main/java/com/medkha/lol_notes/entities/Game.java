package com.medkha.lol_notes.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;


@Entity
public class Game {
	
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
	@Enumerated(EnumType.STRING)
	private Champion champion; 
	
	
	public Game() { 
		
	}
	public Game(Role role, Champion champion) {
		this.role = role; 
		this.champion = champion; 
	}
	
	public static Game copy(Game game) { 
		
		Game copy = new Game(); 
		copy.setId(game.getId());
		copy.setChampion(game.getChampion());
		copy.setRole(game.getRole());
		
		return copy; 
	}
	
	
	
	
	
	
	
	
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public Champion getChampion() {
		return champion;
	}
	public void setChampion(Champion champion) {
		this.champion = champion;
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
		result = prime * result + ((champion == null) ? 0 : champion.hashCode());
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
		if (champion != other.champion)
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
	
	
	
}
